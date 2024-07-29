
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConcurrentCollectionMapTest {
    @Test
    // 测试ArrayList在多线程环境下出现的问题
    public void test1() throws InterruptedException {
        ArrayList<Integer> list=new ArrayList<>();
        Runnable run=()->{
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        };
        for(int j=1;j<=100;j++){
            new Thread(run).start();
        }
        TimeUnit.SECONDS.sleep(1);
        log.info("{}",list.size());
    }
    
    @Test
    // 测试CopyOnWriteArrayList
    public void test2() throws InterruptedException {
        CopyOnWriteArrayList<Integer> list=new CopyOnWriteArrayList<>();
        Runnable run=()->{
            // 这里把循环次数设置小一点，因为循环次数太多主线程休眠的1s内这堆线程执行不完操作导致输出的size比预期值要小
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
        };
        for(int j=1;j<=100;j++){
            new Thread(run).start();
        }
        TimeUnit.SECONDS.sleep(1);
        log.info("{}",list.size());
    }

    @Test
    // 测试HashMap在多线程环境下出现的问题
    public void test3() throws InterruptedException {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            int number = i;
            new Thread(() -> {
                for (int j = 0; j < 100; j++)
                    map.put(number * 1000 + j, "haha");
            }).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(map.size());
    }


    @Test
    // 测试ConcurrentHashMap
    public void test4() throws InterruptedException {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            int number = i;
            new Thread(() -> {
                for (int j = 0; j < 100; j++)
                    map.put(number * 1000 + j, "haha");
            }).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(map.size());
    }
}