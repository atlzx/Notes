package classSample.commonClass.collectionSample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayListSample {
    public static void main(String[] args) {
        // 创建一个空的ArrayList对象，在JDK7靠后的版本以及之后，其底层数组仅创建一个长度为0的数组，直到第一次add时才正式初始化数组长度为10的数组
        // 在JDK7早期版本及之前，创建时底层数组会直接初始化一个长度为10的数组
        List<Integer> list=new ArrayList<>();
        List<Integer> list2=new ArrayList<>(10);  // 可以指定初始化数组的默认长度
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(2);
        List<Integer> list3=new ArrayList<>(set);  // 可以传递一个Collection接口对象来创建ArrayList对象，传入参数的所有元素都会被加入该新的对象内
        for(Integer i:list3){
            System.out.println(i);
        }
    }
}
