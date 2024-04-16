package Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/ServletA")

public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("开始处理请求");
        try {
            Thread.sleep(10);  // 模拟处理请求的时间
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("请求处理完成");
    }
}
