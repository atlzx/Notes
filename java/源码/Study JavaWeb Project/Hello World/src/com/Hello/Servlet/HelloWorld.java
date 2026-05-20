package com.Hello.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*
    HttpServlet继承了GenericServlet,而GenericServlet实现了Servlet接口
    因此，可以通过继承HttpServlet类来间接实现Servlet接口
*/
/*
    使用WebServlet注解可以简洁的配置Servlet对应的请求映射路径
    使用value="xxx"或urlPatterns="xxx"的方式来指定Servlet对应的请求映射路径
    value和或urlPatterns互为别名，设置了一个另一个也会受到影响，因此只需配置一个即可
    如果想指定多个路径，则需要value={"aaa","bbb","ccc"}或或urlPatterns={"aaa","bbb","ccc"}以传入数组
    为其配置name属性以给当前的类起一个别名
    在注解中仅写有单个路径的前提下，可以只写属性值，不写属性名
*/
@WebServlet(value="/Hello")
public class HelloWorld extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginName= request.getParameter("login_name");  // getParameter方法用来根据前端的name属性值获取参数值
        String flag="<h1>OK<h1>";
        if(!loginName.equals("admin")){
            flag="<h1>NO<h1>";
        }
        //response.setHeader("Content-Type","text/css");  // setHeader方法用于设置响应头的部分属性的值
        response.setContentType("text/css");  // setContentType方法用于直接设置响应头的Content-Type属性值
        PrintWriter writer=response.getWriter();  // 该方法返回向响应体中打印字符串的相应流
        writer.write(flag);  // write方法可以向流中写入指定字符串
    }
}
