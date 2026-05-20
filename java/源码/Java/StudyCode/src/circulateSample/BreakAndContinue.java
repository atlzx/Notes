package circulateSample;

public class BreakAndContinue {
    public static void main(String[] args) {
        // 可以通过给for循环设置标签的形式来直接使内层循环的break跳出外层循环
        label:for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.println("循环执行");
                if (j % 4 == 0) {
//                    break label;  break支持使用标签进行跳过
                    System.out.println("跳过本次循环，本次循环时j=" + j);
                    continue label;  // continue支持使用标签进行跳过
                }
            }
        }
    }
}
