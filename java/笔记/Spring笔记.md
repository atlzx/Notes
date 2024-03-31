
# Spring笔记

## 一、概述

+ Spring是一款主流的JavaEE轻量级开源框架 ，它简化了Java企业级应用的开发难度和开发周期
+ 广义上的 Spring 泛指以 Spring Framework 为核心的 Spring 技术栈
+ 狭义上，Spring特指Spring Framework，他有两个核心模块:
  + **IOC(Inverse of Control)**:控制反转，即把我们创建对象的过程交给Spring处理
  + **AOP(Aspect Oriented Programming)**:面向切面编程，它用来封装多个类的公共行为，减少系统的重复代码，降低耦合度
+ Spring有如下特点:
  + **非侵入式**:Spring对应用程序本身的结构影响非常小，不会破坏原有结构，反而能将组件结构进一步简化
  + **控制反转**:翻转资源获取方向。把自己创建资源、向环境索取资源变成环境将资源准备好，我们享受资源注入。
  + **面向切面编程**:在不修改源代码的基础上增强代码功能
  + **容器化管理**:Spring IoC 是一个容器，因为它**包含并且管理组件对象的生命周期**。组件享受到了容器化的管理
  + **组件化**:Spring中可以使用 XML 和 Java 注解组合对象，实现使用简单的组件配置组合成一个复杂的应用
  + 一站式:可以整合各种企业应用的开源框架和优秀的第三方类库

---

## 二、入门

### （一）HelloWorld

+ 首先创建一个父项目，再创建其子项目，在pom.xml内引入依赖:

~~~xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.5</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api -->
    <!-- 引用junit方便我们随时测试代码 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>


</dependencies>
~~~

+ 接下来在`main`文件夹内创建一个Java类，写一些代码

~~~java

    package com.spring.sample;

    public class User {
        public void salHello(){
            System.out.println("你好");
            System.out.println("HelloWorld");
        }
    }

~~~

+ 在`resources`文件夹下创建一个xml文件，名字随便起，加入下面一句标签:

~~~xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 下面这个bean标签是我们要加的标签 -->
    <!-- id属性用来指定该bean标签的唯一标识，class属性用来指定该bean标签对应的类的全类名 -->
    <bean id="user" class="com.spring.sample.User"></bean>
</beans>

~~~

+ 在`test`文件夹下创建测试类，测试代码

~~~java
    package com.spring.test;

    import com.spring.sample.User;
    import org.junit.jupiter.api.Test;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class UserTest {
        @Test
        public void test1(){
            // 创建一个ApplicationContext对象，传入的参数是对应xml文件的名称
            ApplicationContext context=new ClassPathXmlApplicationContext("user.xml");
            // 使用getBean得到对应xml文件配置的指定类对象，即传入xml文件内标签的id属性.
            // 该方法返回的是Object类型对象，因此强转成User类型对象
            User user = (User)context.getBean("user");
            System.out.println("输出得到的user:"+user);  // 输出，检验是否为该类对象
            user.salHello();  // 尝试调用该类对象内声明的方法
        }
    }
~~~

---