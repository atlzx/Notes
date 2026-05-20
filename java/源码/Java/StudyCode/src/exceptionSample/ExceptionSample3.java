package exceptionSample;

public class ExceptionSample3 {
    public static void main(String[] args) {
        ExceptionSample3_1 exceptionSample3_1=new ExceptionSample3_1();
        exceptionSample3_1.show1();
    }
}

// 自定义异常类型如下
class MyException extends RuntimeException{
    static final long serialVersionUID = -3187516999948L;  // 指定唯一标识

    // 提供两个构造器
    public MyException(String message) {
        super(message);
    }

    public MyException() {

    }
}

class ExceptionSample3_1{
    public void show1(){
        throw new MyException("异常测试");  // throw关键字需要跟随Throwable或其子类的实例对象
        // System.out.println("aaaa");  throw后面的代码不会执行
    }
}
