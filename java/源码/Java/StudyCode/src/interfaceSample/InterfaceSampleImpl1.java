package interfaceSample;


public class InterfaceSampleImpl1{
    public static void main(String[] args) {
        InterfaceSampleImpl1_1 interfaceSampleImpl1_1=new InterfaceSampleImpl1_1();
        InterfaceSample1 interfaceSample1=new InterfaceSampleImpl1_1();
        InterfaceSample1_3 interfaceSample1_3=new InterfaceSample1_3();
        InterfaceSample1_5 interfaceSample1_5=new InterfaceSample1_5();
        interfaceSampleImpl1_1.show2();
        System.out.println("******************************************************************************************");
        interfaceSample1_3.show2();
        interfaceSample1_5.show2();  // 调用父类的方法，而不是接口内的默认方法
        interfaceSample1_5.show5();
        /*
            无法通过对象直接调用接口内的静态方法和静态常量
            interfaceSampleImpl1_1.NUMBER;
            interfaceSample1.NUMBER;
            interfaceSampleImpl1_1.show4();
            interfaceSample1.show4();
        */
    }
}

// 实现了接口的类必须实现它们的抽象方法并对其中的具有明显冲突的方法进行重写
 class InterfaceSampleImpl1_1 implements InterfaceSample1,InterfaceSample2,InterfaceSample3{

     @Override
     public void show1() {

     }

     @Override
     public void show2() {
         // 如果我想调用被重写了的实现的接口内的默认方法，需要使用 接口名.super.方法(参数) 的形式来进行调用，如下所示
         InterfaceSample1.super.show2();
         InterfaceSample2.super.show1();
         InterfaceSample3.super.show2();
         // 如果我想调用对应接口内的静态方法，需要使用 接口名.方法(参数) 的形式来进行调用，如下所示
         InterfaceSample1.show4();
         System.out.println("获取接口内的静态常量:"+InterfaceSample1.NUMBER);  // 获取接口内的静态常量，直接使用接口名来调用
     }

     @Override
     public void show2(String str) {
         InterfaceSample1.super.show2(str);
     }
 }


 class InterfaceSampleImpl1_2 implements InterfaceSample4{

     @Override
     public void show1() {
         System.out.println(NUMBER);  // 由于InterfaceSample4继承了前三个接口，就可以通过InterfaceSample4直接访问InterfaceSample1的静态常量,接口名可以忽略不写
         // InterfaceSample4.show4();  接口的静态方法似乎无法被继承
     }

     // InterfaceSample4在类实现接口前就已经处理了三个接口之间的方法冲突问题，因此，下面的show2()方法是对InterfaceSample4接口内默认方法的重写
     // 删掉它们也不会报错，因为默认方法可以不写
     @Override
     public void show2() {
         InterfaceSample4.super.show2();
     }

     @Override
     public void show2(String str) {
         InterfaceSample4.super.show2(str);
     }
 }

 class InterfaceSample1_3 implements InterfaceSample5{

     @Override
     public void show1() {

     }
 }

 class InterfaceSample1_4{
    static int NUMBER=30;
    public void show1(){
        System.out.println("父类的方法1");
    }
    public void show2(){
        System.out.println("父类的方法2");
    }
 }

 // 如果一个类的父类和其实现的接口内的默认方法存在冲突，父类的方法会优先被调用，这被称为 类优先原则
// 由于默认调用父类的方法，因此不需要再实现接口内的抽象方法了，因为父类已经有该方法了
 class InterfaceSample1_5 extends InterfaceSample1_4 implements InterfaceSample1,InterfaceSample5{
    public void show5(){
        // System.out.println(NUMBER); 当继承的类和接口内存在多个同名变量时，像调用指定的变量需要更明确的调用方式
        System.out.println(InterfaceSample5.NUMBER);
        System.out.println(InterfaceSample1.NUMBER);
        System.out.println(InterfaceSample1_4.NUMBER);
    }
 }
