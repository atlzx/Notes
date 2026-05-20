package com.springboot.example.springbootmessageconverter.controller;

import com.springboot.example.springbootmessageconverter.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller")
public class PersonController {

    @GetMapping
    public Person getPerson(){
        Person person = new Person();
        person.setName("lzx");
        person.setAge(20);
        person.setDesc("hahaha");
        return person;
    }
}
