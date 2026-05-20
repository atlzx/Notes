package com.springboot.example.springbootwebsocket.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.example.springbootwebsocket.utils.JsonUtil;
import com.springboot.example.springbootwebsocket.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Slf4j
public class CommonListener implements SpringApplicationRunListener {

    @Override
    public void ready(ConfigurableApplicationContext ioc, Duration timeTaken) {
        log.info("项目已就绪");
        ObjectMapper objectMapper = ioc.getBean(ObjectMapper.class);
        JsonUtil.objectMapper=objectMapper;
        RedisTemplate<String,Object> redisTemplate = (RedisTemplate<String,Object>)ioc.getBean("redisTemplate");
        RedisUtil.redisTemplate=redisTemplate;
    }


}
