package com.springboot.example.springbootwebsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Object respData;
    private Integer statusCode;
    private String message;
    private String jwt;
    public static final String DEFAULT_SUCCESS_MESSAGE="请求成功响应";
    public static final String DEFAULT_ERROR_MESSAGE="请求失败";
    public static final Integer LOGIC_ERROR_STATUS_CODE=-1;
    public static final Integer SUCCESS_STATUS_CODE=200;

    public static Result getResult(Object respData,Integer statusCode,String message,String jwt){
        return new Result(respData,statusCode,message,jwt);
    }
}
