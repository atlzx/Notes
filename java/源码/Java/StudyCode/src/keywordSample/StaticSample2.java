package keywordSample;

public class StaticSample2 {
    public static void main(String[] args) {
        StaticSample3 sample3 = null;
        sample3.hello();  // 输出hello!
        System.out.println(sample3.count);  // 输出1
    }
}

class StaticSample3 {
    public static int count = 1;

    public static void hello() {
        System.out.println("hello!");
    }
}