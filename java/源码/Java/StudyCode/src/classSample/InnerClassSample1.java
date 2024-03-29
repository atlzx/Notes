package classSample;

public class InnerClassSample1 {
    public static void main(String[] args) {
        InnerClassSample1_1 innerClassSample1_1=new InnerClassSample1_1();
        // 非静态内部类需要使用外部类对象创建
        InnerClassSample1_1.InnerClassSample1_1_1 innerClassSample1_1_1=innerClassSample1_1.new InnerClassSample1_1_1("lz");
        innerClassSample1_1_1.show2("l");
        // 静态内部类对象直接new创建
        InnerClassSample1_1.InnerClassSample1_1_2 innerClassSample1_1_2=new InnerClassSample1_1.InnerClassSample1_1_2();

    }
}

class InnerClassSample1_1{
    String name="lzx";
    int count=10;
    // 内部类可以用final关键字修饰，且可以继承和实现接口，其继承和实现接口不受父类影响
    final class InnerClassSample1_1_1 extends InnerClassSample1_1_2{
        String name="lz";

        {
            System.out.println("内部类代码块输出"+name);
        }

        // 内部类可以定义构造器
        public InnerClassSample1_1_1(String name){
            this.name=name;
        }

        public void show2(String name){

            System.out.println("输出方法形参:"+name);  // 读取方法的形参
            System.out.println("输出内部类name属性:"+this.name);  // 使用this关键字调用自己类中的name属性
            System.out.println("输出内部类父类的name属性:"+super.name);
            System.out.println("输出外部类name属性:"+InnerClassSample1_1.this.name);  // 存在同名属性，需要指定更确切的路径来读取name属性
            System.out.println("输出外部类count属性:"+count);  // 此处省略了 InnerClassSample1_1_1.this.
        }
    }


    // 内部类可以被abstract和static关键字修饰，为了演示静态内部类的创建，此处并未写abstract，但是确实能用
     static class InnerClassSample1_1_2{
        String name="llllzzzxxx";
        public void show2(){
            // System.out.println(count); 静态内部类无法读取外部类的非静态变量
        }
    }

    public void show1(){
        System.out.println("外部内的方法1");
    }

    public void show2(){
        System.out.println("外部类的方法2");
    }
}