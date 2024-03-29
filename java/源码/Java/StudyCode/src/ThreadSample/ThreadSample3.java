package ThreadSample;

public class ThreadSample3 {
    public static void main(String[] args) {
        // 继承Thread方法的匿名内部类调用
        new Thread(){
            public void run(){
                for (int i = 1; i <= 10000; i++) {
                    if(i%2==0){
                        System.out.println(Thread.currentThread().getName()+"输出:"+i);
                    }
                }
            }
        }.start();

        // 实现Runnable接口的匿名内部类调用

        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 10000; i++) {
                        if(i%2==0){
                            System.out.println(Thread.currentThread().getName()+"输出:"+i);
                        }
                    }
                }
            }
        ).start();

        for (int i = 1; i <=10000; i++) {
            if(i%2!=0){
                System.out.println("主线程输出:"+i);
            }
        }
    }
}
