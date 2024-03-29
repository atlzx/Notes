package classSample.commonClass;

import org.junit.Test;

import java.util.*;

public class CollectionsSample {
    @Test
    public void test1(){
        // 下面演示列表的反转和乱序
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        Collections.shuffle(list);  // shuffle可以使当前列表乱序，并不会返回新的列表
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        Collections.reverse(list);  // reverse方法可以使列表反转，并不会返回新的列表
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
    }

    @Test
    public void test2(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        Collections.sort(list);  // sort方法默认按照Comparable接口指定的排序方式升序排序
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        Collections.shuffle(list);
        Collections.sort(
            list,
            (o1,o2)->{
                return o2-o1;
            }
        );  // 传入Comparator来指定比较顺序，这里演示按照降序排序
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
    }

    @Test
    public void test3(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        Integer max = Collections.max(list);  // 根据Comparable接口的排序依据来找到最大值
        System.out.println(max.intValue());
        Integer min=Collections.min(list); // 根据Comparable接口的排序依据来找到最小值
        System.out.println(min.intValue());
        Integer max2=Collections.max(
            list,
            (o1,o2)->{
                return o2-o1;
            }
        );  // 也可以通过传入一个Comparator对象经过比较找到符合比较规则的最大值
        System.out.println(max2.intValue());
        // min方法也可以传入同样的Comparator方法，这里不再举例

    }

    @Test
    public void test4(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        for(Integer i:list){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        Collections.sort(list);
        int index = Collections.binarySearch(list, 1);  // 指定要查找的list和想查找的元素，使用该方法的前提是list有序
        System.out.println(index);
        Collections.shuffle(list);
        // 这个Comparator对象不知道干嘛的
        int index2=Collections.binarySearch(
            list,
            1,
            (o1,o2)->{
                return o2-o1;
            }
        );
        System.out.println(index2);
    }

    @Test
    public void test5(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        Collections.swap(list,1,2);  // swap方法可以使list中的两个对应下标的数据交换位置
        int frequency = Collections.frequency(list, 1);  // frequency方法可以统计集合内的对应元素出现的频率
        System.out.println(frequency);
        Collections.replaceAll(list,2,199);  // replaceAll方法可以将list内所有对应值(第二个参数)都替换为新值(第三个参数)
        List<Integer> newList=Arrays.asList(new Integer[list.size()]);
        Collections.copy(newList,list);  // 第一个参数是新复制的对象，第二个参数才是复制源
        for(Integer i:newList){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        Collections.addAll(list,1,2,3,4,5,6);  // addAll方法用于添加不定数量的参数
        for(Integer i:list){
            System.out.println(i);
        }
    }

    @Test
    public void test6(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Random random=new Random();
            list.add(random.nextInt(10));
        }
        Collection<Integer> col1 = Collections.synchronizedCollection(list);  // synchronizedXxx方法可以获得线程安全的指定对象
        List<Integer> list2 = Collections.unmodifiableList(list);  // unmodifiableXxx方法可以获得只读的指定对象
    }
}
