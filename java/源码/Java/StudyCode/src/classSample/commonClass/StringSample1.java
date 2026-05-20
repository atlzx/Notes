package classSample.commonClass;

import org.junit.Test;

public class StringSample1 {
    @Test
    public void test1() {
        String s1 = "hello";  // 字符串可以使用赋值运算符直接赋值，此时变量直接指向字符串常量池内的字符串对象
        String s2 = "hello";

        String s3 = new String("hello");  // 也可以使用构造器赋值，但构造器创建会在常量池外新创建对象，该对象的value指向内部的
        String s4 = new String("hello");

        System.out.println(s1 == s2);//true，使用赋值运算符直接赋值，指向的都是字符串常量池内的字符串对象
        System.out.println(s1 == s3);//false，s3使用了构造器，使用new的都会在堆内创建新的对象
        System.out.println(s1 == s4);//false,同上
        System.out.println(s3 == s4);//false，两个变量都是new的，它们直接指向的对象各不相同

        System.out.println(s1.equals(s2));//true，比较的是它们指向的字符串值是否相等
    }

    @Test
    public void test2() {
        StringSample1_Person p1 = new StringSample1_Person();
        StringSample1_Person p2 = new StringSample1_Person();
        p1.name = "Tom";
        p2.name = "Tom";

        p1.name = "Jerry"; // 给字符串重新赋值，字符串常量池内的值不会变，改变的是该变量所指向的内存地址，由指向"Tom"改为"Jerry"

        System.out.println(p2.name);//Tom
    }

    @Test
    public void test3() {
        String s1 = "hello";
        String s2 = "world";

        String s3 = "helloworld";
        String s4 = "hello" + "world";  // 编译生成的字节码文件和s3的赋值语句是一样的，因为是两个常量相加，结果时确定的
        String s5 = s1 + "world"; //通过查看字节码文件发现调用了StringBuilder的toString()---> new String()
        String s6 = "hello" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false
        System.out.println(s3 == s6);//false
        System.out.println(s3 == s7);//false
        System.out.println(s5 == s6);//false
        System.out.println(s5 == s7);//false
        System.out.println();

        String s8 = s5.intern(); //intern():返回的是字符串常量池中字面量的地址
        System.out.println(s3 == s8);//true
    }

    @Test
    public void test4() {
        final String s1 = "hello";
        final String s2 = "world";

        String s3 = "helloworld";
        String s4 = "hello" + "world";
        String s5 = s1 + "world";
        String s6 = "hello" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s5);//true,使用final修饰的变量是不可变的，实际上也是常量，编译器会当作常量处理，下面同理
        System.out.println(s3 == s6);//true
        System.out.println(s3 == s7);//true
    }

    @Test
    public void test5() {
        String s1 = "hello";
        String s2 = "world";

        String s3 = s1.concat(s2);  // concat无论在什么情况都会new
        String s4 = "hello".concat("world");
        String s5 = s1.concat("world");

        System.out.println(s3 == s4);//false
        System.out.println(s3 == s5);//false
        System.out.println(s4 == s5);//false
    }
}


class StringSample1_Person {
    String name;
}