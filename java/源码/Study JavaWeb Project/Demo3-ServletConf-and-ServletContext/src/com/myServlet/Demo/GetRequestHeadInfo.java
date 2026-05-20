package com.myServlet.Demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/GetRequestHeadInfo")
public class GetRequestHeadInfo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getMethod());  // 获取请求方式  post或get
        System.out.println(request.getScheme());  // 获取请求协议  为HTTP
        System.out.println(request.getProtocol());  // 获取请求协议及版本号  为HTTP/1.1
        System.out.println(request.getRequestURI());  // 获取请求的URI,即项目的资源路径
        System.out.println(request.getRequestURL());  // 获取请求的URL,即项目内资源的完成路径
        System.out.println(request.getLocalPort());  // 获取本应用容器的端口号  为8080
        System.out.println(request.getServerPort());  // 获得客户端发送请求时请求的端口号 如果有代理那么端口号会与LocalPort有所不同，但目前是相同的
        System.out.println(request.getRemotePort());   // 获得客户端软件的端口号
        System.out.println(request.getHeader("Accept"));

        System.out.println("--------------输出当前请求头的所有值---------------");
        Enumeration<String> enu=request.getHeaderNames();  // 获得所有的键，并以迭代器形式返回
        while(enu.hasMoreElements()){
            String element=enu.nextElement();
            System.out.println(request.getHeader(element));
        }
    }
}
