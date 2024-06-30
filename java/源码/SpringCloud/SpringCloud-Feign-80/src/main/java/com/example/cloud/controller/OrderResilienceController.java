package com.example.cloud.controller;

import com.example.cloud.apis.PayFeignApi;
import com.example.cloud.resp.ReturnData;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order/")
@Slf4j
public class OrderResilienceController {
    @Resource
    private PayFeignApi payFeignApi;
    @GetMapping("pay/resilience/circuit/get/info/{id}")
    // 该注解用于让Resilience4j知道该Api调用的是什么服务
    @CircuitBreaker(name="cloud-pay-service",fallbackMethod = "defaultFallBack")
    public ReturnData<String> getCircuitInfo(@PathVariable("id") Integer id){
        return payFeignApi.getCircuitInfo(id);
    }

    @GetMapping("pay/resilience/bulkhead/get/info/{id}")
    @Bulkhead(name="cloud-pay-service",fallbackMethod = "defaultFallBack",type = Bulkhead.Type.SEMAPHORE)
    public ReturnData<String> getBulkheadInfo(@PathVariable("id") Integer id){
        log.info(Thread.currentThread().getName());
        return payFeignApi.getBulkheadInfo(id);
//        return CompletableFuture.supplyAsync(() -> payFeignApi.getBulkheadInfo(id) + "\t" + " Bulkhead.Type.THREADPOOL");
    }



    public ReturnData<String> defaultFallBack(Integer id,Throwable t){
        return ReturnData.ok("Resilience4j:Fallback------>系统繁忙");
    }

    public CompletableFuture<String> threadPoolFallBack(Integer id,Throwable t){
        return CompletableFuture.supplyAsync(() -> "Bulkhead.Type.THREADPOOL，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
    }


}
