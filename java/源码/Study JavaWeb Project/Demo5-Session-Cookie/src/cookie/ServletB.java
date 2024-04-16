package cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/ServletB")
public class ServletB extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
//      getCookies方法返回一个Cookie数组，但如果请求中没有Cookie,那么它将返回null，null无法遍历，因此在遍历前需要进行判断
        if(cookies!=null){
            for(Cookie i:cookies){
                System.out.println(i.getName());
                System.out.println(i.getValue());
            }
        }
    }
}
