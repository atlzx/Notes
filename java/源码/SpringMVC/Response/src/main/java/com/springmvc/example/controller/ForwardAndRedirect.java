package com.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/fr")
public class ForwardAndRedirect {
    @GetMapping("forward")
    public String forward(){
        return "forward:/jsp/index";
    }

    @GetMapping("redirect")
    public String redirect(){
        return "redirect:/jsp/index";
    }

}
