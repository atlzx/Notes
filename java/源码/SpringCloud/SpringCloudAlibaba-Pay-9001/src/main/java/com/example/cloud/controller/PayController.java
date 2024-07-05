package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @GetMapping("/get/info")
    public ReturnData<String> getInfo(){
        int a=1/0;
        return ReturnData.ok("feign-sentinel整合响应");
    }
}
