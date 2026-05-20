package circulateSample;

public class WhileCirculate {
    public static void main(String[] args) {
        // 记录0~100的偶数的个数以及它们相加的和
        int number = 1;
        int sum=0;
        int count=0;
        while (number <= 100) {
            if(number%2==0){
                count++;
                sum+=number;
            }
            number++;
        }
        System.out.println("偶数的个数为" + count + ",这些偶数的和为" + sum);
    }
}
