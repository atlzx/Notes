package com.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/fusing")
public class FusingController {
    @GetMapping("/test1")
    // 测试慢调用熔断
    public ReturnData<String> getInfo1(){
        try{
            log.info("休眠开始时间:{}", DateUtil.now());
            TimeUnit.SECONDS.sleep(1);  // 单位是秒
            log.info("休眠结束时间:{}", DateUtil.now());
        }catch(Exception e){
            log.error("{}",e,e);
        }
        return ReturnData.ok("test1响应");
    }
    @GetMapping("/test2")
    // 测试异常比例熔断
    public ReturnData<String> getInfo2(){
        int a=1/0;
        return ReturnData.ok("test1响应");
    }
}
