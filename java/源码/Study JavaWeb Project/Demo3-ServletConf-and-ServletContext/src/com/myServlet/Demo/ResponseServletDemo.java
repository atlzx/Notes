package com.myServlet.Demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ResponseServletDemo")
public class ResponseServletDemo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 响应头相关API
        response.setStatus(200);
        String info="<h1>你好</h1>";
        response.setHeader("Content-Type","text/html");  // 设置响应头中指定键值对的值
        response.setContentType("text/html");  // 设置响应头中Content-Type值
        int length=info.getBytes().length; // 获取info的字节数的长度
        response.setContentLength(length);  // 设置响应头中ContentLength值



        // 响应体相关API
        // 获取一个向响应体中输入文本信息的字符输出流
        PrintWriter writer=response.getWriter();
        writer.write(info);  // 向响应体内写入文本
        // 获取一个向响应体中输入二进制信息的字节输出流
//        ServletOutputStream stream=response.getOutputStream();


    }
}
