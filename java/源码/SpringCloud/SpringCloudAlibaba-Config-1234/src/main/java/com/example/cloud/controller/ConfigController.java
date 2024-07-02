package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/config")
@RefreshScope
public class ConfigController {
    @Value("${config.info}")
    private String info;

    @GetMapping("/get/config/info")
    public ReturnData<String> getConfigInfo(){
        return ReturnData.ok(info);
    }
}
