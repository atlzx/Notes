package ThreadSample;

// 现在需要实现多线程卖票，多个线程共用100张票

import java.util.concurrent.locks.ReentrantLock;

public class ThreadSample4 {
    public static void main(String[] args) {
        ThreadSample4_1 threadSample4_1=new ThreadSample4_1();
        Thread t1=new Thread(threadSample4_1,"线程1");
        Thread t2=new Thread(threadSample4_1,"线程2");
//        t1.start();
//        t2.start();
        ThreadSample4_2 t3=new ThreadSample4_2();
        ThreadSample4_2 t4=new ThreadSample4_2();
        t3.setName("线程3");
        t4.setName("线程4");
        t3.start();
        t4.start();
    }
}


class ThreadSample4_1 implements Runnable{
    private int ticket=100;  // 使用实现Runnable接口的定义方式，可以将变量声明作为类的属性存在
    @Override
    public void run() {
        while(true){
            // 使用同步代码块来解决线程安全问题
            synchronized (this){
                if(ticket>0){
                    System.out.println(Thread.currentThread().getName()+"卖出了序号为"+ticket+"的票");
                    ticket--;
                }else{
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ThreadSample4_2 extends Thread{
    static int ticket=100;  // 使用继承Thread类的方式时，需要使用static关键字修饰该共享资源
    @Override
    // 对run方法直接进行synchronized修饰是不可取的，因为此时其同步锁默认为this,这个this指向的对象会变化，因此跟没同步一样
    public void run() {
        while(ticket>0){
            saleTicket();
        }
    }

    // 使用synchronized修饰的静态方法默认会使用 类名.class 作为其同步锁
    public synchronized static void saleTicket(){
        if(ticket>0){
            System.out.println(Thread.currentThread().getName()+"卖出了序号为"+ticket+"的票");
            ticket--;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class ThreadSample4_3 implements Runnable{
    private final ReentrantLock lock=new ReentrantLock();
    private int ticket=100;
    @Override
    public void run() {
        while (true) {
            try{
                lock.lock();
                if(ticket>0){
                    System.out.println(Thread.currentThread().getName()+"卖出了序号为"+ticket+"的票");
                    ticket--;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }finally {
                // 如果同步代码运行时出现异常，需要将lock.unlock()写入异常处理代码块的finally内
                lock.unlock();
            }
        }

    }
}