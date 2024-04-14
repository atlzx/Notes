# SpringMVC笔记

## 一、简介

+ Spring Web MVC是基于Servlet API构建的原始Web框架，从一开始就包含在Spring Framework中
+ 其正式名称为Spring Web MVC，来源于其源模块的名称
+ SpringMVC是当前Java EE项目表述层开发的首选方案
  + Spring 家族原生产品，与IOC容器等基础设施无缝对接
  + 表述层各细分领域需要解决的问题全方位覆盖，提供全面解决方案
  + 代码清新简洁，大幅度提升开发效率
  + 内部组件化程度高，可插拔式组件即插即用，想要什么功能配置相应组件即可
  + 性能卓著，尤其适合现代大型、超大型互联网项目要求

---

## 二、使用

### （一）调用流程

+ Spring MVC与许多其他Web框架一样，是围绕前端控制器模式设计的，其中**中央Servlet  DispatcherServlet做整体请求处理调度**

![SpringMVC调用流程](../文件/图片/SpringMVC图片/SpringMVC调用流程.png)

+ DispatcherServlet :SpringMVC提供，我们需要使用web.xml配置使其生效，它是**整个流程处理的核心，所有请求都经过它的处理和分发**
+ HandlerMapping :SpringMVC提供，我们**需要进行IoC配置使其加入IoC容器方可生效**，它内部缓存handler(controller方法)和handler访问路径数据，**DispatcherServlet收到请求时，首先调用它来确认访问路径能否匹配，用于查找路径对应的handler**
+ HandlerAdapter : SpringMVC提供，我们**需要进行IoC配置使其加入IoC容器方可生效**，它可以处理请求参数和处理响应数据数据，**DispatcherServlet确认路径映射后，便通过handlerAdapter间接调用handler**，他是handler和DispatcherServlet之间的适配器
+ Handler : handler又称处理器，他是Controller类内部的方法简称，是**由我们自己定义，用来接收参数，向后调用业务，最终返回响应结果**
+ ViewResovler : SpringMVC提供，我们**需要进行IoC配置使其加入IoC容器方可生效**！视图解析器主要作用简化模版视图页面查找的，但是需要注意，前后端分离项目，后端只返回JSON数据，不返回页面，那就不需要视图解析器！所以，**视图解析器，相对其他的组件不是必须的**

---

### （二）HelloSpringMVC

+ 想实现一个最基本的SpringMVC接收请求并处理的后端代码，需要如下操作:
  + 将本项目变为web项目，即添加webapp以及web.xml等
  + 从调用流程可以看到，大部分的组件都依赖于IoC容器，因此我们需要先创建一个IoC配置类，并提供RequestMappingHandlerMapping和RequestMappingHandlerAdapter的bean对象
  + 创建Controller层的类，配置@Controller注解。在里面写一个方法，方法**使用@RequestMapping(value)指定映射路径，使用@ResponseBody指定返回值直接返回给前端**
  + 创建一个类，继承AbstractAnnotationConfigDispatcherServletInitializer类，并实现其中的三个方法
  + 配置tomcat
  + 运行，访问刚刚使用@RequestMapping配置的映射路径

+ [配置类](../源码/SpringMVC/HelloMVC/src/main/java/com/springmvc/example/config/MvcConfig.java)
+ [SpringMVC初始配置](../源码/SpringMVC/HelloMVC/src/main/java/com/springmvc/example/config/MvcInit.java)
+ [控制层类](../源码/SpringMVC/HelloMVC/src/main/java/com/springmvc/example/controller/HelloController.java)

---

