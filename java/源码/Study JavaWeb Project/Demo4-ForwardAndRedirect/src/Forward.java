import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;

@WebServlet("/Forward")
public class Forward extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Forward类中的service方法执行了");
        RequestDispatcher dispatcher=request.getRequestDispatcher("ForwardHandler");  // 获得请求转发器
        dispatcher.forward(request,response);  // 使用请求转发器实现请求转发
    }
}
