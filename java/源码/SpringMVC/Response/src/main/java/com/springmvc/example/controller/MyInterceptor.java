package com.springmvc.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class MyInterceptor implements HandlerInterceptor {
    @Override
    // 该方法在handler执行前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("在handler执行前执行");
        return true;
    }

    @Override
    // 该方法在handler执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("在handler执行之后执行");
    }

    @Override
    // 该方法在视图解析器解析完毕之后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ex.printStackTrace();
        System.out.println("在视图解析器解析完毕之后执行");
    }
}
