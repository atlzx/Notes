package com.springmvc.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// 该类需要继承AbstractAnnotationConfigDispatcherServletInitializer类，并实现其中的三个方法
public class MvcInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};  // 在数组对象内传入配置类的class对象
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};  // 配置路径映射
    }
}
