package com.springboot.example.springboottest.aop;

import com.springboot.example.springboottest.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import java.lang.reflect.Proxy;

@Aspect
@Slf4j
//@Component
public class TestAop{

    @Before("@target(com.springboot.example.springboottest.aop.Custom)")
    public void sayHello() throws Throwable {
        log.info("{}", "haha");
    }
}
