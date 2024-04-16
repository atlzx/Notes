package com.springmvc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@WebFilter(filterName = "filter1",urlPatterns = "/")
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        filterChain.doFilter(servletRequest,servletResponse);
        servletResponse.setCharacterEncoding("UTF-8");
        System.out.println("abababababababab");
    }
}
