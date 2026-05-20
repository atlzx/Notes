package polymorphism;

class Base {
    int count = 10;
    public void display() {
        System.out.println(this.count);
    }
}

class Sub extends Base {
    int count = 20;
    public void display() {
        System.out.println(this.count);
    }
}


public class PolymorphismExer1 {
    public static void main(String[] args){
        Sub s = new Sub();
        System.out.println(s.count);//20
        s.display();//20
        Base b = s;
        System.out.println(b == s); //因为是引用数据类型，赋值的是内存地址，比较时也比较内存地址，因此为true
        System.out.println(b.count);//b的声明类型为父类，因此读取到的是父类的属性，为10
        b.display();//20

        Base b1 = new Base();
        System.out.println(b1.count); //10
        b1.display();//10
    }
}
