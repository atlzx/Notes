package keywordSample;

public class ThisAndSuperExer1 {

    public static void main(String[] args) {
        // 判断如下代码的输出结果
        new A(new B());
        // 如果B类不继承A类，那么输出顺序为 keyword_sample.B、keyword_sample.A、AB
        // 如果B类继承A类，那么输出顺序为A、keyword_sample.B、keyword_sample.A、AB，这是因为B的构造器被调用时会默认先调用super()方法执行A的无参构造器
    }
}

class A {
    public A() {
        System.out.println("keyword_sample.A");
    }

    public A(B b) {
        this();
        System.out.println("AB");
    }
}

//class keyword_sample.B {
//    public keyword_sample.B() {
//        System.out.println("keyword_sample.B");
//    }
//}

class B extends A{
    public B() {
        System.out.println("keyword_sample.B");
    }
}
