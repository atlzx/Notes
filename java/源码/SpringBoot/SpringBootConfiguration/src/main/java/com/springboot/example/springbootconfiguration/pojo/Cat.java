package com.springboot.example.springbootconfiguration.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("test")
public class Cat {
    @Value("Tom")
    private String name;
}
