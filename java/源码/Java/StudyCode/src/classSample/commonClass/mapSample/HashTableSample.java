package classSample.commonClass.mapSample;

import org.junit.Test;

import java.util.Hashtable;

public class HashTableSample {
    @Test
    public void test1(){
        // HashTable是最老的Map类，它是线程安全的，但是效率不高，也没什么人使
        Hashtable<Integer,Integer> table=new Hashtable<>();
        table.put(null,null);  // HashTable无法添加null:null的键值对，会报空指针异常
    }
}
