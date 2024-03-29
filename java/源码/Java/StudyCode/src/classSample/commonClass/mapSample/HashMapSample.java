package classSample.commonClass.mapSample;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapSample {
    @Test
    public void test1(){
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,1);  // map通过put方法添加数据
        map.put(null,null);  // HashMap可以添加null:null的键值对
        System.out.println(map.containsKey(1));;  // containsKey方法可以判断是否存在某一个键
        System.out.println(map.containsValue(1));  // containsValue方法可以判断是否存在某一个值
        map.remove(1);  // remove方法用于移除键为某个值的键值对
        map.remove(1,1);  // remove方法还可以移除更精确的键值对
        map.clear();  // 清空map
        map.put(2,1);
        map.get(2);  // 得到键对应的指，没有会返回null
        System.out.println(map.size());  // 输出map目前存储的元素数量
        map.put(3,2);
        System.out.println("--------------------------------------------");
        // keySet方法用来返回键组成的Set集合
        for (Integer i : map.keySet()) {
            System.out.println(i);
        }
        System.out.println("--------------------------------------------");
        // values方法用来返回值组成的List集合
        for (Integer value : map.values()) {
            System.out.println(value);
        }
        System.out.println("--------------------------------------------");
        // entrySet方法可以得到map的键值对组成的Set
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());  // entry.getKey得到key
            System.out.println(entry.getValue());  // entry.getValue得到value
        }
        // forEach也可以进行操作
        map.forEach(
            (a,b)->{
                System.out.println(a);
                System.out.println(b);
            }
        );
    }
    @Test
    public void test2(){
    }
}
