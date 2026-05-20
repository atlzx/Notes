package com.example.redis.customer;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

@Component
public class DistributedLockFactory {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    private String serviceId;
    private Map<String, Lock> lockMap;

    public DistributedLockFactory() {
        this.serviceId= IdUtil.simpleUUID();
        this.lockMap=new ConcurrentHashMap<>();
    }

    public Lock getRedisDistributedLock(String lockName){
        if(lockMap.get(lockName)==null){
            lockMap.put(lockName,new RedisDistributedLock(redisTemplate,serviceId,lockName));
        }
        return lockMap.get(lockName);
    }
    public Lock getRedisDistributedLock(String lockName,long expireTime){
        if(lockMap.get(lockName)==null){
            lockMap.put(lockName,new RedisDistributedLock(redisTemplate,serviceId,lockName,expireTime));
        }
        return lockMap.get(lockName);
    }
}
