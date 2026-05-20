package WrapperSample;

public class WrapperSample3 {
    public static void main(String[] args) {
        // 判断下面情况的输出结果
        //题目1：
        int i = 10;
        double d = 10.2;
        System.out.println(i == d);//false
        //题目2：
        Integer i1 = 10;
        Double d1 = 10.2;
//		System.out.println(i1 == d1); //编译报错，因为二者没有任何关联

        //题目3：
        Integer m = 1000;
        double n = 1000;
        System.out.println(m == n);//true,在进行这种运算时包装类对象会拆箱，拆箱后运算时发生自动类型提升，得到true

        //题目4：
        Integer x = 1000;
        int y = 1000;
        System.out.println(x == y);//true，同上，但不会发生自动类型提升
    }
}