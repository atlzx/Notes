package com.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error",produces = "application/json;charset=utf-8")
public class ExceptionClassSample {
    @RequestMapping("/error1")
    public String error1(){
        int a=1/0;
        return "aaa";
    }

    @RequestMapping("/error2")
    public String error2(){
        String str=null;
        int index = str.indexOf("a");
        return ""+index;
    }

    public void error3(){
        throw new RuntimeException("出现错误");
    }


}
