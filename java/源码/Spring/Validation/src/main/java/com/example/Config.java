package com.example;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan("com.example")
public class Config {
    @Bean
    // 校验需要校验器，这里提供指定的校验器，Spring读取的校验器的id是validator,因此设置该bean的id为validator
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor validationPostProcessor(){
        return new MethodValidationPostProcessor();
    }
}
