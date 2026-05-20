package abstractSample;

public class AbstractSample1 {
    public static void main(String[] args) {
        AbstractSample1_3 abstractSample1_3=new AbstractSample1_3();
        System.out.println(abstractSample1_3.getCount());  // 输出2
    }
}

abstract class AbstractSample1_1{
    // 抽象类中可以声明非抽象属性和方法，但抽象方法必须写在抽象类内
    int count=1;
    static String name="lzx";
    // abstract int age=12;  abstract不能修饰变量
    abstract public void show1(); // 抽象方法没有方法体

    public int getCount(){
        return count;
    }
    /*
        abstract关键字不能修饰构造器
        public abstract AbstractSample1_1(){

        }
    */
}

abstract class AbstractSample1_2 extends AbstractSample1_1{
    abstract public void show1(); // 抽象类继承抽象类，还能重写父类的抽象方法，我也跟着抽象起来了

    public int getCount(){
        return 2;
    }
    // private abstract void show2(); abstract不能修饰私有方法
    // public abstract static void show3();  abstract不能修饰static方法

}

class AbstractSample1_3 extends AbstractSample1_2{
    @Override
    public void show1() {
        System.out.println("这是子类复写的父类的抽象方法");
    }
}