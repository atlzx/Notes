package com.springboot.example.springbootstarterrobot.controller;

import com.springboot.example.springbootstarterrobot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/robot")
public class RobotController {
    @Autowired
    RobotService robotService;

    @GetMapping("/hello")
    public String sayHello(){
        return robotService.sayHello();
    }
}
