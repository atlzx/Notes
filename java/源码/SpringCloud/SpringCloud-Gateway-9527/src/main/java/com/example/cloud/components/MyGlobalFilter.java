package com.example.cloud.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component  // 注册进IOC容器
@Slf4j
// 通过实现GlobalFilter和Ordered接口来实现全局过滤
// GlobalFilter声明了过滤方法，Ordered支持过滤器调整加载顺序
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override  // 表示加载的优先级，返回的值越小，优先级越高
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long beginTime = System.currentTimeMillis();
        // chain.filter方法的调用即表示放行，后面的then方法是服务执行完之后过滤器相应的处理。在方形之前执行的方法是请求未放行所执行的方法
        return chain.filter(exchange).then(
            Mono.fromRunnable(
                ()->{
                    long endTime=System.currentTimeMillis();
                    log.info("接口调用时间:{}毫秒",endTime-beginTime);
                    log.info("访问接口主机:{}",exchange.getRequest().getURI().getHost());
                    log.info("访问接口URL参数:{}",exchange.getRequest().getURI().getRawQuery());
                    log.info("访问接口端口:{}",exchange.getRequest().getURI().getPort());
                    log.info("访问接口URL:{}",exchange.getRequest().getURI().getPath());
                }
            )
        );
    }
}
