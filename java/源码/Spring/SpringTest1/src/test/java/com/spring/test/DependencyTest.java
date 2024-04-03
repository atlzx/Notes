package com.spring.test;

import com.spring.sample.dependencyInject.DependencyInjectSample1;
import com.spring.sample.dependencyInject.JDBCTest;
import com.spring.sample.dependencyInject.Student;
import com.spring.sample.dependencyInject.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyTest {
    private final ApplicationContext context1=new ClassPathXmlApplicationContext("dependencyInject1.xml");
    private final ApplicationContext context2=new ClassPathXmlApplicationContext("dependencyInject2.xml");
    private final ApplicationContext context3=new ClassPathXmlApplicationContext("dependencyInject3.xml");

    @Test
    // 测试property基本的setter依赖注入
    public void test1(){
        DependencyInjectSample1 bean = context1.getBean(DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }

    @Test
    // 测试constructor-arg基本的构造器依赖注入
    public void test2(){
        DependencyInjectSample1 bean = context1.getBean("diSample2",DependencyInjectSample1.class);
        System.out.println("输出:"+bean);
    }

    @Test
    // 测试引用数据类型的依赖注入
    public void test3(){
        Teacher teacher1=context2.getBean("LiMing",Teacher.class);
        Teacher teacher2=context2.getBean("ZhangSan",Teacher.class);
        System.out.println(teacher1.getCourse());
        System.out.println(teacher2.getCourse());
    }

    @Test
    // 测试数组、Map、List依赖注入
    public void test4(){
        Student stu1 = context2.getBean("stu1", Student.class);
        System.out.println(stu1);
    }

    @Test
    // 测试util:对应的集合依赖注入
    public void test5(){
        Student stu1 = context2.getBean("stu2", Student.class);
        System.out.println(stu1);
    }

    @Test
    // 测试p命名空间和引入外部属性文件
    public void test6(){
        JDBCTest jdbcTest = context3.getBean("jdbcTest", JDBCTest.class);
        Student stu3 = context3.getBean("stu3", Student.class);
        System.out.println(jdbcTest);
        System.out.println(stu3);
    }
}
