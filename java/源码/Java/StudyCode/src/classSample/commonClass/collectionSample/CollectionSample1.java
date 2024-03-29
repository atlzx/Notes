package classSample.commonClass.collectionSample;

import org.junit.Test;

import java.util.*;

public class CollectionSample1 {
    @Test
    public void test1(){
        Collection list=new ArrayList();
        Collection list1=new ArrayList();
        System.out.println(list1.isEmpty());  // 判断是否为空
        list.add(1);
        System.out.println(list.size());
        list1.add("bbb");
        list1.add(new int[]{1,2,3,4});
        list.addAll(list1);  // 将list1内的全部元素依次添加进本容器内
        list.add(list1);  // 这里比对一下两个操作的结果
        System.out.println(list.toString());
        list.remove(new int[]{1,2,3,4});  // 这样删除不成功，因为数组对象并没有重写equals方法
        list.remove(new String("bbb"));  // String类重写了equals方法，因此可以移除
        System.out.println(list.toString());
        list.retainAll(list1);  // 取交集
        System.out.println(list.toString());
        list.add(1);
        System.out.println(list.contains(Integer.valueOf(1)));  // Integer类也重写了equals方法，因此返回true
        System.out.println(list.containsAll(list1));  // 由于之前取了交集，因此返回false
        System.out.println(list.toArray());  // 转换为数组然后输出
        System.out.println(list.hashCode());
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }
    }
    @Test
    public void test2(){
        // asList方法支持使用两种方式转换为Collection对象
        String[] arr = new String[]{"AA","BB","CC"};
        Collection list = Arrays.asList(arr);
        System.out.println(list);

        List list1 = Arrays.asList("AA", "BB", "CC", "DD");
        System.out.println(list1);
    }
}
