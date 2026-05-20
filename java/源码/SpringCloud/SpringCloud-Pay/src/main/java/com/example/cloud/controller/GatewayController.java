package com.example.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.example.cloud.resp.ReturnData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/gateway")
@Slf4j
public class GatewayController {
    @GetMapping("/get/info")
    public ReturnData<String> getInfo(){
        return ReturnData.ok("网关响应:"+ IdUtil.simpleUUID());
    }

    @GetMapping("/get/filter/info")
    public ReturnData<String> getFilterInfo(HttpServletRequest request){
        log.info("{}", request.getHeader("X-Request-id1"));
        log.info("{}", request.getHeader("X-Request-id2"));
        log.info("{}", request.getHeader("sec-fetch-site"));
        log.info("{}", request.getHeader("sec-fetch-mode"));
        request.getParameterMap().forEach(
            (key,value)->{
                log.info("{}:{}",key,value);  // 得到请求URL参数
            }
        );
        return ReturnData.ok("网关响应过滤器信息:"+IdUtil.simpleUUID());
    }

    @GetMapping("/abc/def/{info}")
    public ReturnData<String> getFilterInfo2(@PathVariable("info") String info){
        return ReturnData.ok(info);
    }


}
