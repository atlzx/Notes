package com.springmvc.example.controller;

import com.springmvc.example.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/json")
@RestController
public class ReturnJSON {
    @RequestMapping("getJSON")
    public Person returnJSON(@RequestBody Person person){
        return person;
    }
    @RequestMapping("getList")
    public List<Integer> getList(){
        return new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }
}
