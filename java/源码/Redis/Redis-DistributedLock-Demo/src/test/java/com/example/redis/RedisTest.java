package com.example.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    // 测试redisTemplate是否能够正常使用
    public void test1(){
        redisTemplate.opsForValue().set("hello","redis");
    }

    @Test
    // 测试高并发下自定义的分布式锁的情况
    public void test2(){

    }
}
