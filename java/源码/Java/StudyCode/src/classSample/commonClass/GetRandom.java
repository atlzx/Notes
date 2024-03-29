package classSample.commonClass;

public class GetRandom {
    public static void main(String[] args) {
        // 得到[3,5]范围内的随机数
        int num = (int) (Math.random() * (5 - 3 + 1)) + 3;
        System.out.println(num);
    }
}
