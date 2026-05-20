package com.spring.sample.annotationInject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AnnoInjectSample1 {
    /*
        Autowired注解用来自动寻找对应相关的类对象进行赋值，他默认会根据byType模式进行寻找
        该注解适用于属性、形参、方法和构造器，根据其声明位置的不同，Spring会依据不同的方式进行赋值
        声明在属性上会直接进行属性注入，声明在方法上会依据setter注入，声明在构造器上会依据构造器注入
        如果想根据name进行装配，需要用到Qualifier注解
     */
    @Autowired
    @Qualifier("commonInject")
    private CommonInject commonInject;

    public void setCommonInject(CommonInject commonInject) {
        System.out.println("AnnoInjectSample1的setter方法执行");
        this.commonInject = commonInject;
    }

    public CommonInject getCommonInject() {
        return commonInject;
    }

    public void init(){
        System.out.println("初始化！！！");
    }

    public void destroy(){
        System.out.println("销毁！！！");
    }
}
