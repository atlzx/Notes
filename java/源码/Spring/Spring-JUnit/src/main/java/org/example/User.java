package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
    @Value("lzx")
    private String name;
    @Value("12")
    private int age;
}
