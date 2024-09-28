package com.springboot.example.springboottest.service;


import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
public class C implements Common{

    @Override
    public void say() {
        System.out.println("C");
    }

    @PostConstruct
    public void init(){
        System.out.println("C");
    }
}
