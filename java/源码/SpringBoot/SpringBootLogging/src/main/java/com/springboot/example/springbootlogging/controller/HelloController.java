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
        log.trace("hello方法的trace日志");
        log.debug("hello方法的debug日志");
        log.info("hello方法的info日志");
        log.warn("hello方法的warn日志");
        log.error("hello方法的error日志");
        return "Hello World";
    }
}
