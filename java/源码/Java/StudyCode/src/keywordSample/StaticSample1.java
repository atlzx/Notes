package keywordSample;

public class StaticSample1 {

    public static void main(String[] args) {
        // 在不创建对象的情况下就可以使用类中的静态方法和属性，说明静态成员是随着类的加载而被创建的
        System.out.println(StaticSample1_1.count);  // 直接使用类名就可以调用静态变量
        StaticSample1_1.show2();  // 直接使用类名调用静态方法
        StaticSample1_2.show2();
        StaticSample1_2.show3();
        System.out.println("继承了含有静态变量的子类读取的父类的静态变量:"+StaticSample1_2.count);
    }

}

class StaticSample1_1{
    static int count=10;
    String name="aaa";
    public void show1(){
        // 非静态方法内可以调用静态方法，也能调用非静态方法
        show2();
        show4();
        System.out.println(count);
        System.out.println(name);
    }

    public static void show2(){
        /*
            静态方法内不能调用其它非静态方法和变量
            show1();
            System.out.println(name);
        */
        // 静态方法内可以调用静态方法
        show3();
        System.out.println(count);
    }

    public static void show3(){
        System.out.println("这是静态方法show3");
    }

    public void show4(){
        System.out.println("这是非静态方法show4");
    }
}


class StaticSample1_2 extends StaticSample1_1{

}