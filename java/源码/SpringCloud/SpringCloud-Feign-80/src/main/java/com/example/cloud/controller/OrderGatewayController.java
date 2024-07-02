package com.example.cloud.controller;

import com.example.cloud.apis.GatewayFeignApi;
import com.example.cloud.resp.ReturnData;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderGatewayController {
    @Resource
    private GatewayFeignApi gatewayFeignApi;

    @GetMapping("/pay/gateway/get/info")
    public ReturnData<String> getGatewayInfo(){
        return gatewayFeignApi.getInfo();
    }
}
