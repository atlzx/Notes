package com.springboot.example.springboottest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class SpringBootTestApplication {
    public static void main(String[] args) {
        System.load("E:\\code\\opencv\\build\\java\\x64\\opencv_java4100.dll");
        SpringApplication.run(SpringBootTestApplication.class, args);
    }

}
