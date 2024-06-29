package com.example.cloud.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pay/resilience")
@Slf4j
public class ResilienceController {
    @GetMapping("circuit/get/info/{id}")
    public ReturnData<String> getCircuitInfo(@PathVariable("id") Integer id) throws Exception {
        if(id<0){
            throw new Exception("id不能为负数");
        }
        else if(id>100){
            try{
                log.info("休眠开始时间:{}", DateUtil.now());
                TimeUnit.SECONDS.sleep(3);
                log.info("休眠结束时间:{}", DateUtil.now());
            }catch(Exception e){
                log.error("{}",e,e);
            }
        }
        return ReturnData.ok("circuit响应"+UUID.fastUUID());
    }

    @GetMapping("bulkhead/get/info/{id}")
    public ReturnData<String> getBulkheadInfo(@PathVariable("id") Integer id){
        try{
            log.info("休眠开始时间:{}", DateUtil.now());
            TimeUnit.SECONDS.sleep(5);
            log.info("休眠结束时间:{}", DateUtil.now());
        }catch(Exception e){
            log.error("{}",e,e);
        }
        return ReturnData.ok("bulkhead"+UUID.fastUUID());
    }


}
