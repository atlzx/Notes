package com.test;

import com.spring.sample.AnnoSample1;
import com.spring.sample.AnnoSample1;
import com.spring.sample.AnnotationApplicationContext;
import com.spring.sample.ApplicationContext;
import org.junit.jupiter.api.Test;

public class AnnoTest {
    @Test
    public void test1(){
        ApplicationContext context=new AnnotationApplicationContext("com.spring");
        AnnoSample1 bean = context.getBean(AnnoSample1.class);
        System.out.println(bean);
        bean.run1();
    }
    @Test
    public void test2(){
        ClassLoader classLoader = Thread.currentThread().getClass().getClassLoader();
        System.out.println(classLoader);
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }
}
