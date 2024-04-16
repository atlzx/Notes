package cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ServletA")
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookieA = new Cookie("name", "zhangsan");  // 创建一个Cookie对象
        cookieA.setMaxAge(300);  // 该方法用于设置Cookie对象的存在时间，传入的参数单位为秒
        Cookie cookieB = new Cookie("value", "lisi");
        resp.addCookie(cookieA);  // 将Cookie对象添加进response对象，它之后会被tomcat整合进响应报文中反馈给浏览器，浏览器中就会有该cookie对象
        resp.addCookie(cookieB);  // 该方法未使用setMaxAge方法设置存在时间，因此关闭浏览器再打开时它将会消失
    }
}
