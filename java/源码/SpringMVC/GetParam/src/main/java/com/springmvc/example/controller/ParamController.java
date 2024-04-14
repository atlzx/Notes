package com.springmvc.example.controller;

import com.springmvc.example.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/param")
@ResponseBody
@Controller
public class ParamController {


    @RequestMapping(value = "/test1")
    public String getJSONParam(@RequestBody Person person){
        return person.toString();
    }

    @RequestMapping(value = "/test2")
    public String test(){
        return "aaa";
    }
}
