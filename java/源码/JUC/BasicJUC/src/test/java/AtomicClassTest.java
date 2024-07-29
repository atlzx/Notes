
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.*;

@Slf4j
public class AtomicClassTest {
    @Test
    // 测试原子数值如AtomicInteger、AtomicLong、AtomicBoolean等原子类
    public void test1(){
        // 这里演示的是AtomicInteger，AtomicLong和AtomicBoolean的API都大差不差
        AtomicInteger i=new AtomicInteger(1);
        log.info("{}",i.getAndIncrement());  // 相当于i++
        log.info("{}",i.getAndSet(5));  // 先用后赋值
        log.info("{}",i.getAndAdd(6));  // 先用后加
        log.info("{}",i.incrementAndGet());  // 相当于++i
        log.info("{}",i.addAndGet(6));  // 先加后用
        i.set(5);  // 这个可以直接赋值
        log.info("{}", i.compareAndSet(5, 2));  // CAS操作修改i的值
        log.info("{}",i.get());  // 得到当前i的值
        i.lazySet(10);  // 不采用volatile的可见机制来设置值
        log.info("{}",i.get());
    }
    
    @Test
    // 测试原子数组
    public void test2(){
        // 原子数组类与原子基本类型类的API也差不多，就是方法多了一个参数来指定要操作的值对应的下标而已
        AtomicIntegerArray arr=new AtomicIntegerArray(new int[]{1,2,3,4,5});
        log.info("{}", arr.getAndAdd(0, 2));
        log.info("{}",arr.get(0));
    }

    @Test
    // 测试原子类型的原子性
    public void test3() throws InterruptedException {
        // 这是使用原子类型来实现volatile关键字无法解决的问题
        // 原子类封装的方法都保证了对其value的操作都是原子性的，因此可以保证该操作的原子性，从而避免多个线程同时提交同一个值的情况
        AtomicInteger i=new AtomicInteger(0);
        Runnable run=()->{
            for(int j=1;j<=100000;j++){
                i.incrementAndGet();
            }
        };
        new Thread(run,"A").start();
        new Thread(run,"B").start();
        Thread.sleep(100);
        log.info("{}",i.get());
    }
    
    @Test
    // 测试引用类型的原子类
    public void test4(){
        // 通过泛型来指定引用类型
        AtomicReference<String> str=new AtomicReference<>("haha");
        log.info("{}", str.compareAndSet("haha", "hello"));
        log.info("{}",str.get());
    }
    
    
    @Test
    // 测试字段原子更新器
    public void test5() throws NoSuchFieldException {
        // 这里测试的是引用类型的原子字段更新器，JUC还提供了一些基本数据类型的原子字段更新器，如AtomicIntegerFieldUpdater
        // 它们的使用方式都差不多，举一个例子就够了
        Student student = new Student(0);
        // 字段更新器需要调用其自己提供的静态方法newUpdater来得到更新器对象
        // 首先类型声明的两个泛型类型，第一个是属性所在的类，另一个是属性类型
        // newUpdater接收三个参数
        //     第一个参数需要的是属性所在类的Class对象
        //     第二个参数需要的是属性类型的Class对象
        //     第三个参数需要属性名
        AtomicReferenceFieldUpdater<Student,Integer> updater=AtomicReferenceFieldUpdater.newUpdater(
            Student.class,
            Integer.class,
            "age"
        );

        log.info("{}", updater.compareAndSet(student, 0, 10));
        log.info("{}",student.age);
    }


    @Test
    // 测试Adder与Atomic的差距
    public void test6() throws InterruptedException {
//        LongAdder longAdder=new LongAdder();
        AtomicLong atomicLong=new AtomicLong(0);
        Runnable run=()->{
            for (int i = 0; i < 100000; i++) {
//                longAdder.add(1);
                atomicLong.incrementAndGet();
            }
        };
        for(int j=0;j<1000;j++){
            new Thread(run).start();
        }
        Thread.sleep(1000);
//        log.info("{}",longAdder.sum());
        // 测试时会出现输出的值并不是我们期望的值，这是因为主线程休眠时间太短导致的，主线程休眠完执行该代码时其它线程还没有全执行完就会导致输出的结果小于预期值
        // 这对于我们的测试已经足够了，可以很明显的看出来AtomicLong明显运算速度比LongAdder慢，要不然1s都过去了不可能算不出来
        log.info("{}",atomicLong.get());
    }


    @Test
    // 测试版本号机制的CAS操作
    public void test7(){
        AtomicStampedReference<String> reference=new AtomicStampedReference<>("haha",1);
        reference.compareAndSet("haha","hello World",1,2);
        log.info("{},{}",reference.getReference(),reference.getStamp());


    }



}

class Student{
    public volatile Integer age;

    public Student(Integer age) {
        this.age = age;
    }
}
