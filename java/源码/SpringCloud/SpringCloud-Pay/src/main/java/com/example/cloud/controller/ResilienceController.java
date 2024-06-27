package com.example.cloud.controller;

import cn.hutool.core.lang.UUID;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/resilience")
@Slf4j
public class ResilienceController {
    @GetMapping("circuit/get/info/{id}")

    public ReturnData<String> getCircuitInfo(@PathVariable("id") Integer id) throws Exception {
        if(id<0){
            throw new Exception("id不能为负数");
        }
        return ReturnData.ok(UUID.fastUUID().toString());
    }


}
