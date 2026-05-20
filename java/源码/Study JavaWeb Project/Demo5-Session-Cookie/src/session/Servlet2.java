package session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println(session.getId());
        System.out.println(session.isNew());
        System.out.println(session.getAttribute("name"));  //
        System.out.println(session.getCreationTime());  // 输出创建时间的时间戳
        System.out.println(session.getLastAccessedTime());  // 输出上次访问时间的时间戳
    }
}
