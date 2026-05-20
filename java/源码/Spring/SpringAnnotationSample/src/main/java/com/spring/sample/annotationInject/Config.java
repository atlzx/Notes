package com.spring.sample.annotationInject;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.*;

@Configuration  // 将类声明为配置类
@ComponentScan(basePackages = "com.spring.sample")  // 配置包扫描路径
public class Config {

    /*
        Bean注解可以作用于方法上，它有多个属性可选
            name和value属性用来起名字，两者之间没区别
            autowireCandidate属性用来配置是否可被自动装配识别
            initMethod属性用来指定初始化方法，它能指定的初始化方法是在该注解所映射的类中的方法，不是Config类里面的方法
            destroyMethod属性用来指定销毁方法，它的功能可以被PreDestroy取代
     */
    @Bean(name = "anno1",initMethod = "init",destroyMethod = "destroy")
    public AnnoInjectSample1 aaa(){
        return new AnnoInjectSample1();
    }
    @Bean(name = "anno2")
    @Lazy  // 指定是否懒加载
    @DependsOn(value = "anno1")  // 指定依赖加载对象
    @Scope(value = "prototype")  // 指定作用域
    public AnnoInjectSample2 bbb(){
        return new AnnoInjectSample2();
    }
    @Bean(name = "anno3")
    public AnnoInjectSample3 ccc(){
        return new AnnoInjectSample3(new CommonInject("描述"),"lzx");
    }

    @PostConstruct  // Config类对象初始化时执行的方法，该注解是jakarta包中的注解，不依赖于Spring
    public void init(){
        System.out.println("初始化方法执行了");
    }
    @PreDestroy  // Config类对象销毁时执行的方法，该注解是jakarta包中的注解，不依赖于Spring
    public void destroy(){
        System.out.println("销毁方法执行了");
    }

}
