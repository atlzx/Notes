package com.example.cloud.controller;


import cn.hutool.core.util.IdUtil;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos")
@Slf4j
public class NacosController {
    @Value("${server.port}")
    private String port;
    @GetMapping("/get/info")
    public ReturnData<String> getNacosInfo(){
        return ReturnData.ok("nacos注册信息响应:"+IdUtil.simpleUUID()+"端口号:"+port);
    }
}
