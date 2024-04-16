package Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servlet1 implements Filter {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("开始第一次过滤");
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;  // 参数父传子
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;
        String requestURI = servletRequest1.getRequestURI();  // 获取当前资源的URI
        String time = simpleDateFormat.format(new Date());  // 规范化当前时间
        String beforeLogging = requestURI + "在" + time + "时刻被请求了";  // 生成日志
        System.out.println(beforeLogging);  // 输出该日志
        long t1 = System.currentTimeMillis();  // 得到当前系统时间的时间戳
        System.out.println("第一次过滤完成，放行");
        filterChain.doFilter(servletRequest1, servletResponse1);  // 第一次过滤操作后放行
        System.out.println("开始第二次过滤");
        long t2 = System.currentTimeMillis();  // 获取当前系统时间的时间戳
        String afterLogging = requestURI + "在" + time + "的请求耗时" + (t2 - t1) + "毫秒被响应";  // 生成日志
        System.out.println(afterLogging);  // 打印日志
        System.out.println("第二次过滤完成");
    }
}
