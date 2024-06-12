package com.springboot.example.springbootwebsocket.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "common")
@Data
public class CommonConstantUtil {
    private String jwtParseName;
    private String jwtKey;
}
