package com.springboot.example.springbootwebsocket;

import com.springboot.example.springbootwebsocket.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedisTest {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void test1(){
    }
}
