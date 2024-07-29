
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReentrantReadWriteLockTest {
    @Test
    // 测试读锁（在没有加写锁的情况下）可以被多个线程共享，但是加了写锁其它线程就无法加读锁或写锁了
    // 锁降级:同一个线程，加了写锁还可以加读锁
    public void test1() throws InterruptedException {
        ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
//        lock.writeLock().lock();
//        log.info("{}","主线程获得写锁");
        new Thread(
            ()->{
                lock.readLock().lock();
                log.info("{}","A线程获得读锁");
            },
            "A"
        ).start();
        TimeUnit.SECONDS.sleep(1);
        lock.readLock().lock();
        log.info("{}","主线程获得读锁");
    }

    @Test
    // 测试ReentrantReadWriteLock不支持锁升级
    // 锁升级:同一个线程，加了读锁还可以加写锁
    public void test2(){
        ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
        lock.readLock().lock();
        log.info("{}","主线程获得读锁");
        lock.writeLock().lock();
        log.info("{}","主线程获得写锁");
    }

    @Test
    // 测试不同线程之间，加了写锁就不能加读锁，加了读锁就不能加写锁
    public void test3() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        new Thread(lock.readLock()::lock).start();
        Thread.sleep(1000);
        lock.writeLock().lock();
    }

    @Test
    // 测试锁的可重入特性
    public void test4() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        lock.writeLock().lock();
        new Thread(
            () -> {
                log.info("{}","线程A尝试获取锁");
                lock.writeLock().lock();
                log.info("{}", "成功获取到写锁！");
            },
            "A"
        ).start();
        Thread.sleep(1000);
        log.info("{}", "释放第一层锁！");
        lock.writeLock().unlock();
        Thread.sleep(1000);
        log.info("{}", "释放第二层锁！");
        lock.writeLock().unlock();
    }
}
