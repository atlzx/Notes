package com.springboot.example.springbootinitializrdemo.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@Data
public class Child {
    private String name;
    private Date birthday;
    private Integer age;
}
