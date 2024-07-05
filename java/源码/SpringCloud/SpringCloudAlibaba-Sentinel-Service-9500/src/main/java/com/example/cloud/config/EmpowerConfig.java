package com.example.cloud.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpowerConfig implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        // 返回想要进行匹配的请求参数的值，使其与Sentinel上配置的黑白名单的值进行匹配
        return httpServletRequest.getParameter("data");
    }
}
