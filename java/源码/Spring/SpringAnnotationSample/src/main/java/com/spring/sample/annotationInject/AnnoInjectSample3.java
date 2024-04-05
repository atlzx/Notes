package com.spring.sample.annotationInject;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AnnoInjectSample3 {
    private CommonInject commonInject;
    private String name;

    // 当构造器仅一个时，不写注解也会进行自动装配（对于引用数据类型）
    // 如果是JDK自带的类型，需要使用Value注解进行注入
    public AnnoInjectSample3(CommonInject commonInject, @Value("lzx") String name) {
        this.commonInject = commonInject;
        this.name = name;
    }

    public CommonInject getCommonInject() {
        return commonInject;
    }

    public String getName() {
        return name;
    }
}
