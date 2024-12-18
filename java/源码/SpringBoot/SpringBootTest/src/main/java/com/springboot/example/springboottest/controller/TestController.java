package com.springboot.example.springboottest.controller;

import com.springboot.example.springboottest.common.A;
import com.springboot.example.springboottest.common.B;
import com.springboot.example.springboottest.common.anno.TestAnnotation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@TestAnnotation
public class TestController {
    @Resource
    private A a;
    @Resource
    private B b;
    @GetMapping("/test")
    public String hello(){
        b.caonima();
        return "hello";
    }
    @GetMapping("/caonima")
    public String caonima(){
        return "caonima";
    }
}
