
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.Date;

@Slf4j
public class BlockingQueueTest {
    // 使用ArrayBlockingQueue来测试生产者消费者问题
    // 生产者有2个，消费者3个，阻塞队列容量为1
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(1);
        Runnable supplier = () -> {
            while (true) {
                try {
                    String name = Thread.currentThread().getName();
                    log.info(time() + "生产者 " + name + " 正在生产");
                    TimeUnit.SECONDS.sleep(3);
                    queue.put(new Object());
                    log.info(time() + "生产者 " + name + " 生产完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
        Runnable consumer = () -> {
            while (true) {
                try {
                    String name = Thread.currentThread().getName();
                    log.info(time() + "消费者 " + name + " 正在等待消费");
                    queue.take();
                    log.info(time() + "消费者 " + name + " 得到了产品");
                    TimeUnit.SECONDS.sleep(4);
                    log.info(time() + "消费者 " + name + " 消费完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
        for (int i = 0; i < 2; i++) new Thread(supplier, "Supplier-" + i).start();
        for (int i = 0; i < 3; i++) new Thread(consumer, "Consumer-" + i).start();
        Thread.sleep(1000);
    }
    @Test
    // 测试ArrayBlockingQueue
    public void test1() throws InterruptedException {
        int[] arr=new int[]{1,2,3,4,5};
        new Thread(
            ()->{
                try {
                    arrFunction(arr);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        ).start();
        new Thread(
            ()->{
                while (true){

                }
            }
        ).start();
        Thread.sleep(1000);
        arr[0]=2;
    }



    @Test
    // 测试SynchronousQueue
    public void test2() throws InterruptedException {
        SynchronousQueue<String> queue=new SynchronousQueue<>();
        new Thread(
            ()->{
                try {
                    log.info("{}", queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        ).start();
        queue.put("aaa");
    }
    
    @Test
    // 测试LinkedTransferQueue
    public void test3() throws InterruptedException {
        LinkedTransferQueue<Integer> queue=new LinkedTransferQueue<>();
        new Thread(
            ()->{
                queue.put(1);
            }
        ).start();
        Thread.sleep(1000);
        queue.put(2);
        queue.forEach(
            (data)->{
                log.info("{}",data);
            }
        );
    }


    @Test
    // 测试PriorityBlockingQueue
    public void test4() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue=new PriorityBlockingQueue<>(10, Integer::compare);
        queue.put(3);
        queue.put(2);
        queue.put(1);
        for(int i=0;i<3;i++){
            // 优先级队列会根据指定的比较规则对数据进行排序，线程在获取数据时按照优先级获取数据
            log.info("{}",queue.take());
        }
    }
    
    @Test
    // 测试DelayQueue
    public void test5() throws InterruptedException {
        /*
        * DelayQueue的使用有很多要求
        *   1.其泛型所指定的类型必须是实现了Delayed接口的类，实际上Delayed接口还继承自Comparable接口，因此实现类实际上需要实现这两个接口的方法
        *   2.Delayed接口定义的getDelay方法返回的是还需要等待多少纳秒才能拿到数据，
        *       比如我这个数据是上午8点添加进队列的，数据需要延迟5s才能被消费者接收
        *       当前时间是8点零3秒，那么还需要等2秒才能到达延迟时间，因此getDelay就返回2s对应的纳秒时长(10^9ns)
        *       这是一个动态变化的值，DelayQueue在底层会循环调用该方法，直到该方法<=0才说明延迟时间到了
        *       getDelay方法会提供一个TimeUnit对象，该对象的时间单位是纳秒，因为底层需要调用condition的awaitNanos方法进行等待
        *       因此需要使用该参数调用convert方法进行时间值的转换
        */
        DelayQueue<DelayClass> queue=new DelayQueue<>();
        queue.put(new DelayClass(2));
        queue.put(new DelayClass(3));
        queue.put(new DelayClass(1));
        for(int i=0;i<3;i++){
            // 优先级队列会根据指定的比较规则对数据进行排序，线程在获取数据时按照优先级获取数据
            log.info("{}",queue.take());
        }
    }
    private static String time() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    private void arrFunction(int[] arr) throws InterruptedException {
        Thread.sleep(2000);
        log.info("{}",arr);
    }

}

@Data
// DelayQueue中的数据对象必须实现了Delayed接口
class DelayClass implements Delayed{
    public DelayClass(Integer value) {
        this.value = value;
        this.startTime=new Date().getTime();

    }

    private Integer value;
    private Long startTime;
    @Override
    public long getDelay(TimeUnit unit) {
        // 这里默认延迟0.5s，因此计算当前值与开始时间的差值，再与500（单位是毫秒）做差计算还需要等待多少秒才能拿到数据
        return unit.convert(500-(System.currentTimeMillis()-startTime),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Integer.compare(this.value,((DelayClass)o).getValue());
    }
}


