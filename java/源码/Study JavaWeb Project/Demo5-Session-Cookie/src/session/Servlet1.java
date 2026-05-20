package session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();  // 得到session对象，没有就创建一个
        System.out.println(session.getId());  // 输出当前session对象的ID
        System.out.println(session.isNew());  // 输出当前的session是不是新的
        session.setMaxInactiveInterval(60*30);
        Cookie cookie=new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60*30);
        session.setAttribute("name","张三");  // 给当前的session设置一个属性
        resp.setContentType("text/html;charset=UTF-8");  // 设置响应头的Content-Type属性
        resp.setCharacterEncoding("UTF-8");  // 设置响应体整体的编码格式
        resp.getWriter().write("成功");  // 读取字符输出流并向其中写入字符
        resp.addCookie(cookie);
    }
}
