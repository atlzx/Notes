package com.example.cloud.apis;

import com.example.cloud.resp.ReturnData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("cloud-gateway")
public interface GatewayFeignApi {
    @GetMapping("/pay/gateway/get/info")
    ReturnData<String> getInfo();

    @GetMapping("/pay/get/filter/info")
    ReturnData<String> getFilterInfo(HttpServletRequest request);
}
