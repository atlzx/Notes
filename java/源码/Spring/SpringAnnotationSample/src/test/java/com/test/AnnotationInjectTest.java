package com.test;

import com.spring.sample.annotationInject.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationInjectTest {
    private final ApplicationContext context1=new ClassPathXmlApplicationContext("bean.xml");
    private final ApplicationContext context2=new AnnotationConfigApplicationContext(Config.class);
    @Test
    // 测试基本依赖注入注解
    public void test1(){
        CommonInject bean = context1.getBean("commonInject",CommonInject.class);
        System.out.println(bean.getDescription());
    }

    @Test
    // 测试自动装配引用数据类型值
    public void test2(){
        AnnoInjectSample1 bean = context1.getBean("annoInjectSample1", AnnoInjectSample1.class);
        System.out.println(bean.getCommonInject().getDescription());
    }

    @Test
    // 测试Resource注解的三种情况
    public void test3(){
        AnnoInjectSample2 bean = context1.getBean("annoInjectSample2", AnnoInjectSample2.class);
        System.out.println(bean.getC());
        System.out.println(bean.getCommonInject());
        System.out.println(bean.getCo());
    }

    @Test
    // 测试全注解开发
    // 如果想运行该测试方法，请将Config类内的方法注释起来，否则会报错
    public void test4(){
        AnnoInjectSample3 bean = context2.getBean(AnnoInjectSample3.class);
        System.out.println(bean.getCommonInject().getDescription());
        System.out.println(bean.getName());
    }

    @Test
    // 测试Bean注解
    public void test5(){
        AnnoInjectSample1 bean1 = context2.getBean("anno1", AnnoInjectSample1.class);
        AnnoInjectSample2 bean2 = context2.getBean("anno2", AnnoInjectSample2.class);
        AnnoInjectSample3 bean3 = context2.getBean("anno3", AnnoInjectSample3.class);
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);
    }
}
