package WrapperSample;

public class WrapperSample2 {
    public static void main(String[] args) {

        Integer i = new Integer(1);
        Integer j = new Integer(1);
        System.out.println(i == j);//false

        //底层都会调用Integer的valueOf()
        // 出现下面不同结果的原因是进行自动装箱时，如果是-128~127的数，都会从缓存的Integer类型的数组内取出对应的值赋给对应变量
        // 而因为Integer是引用数据类型，取出的实际上是内存地址，因此赋的内存地址是相同的，所以第一次输出true
        // 又因为第二次赋值超过了这个区间，因此要新创建Integer对象，所以内存地址不同输出false
        Integer m = 1; //自动的装箱
        Integer n = 1;
        System.out.println(m == n);//true

        Integer x = 128;
        Integer y = 128;
        System.out.println(x == y);//false

    }

}