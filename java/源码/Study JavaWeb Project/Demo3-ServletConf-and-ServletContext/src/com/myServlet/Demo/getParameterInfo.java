package com.myServlet.Demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet("/getParameterInfo")
public class getParameterInfo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("username"));  // 获取单个键值对格式的属性值
        String[] array= request.getParameterValues("hobby");  // 获得指定键对应的多个值
        System.out.println(Arrays.toString(array));  // 将数组转换为字符串输出
        System.out.println("------------------------------使用getParameterNames得到每个键并迭代输出---------------------------------");
        // 获取所有键，并输出每个键对应的值
        Enumeration<String> enu=request.getParameterNames();  // 获得所有键的Enumeration迭代器
        while(enu.hasMoreElements()){
            String element=enu.nextElement();
            String[] values=request.getParameterValues(element);  // 统一使用getParameterValues来获取字符数组格式的属性值，以避免一个键对应不同数量值的问题
            // 判断values值的长度，以确定该次循环得到的values是多值还是单值
            if(values.length>1){
                System.out.println(Arrays.toString(values));
            }else{
                System.out.println(values[0]);
            }
        }

        System.out.println("-------------------------------使用getParameterMap得到每个键并迭代输出------------------------------------");
        // 获取所有键，并输出每个键对应的值
        Map<String,String[]> map=request.getParameterMap();  // 返回一个Map类型的数据容器，该容器含有全部的键值对
        Set<Map.Entry<String,String[]>> set=map.entrySet();  // 将该数据容器转换为可以迭代的类型
        for(Map.Entry<String,String[]> entry:set){
            String key= entry.getKey();  //得到当前循环的键
            String[] value= entry.getValue();  // 得到value值
            // 判断得到的值是单值还是多值，再进行处理
            if(value.length>1){
                System.out.println(Arrays.toString(value));
            }else{
                System.out.println(value[0]);
            }
        }

    //        BufferedReader reader=request.getReader();   该方法用来获取一个从请求体读取字符的字符输入流，用来读取如JSON串的非键值对格式数据
    //        ServletInputStream stream=request.getInputStream();   该方法用来获得从一个请求中读取二进制数据字节的输入流，用来读取请求体的文件
    }
}
