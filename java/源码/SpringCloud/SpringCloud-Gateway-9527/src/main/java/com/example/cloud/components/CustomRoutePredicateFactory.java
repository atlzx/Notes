package com.example.cloud.components;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component  // 我们需要把这个自定义Predicate加入IOC容器
// 类需要继承AbstractRoutePredicateFactory，泛型指定要校验对象的Config类。一般都是写自己的自定义类内自定义的Config类进去
// 我们自定义Predicate,可以参照官方的Predicate的定义方式，照着官方的板子写就行
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    // 提供一个无参的构造方法，调用父类的构造方法，将自定义的Config类的class对象传入，用来提供本类的Config类实例
    public CustomRoutePredicateFactory() {
        super(CustomRoutePredicateFactory.Config.class);
    }

    @Override
    // 该方法用于添加shortcut方式的匹配参数
    public List<String> shortcutFieldOrder() {
        // 如果是单值，那么使用Collections.singletonList方法，将Config类中的属性名传入即可
        return Collections.singletonList("name");
        // 如果是多值，参考CookieRoutePredicateFactory，使用Arrays.asList传递，传递顺序即为我们配置文件的书写顺序
    }

    @Override
    // 该方法是进行校验的主方法
    public Predicate<ServerWebExchange> apply(CustomRoutePredicateFactory.Config config) {
        return (GatewayPredicate) exchange -> {
            // 得到请求参数的值（如果是数组得到第一个值）
            String test = exchange.getRequest().getQueryParams().getFirst("name");
            // 判断是否为空
            if(test!=null){
                // 通过config可以得到配置文件中提供的对应值，将它与我们得到的当前请求参数进行对比
                return config.getName().equals(test);
            }
            return false;  // 否则返回false
        };
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Validated
    // 该Config类中的属性就是我们要进行校验的项，我们一般需要提供它的getter和setter方法
    public static class Config{
        @NotEmpty
        private String name;  // 我们需要进行校验的值
    }
}
