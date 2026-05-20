package ThreadSample;

public class ThreadSample2 {
    public static void main(String[] args) {
        Runnable r1=new ThreadSample2_1();
        // 以这种方式创建多个线程对象时，不需要再额外创建Runnable对象了，此时如果在创建线程对象传入的参数是同一个Runnable对象时，run方法操作使调用的Runnable对象的属性和方法也是共享的
        // System.out.println(r1.count);  直接使用对象读取该属性是读取不了的，因为编译时该对象的类型是Runnable，输出能看到count的原因是方法在类内部，可以访问到属性
        Thread t1=new Thread(r1);
        Thread t2=new Thread(r1);
        t1.start();
        t2.start();
        for (int i = 1; i <=10000; i++) {
            if(i%2!=0){
                System.out.println("主线程输出:"+i);
            }
        }
    }
}

class ThreadSample2_1 implements Runnable{

    int count=1;

    @Override
    public void run() {
        for (int i = 1; i <= 10000; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+"输出:"+i+" 输出count"+count);
                count++;
            }
        }
    }
}
