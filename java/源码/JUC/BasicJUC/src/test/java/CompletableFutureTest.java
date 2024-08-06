import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CompletableFutureTest {
    @Test
    // 测试CompletableFuture的异步执行
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future=CompletableFuture.supplyAsync(
            ()->{
                Integer i=1;
                log.info("{}","线程开始执行");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return i+10;
            }
        );
        log.info("等待CompletableFuture的异步线程执行完毕后的值:{}",future.get());
    }

    @Test
    // 测试无返回值的CompletableFuture的异步执行
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future=CompletableFuture.runAsync(
            ()->{
                log.info("{}","线程开始执行");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );
        future.get();
        log.info("{}","主线程等待完毕");
    }

    @Test
    // 测试CompletableFuture的类似Promise的特性
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future=CompletableFuture.supplyAsync(
            ()->{
                return 10;
            }
        ).thenApply(
            (i)->{
//                i=i/0;
                return i*10;
            }
        ).thenApply(
            (i)->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return i-10;
            }
        ).exceptionally(
            (e)->{
                log.info("异常:{}",e.toString());
                return 2;
            }
        );
        log.info("值:{}", future.get());
    }
    
    
    @Test
    // 测试CompletableFuture无返回结果式消费
    public void test4() throws ExecutionException, InterruptedException {
        AtomicBoolean isSuccess=new AtomicBoolean(false);
        CompletableFuture<Void> future=CompletableFuture.supplyAsync(
            ()->{
                return 10;
            }
        ).thenApply(
            (i)->{
                i=i/0;
                return i*10;
            }
        ).thenApply(
            (i)->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return i-10;
            }
        ).thenAccept(
            (i)->{
                log.info("运行完成,最终结果为{}",i);
                isSuccess.compareAndSet(false,true);
            }
        ).exceptionally(
            (e)->{
                log.info("异常:{}",e.toString());
                return null;
            }
        );
        log.info("执行是否成功:{}",isSuccess);
    }
    
    @Test
    // 测试CompletableFuture的结果合并
    public void test5() throws ExecutionException, InterruptedException {
        AtomicInteger i=new AtomicInteger(0);
        CompletableFuture<Integer> future1=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","先加一个10");
                i.compareAndSet(i.get(),i.get()+10);
                return i.get();
            }
        );
        CompletableFuture<Integer> future2=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","再乘以10");
                i.compareAndSet(i.get(),i.get()*10);
                return i.get();
            }
        );
        CompletableFuture<ArrayList<Integer>> result = future1.thenCombine(future2,
            (a, b) -> {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(a);
                list.add(b);
                return list;
            }
        );
        log.info("最终结果为:{}",result.get());



    }
    
    @Test
    // 测试CompletableFuture的AllOf和AnyOf
    public void test6() throws ExecutionException, InterruptedException {
        AtomicInteger i=new AtomicInteger(0);
        CompletableFuture<Integer> future1=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","加10");
                i.compareAndSet(i.get(),i.get()+10);
                return i.get();
            }
        );
        CompletableFuture<Integer> future2=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","乘以10");
                i.compareAndSet(i.get(),i.get()*10);
                return i.get();
            }
        );
        CompletableFuture<Integer> future3=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","减10");
                i.compareAndSet(i.get(),i.get()-10);
                return i.get();
            }
        );
        CompletableFuture<Integer> future4=CompletableFuture.supplyAsync(
            ()->{
                log.info("{}","除以10");
                i.compareAndSet(i.get(),i.get()/10);
                return i.get();
            }
        );

        CompletableFuture<Void> res1 = CompletableFuture.allOf(future1, future2, future3, future4);
        CompletableFuture<Object> res2 = CompletableFuture.anyOf(future1, future2, future3, future4);

        log.info("{},{}",res1.get(),res2.get());
    }

}
