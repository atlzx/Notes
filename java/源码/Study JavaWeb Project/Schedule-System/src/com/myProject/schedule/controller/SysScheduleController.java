package com.myProject.schedule.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    通过继承BaseController类的方式来实现对service方法的实现，同时减少代码冗余
    在WebServlet注解内写入 /schedule/* 是该类会处理所有前缀为schedule,后缀任意的资源请求路径
    此时本类中没有service方法，就会调用父类的service方法，然后再根据请求的具体情况决定调用类中的哪个方法
*/

@WebServlet("/schedule/*")
public class SysScheduleController extends BaseController {
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("add");
    }
    protected void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("find");
    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("update");
    }
    protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("remove");
    }
}
