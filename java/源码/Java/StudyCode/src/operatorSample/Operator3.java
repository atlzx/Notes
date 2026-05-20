package operatorSample;

public class Operator3 {
    public static void main(String[] args) {
        boolean b1,b2;
        b1=b2=true;
        int a,b;
        a=b=20;
        b1 = (a++ % 3 == 0) && (++a % 7 == 0);  // 短路与在确定前面是false后，直接返回false,不再执行后续判断
        b2 = (++b % 3 == 0) & (b++ % 7 == 0);
        System.out.println("b1 = "+ b1 + ", a = "+ a);
        System.out.println("b2 = "+ b2 +", b = "+ b);
    }
}
