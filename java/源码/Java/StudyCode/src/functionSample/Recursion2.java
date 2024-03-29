package functionSample;

public class Recursion2 {
    // 给定如下代码，求递归的次数
    private static int count = 0;

    public static void main(String[] args) {
        Recursion2 recursion2=new Recursion2();
        recursion2.binomial();
    }

    public void binomial() {
        recursion(10);
    }


    public int recursion(int n) {
        count++;
        System.out.println("count:" + count + "n=" + n);  // 此处最后的count值即为递归次数
        if (n <= 0) {
            return 0;
        }
        return recursion(n - 1) + recursion(n - 2);  // 递归次数最终为287次，通过画树形图可以得知，递归次数的方程为f(n)=f(n-1)+f(n-2)+1
        // return recursion(n-1);  调用了11次
        // return recursion(n-1)+recursion(n-1); 方程为f(n)=2*f(n-1)+1  或理解为2^(n+1)-1
    }

}
