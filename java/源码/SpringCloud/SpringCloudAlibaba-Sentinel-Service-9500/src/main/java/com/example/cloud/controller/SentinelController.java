package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import com.example.cloud.service.SentinelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/sentinel")
public class SentinelController {
    @Resource
    private SentinelService sentinelService;
    // 用于进行限流-单体的测试
    @GetMapping("/test1")
    public ReturnData<String> test1(){
        return ReturnData.ok("当前时间:"+System.currentTimeMillis());
    }

    // 用于进行限流-关联的测试
    @GetMapping("/test2")
    public ReturnData<String> test2(){
        return ReturnData.ok("test2响应");
    }


    @GetMapping("/test3")
    public ReturnData<String> test3(){
        return ReturnData.ok("test3响应");
    }
    @GetMapping("/test4")
    public ReturnData<String> test4(){
        return ReturnData.ok("test4响应");
    }
    // 用于进行限流-链路的测试
    @GetMapping("/test5")
    public ReturnData<String> test5(){
        return ReturnData.ok(sentinelService.test1());
    }
    @GetMapping("/test6")
    public ReturnData<String> test6(){
        return ReturnData.ok(sentinelService.test1());
    }

    @GetMapping("/test7")
    public ReturnData<String> test7(){
        return ReturnData.ok("test7响应");
    }


}
