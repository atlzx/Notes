package com.springboot.example.springbootthymeleaf.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MyService {

    public String useRequest(){
        // 得到的对象是RequestAttributes，需要强转成ServletRequestAttributes
        // 如果不强转，无法调用其getRequest和getResponse方法
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute("aaa","test");
        return "aaa";
    }
}
