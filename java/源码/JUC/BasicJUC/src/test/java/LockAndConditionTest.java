
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockAndConditionTest {
    private static int i = 0;

    @Test
    public void test1() throws InterruptedException {
        // 这里依旧以自增的例子来看，看看lock是否能够发挥与synchronized的作用
        Lock testLock = new ReentrantLock();   //  创建一个可重入锁实例
        Runnable action = () -> {
            for (int j = 0; j < 100000; j++) {
                testLock.lock();    //加锁，加锁成功后其他线程如果也要获取锁，会阻塞，等待当前线程释放
                i++;
                testLock.unlock();  //解锁，释放锁之后其他线程就可以获取这把锁了（注意在这之前一定得加锁，不然报错）
            }
        };
        new Thread(action).start();
        new Thread(action).start();
        Thread.sleep(1000);   //等上面两个线程跑完
        System.out.println(i);  // 打印i的值，从理论上来说，加锁可以保证执行的原子性。因此结果为200000
    }

    @Test
    public void test2() throws InterruptedException {
        // 这里用来测试condition的await和signal方法的作用
        //   - await方法会使线程释放锁，并进入等待状态
        //   -
        Lock testLock = new ReentrantLock();
        Condition condition = testLock.newCondition();
        new Thread(() -> {
            testLock.lock();   //和synchronized一样，必须持有锁的情况下才能使用await
            System.out.println("线程1进入等待状态！");
            try {
                condition.await();   //进入等待状态，等待会释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1等待结束！");
            testLock.unlock();
        }).start();
        Thread.sleep(100); //防止线程2先跑
//        new Thread(() -> {
//            testLock.lock();
//            System.out.println("线程2开始唤醒其他等待线程");
//            condition.signal();   //唤醒线程1，但是此时线程1还必须要拿到锁才能继续运行
//            System.out.println("线程2结束");
//            testLock.unlock();   //这里释放锁之后，线程1就可以拿到锁继续运行了
//        }).start();
    }

    @Test
    public void test3() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);  // 让线程睡三秒
        System.out.println();
        log.info("90s是{}分钟", TimeUnit.SECONDS.toMinutes(90));  // 由于返回值是个整数，因此它会向下取整

    }
}
