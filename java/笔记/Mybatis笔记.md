# Mybatis笔记

## 一、简介

+ MyBatis最初是Apache的一个开源项目iBatis，在2010年6月该项目被迁移到了Google Code，随后开发团队转投到Google Code旗下。随后 iBatis3.x正式更名为MyBatis。代码于2013年11月迁移到Github。
+ MyBatis 是一款优秀的持久层框架，它**支持自定义 SQL、存储过程以及高级映射**
+ MyBatis 可以**通过简单的 XML 或注解**来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

---

## 二、HelloMybatis

+ 导入依赖:

~~~xml
    <dependencies>
    <!-- mybatis依赖 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.11</version>
    </dependency>

    <!-- MySQL驱动 mybatis底层依赖jdbc驱动实现,本次不需要导入连接池,mybatis自带! -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.25</version>
    </dependency>

    <!--junit5测试-->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.3.1</version>
    </dependency>
    </dependencies>
~~~

+ 创建一个数据库表:

~~~sql
    create table mybatis_emp
    (
        emp_id     int auto_increment
            primary key,
        emp_name   char(100)     null,
        emp_salary double(10, 5) null
    );
~~~

+ 创建对应的Java实体类和接口，一般**接口都命名为XxxMapper，放在mappers包下**。Java实体类与数据库表名按照大驼峰命名法书写，**放在pojo包下**
+ [实体类](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/pojo/Employee.java)
+ [接口](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/mapper/EmployeeMapper.java)
+ 随后是xml文件配置，我们需要将xml文件放在resources目录下，首先是详细的mapper相关的xml文件:
  + 为方便管理，mapper相关xml文件可以**放在resources下的mappers目录里**
  + [示例](../源码/Mybatis/src/main/resources/hellomybatis/mappers/employeeMapper.xml)
+ 接下来是mybatis整合mapper的配置xml文件，**一般叫mybatis-config.xml，放在与mappers目录同级的目录下**
  + [示例](../源码/Mybatis/src/main/resources/hellomybatis/mybatis-config.xml)
+ 随后编写测试代码运行:

~~~java
    @Test
    // 测试HelloMybatis的基本使用
    public void test() throws IOException {
        // 获得配置文件的输入流对象
        InputStream in = Resources.getResourceAsStream("hellomybatis/mybatis-config.xml");
        // 使用SqlSessionFactoryBuilder工厂构建对象构建SqlSessionFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 使用工厂对象打开一个会话，并得到会话对象
        SqlSession session = factory.openSession();
        // 调用会话的getMapper方法，来得到接口的代理对象
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        // 执行接口代理对象的方法，得到结果
        Employee result1 = mapper.selectEmployeeById(1);
        Integer result2 = mapper.deleteEmployeeById(1);
        System.out.println(result1);
        System.out.println(result2);
        // 执行会话的提交
        session.commit();
        // 关闭会话
        session.close();
    }
~~~

---

## 三、类型传入与结果输出

### （一）插值运算符

+ mybatis提供`#{}`和`${}`两种插值表达式来向sql语句中插入相关的值
  + 使用#{}表达式插值，Mybatis会将其转换为PreparedStatement的占位符，可以防止xss注入攻击。但仅能插入变量值，无法插入数据库表名、字段名之类的值
  + 使用${}表达式插值，Mybatis会使用字符串拼接的方式直接插入，有xss注入攻击风险。但可以在任意地方插入
+ 因此，尽量使用更安全的#{}插值运算符

---

### （二）类型传入


+ 如果我们想传入一个简单的数据类型，那么只需要在插值表达式内任意写一个值即可，**推荐写参数的名称**
+ 如果我们想传入多个简单的数据类型，那么需要遵循如下写法:
  + Mybatis默认从左到右给每个参数以arg0、arg1、arg2...等顺序编号，我们可以这样写
  + Mybatis默认也从左到右给每个参数以param1、param2、param3...等顺序编号，我们可以这样写
  + **推荐:使用@Param(value)注解作用于每个形参上**，这样可以使Mybatis读取到多个参数的名称，这样我们就可以通过指定每个参数名称的方式赋值了
+ 如果我们想传入一个引用数据类型，需要遵循如下写法:
  + 如果传入的是一个实体类对象，可以写它的属性名
  + 如果传入的是List类型，需要使用foreach标签来遍历list中的所有元素，并将它们一一放在查询语句内
  + 如果传入的是Map类型，可以直接写它的key值即可
+ [样例](../源码/Mybatis/src/test/java/hellomybatis/TypeInjectTest.java)

---

### （三）结果输出

+ resultType属性用来指定sql语句的返回值，它专门作用于select标签。他需要**指定类的全类名**
  + Mybatis已经为我们**提供了常用的JDK类型的别名**，我们也可以**使用这些别名来代指类的全类名**
    + 基本数据类型,如`int`对应的全类名是`_int`，即在基本数据类型名前加上下划线
    + 引用数据类型，采用小写:如HashMap->hashmap、Integer->int/integer
    + 除此以外，我们还可以自行指定类的别名。指定别名的操作需要在mybatis-config.xml文件内进行
      + 在typeAliases标签内指定typeAlias标签可以为一个类指定别名
      + 在typeAliases标签内指定package标签可以为指定包下的所有类都起别名，且别名默认是首字母小写的类名
      + 如果使用了package标签且类名上有@Alias(value)注解，那么别名将是注解指定的名称
+ 如果返回值类型是JDK数据类型，那么直接写别名或全类名即可
+ 如果返回值类型是Map类型，那么直接写map，该情况适用于我们没有实体类可以接收的情况
+ 如果返回值类型是List类型，那么**需要写的类型是List内元素对应的泛型类型**
+ [mapper.xml样例](../源码/Mybatis/src/main/resources/hellomybatis/mappers/employeeMapper.xml)
+ [config.xml样例](../源码/Mybatis/src/main/resources/hellomybatis/mybatis-config.xml)
+ [接口](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/mapper/EmployeeMapper.java)
+ [实现类](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/pojo/Employee.java)
+ [测试样例](../源码/Mybatis/src/test/java/hellomybatis/TypeInjectTest.java)

---


## 配置汇总

### （一）Mybatis配置文件

+ mybatis配置文件一般被命名为mybatis-config.xml，它的标签结构如下:
  + **各结构必须严格按照顺序编写**，不然会出现问题

~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>

    <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">

    <configuration>
        <!-- 该标签内用于配置一些xml内可以直接调用的属性 -->
        <properties>...</properties>
        <!-- 该标签内用于配置mybatis在运行时支持的行为 -->
        <settings>...</settings>
        <!-- 该标签内用于配置mybatis能够识别的类型别名 -->
        <typeAliases>...</typeAliases>
        <!-- 该标签内用于配置mybatis能够识别的类型处理器 -->
        <typeHandlers>...</typeHandlers>
        <!-- 该标签内用于配置mybatis的对象工厂 -->
        <objectFactory>...</objectFactory>
        <!-- 该标签内用于配置mybatis插件 -->
        <plugins>...</plugins>
        <!-- 该标签内用于配置mybatis的环境变量，如连接数据库要用到的用户名、密码、驱动全类名、数据库路径等在此处配置 -->
        <environments>...</environments>
        <!-- 该标签内用于配置mybatis的数据库厂商标识 -->
        <databaseIdProvider>...</databaseIdProvider>
        <!-- 该标签用于配置映射，即将我们定义的mapper.xml文件配置到config.xml文件中，使mybatis能够识别到 -->
        <mappers>...</mappers>
    </configuration>
~~~

#### ①properties标签



---


#### ②settings标签

+ settings标签内用于配置mybatis运行时的设置

~~~xml

    <settings>
        <!-- 每个setting标签都是一项设置，name属性即设置的具体参数，value就是其具体值 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

~~~

|name属性值|作用|可选值|默认值|备注|
|mapUnderscoreToCamelCase|是否开启数据库表字段的驼峰命名自动映射|true/false|false|无|
|logImpl|指定mybatis日志的具体实现方式|1.SLF4J:使用SLF4J输出日志<br>2.LOG4J:使用LOG4J输出日志<br>3.LOG4J2:使用LOG4J2输出日志<br>4.JDK_LOGGING:使用JDK的日志类输出日志<br>5.COMMONS_LOGGING:不知道干嘛的<br>6.STDOUT_LOGGING:以传统的sout形式输出日志<nr>7.NO_LOGGING:不输出日志|无默认值|无|

---

#### ③typeAliases标签

+ typeAliases标签用来给类起别名，它有两个子标签
  + typeAlias:用来给单个类起别名
  + package:用来给一整个包下的类都起别名，默认别名是首字母小写的类名，如果有@Alias注解，那么别名是@Alias注解设置的值

~~~xml

<typeAliases>
    <typeAlias type="com.atguigu.pojo.Employee" alias="ergouzi" />
    <package name="com.mybatis.example.hellomybatis.pojo"/>
</typeAliases>

~~~

---



