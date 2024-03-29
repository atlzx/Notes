package permissionmodifiers1;

public class Sample1 {
    private int test1;
    int test2;
    protected int test3;
    public int test4;

    private int show1(){
        return 1;
    }

    int show2(){
        return 1;
    }

    protected int show3(){
        return 1;
    }

    public int show4(){
        return 1;
    }

    public void show(){
        // 在本类中调用四个权限修饰符修饰的类和属性，发现全部都能调用
        System.out.println(test1);
        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);
        show1();
        show2();
        show3();
        show4();
    }
}

class Sample2 extends Sample1{


    public static void main(String[] args) {
        Sample2 sample2=new Sample2();
        sample2.show();
    }
    public void show(){
        Sample1 sample1=new Sample1();
        Sample2 sample2=new Sample2();
        // 在本类之外，本包之中调用四个权限修饰的属性或方法，发现private修饰的变量和方法无法被调用
        // System.out.println(sample1.test1);  该属性无法被调用，编译时会报错
        System.out.println(sample1.test2);
        System.out.println(sample1.test3);
        System.out.println(sample1.test4);
        //sample1.show1();  该方法无法被调用，编译时会报错
        sample1.show2();
        sample1.show3();
        sample1.show4();
        // 即使使用继承了Sample1类的对象来调用。结果也是一样的
        // System.out.println(sample2.test1);  该属性无法被调用，编译时会报错
        System.out.println(sample2.test2);
        System.out.println(sample2.test3);
        System.out.println(sample2.test4);
        // sample2.show1();  该方法无法被调用，编译时会报错
        sample2.show2();
        sample2.show3();
        sample2.show4();
    }
}

/*
    使用protected和private修饰类会报错
    protected class Sample3{

    }

    private class Sample4{

    }

*/


