import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTest {
    @Test
    public void test1() throws InterruptedException {
        // 测试ReentrantLock的可重入特性
        ReentrantLock lock = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        lock.lock();
        lock.lock();   //连续加锁2次
        new Thread(() -> {
            System.out.println("线程2想要获取锁");
            lock.lock();
            System.out.println("线程2成功获取到锁");
        }).start();
        lock.unlock();
        System.out.println("线程1释放了一次锁");
        TimeUnit.SECONDS.sleep(1);
//        lock.unlock();
        lock.unlock();
        System.out.println("线程1再次释放了一次锁");  //释放两次后其他线程才能加锁

        // 下面的synchronized貌似也是支持可重入的
        synchronized (ReentrantLockTest.class) {
            synchronized (ReentrantLockTest.class) {
                new Thread(() -> {
                    System.out.println("");
                    log.info("{}", "线程2尝试获取锁");
                    synchronized (ReentrantLockTest.class) {
                        log.info("{}", "线程2成功获取到锁");
                    }

                }).start();
            }
            log.info("线程1释放了一次锁");
        }
        log.info("线程1再次释放了一次锁");
    }

    @Test
    public void test2() throws InterruptedException {
        // getHoldCount和isLocked方法示例。
        // getHoldCount方法用于获取该锁被当前线程占用的次数
        // isLocked方法用于判断该锁是否被某个线程占用
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        System.out.println("当前加锁次数：" + lock.getHoldCount() + "，是否被锁：" + lock.isLocked());
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        System.out.println("当前加锁次数：" + lock.getHoldCount() + "，是否被锁：" + lock.isLocked());
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        System.out.println("当前加锁次数：" + lock.getHoldCount() + "，是否被锁：" + lock.isLocked());
    }

    @Test
    public void test3() throws InterruptedException {
        // getQueueLength和hasQueuedThread方法示例。
        // getQueueLength方法用于得到正在等待获取该锁的线程估计值
        // hasQueuedThread方法用于判断指定线程是否在等待获取该锁
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Thread t1 = new Thread(lock::lock), t2 = new Thread(lock::lock);;
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("当前等待锁释放的线程数："+lock.getQueueLength());
        System.out.println("线程1是否在等待队列中："+lock.hasQueuedThread(t1));
        System.out.println("线程2是否在等待队列中："+lock.hasQueuedThread(t2));
        System.out.println("当前线程是否在等待队列中："+lock.hasQueuedThread(Thread.currentThread()));
    }

    @Test
    public void test4() throws InterruptedException {
        // getWaitQueueLength方法样例，该方法用于根据condition判断等待占用该锁的线程估计值
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }).start();
        TimeUnit.SECONDS.sleep(1);
        lock.lock();
        System.out.println("当前Condition的等待线程数："+lock.getWaitQueueLength(condition));
        condition.signal();
        System.out.println("当前Condition的等待线程数："+lock.getWaitQueueLength(condition));
        lock.unlock();
    }

    @Test
    public void test5(){
        // 测试公平锁与非公平锁
        // ReentrantLock的构造器可以传入一个参数，用来表示该锁是否为公平锁
        ReentrantLock lock = new ReentrantLock(true);

        Runnable action = () -> {
            System.out.println("线程 "+Thread.currentThread().getName()+" 开始获取锁...");
            lock.lock();
            System.out.println("线程 "+Thread.currentThread().getName()+" 成功获取锁！");
            lock.unlock();
        };
        for (int i = 0; i < 10; i++) {   //建立10个线程
            new Thread(action, "T"+i).start();
        }
    }
}
