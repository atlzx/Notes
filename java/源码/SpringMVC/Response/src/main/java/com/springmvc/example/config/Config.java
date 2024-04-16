package com.springmvc.example.config;

import com.springmvc.example.controller.MyInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.springmvc.example.controller")
public class Config implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 这里的设置是自动在视图解析时为返回的字符串加上对应的前缀和后缀，使它形成一个完整的路径
        registry.jsp("/WEB-INF/view/",".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // 开启静态资源匹配
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 首先传入拦截器对象，接下来可以精确的指定拦截器像拦截的请求，接下来还可以排除在拦截范围内的请求
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/jsp/index","/fr/forward","/fr/redirect","/json/getJSON")
                .excludePathPatterns("/json/getJSON");
    }
}
