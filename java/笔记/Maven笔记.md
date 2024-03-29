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

## 三、Maven基本使用

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
  + `GroupID`:用于设置该项目的组`ID`，相当于它在`Maven`仓库内的唯一标识，一般都使用`com.公司/BU.业务线.\子业务线`来编写，如`com.taobao.tddl`
  + `ArtifactID`:用于设置模块名，默认与当前模块名一致
  + `Version`:版本号，`IDEA`会自动设置，格式为`主版本号.次版本号.修订号`
  + `Packaging`:配置打包方式
    + `jar`:打成jar包，用于普通的`JavaSE`工程
    + `war`:打成war包，用于`JavaWeb`工程
    + `pom`:不打包，用来做继承的父工程

#### ①创建JavaSE工程

+ 我们在新创建一个项目后，新创建一个模块，构建系统选择`Maven`
+ 点开高级选项，填写`Group ID`，然后点击创建
+ 创建后，会打开`pom.xml`

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

+ 第一次打开时,`idea`可能会报错，因为此时`Maven`缺少自己运行的库，在`IDEA`进度可以看到在下载一些东西，等下载完成后，就不报错了

---

#### ②创建JavaWeb工程

