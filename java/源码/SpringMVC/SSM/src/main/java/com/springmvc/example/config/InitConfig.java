package com.springmvc.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class InitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    // 该方法返回的数组对象内需要包含rootIOC容器的配置类的Class对象
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ServiceConfig.class, MapperConfigNew.class};
    }

    @Override
    // 该方法返回的数组对象需要包含webIOC容器需要的配置类的Class对象
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ControllerConfig.class};
    }

    @Override
    // 用于指定DispatcherServlet拦截的请求路径，/ 是全都拦截
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
