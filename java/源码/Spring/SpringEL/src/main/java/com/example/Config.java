package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.example")
public class Config {
    @Bean
    public ExpressionParser getExpressionParser(){
        return new SpelExpressionParser();
    }
}
