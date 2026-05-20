package com.springboot.example.springbootlogging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goodbye")
@Slf4j
public class GoodbyeController {
    @GetMapping
    public String goodbye(){
        for (int i = 0; i < 1000; i++) {
            log.trace("goodbye方法的trace日志");
            log.debug("goodbye方法的debug日志");
            log.info("goodbye方法的info日志");
            log.warn("goodbye方法的warn日志");
            log.error("goodbye方法的error日志");
        }

        return "Goodbye World";
    }
}
