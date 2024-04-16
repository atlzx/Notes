package com.myProject.schedule.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 获取请求的具体路径，该路径从上下文路径开始 */
        String[] split = req.getRequestURI().split("/");
        String str = split[split.length-1];
        /* 依据前端请求的路径不同，使后端定义的方法与其对应，因此可以使用反射的方式调用这些函数 */
        Class clazz = this.getClass();
        try {
            Method method = clazz.getDeclaredMethod(str, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*

        使用传统的依据请求路径进行判断，代码比较冗长

        String[] split = req.getRequestURI().split("/");
        String str = split[split.length];
        if(str.equals("add")){
            this.add(req,resp);
        }else if(str.equals("find")){
            this.find(req,resp);
        }else if(str.equals("update")){
            this.update(req,resp);
        }else if(str.equals("remove")){
            this.remove(req,resp);
        }


        */



    }
}
