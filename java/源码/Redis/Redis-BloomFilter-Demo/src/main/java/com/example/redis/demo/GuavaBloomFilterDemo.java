package com.example.redis.demo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GuavaBloomFilterDemo {
    @Test
    // 测试Guava的布隆过滤器

    public void test1(){
        // 统计过滤器出错的次数
        int count=0;
        // 调用静态方法创建一个布隆过滤器对象
        BloomFilter<Integer> filter=BloomFilter.create(
            Funnels.integerFunnel(),
            100
        );
        // 向过滤器添加100条数据，从0到99
        for(int i=0;i<100;i++){
            filter.put(i);
        }
        // 测试不存在于过滤器中的100000条数据
        for(int i=100;i<100100;i++){
            // 如果过滤器返回true，那么说明判断出现了错误
            if(filter.mightContain(i)){
                log.info("第{}个出现判断失误",i-99);
                count++;
            }
        }
        log.info("总计判断失误次数:{}",count);
    }

}
