package keywordSample;

public class FinalSample1 {
    public final static int NUMBER1=12;
    public final int NUMBER2;  // 如果放弃显式赋值，那么还可以通过构造器或代码块的方式来赋值
    public final int NUMBER3;
    public static int NUMBER4=10;
    public static String str;
    {
        NUMBER2=10;
    }
    {
        // NUMBER2=20; 一旦final修饰的变量被初始化，它就无法再次被赋值了
    }

    // 在静态代码块中进行赋值本身是没有问题的，但是如果在静态代码块里面的try-catch语句进行赋值，就会出现编译错误的问题
    // static{
    //     try{
    //         str="lzx";
    //     }catch(Exception e){
    //         str="lzx";
    //     }
    // }

    // 如果选择使用构造器来进行final修饰变量的赋值的话，必须保证对象创建时，每一个final修饰的变量都能得到赋值
    // 如果这个空参构造器没有给NUMBER3赋值的话，那么会导致编译错误，因为此时若使用空参构造器创建类的实例对象，那么NUMBER3便没有被初始化，这不符合final的要求
    public FinalSample1() {
        NUMBER3=13;  // 这是为了满足 对象创建时，每一个final修饰的变量都能得到赋值 的要求
    }

    


    public static void main(String[] args) {
        // FinalSample1.NUMBER1=10;  被final修饰的静态变量无法被修改，它此时已经相当于常量了
        FinalSample1.NUMBER4=20;  // 普通的静态变量对比样例
        FinalSample1 finalSample1=new FinalSample1();
        finalSample1.show1();
    }
    public FinalSample1(int number){
        NUMBER3=number;
    }

    // 一般方法不需要同时被final和static修饰，因为static修饰的方法本身就不能被重写
    public final void show1(){
        System.out.println("这是被final修饰的方法，它无法被重写");
    }
}

class FinalSample1_3 extends FinalSample1{
    /*
        继承了FinalSample1但是无法重写show1方法，因为show1方法被final修饰了
        public void show1(){

        }

        public final void show1(){

        }
    */
}

final class FinalSample1_1{ }


/*
    被final修饰的类无法被继承
    class FinalSample1_2 extends FinalSample1_1{

    }
*/


