package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.cloud.mapper")
public class AccountSeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountSeataApplication.class);
    }
}
