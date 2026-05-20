package com.springboot.example.redis.controller;

import com.springboot.example.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Resource
    private RedisService redisService;
    @GetMapping("set/{key}/{value}")
    public String setData(@PathVariable String key,@PathVariable String value){
        return redisService.setData(key,value);
    }

    @GetMapping("/get/{key}")
    public String getData(@PathVariable String key){
        return redisService.getData(key);
    }
}
