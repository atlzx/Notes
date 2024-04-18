package com.springmvc.example.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
    实现controller层相关配置类需要做如下配置:
        声明为配置类
        开启组件扫描
        提供HandlerAdapter与HandlerMapping
        静态资源处理
        开启jsp视图解析
        实现json转换
        配置拦截器
 */
@Configuration  // 生命配置类
@ComponentScan({"com.springmvc.example.controller","com.springmvc.example.exception"})  // 开启组件扫描
@EnableWebMvc  // 提供HandlerAdapter与HandlerMapping并实现JSON转换

// 实现WebMvcConfigurer来进一步配置
public class ControllerConfig implements WebMvcConfigurer {

    // 开启jsp视图解析
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/",".jsp");
    }

    // 开启静态资源处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 该方法用于配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
