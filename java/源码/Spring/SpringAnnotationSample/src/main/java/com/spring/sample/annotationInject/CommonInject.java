package com.spring.sample.annotationInject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("commonInject")  // Component用来标识该类，并为其指定id,如果没写默认依据大驼峰命名法命名
public class CommonInject {

    // Value注解用来注入基本数据类型的值
    // 它可以作用于方法形参、类的属性、方法，这样分别表示构造器注入、属性直接注入（不调用setter方法）、setter方法注入
    // 当然，setter方法注入也可以使用形参进行注入
    // 如果全都写，貌似setter方法注入优先级更高
    @Value("aaa")
    private String description;

    public CommonInject(@Value("bbb") String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Value("ccc")
    public void setDescription( String description) {
        System.out.println("CommonInject的setter方法执行");
        this.description = description;
    }
}
