package com.example.cloud.controller;

import com.example.cloud.apis.PayFeignApi;
import com.example.cloud.resp.ReturnData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderMicrometerController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/pay/micrometer/get/info")
    public ReturnData<String> getMicrometerInfo(){
        return payFeignApi.getMicrometerInfo();
    }
}
