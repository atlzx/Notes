package com.springboot.example.springbootwebsocket.interceptor;


import com.springboot.example.springbootwebsocket.utils.CommonConstantUtil;
import com.springboot.example.springbootwebsocket.utils.JwtUtil;
import com.springboot.example.springbootwebsocket.utils.SpringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;


@Slf4j
@Data
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method=request.getMethod();
        String jwt=request.getHeader("Authorization");
        log.info("拦截器执行了,请求方式:{}",method);
        if("OPTIONS".equals(method)){
            return true;
        }

        Map<String, Object> parseMap = JwtUtil.parseJwt(jwt, SpringUtil.getBean(CommonConstantUtil.class).getJwtKey());
        if (parseMap != null) {
            request.setAttribute(SpringUtil.getBean(CommonConstantUtil.class).getJwtParseName(), parseMap);
            log.info(request.getAttribute(SpringUtil.getBean(CommonConstantUtil.class).getJwtParseName()).toString());
            return true;
        } else {
            return false;
        }
    }
}
