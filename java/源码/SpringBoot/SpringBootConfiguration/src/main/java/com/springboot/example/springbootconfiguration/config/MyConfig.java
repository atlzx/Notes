package com.springboot.example.springbootconfiguration.config;

import com.springboot.example.springbootconfiguration.pojo.Sheep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("other")
@PropertySource("classpath:config.properties")
public class MyConfig {
    @Bean
    @Profile("test")
    public Sheep sheep(){
        return new Sheep("xiaoen");
    }

}
