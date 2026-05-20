package classSample.commonClass.collectionSample;

import org.junit.Test;

import java.util.TreeSet;

public class TreeSetSample {
    @Test
    public void test1(){
        // TreeSet内的所有元素都必须是同一个类型的
        // 指定Comparator对象后，加入子类的实例对象是允许的，但不指定时，该操作不能被允许
        // 指定Comparator对象可以使set内的对象有序排列，使遍历时它们的顺序是一致的，同时TreeSet依据Comparator对象进行重复性判断
        TreeSet set=new TreeSet<>(
            (A o1,A o2)->{
                return o1.value-o2.value;
            }
        );
        set.add(new A(1));
        set.add(new B(2));
        set.add(new A(-1));
        for(Object i:set){
            System.out.println(((A)i).value);
        }
    }
}


class A{
    int value;

    public A(int value) {
        this.value = value;
    }
}

class B extends A{

    public B(int newValue) {
        super(newValue);
    }
}