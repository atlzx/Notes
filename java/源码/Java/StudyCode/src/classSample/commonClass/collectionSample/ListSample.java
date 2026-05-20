package classSample.commonClass.collectionSample;

import java.util.ArrayList;
import java.util.List;

public class ListSample {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(0,1);  // add方法可以通过在第一个参数内传一个index来指定元素的插入下标，插入后该元素对应的下标即为index
        list.add(1,2);
        list.add(2,3);
        list.add(3,4);
        list.add(4,5);
//        list.add(10,6);  使用该方法时需要确保索引合法
        System.out.println(list.get(1));  // get方法通过传入下标来得到对应下标的指
        // subList方法可以对list进行切片，切出[index1,index2)区间内的list
        for(Integer i:list.subList(2,5)){
            System.out.println(i);
        }
        System.out.println("-----------------------------------");
        List<Integer> list2=new ArrayList<>();
        list2.add(2);
        list2.add(2);
        list2.add(2);
        list.addAll(1,list2);  // addAll方法可以指定一个下标，来使导入的Collection对象插入到指定下标下，它是将每个元素添加而不是将整体添加
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("-----------------------------------");
        System.out.println(list.indexOf(10));  // 找到指定元素第一次出现的位置，找不到返回-1
        System.out.println(list.lastIndexOf(10));  // 找到指定元素最后一次出现的位置，找不到返回-1
        list.remove(1);  // 移除指定下标为1的属性，传入的参数可能会与Collection定义的remove方法接收的参数相冲突，此时根据就近原则选择
        list.set(2,10);
        for(Integer i:list){
            System.out.println(i);
        }
    }
}
