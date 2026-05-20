package com.springboot.example.springbootinitializrdemo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "person")
@Component
@Data
public class Person {
    private String name;
    private Integer age;
}
