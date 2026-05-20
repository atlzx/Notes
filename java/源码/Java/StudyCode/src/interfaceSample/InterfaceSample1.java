package interfaceSample;

public interface InterfaceSample1 {
    int NUMBER=10;  // 接口内仅能声明public final static修饰的成员变量，该前缀可以省略。它需要直接使用接口名进行调用

    void show1();  // 接口内可以声明public abstract修饰的抽象方法，该前缀可以省略

    // 接口内可以声明具有方法体的默认方法(JDK8 新增)，它可以不用被重写，如果不重写，调用时会直接执行该方法体内的代码
    public default void show2(){
        System.out.println("这是接口内的默认方法");
        show3();
    }
    // 接口内可以声明具有方法体的私有方法(JDK 9新增)，它不能被重写，仅能在接口内供默认方法和静态方法调用。
    private void show3(){
        System.out.println("接口内的私有方法执行了");
    }
    // 接口内可以声明具有方法体的静态方法(JDK8 新增)，它不能被重写，需要直接使用接口名进行调用
    public static void show4(){
        System.out.println("这是接口内的静态方法");
    }

    public default void show2(String str){
        System.out.println("重载的方法输出指定的字符串:"+str);
    }

}
