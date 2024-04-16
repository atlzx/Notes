package com.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jsp")
public class JSPSample {

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","index data");
        return "index";
    }
}
