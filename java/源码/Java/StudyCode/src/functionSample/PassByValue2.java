package functionSample;

public class PassByValue2 {
    public static void main(String[] args) {
        int a=10;
        int b=10;
        method(a,b);  // 需要在method方法被调用之后，使得程序输出的结果为 “a=100,b=200”，请写出method方法的代码
        System.out.println("a="+a);
        System.out.println("b="+b);
    }

    // 方法一：在方法内强制输出对应结果，并直接使程序结束，阻断主方法继续向下执行
    public static void method(int a,int b){
        a=100;
        b=200;
        System.out.println("a="+a);
        System.out.println("b="+b);
        System.exit(0);
    }



    /*  方法二:允许主方法在调用完成后继续执行，但需要在method方法内重写System.out.println方法，使其输出题目要求的结果
        public static void method(int a,int b){
            PrintStream ps = new PrintStream(System.out) {
                @Override
                public void println(String x) {
                    if ("a=10".equals(x)) {
                        x = "a=100";
                    } else if ("b=10".equals(x)) {
                        x = "b=200";
                    }
                    super.println(x);
                }
            };
            System.setOut(ps);
        }

    */
}
