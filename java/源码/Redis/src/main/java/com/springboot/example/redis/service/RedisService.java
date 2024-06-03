package com.springboot.example.redis.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    public String setData(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        log.info(key);
        return getData(key);
    }

    public String getData(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }
}
