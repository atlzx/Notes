package com.springboot.example.springbootconfiguration.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("other")
public class People {
    @Value("lzx")
    private String name;
}
