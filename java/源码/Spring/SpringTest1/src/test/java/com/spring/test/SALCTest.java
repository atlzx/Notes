package com.spring.test;

import com.spring.sample.dependencyInject.DependencyInjectSample1;
import com.spring.sample.dependencyInject.JDBCTest;
import com.spring.sample.lifecycle.LifeCycleSample;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// SALC(Scope And Life Cycle)作用域与生命周期
public class SALCTest {

    private final ApplicationContext context=new ClassPathXmlApplicationContext("SALC.xml");
    @Test
    // 测试作用域
    public void test1(){
        DependencyInjectSample1 bean1 = context.getBean("scopeSample2", DependencyInjectSample1.class);
        DependencyInjectSample1 bean2 = context.getBean("scopeSample2", DependencyInjectSample1.class);
        JDBCTest jdbcTest1 = context.getBean("scopeSample1", JDBCTest.class);
        JDBCTest jdbcTest2 = context.getBean("scopeSample1", JDBCTest.class);
        System.out.println(bean1==bean2);  // 是true，因为默认的作用域是singleton
        System.out.println(jdbcTest1==jdbcTest2);  // 是false，因为修改了其作用域是prototype
    }

    @Test
    // 生命周期的测试方法
    // 运行该方法时可能会出现两个意外的后置处理器相关输出，这其实是context写在了类中，因此它在初始化id为scopeSample2的bean时触发的输出
    // 这表名后置处理器会处理xml文件内所有声明的bean对象，在它们初始化时都会统一进行处理，因此出现意外的输出是正常的
    public void test2(){

        LifeCycleSample lifecycle = context.getBean("lifecycle", LifeCycleSample.class);
        System.out.println("第六步，得到bean对象");
        System.out.println(lifecycle);
        // ApplicationContext接口没有close方法，需要强转成ClassPathXmlApplicationContext对象
        ClassPathXmlApplicationContext context1 = (ClassPathXmlApplicationContext) context;
        context1.close();  // 调用close方法使IoC容器关闭，从而触发其bean对象的销毁
    }
}
