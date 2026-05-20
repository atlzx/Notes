package operatorSample;

public class Operator2 {
    public static void main(String[] args) {
        int i1=10;
        int i2=20;
        System.out.println(i1==i2);  // 输出false
        System.out.println(i1=i2);  // 此处会先将i2的值赋值给i1,然后输出i1的值
    }
}
