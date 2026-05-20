package com.myServlet.Demo;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

/* 使用类型注解的方式进行ServletConfig初始化参数的配置*/
@WebServlet(
        value = "/ServletConfAndServletContDemo1",
        initParams = {@WebInitParam(name="keyA",value="valueA"),@WebInitParam(name="keyB",value="valueB")}
)
public class ServletConfAndServletContDemo1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--------------------输出ServletConfig----------------------");
        ServletConfig config=this.getServletConfig();  // 该方法可以获得ServletConfig对象
        Enumeration<String> configParameterNames=config.getInitParameterNames();  // 该方法可以获得ServletConfig中所有的键
        //  判断游标指针的下一个键是否存在
        while(configParameterNames.hasMoreElements()){
            String parameter=configParameterNames.nextElement();  // 判断获得游标指针的下一个键,同时游标指针下移一行
            System.out.println(parameter+"="+config.getInitParameter(parameter));  // getInitParameter方法通过传入键值来获得对应的value值
        }
        System.out.println("--------------------输出ServletContext----------------------");
        ServletContext context=req.getServletContext();  // 可以通过request对象获取
        context=getServletContext();  // 可以直接通过getServletContext获取
        context=getServletConfig().getServletContext();  // 在本质上,ServletContext是通过ServletConfig得到的
        Enumeration<String> contextParameterNames=context.getInitParameterNames();
        while(contextParameterNames.hasMoreElements()){
            String element=contextParameterNames.nextElement().toString();
            System.out.println(element+"="+context.getInitParameter(element));
        }

        // ServletContext是webapp中最大的域，它可以在本应用内实现数据的传递与共享
        context.setAttribute("name",context);
    }
}
