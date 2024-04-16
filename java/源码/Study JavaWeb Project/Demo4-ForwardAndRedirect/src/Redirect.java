import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Redirect")
public class Redirect extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Redirect类中的方法执行了");
        /* 使用setStatus与setHeader方法设置状态码和响应头的location实现重定向
        response.setStatus(302);
        response.setHeader("location","RedirectHandler");
         */
        response.sendRedirect("RedirectHandler");
    }

}
