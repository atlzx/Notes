package com.springboot.example.springbootinitializrdemo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "dog")
@Data
public class Dog {
    private String name;
    private Integer age;
}
