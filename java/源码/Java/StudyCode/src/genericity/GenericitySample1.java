package genericity;

import java.util.Comparator;

public class GenericitySample1 {
    public static void main(String[] args) {
        // 下面是判断泛型约束条件是否成立的样例
        Object test1=new Object();
//        Test2.test(test1);  // 解除注释后可以看到是成立的
        // 下面是实现了Comparable接口的类(String)和继承了Comparable接口的子接口对象分别作为参数来调用test1方法
        // 该情况证明了通配符使用extends表示的是子接口对象或实现了Comparable接口的类对象
        String str="aaa";
        Test4 test4=new Test5();
        Test2.test1(str);
    }
}


interface Father{

}

interface Son extends Father{

}

class Test1 implements Father{

}

class Test3{

}

class Test2{
    public static <T extends Father> void test(T a){
        System.out.println(a.getClass());
    }

    public static <T extends Comparable> void test1(T a){
        System.out.println(a.getClass());
    }

    // 此处使用通配符进行方法多样化接收参数类型并进行限制
    // 通配符不能作为泛型方法的泛型标识存在、不能作为类和接口的泛型约束存在、不能作为属性的泛型类型存在
    public static void test2(Comparable<? extends Father> a, Comparator<? super Father> b){
        System.out.println(a.getClass());
    }
}

interface Test4 extends Comparable{

}

class Test5 implements Test4{
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}