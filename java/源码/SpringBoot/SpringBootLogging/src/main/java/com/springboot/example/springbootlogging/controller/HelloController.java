package com.springboot.example.springbootlogging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String hello(){
        log.info("请求方法执行");
        return "Hello World";
    }
}
