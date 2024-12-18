package com.springboot.example.springboottest.common;

import com.springboot.example.springboottest.common.anno.TestAnnotation;
import org.springframework.stereotype.Component;

@TestAnnotation
@Component
public class A {
    public String caonima(){
        return "caonima";
    }
}
