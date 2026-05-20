package com.springboot.example.fileupload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private String message;
    private Object result;

    public static Result ok(Object result){
        return new Result("success",result);
    }
}
