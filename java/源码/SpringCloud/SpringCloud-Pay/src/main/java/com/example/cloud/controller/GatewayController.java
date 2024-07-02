package com.example.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/gateway")
@Slf4j
public class GatewayController {
    @GetMapping("/get/info")
    public ReturnData<String> getInfo(){
        return ReturnData.ok("网关响应:"+ IdUtil.simpleUUID());
    }
}
