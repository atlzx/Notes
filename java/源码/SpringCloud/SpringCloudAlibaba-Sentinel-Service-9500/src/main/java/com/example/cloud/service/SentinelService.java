package com.example.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentinelService {
    @SentinelResource(value = "common")
    public String test1(){
        return "链路响应";
    }
}
