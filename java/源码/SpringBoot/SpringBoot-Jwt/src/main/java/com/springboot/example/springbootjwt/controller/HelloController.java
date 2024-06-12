package com.springboot.example.springbootjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@CrossOrigin
@Slf4j
public class HelloController {
    @RequestMapping
    public String hello(){
        return "Hello World";
    }
}
