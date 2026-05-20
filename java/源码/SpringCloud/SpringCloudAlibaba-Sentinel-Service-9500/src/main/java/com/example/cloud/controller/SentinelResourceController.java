package com.example.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.cloud.resp.ReturnData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sr")
public class SentinelResourceController {
    @GetMapping("/test1")
    @SentinelResource(value = "haha",blockHandler = "defaultBlockHandler",fallback = "defaultFallBack")
    public ReturnData<String> getInfo1(){
        int a=1/0;
        return ReturnData.ok("SentinelResource注解测试响应");
    }

    // blockHandler和fallback方法都需要与原方法有相同的返回类型和参数，同时加上自己接收的异常类型
    public ReturnData<String> defaultBlockHandler(BlockException e){
        return ReturnData.ok("熔断....");
    }

    public ReturnData<String> defaultFallBack(Throwable e){
        return ReturnData.fail(HttpStatus.BAD_REQUEST.value());
    }
}
