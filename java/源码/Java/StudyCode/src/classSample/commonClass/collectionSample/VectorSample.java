package classSample.commonClass.collectionSample;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class VectorSample {
    public static void main(String[] args) {
        // Vector是Java中最老的集合类之一，它在JDK1.0的时候就存在。它是线程安全的，但是效率较慢
        Vector<Integer> vector=new Vector<>();
        Vector<Integer> vector2=new Vector<>(10);  // 指定底层数组在初始化时的初始长度
        Vector<Integer> vector3=new Vector<>(10,20);  // 指定底层数组在初始化时的初始长度和每次数组扩容时的增量
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(2);
        Vector<Integer> vector4=new Vector<>(set);  // 支持传入Collection对象
        vector.addElement(1);  // 与add方法作用一致
        vector.insertElementAt(2,1);  // 与ArrayList的add方法作用一致，但是参数的顺序相反
        vector.setElementAt(3,1);  // 与ArrayList的set方法作用一致，但是参数的顺序相反
        vector.removeElement(1);  // 删除找到的第一个元素
        vector.removeElementAt(0);  // 删除下标为0的元素，与ArrayList的remove方法作用一致
        vector.removeAllElements();  // 删除全部的元素，与clear方法作用一致
    }
}
