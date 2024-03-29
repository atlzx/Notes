package ThreadSample;

public class ThreadSample1 {
    public static void main(String[] args) {
        Thread t1=new ThreadSample1_1();
        t1.start();
        // 让主线程也输出1~10000的奇数
        for (int i = 1; i <=10000; i++) {
            if(i%2!=0){
                System.out.println("主线程输出:"+i);
            }
        }
    }
}


class ThreadSample1_1 extends Thread{
    @Override
    public void run() {
        // 打印1~10000的偶数
        for (int i = 1; i <= 10000; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+"输出:"+i);
            }
        }
    }
}
