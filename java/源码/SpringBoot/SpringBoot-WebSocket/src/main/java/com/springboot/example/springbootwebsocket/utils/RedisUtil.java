package com.springboot.example.springbootwebsocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component
public class RedisUtil {
    public static final Integer HASH=0;
    public static final Integer String=1;
    // 这个玩意也可以直接用StringRedisTemplate类，因为它是RedisTemplate的子类，功能更丰富，也支持中文序列化。缺点是操作对象只能是字符串类型
    public static RedisTemplate<String,Object> redisTemplate;
    public static void update(String key, Object value) throws JsonProcessingException {
        Map map = JsonUtil.entityToMap(value);
//        log.info(map.toString());
        HashOperations<String, String, Map> operations = redisTemplate.opsForHash();
        operations.put(key,CommonUtil.getClassName(value.getClass(),true),map);
    }

    public static void update(String key,String mapKey,Object value){
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        log.info("{},{},{}",key,mapKey,value.toString());
        operations.put(key,mapKey,value);
    }

    public static Object getValue(String key,Integer mode,String ...other){
        switch(mode){
            case 0:return redisTemplate.opsForHash().get(key,other[0]);
            case 1:return redisTemplate.opsForValue().get(key);
            default:return null;
        }
    }
}
