# Springboot笔记

## 一、简介

+ Springboot是Spring官方提供的可以帮我们简单、快速地创建一个独立的、生产级别的 Spring 应用
+ SpringBoot可以:
  + 快速创建独立的Spring应用
  + 直接嵌入Tomcat、Jetty or Undertow（无需部署 war 包）(Servlet容器):如果是jar包可以通过`java -jar`命令运行，当然war包也可以，但建议war包放到tomcact的webapps目录内运行
  + 提供可选的starter，简化应用整合:**SpringBoot提供了许多场景启动器，并为每种场景准备了对应依赖，且自动为我们导入了符合当前版本的包**
  + 按需自动配置 Spring 以及 第三方库:**SpringBoot遵循约定大于配置的原则，它的每个场景都有很多默认配置**，如果我们想自定义，只需要修改几项即可
  + 提供生产级特性：如 监控指标、健康检查、外部化配置等
  + 无代码生成、无xml
+ 总结：SpringBoot可以简化开发，简化配置，简化整合，简化部署，简化监控，简化运维

---

## 二、HelloSpringBoot

+ 首先需要创建一个项目，在项目内指定该项目继承自spring-boot-starter-parent

~~~xml

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.0.5</version>
        <!-- 
          该标签的作用是Maven在查找依赖时先从该标签指定的本地路径下（这个本地路径不是仓库路径，而是父项目的pom.xml路径）查找，找不到再找本地仓库，找不到再找全局仓库
          但是不写IDEA（比较老的版本可能不报）可能会报错
          将该标签设置为空体可以使Maven直接从本地仓库中寻找依赖项，就直接绕过了本地路径
         -->
        <relativePath/>
    </parent>

~~~

+ 接下来指定依赖项:

~~~xml

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

~~~

+ 接下来开始编写Application类，该类是整个SpringBoot项目的启动类:

~~~java
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication  // 该注解用来声明这是SpringBoot的项目启动类
    public class MainApplication {
        public static void main(String[] args) {
            // 调用SpringApplication的run方法，传入本类的Class对象和参数来启动SpringBoot项目
            // 这是固定写法
            SpringApplication.run(MainApplication.class,args);
        }
    }

~~~

+ 启动main方法便可以开始运行了
+ SpringBoot内置了对应的tomcat,因此我们无需再配置，它的默认端口为8080,通过访问可以发现它返回了404:

![SpringBoot启动](../文件/图片/SpringBoot图片/SpringBoot启动.png)

+ 这是因为我们一个controller都没写，没有接受任何请求
+ 接下来创建controller类:

~~~java
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController  // @ResponseBody+@Controller
    @RequestMapping("/index")  // 匹配映射/index访问
    public class HelloSpringBootController {
        @GetMapping
        public String helloSpringBoot(){
            return "Hello SpringBoot3";
        }
    }
~~~

+ 接下来就能访问了，直接访问[指定路径](http://localhost:8080/index)来测试是否正确配置
<br>
+ 除此以外，SpringBoot还额外提供了插件，供我们通过命令行的方式直接运行该jar包或war包
  + 首先执行`mvn clean package`重新打包
  + 接下来在所在包的对应目录下打开cmd,执行`java -jar xxxx.jar/war`来运行
  + 访问路径确认是否项目已经运行
<br>
<br>
+ 由于SpringBoot为了简化配置，它自动的配置了很多默认配置项
+ 如果我们想修改，我们可以在对应jar包或war包的同级目录下新建一个叫`application.properties`的配置文件，在该配置文件内指定自定义配置:

~~~properties
    <!-- 这里指定端口号为8888 -->
    server.port=8888
~~~

---

## 三、基础知识

### （一）特性

+ SpringBoot可以简化多个方面:
  + **简化整合**:我们想实现什么功能，就导入什么场景。
    + 官方提供的场景一般叫`spring-boot-starter-*`，而第三方提供的场景一般叫`*-spring-boot-starter`
    + [默认支持的场景](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters)
  + **简化开发**:无需进行任何配置，直接就可以开始开发
  + **简化配置**:SpringBoot的约定大于配置
    + 它提供了许多默认的配置，这样就不用我们在每次开始开发的时候先进行大量的配置工作
    + 如果想更改配置，可以创建一个`application.properties`文件，所有的配置都写在该文件中修改即可
    + [可修改的配置](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties)
  + **简化部署**:打包为可执行的jar包，只需要linux系统上有java环境即可
  + **简化运维**:可以快速地修改配置（通过application.properties）、监控、健康检测

---

### （二）SpringInitializr

+ IDEA提供了自动创建SpringBoot项目的模板模块:SpringInitializr，他可以为我们快速创建一个对应的SpringBoot项目
+ 首先new->Module，在左边的目录选择SpringInitializr

![创建项目](../文件/图片/SpringBoot图片/SpringInitializr创建项目1.png)

+ Name后写项目名称
+ Type选择Maven
+ Group自己指定一个对应的Group
+ JDK推荐选择17及以上
+ 打包方式随便
+ 接下来点击Next,进到选择场景界面

![创建项目2](../文件/图片/SpringBoot图片/SpringInitializr创建项目2.png)

+ 在此界面的左上角可以选择SpringBoot的对应版本
+ 下面的Depemdencies下可以选择对应的场景:
  + 一般都是写JavaWeb项目，所以需要勾选Web下的SpringWeb
  + 如果想使用数据库，在SQL选择JDBC API和MySQL Driver，还可以选择Mybatis Framework
  + 如果想使用与jsp相关的视图解析器，可以在Template Engines下选择Thymleaf
  + 如果想使用Lombok，在Developers Tools下选择Lombok
+ 选择好之后点击创建，IDEA会**自动导入相关依赖**并**创建好相对应的项目启动类**
+ 如果我们想编写业务代码，我们需要把我们的代码及它们所在包写在项目启动类所在包的子包或后代包下，因为**SpringBoot默认只会扫描项目启动类所在包的子包及后代包的类**

---

### （三）依赖管理

+ SpringBoot容易使用，其中的原因之一就是依靠依赖管理来实现的
  + 根据Maven的依赖传递原则，A依赖了B，且B依赖了C，那么A同时拥有B和C
    + 根据该情况，我们只需要导入对应的场景，而**SpringBoot提供的对应场景启动器依赖本身已经导入了许多依赖**，这样，当我们想开发什么场景时，就导入指定的场景启动器就可以直接进行开发了，因为这些场景启动器已经导入了我们想导入的依赖
  + 我们在**导入依赖时，不需要写版本号**
    + 这是因为每个SpringBoot项目都有公共的父项目——`spring-boot-starter-parent`，而该项目的父项目又是`spring-boot-dependencies`，它**为我们声明了许多常用依赖的适配版本**
    + 如果我们想改变依赖的版本号，可以依据Maven的就近原则，直接在我们项目的pom.xml文件内的properties标签内声明对应的版本，然后再在denpendency标签内声明版本即可；或者直接在dependency标签内声明版本也可以。由于就近原则，Maven会认定我们手动指定的版本优先级更高
  + 如果我们想导入的依赖没有在我们继承的父项目内，那么我们只能自己导入了（如Druid连接池）

---

### （四）自动配置机制

#### ①初步认识

+ 一旦我们导入了场景，我们在启动SpringBoot项目启动类的时候，其run方法在执行过程中会自动为我们向IOC容器内装配好该场景的相关组件，便于我们使用
+ 验证:
  + run方法会返回IOC容器对象，我们可以使用IOC容器对象得到其内部所有的bean:
  + [验证样例](../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/SpringBootInitializrDemoApplication.java)

#### ②主程序类

+ 被@SpringBootApplication注解作用的类就是SpringBoot项目的主程序类
  + 默认SpringBoot项目在扫描包时，只会扫描主程序类所在包及其后代包
    + 如果想更改，可以**通过@SpringBootApplication注解的scanBasePackages属性指定想扫描的包**
    + 观察@SpringBootApplication的源码可以发现，它被@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan注解作用，因此我们也可以**将@SpringBootApplication注解拆分成这三个注解，并通过@ComponentScan注解来实现自定义扫描路径**

---

#### ③配置文件

+ 可以使用application.properties配置文件来进行SpringBoot项目的配置修改
  + 我们修改的每一个属性都对应着配置类中的一个属性，如
    + ServerProperties类内的属性都是关于Tomcat服务器配置相关的
    + MultipartProperties类内的属性都是关于文件上传相关的
+ 我们导入的依赖`spring-boot-starter-xxx`，它们都会依赖于`spring-boot-starter`
  + 该依赖是所有starter的starter
  + `spring-boot-starter`又导入了`spring-boot-autoconfigure`包，在该包下存放着各个场景下的自动配置类
    + **这些配置类并不是全都生效，如果我们并未导入某些配置类的依赖项，那么对应的配置类就不会生效**

---

#### ④完整流程

+ @SpringBootApplication注解是一个复合注解，从下面的常用注解中我们已经知道:
  + @SpringBootConfiguration用来声明该类是一个SpringBoot配置类
  + @omponentScan注解用来扫描指定包
+ 只剩下一个@EnableAutoConfiguration注解，点开该注解，可以看到:

~~~java

  import java.lang.annotation.Documented;
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Inherited;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  import org.springframework.context.annotation.Import;

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  @AutoConfigurationPackage
  @Import({AutoConfigurationImportSelector.class})  // 导入AutoConfigurationImportSelector类
  public @interface EnableAutoConfiguration {
      String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

      Class<?>[] exclude() default {};

      String[] excludeName() default {};
  }

~~~

+ 该注解也是一个复合注解，其中它的作用之一就是导入AutoConfigurationImportSelector类的bean
  + AutoConfigurationImportSelector类就是SpringBoot自动装配的关键
  + 之所以要导入该类，是因为**默认的@ComponentScan注解扫描不到我们的自动配置类（spring-boot-autoconfigure依赖）所在的包**，而该类正好是spring-boot-autoconfigure依赖包下的类,**只要加载它，他就可以加载它所在包下的我们导入的场景对应的自动配置类**
  + AutoConfigurationImportSelector类作用就是**将对应的IOC容器所需的自动配置类加载进IOC，从而达到自动配置的目的**
  + 该类会自动从其所在包下的`META-INF\spring\org.springframework.boot.autoconfigure.AutoConfiguration.imports`加载对应的自动配置类
    + 每个配置类下一般都有**被@Bean注解作用的方法，用来将对应类的对象放入IOC容器中**，也就是说，该自动配置类会给容器中导入一堆相关组件
    + 同时，对应方法还有可能会存在@EnableConfigurationProperties类，并在方法参数列表内接收了指定的类对象，用来配置放入IOC容器对象内的bean的基本配置，而**该注解所指定的类就是我们的application.properties内所声明属性对应的指定Peoperties配置类**
  + 加载时会经过getAutoConfigurationEntry方法，在该方法内调用getCandidateConfigurations方法，再调用ImportCandidates.load方法来加载上面的imports文件中的对应值来放入待加载配置类列表，最后经由一系列操作实现自动配置
  + 但是，**并不是加载的全部的配置类都会生效**，对应的配置类也需要指定条件才会生效。因为**每一个配置类都有条件注解约束**

![自动配置流程](../文件/图片/SpringBoot图片/自动配置流程.png)

---

### （五）常用注解

#### ①组件注册注解

|注解|作用|作用范围|备注|
|:---:|:---:|:---:|:---:|
|@Configuration|声明对应类为配置类|类|无|
|@SpringBootConfiguration|生命对应类为SpringBoot项目的配置类|类|其实跟上面的注解没有区别|
|@Bean|使方法返回值作为bean加入到IOC容器内|方法|无|
|@Scope|声明该类型的bean是单实例还是多实例|方法|无|
|@Controller/@Service/@Repository/@Conponent|声明对应类属于控制层/服务层/DAO层/其它层，并将其纳入IOC容器管理|类|无|
|@Import|指定对应类受IOC容器管理|类|一般用于将第三方包下的类纳入IOC容器管理|
|@ComponentScan|开启组件扫描|类|作用于配置类上|

+ [样例](../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)

---

#### ②条件注解

|注解|作用|作用范围|备注|
|:---:|:---:|:---:|:---:|
|@ConditionalOnClass|若类路径下存在该类，那么触发指定行为|类/方法|触发指定行为需要利用其他注解实现，如加入IOC容器需要@Bean注解|
|@ConditionalOnMissingClass|若类路径下不存在该类，那么触发指定行为|^|^|
|@ConditionalOnBean|若IOC容器内存在指定的bean,那么触发指定行为|^|^|
|@ConditionalOnMissingBean|如果容器中不存在这个Bean（组件）,那么触发指定行为|^|^|

+ 例:
  + 如果存在FastSqlException这个类，给容器中放一个Cat组件，名cat01
  + 否则，就给容器中放一个Dog组件，名dog01
  + 如果系统中有dog01这个组件，就给容器中放一个 User组件，名zhangsan
  + 否则，就放一个User，名叫lisi

+ [样例](../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)

---

#### ③属性绑定

|注解|作用|作用范围|备注|
|:---:|:---:|:---:|:---:|
|@ConfigurationProperties|声明组件的属性和配置文件内key的前缀项以进行项绑定|类|该注解生效必须**使作用类被@Component及相关注解作用或被配置类的@EnableConfigurationProperties指定**，且**对应的实体类需要有getter和setter方法**<br>该注解生效的时机貌似是bean创建时检查|
|@EnableConfigurationProperties|指定某些类是属性绑定类|类|应作用于配置类|

+ [实体类绑定样例](../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/pojo/Person.java)
+ [配置类样例](../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)

---

## 四、核心

### （一）场景处理

+ 在了解完成SpringBoot的自动配置机制后，我们就可以进行基本的场景定制化处理了
  + 首先我们需要导入对应的场景启动器
  + 接下来可以修改配置文件，以达到我们期望的配置
  + 分析该场景向IOC容器内增加了什么组件，这些组件是否能够满足我们的需求
    + 如果满足，我们就可以使用@AutoWired注解自动装配了
    + 如果不满足，我们可以进行定制化
      + 可以看到每个AutoConfiguration类内要加入IOC容器的组件，如果想加入，**它们都有前置条件**
      + 因此只要打破这些前置条件，其自动配置就不会执行
      + 而我们打**破这些前置条件最简单的方法就是我们在自己编写的配置类或其它地方也提供对应的bean**
      + 这样就实现了定制化

---

### （二）yaml文件

#### ①基本语法

+ 我们的application.properties文件不能明显的表示层级关系，因此当其配置变多以后，里面的内容会变得难以阅读和维护
+ 为了解决这方面的困境，我们可以提供另外一种配置文件:application.yaml配置文件
  + YAML 是 "YAML Ain't a Markup Language"（YAML 不是一种标记语言）。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（是另一种标记语言）
  + 该文件层次分明，方便人类读写
+ 它的基本语法很简单
  + 区分大小写
  + 使用缩进表示层级关系
  + 使用`key:(空格)value`的键值对形式表示数据，**value前的空格必须写**
  + 只要两个key的左侧是对齐的，那么它们就是相同层级的
  + #表示注释
  + 不能使用Tab键，只能使用空格
  + **二者的部分配置冲突时，properties文件的优先级比yaml文件的优先级高**
+ 示例:
  + 现在我们想对一个java实体类进行属性注入，我们需要在配置类中配置其属性默认值:

~~~java

@Component
@ConfigurationProperties(prefix = "people")
@Data
public class People {
    private String name;
    private Integer age;
    private Date birthDay;
    private Boolean like;
    private Child child; //嵌套对象
    private List<Dog> dogs; //数组（里面是对象）
    private Map<String,Cat> cats; //表示Map
}

~~~

+ 首先是[使用properties文件进行配置](../源码/SpringBoot/SpringBootInitializrDemo/src/main/resources/application.properties)
+ 接下来[使用yaml文件进行配置](../源码/SpringBoot/SpringBootInitializrDemo/src/main/resources/application.yaml)

---

#### ②复杂对象表示与语法细节

+ 如果我们想使用properties文件表示复杂对象的话:
  + 对于Map对象，就直接写xxx.xxx.key.propertyName=yyy
  + 对于List或数组对象，直接写xxx.xxx.arr[index].propertyName=yyy
+ 如果我们想使用yml文件表示复杂对象:

~~~yml
  xxx:
    yyy: [value1,value2,...] # 表示数组
    zzz: 
      - name: lzx  # 如果是对象数组，直接在- 后面加上属性，然后赋值
      - age: 10
    kkk: {name: ly,age: 20}  # 如果是对象，可以直接用大括号表示
    jjj:
      name: ly  # 或者使用这种方法进行测试
      age: 20
~~~

+ 配置文件还有一些特殊语法:
  + 如果我们的属性名是小驼峰命名法命名的，如birthDay,那么**yml文件或properties文件内都可以写成birth-day**
  + **在properties文件中**，如果一行文本过长，那么可以写一个`\`字符，然后换行接着写
  + 对于文本，一般不需要加引号，就默认是字符串。但是yml文件确实提供了引号:
    + 单引号内的转义字符不会生效
    + 双引号内的转义字符会生效
  + 对于大文本，即多行文本
    + 使用`|`开头，然后将大文本写在其下方，**注意是正下方或右下，不能写的相对于该符号靠左**，它可以保留文本格式，即文本写的是什么样，输出就是什么样
    + 使用`>`开头，然后将大文本写在其下方，**注意是正下方或右下，不能写的相对于该符号靠左**，它会折叠换行符，改为一个空格隔开
  + 还可以使用`---`将多个yml文档合并在一个文档中，每个文档区依然认为内容独立

---

### （三）Thymeleaf

#### ①快速体验

+ [Thymeleaf](https://www.thymeleaf.org/)是一款用于前后端不分离时渲染页面的模板引擎，SpringBoot默认支持该模板引擎，但未导入对应场景
  + 除ThymeLeaf外，SpringBoot还默认支持以下引擎:
    + FreeMarker
    + Groovy
    + Mustache
+ 首先我们要导入场景

~~~xml

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>

~~~

+ SpringBoot有专门的ThymeleafAutoConfiguration类，在其内部类DefaultTemplateResolverConfiguration内的defaultTemplateResolver方法中，设置了寻找对应模板的前缀和后缀:

~~~java
    @Bean
    SpringResourceTemplateResolver defaultTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        ...
        resolver.setPrefix(this.properties.getPrefix());  // 设置寻找模板引擎的前缀
        resolver.setSuffix(this.properties.getSuffix());  // 设置寻找模板引擎的后缀
        ...
        return resolver;
    }
~~~

+ 我们通过查看getPrefix方法和getSuffix方法可以看到，默认的值分别为`classpath:/templates/`和`.html`
+ 也就是说，**该模板引擎默认从类路径下的tamplates目录下寻找xxx.html文件**
+ 现在我们可以开始编写一个简单的Thtmeleaf模板了:
  + 首先写一个controller，不要写@ResponseBody,直接返回我们想渲染的模板名称，也不需要带后缀，直接返回字符串即可
  + 在对应路径下声明一个对应的html模板，可以在其html标签下加上属性约束:`<html lang="en" xmlns:th="http://www.thymeleaf.org">`，这样idea会有提示
+ 然后就可以用了
+ [模板样例](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/hello.html)
+ [controller样例](../源码/SpringBoot/SpringBootThymeleaf/src/main/java/com/springboot/example/springbootthymeleaf/controller/ThymeleafController.java)

---

#### ②核心语法

|语法|作用|值|备注|样例|
|:---:|:---:|:---:|:---:|:---:|
|th:text|将文本值渲染到对应标签内|一般使用插值表达式插入|无|[样例1](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/hello.html)|
|th:utext|将HTML渲染到对应标签内|^|浏览器会当成HTML语句渲染|^|
|th:属性|渲染属性值|^|无|^|
|th:attr|批量渲染属性值|例:`th:attr="style=${style},src=${src}"`|无|^|
|th:if|如果其表达式为真，那么该属性所在标签会被渲染|一般使用插值表达式插入，并运算|无|^|
|th:switch|相当于switch语句|一般使用插值表达式插入|无|^|
|th:case|相当于case语句|^|无|^|
|th:object|变量选择，配合*{}插值表达式可以在子标签中引用该变量|^|无|^|
|th:each|遍历集合|例:`th:each="item,state : ${list}"`|[样例2](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/list.html)|
|th:fragment|定义模板|例:`th:fragment="xxx"`|无|[样例3](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/template.html)|
|th:insert|在标签内部插入对应组件|例:`th:insert="~{templateName :: fragmentName}"`|无|[样例4](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/useTemplate.html)|
|th:replace|将该标签替换为组件|例:`th:replace="~{templateName :: fragmentName}"`|无|^|

|插值表达式|作用|备注|
|:---:|:---:|:---:|
|${}|将request域中的变量取出使用|无|
|@{}|专门用于适配URL路径，会动态的加上后端的上下文路径|无|
|#{}|国际化消息|无|
|~{}|导入片段（模板）时使用|无|
|*{}|变量选择，需要配合th:object绑定对象|无|

|系统工具/内置对象|作用|备注|
|:---:|:---:|:---:|
|param|请求参数对象|无|
|session|session对象|无|
|application|context对象|无|
|#execInfo|模板执行消息|无|
|#messages|国际化消息|无|
|#uris|uri/url工具|无|
|#conversions|类型转换工具|无|
|#dates|日期工具，是java.util.Date的工具类|无|
|#calendars|日期工具，是java.util.Calendar的工具类|无|
|#temporals|JDK8+,java.time的工具类|无|
|#numbers|数字操作工具|无|
|#strings|字符串操作工具|无|
|#objects|对象操作工具|无|
|#bools|布尔值操作工具|无|
|#arrays|数组操作工具|无|
|#lists|List操作工具|无|
|#sets|Set操作工具|无|
|#maps|Map操作工具|无|
|#aggregates|集合聚合工具(sum、avg)|无|
|#ids|id生成工具|无|

+ 其它相关操作与java基本一致，但是有几个特殊的:
  + 布尔运算中，**与操作需要使用`and`关键字，或操作需要使用`or`关键字**
  + 字符串拼接时，可以在拼接的字符串开头和结尾加上`|`来避免传统的使用`+`符号进行拼接，而是使用类似模板字符串的拼接方式进行拼接
  + 条件运算发生了一些变化:
    + if-then： `(value)?(then)`
    + if-then-else: `(value)?(then):(else)`（三元运算符）
    + default: `(value)?:(defaultValue)`
  + 如果想在属性内表示字符串，需要使用单引号引起来
  + **如果想在标签内部直接插值，可以使用`[[...]]`或`[(...)]`进行插值**

---

#### ③遍历

+ 使用th:each可以对集合进行遍历:
  + `th:each="item,state : ${list}`
    + item表示当前遍历到的集合元素，变量名可以随便取
    + state表示当前遍历到的元素的状态，这是个键值对类型的集合对象
    + list表示被遍历的集合对象，它的名字取决于request域中的待遍历对象的变量名
+ state变量中有多个属性，这些属性都有他们各自的名称:

|属性名|作用|备注|
|:---:|:---:|:---:|
|index|索引|无|
|count|遍历到的是第几个元素|无|
|size|遍历的元素总量|无|
|current|遍历到的当前元素值|无|
|even|当前的count是否是偶数|无|
|odd|当前的count是否是奇数|无|
|first|是否是第一个元素|无|
|last|是否是最后一个元素|无|

+ [样例](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/list.html)

---

#### ④属性优先级

+ Order值越低，优先级越高

|Order|分类|属性|
|:---:|:---:|:---:|
|1|片段包含|th:insert<br>th:replace|
|2|遍历|th:each|
|3|判断|th:if<br>th:unless<br>th:switch<br>th:case|
|4|定义本地变量|th:object<br>th:with|
|5|通用方式属性修改|th:attr<br>th:attrprepend<br>th:attrappend|
|6|指定属性修改|th:value<br>th:href<br>th:src<br>...|
|7|文本值|th:text<br>th:utext|
|8|片段指定|th:fragment|
|9|片段移除|th:remove|

---

#### ⑤模板布局

+ 我们有时想将网页变成一些可复用的组件，Thymeleaf也为我们提供了这一功能:
  + 如果我们想定义一个可复用的组件，我们需要在该组件最外层的标签上加上`th:fragment`属性，并给该组件起一个名字
  + 接下来我们就可以在别的地方引用了
    + 使用`th:insert`或`th:replace`属性来引用该组件
    + 属性内使用`~{}`插值表达式来专门进行引用
    + 语法为`templateName :: componentName`，如我们的组件名字叫top，它所在的文件名是template，那么引用时就是`~{template :: top}`
+ [组件样例](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/template.html)
+ [调用组件样例](../源码/SpringBoot/SpringBootThymeleaf/src/main/resources/templates/useTemplate.html)

---

## 配置汇总

### （一）日志配置

#### ①日志简述

+ 在开发中，我们应该使用日志来记录信息

|日志门面|日志实现|
|:---:|:---:|
|JCL(Jakarta Commons Logging)|Log4J<br>JUL(java.util.logging)<br>Log4j2<br>Logback|
|SLF4J(Simple Logging Facade for Java)|^|
|jboss-logging|^|

+ SpringBoot的默认日志配置是`SLF4J+Logback`，但我们可以自定义想实现的日志，不过SpringBoot的默认日志配置已经够用了
+ 在Spring5版本后，commons-logging被spring直接自己封装了

---

#### ②实现原理

+ 核心场景`spring-boot-starter`内引入了`spring-boot-starter-logging`
+ 一般情况下，对应的依赖都会有一个自动配置类，叫XxxAutoConfiguration
+ 但是日志比较特殊，它需要在程序启动时就执行，因此不能在程序启动时再加载器配置项
+ 因此**日志利用的是监听器机制配置**好的
+ 不过，我们依然能够通过配置文件来修改日志的配置

---

#### ③输出格式

+ SpringBoot的默认输出格式为（从左到右）:
  + 时间和日期:精确到毫秒
  + 日志级别:根据情况，会打印**ERROR**、**WARN**、**INFO**、**DEBUG**或**TRACE**
  + 进程ID
  + ---:消息分隔符
  + 线程名:当前执行操作的方法所在的线程，使用[]包含
  + Logger名:通常是产生日志的类名
  + 消息:打印的日志信息
+ **Logback并没有FATAL级别的日志，取而代之的是ERROR**
+ 其默认的输出格式参数参照spring-boot包下的additional-spring-configuration-metadata.json文件
+ 默认输出值为:`%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}`
+ 可以修改为`%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} ===> %msg%n`

+ [配置类样例](../源码/SpringBoot/SpringBootLogging/src/main/resources/application.properties)

---

#### ④日志级别与分组

+ 日志级别一般分为如下类别（由高到低）:
  + ALL:打印所有日志
  + TRACE:追踪框架的详细流程日志并打印
  + DEBUG:开发调试细节日志
  + INFO:关键的、感兴趣的日志
  + WARN:警告但不是错误的日志，如版本过时的信息
  + ERROR:业务错误日志，如出现各种异常
  + FATAL:致命错误日志，如JVM虚拟机系统崩溃
  + OFF:关闭所有日志记录
+ 在以上的日志级别中，**我们可以主动输出日志信息的有TRACE、DEBUG、INFO、WARN、ERROR这五个**
+ **在指定某一日志级别后，系统只会打印该级别或该级别以下的日志信息**，如指定了INFO作为日志级别，那么系统仅会打印INFO、WARN、ERROR、FATAL等日志信息，而级别高的DEBUG和TRACE不会打印
+ **SpringBoot默认使用Logback作为日志实现依赖，且默认指定的日志级别为INFO**
+ SpringBoot支持我们自定义全局的日志级别，或自定义某一类的日志级别
  + 我们可以通过[下表]查阅如何指定，或者查看[样例]()
  + 但是，当我们想手动指定的类的日志级别变多时，配置会非常繁琐，因此SpringBoot又提供了分组功能来简化配置
    + 我们可以将多个类组成一个组，然后将这一个组看成一个整体，进行日志级别的指定
    + SpringBoot已经为我们提供了两个默认的组:
      + web组:包含org.springframework.core.codec、org.springframework.http、org.springframework.web、org.springframework.boot.actuate.endpoint.web、org.springframework.boot.web.servlet.ServletContextInitializerBeans类
      + sql组:包含org.springframework.jdbc.core、org.hibernate.SQL、org.jooq.tools.LoggerListener类

+ [配置类样例](../源码/SpringBoot/SpringBootLogging/src/main/resources/application.properties)

---

#### ⑤文件输出、归档与滚动切割

+ **文件输出**
  + 通过`logging.file.name`可以指定输出的文件的名字，如果文件不存在，会在当前项目所在目录内创建一个，然后输出日志
    + 可以为其指定路径，使得日志文件保存在指定路径下，且名称也可以自定义，因此**推荐使用该方式**
  + 通过`logging.file.path`可以指定输出的文件的路径，使用该方式输出文件，spring会自动创建一个Spring.log文件。当`logging.file.name`配置存在时，后者优先级更高
+ **归档**
  + 通过某种区分方式来将日志划分到指定目录叫做归档（一般按时间，如一天对应一个目录）
  + 该方式主要是为了防止一直将日志输出到一个文件导致文件过大的问题
  + 归档是只有当一整天结束时，SpringBoot才会进行本日的日志归档，因此我们如果想看到效果，需要向后调一天
+ **滚动切割**
  + 即使使用了归档，可能也会出现日志文件过大的情况，因此我们可以按某些标准（如限制单文件最大大小）来将文件切片
  + 该方式进一步解决了文件过大的问题
  + 这玩意貌似只能识别整数，比如设置的是1MB大小的限制，它会等到日志文件到2MB以上才会进行切割

+ [配置类样例](../源码/SpringBoot/SpringBootLogging/src/main/resources/application.properties)

---

#### ⑥自定义配置

+ 如果我们认为SpringBoot的配置文件用着不爽，我们也可以使用传统的xml文件的方式来自定义日志
  + xml文件的命名需要严格遵守SpringBoot的对应规范:
    + 建议名称都以`xxx-spring.xml`的规范命名，因为这样SpringBoot可以完全控制该配置文件

|日志系统|文件命名规范|
|:---:|:---:|
|Logback|logback-spring.xml(**推荐**), logback-spring.groovy, logback.xml, or logback.groovy|
|Log4j2|log4j2-spring.xml（**推荐**） or log4j2.xml|
|JDK (Java Util Logging)|logging.properties|

+ 另外，当我们想切换日志组合时，我们需要先排除掉SpringBoot所指定的默认日志组合
  + 我们可以利用Maven的就近原则，在我们的项目内直接引入spring-boot-starter依赖，然后在下面排除掉spring-boot-starter-logging依赖

~~~xml

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
      <exclusions>
          <exclusion>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-logging</artifactId>
          </exclusion>
      </exclusions>
  </dependency>

~~~

+ 接下来导入我们对应的场景，比如我们想导入log4j2的场景，导入前需要确认[官方](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging)是否支持

~~~xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-log4j2</artifactId>
  </dependency>
~~~

+ 然后就可以使用了
+ log4j2的性能相较于Logback有很大提升，推荐使用log4j2
+ 如果想使用log4j2,**Spring官方推荐其配置文件写log4j2-spring.xml**
+ **log4j2支持yaml和json格式的配置文件**:

|文件格式|实现支持需要导入的依赖|规范文件名|
|YAML|**com.fasterxml.jackson.core:jackson-databind**和**com.fasterxml.jackson.dataformat:jackson-dataformat-yaml**|log4j2.yaml或log4j2.yml|
|JSON|com.fasterxml.jackson.core:jackson-databind|log4j2.json或log4j2.jsn|

+ [项目pom.xml文件](../源码/SpringBoot/SpringBootLogging/pom.xml)
+ [配置类样例](../源码/SpringBoot/SpringBootLogging/src/main/resources/application.properties)
+ [log4j2-spring.xml样例](../源码/SpringBoot/SpringBootLogging/src/main/resources/log4j2-spring.xml)

---

#### ⑦配置总览

|配置|作用|备注|
|:---:|:---:|:---:|
|logging.level.{root\|sql\|web\|类的全类名\|自定义组名}|指定全局/sql组/web组/类/自定义组的日志级别|无|
|logging.group.自定义组名|将多个类划分为一个组|无|
|logging.file.name|指定日志输出的文件|也可以写路径，如果是相对路径，那么是相对于项目所在目录的|
|loggging.file.path|指定日志输出的路径|优先级没有logging.file.name高|
|logging.logback.rollingpolicy.file-name-pattern|指定日志归档的命名格式，默认值是`${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz`|从配置项可以看出，只有使用Logback才能使用该配置|
|logging.logback.rollingpolicy.clean-history-on-start|应用启动前是否清除以前日志文件（默认为false）|^|
|logging.logback.rollingpolicy.max-file-size|指定每个日志文件的最大大小|^|
|logging.logback.rollingpolicy.total-size-cap|指定日志文件总大小超过指定大小后，就删除旧的日志文件（默认0B）|^|
|logging.logback.rollingpolicy.max-history|日志文件保存的最大天数（默认7，单位天）|^|

---

### （二）Web配置

#### ①资源配置

+ SpringBoot的Web开发能力，是由SpringMVC实现的
+ 如果我们想配置Web,我们需要明确配置什么东西，下面是与Web相关的AutoConfiguration:

~~~java
    org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration
    org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration
    ====以下是响应式web场景和现在的没关系======
    org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration
    org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration
    ================以上没关系=================
    org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
    org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
    org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
    org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
    org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
    org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
~~~

+ 上面是与web相关的AutoConfiguration类，它们都是负责自动装配我们的Web项目的
+ 同时，它们对应的配置类绑定了配置文件的一堆配置项:
  + SpringMVC的所有配置 spring.mvc
  + Web场景通用配置 spring.web
  + 文件上传配置 spring.servlet.multipart
  + 服务器的配置 server: 比如：编码方式

##### ⅠWebMvcAutoConfiguration类

+ 与SpringMVC相关的自动配置功能，都由WebMvcAutoConfiguration类来装配:

~~~java
    @AutoConfiguration(
        after = {DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class}
    )  // 该配置类的初始化在这些配置类之后
    @ConditionalOnWebApplication(
        type = Type.SERVLET
    )  // 如果type属于SERVLET时才生效
    @ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})  // 类路径下存在这些类才生效
    @ConditionalOnMissingBean({WebMvcConfigurationSupport.class})  // 不存在WebMvcConfigurationSupport的bean时生效，默认确实不存在
    @AutoConfigureOrder(-2147483638)  // 设置初始化优先级
    @ImportRuntimeHints({WebResourcesRuntimeHints.class})  // 不知道干嘛的
    public class WebMvcAutoConfiguration {
        ...


        @Bean
        @ConditionalOnMissingBean({HiddenHttpMethodFilter.class})
        @ConditionalOnProperty(
            prefix = "spring.mvc.hiddenmethod.filter",
            name = {"enabled"}
        )
        // 该过滤器用来支持服务器接收表单提交Rest请求
        public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
            return new OrderedHiddenHttpMethodFilter();
        }

        @Bean
        @ConditionalOnMissingBean({FormContentFilter.class})
        @ConditionalOnProperty(
            prefix = "spring.mvc.formcontent.filter",
            name = {"enabled"},
            matchIfMissing = true
        )
        // 该过滤器可以得到表单提交到的内容
        public OrderedFormContentFilter formContentFilter() {
            return new OrderedFormContentFilter();
        }

        ...

        private <T extends AbstractUrlHandlerMapping> T createWelcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider, WelcomePageHandlerMappingFactory<T> factory) {
            TemplateAvailabilityProviders templateAvailabilityProviders = new TemplateAvailabilityProviders(applicationContext);
            // 得到静态的前端请求路径，即/**
            String staticPathPattern = this.mvcProperties.getStaticPathPattern();
            // this.getIndexHtmlResource()方法会得到四个默认的请求资源路径下，包含默认的index前缀文件的Resource对象
            // 所以该方法的作用就是建立/**的前端请求路径和默认寻找静态资源路径下的index文件路径之间的映射
            T handlerMapping = factory.create(templateAvailabilityProviders, applicationContext, this.getIndexHtmlResource(), staticPathPattern);
            // 下面又配了点东西，看不懂，不过没关系
            handlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
            handlerMapping.setCorsConfigurations(this.getCorsConfigurations());
            return handlerMapping;
        }
    }
~~~

---

##### ⅡWebMvcConfigurer接口

+ WebMvcConfigurer接口内有多个方法，我们只需要让我们的配置类实现对应的方法，就可以使我们的项目支持一些功能

|方法|作用|备注|
|:---:|:---:|:---:|
|addArgumentResolvers|添加请求参数的解析器|无|
|addCorsMappings|添加跨域请求配置|无|
|addFormatters|添加格式化器|无|
|addInterceptors|添加拦截器|无|
|addResourceHandlers|添加静态资源处理器，用来处理静态资源的映射关系|无|
|addReturnValueHandlers|添加返回值处理器|无|
|addViewControllers|添加视图控制器|无|
|configureAsyncSupport|配置异步支持|无|
|configureContentNegotiation|配置内容协商|无|
|configureDefaultServletHandling|配置默认处理的请求路径，默认为/，表示接收所有请求|无|
|configureHandlerExceptionResolvers|配置异常处理器|无|
|configureMessageConverters|配置消息转换器|无|
|configurePathMatch|配置路径匹配|无|
|configureViewResolvers|配置视图解析|无|
|extendsHandlerExceptionResolvers|拓展异常处理器|无|
|extendsMessageConverters|拓展消息转换器|无|

+ 我们可以让配置类实现WebMvcConfigurer接口，在里面实现对应的方法来进行相关的配置，**推荐配置类不要加@EnableWebMvc注解，因为不加这个注解可以在保留SpringBoot默认配置的前提下追加配置，而加了以后SpringBoot的相关默认配置就会失效，我们就需要全部自己进行相应的配置**
+ 另外，我们其实也可以直接提供一个WebMvcConfigurer接口对象，在该对象内实现指定的方法(使用匿名内部类)。
+ 第二种方式也可行的原因是，**SpringBoot在底层会把全部的实现了WebMvcConfigurer接口的对象汇聚成一个List，然后逐个调用对应的方法**，我们的配置类实现了该接口，且配置类也会加入IOC容器，因此配置类对象也满足WebMvcConfigurer接口的对象条件，故也会执行里面的方法
  + 详细的源码在DelegatingWebMvcConfiguration，该类是WebMvcAutoConfiguration的一个内部类EnableWebMvcConfiguration父类

~~~java
    @Configuration(proxyBeanMethods = false)
    public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
        private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();

        @Autowired(required = false)
        // 收集全部的WebMvcConfigurer实现类对象，并加入configurers中，方便以后使用
        public void setConfigurers(List<WebMvcConfigurer> configurers) {
            if (!CollectionUtils.isEmpty(configurers)) {
                this.configurers.addWebMvcConfigurers(configurers);  
            }

        }
    }
~~~

---

##### ⅢWebMvcConfigurationSupport类

+ 提供了很多的默认设置。
+ 判断系统中是否有相应的类：如果有，就加入相应的HttpMessageConverter

~~~java
    static {
        ClassLoader classLoader = WebMvcConfigurationSupport.class.getClassLoader();
        romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", classLoader);
        jaxb2Present = ClassUtils.isPresent("jakarta.xml.bind.Binder", classLoader);
        jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
        jackson2SmilePresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
        jackson2CborPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.cbor.CBORFactory", classLoader);
        gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        jsonbPresent = ClassUtils.isPresent("jakarta.json.bind.Jsonb", classLoader);
        kotlinSerializationCborPresent = ClassUtils.isPresent("kotlinx.serialization.cbor.Cbor", classLoader);
        kotlinSerializationJsonPresent = ClassUtils.isPresent("kotlinx.serialization.json.Json", classLoader);
        kotlinSerializationProtobufPresent = ClassUtils.isPresent("kotlinx.serialization.protobuf.ProtoBuf", classLoader);
    }

~~~

---

##### Ⅳ静态资源配置

###### <一>源码分析

+ SpringBoot通过WebMvcAutoConfiguration类向IOC容器内自动装配对应的bean:
  + 该自动配置类中自动配置了静态资源相关的配置，具体为:
    + 当前端请求/webjars/**相关资源时，后端从默认从classpath:META-INF/resources/webjars文件夹内找
    + 当前端请求/**相关资源时，后端默认从下面四个路径下找:
      + classpath:/META-INF/resources/
      + classpath:/resources/
      + classpath:/static/
      + classpath:/public/
  + 同时，该自动配置类内也配置了缓存相关的内容，大概有
    + period:**什么时候浏览器找服务器要新的资源**，单位是秒，默认空
    + cacheControl:**缓存控制对象**，默认没什么用
    + useLastModified:**开启后，浏览器找服务器请求资源前，先发送请求资源是否发生了更改，在服务器确认后，再发送请求**。默认是true
+ WebMvcAutoConfiguration类内的WebMvcAutoConfigurationAdapter内部类配置了静态资源的相关路径匹配映射:

~~~java

    // 下面是WebMvcAutoConfiguration内声明的内部类，可以看到它实现了WebMvcConfigurer接口

    @Configuration(proxyBeanMethods = false)  // 声明为配置类
    @Import({EnableWebMvcConfiguration.class})  // 导入对应类加入IOC容器
    @EnableConfigurationProperties({WebMvcProperties.class, WebProperties.class})  // 声明两个类通过属性依赖注入属性值，并将这俩类加入IOC容器
    @Order(0)  // 没用
    public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer, ServletContextAware {
        private static final Log logger = LogFactory.getLog(WebMvcConfigurer.class);
        private final WebProperties.Resources resourceProperties;
        private final WebMvcProperties mvcProperties;
        private final ListableBeanFactory beanFactory;
        private final ObjectProvider<HttpMessageConverters> messageConvertersProvider;
        private final ObjectProvider<DispatcherServletPath> dispatcherServletPath;
        private final ObjectProvider<ServletRegistrationBean<?>> servletRegistrations;
        private final ResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer;
        private ServletContext servletContext;

        // 内部类构造器
        public WebMvcAutoConfigurationAdapter(
            WebProperties webProperties, 
            WebMvcProperties mvcProperties, 
            ListableBeanFactory beanFactory, 
            ObjectProvider<HttpMessageConverters> messageConvertersProvider, 
            ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider, ObjectProvider<DispatcherServletPath> dispatcherServletPath, 
            ObjectProvider<ServletRegistrationBean<?>> servletRegistrations
        ) {
            this.resourceProperties = webProperties.getResources();
            this.mvcProperties = mvcProperties;
            this.beanFactory = beanFactory;
            this.messageConvertersProvider = messageConvertersProvider;
            this.resourceHandlerRegistrationCustomizer = (ResourceHandlerRegistrationCustomizer)resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
            this.dispatcherServletPath = dispatcherServletPath;
            this.servletRegistrations = servletRegistrations;
        }

        ...

        // 实现WebMvcConfigurer声明的addResourceHandler方法
        public void addResourceHandlers(ResourceHandlerRegistry registry) {

            // resourceProperties在上面的构造器中被赋值，而正常情况下addMappings属性的值都是true，因此一般情况下都会走else
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                //  添加请求路径与静态资源匹配路径
                //  这里的getWebjarsPathPattern方法得到的路径是"/webjars/**"，它直接被写在了WebMvcProperties内的属性中
                // 这句话的意思是，当前端请求的/webjars/**资源时，后端去类路径下的META-INF/resources/webjars文件夹下找
                // 这里调用的是重载的addResourceHandler方法
                this.addResourceHandler(registry, this.mvcProperties.getWebjarsPathPattern(), "classpath:/META-INF/resources/webjars/");
                /* 这里的getStaticPathPattern方法返回"/**"，而下面的getStaticLocations方法返回值是 
                    new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"}
                    这个值就是WebProperties类的内部类Resources类的staticLocations属性
                    它在构造器中被赋值为CLASSPATH_RESOURCE_LOCATIONS，而上面的值就是CLASSPATH_RESOURCE_LOCATIONS属性被显式赋在代码中的值
                    这里调用的也是重载的addResourceHandler方法，但是调用的方法是不一样的，因为二者传的参数不一样
                */
                this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
                    registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                    // 查看是否配置了ServletContext类对象，这取决于我们是否配置了ServletContext
                    if (this.servletContext != null) {
                        ServletContextResource resource = new ServletContextResource(this.servletContext, "/");
                        // 为后端响应前端的请求路径再添加一个路径映射
                        registration.addResourceLocations(new Resource[]{resource});
                    }

                });
                // 整体上来说，SpringBoot让WebMvcAutoConfigurationAdapter类对象先调用其重载的方法，再经过重载的方法将前端请求路径和后端寻找路径加入映射中去
            }
        }

        private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, String... locations) {
            // 这里最后调的也是下面的方法
            this.addResourceHandler(registry, pattern, (registration) -> {
                // 设置对应的后端寻找资源的路径
                registration.addResourceLocations(locations);
            });
        }

        private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, Consumer<ResourceHandlerRegistration> customizer) {
            // 判断要加入的pattern是否已经有映射了
            if (!registry.hasMappingForPattern(pattern)) {
                // 这里真正将前端请求的路径加入映射
                ResourceHandlerRegistration registration = registry.addResourceHandler(new String[]{pattern});
                // 这里将对应前端请求的后端寻找资源路径加入映射
                customizer.accept(registration);
                // 设置缓存
                registration.setCachePeriod(this.getSeconds(this.resourceProperties.getCache().getPeriod()));
                registration.setCacheControl(this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl());
                registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
                this.customizeResourceHandlerRegistration(registration);
            }
        }
    }

~~~

+ 在上面的代码中，有些代码与WebMvcProperties类和WebProperties配置类有关
  + WebMvcProperties类包含了前端的请求路径相关的配置
  + WebProperties类包含了后端对于请求路径和静态资源的响应相关的配置

~~~java

    //WebMvcProperties类的部分代码:
    // 从这里可以看出，如果想mvc的相关配置，对应前缀是spring.mvc
    @ConfigurationProperties(prefix = "spring.mvc")
    public class WebMvcProperties {
        private String staticPathPattern = "/**";  // 静态资源匹配路径
        private String webjarsPathPattern = "/webjars/**";  // webjars资源匹配路径
    }
    // 得到默认的Webjars路径映射
    public String getWebjarsPathPattern() {
        return this.webjarsPathPattern;
    }


    // WebProperties类的部分代码:
    // 从这里可以看出，如果想配置web相关配置，对应前缀是spring.web
    @ConfigurationProperties("spring.web")
    public class WebProperties {
        // Resources类是WebProperties的内部类
        public static class Resources {
            // 默认后端寻找静态资源锁寻找的路径
            private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
            private String[] staticLocations;  // 静态资源锁寻找的路径
            private boolean addMappings;  
            private boolean customized;
            private final Chain chain;
            private final Cache cache;  // 前端缓存相关配置

            public Resources() {
                this.staticLocations = CLASSPATH_RESOURCE_LOCATIONS;  // staticLocations属性直接被赋值为CLASSPATH_RESOURCE_LOCATIONS，就是上面那一串
                this.addMappings = true;  //addMappings属性默认是true
                this.customized = false;
                this.chain = new Chain();
                this.cache = new Cache();
            }

            // 得到后端寻找静态资源的默认路径
            public String[] getStaticLocations() {
                return this.staticLocations;  
            }

              // 注意，这个Cache类是Resources的内部类
            public static class Cache {
                private boolean customized = false;
                @DurationUnit(ChronoUnit.SECONDS)
                private Duration period;  // period默认是空
                private final Cachecontrol cachecontrol = new Cachecontrol();  // cachecontrol会得到对应的对象，但是该对象没有进行任何配置
                private boolean useLastModified = true;  // 默认开启useLastModified
                // Cachecontrol类是Cache类的内部类
                public static class Cachecontrol {
                    private boolean customized = false;
                    @DurationUnit(ChronoUnit.SECONDS)
                    private Duration maxAge;
                    private Boolean noCache;
                    private Boolean noStore;
                    private Boolean mustRevalidate;
                    private Boolean noTransform;
                    private Boolean cachePublic;
                    private Boolean cachePrivate;
                    private Boolean proxyRevalidate;
                    @DurationUnit(ChronoUnit.SECONDS)
                    private Duration staleWhileRevalidate;
                    @DurationUnit(ChronoUnit.SECONDS)
                    private Duration staleIfError;
                    @DurationUnit(ChronoUnit.SECONDS)
                    private Duration sMaxAge;
                }
            }


        }
    }

~~~

---

###### <二>静态资源配置

+ 静态资源配置可以通过两种方式配置:
  + 通过配置文件配置:由于在上面出现了配置文件前缀，因此只要找对应的前缀，配置对应的属性即可
  + 通过代码进行配置:通过配置类实现WebMvcConfigurer接口并实现其addResourceHandlers来进行配置，但**不要在配置类上加@EnableWebMvc注解，一旦加上，原有的默认配置会失效**
+ 另外，部分浏览器会自动请求favicon.ico这个文件，因此在`/**`路径想映射的后端路径内加一个favicon.ico文件，就可以让网页显示出自定义的小图标了

|配置|作用|属性值|备注|
|:---:|:---:|:---:|:---:|
|spring.mvc.static-path-pattern|用来**设置匹配的前端请求静态资源的路径**|字符串值|无|
|spring.mvc.webjars-path-pattern|用来**设置匹配的前端请求webjars资源的路径**|字符串值|无|
|spring.web.resources.static-locations|配置用来设置后端处理静态资源要寻找的目录，**它会覆盖掉SpringBoot默认配置的四个路径**|字符串值|**针对webjars的路径匹配依然有效，因为根据源码，webjars相关的路径匹配被单独配置了，而该项配置与webjars的路径匹配没有关系**|
|spring.web.resources.add-mappings|开启静态资源映射|默认为true|无|
|spring.web.resources.cache.period|配置浏览器使用资源的大概时间|数值，单位秒|**如果配置了控制项，该配置会被覆盖**|
|spring.web.resources.cache.use-last-modified|配置是否在浏览器找服务器请求资源前，先发送请求确认资源是否发生了更改|布尔值|无|
|spring.web.resources.cache.cachecontrol.max-age|配置浏览器使用缓存的最大时间，在此期间，浏览器会使用缓存加载资源|数值，单位秒|无|
|spring.web.resources.cache.cachecontrol.cache-public|设置是否共享缓存|布尔值|无|

+ 可以通过查看状态码来确认是否有缓存，**出现304状态码或网络栏中的履行者显示为`disk cache`即为使用了缓存**
  + 刷新网页不会使用缓存，详情见下图:
    + 其中Etag 是 HTTP 响应头部的一部分，用于标识资源的版本。它通常由服务器生成，并在响应中发送给客户端

![缓存机制](../文件/图片/SpringBoot图片/HTTP缓存机制.png)

+ [配置文件](../源码/SpringBoot/SpringBootWebStaticResource/src/main/resources/application.properties)
+ [配置类](../源码/SpringBoot/SpringBootWebStaticResource/src/main/java/com/springboot/example/springbootwebstaticresource/config/MyConfig.java)

---

#### ②路径匹配

+ Spring5.3之前，只支持AntPathMatcher的路径匹配策略，在Spring5.3时，添加了新的PathPatternParser的路径匹配策略
+ 我们也可以在配置文件内进行相对的配置

|Ant风格通配符|作用|备注|样例|
|:---:|:---:|:---:|:---:|
|*|匹配一层下所有字符|无|`*.html`表示匹配任意html文件|
|?|表示匹配一个任意字符|无|`/fol?er/*.html`表示匹配fol(任意字符)er目录下的任意html文件|
|**|匹配后面的所有层|无|`/folder2/**/*.jsp` 匹配在folder2目录下任意目录深度的.jsp文件|
|{name}|将对应层的值取出，放入name中|无|`/{type}/{id}.html` 匹配任意文件名为{id}.html，在任意命名的{type}目录下的文件|
|[]|匹配对应的字符集合|无|无|

+ PathPatternParser兼容 AntPathMatcher语法，并支持更多类型的路径模式，**它的效率较PathPatternParser的效率高**
+ **PathPatternParser的`**`多段匹配仅能写在路径最后，不能再在中间写**。如果想这样用，需要在配置文件内把匹配准则改为PathPatternParser
+ 新版的默认路径匹配规则是PathPatternParser匹配原则
+ 使用`spring.mvc.pathmatch.matching-strategy`来手动修改路径匹配原则
  + ant_path_matcher表示恢复到AntPathMatcher
  + path_pattern_parser表示改为新版匹配原则

---

#### ③内容协商

##### Ⅰ默认协商

+ 如果我们有多个端向服务器器发送同一个请求，但是各个端期望服务器返回的请求格式不一致，此时SpringBoot会根据前端传来的**请求头参数(Accept)**或**路径参数(Param)**来进行对应的返回格式转换，该操作被称为**内容协商**
  + SpringBoot默认开启基于请求头的内容协商，服务器会根据Accept请求参数来确认使用什么格式进行返回。通过`spring.mvc.contentnegotiation.favor-parameter`来配置是否开启，默认是true
  + SpringBoot**默认不开启**基于路径参数的内容协商
    + 我们需要配置`spring.mvc.contentnegotiation.favor-parameter`来手动设置为true，以开启该方式的内容协商，默认的该方式的请求参数名为format
    + 还可以设置`spring.mvc.contentnegotiation.parameter-name`参数来手动设置以路径参数请求返回值类型时，传递该返回值类型的请求参数的名字。即SpringBoot会依据该配置的值去请求路径中去寻找对应的参数名，然后读取该值期望的返回值类型
    + **使用该方式请求的值与Accept请求值略有不同，如Accept指定的application/json，请求参数仅需要传递json即可**

---

##### Ⅱ自定义内容返回

+ SpringBoot的默认转换器能转换的格式实在有限，因此我们需要会怎么自定义内容返回
+ 我们以增加yaml返回值类型为例，来举例如何自定义内容返回
+ 首先需要导入相关依赖:

~~~xml

  <dependency>
      <groupId>com.fasterxml.jackson.dataformat</groupId>
      <artifactId>jackson-dataformat-yaml</artifactId>
  </dependency>

~~~

+ 在配置类中添加HttpMessageConverter组件，将能够转换yaml文件格式的对象添加进去

~~~java

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override //配置一个能把对象转为yaml的messageConverter
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyYamlHttpMessageConverter());
            }
        };
    }

~~~

+ 编写配置新增支持的媒体类型:
  + spring.mvc.contentnegotiation.media-types.{type}=aaa/bbb
  + 其中type是我们给这个媒体类型起的名字，这个名字是用来路径传参的时候携带的值，比如`spring.mvc.contentnegotiation.media-types.yaml=text/yaml`,那么路径传参的时候请求参数就是`type=yaml`

~~~properties
    spring.mvc.contentnegotiation.media-types.yaml=text/yaml
~~~

+ 创建一个实现了HttpMessageConverter接口的类
  + SpringBoot提供了AbstractHttpMessageConverter类，供我们更简便的实现HttpMessageConverter接口，我们只需继承该类即可
+ 继承了AbstractHttpMessageConverter类**要实现三个方法**
  + protected boolean supports(Class clazz)这里用来**筛选我们的转换器对象能够把什么类型的对象转换为我们期望的格式**
  + protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException:这玩意是**用来将前端传来的参数转换为被@RequestBody注解作用的变量对象**的
  + protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException:这玩意是**用来将我们的handler执行结果转换为我们期望的格式**的
+ [自定义实现类样例](../源码/SpringBoot/SpringBootMessageConverter/src/main/java/com/springboot/example/springbootmessageconverter/component/MyHttpMessageConverter.java)
+ 提示:如果使用浏览器进行请求测试，**浏览器因为无法解析yaml格式的返回值，会把它下载下来保存为一个文件**
---

##### ⅢDispatcherServlet执行的大致流程

+ `spring-boot-starter-web`场景导入了`spring-boot-starter-json`场景，而该场景又导入了jackson依赖，使**SpringBoot项目默认就支持JSON数据转换**
+ DispatcherServlet执行的大致流程:
  + 该类对象拦截请求，并执行doService方法，在方法内执行了doDispatch方法
  + 在doDispatch方法内通过执行getHandlerAdapter得到HandlerAdapter派发器对象ha，接下来执行派发器对象的handle方法来开始执行方法
  + 派发器对象是RequestMappingHandlerAdapter类对象，但该类没有实现handle方法，因此执行的handle方法是其父类AbstractHandlerMethodAdapter类的handle方法
  + 其父类的handle方法调用了handleInternal方法，而在handleInternal方法内部调用了invokeHandlerMethod方法执行
    + 在invokeHandlerMethod方法执行前，需要准备好两个东西
      + HandlerMethodArgumentResolver：**参数解析器，确定目标方法每个参数值**
      + HandlerMethodReturnValueHandler：**返回值处理器，确定目标方法的返回值该怎么处理**
  + 在invokeHandlerMethod方法最后，执行了ServletInvocableHandlerMethod类对象的invokeAndHandle方法来执行handler
  + invokeAndHandle方法又调用invokeForRequest来获得handler方法的执行结果
  + ServletInvocableHandlerMethod类并未实现invokeForRequest方法，因此执行其父类InvocableHandlerMethod的invokeForRequest方法
  + invokeForRequest方法调用doInvoke方法来执行handler方法
  + 最终，**在doInvoke方法内，通过得到handler方法的Method对象，通过反射的方式来调用我们定义的handler方法，得到返回结果后开始向上返回**
  + 现在回到invokeAndHandle方法内，invokeForRequest方法已经返回，开始处理返回值
  + 在方法的try-catch语句内调用HandlerMethodReturnValueHandlerComposite类对象的handleReturnValue方法
  + 在handleReturnValue方法中，执行了selectHandler方法来筛选出**能处理请求指定的返回方式的处理对象**，该对象会直接执行下面的handleReturnValue方法
  + 如果方法被@ResponseBody注解作用，那么得到的处理对象是RequestResponseBodyMethodProcessor类对象,它是HandlerMethodReturnValueHandler接口对象。**handleReturnValue方法调用了该处理对象的handleReturnValue方法**
  + 该方法的实际执行者是RequestResponseBodyMethodProcessor类对象
  + 该方法在调用了writeWithMessageConverters方法，来向HttpResponse对象内写入最终内容
  + **方法在最后使用GenericHttpMessageConverter对象的write方法，最终将我们handler方法的返回值转换成对应的返回类型，然后写入到response中**
+ 总流程:`DispatcherServlet.doService`->`this.doDispatch`->`HandlerAdapter.handle`->`AbstractHandlerMethodAdapter.handle`->`this.handleInternal`->`this.invokeHandlerMethod`->`ServletInvocableHandlerMethod.invokeAndHandle`->`this.invokeForRequest`->`this.doInvoke（使用反射执行handler,并向上返回）`->`ServletInvocableHandlerMethod.invokeAndHandle（方法继续执行）`->`HandlerMethodReturnValueHandlerComposite.handleReturnValue`->`RequestResponseBodyMethodProcessor.handleReturnValue`->`this.writeWithMessageConverters`->`GenericHttpMessageConverter.write（进行返回类型转换并加入response中）`

---

##### ⅣHttpMessageConverter执行原理

+ SpringBoot在HandlerMethodReturnValueHandlerComposite类对象的handleReturnValue方法一执行，就调用了selectHandler方法，来筛选出能处理请求指定的返回方式的

~~~java

    private HandlerMethodReturnValueHandler selectHandler(@Nullable Object value, MethodParameter returnType) {
            boolean isAsyncValue = this.isAsyncReturnValue(value, returnType);
            // 这个returnValueHandlers就是SpringBoot中默认的用于处理各种返回方式的处理对象组成的集合
            // 这个处理对象并不是XML转换器对象或者JSON转换器对象之类的东西，它是用于处理我们声明的该方法是以何种方式返回的处理对象
            // 比如我在类上声明了@ResponseBody注解，那么就会选择到RequestResponseBodyMethodProcessor对象
            // 因此它是处理返回方式的对象，不是处理返回类型的转换对象
            Iterator var4 = this.returnValueHandlers.iterator();

            HandlerMethodReturnValueHandler handler;
            do {
                do {
                    if (!var4.hasNext()) {
                        return null;
                    }

                    handler = (HandlerMethodReturnValueHandler)var4.next();
                } while(isAsyncValue && !(handler instanceof AsyncHandlerMethodReturnValueHandler));
            } while(!handler.supportsReturnType(returnType)); // 如果选中的handler满足类型匹配就停止循环，相当于找到了能进行处理的对象
            // supportsReturnType对于ResponseBody的判断原理就是判断方法是否被@ResponseBody注解作用

            return handler;
        }

~~~

+ 在得到对应的处理对象之后，调用处理对象的handleReturnValue方法，在handleReturnValue方法内最后又调用了writeWithMessageConverters方法进行最终的操作:

~~~java

    // 这是writeWithMessageConverters方法的部分截取

        ....
        MediaType selectedMediaType = null;  // 该变量用来表示前端想让后端返回的返回类型
        MediaType contentType = outputMessage.getHeaders().getContentType();
        boolean isContentTypePreset = contentType != null && contentType.isConcrete();
        if (isContentTypePreset) {
            ...
            selectedMediaType = contentType;
        }else{
            ....

            // 看不懂
            // 这个compatibleMediaTypes是前端发来的能够接受的返回格式组成的集合
            // 貌似路径参数会把请求头参数覆盖掉
            Iterator var15 = compatibleMediaTypes.iterator();

            while(var15.hasNext()) {
                MediaType mediaType = (MediaType)var15.next();
                if (mediaType.isConcrete()) {
                    selectedMediaType = mediaType;  // 最后赋的是这玩意
                    break;
                }

                if (mediaType.isPresentIn(ALL_APPLICATION_MEDIA_TYPES)) {
                    selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
                    break;
                }
            }
        }

        if (selectedMediaType != null) {
            // 什么抽象代码块
            label166: {
                selectedMediaType = selectedMediaType.removeQualityValue();
                // messageConverters里面是能够进行对应的返回值格式的转换器,这里调用iterator方法就是要一个一个遍历来看哪个转换器能够进行对应格式的转换
                // 可以看到messageConverters是一个List<HttpMessageConverter<?>>对象，也就是说里面的元素都是HttpMessageConverter接口对象
                Iterator var21 = this.messageConverters.iterator();

                HttpMessageConverter converter;  // 提前声明converter变量
                GenericHttpMessageConverter genericConverter;
                // 开始遍历
                while(true) {
                    // 遍历到最后直接跳出抽象代码块
                    if (!var21.hasNext()) {
                        break label166;
                    }
                    // 使用converter进行承接
                    converter = (HttpMessageConverter)var21.next();
                    GenericHttpMessageConverter var10000;
                    // 判断遍历到的converter是否属于GenericHttpMessageConverter相关实例对象
                    if (converter instanceof GenericHttpMessageConverter) {
                        GenericHttpMessageConverter ghmc = (GenericHttpMessageConverter)converter;
                        var10000 = ghmc;
                    } else {
                        var10000 = null;
                    }
                    // 把当前var10000，也就是当前converter赋给genericConverter，只是如果实例对象不满足条件赋null
                    genericConverter = var10000;
                    // 下面开始判断genericConverter能够进行相对应的类型转换
                    if (genericConverter != null) {
                        // 如果可以，直接跳出循环
                        if (((GenericHttpMessageConverter)converter).canWrite((Type)targetType, valueType, selectedMediaType)) {
                            break;
                        }
                        // 如果负责converter也能处理，也跳出循环
                    } else if (converter.canWrite(valueType, selectedMediaType)) {
                        break;
                    }
                }
                // 下面的代码一定是循环找到了能进行转换的对象才执行的
                body = this.getAdvice().beforeBodyWrite(body, returnType, selectedMediaType, converter.getClass(), inputMessage, outputMessage);
                if (body != null) {
                    
                    ...

                    if (genericConverter != null) {
                        // 进行对应的类型转换，并将其写进response中
                        genericConverter.write(body, (Type)targetType, selectedMediaType, outputMessage);
                    } else {
                        // 如果genericConverter!=null，那么就执行这个
                        converter.write(body, selectedMediaType, outputMessage);
                    }
                }

                ...

                return;
            }
        }

~~~

+ 因此，**我们如果想自定义内容返回，就需要向IOC容器内添加对应的HttpMessageConverter接口对象**

---

##### Ⅴ与WebMvcAutoConfiguration的关系

+ WebMvcAutoConfiguration中有一个内部类EnableWebMvcConfiguration，该类继承自DelegatingWebMvcConfiguration类，而DelegatingWebMvcConfiguration类又继承自WebMvcConfigurationSupport
+ 在资源匹配中我们已经得知，WebMvcConfigurationSupport类中有一个静态代码块，只要项目一开始运行，它的静态代码块就会判断依赖中是否有一些指定的依赖，如果有，就将相关的变量置为true

~~~java

    static {
        ClassLoader classLoader = WebMvcConfigurationSupport.class.getClassLoader();
        romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", classLoader);
        jaxb2Present = ClassUtils.isPresent("jakarta.xml.bind.Binder", classLoader);
        jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
        jackson2SmilePresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
        jackson2CborPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.cbor.CBORFactory", classLoader);
        gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        jsonbPresent = ClassUtils.isPresent("jakarta.json.bind.Jsonb", classLoader);
        kotlinSerializationCborPresent = ClassUtils.isPresent("kotlinx.serialization.cbor.Cbor", classLoader);
        kotlinSerializationJsonPresent = ClassUtils.isPresent("kotlinx.serialization.json.Json", classLoader);
        kotlinSerializationProtobufPresent = ClassUtils.isPresent("kotlinx.serialization.protobuf.ProtoBuf", classLoader);
    }

~~~

+ WebMvcConfigurationSupport类中提供了getMessageConverters的方法:
  

~~~java
    protected final List<HttpMessageConverter<?>> getMessageConverters() {
        if (this.messageConverters == null) {
            this.messageConverters = new ArrayList();
            this.configureMessageConverters(this.messageConverters);
            if (this.messageConverters.isEmpty()) {
                // 配置全在该方法内
                this.addDefaultHttpMessageConverters(this.messageConverters);
            }

            this.extendMessageConverters(this.messageConverters);
        }

        return this.messageConverters;
    }
~~~

+ 这里主要看全都是空的情况下的addDefaultMessageConverters方法

~~~java
    protected final void addDefaultHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        // 这里导入了一堆的默认转换器
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new ResourceRegionHttpMessageConverter());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        if (romePresent) {
            messageConverters.add(new AtomFeedHttpMessageConverter());
            messageConverters.add(new RssChannelHttpMessageConverter());
        }

        Jackson2ObjectMapperBuilder builder;
        // 静态代码块中被赋值的变量，在这里被使用了
        if (jackson2XmlPresent) {
            builder = Jackson2ObjectMapperBuilder.xml();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }

            messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
        } else if (jaxb2Present) {
            messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (kotlinSerializationCborPresent) {
            messageConverters.add(new KotlinSerializationCborHttpMessageConverter());
        }

        if (kotlinSerializationJsonPresent) {
            messageConverters.add(new KotlinSerializationJsonHttpMessageConverter());
        }

        if (kotlinSerializationProtobufPresent) {
            messageConverters.add(new KotlinSerializationProtobufHttpMessageConverter());
        }

        if (jackson2Present) {
            builder = Jackson2ObjectMapperBuilder.json();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }

            messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        } else if (gsonPresent) {
            messageConverters.add(new GsonHttpMessageConverter());
        } else if (jsonbPresent) {
            messageConverters.add(new JsonbHttpMessageConverter());
        }

        if (jackson2SmilePresent) {
            builder = Jackson2ObjectMapperBuilder.smile();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }

            messageConverters.add(new MappingJackson2SmileHttpMessageConverter(builder.build()));
        }

        if (jackson2CborPresent) {
            builder = Jackson2ObjectMapperBuilder.cbor();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }

            messageConverters.add(new MappingJackson2CborHttpMessageConverter(builder.build()));
        }

    }
~~~

---

### （三）配置文件

|分组|配置|作用|值|备注|
|:---:|:---:|:---:|:---:|:---:|
|日志|logging.level.{root\|sql\|web\|类的全类名\|自定义组名}|指定全局/sql组/web组/类/自定义组的日志级别|字符串值|无|
|^|logging.group.自定义组名|将多个类划分为一个组|全类名|无|
|^|logging.file.name|指定日志输出的文件|文件路径|也可以写路径，如果是相对路径，那么是相对于项目所在目录的|
|^|loggging.file.path|指定日志输出的路径|文件路径|优先级没有logging.file.name高|
|^|logging.logback.rollingpolicy.file-name-pattern|指定日志归档的命名格式，默认值是`${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz`|配置规则|从配置项可以看出，只有使用Logback才能使用该配置|
|^|logging.logback.rollingpolicy.clean-history-on-start|应用启动前是否清除以前日志文件|布尔值，默认为false|^|
|^|logging.logback.rollingpolicy.max-file-size|指定每个日志文件的最大大小|数值|^|
|^|logging.logback.rollingpolicy.total-size-cap|指定日志文件总大小超过指定大小后，就删除旧的日志文件|大小，默认为0B|^|
|^|logging.logback.rollingpolicy.max-history|日志文件保存的最大天数|数值，默认7，单位天|^|
|静态资源|spring.mvc.static-path-pattern|用来**设置匹配的前端请求静态资源的路径**|字符串值|无|
|^|spring.mvc.webjars-path-pattern|用来**设置匹配的前端请求webjars资源的路径**|字符串值|无|
|^|spring.web.resources.static-locations|配置用来设置后端处理静态资源要寻找的目录，**它会覆盖掉SpringBoot默认配置的四个路径**|字符串值|**针对webjars的路径匹配依然有效，因为根据源码，webjars相关的路径匹配被单独配置了，而该项配置与webjars的路径匹配没有关系**|
|^|spring.web.resources.add-mappings|开启静态资源映射|默认为true|无|
|^|spring.web.resources.cache.period|配置浏览器使用资源的大概时间|数值，单位秒|**如果配置了控制项，该配置会被覆盖**|
|^|spring.web.resources.cache.use-last-modified|配置是否在浏览器找服务器请求资源前，先发送请求确认资源是否发生了更改|布尔值|无|
|^|spring.web.resources.cache.cachecontrol.max-age|配置浏览器使用缓存的最大时间，在此期间，浏览器会使用缓存加载资源|数值，单位秒|无|
|^|spring.web.resources.cache.cachecontrol.cache-public|设置是否共享缓存|布尔值|无|
|路径匹配|spring.mvc.pathmatch.matching-strategy|设置路径匹配原则|无|
|^|server.servlet.context-path|设置项目的上下文路径|路径字符串|详见[问题汇总](问题汇总.md)|
|内容协商|spring.mvc.contentnegotiation.favor-parameter|设置SpringBoot开启基于路径参数的内容协商|布尔值，默认false|无|
|^|spring.mvc.contentnegotiation.parameter-name|指定通过参数内容协商传递返回类型的参数名|字符串值|无|
|^|spring.mvc.contentnegotiation.media-types.{type}=aaa/bbb|type是我们给这个媒体类型起的名字，这个名字是用来路径传参的时候携带的值，比如`spring.mvc.contentnegotiation.media-types.yaml=text/yaml`,那么路径传参的时候请求参数就是`type=yaml`|媒体类型|无|

---

### （四）注解汇总

|分组|注解|作用|作用范围|备注|
|:---:|:---:|:---:|:---:|:---:|
|组件注册|@Configuration|声明对应类为配置类|类|无|
|^|@SpringBootConfiguration|生命对应类为SpringBoot项目的配置类|类|其实跟上面的注解没有区别|
|^|@Bean|使方法返回值作为bean加入到IOC容器内|方法|无|
|^|@Scope|声明该类型的bean是单实例还是多实例|方法|无|
|^|@Controller/@Service/@Repository/@Conponent|声明对应类属于控制层/服务层/DAO层/其它层，并将其纳入IOC容器管理|类|无|
|^|@Import|指定对应类受IOC容器管理|类|一般用于将第三方包下的类纳入IOC容器管理|
|^|@ComponentScan|开启组件扫描|类|作用于配置类上|
|条件注解|@ConditionalOnClass|若类路径下存在该类，那么触发指定行为|类/方法|触发指定行为需要利用其他注解实现，如加入IOC容器需要@Bean注解|
|^|@ConditionalOnMissingClass|若类路径下不存在该类，那么触发指定行为|^|^|
|^|@ConditionalOnBean|若IOC容器内存在指定的bean,那么触发指定行为|^|^|
|^|@ConditionalOnMissingBean|如果容器中不存在这个Bean（组件）,那么触发指定行为|^|^|
|@ConfigurationProperties|声明组件的属性和配置文件内key的前缀项以进行项绑定|类|该注解生效必须**使作用类被@Component及相关注解作用或被配置类的@EnableConfigurationProperties指定**，且**对应的实体类需要有getter和setter方法**<br>该注解生效的时机貌似是bean创建时检查|
|@EnableConfigurationProperties|指定某些类是属性绑定类|类|应作用于配置类|
|Jackson相关注解|@JacksonXmlRootElement|声明对应类可被转换为xml格式|类|无|
|日志|@Slf4j|被该注解作用的类中的方法内，都默认可以得到一个实现了SLF4J日志门面的日志对象|类|该注解来自于Lombok|

---