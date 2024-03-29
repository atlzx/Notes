package classSample.commonClass.collectionSample;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class HashSetSample {
    @Test
    public void test1(){
        // HashSet底层通过HashMap实现，因此它通过比较各元素的哈希值和调用equals方法判断是否相等
        // set大部分都是使用Collection接口定义的方法，此处列举一些
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(1);
        System.out.println(set.size());
        System.out.println(set.contains(1));
        set.remove(1);
        System.out.println(set.isEmpty());
    }
    @Test
    public void test2(){
        Set<Integer> set=new HashSet<>();
        Integer[] arr=new Integer[set.size()];
        set.add(1);
        set.add(2);
        // toArray方法可以转换为Object[]类型的数组对象
        for(Object i:set.toArray()){
            System.out.println(i);
        }
        // 通过传入一个数组对象，来得到数组对象
        // 作为参数的数组对象并不会承载任何的数据，toArray方法返回的新数组对象才有数据
        for(Integer i:set.toArray(arr)){
            System.out.println(i);
        }
    }
}
