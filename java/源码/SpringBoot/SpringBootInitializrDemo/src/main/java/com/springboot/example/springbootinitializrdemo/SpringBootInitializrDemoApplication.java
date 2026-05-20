package com.springboot.example.springbootinitializrdemo;

import com.springboot.example.springbootinitializrdemo.pojo.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootInitializrDemoApplication {

    public static void main(String[] args) {
        // run方法会返回一个IOC容器对象
        var context = SpringApplication.run(SpringBootInitializrDemoApplication.class, args);
//        test1(context);
//        test2(context);
        test3(context);
    }

    // 测试常用注解
    public static void test1(ApplicationContext context){
        // getBeanDefinitionNames来得到每个bean的名称
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        Object bean = context.getBean("DruidDataSource");  // 测试是否存在指定的bean
        Object bean1 = context.getBean("DruidDataSource");
        Object bean2 = context.getBean("myConfig");
        System.out.println(bean);  // 输出
        System.out.println(bean1);  // 输出
        System.out.println(bean==bean1);  // 判断二者是否相同
        System.out.println(bean2);
    }


    // 测试属性依赖注入
    public static void test2(ApplicationContext context){
        /*
            小小的备注一手:
                Cat对象和User对象存在是因为满足ConditionalOnXxx注解而创建的
                Person对象存在是因为Person类上有@Component注解
                Dog类对象存在是因为配置类使用@EnableConfigurationProperties指定Dog类的属性注入

                但是Dog类对象存在，那么User类的zhangsan应该会被创建，因为随着Dog类的创建，它也应该被创建
                然而事实并不是这样，猜测是@ConditionalOnXxx注解在bean创建时才会进行判断，而在遍历@Bean方法执行时，此时Dog还不存在，故就没有创建

                同时可以发现User的实例对象输出的name是我的windows系统的用户名,这个是因为windows系统内表示用户名的变量名就叫username
                虽然不知道它怎么会与Spring读取的properties文件内的属性名冲突的，但是显然它的优先级要高
                因此就输出了windows系统的用户名
                解决该问题的方法就是改名
         */
        System.out.println(context.getBean(Cat.class));
        System.out.println(context.getBean(Dog.class));
        System.out.println(context.getBean(Person.class));
        System.out.println(context.getBean(User.class));
    }

    // 测试yaml文件与properties文件相关语法
    public static void test3(ApplicationContext context){
        System.out.println(context.getBean(People.class));
    }

}
