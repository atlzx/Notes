package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/pay/micrometer")
public class MicrometerController {
    @GetMapping("/get/info")
    public ReturnData<String> getMicrometerInfo(){
        return ReturnData.ok("Micrometer响应");
    }
}
