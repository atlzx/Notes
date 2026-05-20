package Listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();  // 得到ServletContext对象
        System.out.println("向应用域内添加属性");
        servletContext.setAttribute("name","张三");
        System.out.println("修改应用域中的属性");
        servletContext.setAttribute("name", "李四");
        System.out.println("移除应用域中的属性");
        servletContext.removeAttribute("name");
    }
}
