package com.spring.sample;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@EnableAspectJAutoProxy  // 使Spring自动创建代理来支持AOP切面操作，它需要与@Configuration才能生效
@Configuration  // 声明该类为配置类对象，使Spring能够扫描对应路径下的IoC容器相关注解
@ComponentScan(value = "com.spring.sample")
public class Config {

}
