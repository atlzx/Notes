
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

### （二）ApplicationContext

#### ①实现类

+ Spring的IOC容器是Spring对IOC思想的实现，在Spring的容器中，它管理的组件被称为`Bean`
+ 我们在创建Bean之前，需要先创建容器对象，Spring为我们提供了两种方式来创建容器对象:
  + 使用底层的`BeanFactory`创建
  + 使用具有更多高级特性，且是BeanFctory的子接口的`ApplicationContext`创建
+ ApplicationContext接口的实现类包括
![ApplicationContext接口与其实现类](../文件/图片/Spring图片/ApplicationContext接口与其实现类.png)

|实现类|作用|
|:---:|:---:|
|ClassPathXmlApplicationContext|通过读取类路径下的 XML 格式的配置文件创建 IOC 容器对象|
|AnnotationConfigApplicationContext|通过读取配置类的Class对象创建IoC容器对象|
|FileSystemXmlApplicationContext|通过文件系统路径读取 XML 格式的配置文件创建 IOC 容器对象|
|ConfigurableApplicationContext|ApplicationContext的子接口，包含一些扩展方法 refresh() 和 close() ，让 ApplicationContext 具有启动、关闭和刷新上下文的能力。|
|WebApplicationContext|专门为 Web 应用准备，基于 Web 环境创建 IOC 容器对象，并将对象引入存入 ServletContext 域中。|

---

#### ②访问通配符

+ Spring允许我们通过通配符和前缀的方式，一次性读取多个xml文件
  + 如果仅使用`classpath:`，那么**仅会匹配到第一个符合后续条件的文件**
  + 使用`classpath*`来确定我们要加载类加载路径下**所有满足该规则的配置文件**
  + 在后续条件加上*,如`bean*.xml`，表示匹配以bean为前缀的文件
  + 二者可以混用:如`classpath*:bean*.xml`
+ [样例](../源码/Spring/Resources/src/test/java/ApplicationContextTest.java)
+ [样例拓展](../源码/Spring/SpringEL/src/test/java/PathTest.java)

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
  1. bean对象的创建:Spring会将所有可以加入IOC容器的对象加入到IOC容器内，包括但可能不限于:配置类对象、MVC三层类对象、**被@Bean注解作用的配置类内的方法返回的对象**...
  2. 给bean对象的属性赋值:在得到所有的对象后，Spring会批量给每个属性进行赋值,**如果@Bean注解作用的方法使用了其所在类下的被@Autowired注解作用的属性，此时由于属性赋值发生在bean对象创建之后，因此无法得到详细的值，只能得到null或属性的默认值**
  3. 调用bean的后置处理器
  4. bean对象初始化:执行初始化方法
  5. 调用bean的后置处理器
  6. bean对象就绪
  7. bean对象销毁:关闭IOC容器时，bean对象会在IOC容器关闭之前销毁
  8. IoC容器关闭
+ 使用init-method和destroy-method属性可以指定初始化时执行的方法(第四步)和销毁时执行的方法(第七步)
+ 后置处理器用来在bean对象初始化前后执行额外的操作，**后置处理器需要我们自己手动写**:
  + 首先定义一个类，实现BeanPostProcessor接口，并实现接口的方法
  + 在xml文件内创建一个bean来表示该类的实例
  + 这样就能使了
+ [样例](../源码/Spring/SpringTest1/src/test/java/com/spring/test/SALCTest.java)
+ [xml样例](../源码/Spring/SpringTest1/src/main/resources/SALC.xml)

---

#### ⑥FactoryBean

+ FactoryBean是Spring提供的一种整合第三方框架的常用机制，它可以帮我们把复杂组件创建的详细过程和繁琐细节都屏蔽起来，只把最简洁的使用界面展示给我们
+ **FactoryBean和BeanFactory不是一个东西**
+ 想要使用FactoryBean，我们需要做如下操作:
  + 创建一个类，实现FactoryBean接口，并指定其对应的想生产的泛型对象。同时实现方法
    + **需要实现的有两个方法:getObject和getObjectType，一个返回bean的实例对象，一个返回bean的类型的Class对象**
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
|@Bean|用于配置一个bean对象，对应xml文件内的一个bean标签。|**1.被该注解作用的方法执行早于被@Value注解作用的属性注入赋值**<br>2.**如果不指定name属性，那么作用方法返回的bean名称与方法名一致**|^|
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
|@Autoware|根据byType模式匹配对应的引用数据类型对象并注入|1.该注解无法自动装配JDK自带的数据类型<br>**不能作用于测试类**|^|
|@Qualifier|使自动装配按照byName的方式匹配，且依据的是该注解内指定的name值进行匹配|无|^|
|@Resource|依据 指定的name -> byName -> byType的模式依次匹配对应的引用数据类型对象并注入|1.该注解来源于`jakarta`包<br>2.**不能作用于测试类**|^|

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
|@Order|指定Bean的初始化顺序/切面类的执行顺序|无|

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

### （五）切面优先级

+ 一个方法上面可能会被多个切面作用，我们可以通过@Order注解控制切面的优先级
  + 注解的值越大，优先级越小。值越小，优先级越大
  + 优先级高的嵌套在外面，其前置方法先执行。优先级低的被嵌套在里面，其返回、异常和后置通知先执行
  + 使用@Order注解可以指定切面执行的优先级

## 五、事务

### （一）JdbcTemplate

+ Spring封装了JDBC操作，简化了我们与数据库的交互过程，它将JDBC的操作封装为JdbcTemplate类供我们调用
+ 要使用JdbcTemplate，需要用到两个依赖:

~~~xml

    <mysql-connection.version>8.0.33</mysql-connection.version>
    <druid.version>1.2.22</druid.version>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql-connection.version}</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>6.0.2</version>
    </dependency>

~~~

+ JdbcTemplate是Spring自己的类，我们无法更改，因此没办法给它加Component注解，所以我们需要**在配置类里面使用Bean注解来提供它的bean，或者在xml文件内配置**
+ Druid连接池同理，也需要这么配置
+ [使用配置类提供bean](../源码/Spring/Spring-JDBC/src/main/java/com/example/Config.java)
+ [使用xml文件提供bean]()

|方法|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|update(String sql, Object... args)|sql:PreparedStatement格式的sql语句<br>args:sql语句需要用到的值，**按顺序填写**|执行sql语句|影响的行数|int|DataAccessException|无|[样例](../源码/Spring/Spring-JDBC/src/main/java/com/example/dao/JdbcTemplateDaoImpl.java)|
|query(String sql, RowMapper<T> rowMapper)|sql:PreparedStatement格式的sql语句<br>rowMapper:处理结果集并返回结果的接口对象|查询|List对象|List|^|无|^|
|queryForObject(String sql, Class<T>requiredType)|sql:PreparedStatement格式的sql语句<br>requiredType:想得到的结果类型的Class对象|得到单行单列的值|>|取决于requiredType|^|无|^|
|String sql, RowMapper<T> rowMapper, Object... args|sql:PreparedStatement格式的sql语句<br>rowMapper:处理结果集并返回结果的接口对象<br>args:sql语句需要用到的值|查询|>|Object|^|无|^|

---

### （二）事务

+ 事务分为两种:
  + 编程式事务:事务功能的相关操作全部通过自己编写代码来实现
  + 声明式事务:通过框架为我们提供的配置，然后让框架实现事务功能
+ 由于编程式事务过于复杂并且具有高度耦合性，这里仅说明声明式事务
+ **事务在底层由AOP实现**，因此需要aop相关依赖

---

### （三）使用注解

|注解|作用|备注|
|:---:|:---:|:---:|
|@EnableTransactionManagement|让Spring支持事务管理|配置在配置类上|
|@Transactional|配置事务处理规则|可以作用在类上或方法上，如果作用在类上，即代表类的全部方法都使用该事务|

|规则|值类型|说明|备注|
|:---:|:---:|:---:|:---:|
|readOnly|boolean|声明为true说明方法内仅允许查询操作|无|
|timeout|int|超时指定秒后回滚|**这个时间是与数据库连接的时间，而不是方法执行的总时间，且如果作用方法内没有任何与数据库的操作，也不会回滚**|
|propagation|Propagation,这是个枚举类|指定事务的传播行为|无|
|isolation|Isolation,这是个枚举类|指定事务的隔离级别|无|
|rollbackFor|Class<? extends Throwable>[]|指定某些异常出现时才回滚|无|
|rollbackForClassName|String[]|当出现的异常的类名在本数组中时，才会滚|无|
|noRollbackFor|Class<? extends Throwable>[]|指定某些异常出现时不回滚|无|
|noRollbackForClassName|String[]|当出现的异常的类名在本数组中时，不会滚|无|

+ propagation属性有多个值:

|值|含义|备注|
|:---:|:---:|:---:|
|**REQUIRED**|如果没有事务，那么创建一个，如果有，汇入当前事务中执行|无|
|SUPPORTS|如果当前没有事务，就以非事务方式执行，否则汇入当前事务中执行|无|
|MANDATORY|如果当前没有事务，直接抛出异常。否则汇入当前事务中执行|无|
|**REQUIRES_NEW**|开启一个新的事务，该事务独立于正在运行的事务。新事务执行时，老事务被挂起|无|
|NOT_SUPPORTED|以非事务方式运行，如果有事务，那么让事务挂起|无|
|NEVER|以非事务方式运行，如果有事务存在，抛出异常|无|
|NESTED|如果当前正有一个事务在进行中，则该方法应当运行在一个嵌套式事务中。被嵌套的事务可以独立于外层事务进行提交或回滚。如果外层事务不存在，则行为与REQUIRED保持一致|各厂商对该传播行为的支持存在差异|

+ isolation属性也有多个值:

|值|含义|脏读|不可重复读|幻读|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|
|READ UNCOMMITTED|允许事务读取其它事务未提交的修改|有|有|有|
|READ COMMITTED|事务仅能读取其他事务已提交的修改|无|有|有|
|REPEATABLE READ|事务执行期间，禁止其他事务对本事务字段进行更新操作|无|无|有|
|SERIALIZABLE|事务执行期间，禁止其他事务对本事务字段进行添加、修改、删除操作|无|无|无|

+ 隔离级别越高，安全性越高，同时效率也越低
+ [注解样例1](../源码/Spring/Spring-JDBC/src/main/java/com/example/dao/JdbcTemplateDaoImpl.java)
+ [注解样例2](../源码/Spring/Spring-JDBC/src/main/java/com/example/service/JdbcTemplateServiceImpl.java)
+ [注解样例3](../源码/Spring/Spring-JDBC/src/main/java/com/example/service/CheckImpl.java)
+ [测试样例](../源码/Spring/Spring-JDBC/src/test/java/JdbcTemplateTest.java)

---

### （四）xml配置

+ 这个傻逼xml配置，真他妈操蛋
  + 事务的底层是由AOP实现的，因此**必须配置AOP**，并**使用切入点表达式配置事务的详细处理路径**。它**必须是服务层的类**
+ [xml样例](../源码/Spring/Spring-JDBC/src/main/resources/JDBC.xml)
+ [测试类](../源码/Spring/Spring-JDBC/src/test/java/JdbcTemplateXmlTest.java)
+ 如果想使用xml测试方法，**需要暂时把Config的EnableTransactionManagement注解注释掉，并注释掉Config类里面的方法**

---

## 六、Resources

+ java提供的原生的资源相关类有时无法满足我们的相关需求，因此，Spring在Resources方面对这些需求做了封装

### （一）Resource接口

+ Resource接口提供了如下方法，它有很多实现类
  + 同时它继承了InputStreamSource接口，因此也继承了该接口的方法，该接口**只有一个方法**:getInputStream()

|方法|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|getInputStream|无参|得到资源的输入流对象|输入流对象|InputStream|IOException|**这是继承的InputStreamSource接口的方法**|[样例](../源码/Spring/Resources/src/test/java/UrlResourceTest.java)|
|exists|无参|检查资源是否存在|true或false|boolean|无|无|^|
|isReadable|无参|检查资源是否可读|true或false|boolean|无|无|^|
|isOpen|无参|检查资源是否已经被打开|true或false|boolean|无|无|^|
|isFile|无参|检查资源是否是文件|true或false|boolean|无|无|^|
|getURL|无参|得到资源的URL对象|URL对象|URL|IOException|无|^|
|getURI|无参|得到资源的URI对象|URI对象|URI|^|无|^|
|getFile|无参|得到文件对象|File对象|File|^|无|^|
|readableChannel|无参|获取资源的可读通道|通道对象|ReadableByteChannel|^|无|^|
|contentLength|无参|获取资源的内容长度|数值|long|^|无|^|
|lastModified|无参|最后一次修改的时间戳|时间戳|long|^|无|^|
|createRelative(String relativePath)|relativePath:相对路径|创建相对于资源的资源对象|资源对象|Resource|^|无|^|
|getFilename|无参|得到文件名称|名称|String|无|无|^|
|getDescription|无参|得到资源信息|描述信息|String|无|无|^|

+ Resource有下列实现类

![Resource实现类](../文件/图片/Spring图片/Resource接口实现类.png)

---

#### ①UrlResource

+ UrlResource是Resource接口的实现类之一，用来访问网络资源。它支持下面的类型:
  + http: 该前缀用于访问**基于HTTP协议**的网络资源
  + ftp: 该前缀用于访问**基于FTP协议**的网络资源
  + file: 该前缀用于从**文件系统**中读取资源，如果使用相对路径，**相对的是项目根路径**
+ 正常情况下，使用UrlResource构造器，并传入带上面的前缀的路径就能创建资源对象
+ 注意:
> + 在测试类内，相对路径**相对于当前项目的根路径**
> + 在其他类内，相对路径**相对于最外层项目的根路径**
+ [样例](../源码/Spring/Resources/src/test/java/UrlResourceTest.java)

---

#### ②ClassPathResource

+ ClassPathResource用来访问**相对于classpath**下的资源
+ 正常情况下，使用UrlResource构造器，并传入带上面的前缀的路径就能创建资源对象
+ [样例](../源码/Spring/Resources/src/test/java/UrlResourceTest.java)

---

#### ③FileSystemResource

+ FileSystemResource用于访问文件系统资源，但是Java 提供的 File 类也可用于访问文件系统资源，它相对于File类并没有太大的优势
+ 正常情况下，使用UrlResource构造器，并传入带上面的前缀的路径就能创建资源对象
+ 注意:
> + 在测试类内，相对路径**相对于当前项目的根路径**
> + 在其他类内，相对路径**相对于最外层项目的根路径**
+ [样例](../源码/Spring/Resources/src/test/java/UrlResourceTest.java)

---

#### ④其它相关类

+ **ServletContextResource**:ServletContext资源的Resource实现，它解释相关Web应用程序根目录中的相对路径。它始终支持流(stream)访问和URL访问，但只有在扩展Web应用程序存档且资源实际位于文件系统上时才允许java.io.File访问。无论它是在文件系统上扩展还是直接从JAR或其他地方（如数据库）访问，实际上都依赖于Servlet容器。
+ **InputStreamResource**:是给定的输入流(InputStream)的Resource实现。它的使用场景在没有特定的资源实现的时候使用(感觉和@Component 的适用场景很相似)。与其他Resource实现相比，这是已打开资源的描述符。 因此，它的isOpen()方法返回true。如果需要将资源描述符保留在某处或者需要多次读取流，请不要使用它。
+ **ByteArrayResource**:字节数组的Resource实现类。通过给定的数组创建了一个ByteArrayInputStream。它对于从任何给定的字节数组加载内容非常有用，而无需求助于单次使用的InputStreamResource。

---

### （二）ResourceLoader接口

+ Spring的ResourceLoader接口内仅包含了一个方法:
  + `Resource getResource（String location）`:该方法可以直接获取相关的资源对象
+ 当Spring应用需要进行资源访问时，实际上并不需要直接使用Resource实现类，而是调用ResourceLoader实例的getResource()方法来获得资源，ReosurceLoader将会负责选择Reosurce实现类，也就是确定具体的资源访问策略，从而**将应用程序和具体的资源访问策略分离开来**
+ 因此，ResourceLoader接口的作用就是**使用实现类的访问策略来得到资源**
  + ApplicationContext实现类都实现了ResourceLoader接口，因此**ApplicationContext可直接获取Resource实例**
  + 使用ApplicationContext实现类对象获取Resource对象时，**可以通过制定不同前缀强制获取指定的Resource实现类对象，默认得到的Resource实现类对象取决于ApplicationContext实现类对象类型**，如FileSystemXmlApplicationContext得到的就是FileSystemResource实例；是ClassPathXmlApplicationContextResource得到的就是ClassPathResource实例
+ Spring有两个相关接口
  + ResourceLoader接口:[样例](../源码/Spring/Resources/src/test/java/ResourceLoaderTest.java)
  + ResourceLoaderAware接口
    + 它属于Bean Aware的一个接口，所以它的实现类实现的方法会在Spring容器初始化其实例的时候就执行，我们可以**通过该实现方法在该bean加载时获取它的ResourceLoader对象，并进行一些操作**

---

### （三）提取Resource路径

+ 在上面的样例中，我们想得到Resource实例对象，还是需要**将路径写死在代码中，一旦路径发生修改，还要更改程序，这并不是我们希望的**
+ 因此，我们可以创建一个类，让Resource对象作为该类的一个属性存在，并为该类写一些方法来表示Resource对象的一些操作
+ 这样，我们就可以通过**依赖注入**的方式，通过xml文件的方式将得到Resource对象了，这可以帮助我们提取出程序中的路径，并将它放在文件中。以后我们修改，只需要修改文件中的路径就可以了，就不用修改程序了
+ 样例略

---

## 七、国际化

+ 国际化也称作i18n，其来源是英文单词 internationalization的首末字符i和n，18为中间的字符数。由于软件发行可能面向多个国家，对于不同国家的用户，软件显示不同语言的过程就是国际化。通常来讲，软件中的国际化是通过配置文件来实现的，假设要支撑两种语言，那么就需要两个版本的配置文件。
+ Java提供了java.util.Locale和java.util.ResourceBundle来支持国际化
  + Locale用于**指定当前用户所属的语言环境等信息**，Locale包含了language信息和country信息
  + ResourceBundle用于**查找绑定对应的资源文件**
+ 为了实现国际化，需要:
  + **在resources目录下创建properties文件**，其命名格式需要遵守`basename_language_country.properties`的格式，其中basename是必须的，language和country可选
+ [Java原生的国际化样例](../源码/Spring/I18n/src/test/java/I18nJDKTest.java)
+ Spring也提供了相关的国际化API，它通过MessageSource这个接口来支持，他有多个实现类
  + **ResourceBundleMessageSource**:基于Java的ResourceBundle基础类实现，允许仅通过资源名加载国际化资源
  + **ReloadableResourceBundleMessageSource**:相比第一个，多了定时刷新功能，允许在不重启系统的情况下，更新资源的信息
  + **StaticMessageSource**:它允许通过编程的方式提供国际化信息，可以通过这个来实现db中存储国际化信息的功能。
+ [Spring国际化样例](../源码/Spring/I18n/src/test/java/I18nSpringTest.java)

---

## 八、数据校验

+ 进行数据的合法性检验是开发过程中常有的事，因此，Spring提供了数据校验的相关API供我们便捷的进行数据校验，并有效地减少了代码的耦合度。
+ 在Spring中，可以使用三种方式进行数据校验:
  + 通过实现org.springframework.validation.Validator接口，然后在代码中调用这个类
  + 按照Bean Validation方式来进行校验，即通过注解的方式。
  + 基于方法实现校验
  + 实现自定义校验
+ 使用数据校验需要引入相关依赖:

~~~xml

    <dependencies>
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>8.0.1.Final</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>jakarta.el</artifactId>
            <version>5.0.0-M1</version>
        </dependency>
    </dependencies>

~~~

### （一）接口校验

+ 创建一个类，需要**实现Validator接口，并实现supports和validate方法**
  + 导入org.springframework.validation.Validator接口
  + public boolean supports(Class<?> clazz)方法**用来判断传入的对象是否是判断类对象**，如果是返回true
    + clazz表示待判断的对象
  + public void validate(Object target, Errors errors)方法**用来进行校验**
    + target表示当前校验的类对象
    + errors表示错误对象
+ 创建后，需要创建一个DataBinder对象来执行校验
  + DataBinder(Object target)构造器用来创建一个DataBinder对象
  + setValidator(Validator validator)方法用来设置校验器对象
  + validate()方法执行校验
  + getBindingResult()方法得到校验结果

|所属|方法|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|Validator|supports(Class<?> clazz)|clazz:待判断的对象|判断传入的对象是否符合校验条件|符合返回true|boolean|无|无|[样例](../源码/Spring/Validation/src/main/java/com/example/InterfaceValidationSample.java)|
|^|validate(Object target, Errors errors)|target:当前校验的类对象<br>errors:错误对象|进行校验|无返回值|void|无|无|^|
|ValidationUtils|rejectIfEmpty(Errors errors, String field, String errorCode, String defaultMessage)|errors:错误对象<br>field:被校验对象的属性名<br>errorCode:错误码<br>defaultMessage:错误提示|判断对象的指定属性名的属性是否为空，如果是空，那么报异常并输出错误信息|无返回值|void|无|无|^|
|Errors|rejectValue(String field, String errorCode)|field:被校验对象的属性名<br>errorCode:错误码|向错误对象内注入相关属性的校验错误信息|无返回值|void|无|无|^|
|DataBinder|DataBinder(Object target)|target:被校验的对象|DataBinder的构造器|DataBinder对象|DataBinder|无|无|[样例](../源码/Spring/Validation/src/test/java/InterfaceValidationTest.java)|
|^|setValidator(Validator validator)|validator:校验器对象|设置校验器对象|无返回值|void|无|无|^|
|^|validate()|无参|执行校验|无返回值|void|无|无|^|
|^|getBindingResult()|无参|得到校验结果|校验结果|BindingResult|无|无|^|
|BindingResult|getAllErrors()|无参|得到全部的错误信息|列表对象|List<ObjectError>|无|无|^|

---

### （二）注解校验

+ 使用注解校验，首先需要创建一个LocalValidatorFactoryBean类的Bean，可以在配置类里使用@Bean注解创建
+ 接下来使用注解作用于被校验类的属性上，便可以指定校验规则了

|注解|作用|备注|
|:---:|:---:|:---:|
|@NotNull|非空|无|
|@NotEmpty|非空|**作用于String类型属性**|
|@NotBlank|去首尾空白字符后非空|^|
|@DecimalMax(value)|设置上限|无|
|@DecimalMin(value)|设置下限|无|
|@Max(value)|设置上限|无|
|@Min(value)|设置下限|无|
|@Pattern(value)|正则匹配|无|
|@Size(max,min)|限制字符长度在指定区间内|无|
|@Email|校验邮箱格式|无|

+ 使用注解校验可以使用两种校验器进行校验
  + 一种是jakarta.validation.Validator下的校验器
  + 一种是org.springframework.validation.Validator下的校验器
+ 得到校验器对象后，都调用其validate方法进行校验即可

|所属|方法|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|Validator(jakarta)|validate(T var1, Class<?>... var2)|var1:被校验对象<br>var2:指定验证组,没有不用传|执行校验|set对象|Set<ConstraintViolation<?>>|无|无|见下|
|Validator(spring)|validate(Object target, Errors errors)|target<br>被校验对象<br>errors:错误对象|执行校验|无返回值|void|无|无|^|
|bindException|BindException(Object target, String objectName)|target:被检验对象<>objectName:对象名称|BindException的构造器|BindException对象|BindException|无|无|^|
|^|getBindingResult()|无参|得到校验结果|校验结果|BindingResult|无|无|^|

+ [配置类](../源码/Spring/Validation/src/main/java/com/example/Config.java)
+ [被校验类](../源码/Spring/Validation/src/main/java/com/example/annovalidation/Person.java)
+ [测试](../源码/Spring/Validation/src/test/java/AnnotationValidationTest.java)

---

### （三）方法校验

+ 想使用方法校验，需要在配置类中生成一个MethodValidationPostProcessor的Bean对象
+ 之后创建测试用的被校验类
+ 然后创建一个类，它提供方法来启动校验方法，该方法接收被校验类的对象来进行校验
+ 在测试类中测试，得到启动校验的类对象，接着执行其方法
+ **使用方法注解需要用到@Validated和@Valid注解**

+ [配置类](../源码/Spring/Validation/src/main/java/com/example/Config.java)
+ [被校验类](../源码/Spring/Validation/src/main/java/com/example/User.java)
+ [校验服务类](../源码/Spring/Validation/src/main/java/com/example/MethodValidateSample.java)
+ [测试](../源码/Spring/Validation/src/test/java/MethodValidationTest.java)

---

### （四）自定义校验

+ 自定义校验需要我们自定义注解来实现，我们自定义的注解模板可以直接复制本来就有的注解模板，然后加以修改:

~~~java
    import jakarta.validation.Constraint;
    import jakarta.validation.Payload;
    import jakarta.validation.constraints.NotNull;
    import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

    import java.lang.annotation.*;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {NoBlankValidator.class})
    public @interface NoBlank {
        // 提示信息
        String message() default "{不能包含空格}";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
        @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        public @interface List {
            NotNull[] value();
        }
    }
~~~

+ 接下来定义实现类

~~~java
    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;
    import jakarta.validation.ConstraintViolation;

    // 实现的校验器需要实现ConstraintValidator接口，它的两个泛型分别表示该校验器作用的是被哪个注解作用的属性，以及该属性的类型
    public class NoBlankValidator implements ConstraintValidator<NoBlank,String> {

        @Override
        public void initialize(NoBlank constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        // isValid用来指定校验逻辑，如果通过校验返回true
        public boolean isValid(String s, ConstraintValidatorContext context) {
            if(s==null||s.contains(" ")){
                //获取默认提示信息
                String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
                System.out.println("default message :" + defaultConstraintMessageTemplate);
                //禁用默认提示信息
                context.disableDefaultConstraintViolation();
                //设置提示语
                context.buildConstraintViolationWithTemplate("不能包含空格").addConstraintViolation();
                return false;
            }
            return true;
        }
    }

~~~

+ [自定义校验注解](../源码/Spring/Validation/src/main/java/com/example/NoBlank.java)
+ [校验注解的校验器](../源码/Spring/Validation/src/main/java/com/example/NoBlankValidator.java)
+ 方便起见，直接使用上面的方法校验的[样例](../源码/Spring/Validation/src/test/java/MethodValidationTest.java)来测试自定义校验是否成立

---

## 九、SpringEL表达式

+ SpEL是一种强大，简洁的装配 Bean 的方式。它可以通过执行运行期间执行的表达式将值装配到属性或构造器内，也可以调用JDK提供的静态常量，还能读取外部Properties的配置

### （一）读取Properties文件

+ 我们可以通过@PropertySource注解来读取properties文件的内容，它作用在类上，受其影响的类可以**通过@Value注解搭配插值表达式来得到对应的内容**
  + 可以通过encoding属性指定解码字符集
  + 可以通过value属性指定读取的文件路径，如果想读取类路径下的，需要加`classpath:`作为前缀
+ [注入的类](../源码/Spring/SpringEL/src/main/java/com/example/PropertiesValueSample.java)
+ [测试样例](../源码/Spring/SpringEL/src/test/java/SpringELTest.java)

---

### （二）简单使用

+ Spring提供了ExpressionParser类用来得到一个Spel分析器对象
+ 调用parseExpression方法来得到SPEL对象
+ 使用getValue方法来得到表达式的值

|所属|方法|参数|作用|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|SpelExpressionParser|SpelExpressionParser()|无参|>|生成一个ExpressionParser类型对象|ExpressionParser|无|无|[样例](../源码/Spring/SpringEL/src/main/java/com/example/Config.java)|
|ExpressionParser|parseExpression(String expressionString)|expressionString:SpEL表达式|得到SpEL表达式对象|SpEL表达式对象|Expression|ParseException|无|[样例](../源码/Spring/SpringEL/src/test/java/SpringELTest.java)|
|Expression|getValue()|无参|得到表达式执行结果|结果|Object|EvaluationException|无|^|
|^|getValue(Object rootObject)|rootObject:表达式作用的类对象|^|结果|Object|^|无|^|
|^|setValue(Object rootObject,Object value)|rootObject:表达式作用的类对象<br>value:想赋的值|给指定对象的指定属性(在SpEL内声明)赋值|无返回值|void|^|无|^|

+ SpEL有一些运算符和语法:
  + 类似JS对象的Map类型声明方式:`{name:'lzx',age:'12'}`
  + 直接的List类型声明方式:`{1,2,3,4,5}`
  + .?运算符:用于进行数据过滤并返回过滤后的结果:`list.?[name=='lzx']`，这里**选择list属性的每一个元素的name属性为lzx字符串的元素**，加入新的ArrayList对象内。如果是map,那么返回的是HashMap对象
  + .!运算符:用于解构，使我们选择我们想要的属性值:`list.![name]`，这里**选择list属性的每一个元素的name属性值**，加入新的ArrayList对象内。如果是map,那么返回的是HashMap对象
    + 如果想得到键值对形式的值，键的名字就是key，值是想得到的值，可以写:`{'key':key}`
    + 想得到List封装的值，可以写:`{key,value}`，这样可以得到多个值
    + 想得到一个值，直接写就行
  + ?.可选链运算符:用于判断前面的值是否为空，**如果为空结束执行并返回null,如果不空继续执行后面的语句**

---

## 配置汇总与杂项

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
        <mysql-connection.version>8.0.33</mysql-connection.version>
        <druid.version>1.2.22</druid.version>
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
            <!-- spring-jdbc依赖 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-jdbc</artifactId>
                <version>6.1.5</version>
            </dependency>
            <!-- JDK注解拓展，有一些依赖注入的注解 -->
            <dependency>
                <groupId>jakarta.annotation</groupId>
                <artifactId>jakarta.annotation-api</artifactId>
                <version>${annotation.version}</version>
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
            <!-- MySQL驱动 -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql-connection.version}</version>
            </dependency>
            <!-- Druid连接池 -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
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

### （四）整合junit

+ 测试时，每次测试都需要创建一个ApplicationContext容器对象，很麻烦
+ Spring提供了专门整合junit的依赖，让junit不需要再创建容器对象，就能读取到对象的值
+ 依赖:

~~~xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>6.1.5</version>
    </dependency>
~~~

+ 在resources文件夹下添加xml文件，加下面的语句(需要context约束)

~~~xml
    <!-- 让Spring扫描下面的路径的注解 -->
    <context:component-scan base-package="org.example" />

~~~

+ 需要为测试类添加如下注解:

~~~java
    @SpringJUnitConfig(locations = "classpath:junit.xml")  // 这是根据xml配置的
    @SpringJUnitConfig(classes = Config.class)  // 如果用注解配置，向classes传入配置类的Class对象

    // 下面的是整合Junit5的另一种注解方式

    @ExtendWith(SpringExtension.class)  // 这是固定格式
    @ContextConfiguration("classpath:beans.xml")  // 与上面的xml配置值一样

    // 下面是junit4整合的注解写法
    @RunWith(SpringJUnit4ClassRunner.class)  // 固定格式
    @ContextConfiguration("classpath:beans.xml")  //  与上面的xml配置值一样
~~~

+ 然后就能使了

~~~java

    package org.example;

    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


    //  该注解能让junit在测试时直接就能找到Spring的对应bean对象，而不需要再创建IoC容器对象获取了
    @SpringJUnitConfig(classes = Config.class)
    @Component
    public class JunitTest {
        @Value("12")
        private int age;

        @Autowired
        private User user;

        @Test
        public void printTest(){
            System.out.println(age);
            System.out.println(user);
        }
    }


~~~

+ [样例](../源码/Spring/Spring-JUnit/src/main/java/org/example/JunitTest.java)

---

### （五）文件路径前缀与通配符

|前缀|作用|备注|
|:---:|:---:|:---:|
|classpath:|相对于类路径加载资源|无|
|context:|相对于Spring上下文加载资源|无|
|file:|相对于文件系统加载资源，支持绝对路径和相对路径|无|
|url|直接指定URL，支持多种协议|无|
|http:/https:|从http或https协议加载资源|无|
|ftp: / ftps:|从ftp或ftps协议加载资源|无|
|databasetable:|从数据库表中加载资源|无|
|classpath*:|匹配类路径下所有符合条件的资源并加载|无|

|后缀|作用|备注|
|:---:|:---:|:---:|
|aaa*|匹配前缀为aaa的资源|无|
|*aaa|匹配后缀是aaa的资源|无|
|a*b|匹配前缀是a，后缀是b的资源|无|

+ [样例](../源码/Spring/SpringEL/src/test/java/PathTest.java)










