package com.springboot.example.springboottest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
//@Slf4j
@Component
public class TestAop {

//    @Around("@target(org.springframework.transaction.annotation.Transactional)")
    public Object sayHello(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("{}","Hello");
        return null;
    }
}
