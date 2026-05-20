package com.example.redis.customer;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Slf4j
@Component
@Getter
// TODO 现在的锁是存在问题的，当锁被使用时，其续期线程就会被创建，然而该线程一旦被创建就无法被销毁，因为销毁时可能会导致并发问题。现在不知道怎么解决
// TODO 并发问题主要存在于获取锁的步骤和线程结束步骤的冲突，当一个新线程尝试去获得锁时，如果此时续期线程被关闭，那么线程可能会在没有续期线程续期的情况下去执行业务，此时若线程未在锁生效期间内完成业务，那么就会出现并发问题
public class RedisDistributedLock implements Lock {
    public static final long DEFAULT_EXPIRE_TIME=30L;
    // 表示锁多长时间过期，默认是30s
    private long expireTime;
    // 想获取锁的线程所在服务的id，该值应该是不变的，因为服务一般是不变的，而一个服务可以有多个线程
    // 使用 服务id+线程id的方式可以唯一确定一个当前正在占用该锁的线程
    private String serviceId;
    // 锁的名称，多个线程争抢同一把锁时，锁是不变的，因此该属性值也应该是不变的
    // 该值是判断Redis分布式锁存在的重要指标，因为它表示Redis分布式锁的key，可以使用exists命令判断是否存在该锁
    // 因此在多个线程争抢该锁时，必须确保该值固定不变，以便某一线程获得锁后其它线程可以根据该属性判断锁是否被占用
    private String lockName;
    // 该属性用来标记占有该锁的线程id，续期线程通过该属性来判断锁是否存在
    private long threadId;
    // 该属性用来标识进行续期的线程id,当其不为空时，在线程获取到锁后将不会再创建自动续期的线程了
    private Long renewalThread;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    // 该构造器是给SpringBoot进行自动装配时提供的
    public RedisDistributedLock() {
        this.expireTime=DEFAULT_EXPIRE_TIME;
        this.serviceId=this.lockName=IdUtil.simpleUUID();
    }

    public RedisDistributedLock(RedisTemplate<String,Object> redisTemplate, String serviceId, String lockName){
        this(redisTemplate,serviceId,lockName,DEFAULT_EXPIRE_TIME);
    }

    // 该构造器是工厂类调用的
    public RedisDistributedLock(RedisTemplate<String,Object> redisTemplate, String serviceId, String lockName,Long expireTime) {
        this.expireTime=expireTime;
        this.serviceId=serviceId;
        this.lockName=lockName;
        this.redisTemplate=redisTemplate;
    }

    @Override
    // lock底层直接调tryLock
    public void lock() {
        tryLock();
    }



    @Override
    // tryLock再调重载的tryLock方法
    public boolean tryLock() {
        try {
            tryLock(-1,TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    /*
        下面的Lua脚本语句表示判断锁是否存在，或是否是同一个线程在尝试获取该锁，如果锁不存在或同一个线程在尝试获得该锁
        那么就更新锁的value值同时重置锁的过期时间，并返回1表示加锁成功
        如果上述条件不满足，那么返回0表示加锁失败
         if redis.call('exists',KEYS[1])==0 or redis.call('exists',KEYS[1],ARGV[1])==1 then
            redis.call('hincrby',KEYS[1],KEYS[2],1)
            redis.call('expire',KEYS[1],ARGV[1])
            return 1
         else
            return 0
         end
     */
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long endTime = System.currentTimeMillis()+unit.toMillis(time);
        final String script= """
             if redis.call('exists',KEYS[1])==0 or redis.call('hexists',KEYS[1],KEYS[2])==1 then
                 redis.call('hincrby',KEYS[1],KEYS[2],1)
                 redis.call('expire',KEYS[1],ARGV[1])
                 return 1
             else
                 return 0
             end
        """;
        // 直接调用redisTemplate.execute时IDEA会提示拆箱时可能导致空指针异常
        // 这里使用Boolean.FALSE.equals方法可以规避该问题
        // 在执行Lua脚本之前判断时间是否合法
        while(
            !(time>=0&&System.currentTimeMillis()>endTime)
                &&
            Boolean.FALSE.equals(
                    redisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class),
                    Arrays.asList(lockName, serviceId + Thread.currentThread().getId()),
                    unit.toSeconds(expireTime)
                )
            )
        ){
            // 休眠20ms再尝试获得锁
            TimeUnit.MILLISECONDS.sleep(20);
        }
        log.info("{}","加锁成功");
        // 得到锁后，将threadId属性置为当前属性，表示锁被哪个线程占用
        this.threadId=Thread.currentThread().getId();
        // 新启动一个线程，用来给key续期
        if(renewalThread==null){
            renewalThread=autoRenewal(expireTime);
        }
        return true;
    }

    @Override
    /*
        解锁时，先考虑key存不存在，若不存在则返回null
        再考虑可重入锁的问题，如果第一个条件不满足那么锁一定存在，存在时让锁的value-1，如果减到0说明可重入锁全部被解锁了，那么就删除该锁。否则返回0
        需要注意的是Lua的语法中，与Java不同的是elseif这两个词语中间是没有空格的
        if redis.call('hexists',KEYS[1],KEYS[2])==0 then
                return nil
        elseif redis.call('hincrby',KEYS[1],KEYS[2],-1)==0 then
            redis.call('del',KEYS[1])
            return 1
        else
            return 0
        end
     */
    public void unlock() {
        final String script= """
            if redis.call('hexists',KEYS[1],KEYS[2])==0 then
                return nil
            elseif redis.call('hincrby',KEYS[1],KEYS[2],-1)==0 then
                redis.call('del',KEYS[1])
                return 1
            else
                return 0
            end
        """;
        String id=serviceId+Thread.currentThread().getId();
        redisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class),Arrays.asList(lockName,id));

    }

    /*
        如果锁是存在的，那么给锁续期
        否则返回0结束，当返回0时，肯定是当前的锁已经不存在了，因此负责续期的线程会结束其执行
        if redis.call('hexists',KEYS[1],KEYS[2])==1 then
             redis.call('expire',KEYS[1],ARGV[1])
             return 1
         else
             return 0
         end
     */
    // 该方法在Redis分布式锁为单实例对象时只会创建一个续期线程，但是如果通过工厂模式创建则会导致每个对象都对应一个续期线程
    // 因为工厂模式每创建一个实例对象都是新的，
    private long autoRenewal(long time){
        log.info("{}","续期线程被创建");
        final String script= """
             if redis.call('hexists',KEYS[1],KEYS[2])==1 then
                 redis.call('expire',KEYS[1],ARGV[1])
                 return 1
             else
                 return 0
             end
        """;
        Long forwardRes= 1L;  // 这是期望的返回值，当redisTemplate.execute方法返回1时，证明锁是存在的，现在暂时没用
        Thread thread = new Thread(
            () -> {
                // 线程会一直持续下去给锁续期
                while (true) {
                    try {
                        // 使线程休眠key过期的1/3时间
                        TimeUnit.SECONDS.sleep(time / 3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 休眠完成后，给key续期
                    redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockName, serviceId + threadId), time);
                }
                // 此处暂时没用
                // 该线程不能置threadId属性为null,只能将renewalThreadId属性置为null
                // 因为对threadId属性赋值会与tryLock方法对threadId属性的赋值操作出现冲突
                // 如果两者的赋值顺序不一致，那么会导致并发问题
                // 具体就是锁还在被线程持有，但是分布式锁对象的threadId属性却是null
                // 此时如果持有锁的线程未能在锁过期时间内执行完毕，那么续期线程也无法为其续期
                // 因为threadId是null，续期线程会认为该锁并不被任何线程持有
//                this.renewalThread=null;
            }
        );
        thread.start();
        return thread.getId();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }


}
