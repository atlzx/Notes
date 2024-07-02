package com.example.cloud.components;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component  // 注册进Ioc容器
@Slf4j
public class MyCustomGatewayFilterFactory extends AbstractGatewayFilterFactory<MyCustomGatewayFilterFactory.Config> {
    public MyCustomGatewayFilterFactory(){
        super(MyCustomGatewayFilterFactory.Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        // GatewayFilter接口是函数式接口，使用lambda表达式来进行书写
        return (exchange, chain)->{
            ServerHttpRequest req = exchange.getRequest();
            log.info("{}", req.getQueryParams().getFirst("status"));
            // 如果status与参数一致，那么放行
            if(config.getStatus().equals(req.getQueryParams().getFirst("status"))){
                return chain.filter(exchange);
            }else{
                // 否则返回请求失败
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                // setComplete等方法是SpringBoot响应式编程的方式
                return exchange.getResponse().setComplete();
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("status");
    }

    @Data
    public static class Config{
        private String status;
    }
}
