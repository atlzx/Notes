# Maven笔记

## 一、Maven简介

+ 随着我们使用越来越多的框架，或者框架封装程度越来越高，项目中使用的jar包也越来越多
  + 这些jar包的**来源各不相同**，因此无法一次性得到，获取非常麻烦
  + 即使得到了这些包，也需要将它们**放在指定的文件夹下**，非常的局限
  + 在这种情况下，手动导包会变的非常麻烦，此时我们就需要一个工具，即`Maven`来帮助我们实现包的管理与导入
+ 同时，如果脱离了`IDEA`的生产环境，项目的构建和部署会变得非常困难
  + `Maven`可以帮助我们进行项目的构建工作
+ 因此，`Maven`是一款**为`Java`项目管理构建、依赖管理的工具**（软件），使用Maven可以自动化构建、测试、打包和发布项目，大大提高了开发效率和质量

---

## 二、Maven安装与配置

1. 在[Maven官网](https://maven.apache.org/download.cgi)下载`Maven`（下载`Binary`，不要下`Source`）
2. 解压`Maven`压缩包到指定的目录
  + 解压后的`Maven`是下面这样的:
    + bin目录:含有Maven的运行脚本
    + boot目录:含有plexus-classworlds类加载器框架
    + conf目录:含有Maven的核心配置文件
    + lib目录:含有`Maven`运行时所需的`Java`类库
    + LICENSE、NOTICE、README.txt：针对Maven版本，第三方软件等简要介绍
3. 配置`MAVEN_HOME`环境变量，值为`Maven`所在目录。同时配置`%MAVEN_HOME%\bin`环境变量。通过`mvn -v`命令验证是否配置成功
4. 配置`Maven->conf->setting.xml`文件，主要为下面的部分:

~~~xml
    <!-- 配置本地仓库，在下面的注释下面配置 -->
    <!-- localRepository
       | The path to the local repository maven will use to store artifacts.
       |
       | Default: ${user.home}/.m2/repository
      <localRepository>/path/to/local/repo</localRepository>
      -->
      <!-- 文件夹可以不存在，如果不存在，第一次创建Maven工程时会自动创建 -->
    <localRepository>E:\Programming Language\Java\maven-repository</localRepository>  
~~~

~~~xml
    <!-- 配置阿里镜像源(在mirrors标签内) -->
    <mirror>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        <mirrorOf>central</mirrorOf>
    </mirror>
~~~

~~~xml
    <!-- 配置JDK版本项目构建 -->
    <profile>
        <id>jdk-17</id>
        <activation>
          <activeByDefault>true</activeByDefault>
          <jdk>17</jdk>
        </activation>
        <properties>
          <maven.compiler.source>17</maven.compiler.source>
          <maven.compiler.target>17</maven.compiler.target>
          <maven.compiler.compilerVersion>17</maven.compiler.compilerVersion>
        </properties>
    </profile>

~~~

5. 在`IDEA`的`settings->Build,Execution,Deployment->Build Tools->Maven`中配置`Maven home path`为我们安装的`Maven`路径，此时如果`setting.xml`文件配置成功，在配置完路径后，下面的`Local repository`会变为我们自己配置的本地路径。接下来将`User settings file`改为我们刚才配置的`settings.xml`的文件路径即可

+ **注意**:
> + `Maven`的配置需要本机配置了`Java`并拥有`JAVA_HOME`

---

## 三、Maven工程

### （一）Maven工程项目结构

~~~xml
|-- pom.xml                               # Maven 项目管理文件 
|-- src
    |-- main                              # 项目主要代码
    |   |-- java                          # Java 源代码目录
    |   |   `-- com/example/myapp         # 开发者代码主目录
    |   |       |-- controller            # 存放 Controller 层代码的目录
    |   |       |-- service               # 存放 Service 层代码的目录
    |   |       |-- dao                   # 存放 DAO 层代码的目录
    |   |       `-- model                 # 存放数据模型的目录
    |   |-- resources                     # 资源目录，存放配置文件、静态资源等
    |   |   |-- log4j.properties          # 日志配置文件
    |   |   |-- spring-mybatis.xml        # Spring Mybatis 配置文件
    |   |   `-- static                    # 存放静态资源的目录
    |   |       |-- css                   # 存放 CSS 文件的目录
    |   |       |-- js                    # 存放 JavaScript 文件的目录
    |   |       `-- images                # 存放图片资源的目录
    |   `-- webapp                        # 存放 WEB 相关配置和资源
    |       |-- WEB-INF                   # 存放 WEB 应用配置文件
    |       |   |-- web.xml               # Web 应用的部署描述文件
    |       |   `-- classes               # 存放编译后的 class 文件
    |       `-- index.html                # Web 应用入口页面
    `-- test                              # 项目测试代码
        |-- java                          # 单元测试目录
        `-- resources                     # 测试资源目录
~~~

+  pom.xml：Maven 项目管理文件，用于**描述项目的依赖和构建配置**等信息。
+  src/main/java：**存放项目的Java源代码**。
+  src/main/resources：**存放项目的资源文件**，如配置文件、静态资源等。
+  src/main/webapp/WEB-INF：**存放 Web应用的配置文件**。
+  src/main/webapp/index.jsp：Web应用的**入口页面**。
+  src/test/java：**存放项目的测试代码**。
+  src/test/resources：**存放测试相关的资源文件**，如测试配置文件等。

---

### （二）创建工程

#### ①GAVP

+ GAVP是四个英文单词的大写字母拼凑:
  + GroupID:用于告知Maven该项目属于哪个组，一般都使用`com.公司/BU.业务线.\子业务线`来编写，如公司是mycom，有一个项目为myapp，那么groupId就应该是`com.mycom.myapp`
  + ArtifactID:在组内的唯一id，默认与当前模块名一致
  + Version:版本号，`IDEA`会自动设置，格式为`主版本号.次版本号.修订号`
  + Packaging:配置打包方式
    + jar:打成jar包，用于普通的`JavaSE`工程
    + war:打成war包，用于`JavaWeb`工程
    + pom:不打包，用来做继承的父工程

#### ②创建JavaSE工程

+ 我们在新创建一个项目后，新创建一个模块，构建系统选择`Maven`
+ 点开高级选项，填写`Group ID`，然后点击创建
+ 创建后，会打开`pom.xml`
+ 第一次打开时,`idea`可能会报错，因为此时`Maven`缺少自己运行的库，在`IDEA`进度可以看到在下载一些东西，等下载完成后，就不报错了

~~~xml

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>  <!-- 该配置代表xml的版本，不用管 -->

        <groupId>com.maven.javase</groupId>  <!-- 刚才设置的GroupID -->
        <artifactId>Maven_JavaSE</artifactId>  <!-- 刚才默认的artifactedID -->
        <version>1.0-SNAPSHOT</version>  <!-- 默认的version -->
        <packaging>jar</packaging>  <!-- 默认的打包方式就是打jar包，这句话写不写都行 -->
    </project>
~~~

+ 新创建项目后，会发现项目内出现了下面的内容:

~~~java
    模块名
        src
            main  // 这里负责编写代码
                com
                    ...
            test  // 这里负责对代码进行测试
~~~

---

#### ③创建JavaWeb工程

+ 创建方式1:
  + 首先创建一个`JavaSE`工程
  + `JavaWeb`工程与`JavaSE`工程的区别实际上就是模块设置下，`JavaWeb`工程多了一个`web`。
  + 我们可以通过设置`pom.xml`的`<packaging>war</packaging>`标签，来手动指定我们的项目将要被打包为`war`包，从而可以使`IDEA`自动帮我们创建模块设置下  `web`
  + 这样就可以使我们的项目变为`JavaWeb`项目了，但是我们还需要创建一个`web.xml`文件
  + `Open Module Settings->Modules->项目名->Web->Deployment Descriptions`下面的加号，然后指定`web.xml`文件的路径,**默认的路径是错的**，需要改一下
+ 创建方式2:
  + 安装插件`JBLJavaToWeb`
  + 创建一个`JavaSE`项目
  + 右键项目，点击`JBLJavaToWeb`，然后跟着它的提示点
+ 创建方式3:
  + 创建模块时选择`Maven Archetype`，`archetype`选择`xxx-webapp`(最后一个),然后等着他创建
  + 这样写创建的`web.xml`版本会低
+ 这样就创建完了，然后可以根据[JavaWeb笔记](javaweb笔记.md)的部署`tomcat`步骤来部署到`tomcat`

---

### （三）依赖管理

#### ①pom.xml文件配置

+ **pom.xml**文件是Maven项目的核心配置文件，它可以配置我们项目的依赖项，并进行管理
+ [该网站](https://mvnrepository.com/)可以寻找我们想要得到的库，并提供给我们其相应的xml标签

+ **pom.xml**文件的文件配置如下:

<a id="pom.xml-template"></a>

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">


        <modelVersion>4.0.0</modelVersion>

        <groupId>com.maven.javase</groupId>  <!-- 指定项目所属的组ID，必填 -->
        <artifactId>Maven_JavaSE</artifactId>  <!-- 指定项目在组内的唯一标识ID，默认与项目名称一致，必填 -->
        <version>1.0-SNAPSHOT</version>  <!-- 指定项目版本，IDEA会自动创建，必填 -->
        <packaging>jar</packaging>  <!-- 指定打包方式，有三个可选值:jar war pom,分别表示打包成jar包、war包和不打包作为被继承的父工程 -->

        <properties>

            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  <!-- 告知Maven编译时使用UTF-8字符集 -->
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>  <!-- 告知Maven打印报告时使用UTF-8进行字符集 -->

            <junit.version>4.12</junit.version>  <!-- 自定义版本声明，主要用于便捷修改依赖版本，标签名称可以随便起 -->
        </properties>


        <!-- 在该标签内写入要导入的依赖，每个依赖都以一个dependency标签作为单位 -->
        <dependencies>

            <dependency>
                <groupId>junit</groupId>  <!-- 指定想导入依赖所属的组 -->
                <artifactId>junit</artifactId>  <!-- 指定想导入依赖的组内ID -->
                <version>${junit.version}</version> <!-- 指定想导入依赖的版本，支持嵌入在properties标签内自定义的版本，也可以直接指定版本 -->
                <scope>test</scope>  <!-- 指定依赖的作用范围 -->
                <optional>true</optional>  <!-- 设定该依赖无法被子依赖所继承 -->
            </dependency>

            <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
                <scope>provided</scope>
            </dependency>

        </dependencies>


        <build>
        <!--设置要打包的资源位置-->
            <resources>
                <resource>
                    <!--设置资源所在目录-->
                    <!-- 该配置解决了一些配置文件不在resource文件夹下的问题，因为默认情况下，这些文件不会被打包 -->
                    <directory>src/main/java</directory>  <!-- 指定目录 -->
                    <includes>
                        <!--设置包含的资源类型-->
                        <!-- 该设置可以使非reource目录下的指定路径下的配置文件被打包 -->
                        <include>**/*.xml</include>  <!-- 完整路径为src/main/java**/*.xml，**表示任意文件夹，*.xml表示任意后缀是.xml的文件 -->
                    </includes>
                </resource>
            </resources>


            <plugins>
                <finalName>定义打包名称</finalName>  <!-- 自定义打包的名称，默认的打包名称是artifactid+verson.打包方式 -->
            <!-- java编译插件，配jdk的编译版本 -->
                <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>17</source>
                        <target>17</target>
                        <encoding>UTF-8</encoding>
                    </configuration>
                </plugin>
                <!-- tomcat插件 -->
                <plugin>
                    <groupId>org.apache.tomcat.maven</groupId>
                    <artifactId>tomcat7-maven-plugin</artifactId>
                    <version>2.2</version>
                    <configuration>
                        <port>8090</port> <!-- 配置tomcat端口 -->
                        <path>/</path>  <!-- 配置tomcat访问根路径 -->
                        <uriEncoding>UTF-8</uriEncoding>  <!-- 配置tomcat在接收get请求时，解码所依据的解码格式 -->
                        <server>tomcat7</server> <!-- 配置tomcat服务版本 -->
                    </configuration>
                </plugin>
            </plugins>
        </build>


    </project>

~~~

#### ②依赖范围

+ 依赖可以作用的Java范围有三种:
  + 编译范围:即 src/main/ 路径内的代码范围
  + 测试范围:即 src/test 路径下的代码范围
  + 运行时:即部署后，程序运行时的范围
+ 在[上例](#pom.xml-template)中，dependency标签内的**scope标签**用来指定依赖的作用范围，其可选值如下表所示:

|值|范围|备注|
|:---:|:---:|:---:|
|**compile**|编译依赖范围，**是scope标签不写时的默认值**，在编译范围、测试范围和运行时都起作用|无|
|**test**|测试依赖范围。**仅在测试范围内有效**|无|
|**provided**|已提供依赖范围。**在测试范围内和编译范围内有效**|Servlet就属于这一类，因为它在运行时由tomcat提供|
|runtime|运行时依赖范围。**在测试范围内和运行时有效**|JDBC就属于这一类，因为它在编译时仅需要验证是否符合接口规范，在测试和运行时才用到JDBC的驱动|
|system|系统依赖范围，其效果与 provided 的依赖范围一致。其用于添加非 Maven 仓库的本地依赖，通过依赖元素 dependency 中的 systemPath 元素指定本地依赖的路径。鉴于使用其会导致项目的可移植性降低，一般不推荐使用。|无|
|import|导入依赖范围，该依赖范围只能与 dependencyManagement 元素配合使用，其功能是将目标 pom.xml 文件中 dependencyManagement 的配置导入合并到当前 pom.xml 的 dependencyManagement 中。|无|

---

#### ③依赖传递

+ 在Maven工程下，我们可以通过`pom.xml`文件来指定我们所依赖的项目
+ 指定后，**依赖项目的依赖项也会被我们继承**，这样，我们就不用再配置一遍我们项目所依赖的项目的依赖了
+ 我们称这种特性为**依赖传递**
  + 在IDEA的Maven可视化界面可以通过其项目的`Dependencies`来查看其当前依赖
![IDEA查看依赖传递](../文件/图片/Maven图片/IDEA查看依赖传递.png)
  + 依赖项的依赖范围是`test`或`provided`时，无法传递依赖
  + 依赖项存在optional标签设置为true时，无法传递依赖



---

#### ④依赖冲突

+ 当我们的项目所指定的依赖与我们依赖项目所指定的依赖重复时，我们称这种情况为**依赖冲突**
  + Maven默认已经考虑到了该情况，因此它默认会根据一些情况处理这些冲突
  + 当发生冲突的双方的依赖深度不同时，取较近的一方。
    + 例:A继承了B，同时直接指定C为其依赖项，B也指定了C为其依赖项，A继承的C的以来深度是1，因为是直接继承的，而A继承的B的C是间接继承的，依赖深度是2，因此会依据就近原则，**选择A直接依赖的C**，而不是B依赖的C
  + 如果双方的依赖深度相同，那么Maven将比较双方谁在`pom.xml`文件内先声明，先声明的，就是被选择的一方
+ 

---

### （四）构建

#### ①构建过程

+ 项目构建是指将源代码、依赖库和资源文件等转换成可执行或可部署的应用程序的过程，在这个过程中包括编译源代码、链接依赖库、打包和部署等多个步骤
+ Maven提供了简便的命令来提高构建的效率，使得开发人员能够**更加专注于应用程序的开发和维护**，而不必关心应用程序的构建细节
+ 构建分为多个步骤
  + 清理:删除之前编译生成的结果
  + 编译:对源码进行编译，并放入`target`文件夹内
  + 测试:测试test文件夹的东西
  + 报告:测试的时候会自动生成报告，存放在`target/surefire-reports`内
  + 打包:生成项目对应的jar或war包，打包会自动进行编译
  + 部署:将生成的jar或war包上传到本地或私服仓库，部署会包含了打包过程
+ 本地仓库内各依赖按照`groupId->artifactId->version`来区分每个依赖，我们也可以通过这种方式找到我们想要的包

![构建过程](../文件/图片/Maven图片/项目构建.png)

---

#### ②构建命令

|命令|描述|备注|
|:---:|:---:|:---:|
|mvn compile|编译项目，生成target文件|不会编译test文件夹下的源码|
|mvn package|打包项目，生成jar或war文件|无|
|mvn clean|清理编译或打包后的项目结构|无|
|mvn install|打包后上传到maven本地仓库|无|
|mvn deploy|只打包，上传到maven私服仓库|无|
|mvn site|生成站点|无|
|mvn test|执行测试源码|建议**测试类以`Test`开头或结尾**，且**方法以`test`开头或结尾**|
|mvn test-compile|编译测试源码|无|

+ 使用`package`命令打war包时，可能会出现报错，大概率是因为**打war包的插件与当前的JDK版本不匹配**

~~~xml
  <!-- 添加如下配置让插件匹配JDK17 -->
  <build>
      <!-- jdk17 和 war包版本插件不匹配 -->
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-war-plugin</artifactId>
              <version>3.2.2</version>
          </plugin>
      </plugins>
  </build>
~~~

---

