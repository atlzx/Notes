package com.springboot.example.springboottest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class TestAop {

    @Before("@target(com.springboot.example.springboottest.aop.Custom)")
    public void sayHello() throws Throwable {
        log.info("{}","haha");
    }
}
