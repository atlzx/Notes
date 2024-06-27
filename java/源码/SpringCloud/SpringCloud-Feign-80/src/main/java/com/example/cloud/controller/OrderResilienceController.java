package com.example.cloud.controller;

import com.example.cloud.apis.PayFeignApi;
import com.example.cloud.resp.ReturnData;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/")
public class OrderResilienceController {
    @Resource
    private PayFeignApi payFeignApi;
    @GetMapping("pay/resilience/circuit/get/info/{id}")
    // 该注解用于让Resilience4j知道该Api调用的是什么服务
    @CircuitBreaker(name="cloud-pay-service",fallbackMethod = "defaultFallBack")
    public ReturnData<String> getCircuitInfo(@PathVariable("id") Integer id){
        return payFeignApi.getCircuitInfo(id);
    }

    public ReturnData<String> defaultFallBack(Integer id,Throwable t){
        return ReturnData.ok("CircuitFallback------>系统繁忙");
    }
}
