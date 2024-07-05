package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.cloud.apis.SentinelPayFeignApi;
import com.example.cloud.resp.ReturnData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feign/order")
public class FeignOrderController {
    @Resource
    private SentinelPayFeignApi sentinelPayFeignApi;
    @GetMapping("/pay/get/info")
    @SentinelResource(value = "feign-get-info",blockHandler = "defaultBlockHandler")
    public ReturnData<String> getInfo(){
        return sentinelPayFeignApi.getInfo();
    }

    public ReturnData<String> defaultBlockHandler(BlockException e){
        return ReturnData.fail(HttpStatus.TOO_MANY_REQUESTS.value());
    }
}
