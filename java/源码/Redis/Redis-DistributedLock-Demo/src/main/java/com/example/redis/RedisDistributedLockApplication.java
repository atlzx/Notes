package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@SpringBootApplication
@Slf4j
public class RedisDistributedLockApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(RedisDistributedLockApplication.class);
        log.info("{}",ioc.getBean(LettuceConnectionFactory.class));
    }
}
