package com.example.redis.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedLockService {
    @Resource
    private Redisson redisson;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public void redLockConsume(){
        RLock lock = redisson.getLock("redisLock");

        // 尝试获得锁
        lock.lock();

        // 该方法是等待获取锁10s，如果超过了等待时间，那么就放弃上锁
//        lock.lock(10, TimeUnit.SECONDS);
        try {
            String count = stringRedisTemplate.opsForValue().get("goods");
            int number = Integer.parseInt(count);
            if(number>1){
                stringRedisTemplate.opsForValue().set("goods",String.valueOf(number-1));
            }
            log.info("还剩下{}件商品",number-1);
        } finally {
            // 判断所是否存在且是否是当前线程在持有锁，如果满足条件再解锁
            if(lock.isLocked() && lock.isHeldByCurrentThread())
            {
                lock.unlock();
            }
        }
    }
}
