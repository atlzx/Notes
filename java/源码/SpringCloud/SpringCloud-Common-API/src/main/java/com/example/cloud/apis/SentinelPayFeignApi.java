package com.example.cloud.apis;

import com.example.cloud.apis.fallback.SentinelPayFeignApiFallback;
import com.example.cloud.resp.ReturnData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "nacos-pay-service",fallback = SentinelPayFeignApiFallback.class)
public interface SentinelPayFeignApi {
    @GetMapping("pay/get/info")
    ReturnData<String> getInfo();
}
