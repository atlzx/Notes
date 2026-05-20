package com.Hello.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(value = "/ServletLifeCycle",loadOnStartup = 10)
public class ServletLifeCycle extends HttpServlet {
    public ServletLifeCycle(){
        System.out.println("进行了构建");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("进行了初始化");
    }  // init方法是无参的init方法

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("服务");
    }

    @Override
    public void destroy() {
        System.out.println("执行了销毁");
    }
}
