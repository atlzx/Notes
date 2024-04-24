package com.springboot.example.springbootthymeleaf.controller;

import jakarta.servlet.ServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/th")
public class ThymeleafController {
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name, Model model){
        model.addAttribute("message",name);
        return "hello";
    }
}
