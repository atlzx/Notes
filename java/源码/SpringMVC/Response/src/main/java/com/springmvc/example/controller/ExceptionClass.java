package com.springmvc.example.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// @RestControllerAdvice相当于@ControllerAdvice+@ResponseBody
@RestControllerAdvice
public class ExceptionClass {
    @ExceptionHandler(ArithmeticException.class)
    public String arithmeticExceptionHandler(){
        return "发生了数据错误";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(){
        return "出现了空指针异常";
    }

    @ExceptionHandler(Exception.class)
    public String otherExceptionHandler(){
        return "未知错误";
    }
}
