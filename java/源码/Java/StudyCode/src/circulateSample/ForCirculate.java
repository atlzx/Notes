package circulateSample;

public class ForCirculate {
    public static void main(String[] args) {


        // 循环后处理表达式使用多个操作
        int number=1;
        for(System.out.print("a");number<3; System.out.print("c"),number++){
            System.out.print("b");
        }

        /* 无限循环
        for( ; ; ){
            System.out.println("输出");
        }
        */
    }
}
