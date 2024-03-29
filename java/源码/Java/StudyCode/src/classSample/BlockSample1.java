package classSample;

public class BlockSample1 {
    static int test1;
    int test2;

    static{
        System.out.println("静态代码块执行");
        test1=1;
        show1();
        /*
            静态代码块内部无法调用非静态方法和变量
            test2=2;
            show2();
        */
    }

    {
        System.out.println("非静态代码块执行");
        test2=2;
        show2();
    }

    public BlockSample1(){

    }

    public static void main(String[] args) {
        BlockSample1 blockSample1=new BlockSample1();
    }

    public static void show1(){
        System.out.println("静态方法执行");
    }

    public void show2(){
        System.out.println("普通方法执行");
    }
}
