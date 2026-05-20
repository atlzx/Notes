package com.myServlet.Demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Servlet3")
public class Servlet3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path1=getServletContext().getRealPath("info");  // 获取webapp项目目录下的指定目录,文件等的路径
        String path2=getServletContext().getContextPath();  // 获取项目的上下文路径
        System.out.println(path1);
        System.out.println(path2);
    }
}
