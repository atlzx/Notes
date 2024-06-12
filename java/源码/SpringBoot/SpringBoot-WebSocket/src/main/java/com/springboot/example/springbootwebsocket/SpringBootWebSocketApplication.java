package com.springboot.example.springbootwebsocket;

import com.springboot.example.springbootwebsocket.utils.CommonConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.springboot.example.springbootwebsocket.mapper")
@EnableConfigurationProperties(value = {CommonConstantUtil.class})
@Slf4j
public class SpringBootWebSocketApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(SpringBootWebSocketApplication.class);
    }
}
