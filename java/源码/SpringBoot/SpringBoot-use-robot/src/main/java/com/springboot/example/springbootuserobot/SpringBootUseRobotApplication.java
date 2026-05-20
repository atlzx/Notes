package com.springboot.example.springbootuserobot;

import com.springboot.example.springbootstarterrobot.autoconfiguration.RobotAutoConfiguration;
import com.springboot.example.springbootuserobot.customannotation.EnableRobot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(RobotAutoConfiguration.class)  // 第一种方式:使用@Import注解导入类
//@EnableRobot  // 第二种方式:自定义Enable注解
public class SpringBootUseRobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootUseRobotApplication.class, args);
    }

}
