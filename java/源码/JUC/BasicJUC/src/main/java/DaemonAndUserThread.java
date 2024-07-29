import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;


public class DaemonAndUserThread {
//    private static final org.junit.platform.commons.logging.Logger log=LoggerFactory.getLogger(Logger.class);
    public static void main(String[] args) {
        // 创建一个线程，该线程负责测试JVM只要有一个用户线程在运行，进程就不会终止
        Thread testThread=new Thread(
            ()->{
                // 从IDEA的状态图标中可以看到，即使主线程已经停止运行了，当该线程是用户线程时，Java进程也会继续运行下去
                // 但是当该线程成为守护线程以后，主线程终止后，由于用户线程没有了，该线程的run方法就不会执行了
                System.out.println("进程名:"+Thread.currentThread().getName()+",是否为守护线程:"+Thread.currentThread().isDaemon());
                while(true){

                }
            },
            "testThread"
        );
        System.out.println("主线程名"+Thread.currentThread().getName());
        // 设置为守护线程
//        testThread.setDaemon(true);
        testThread.start();
    }
}
