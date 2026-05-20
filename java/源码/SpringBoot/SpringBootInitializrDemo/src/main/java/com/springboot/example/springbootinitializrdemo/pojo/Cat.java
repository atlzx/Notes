package com.springboot.example.springbootinitializrdemo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cat")
@Data
public class Cat {
    private String name;
    private Integer age;
}
