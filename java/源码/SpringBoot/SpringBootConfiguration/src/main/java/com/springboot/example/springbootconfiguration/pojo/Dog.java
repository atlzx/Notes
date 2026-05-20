package com.springboot.example.springbootconfiguration.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("build")
public class Dog {
    @Value("Lucy")
    private String name;
}
