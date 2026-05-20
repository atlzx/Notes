package com.spring.test;

import com.spring.sample.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanTest {
    private final ApplicationContext context=new ClassPathXmlApplicationContext("factoryBean.xml");
    @Test
    // 测试FactoryBean
    public void test1(){
        User bean = (User) context.getBean("userFactory");
        System.out.println(bean);
    }
}
