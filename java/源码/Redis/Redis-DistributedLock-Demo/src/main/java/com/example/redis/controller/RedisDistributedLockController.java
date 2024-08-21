package com.example.redis.controller;

import com.example.redis.customer.DistributedLockFactory;
import com.example.redis.customer.RedisDistributedLock;
import com.example.redis.service.RedLockService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;

@RestController
@Slf4j
@RequestMapping("/lock")
public class RedisDistributedLockController {
    @Resource
    private RedisDistributedLock redisDistributedLock;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private DistributedLockFactory lockFactory;
    @Resource
    private RedLockService redLockService;
    @GetMapping("/consume")
    public void consume(){
//        log.info("{}","方法执行");
//        executeConsume(redisDistributedLock);
        redisDistributedLock.lock();
        try{
            Object count = redisTemplate.opsForValue().get("goods");
            if(count!=null&&Long.parseLong(count.toString())>=1){
                redisTemplate.opsForValue().increment("goods",-1);
                log.info("还剩{}个货物",Long.parseLong(count.toString())-1);
//                log.info("执行操作的线程:{}",Thread.currentThread().getName());
            }
        }finally {
            redisDistributedLock.unlock();
        }
    }

    @GetMapping("/factory/consume")
    public void factoryConsume(){
        Lock lock = lockFactory.getRedisDistributedLock("redisLock");
        lock.lock();
        try{
            Object count = redisTemplate.opsForValue().get("goods");
            if(count!=null&&Long.parseLong(count.toString())>=1){
                redisTemplate.opsForValue().increment("goods",-1);
                log.info("还剩{}个货物",Long.parseLong(count.toString())-1);
//                log.info("执行操作的线程:{}",Thread.currentThread().getName());
            }
        }finally {
            lock.unlock();
        }

    }

    // 这个是小规模模拟并发情况下的消费执行情况
    private void executeConsume(Lock lock){
        Runnable run=()->{
            while(true){
                lock.lock();
                try{
                    Object count = redisTemplate.opsForValue().get("goods");
                    if(count!=null&&Long.parseLong(count.toString())>=1){
                        redisTemplate.opsForValue().increment("goods",-1);
                        log.info("还剩{}个货物",Long.parseLong(count.toString())-1);
                        log.info("执行操作的线程:{}",Thread.currentThread().getName());
                    }else{
                        break;
                    }
                }finally {
                    lock.unlock();
                }
            }
        };
        for(int i=1;i<=10;i++){
            new Thread(run,"Thread"+i).start();
        }
    }

    @GetMapping("/redLock/consume")
    public void redLockConsume(){
        redLockService.redLockConsume();
    }
}
