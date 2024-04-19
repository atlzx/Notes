package com.springboot.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class HelloSpringBootController {
    @GetMapping
    public String helloSpringBoot(){
        return "Hello SpringBoot3";
    }
}
