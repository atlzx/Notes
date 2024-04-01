package com.spring.test;

import com.spring.sample.dependencyInject.DependencyInjectSample1;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyTest {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject1.xml");
        DependencyInjectSample1 bean = context.getBean(DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }

    @Test
    public void test2(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject1.xml");
        DependencyInjectSample1 bean = context.getBean("diSample2",DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }
}
