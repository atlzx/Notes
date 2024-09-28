package com.springboot.example.springboottest.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class B implements Common{

    @Override
    public void say() {
        System.out.println("B");
    }

    @PostConstruct
    public void init(){
        System.out.println("B");
    }
}
