package com.springboot.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@Slf4j
public class RedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(RedisApplication.class, args);
        RedisTemplate redisTemplate = (RedisTemplate) ioc.getBean("redisTemplate");
        log.info(redisTemplate.getValueSerializer().getClass().toString());
    }

}
