package com.springboot.example.springboottest.common;

import org.springframework.stereotype.Component;

@Component
public class B extends A{
    @Override
    public String caonima() {
        return "wocaonima";
    }
}
