package com.springmvc.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// @RestControllerAdvice相当于@ControllerAdvice+@ResponseBody
@RestControllerAdvice
public class ExceptionClass {
    @ExceptionHandler(ArithmeticException.class)
    public String arithmeticExceptionHandler(Exception e){
        e.printStackTrace();
        return "some math error has happened";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(Exception e){
        e.printStackTrace();
        return "nullPointException was happened";
    }

    @ExceptionHandler(Exception.class)
    public String otherExceptionHandler(Exception e){
        e.printStackTrace();
        return "I don't know what was happened,but something has wrong";
    }
}
