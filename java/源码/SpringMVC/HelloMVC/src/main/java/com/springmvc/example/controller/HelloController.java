package com.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping("springMvc/hello")  // 该注解用来进行请求路径的映射匹配
    @ResponseBody  // 该注解表示直接将方法结果返回给前端
    public String hello(){
        System.out.println("Hello World");
        return "Hello World";  // 直接返回字符串
    }

}
