package com.springboot.example.springbootwebsocket.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "common.name")
public class CommonNameUtil {
    public static String jwtParseName;
}
