package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/empower")
@RestController
public class EmpowerController {
    @GetMapping("/get/info")
    @SentinelResource(value = "empower")
    public ReturnData<String> getInfo(@RequestParam("data") String data){
        return ReturnData.ok(data);
    }
}
