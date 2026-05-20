package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String PAY_URL ;

    @GetMapping("/nacos/get/info")
    public ReturnData<String> getNacosInfo(){
        ReturnData<String> result = restTemplate.getForObject(PAY_URL + "/nacos/get/info", ReturnData.class);
        return result;
    }
}
