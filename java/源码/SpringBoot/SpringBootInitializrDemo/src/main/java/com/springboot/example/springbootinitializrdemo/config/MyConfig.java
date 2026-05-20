package com.springboot.example.springbootinitializrdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.example.springbootinitializrdemo.exception.FastSqlException;
import com.springboot.example.springbootinitializrdemo.pojo.Cat;
import com.springboot.example.springbootinitializrdemo.pojo.Child;
import com.springboot.example.springbootinitializrdemo.pojo.Dog;
import com.springboot.example.springbootinitializrdemo.pojo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration  // 声明类为配置类，配置类也会纳入IOC容器管理，其名称默认以类名的小驼峰命名法来命名
@EnableConfigurationProperties(value = {Dog.class})  // 该注解用于指定属性绑定类，同时被该注解内的属性所指定的类无需再写@Component之类的注解，会自动加入IOC容器
@Import(value = {DruidDataSource.class})  // 导入Druid第三方依赖
public class MyConfig {

//    @Bean  // 使用bean来使方法返回值加入到IOC容器内，如果不指定@Bean注解的name属性，那么bean的默认名称与方法名一致
//    @Scope(value = "prototype")  // 指定生成的bean是多例，默认是singleton
//    public DataSource DruidDataSource(){
//        return new DruidDataSource();
//    }

    // 该注解需要传入的是指定类的全类名，当然它可以传Class对象
    @ConditionalOnClass(name = "com.springboot.example.springbootinitializrdemo.exception.FastSqlException")
    @Bean
    public Cat cat01(){
        System.out.println();
        return new Cat();
    }

    // 该注解需要传入的是指定类的全类名
    @ConditionalOnMissingClass(value = "com.springboot.example.springbootinitializrdemo.exception.FastSqlException")
    @Bean
    public Dog dog01(){
        return new Dog();
    }

    // 该注解可以传Class对象
    @ConditionalOnBean(value = Dog.class)
    @Bean
    public User zhangsan(){
        return new User();
    }

    // 该注解可以传Class对象
    @ConditionalOnMissingBean(value = Dog.class)
    @Bean
    public User lisi(){
        return new User();
    }

}
