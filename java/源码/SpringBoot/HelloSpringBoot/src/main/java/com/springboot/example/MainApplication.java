package com.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 该注解用来声明这是SpringBoot的项目启动类
public class MainApplication {
    public static void main(String[] args) {
        // 调用SpringApplication的run方法，传入本类的Class对象和参数来启动SpringBoot项目
        // 这是固定写法
        SpringApplication.run(MainApplication.class,args);
    }
}
