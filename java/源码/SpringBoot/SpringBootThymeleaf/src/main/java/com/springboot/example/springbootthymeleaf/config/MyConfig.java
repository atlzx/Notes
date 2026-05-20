package com.springboot.example.springbootthymeleaf.config;

import com.springboot.example.springbootthymeleaf.things.Something;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.*;

import javax.print.attribute.standard.Media;

@Configuration
public class MyConfig {
    @Bean
    public RouterFunction<ServerResponse> testRoute(Something something) {
        return RouterFunctions
            // 调用route方法来得到一个构建器对象，得到构建器对象之后就可以进行相关的操作了
            .route()
            // 针对相关路由进行相关处理，第一个参数是前端路径匹配，第二个是设置前端请求返回的格式，这两个参数一起构成路由的匹配
            // 例:请求/test2/11，且希望后端返回application/json，那么可以匹配，但是相同请求路径下，希望后端返回text/html，那么就不能匹配，因为后端并未设置该返回格式
            // 第三个配置一个HandlerFunction接口对象，该接口对象是一个函数式接口，它的方法会在匹配路由时被调用
            .GET("/test1/{id}", RequestPredicates.accept(MediaType.ALL),something::handle1)
            .POST("/test2/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON),something::handle2)
            .build();
    }
}
