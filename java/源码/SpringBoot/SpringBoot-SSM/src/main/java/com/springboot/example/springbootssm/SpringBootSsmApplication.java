package com.springboot.example.springbootssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.springboot.example.springbootssm.mapper")
public class SpringBootSsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSsmApplication.class, args);
    }

}
