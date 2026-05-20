package permissionmodifiers2;

import permissionmodifiers1.Sample1;

public class Sample3 extends Sample1 {
    public void show(){
        Sample1 sample1=new Sample1();
        // 在本包之外，外包的子类之中调用四个权限修饰的属性或方法，发现private、缺省、protected修饰的变量和方法均无法被调用
        // 因为这种调用方式和在包外的非子类中调用没有区别，都是仅调用Sample1的实例对象
        // System.out.println(sample1.test1);  该属性无法被调用，编译时会报错
        // System.out.println(sample1.test2);  该属性无法被调用，编译时会报错
        // System.out.println(sample1.test3);  该属性无法被调用，编译时会报错
        System.out.println(sample1.test4);
        // sample1.show1(); 该方法无法被调用，编译时会报错
        // sample1.show2(); 该方法无法被调用，编译时会报错
        // sample1.show3(); 该方法无法被调用，编译时会报错
        sample1.show4();
        // 使用在包外继承了Sample1的类的实例对象来调用，发现private和缺省修饰的变量和方法无法使用
        Sample3 sample3=new Sample3();
        // System.out.println(sample3.test1);  无法被调用，编译时会报错
        // System.out.println(sample3.test2);  无法被调用，编译时会报错
        System.out.println(sample3.test3);
        System.out.println(sample3.test4);
        // sample3.show1();  无法被调用，编译时会报错
        // sample3.show2();  无法被调用，编译时会报错
        sample3.show3();
        sample3.show4();
    }
}

/*
    缺省修饰的类无法在包外被调用
    class Sample4 extends Sample2 {

    }
*/

class Sample5{
    public void show(){
        // 在外包的非子类之中调用四个权限修饰的属性或方法，发现仅public修饰的变量和方法均无法被调用
        Sample1 sample1=new Sample1();
        // System.out.println(sample1.test1);  无法被调用，编译时会报错
        // System.out.println(sample1.test2);  无法被调用，编译时会报错
        // System.out.println(sample1.test3);  无法被调用，编译时会报错
        System.out.println(sample1.test4);
        // sample1.show1();  无法被调用，编译时会报错
        // sample1.show2();  无法被调用，编译时会报错
        // sample1.show3();  无法被调用，编译时会报错
        sample1.show4();
    }
}
