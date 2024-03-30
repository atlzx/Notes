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

+ 注意:
> + `Maven`的配置需要本机配置了`Java`并拥有`JAVA_HOME`

---

## 三、Maven工程

### （一）Maven文件夹结构

+ 解压后的`Maven`是下面这样的:
  + bin目录:含有Maven的运行脚本
  + boot目录:含有plexus-classworlds类加载器框架
  + conf目录:含有Maven的核心配置文件
  + lib目录:含有`Maven`运行时所需的`Java`类库
  + LICENSE、NOTICE、README.txt：针对Maven版本，第三方软件等简要介绍

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

#### ①创建JavaSE工程

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

#### ②创建JavaWeb工程

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

#### ③Maven工程项目结构

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

### （三）pom.xml文件配置

+ [该网站](https://mvnrepository.com/)可以寻找我们想要得到的库，并提供给我们其相应的xml标签

|所属父标签|标签名|可选值|含义|相对pom.xml是否必须|相对父标签是否必须|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**Project**|modelVersion|自定义|指定pom的版本|是|是|无|
|^|groupId|^|指定项目属于的组|是|是|无|
|^|artifactId|^|指定项目在组内的唯一id|是|是|无|
|^|version|^|当前项目的版本|否|否|无|
|^|packaging|jar:打包成jar包<br>war:打包成war包，项目被识别为JavaWeb项目<br>pom:不打包，用来做继承的父工程|指定打包方式，默认是jar|^|^|无|
|^|dependencies|各个dependency标签|各依赖的父标签|^|^|无|
|**dependencies**|dependency|各依赖子标签|指定依赖|^|^|无|
|**dependency**|groupId|自定义|指定该依赖所属的组|^|是|无|
|^|artifactId|^|指定该依赖在其组内的唯一标识|^|^|无|
|^|version|^|指定依赖的版本|^|^|无|
|^|scope|^|不知道干嘛的|^|否|不知道|

### （四）构建

#### ①构建过程

+ 项目构建是指将源代码、依赖库和资源文件等转换成可执行或可部署的应用程序的过程，在这个过程中包括编译源代码、链接依赖库、打包和部署等多个步骤
+ Maven提供了简便的命令来提高构建的效率，使得开发人员能够**更加专注于应用程序的开发和维护**，而不必关心应用程序的构建细节
+ 构建分为多个步骤
  + 清理
  + 编译
  + 测试
  + 报告
  + 打包
  + 部署

![构建过程](../文件/图片/Maven图片/项目构建.png)

---

#### ②构建命令

|命令|描述|
|:---:|:---:|
|mvn compile|编译项目，生成target文件|
|mvn package|打包项目，生成jar或war文件|
|mvn clean|清理编译或打包后的项目结构|
|mvn install|打包后上传到maven本地仓库|
|mvn deploy|只打包，上传到maven私服仓库|
|mvn site|生成站点|
|mvn test|执行测试源码|

---

