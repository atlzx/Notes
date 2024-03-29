package classSample.commonClass.collectionSample;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LinkedListSample {
    public static void main(String[] args) {

        // LinkedList底层使用链表存储数据，它的插入和删除很快，但是查询和添加较慢
        LinkedList<Integer> list=new LinkedList<>();  // 创建一个空的LinkedList对象
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(2);
        LinkedList<Integer> list2=new LinkedList<>(set);  // 创建一个带有set内全部元素集合的对象
        list2.addFirst(0);  // 在链表头插入新数据
        list2.addLast(3);  // 在链表尾部添加新数据
        System.out.println(list2.getFirst());  // 得到链表头的数据
        System.out.println(list2.getLast());  // 得到链表尾的数据
        list2.removeFirst();  // 移除链表头的数据
        list2.removeLast();  // 移除链表尾的数据
        for(Integer i:list2){
            System.out.println(i);
        }
    }
}
