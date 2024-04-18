package com.springmvc.example.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 声明类为异常处理类
@RestControllerAdvice
public class SSMException {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        e.printStackTrace();
        return "something wrong";
    }

}
