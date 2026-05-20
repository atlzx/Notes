package operatorSample;

public class Operator1 {
    public static void main(String[] args) {
        byte m=1;
        int n = m++ + ++m; // 自增运算符计算得到的结果不会改变变量的数据类型，但如果将n的取值数据类型换成byte，依旧会报错，因为该加法是两个byte类型在做，它是运算的特殊情况，会返回int值
        System.out.println("得到的结果是:"+n);
        m=1;
        m=m++;
        /*
            该代码的实际执行流程是:
            1. 将当前m的值(为1)取出，临时存放在一个内存区域内
            2. 对m执行+1操作，并赋值给m，此时m的值为2
            3. 将临时存放的值赋值给m，m的值变为1
            也就是说，该代码的执行使m被赋值了两次，最终还是得到的是1
         */
        System.out.println("结果为:"+m);


        /*
            相对的，先加后用的流程是:
            1. m+1，此时m的值为2
            2. 将当前m的值赋值给m，m的值为2
            自减运算同理
        */
        m=++m;
        System.out.println(m);

        byte a=1;
        a+=1;  // 使用该运算符时，运算时不会改变数据的类型
        // a=a+1;  该段代码会报错，因为1字面量是int型的数据，运算会发生自动类型提升，但接收时依旧使用byte类型接收，导致编译错误
    }
}
