package extendtest;

public class Extends2 extends Extends1{
    String str;

    public static void main(String[] args) {
        Extends2 extends2=new Extends2();
        // 继承使得子类可以调用父类的方法和属性，但会受到权限修饰符的限制
        extends2.test();
        System.out.println(extends2.name);
        System.out.println(extends2.number);
    }
}
