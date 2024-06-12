package com.springboot.example.springbootwebsocket.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.springboot.example.springbootwebsocket.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice
@Slf4j
public class LoginExceptionHandler {
    @ExceptionHandler(
        value = {
            JsonProcessingException.class,
            InvocationTargetException.class,
            InstantiationException.class,
            IllegalAccessException.class,
            NoSuchMethodException.class
        }
    )
    public Result JsonExceptionHandler(Exception e){
        log.error(e.toString());
        return Result.getResult(null,Result.LOGIC_ERROR_STATUS_CODE,"后端逻辑出错",null);
    }
}
