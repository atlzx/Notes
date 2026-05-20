package classSample;

public class InnerClassSample2 {
    public static void main(String[] args) {
        // 局部内部类样例1:有名称的局部内部类和对象
        class InnerClassSample2_1{
            public void show(){
                System.out.println("第一个内部类中的方法");
            }
        }
        InnerClassSample2_1 innerClassSample2_1_1=new InnerClassSample2_1();
        innerClassSample2_1_1.show();
        // 局部内部类样例2:匿名的内部类对象
        InnerClassSample2_1 innerClassSample2_1_2=new InnerClassSample2_1(){
            public void show1(){
                System.out.println("匿名内部类方法调用");
            }
        };
        // innerClassSample2_1_2.show1();  此处想调用show1方法是调不了的，因为创建的对象是内部类的子类对象，而接收该对象却声明的是父类类型，满足多态性，根据多态性的特点，该对象无法调用子类对象独有的方法
        // 下面是对 创建的对象是内部类的子对象 的证明:
        System.out.println("该对象的父类是:"+innerClassSample2_1_2.getClass().getSuperclass());
        // 如果想调用匿名内部类子类对象内独有的方法，可以在创建后直接调用该方法，无须使用变量接收
        new InnerClassSample2_1(){
            public void show1(){
                System.out.println("匿名内部类方法调用");
            }
        }.show1();

    }
}

class InnerClassSample2_2_1{

}