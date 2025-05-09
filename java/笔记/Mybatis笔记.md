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

## 三、相关API

|归属|方法|参数|描述|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|Resources(ibatis)|getResourceAsStream(String resource)|resource:mybatis配置xml文件**相对于classpath的路径**|得到配置文件的输入流对象|输入流对象|InputStream|IOException|无|[样例](../源码/Mybatis/src/test/java/hellomybatis/HelloMybatisTest.java)
|SqlSessionFactoryBuilder|SqlSessionFactoryBuilder()|无参|SqlSessionFactory实现类的空参构造器|得到生成会话对象的工厂对象的构建器对象|SqlSessionFactory|无|无|^|
|^|build(InputStream inputStream)|inputStream:配置文件的输入流对象|>|得到生成数据库会话的工厂对象|SqlSessionFactory|无|无|^|
|SqlSessionFactory|openSession()|无参|>|得到一个数据库会话对象|SqlSession|无|无|^|
|^|openSession(boolean autoCommit)|autoCommit:设置执行数据库操作时是否自动提交，默认否|^|^|^|^|无|^|
|SqlSession|<T> getMapper(Class<T> type)|type:接口类的Class对象|>|得到对应接口类的代理对象|T|无|无|^|
|^|commit()|无参|手动进行事务提交|无返回值|void|无|无|^|
|^|close()|无参|关闭会话|无返回值|void|无|无|^|

---

## 四、类型传入与结果输出

### （一）插值运算符

+ mybatis提供`#{}`和`${}`两种插值表达式来向sql语句中插入相关的值
  + 使用#{}表达式插值，Mybatis会将其转换为PreparedStatement的占位符，可以防止xss注入攻击。但仅能插入变量值，**无法插入数据库表名、字段名之类的值**
  + 使用${}表达式插值，Mybatis会使用字符串拼接的方式直接插入，**有xss注入攻击风险。但可以在任意地方插入**
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
    + 基本数据类型,如`int`对应的全类名是`_int`，即**在基本数据类型名前加上下划线**
    + 引用数据类型，采用小写:如HashMap->hashmap、Integer->int/integer
    + 除此以外，我们还可以自行指定类的别名。指定别名的操作需要在mybatis-config.xml文件内进行
      + 在typeAliases标签内指定typeAlias标签可以为一个类指定别名
      + 在typeAliases标签内指定package标签可以为指定包下的所有类都起别名，且**别名默认是首字母小写的类名**
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

## 五、主键回显与维护

+ 主键回显一般出现在我们进行插入时，我们可以在insert标签上设置useGeneratedKeys和keyProperty来**指定开启主键回显和主键赋值的目标属性**
  + **主键回显会将主键赋值给对应实体类的属性**，而不是以方法的返回值返回，除select语句外，DML语句全都返回数值类型的值
+ 如果我们想给非自增长类型主键赋值，我们可以:
  + 使用Java手动生成主键:该方法使得我们生成主键的过程被内嵌在了代码中
  + 使用mybatis自动进行主键维护:
    + selectKey标签可以嵌套在insert标签内，用来在插入语句被调用前执行其内部的sql语句
    + 其keyProperty用来设置主键名，order用来设置执行时机，resultType用来告知mybatis其返回值类型
    + 然后将主键名插入到insert语句内即可
+ [xml文件1](../源码/Mybatis/src/main/resources/hellomybatis/mappers/employeeMapper.xml)
+ [xml文件2](../源码/Mybatis/src/main/resources/hellomybatis/mappers/teacherMapper.xml)
+ [实现类1](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/pojo/Employee.java)
+ [实现类2](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/pojo/Teacher.java)
+ [接口1](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/mapper/EmployeeMapper.java)
+ [接口2](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/mapper/TeacherMapper.java)
+ [样例](../源码/Mybatis/src/test/java/hellomybatis/KeyTest.java)

---

## 六、多表映射

### （一）基本使用

+ 如果我们有一个订单表和一个顾客表，一个订单对应一个顾客，但一个顾客可以下多个订单
  + 此时一个订单需要对应一个顾客，此时订单实体类内需要有一个属性是顾客实体类对象
  + 而顾客可以下多个订单，因此顾客实体类下需要有一个属性是订单实体类对象组成的List集合
  + 可以看到，如果这样的话，**我们的实体类下的属性就会出现对象的嵌套行为**，在使用mybatis赋值时，就**无法通过简单的resultType属性来赋值了**
+ mybatis提供了resultMap属性来解决这一问题:
  + 它可以**自定义映射**
  + 它**支持嵌套映射**
  + **它与resultType属性不能共用，也就是说，他们是互斥的**
+ 想使用resultMap属性，我们需要在mapper标签下创建一个自定义映射标签，它就叫resultMap标签:

~~~xml

    <!--
        resultMap用来实现实体类属性的嵌套映射，即自定义映射
        其id属性便于后面引用
        type属性为生成的实体类的类型
     -->
    <resultMap id="selectCustomerByCustomerIdMap" type="customer">
        <!--
            id用来映射主键
                column属性用来指定数据库查询结果的字段名称
                property属性用来指定该字段对应的值赋值给实体类的哪一个属性，写属性名
         -->
        <id column="customer_id" property="customerId"/>
        <!--  result用来映射普通属性  -->
        <result column="customer_name" property="customerName" />

        <!--
            collection用来映射集合(List)类型
                property用来指定被赋值的属性的属性名
                ofType用来指定集合内的元素的类型，即指定其泛型
         -->
        <collection property="list" ofType="order">
            <id column="order_id" property="orderId" />
            <result column="order_name" property="orderName" />
        </collection>
        <!--
            association用来映射其它实体类类型
                property用来指定被赋值的属性的属性名
                javaType用来其他实体类型的具体名称，可以是全类名，也可以是别名
         -->
        <association property="customer" javaType="customer">
            <id column="customer_id" property="customerId" />
            <result column="customer_name" property="customerName" />
        </association>
    </resultMap>

~~~

+ [xml文件1](../源码/Mybatis/src/main/resources/multitablemapping/mappers/CustomerMapper.xml)
+ [xml文件2](../源码/Mybatis/src/main/resources/multitablemapping/mappers/OrderMapper.xml)
+ [实体类1](../源码/Mybatis/src/main/java/com/mybatis/example/multitablemapping/pojo/Customer.java)
+ [实体类2](../源码/Mybatis/src/main/java/com/mybatis/example/multitablemapping/pojo/Order.java)
+ [接口1](../源码/Mybatis/src/main/java/com/mybatis/example/multitablemapping/mapper/CustomerMapper.java)
+ [接口2](../源码/Mybatis/src/main/java/com/mybatis/example/multitablemapping/mapper/OrderMapper.java)
+ [测试样例](../源码/Mybatis/src/test/java/multitablemapping/TableMappingTest.java)

---

### （二）resultMap标签

+ [resultMap标签书写顺序](#resultMapOrder)
+ [对象内的List<String>映射](#collection_list)
+ [多级嵌套对象映射](#nest_obj_mapping)

<a id="collection_list"></a>

+ 假设我们有如下的类:
  + 可以发现类中存在一个`List<String>`的属性

~~~java
    @Data
    public class EnableBedVo {
        private String roomFloor;
        private Integer roomNo;
        private List<String> beds;
    }
~~~

+ 对应的xml映射如下:

~~~xml
    <resultMap id="roomResultMap" type="com.example.neud.demo.entity.vo.EnableBedVo">
        <result property="roomFloor" column="room_floor"/>
        <result property="roomNo" column="room_no"/>
        <collection property="beds" ofType="string">
            <!-- 使用constructor标签进行来表示该List属性 -->
            <constructor>
                <arg column="bed_no" javaType="string"/>
            </constructor>
        </collection>
    </resultMap>
~~~

---

<a id="nest_obj_mapping"></a>

+ 这里的情况是一个楼层对应多个房间，一个房间有多个床，想把它搞成这样:

~~~js
    [
        {
            floor:'',
            rooms:[
                {
                    roomNo:'',
                    bed:[]
                }
            ]
        },
    ]
~~~

~~~xml
    <resultMap id="roomResultMap" type="com.example.neud.demo.entity.vo.EnableBedVo">
        <result property="roomFloor" column="room_floor"/>
        <collection property="rooms" ofType="roomBedRelationVo">
            <result column="room_no" property="roomNo" />
            <!-- 深层映射可能导致xml文件识别不到对应proerty映射项而报错，运行是没有问题的 -->
            <collection property="beds" ofType="string">
                <constructor>
                    <arg column="bed_no" javaType="string"/>
                </constructor>
            </collection>
        </collection>
    </resultMap>

    <select id="queryEnableBeds" resultMap="roomResultMap">
        SELECT r.room_floor,r.room_no,b.bed_no
        FROM room r LEFT JOIN bed b ON r.room_no = b.room_no
    </select>
~~~

---


## 七、动态语句

+ 当查询的条件增多时，如果使用传统的JDBC的方式来生成查询语句，那么我们需要根据传入的参数进行复杂的字符串拼接操作，这是非常痛苦的事情
+ mybatis为了解决这一问题，在对应的mapper.xml文件中添加了许多动态生成sql语句的相关标签，通过使用这些标签，我们将可以便捷的生成动态sql语句

|标签|属性|属性作用|作用|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|
|where|>|无属性|**自动补全where关键字，并添加或去掉多余的and和or关键字**|无|[样例](../源码/Mybatis/src/main/resources/dynamicsql/mappers/EmployeeMapper.xml)|
|if|test|测试表达式是否为真|若test内的表达式值为true,那么将标签内的语句拼接到sql语句上|**如果想写`>`、`<`、`>=`、`<=`等符号，推荐使用[字符实体](https://www.w3school.com.cn/charsets/ref_html_8859.asp)**|^|
|set|>|无属性|**自动补全set关键字，并去掉多余的逗号**|无|^|
|trim|prefix|指定要动态添加的前缀，如where、set等|自定义补全前后缀语句并去掉多余符号|无|^|
|^|suffix|指定要动态添加的后缀|^|无|^|
|^|prefixOverrides|指定要动态去掉的前缀，如果有多个，使用`\|`隔开|^|无|^|
|^|suffixOverrides|指定要动态去掉的后缀，如果有多个，使用`\|`隔开|^|无|^|
|choose|>|无属性|类似于switch开始，内部的when从上到下，哪个成立就将其内容拼接上去，且之后的when无论**是否正确都不再拼接**|无|^|
|when|test|同if标签的test|类似于case|无|^|
|otherwise|>|无属性|类似于default,都不成立时执行|无|^|
|foreach|collection|想遍历的集合对象，**需要与参数名一致**|遍历集合，并对每个元素进行操作|无|^|
|^|item|给当前遍历到的元素起个名字|^|无|^|
|^|separator|指定每个元素拼接时，相互之间隔开使用的字符|^|无|^|
|^|open|在遍历完成的字符串整体前需要加的字符串|^|无|^|
|^|close|在遍历完成的字符串整体后需要加的字符串|^|无|^|
|^|index|给当前遍历到的元素对应的下标索引起别名，如果是List，得到的是索引。如果是Map,得到的是key|^|无|^|
|sql|id|起一个id值|生成可复用的sql片段|该标签可以直接使用 引用它的标签 作用域中的Java对象|^|
|include|refid|指定sql标签的id值|复用sql片段|无|^|

+ 如果使用foreach进行批量插入，需要在config文件内的数据库路径下添加`xxx?allowMultiQueries=true`来使数据库支持批量操作

+ [测试样例](../源码/Mybatis/src/test/java/dynamicsql/DynamicSQLTest.java)

---

## 八、缓存

+ Mybatis支持对查询结果进行缓存，其主要有两种缓存:
  + 一级缓存:这是SqlSession级别的缓存，只要是同一个SqlSession的Mapper执行了相同的查询操作，缓存就会命中
  + 二级缓存:这是namespace级别的缓存，不同的SqlSession的Mapper执行相同的查询操作，缓存也会命中，**需要手动开启**
+ 注意:
  + 只要执行了增删改操作，所有缓存都会被清空
  + 二级缓存需要缓存的数据实现Serializable接口
  + 只有会话提交或关闭以后，一级缓存中的数据才会转移到二级缓存中去

---

## 九、批量映射

+ mapper标签总是需要我们一个一个的去配，如果xml文件量过大，会变的非常麻烦
+ mybatis提供了package标签来解决该问题，它可以自动扫描指定路径下的包，并找到对应的mapper文件映射
  + **需要mapper文件名与接口名一致**
  + **需要它们所在包的路径相对于classpath路径相同**
+ 由于相对于classpath路径相同的包内的文件最终会被合并到一起，而resources和java目录都是classpath的起始路径，因此我们可以**在resources目录下创建与java目录内完全相同的包，然后把mapper文件放入，这样，他们在打包后就会被放置在一起**

![批量映射示例1](../文件/图片/Mybatis图片/批量映射示例1.png)
![批量映射示例2](../文件/图片/Mybatis图片/批量映射示例2.png)

---

## 十、插件

+ mybatis提供了插件功能，在mybatis的基础上可以对其功能进行进一步的拓展
  + MyBatis 的插件机制包括以下三个组件
    + Interceptor（拦截器）:定义一个拦截方法，该方法在执行SQL语句、执行查询、查询结果的映射时会被调用
    + Invocation（调用）：实际上是对被拦截的方法的封装，封装了 Object target、Method method 和 Object[] args 这三个字段
    + InterceptorChain（拦截器链）：对所有的拦截器进行管理，包括将所有的 Interceptor 链接成一条链，并在执行 SQL 语句时按顺序调用
+ 插件的开发非常简单，只需要实现 Interceptor 接口，并使用注解 @Intercepts 来标注需要拦截的对象和方法，然后在 MyBatis 的配置文件中添加插件即可。

### （一）PageHelper

+ PageHelper是一个分页插件，它可以帮助我们进行分页操作，而不需要我们再手写limit子句
+ 依赖:

~~~xml

    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
        <version>6.1.0</version>
    </dependency>

~~~

+ 在mybatis-config.xml文件下的plugins标签内配置:

~~~xml

    <plugins>
        <!-- 指定拦截器的全类名 -->
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!-- 
                helperDialect的作用是分页插件会自动检测当前的数据库链接，自动选择合适的分页方式
                value属性告知插件使用什么数据库
             -->
            <property name="helperDialect" value="mysql"/>
        </plugin>
    </plugins>

~~~

+ 相关API

|归属|方法|参数|描述|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|PageHelper|static startPage(int pageNum, int pageSize)|pageNum:当前页码(从1开始)<br>pageSize:每页的数据量|告诉插件我们想得到的页的基本参数|返回一个Page对象，里面有此次分页的基本配置信息|List<Object>|无异常|无|[样例](../源码/Mybatis/src/test/java/dynamicsql/DynamicSQLTest.java)|
|PageInfo|PageInfo(List list)|list:查询得到的list对象|通过查询结果得到页的信息对象|>|PageInfo对象|无|无|^|
|^|getPageSize()|无参|得到每页的标准数据量|数值|int|无|无|^|
|^|getPages()|^|得到总页数|数值|int|无|无|^|
|^|getSize()|^|得到当前页包含的数据量|数值|int|无|无|^|
|^|getList()|^|得到页的List集合|List集合|List|无|无|^|
|^|getTotal()|^|得到数据总量|数值|long|无|无|^|
|^|getEndRow()|^|得到本页的最后一行数据的下标(从1开始)|数值|long|无|无|^|
|^|getStartRow()|^|得到本页的第一行数据的下标(从1开始)|数值|long|无|无|^|

---

### （二）MybatisX

+ MyBatisX 是一个 MyBatis 的代码生成插件，可以通过简单的配置和操作快速生成 MyBatis Mapper、pojo 类和 Mapper.xml 文件
+ 我们可以通过idea下载该插件，直接在插件界面下载即可
+ 按照下图进行配置即可

![MybatisX示例1](../文件/图片/Mybatis图片/MybatisX示例1.png)
![MybatisX示例2](../文件/图片/Mybatis图片/MybatisX示例2.png)

+ 去查看生成结果即可

---

## 十一、逆向工程

+ ORM（Object-Relational Mapping，对象-关系映射）是一种**将数据库和面向对象编程语言中的对象之间进行转换的技术**，它通常有半自动和全自动两种方式
  + 半自动 ORM 通常**需要程序员手动编写 SQL 语句或者配置文件**，将实体类和数据表进行映射，还需要手动将查询的结果集转换成实体对象，Mybatis是典型的半自动ORM的代表
  + 全自动则**不需要程序员手动进行映射**，它会自动完成，hibernate、Spring Data JPA、MyBatis-Plus是全自动ORM的典型代表

|各方面|半自动ORM|全自动ORM|
|:---:|:---:|:---:|
|**映射方式**|需要程序员手动指定实体类和数据表之间的映射关系，通常使用 XML 文件或注解方式来指定|可以自动进行实体类和数据表的映射，无需手动干预|
|**查询方式**|需要程序员手动编写 SQL 语句并将查询结果集转换成实体对象|自动组装 SQL 语句、执行查询操作，并将查询结果转换成实体对象|
|**性能**|程序员必须对 SQL 语句和数据库的底层知识有一定的了解，才能编写高效的 SQL 语句|通过自动优化生成的 SQL 语句来提高性能，程序员无需进行优化，也不能进行优化|
|**学习成本**|要求程序员具备较高的数据库和 SQL 知识|程序员无需了解过多的数据库和 SQL 知识|

+ 通过自动化生成持久层代码和映射文件，被称为**逆向工程**
  + 逆向工程工具可以根据数据库表结构和设置的参数生成对应的实体类、Mapper.xml 文件、Mapper 接口等代码文件，简化手动生成过程
  + Mybatis的逆向工程可以通过两种方式实现
    + MyBatis Generator 插件
    + Maven 插件
  + **逆向工程仅能生成单表CRUD的操作，多表查询依旧需要我们自己编写**

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
|:---:|:---:|:---:|:---:|:---:|
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

#### ⑦environments标签

~~~xml

    <!-- 
        environments表示配置Mybatis的开发环境
        可以配置多个环境，在众多具体环境中，使用default属性指定实际运行时使用的环境。
        default属性的取值是environment标签的id属性的值。 -->
    <environments default="development">
        <!-- environment表示配置Mybatis的一个具体的环境 -->
        <environment id="development">
            <!-- Mybatis的内置的事务管理器 -->
            <transactionManager type="JDBC"/>
            <!-- 配置数据源 -->
            <dataSource type="POOLED">
                <!-- 建立数据库连接的具体信息 -->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/atguigudb"/>
                <property name="username" value="root"/>
                <!-- yyy对应的是密码，使用时修改为对应真实密码 -->
                <property name="password" value="yyy"/>
            </dataSource>
        </environment>
    </environments>

~~~

---

#### ⑧mappers标签

+ mappers标签用来关联各mapper文件的映射

~~~xml

    <mappers>

        <!-- 
            mapper标签用于指定对应mapper文件的映射
                rsource属性用来指定该mapper文件的路径，该路径是相对于classpath的，并不是相对于config文件的
         -->
        <mapper resource="hellomybatis/mappers/employeeMapper.xml"/>
        <mapper resource="hellomybatis/mappers/teacherMapper.xml" />
    </mappers>

~~~

---


### （二）mapper配置文件

+ mapper配置文件一般被命名为xxxMapper.xml，它的标签结构如下:

~~~xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">


    <mapper>
        <select></select>
        <insert></insert>
        <update></update>
        <delete></delete>
    </mapper>

~~~


#### ①mapper标签 

+ mapper标签是mapper文件的根标签

|属性|作用|可选值|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|namespace|声明该mapper文件相关联的接口全类名|接口的全类名|无|无|

#### ②select标签

|属性|作用|可选值|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|id|关联接口的对应方法|接口的方法名|无|无|
|parameterType|指定传入参数的类型|类型所属类的全类名或别名|无|**mybatis可以通过类型处理器推断出参数类型，因此该属性可选**|
|resultType|指定返回值类型|返回值类型的全类名或别名|无|**如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身的类型**|
|useGeneratedKeys|设置主键是否回显|true/false|false|无|
|keyProperty|指定主键回显赋值的对象属性|属性名|无|无|
|timeout|驱动程序若等待数据库返回请求结果时间超出指定秒数会抛出异常|数值|unset(未设置)|无|
|statementType|指定Mybatis使用的Statement类型|STATEMENT:使用Statement类型<br>PREPARED:**默认值**，使用PreparedStatement<br>CALLABLE:使用CallableStatement|无|
|resultMap|指定自定义映射id|id值|无|无|

#### ③update/delete/insert标签

|属性|作用|可选值|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|id|关联接口的对应方法|接口的方法名|无|无|
|timeout|驱动程序若等待数据库返回请求结果时间超出指定秒数会抛出异常|数值|unset(未设置)|无|
|statementType|指定Mybatis使用JDBC的Statement类型|STATEMENT:使用Statement类型<br>PREPARED:**默认值**，使用PreparedStatement<br>CALLABLE:使用CallableStatement|无|
|useGeneratedKeys|设置主键回显|true/false|false|**仅适用于insert标签与update标签**|
|keyProperty|设置主键回显赋值的对应属性|属性名|未设置|^|

---

#### ④selectKey标签

+ **该标签仅在insert标签内能使用**

|属性|作用|可选值|默认值|备注|
|:---:|:---:|:---:|:---:|:---:|
|keyProperty|**给查询结果起别名**，用于后续引用，如果结果有多个值，每个别名间使用逗号隔开|字符串|无|无论查询结果怎么样，最后**引用查询结果的值时**都以该属性的值为准|
|keyColumn|**给列名起别名**，用于后续引用|如果列名有多个，每个别名间使用逗号隔开|^|无|无论查询结果怎么样，最后**引用列名时**都以该属性的值为准|
|resultType|返回值类型，通常Mybatis是可以自己推断出来的，但写上也不会有什么问题|全类名或别名|Mybatis自己推断|无|
|order|设置查询的执行时机|BEFORE:在插入执行前执行<br>AFTER:在插入执行后执行|无|无|
|statementType|>|>|>|同select标签的statementType属性|

---

#### ⑤resultMap

+ resultMap用于自定义结果映射，样例见下


~~~xml
    <resultMap id="selectCustomerByCustomerIdMap" type="customer">
        <result column="customer_test" property="customerTest" />
    </resultMap>

    <!--
        resultMap用来实现实体类属性的嵌套映射，即自定义映射
        id属性用于指定resultMap的唯一标识，可以被其它resultMap继承，或者被select标签的resultMap属性使用
        type属性为生成的实体类的类型，如果没有指定别名需要指定全类名
        extends（可选）属性表示继承某一resultMap的结构(通过指定该resultMap的id)，这样可以简化resultMap的编写，避免重复编写一些属性
        autoMapping（可选）属性表示是否由Mybatis自己根据 查询出来的数据库字段值和该resultMap的实体类属性值 自动映射，默认为false
     -->
    <resultMap id="selectCustomerByCustomerIdMap" type="customer" extends="selectCustomerByCustomerIdMap">
        <!--
            id用来映射可以唯一标识这条记录的数据库字段，它不一定需要是主键，但是指定id标签映射主键一定是可以的。推荐在每个resultMap或其它标签下都指定id标签，因为这可以提高Mybatis的执行效率
                column属性用来指定数据库查询结果的字段名称
                property属性用来指定该字段对应的值赋值给实体类的哪一个属性，写属性名
                javaType属性（可选）用于显式指定该字段对应的java类型，Mybatis一般可以自动推理
                jdbcType属性（可选）用于显式指定该数据库字段的数据库字段类型，如 VARCHAR、Integer等，
         -->
        <id column="customer_id" property="customerId"/>
        <!--  result用来映射普通属性，其标签属性同id标签  -->
        <result column="customer_name" property="customerName" />

        <!--
            collection用来映射一对多关系的嵌套集合(List)类型
                property用来指定被赋值的属性的属性名
                ofType用来指定集合内的元素的类型，即指定其泛型，如果没有指定别名需要写全类名
                column（可选）属性 往往与select属性一起出现（association标签可以不用） 用来指定嵌套查询时这个集合按照哪个字段进行分组。即在嵌套查询时，column用来指定内层循环时需要比较的外层循环提供的数据库查询字段
                    举例:我现在需要查询一个顾客列表，顾客类中有一个List类型的订单列表。因为一个顾客可以点多份订单。
                    在使用JDBC对数据库交互时，需要先把顾客列表查出来，再遍历这个顾客列表，用遍历到的每个顾客的customer_id去订单表里查相关数据并把数据设置到这个顾客的订单列表属性中去
                    那么此时column就应该指定customer_id，因为这是订单列表确定它要映射到的顾客的依据
                    如果想传入多个参数，就需要以 {key1=value1,key2=value2} 的形式编写
                    key为嵌套查询中使用的参数名，value为父查询结果集中的列名
                select（可选）属性 往往与column属性一起出现（association标签可以不用） 用于在嵌套查询时指定使用的查询语句的select标签ID
                resultMap（可选）属性用于指定引用的resultMap的ID，用于替代在collection里面嵌套 id标签和result标签
                columnPrefix（可选）属性用于指定列的点缀，该属性一般在 联表查询 或 该集合(collection或association标签)被一个结果集多次复用 导致列名冲突时指定
                javaType（可选）用于显示指定该字段对应的java类型，Mybatis一般可以自动推理
                autoMapping（可选）属性表示是否由Mybatis自己根据 查询出来的数据库字段值和该resultMap的实体类属性值 自动映射，默认为false
                notNullColumn（可选）属性用来指定哪些列不为空，它不像数据校验那样只要指定的字段为空就报错，而是当指定的字段至少有一个不为空时才会创建对象。默认未设置，如果想指定多个列，使用逗号隔开
                typeHandler（可选）属性用来让该映射使用指定类型处理器而不是默认类型处理器来处理类型
                fetchType（可选）属性有两个值:lazy、eager。指定后将在映射中忽略全局配置参数lazyLoadingEnabled，使用属性的值
                resultSet（可选）属性用来指定映射所关联的结果集，该属性一般用于使用存储过程返回多个ResultSet时让对应映射与之关联
                foreignClolumn（可选）属性用来指定外键字段，指定的外键字段将与父类型的column属性指定的字段名进行匹配
         -->
        <collection property="list" ofType="order" column="customer_id" select="">
            <id column="order_id" property="orderId" />
            <result column="order_name" property="orderName" />
        </collection>
        <!--
            association用来映射一对一关系的嵌套实体类类型
                除ofType为collection特有外，其余属性与collection相同
         -->
        <association property="customer" javaType="customer">
            <id column="customer_id" property="customerId" />
            <result column="customer_name" property="customerName" />
        </association>
    </resultMap>

    

~~~

##### Ⅰ一对多查询案例

~~~xml
    <!-- 
        下面是一个一对多的查询案例 
        一个合集有多个视频，现在我需要拿到所有合集下的指定数量的视频并按照合集分组
    -->

    <resultMap id="base_query_video_info_with_limit_map" type="SeriesDetailVo">
        <id property="seriesId" column="series_id"  />
        <result property="seriesDescription" column="series_description" />
        <result property="seriesName" column="series_name" />
        <result property="sort" column="sort" />
        <!-- 
            collection如果想接收多个参数，就需要以 {key1=value1,key2=value2} 的形式编写
            key为嵌套查询中使用的参数名
            value为父查询结果集中的列名
            这里面的limit参数实际是父查询的传入的参数名而并非列名
         -->
        <collection property="videos" column="{seriesID=series_id,limit=limit_param}" select="loadSeriesVideoDetail"/>
    </resultMap>

    <!-- 
        由于子查询中的limit参数是本方法的参数，但是column只能拿到父查询的列，那么只需要把参数写在父查询的列上即可
        即下面代码的 #{limit} as limit_param 
    -->
    <select id="loadAllUserVideoWithLimit" resultMap="base_query_video_info_with_limit_map">
        select series_id,sort,series_name,series_description,#{limit} as limit_param
        from user_video_series
        where user_id = #{userID}
    </select>
    
    <!-- 嵌套查询的SQL，需要两个参数 -->
    <select id="loadSeriesVideoDetail" resultType="com.easylive.common.series.entity.vo.SeriesVideoVo">
        select uvsv.series_id, sort ,uvsv.video_id ,video_cover ,video_name ,play_count ,like_count ,danmu_count
        comment_count ,coin_count ,collect_count ,duration
        from user_video_series_video uvsv
        join video_info vi on uvsv.video_id = vi.video_id
        <where>
            series_id = #{seriesID}
        </where>
        <if test="limit!=null">
            limit 0,#{limit}
        </if>
    </select>
~~~

---

#### ⑥别名规则

+ resultType属性用来指定sql语句的返回值，它专门作用于select标签。他需要**指定类的全类名**
  + Mybatis已经为我们**提供了常用的JDK类型的别名**，我们也可以**使用这些别名来代指类的全类名**
    + 基本数据类型,如`int`对应的全类名是`_int`，即**在基本数据类型名前加上下划线**
    + 引用数据类型，采用小写:如HashMap->hashmap、Integer->int/integer
    + 除此以外，我们还可以自行指定类的别名。指定别名的操作需要在mybatis-config.xml文件内进行
      + 在typeAliases标签内指定typeAlias标签可以为一个类指定别名
      + 在typeAliases标签内指定package标签可以为指定包下的所有类都起别名，且**别名默认是首字母小写的类名**
      + 如果使用了package标签且类名上有@Alias(value)注解，那么别名将是注解指定的名称
+ 如果返回值类型是JDK数据类型，那么直接写别名或全类名即可
+ 如果返回值类型是Map类型，那么直接写map，该情况适用于我们没有实体类可以接收的情况
+ 如果返回值类型是List类型，那么**需要写的类型是List内元素对应的泛型类型**
+ [mapper.xml样例](../源码/Mybatis/src/main/resources/hellomybatis/mappers/employeeMapper.xml)
+ [config.xml样例](../源码/Mybatis/src/main/resources/hellomybatis/mybatis-config.xml)
+ [接口](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/mapper/EmployeeMapper.java)
+ [实现类](../源码/Mybatis/src/main/java/com/mybatis/example/hellomybatis/pojo/Employee.java)
+ [测试样例](../源码/Mybatis/src/test/java/hellomybatis/TypeInjectTest.java)



### （三）注解汇总

|注解|作用|备注|
|:---:|:---:|:---:|
|@Param|指定mapper最终能看到的参数名称|无|
|@Alias|指定类在mapper文件中的别名|无|

---


