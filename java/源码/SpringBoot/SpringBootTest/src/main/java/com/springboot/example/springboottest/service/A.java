package com.springboot.example.springboottest.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(3)
public class A implements Common{
    @Override
    public void say() {
        System.out.println("A");
    }
    @PostConstruct
    public void init(){
        System.out.println("A");
    }
}
