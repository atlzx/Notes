
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
        public void sayHello(){
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
            user.sayHello();  // 尝试调用该类对象内声明的方法
        }
    }
~~~

+ 我们创建的`ApplicationContext`对象会加载对应的user.xml文件，获取内部对应的标签的属性
+ 接下来根据类的全路径，创建该类的无参对象
+ 创建完的对象会放在Map中，其key是该类的唯一标识，value是类的信息描述信息

---

### （二）log4j2

+ **Apache Log4j2**是一个开源的日志记录组件，使用非常的广泛，它由几个重要的组件构成
  + 日志信息的优先级:日志信息的优先级从高到低有TRACE < DEBUG < INFO < WARN < ERROR < FATAL，**设置高优先级的输出会默认屏蔽低优先级的输出**
    + **TRACE**：追踪，是最低的日志级别，相当于追踪程序的执行
    + **DEBUG**：调试，一般在开发中，都将其设置为最低的日志级别
    + **INFO**：信息，输出重要的信息，使用较多
    + **WARN**：警告，输出警告的信息
    + **ERROR**：错误，输出错误信息
    + **FATAL**：严重错误
  + 日志信息的输出地
  + 日志信息的输出格式
+ 首先引入依赖

~~~xml

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j2-impl -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j2-impl</artifactId>
        <version>2.20.0</version>
    </dependency>

~~~

+ 接下来在resources目录下创建一个叫`log4j2.xml`的文件，**名字不能修改**，在里面写入如下内容:

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
    configuration有两个可选属性
        status用于指定日志框架本身输出的日志优先级，可以修改为DEBUG
        monitorInterval用于指定自动加载配置文件的间隔时间，单位为秒

-->
<configuration>

    <!-- properties内可以声明一些变量，标签体内的值就是变量的值，标签体的name就是变量名，使用时需要使用${name}嵌入 -->
    <properties>
        <property name="LOG_PATH">E:\大学文件\笔记类\各学科笔记\java\源码\Spring</property>
    </properties>


    <loggers>
        <!--
            level指定日志级别，从低到高的优先级：
                TRACE < DEBUG < INFO < WARN < ERROR < FATAL
                trace：追踪，是最低的日志级别，相当于追踪程序的执行
                debug：调试，一般在开发中，都将其设置为最低的日志级别
                info：信息，输出重要的信息，使用较多
                warn：警告，输出警告的信息
                error：错误，输出错误信息
                fatal：严重错误
        -->
        <!-- 设置优先级为DEBUG,TRACE将被忽略 -->
        <root level="DEBUG">
            <appender-ref ref="spring6log"/>  <!-- 这里的ref属性对应着下面标签的name属性 -->
            <appender-ref ref="RollingFile"/>
            <appender-ref ref="log"/>
        </root>
    </loggers>

    <appenders>
        <!--输出日志信息到控制台-->
        <!--
            name与上面标签的ref需要一致
            target值为SYSTEM_OUT时，输出黑色，SYSTEM_ERR输出红色
        -->
        <console name="spring6log" target="SYSTEM_OUT">
            <!--控制日志输出的格式-->
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
        </console>

        <!--文件会打印出所有信息，该log在每次test时都会被覆写，而不是追加-->
        <File name="log" fileName="${LOG_PATH}\logFile\testLog\test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>

        <!--
            fileName表示要将日志文件存入的文件夹，使用${name}来嵌入之前声明的变量
            filePattern指定日志文件的名称格式
        -->
        <RollingFile name="RollingFile" fileName="${LOG_PATH}\logFile\minLog\log"
                     filePattern="${LOG_PATH}\logFile\bigLog\$${date:yyyy-MM}\app-%d{MM-dd-yyyy}-%i.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd 'at' HH:mm:ss z} %-5level %class{36} %L %M - %msg%xEx%n"/>
            <!-- 如果堆存起来的日志信息超过了50MB，那么把它们放入指定的文件夹内 -->
            <SizeBasedTriggeringPolicy size="50MB"/>
            <!-- DefaultRolloverStrategy属性如不设置，
            则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
    </appenders>
</configuration>
~~~

+ 接下来使用Maven进行一下测试，就可以看到控制台输出了打印信息，同时也在对应路径下创建了日志文件
+ 我们也可以手动的输出日志:

~~~java
    // 这里列出我们需要导入的类，不要导错了
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    @Test
        public void test2(){
            Logger logger= LoggerFactory.getLogger(User.class);
            logger.debug("调试");
            logger.info("输出信息");
            logger.error("出现错误");
            logger.trace("跟踪");
            logger.warn("警告");
        }
~~~

---

## 三、IOC

### （一）概述

+ `IOC(Inverse of Control)`指控制反转，它是一种思想，它可以降低程序的耦合度，并提高程序扩展力
+ 反转，指我们**将对象创建和对象与对象之间关系维护的权力交给第三方容器管理**，这里的第三方容器就是Spring
+ 控制反转通过**依赖注入(DI,Dependency Injection)**实现，依赖注入有两种实现方式
  + set注入
  + 构造注入

---

### （二）IOC容器

+ Spring的IOC容器是Spring对IOC思想的实现，在Spring的容器中，它管理的组件被称为`Bean`
+ 我们在创建Bean之前，需要先创建容器对象，Spring为我们提供了两种方式来创建容器对象:
  + 使用底层的`BeanFactory`创建
  + 使用具有更多高级特性，且是BeanFctory的子接口的`ApplicationContext`创建
+ ApplicationContext接口的实现类包括
![ApplicationContext接口与其实现类](../文件/图片/Spring图片/ApplicationContext接口与其实现类.png)

|实现类|作用|
|:---:|:---:|
|ClassPathXmlApplicationContext|通过读取类路径下的 XML 格式的配置文件创建 IOC 容器对象|
|FileSystemXmlApplicationContext|通过文件系统路径读取 XML 格式的配置文件创建 IOC 容器对象|
|ConfigurableApplicationContext|ApplicationContext的子接口，包含一些扩展方法 refresh() 和 close() ，让 ApplicationContext 具有启动、关闭和刷新上下文的能力。|
|WebApplicationContext|专门为 Web 应用准备，基于 Web 环境创建 IOC 容器对象，并将对象引入存入 ServletContext 域中。|

---

### （三）xml文件管理Bean

#### ①xml标签详解

~~~xml

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!-- id属性用来指定该bean标签的唯一标识，class属性用来指定该bean标签对应的类的全类名 -->
        <bean id="helloWorld" class="com.spring.sample.HelloWorld"></bean>
        <bean id="user" class="com.spring.sample.User"></bean>
    <!--    <bean id="user1" class="com.spring.sample.User"></bean>   多个id指向一个类，使用getBean(Class<T> requiredType)方法时会报错      -->
    <!-- 如果id指向的类是该xml文件中某个接口的唯一实现类，那么通过传入接口的Class对象来得到对应的实现类对象时，加载的就是该类 -->
        <bean id="interface1" class="com.spring.sample.GetBeanSampleImpl2"></bean>






        <bean id="xxx" class="xxxxx">
            <!-- 
                property标签用来进行属性值的注入，是setter注入的相关标签
                    name属性需要以该类中的某个属性名一致
                    value属性表示初始要赋给该对象的值，它只能注入普通的值，而且它本质上注入的是字符串。他设置的是我们刚得到对象时，对象的该属性的初始值
                使用property标签需要类实现setter方法
             -->
            <property name="yyyy" value="vvvv"></property>

            <property>
                <null />  <!-- null标签用于表示该属性值是空值，无法使用value属性置空，因为value传递的是字符串 -->
            </property>

            <!-- 
                constructor-arg标签用来进行属性值的注入，是构造器注入的相关标签
                    其value属性表示依次向构造器传递的值
                    index属性表示设置的value在构造器内从左到右的索引位置(从0开始)，这是一个可选属性值
                    name属性与property的name属性一致
             -->
            <constructor-arg value="vvvv" index="number" name="nnn"></constructor-arg>

            <value></value>
            <null />  <!-- 使用null自结束标签来 -->
        </bean>
    </beans>

~~~

---

#### ②常用方法

|所属类|方法/构造器|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|ClassPathXmlApplicationContext|public ClassPathXmlApplicationContext(String configLocation)|configLocation:想读取的xml相对于resources的路径|根据xml文件生成容器对象|容器对象|ClassPathXmlApplicationContext|无异常|无|[样例]()|
|BeanFactory|Object getBean(String name)|name:xml文件内标签中对应类路径的id|得到指定id对应的类对象|指定类对象|Object|BeansException|无|^|
|^|<T> T getBean(Class<T> requiredType)|requiredType:想得到类对应Class对象|得到指定的类对象|^|^|^|无|^|
|^|<T> T getBean(String name, Class<T> requiredType)|name:xml文件内标签中对应类路径的id<br>requiredType:想得到类对应Class对象|得到指定的类对象|^|^|^|无|^|

---

#### ③创建类对象

+ 可以通过`getBean`方法得到指定的类对象，Spring为我们重载了多个该方法，方便我们调用
  + getBean(String name)
  + getBean(Class<T> requiredType)
  + getBean(String name, Class<T> requiredType)


~~~xml
    <bean id="user1" class="com.spring.test.User"></bean>
    <bean id="user2" class="com.spring.test.User"></bean>
    <!-- 这样会报错: -->
~~~

+ **注意**:
> + 如果同一个类在xml文件中被两个id指向，那么使用第二个方法时会报错
> + 如果直接传入接口的Class对象来获取指定的类对象，此时**如果我们的xml文件内仅声明了一个该接口的实现类，那么就会创建该类的对象**。如果声明了多个，会报错

+ [样例1](../源码/Spring/SpringTest1/src/test/java/com/spring/test/UserTest.java)
+ [样例2](../源码/Spring/SpringTest1/src/test/java/com/spring/test/GetBeanTest.java)

---

#### ④依赖注入

+ 上面的获取类对象都是通过调用空参的构造函数得到的对象，如果我们想得到有参的对象的话，我们就需要用到**依赖注入**了
+ 依赖注入分为两种
  + 






