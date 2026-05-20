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
        value = "/ServletConfAndServletContDemo2",
        initParams = {@WebInitParam(name="keyA",value="value2A"),@WebInitParam(name="keyB",value="value2B")}
)
public class ServletConfAndServletContDemo2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig config = this.getServletConfig();
        Enumeration<String> parameterNames = config.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameter = parameterNames.nextElement();
            System.out.println(parameter + "=" + config.getInitParameter(parameter));
        }
        System.out.println("--------------------输出ServletContext----------------------");
        ServletContext context=req.getServletContext();  // 可以通过request对象获取
        context=getServletContext();  // 可以直接通过getServletContext获取
        context=getServletConfig().getServletContext();  // 在本质上,ServletContext是通过ServletConfig得到的
        Enumeration contextParameterNames=context.getInitParameterNames();
        while(contextParameterNames.hasMoreElements()){
            String element=contextParameterNames.nextElement().toString();
            System.out.println(element+"="+context.getInitParameter(element));
        }
        System.out.println(context==context.getAttribute("name"));
    }
}
