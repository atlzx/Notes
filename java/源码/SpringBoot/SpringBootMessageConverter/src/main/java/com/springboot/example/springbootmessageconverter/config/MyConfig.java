package com.springboot.example.springbootmessageconverter.config;

import com.springboot.example.springbootmessageconverter.component.MyHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyConfig implements WebMvcConfigurer{
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 向SpringBoot的IOC容器内增加我们自定义的转换器
        converters.add(new MyHttpMessageConverter());
    }
}
