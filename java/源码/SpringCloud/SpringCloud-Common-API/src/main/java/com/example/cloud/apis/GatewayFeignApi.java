package com.example.cloud.apis;

import com.example.cloud.resp.ReturnData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("cloud-gateway")
public interface GatewayFeignApi {
    @GetMapping("/pay/gateway/get/info")
    ReturnData<String> getInfo();
}
