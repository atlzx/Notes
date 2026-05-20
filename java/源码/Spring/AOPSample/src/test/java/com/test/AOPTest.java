package com.test;

import com.spring.sample.Config;
import com.spring.sample.ProxyInterfaceImpl;
import com.spring.sample.ProxyTestInterface;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {
    @Test
    // AOP注解相关测试
    public void test1(){
        ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        // 使用Spring的相关特性，必须要用Spring提供的API来调用，否则是不成立的
        ProxyTestInterface bean = context.getBean("proxyInterfaceImpl",ProxyTestInterface.class);
        bean.sum(1,2);
    }
    @Test
    // AOP-xml相关测试
    public void test2(){
        // 运行该测试时会出现两次重复通知
        // 这是因为AOPAnnoSample也参与了该次测试
        //为了避免该问题，可以暂时将Config类中的EnableAspectJAutoProxy注解注释掉，或去掉AOPAnnoSample类中的Component标签
        // 出现该问题的原因是xml配置的context:component-scan标签会扫描指定路径下的类，它可以识别@Configuration注解,
        // 而@Configuration注解可以使@EnableAspectJAutoProxy注解生效，因此会使AOPAnnoSample切面类生效
        ApplicationContext context=new ClassPathXmlApplicationContext("AOP.xml");
        ProxyTestInterface bean = context.getBean("proxyInterfaceImpl",ProxyTestInterface.class);
        bean.sum(1,2);
    }
}
