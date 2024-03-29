package functionSample;

public class Recursion1 {
    public static void main(String[] args) {
        // 已知一个数列：f(20) = 1,f(21) = 4,f(n+2) = 2*f(n+1)+f(n)，其中n是大于0的整数，求f(10)的值。
        System.out.println("f(10)的值为:"+solution1(10));

        // f(0) = 1，f(1) = 4，f(n+2)=2*f(n+1) + f(n)，其中n是大于0的整数，求f(10)的值
        // 这道题需要在递归时控制n向小的方向走，因此需要转换一下提供的表达式，使其变为
        // f(n)=2*f(n-1) + f(n-2)

        System.out.println("f(10)的值为:"+solution2(10));
    }

    public static int solution1(int n){
        // 直接根据题意写式子
        if(n==20){
            return 1;
        }else if(n==21){
            return 4;
        }else{
            return solution1(n+2)-2*solution1(n+1);
        }
    }

    public static int solution2(int n){
        if(n==0){
            return 1;
        }else if(n==1){
            return 4;
        }else{
            return 2*solution1(n-1)+solution1(n-2);
        }
    }
}
