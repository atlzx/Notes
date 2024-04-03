package com.spring.test;

import com.spring.sample.dependencyInject.DependencyInjectSample1;
import com.spring.sample.dependencyInject.Student;
import com.spring.sample.dependencyInject.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyTest {
    @Test
    // 测试property基本的setter依赖注入
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject1.xml");
        DependencyInjectSample1 bean = context.getBean(DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }

    @Test
    // 测试constructor-arg基本的构造器依赖注入
    public void test2(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject1.xml");
        DependencyInjectSample1 bean = context.getBean("diSample2",DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }

    @Test
    // 测试引用数据类型的依赖注入
    public void test3(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject2.xml");
        Teacher teacher1=context.getBean("LiMing",Teacher.class);
        Teacher teacher2=context.getBean("ZhangSan",Teacher.class);
        System.out.println(teacher1.getCourse());
        System.out.println(teacher2.getCourse());
    }

    @Test
    // 测试数组、Map、List依赖注入
    public void test4(){
        ApplicationContext context=new ClassPathXmlApplicationContext("dependencyInject2.xml");
        Student stu1 = context.getBean("stu1", Student.class);
        System.out.println(stu1);
    }
}
