package com.spring.test;

import com.spring.sample.GetBeanSample;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetBeanTest {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("user.xml");
        // 我们能得到的实例对象取决于我们的xml文件内写的是哪一个实现类
        GetBeanSample bean = context.getBean(GetBeanSample.class);
        System.out.println("直接传入接口的Class对象生成接口的实例对象:"+bean);
    }
}
