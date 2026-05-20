import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class Test {
    public static Integer value=10000;
    public static void main(String[] args) {
        Runnable runnable=()->{
            while(value>0){
                log.info("当前线程名:{},减之前的值:{}",Thread.currentThread().getName(),value);
                value--;
                log.info("当前线程名:{},减之后的值:{}",Thread.currentThread().getName(),value);

            }
        };
        new Thread(runnable,"thread1").start();
        new Thread(runnable,"thread2").start();
//        new Thread(runnable,"thread3").start();
//        Dog dog=new Dog();
//        new Thread(
//            dog::decrease,
//            "a"
//        ).start();
//        new Thread(
//            dog::decrease,
//            "b"
//        ).start();
    }
}


@Data
@Slf4j
class Dog {
    private Integer value=10000;
    public void decrease(){
        while(value>0){
            this.value--;
            System.out.println("当前线程名:"+Thread.currentThread().getName()+" ,当前值为:"+this.value);
        }

    }
}