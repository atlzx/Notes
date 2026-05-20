package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/hot")
public class HotDataController {
    @GetMapping("/test1")
    @SentinelResource(value = "hotData",blockHandler = "defaultBlockHandler")
    public ReturnData<String> getInfo(@RequestParam(value = "data1",required = false) String data1, @RequestParam(value = "data2",required = false) String data2){
        return ReturnData.ok("数据:"+data1+data2);
    }

    public ReturnData<String> defaultBlockHandler(String data1,String data2,BlockException e){
        return ReturnData.fail(HttpStatus.BAD_REQUEST.value());
    }
}
