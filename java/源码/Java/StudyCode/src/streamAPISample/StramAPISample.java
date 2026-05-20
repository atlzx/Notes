package streamAPISample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class StramAPISample {
    @Test
    public void test1(){
        ArrayList<Integer> list1=new ArrayList<>();
        for(int i=1;i<=10;i++){
            Random random=new Random();
            list1.add(random.nextInt(10));
        }
        for(Integer i:list1){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        // filter可以进行过滤，只有回调函数返回true时才不会被过滤
        list1.stream().filter(
            (item)->{
                return item>5;
            }
            // distinct可以进行去重，仅保留第一次出现的值
            // limit通过指定数值来限制流内元素的数量，如果当前流数量超过了指定的数值，会从后往前裁剪直到满足限制条件
            // skip用来将流前面的指定数量的元素舍弃，也就是跳过指定数量的元素
            // forEach属于终止操作，流一旦终止，便无法再调用中间操作
        ).distinct().limit(2).skip(1).forEach(
            (item)->{
                System.out.println(item);
            }
        );
    }

    @Test
    public void test2(){
        ArrayList<Integer> list1=new ArrayList<>();
        for(int i=1;i<=10;i++){
            Random random=new Random();
            list1.add(random.nextInt(10));
        }
        for(Integer i:list1){
            System.out.println(i);
        }
        System.out.println("------------------------------------");
        // map用来对所有的元素统一进行操作，并返回操作后的值
        Optional<Integer> max = list1.stream().map(
            (item) -> {
                return item + 3;
            }
            // sorted方法可以语句Comparable对象指定的排序方式进行排序
            // 也可以传入Comparator对象来手动指定排序方式
        ).sorted().sorted(
            (i1, i2) -> {
                return i2 - i1;
            }
        ).max(
            (i1, i2) -> {
                return i1 - i2;
            }
        );  // max是终止操作，可以传入一个Comparator接口对象来进行指定排序方式来找到符合排序方式的最大值，该方法返回一个Optional对象
        // 同样的还有min，这里不再举例
        System.out.println(max.get());  // 调用Optional对象的get方法来得到值
    }

    @Test
    public void test3(){
        ArrayList<Integer> list1=new ArrayList<>();
        for(int i=1;i<=10;i++){
            Random random=new Random();
            list1.add(random.nextInt(10));
        }
        // 下面演示的方法全都是终止方法
        System.out.println(list1.stream().count());  // count方法用来统计当前流内的元素数量，并返回long类型的
        System.out.println(list1.stream().findAny().get());  // findAny方法随机返回一个流内的元素
        System.out.println(list1.stream().findFirst().get());  // findFirst方法返回流内的最后一个元素
        boolean b1=list1.stream().allMatch(
            (item)->{
                return item>5;
            }
        );  // allMatch方法用来判断流内的元素是否全都符合一个规范
        boolean b2=list1.stream().anyMatch(
            (item)->{
                return item>5;
            }
        );  // antMatch方法用来判断流内的元素是否至少有一个符合规范
        boolean b3=list1.stream().noneMatch(
            (item)->{
                return item>5;
            }
        );  // noneMatch方法用来判断流内的元素是否全都不符合一个规范
        System.out.println(b1);
        System.out.println(b2);
    }


}
