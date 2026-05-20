package com.springmvc.example.utils;


import lombok.Data;

@Data  // 加上getter和setter方法，在SpringMVC调用JSON转换器转换JSON的过程时要用到
public class Result {
    private int code=200;
    private boolean falg=true;

    private Object data;

    public static Result ok(Object data){
        Result r=new Result();
        r.data=data;
        return r;
    }
}
