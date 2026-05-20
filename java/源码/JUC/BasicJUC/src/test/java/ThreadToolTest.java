import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;


@Slf4j
public class ThreadToolTest {
    @Test
    // 测试CountDownLatch
    public void test1() throws InterruptedException {
        Random random=new Random();
        CountDownLatch latch=new CountDownLatch(10);
        for(int i=1;i<=10;i++){
            new Thread(
                ()->{
                    try {
                        // 让这些线程休眠5~10s
                        Thread.sleep((((int)(random.nextDouble()*5+1))+5)*1000);
                        log.info("线程{}完成任务",Thread.currentThread().getName());
                        latch.countDown();  // 完成任务后，使CountDownLatch对象内的计数-1
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            ).start();
        }
        // 该线程负责报告当前的count值
        new Thread(
            ()->{
                while(true){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long count=latch.getCount();
                    log.info("当前count值:{}",count);
                    if(count==0){
                        break;
                    }
                }
            }
        ).start();
        latch.await();  // 等待直到latch的count值减到0
        log.info("{}","所有任务完成!");
    }


    @Test
    // 测试CyclicBarrier
    public void test2() throws InterruptedException {
        int loopCount=10;
        CyclicBarrier barrier = new CyclicBarrier(5);
        // 该线程每隔0.1s打印当前正在等待的线程数量
        new Thread(
            new Runnable() {
                private Integer count=0;  // 统计累计等待的线程数量
                private Integer prevCount=-1;  // 记住上一次count的值
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        int waiters=barrier.getNumberWaiting();  // 得到等待的线程数量
                        log.info("当前等待的线程数量:{}", waiters);
                        // 如果count的值到达了loopCount规定的循环数量，那么说明全部的线程都执行完了，跳出循环
                        if(waiters!=prevCount&&++count==loopCount){
                            break;
                        }
                        prevCount=waiters;
                    }
                }
            }
        ).start();
        for(int i=1;i<=loopCount;i++){
            new Thread(
                ()->{
                    try {
                        barrier.await();
                        log.info("{}","已冲破壁障");
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                }
            ).start();
            Thread.sleep(200);  // 每隔0.2s再启动下一个线程，方便观察到等待的线程数量的变动
        }
    }

    @Test
    // 测试CyclicBarrier的reset方法以及线程出现中断的情况
    public void test3() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5);
        Runnable run=()->{
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };
        for (int i = 0; i < 2; i++) {
            new Thread(run).start();
        }
        Thread.sleep(200);  // 等待新创建的线程开始运行
        log.info("正在等待的线程数:{}",barrier.getNumberWaiting());
        log.info("想冲破该壁障需要的线程数:{}",barrier.getParties());
//        barrier.reset();
        Thread thread = new Thread(run);
        thread.start();
        Thread.sleep(100);  // 等待新创建的线程开始运行
        thread.interrupt();
        Thread.sleep(100);  // 该等待的作用是等待barrier处理完毕线程出现中断的情况
        log.info("正在等待的线程数:{}",barrier.getNumberWaiting());
        new Thread(run).start();  // 此时再尝试运行会报错，从报错顺序可以看到此次报错是在第二次输出线程数之后的
    }
    
    @Test
    // 测试Semaphore
    public void test4() throws InterruptedException {
        Semaphore semaphore=new Semaphore(2);
        new Thread(
            ()->{
                int count=0;
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("剩余许可证数量:{}",semaphore.availablePermits());
                    log.info("是否存在线程等待许可证:{}",semaphore.hasQueuedThreads());
                    log.info("等待许可证线程数量:{}",semaphore.getQueueLength());
                    if(++count>=7){
                        break;
                    }
                }
            }
        ).start();
        Runnable run=()->{
            try {
                semaphore.acquire(2);  // 获取信号量许可证
                log.info("线程{}执行...",Thread.currentThread().getName());
                Thread.sleep(2000);
                semaphore.release(2);  // 执行完毕后释放信号量许可证
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(run).start();
        }
        // 该方法会尝试让当前线程去获取
        log.info("{}", semaphore.drainPermits());
        Thread.sleep(6000);
    }

    @Test
    // 测试Exchange
    public void test5() throws InterruptedException {
        Exchanger<Integer> exchanger=new Exchanger<>();
        new Thread(
            ()->{
                try {
                    // exchange方法返回另一个线程传过来的值，并将本线程想传递过去的值发出去
                    Integer data = exchanger.exchange(10);
                    log.info("线程1得到主线程的数据:{}",data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },
            "线程1"
        ).start();
        Integer data = exchanger.exchange(20);
        log.info("主线程得到线程1的数据:{}",data);
    }

    @Test
    // 测试Fork/Join框架
    public void test6(){
        ForkJoinPool pool=new ForkJoinPool();
        Integer invoke = pool.invoke(new MyTask(1, 1000));
        log.info("最终的运算结果:{}",invoke);
    }
}

@Slf4j
class MyTask extends RecursiveTask<Integer>{
    private Integer begin;
    private Integer end;

    public MyTask(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    // compute用来详细告诉Fork/Join框架计算的逻辑
    protected Integer compute() {
        final int THRESHOLD = 125;
        // 如果end-begin的差值大于125，那么把这个任务二分，分别计算其结果再相加再返回
        if(end-begin> THRESHOLD){
            log.info("将{}到{}的求和二分为两部分",begin,end);
            MyTask task1=new MyTask(begin,(begin+end)/2);
            MyTask task2=new MyTask((begin+end)/2+1,end);
            task1.fork();
            task2.fork();
            return task1.join()+task2.join();
        }else{
            // 如果小于125直接开算然后把结果返回
            log.info("线程{}开始执行{}到{}的和",Thread.currentThread().getName(),begin,end);
            int sum=0;
            for(int i=begin;i<=end;i++){
                sum+=i;
            }
            return sum;
        }
    }
}
