package com.springboot.example.mybatisplus;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DruidDynamicDataSourceConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@SpringBootApplication
@MapperScan("com.springboot.example.mybatisplus.mapper")
public class SpringBootMybatisPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisPlusApplication.class);
    }
}
