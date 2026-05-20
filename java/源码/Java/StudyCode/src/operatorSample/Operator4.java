package operatorSample;

public class Operator4 {
    public static void main(String[] args) {
        // 样例1:交换两个变量之间的值

        int m = 10;
        int n = 20;
        System.out.println("m = " + m + " n = " + n);
        // 方法一:使用中间变量进行交换
        // 优点:简单，适用性强
        // 缺点:需要占用额外的内存空间来使用一个中间变量
        int temp;
        temp = m;
        m = n;
        n = temp;
        System.out.println("m = " + m + " n = " + n);


        m = 10;
        n = 20;
        // 方法二:使用数学性质进行交换
        // 优点:不需要使用中间变量，节省了内存空间
        // 缺点:没有适用性，仅能作用于数值类型
        m = m + n;
        n = m - n;
        m = m - n;
        System.out.println("m = " + m + " n = " + n);


        m = 10;
        n = 20;

        // 方法三：使用位运算进行交换,该方法需要用到异或的一个性质: m^n^m=n
        // 优点:不需要使用中间变量，节省了内存空间
        // 缺点:没有适用性，仅能作用于数值类型
        m = m ^ n;
        n = m ^ n;
        m = m ^ n;
        System.out.println("m = " + m + " n = " + n);
    }
}
