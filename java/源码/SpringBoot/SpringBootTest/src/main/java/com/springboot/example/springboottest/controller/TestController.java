package com.springboot.example.springboottest.controller;

import com.springboot.example.springboottest.aop.Custom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Custom
public class TestController {
    @GetMapping("/haha")
    public void haha(){

    }
}
