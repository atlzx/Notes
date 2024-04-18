package com.springmvc.example.utils;

public class R {
    private int code=200;
    private boolean falg=true;

    private Object data;

    public static R ok(Object data){
        R r=new R();
        r.data=data;
        return r;
    }
}
