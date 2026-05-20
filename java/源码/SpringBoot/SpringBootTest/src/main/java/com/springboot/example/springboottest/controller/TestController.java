package com.springboot.example.springboottest.controller;

import com.springboot.example.springboottest.aop.Custom;
import com.springboot.example.springboottest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Custom
public class TestController {
    @Autowired
    private TestService testService;



    @GetMapping("/haha")
    public void haha(){
        testService.test();
    }
}
