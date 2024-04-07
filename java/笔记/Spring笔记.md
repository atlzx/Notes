
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

## 二、HelloWorld

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
    <!-- 
        下面引用了util、context和p命名空间 
        p命名空间的引入不需要更新xsi:schemaLocation对应的链接
    -->
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:util="http://www.springframework.org/schema/util"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:context="http://www.springframework.org/schema/context"  
        xsi:schemaLocation="http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util 
        http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context.xsd
        ">



        <!-- 使用import标签用来导入其它xml文件，import标签是自带的，不用外部导入 -->
        <import resource="dependencyInject2.xml" />
        <!-- 使用context:property-placeholder来导入外部属性文件 -->
        <context:property-placeholder location="jdbc.properties" />



        <!-- 
            id 属性用来指定该bean标签的唯一标识
            class 属性用来指定该bean标签对应的类的全类名
            name 属性和id属性的作用是一样的
            lazy-init 用来表示是否懒加载，默认是false，设置为true会使该对象在我们真正调用getBean方法时才创建
            depends-on 用来设置每个Bean在IOC容器内的加载顺序，因为我们不确定IOC怎么加载的对象，因此可以设置上该属性以确保其加载顺序可控，它的作用是IOC在加载对象时，先加载其depends-on属性设置的bean，这样该bean就一定会在其设置的depends-on的bean加载完成后再进行加载
            scope 用来设置bean的作用域
                它的默认值是singleton，在该模式下，每次我们得到的对应bean对象都是同一个，也就是说，它是单例的
                设置为prototype可以使我们每次获取到的bean都是不同的对象，从而摆脱单例模式
            autowire 可以用来设置自动装配，即让Spring在xml文件内自己寻找符合条件的值进行依赖注入，它有下面的一些属性可选:
                byType:根据类型进行自动装配，它会寻找xml文件内符合自己属性所需的类型的bean。如果发现了多个，会报错。它需要类提供setter方法
                byName:根据名称进行自动装配，他会寻找xml文件内id或name属性与自己类的属性一致的bean。它需要类提供setter方法
                constructor:根据构造器进行依赖注入，默认是依据的byType，它需要类提供构造器
            autowire-candidate 用来设置该bean能否被自动装配识别，置为false表示该bean不参与到自动装配中去，可以避免重复的自动装配冲突
            p:xxx p命名空间支持我们不用编写property标签的方式来对属性赋值，p: 的后面需要直接加属性名来指定为哪个属性赋值，它通过类的setter方法进行赋值。如果是引用数据类型，可以通过p:xxx-ref 进行赋值
         -->
        <bean id="helloWorld" class="com.spring.sample.HelloWorld" name="xxx" lazy-init="true" scope="prototype" autowire="byType" p:name="xx"></bean>
        <bean id="user" class="com.spring.sample.User" depends-on="helloWorld"></bean>
    <!--    <bean id="user1" class="com.spring.sample.User"></bean>   多个id指向一个类，使用getBean(Class<T> requiredType)方法时会报错      -->
    <!-- 如果id指向的类是该xml文件中某个接口的唯一实现类，那么通过传入接口的Class对象来得到对应的实现类对象时，加载的就是该类 -->
        <bean id="interface1" class="com.spring.sample.GetBeanSampleImpl2"></bean>





        <!-- 使用util可以在xml文件内生成一个集合对象，这样在依赖注入时就能通过其id直接引用了 -->
        <util:map id="xxx">
            <entry key="key" value="value">
        </util:map>






        <!--
            context:component-scan标签用于开启组件扫描功能
                base-package用于指定扫描的路径，默认会递归的扫描该包下的所有内部文件
                use-default-filters用于设置是否递归的扫描该包下的所有内部文件，设置为false说明不这样做，不写是true
        -->
        <context:component-scan base-package="com.atguigu.spring6" use-default-filters="false">
            <!-- 
                context:exclude-filter标签用来指定基本包下我们不想让它扫描的路径，这里的含义是:
                    递归扫描(use-default-filters="false"是为了示范context:include-filter标签而设置的，这里请无视)com.atguigu.spring6包下的的所有文件，但不扫描org.springframework.stereotype.Controller
 		            type 属性用于设置排除或包含的依据
		                type="annotation"，根据注解排除，expression中设置要排除的注解的全类名
		                type="assignable"，根据类型排除，expression中设置要排除的类型的全类名
	        -->
            <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
            <!-- 
                context:include-filter标签：指定在原有扫描规则的基础上追加的规则，这里的含义是:
                    仅扫描org.springframework.stereotype.Controller
                    use-default-filters属性：取值false表示关闭默认扫描规则
                    此时必须设置use-default-filters="false"，因为默认规则即扫描指定包下所有类
 	        	    type 属性用于设置排除或包含的依据
	        	        type="annotation"，根据注解排除，expression中设置要排除的注解的全类名
	        	        type="assignable"，根据类型排除，expression中设置要排除的类型的全类名
	        -->
            <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>






        <bean id="xxx" name="" class="xxxxx" >


            <!-- 
                property标签用来进行属性值的注入，是setter注入的相关标签，他需要对应类实现其属性的setter方法
                    name属性需要以该类中的某个属性名一致
                    value属性表示初始要赋给该对象的值，它只能注入普通的值，而且它本质上注入的是字符串。他设置的是我们刚得到对象时，对象的该属性的初始值.
                    指定了value属性以后，就不要再向标签里面继续写赋值的标签了
                    ref属性用来注入引用数据类型对象，它的值是其它bean的id值，它是注入引用数据类型对象的第一种方式
                使用property标签需要类实现setter方法
             -->

            <!-- 如果想注入的值是xml实体，那么注入的第一种方式是使用字符实体在value属性内直接插入，字符实体详见html+css笔记 -->
            <property name="yyyy" value="a &nbsp; b" ref="id">
                <!-- null标签用于表示该属性值是空值，无法使用value属性置空，因为value传递的是字符串 -->
                <null />  
                <!-- 注入xml实体的第二种方式是把想注入的值写在 <![CDATA[]]> 最里面的括号内，如下例，注入的值是 a < b -->
                <value><![CDATA[a < b]]></value>
                

                <!-- 在property内插入bean标签意味着向该属性注入bean标签所映射的类的对象，这是注入引用数据类型对象的第二种方式 -->
                <bean class="com.test.xxx">
                    <property></property>
                    <property></property>
                    <property></property>
                </bean>
                <!-- 使用array标签来向数组类型的属性注入值 --> 
                <array>
                    <value>抽烟</value>
                    <value>喝酒</value>
                    <value>烫头</value>
                    <ref bean="xxxx"></ref>
                </array>

                <!-- 使用list标签来向list类型的属性注入值 -->  
                <list>
                    <value>xxx</value>
                    <value>yyy</value>
                    <ref bean="xxxx"></ref>
                </list>
                <!-- 使用map标签来向map类型的属性注入值 -->
                <map>
                    <!-- 可以直接使用属性赋值 -->
                    <!-- 
                        entry标签有四个属性
                            key属性用来赋值基本的数据值
                            key-ref属性用来赋值引用数据类型的对应key值
                            value属性用来赋值基本的数据值
                            value-ref属性用来赋值引用数据类型的对应value值
                     -->
                    <entry key="test1" value-ref="interface1" ><entry>
                    <!-- 也可以使用标签嵌套的方式赋值 -->
                    <entry>
                        <key>
                            <value>xxx</value>
                        </key>
                        <ref bean="yyyy"></ref>
                    </entry>
                </map>

            </property>
            <!-- 让property标签内的name= 上面的映射引用数据类型的property标签内的 name值.类属性名 的方式 ，可以用value修改其对应属性值的初始值-->
            <property name="yyyy.zz" value="xxxx"></property>






            <!-- 
                constructor-arg标签用来进行属性值的注入，是构造器注入的相关标签
                    其value属性表示依次向构造器传递的值
                    index属性表示设置的value在构造器内从左到右的索引位置(从0开始)，这是一个可选属性值
                    name属性与property的name属性不是一致的，这个name属性是跟着构造器的参数名来的，而不是类内的属性名
                    type属性的作用是当存在多个重载的且形参列表数量相同的参数时，用来具体区分其参数类型以确定最终调用哪个构造器
                    name和index属性是不可以混用的，但是混用可能不会报错，虽然IDEA会报错，但是Spring貌似会以某种方式正常的创建对象
                    推测是先把index对应的value赋上，再根据有name属性的constructor-arg在xml文件内的声明顺序依次赋值，越靠上面越先赋值(不确定)
             -->
            <constructor-arg value="vvvv" index="number" name="nnn" type="int"></constructor-arg>

        </bean>
    </beans>

~~~

|标签|作用|备注|
|:---:|:---:|:---:|
|bean|指定类的bean映射，一个bean即代表一个类对象的配置|无|
|property|用来指定对应类对象内属性的值，它是setter注入的相关标签|无|
|constructor-arg|用来指定对应类对象内属性的值，它是构造器注入的相关标签|无|
|value|用来指定一些基础值|无|
|ref|用来指定一些引用数据类型的赋值|无|
|array|表示数组的依赖注入|无|
|list|表示List类型的依赖注入|无|
|map|表示map类型的依赖注入|无|
|entry|表示map的一个键值对|无|

---

#### ②常用方法

|所属类|方法/构造器|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|ClassPathXmlApplicationContext|public ClassPathXmlApplicationContext(String configLocation)|configLocation:想读取的xml相对于resources的路径|根据xml文件生成容器对象|容器对象|ClassPathXmlApplicationContext|无异常|无|[样例](../源码/Spring/SpringTest1/src/test/java/com/spring/test/GetBeanTest.java)|
|BeanFactory|Object getBean(String name)|name:xml文件内标签中对应类路径的id|得到指定id对应的类对象|指定类对象|Object|BeansException|无|^|
|^|<T> T getBean(Class<T> requiredType)|requiredType:想得到类对应Class对象|得到指定的类对象|^|^|^|**如果对应类实现了接口，那么方法接收的是接口的Class对象**|^|
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

+ [xml文件](../源码/Spring/SpringTest1/src/main/resources/dependencyInject1.xml)
+ [样例1](../源码/Spring/SpringTest1/src/test/java/com/spring/test/UserTest.java)
+ [样例2](../源码/Spring/SpringTest1/src/test/java/com/spring/test/GetBeanTest.java)

---

#### ④依赖注入

+ 上面的获取类对象都是通过调用空参的构造函数得到的对象，如果我们想得到有参的对象的话，我们就需要用到**依赖注入**了
+ 依赖注入分为两种
  + setter注入:Spring通过类定义的setter方法对类对象的属性进行赋值
    + setter注入使用`property`标签进行赋值
  + 构造器注入:Spring通过类定义的构造器对类对象的属性进行赋值
    + 构造器注入使用`constructor-arg`标签进行赋值
+ 上述标签拥有value属性可以赋基本类型的值，而其拥有的ref属性可以赋引用数据类型的值，但是依然有一些值需要额外注意:
  + 数组类型的值，需要使用`array`标签包裹
  + List类型的值，需要使用`list`标签包裹
  + Map类型的值，需要使用`map`标签包裹，且每个map元素需要使用entry表示
+ 此外，util可以让我们独立于bean在xml内创建一组集合对象，便于ref引用
+ p命令空间支持直接通过`p:属性名`的方式来作为bean标签的属性给该类对象的属性赋值，而不需要写property标签
+ context可以使我们引入外部的文件，如果**想使用其他xml文件，请使用自带的import标签**
+ 使用bean标签自带的autowire和autowire-candidate属性用来指定进行**自动装配**和使对应的bean对象不参与自动装配
+ [样例](../源码/Spring/SpringTest1/src/test/java/com/spring/test/DependencyTest.java)
+ [xml文件1](../源码/Spring/SpringTest1/src/main/resources/dependencyInject2.xml)
+ [xml文件2](../源码/Spring/SpringTest1/src/main/resources/dependencyInject3.xml)
+ [xml文件3](../源码/Spring/SpringTest1/src/main/resources/dependencyInject4.xml)


---

#### ⑤作用域与生命周期

+ 每个IoC容器的bean都有其作用域，它的作用域默认是singleton，即**单例的，且对象在IoC容器初始化时就被创建**
  + 使用scope属性可以修改指定bean的作用域，修改为prototype可以使**每次通过IoC容器获取对象时得到的对象都是新对象，且对象在获取bean时才被创建**
  + 如果在WebApplicationContext环境下还会有另外几个作用域:request(单次请求有效)和session(会话范围内有效)
+ 每个bean也有其生命周期
  1. bean对象的创建
  2. 给bean对象的属性赋值
  3. 调用bean的后置处理器
  4. bean对象初始化
  5. 调用bean的后置处理器
  6. bean对象就绪
  7. bean对象销毁
  8. IoC容器关闭
+ 使用init-method和destroy-method属性可以指定初始化时执行的方法(第四步)和销毁时执行的方法(第七步)
+ 后置处理器用来在bean对象初始化前后执行额外的操作，**后置处理器需要我们自己手动写**:
  + 首先定义一个类，实现BeanPostProcessor接口，并实现接口的方法
  + 在xml文件内引入以使Spring检测到
  + 这样就能使了
+ [样例](../源码/Spring/SpringTest1/src/test/java/com/spring/test/SALCTest.java)
+ [xml样例](../源码/Spring/SpringTest1/src/main/resources/SALC.xml)

---

#### ⑥FactoryBean

+ FactoryBean是Spring提供的一种整合第三方框架的常用机制，它可以帮我们把复杂组件创建的详细过程和繁琐细节都屏蔽起来，只把最简洁的使用界面展示给我们
+ **FactoryBean和BeanFactory不是一个东西**
+ 想要使用FactoryBean，我们需要做如下操作:
  + 创建一个类，实现FactoryBean接口，并指定其对应的想生产的泛型对象。同时实现方法
  + 在xml文件内引入该实现类，让Spring能够检测到它
  + 调用getBean方法得到对象，传入实现类的bean的id，可以看到最终得到的是实现类指定的泛型类对象
+ [样例](../源码/Spring/SpringTest1/src/test/java/com/spring/test/FactoryBeanTest.java)
+ [xml样例](../源码/Spring/SpringTest1/src/main/resources/factoryBean.xml)

---

### （四）注解管理bean

+ 使用xml配置bean在配置大型项目时会变得繁琐，且非常不便捷
+ 为解决这一问题，Spring在2.5版本引入了注解来配置bean

|注解|作用|备注|样例|
|:---:|:---:|:---:|:---:|
|@Configuration|声明作用类为配置类，免除xml文件内配置扫描注解路径|无|[样例](../源码/Spring/SpringAnnotationSample/src/test/java/com/test/AnnotationInjectTest.java)|
|@ComponentScan|配置扫描注解路径|无|^|
|@Bean|用于配置一个bean对象，对应xml文件内的一个bean标签。|无|^|
|@Lazy|指定是否懒加载|无|^|
|@DependOn|指定依赖加载对象|无|^|
|@Scope|指定作用域|无|^|
|@PreDestroy|指定销毁方法|该注解来源于`jakarta`包|^|
|@PostConstruct|指定初始化方法|^|^|
|@Component|将类定义为一个bean对象|它可以作用于任何层|^|
|@Service|^|作用于Service层|^|
|@Controller|^|作用于Controller层|^|
|@Repository|^|作用在Dao层|^|
|@Value|注入基本数据类型对象|无|^|
|@Autoware|根据byType模式匹配对应的引用数据类型对象并注入|该注解无法自动装配JDK自带的数据类型|^|
|@Qualifier|使自动装配按照byName的方式匹配，且依据的是该注解内指定的name值进行匹配|无|^|
|@Resource|依据 指定的name -> byName -> byType的模式依次匹配对应的引用数据类型对象并注入|该注解来源于`jakarta`包|^|

+ 如果使用了配置类，那么**获取IoC容器对象的类就从ClassPathXmlApplicationContext变到了AnnotationConfigApplicationContext**，并需要向其构造器内传入配置类的Class对象
+ 在未使用配置类的情况下，仍需要使用xml文件进行配置:
  + xml需要引入context相关约束

~~~xml

    <?xml version="1.0" encoding="UTF-8"?>
    <!-- 需要引入context相关约束 -->
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/context
                http://www.springframework.org/schema/context/spring-context.xsd">
        <!--
            context:component-scan标签用于开启组件扫描功能
                base-package用于指定扫描的路径，默认会递归的扫描该包下的所有内部文件
                use-default-filters用于设置是否递归的扫描该包下的所有内部文件，设置为false说明不这样做，不写是true
        -->

        <context:component-scan base-package="com.atguigu.spring6" use-default-filters="false">
            <!-- 
                context:exclude-filter标签用来指定基本包下我们不想让它扫描的路径，这里的含义是:
                    递归扫描(use-default-filters="false"是为了示范context:include-filter标签而设置的，这里请无视)com.atguigu.spring6包下的的所有文件，但不扫描org.springframework.stereotype.Controller
 		            type 属性用于设置排除或包含的依据
		                type="annotation"，根据注解排除，expression中设置要排除的注解的全类名
		                type="assignable"，根据类型排除，expression中设置要排除的类型的全类名
	        -->
            <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
            <!-- 
                context:include-filter标签：指定在原有扫描规则的基础上追加的规则，这里的含义是:
                    仅扫描org.springframework.stereotype.Controller
                    use-default-filters属性：取值false表示关闭默认扫描规则
                    此时必须设置use-default-filters="false"，因为默认规则即扫描指定包下所有类
 	        	    type 属性用于设置排除或包含的依据
	        	        type="annotation"，根据注解排除，expression中设置要排除的注解的全类名
	        	        type="assignable"，根据类型排除，expression中设置要排除的类型的全类名
	        -->
            <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>
    </beans>

~~~

---

### （五）最基本的IoC容器实现

+ [ApplicationContext接口](../源码/Spring/CreateIoC/src/main/java/com/spring/sample/ApplicationContext.java)
+ [ApplicationContext实现类](../源码/Spring/CreateIoC/src/main/java/com/spring/sample/AnnotationApplicationContext.java)
+ [Bean注解](../源码/Spring/CreateIoC/src/main/java/com/spring/sample/Bean.java)
+ [DependencyInject注解](../源码/Spring/CreateIoC/src/main/java/com/spring/sample/DependencyInject.java)
+ [测试样例](../源码/Spring/CreateIoC/src/test/java/com/test/AnnoTest.java)

---

## 四、AOP

+ AOP指面向切片编程,它是通过预编译方式和运行期动态代理方式实现的，在不修改源代码的情况下，给程序动态统一添加额外功能的一种技术
+ AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

### （一）概念术语

+ **横切关注点**:分散在每个各个模块中解决同一样的问题，如用户验证、日志管理、事务处理、数据缓存都属于
+ **通知**:就是我们想添加的额外功能，通知分为五种类型:
  + 前置通知：在被代理的**目标方法执行前执行**
  + 返回通知：在被代理的**目标方法执行完毕后执行**
  + 异常通知：在被代理的**目标方法出现异常时执行**
  + 后置通知：该通知方法一定**在最后执行**
  + 环绕通知：使用try...catch...finally结构围绕整个被代理的目标方法，包括上面四种通知对应的所有位置
+ **切面**:封装通知方法的类
+ **目标**:被代理的目标对象。
+ **代理**:作为目标的代理的代理对象
+ **连接点**:可以理解为通知与方法执行的衔接处，即spring允许我们使用通知的地方

---

### （二）切入点表达式

+ 切入点表达式用来描述该通知的作用范围，其具体语法格式如下:`修饰符 返回值 包名.类名.方法名(形参类型列表)`
  + 权限修饰符与返回值:
    + 用*号代替“权限修饰符”和“返回值”部分表示“权限修饰符”和“返回值”不限
    + **如果想要明确指定一个返回值类型，那么必须同时写明权限修饰符**
      + 例如：execution(public int *..*Service.*(.., int))	正确
      + 例如：execution(* int *..*Service.*(.., int))	错误
  + 包、类与方法:
    + 一个`*`号只能代表**匹配包的任意一层**
      + 例如：*.Hello匹配com.Hello，不匹配com.atguigu.Hello
    + 使用`*..`表示**匹配包名任意、包的层次深度任意**
    + 可以使用*号表示类名或方法名任意，也可以表示匹配类名或方法名的一部分
      + 例如：*Service匹配所有名称以Service结尾的类或接口
      + 例如：*Operation匹配所有方法名以Operation结尾的方法
  + 形参类型列表:
    + 使用`..`表示参数列表任意
    + 使用`int,..`表示参数列表以一个int类型的参数开头
    + **基本数据类型和对应的包装类型是不一样的**
      + 切入点表达式中使用 int 和实际方法中 Integer 是不匹配的
+ **可复用的切入点表达式**
  + 一个一个配置切入点表达式会非常麻烦，因此我们希望切入点表达式像方法一样可以被复用
  + 使用@PointCut注解，在注解内配置value的值为切入点表达式，可以使切入点表达式被复用。该注解作用在方法上，可以写一个空方法，使该注解作用在其上面
    + 如果是同类下，直接调用即可:`@Around(value = "pointCut()")`
    + 如果是不同类下，需要使用全类名:`@Before(value = "com.spring.sample.AOPAnnoSample.pointCut()")`
  + 使用xml配置时，需要使用`<aop:pointcut id="xxx" expression="..." />`标签，其它标签使用时，仅需要使用pointcut-ref属性指定其标签上的id即可

---

### （三）注解配置

|注解|作用|备注|
|:---:|:---:|:---:|
|@EnableAspectJAutoProxy|使Spring自动创建代理支持AOP操作|**该注解发挥作用需要@Configuration注解**，因此，它必须与该注解一起放在配置类上|
|@Aspect|声明作用类为切面类|仅声明切面是不够用的，**需要加上@Component注解来使其受IoC容器管理**|
|@Before|声明方法为**前置通知方法**|无|
|@AfterReturning|声明方法为**返回通知方法**|无|
|@AfterThrowing|声明方法为**异常通知方法**|无|
|@After|声明方法为**后置通知方法**|无|
|@Around|声明方法为**环绕通知方法**|无|
|@PointCut|声明方法为切入点表达式复用方法|**仅能作用在方法上**|

+ [样例](../源码/Spring/AOPSample/src/main/java/com/spring/sample/AOPAnnoSample.java)
+ [测试样例](../源码/Spring/AOPSample/src/test/java/com/test/AOPTest.java)

---

### （四）xml配置

~~~xml

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
        <!-- 使Spring在对应路径下扫描相关注解 -->
        <context:component-scan base-package="com.spring.sample" />
        <!-- 使Spring自动代理，该标签是作用于注解的，相当于配置类的EnableAspectJAutoProxy注解。在这里配置只是写一下，说明xml文件如何支持注解 -->
    <!--    <aop:aspectj-autoproxy/>-->
        <!-- 配置通知 -->
        <aop:config>
            <!-- 配置AOPXmlSample是一个切面类 -->
            <aop:aspect ref="AOPXmlSample">
                
                <!-- aop:pointcut用来配置可复用的切入点表达式 -->
                <aop:pointcut id="pointCut" expression="execution(* com.spring.sample.ProxyInterfaceImpl.*(..))"/>

                <!-- 配置该类中的各方法的通知方法类型 -->
                <aop:before method="beforeMethod" pointcut="com.spring.sample.AOPAnnoSample.pointCut()" />
                <aop:after-returning method="afterReturningMethod" pointcut="com.spring.sample.AOPAnnoSample.pointCut()" returning="aa"/>
                <aop:after-throwing method="afterThrowing" pointcut="com.spring.sample.AOPAnnoSample.pointCut()" throwing="e" />
                <aop:after method="afterMethod" pointcut="com.spring.sample.AOPAnnoSample.pointCut()"/>
                <!-- 使用pointcut-ref便捷的使用可复用的切入点表达式 -->
                <aop:around method="aroundMethod" pointcut-ref="pointCut"/>
            </aop:aspect>
        </aop:config>

    </beans>

~~~

+ [样例](../源码/Spring/AOPSample/src/main/java/com/spring/sample/AOPXmlSample.java)
+ [测试样例](../源码/Spring/AOPSample/src/test/java/com/test/AOPTest.java)

---

## 五、配置汇总与杂项

### （一）依赖总览

~~~xml

<properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <spring.version>6.1.5</spring.version>
        <junit.version>5.10.2</junit.version>
        <log4j.version>2.20.0</log4j.version>
        <annotation.version>2.1.1</annotation.version>
    </properties>


    <dependencyManagement>
        <dependencies>
            <!-- Spring核心框架 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <!--spring aop依赖-->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aop</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <!--spring aspects依赖-->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <!-- spring junit兼容依赖 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-test</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <!-- junit依赖，用来测试 -->
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!-- log4j依赖，用来输出日志 -->
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-core</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <!-- log4j2，用来输出日志 -->
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-slf4j2-impl</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <!-- JDK注解拓展，有一些依赖注入的注解 -->
            <dependency>
                <groupId>jakarta.annotation</groupId>
                <artifactId>jakarta.annotation-api</artifactId>
                <version>${annotation.version}</version>
            </dependency>

        </dependencies>
    </dependencyManagement>

~~~

---

### （二）beans标签值

~~~xml
    <!-- 
        xmlns和xmlns:xsi是最基本的属性，在开始就有。
        xmlns:util支持在beans标签内直接写可复用的集合类对象。它需要在xsi:schemaLocation增加约束
        xmlns:p用来引入p命名空间，支持直接在bean标签内进行依赖注入，不需要增加约束
        xmlns:context用来支持Spring对注解进行扫描。它需要在xsi:schemaLocation增加约束
        xmlns:aop用来支持Spring的AOP操作。它需要在xsi:schemaLocation增加约束
        增加约束仅需要按照前面的约束，然后修改最后面的值为想增加的约束名称即可
     -->

    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:util="http://www.springframework.org/schema/util"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:context="http://www.springframework.org/schema/context"  
        xmlns:aop="http://www.springframework.org/schema/aop"
        xsi:schemaLocation="http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util 
        http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        ">

~~~

### （三）log4j2

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










