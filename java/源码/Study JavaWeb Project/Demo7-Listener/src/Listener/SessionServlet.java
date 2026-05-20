package Listener;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();  // 得到当前请求的session域对象
        System.out.println("向session域中添加普通的属性");
        session.setAttribute("name", "张三");
        System.out.println("修改session域中普通的属性");
        session.setAttribute("name", "李四");
        System.out.println("从session域中移除属性");
        session.removeAttribute("name");
        System.out.println("将SessionListener的实例对象添加到session域中");
        session.setAttribute("myServlet",new SessionListener());
        System.out.println("从session域中移除SessionListener的实例对象");
        session.removeAttribute("myServlet");
        Cookie cookie=new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60*10);
        resp.addCookie(cookie);
        session.setAttribute("listener",new SessionListener());  /* 在第一次添加时为创建，再次活化而执行改代码会变为修改 */
        session.setAttribute("data",111); /* 在第一次添加时为创建，再次活化而执行改代码会变为修改 */
    }
}
