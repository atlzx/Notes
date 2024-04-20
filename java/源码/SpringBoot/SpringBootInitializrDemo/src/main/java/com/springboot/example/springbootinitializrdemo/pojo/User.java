package com.springboot.example.springbootinitializrdemo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user")
@Data
public class User {
    private String name;
    private String description;
}
