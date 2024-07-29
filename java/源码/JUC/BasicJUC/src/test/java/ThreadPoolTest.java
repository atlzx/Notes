import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolTest {
    @Test
    // 测试ThreadPoolExecutor线程池
    public void test1() throws InterruptedException {
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(4,5,3, TimeUnit.SECONDS,new SynchronousQueue<>());
        for(int i=0;i<5;i++){
            poolExecutor.execute(
                ()->{
                    log.info("开始时间:{}",new Date());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("结束时间:{}",new Date());
                }
            );
        }
        Thread.sleep(1000);
        log.info("线程池中的线程数量:{}", poolExecutor.getPoolSize());
        Thread.sleep(4000);
        poolExecutor.shutdownNow();
    }
    
    
    @Test
    // 测试四种拒绝策略和自定义策略
    public void test2() throws InterruptedException {
        // abortPolicy拒绝策略在出现多余请求时会直接抛出异常
        // callerRunsPolicy拒绝策略在出现多余请求时会让当前线程运行该方法
        // discardOldestPolicy拒绝策略在出现多余请求时会直接让等待队列最先来的请求出队，然后让该请求入队
        // discardPolicy拒绝策略在出现多余请求时会什么也不做
//        abortPolicyTest();
//        callerRunsPolicyTest();
//        discardOldestPolicyTest();
//        discardPolicyTest();
        customRejectPolicyTest();

        Thread.sleep(1000);

    }
    
    
    @Test
    // 测试自定义线程创建工厂参数
    public void test3(){
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1, 1, 3,
            TimeUnit.SECONDS, new SynchronousQueue<>(),
            new ThreadFactory() {
                private int count=0;
                @Override
                public Thread newThread(Runnable r) {
                    count++;
                    return new Thread(r,"自定义线程工厂得到的线程"+count);
                }
            },
        new ThreadPoolExecutor.DiscardPolicy()
        );
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }


    @Test
    // 测试Future类
    public void test4() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>());
        Future<String> future = executor.submit(
            () -> {
                Thread.sleep(1000);
                return "Hello World";
            }
        );

        log.info("future是否执行完毕了:{}",future.isDone());
        log.info("执行是否被取消了:{}",future.isCancelled());
        Thread.sleep(2000);
        log.info("执行完毕后的返回值{}",future.get());
        executor.shutdown();
    }
    
    @Test
    // 测试ScheduledThreadPoolExecutor
    public void test5() throws ExecutionException, InterruptedException {
//        scheduleTest();  // 测试schedule方法
//        scheduleAtFixedRateTest();  // 测试scheduleAtFixedRate方法
        scheduleWithFixedDelayTest();  // 测试scheduleWithFixedDelay方法

    }

    @Test
    //
    public void test6(){
        boolean isGood=true;
        int count=0;
        try{
            while(count<=5){
                if(++count==3){
                    int a=1/0;
                }
            }
            isGood=false;
        }finally {
            log.info("isGood的值:{}",isGood);
        }
    }


    private void abortPolicyTest(){
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1,1,3, TimeUnit.SECONDS,new SynchronousQueue<>());
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }


    private void callerRunsPolicyTest(){
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1,1,3, TimeUnit.SECONDS,new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }

    private void discardOldestPolicyTest(){
        // 这里测试出现StackOverflowError是因为DiscardOldestPolicy底层抛弃队列最先来的请求的流程是:
        // 得到等待队列，然后调用其poll方法
        // 接下来调用线程池对象的execute执行本次请求的方法
        // 但是这里指定的阻塞队列是SynchronousQueue，它没有容量，因此出队实际上没有用，这就导致它出队再执行execute还是会执行拒绝策略，最后导致无限递归，然后导致栈溢出
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1,1,3, TimeUnit.SECONDS,new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardOldestPolicy());
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }

    private void discardPolicyTest(){
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1,1,3, TimeUnit.SECONDS,new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }

    private void customRejectPolicyTest(){
        // 由于拒绝策略是靠RejectedExecutionHandler接口对象实现的，而该接口是一个函数式接口
        // 因此直接使用lambda表达式自定义就行了
        ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(1,1,3, TimeUnit.SECONDS,new SynchronousQueue<>(),
            (run,executor)->{
                log.info("请求拒绝策略执行了...");
            }
        );
        for(int i=1;i<=2;i++){
            poolExecutor1.execute(
                ()->{
                    log.info("{}","aaa");
                }
            );
        }
    }

    private void scheduleTest() throws InterruptedException, ExecutionException {
        ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
        // schedule方法用来一次性的使一个线程延时地执行一个任务
        ScheduledFuture<String> future = executor.schedule(
            () -> {
                log.info("{}","线程开始执行");
                return "Hello World";
            },
            3L,
            TimeUnit.SECONDS
        );
        for( ; ; ){
            Thread.sleep(1000);
            long time=future.getDelay(TimeUnit.MILLISECONDS);
            log.info("还需要延时等待{}毫秒",time);
            if(time<=0){
                break;
            }
        }
        log.info("{}",future.get());
    }

    private void scheduleAtFixedRateTest() throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
        // schedule方法用来一次性的使一个线程延时地执行一个任务
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(
            new Runnable() {
                private Integer count=1;
                @Override
                public void run() {
                    log.info("{}","方法开始执行");
                    log.info("{}","方法第"+count+"次执行");
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("{}","方法执行完毕");
                }
            },
            3,
            1,
            TimeUnit.SECONDS
        );
        for( ; ; ){
            Thread.sleep(1000);
            long time=future.getDelay(TimeUnit.MILLISECONDS);
            log.info("距离线程第一次执行还需要延时等待{}毫秒",time);
            if(time<=0){
                break;
            }
        }
        log.info("{}",future.get());
    }

    private void scheduleWithFixedDelayTest() throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
        // schedule方法用来一次性的使一个线程延时地执行一个任务
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(
            new Runnable() {
                private Integer count=1;
                @Override
                public void run() {
                    log.info("{}","方法开始执行");
                    log.info("{}","方法第"+count+"次执行");
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("{}","方法执行完毕");
                }
            },
            3,
            1,
            TimeUnit.SECONDS
        );
        for( ; ; ){
            Thread.sleep(1000);
            long time=future.getDelay(TimeUnit.MILLISECONDS);
            log.info("距离线程第一次执行还需要延时等待{}毫秒",time);
            if(time<=0){
                break;
            }
        }
        log.info("{}",future.get());
    }

    
}
