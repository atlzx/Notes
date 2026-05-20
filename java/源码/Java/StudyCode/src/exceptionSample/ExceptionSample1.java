package exceptionSample;

public class ExceptionSample1 {
    public static void main(String[] args) {
        show1();
        show2();
        show3();
    }

    public static void show1(){
        try{
            int a=1;
            System.out.println(a/0);  // 这会导致运行时异常
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public static void show2(){
        int a=1;
        System.out.println(a/0);  // // 会报错，但是可以不处理，编译时也可以通过。但如果是编译时异常，则必须处理
    }

    // 可以在方法后通过throws关键字继续向上抛出异常
    public static void show3() throws RuntimeException{
        int a=1;
        System.out.println(a/0);  // 这会导致运行时异常
    }
}
