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

### （三）访问路径设置

+ @RequestMapping注解用来设置访问路径的映射，它可以作用于类和方法上
  + 作用于类上时，设置的路径会**成为其类内所有方法匹配路径的公共前缀**
  + 作用于方法上时，设置的路径会与类上的前缀做拼接，从而得到完整路径的映射，匹配时，匹配的是该完整路径的映射。如果类上无路径，那么设置路径就是完整路径
  + 如果**只想匹配类上的注解声明的路径，那么方法上只需要声明注解，无需写value即可**
  + 除此以外，该注解还能指定作用的方法接收什么类型的请求。直接通过它的method属性接收枚举类RequestMethod中的属性值
    + 同时，为了方便我们指定类型请求，SpringMVC提供了一些注解专门就可以直接设置接收类型请求，它们的含义也可以直接看名字看出来
      + @GetMapping
      + @PostMapping
      + @PutMapping
      + @DeleteMapping
      + @PatchMapping
+ 设置路径时，路径可以使用通配符来匹配

|通配符|作用|备注|例|
|:---:|:---:|:---:|:---:|
|?|匹配任意一个字符|无|`/pages/t?st.html` 匹配 `/pages/test.html`|
|*|匹配一层路径的零个或多个字符|无|`/*/test.html` 匹配 `/pages/test.html`|
|**|匹配零层或多层路径|**必须写在路径最后**|`/pages/**`匹配`/pages/test/page.html`|
|{name}|取出对应路径的字段值|无|`/{page}/test.html`匹配`/pages/test.html`，读取到的值为name=pages|
|{name:[a-z]}|取出对应路径满足后面的正则表达式的值|`/{page:[a-z]}/test.html`匹配`/pages/test.html`，但不匹配`/pages1/test.html`|
|{*path}|从当前路径开始截取，直到最后|**需要写在路径最后**|`/resources/{*file}`匹配`/resources/images/file.png`，读取到的值为file=/images/file.png|

---

### （四）参数接收

#### ①param参数

+ 可以使用@RequestParam注解进行**get参数和请求体参数的接收**
  + @RequestParam注解是专门接收param参数的
    + 如果传来的参数名与方法对应的参数名不同，可以使用value属性**指定传来的前端参数名**，并将该注解作用于其对应的方法参数上
    + required可以**指定该参数是否必须**，默认是true，改为false为不必须，**此时参数可以不传，不传不会报错**
    + defaultValue用来**指定参数的默认值**
    + **注解必须作用于相关参数，否则会报500错误**
+ 使用Param参数传递可以传递有多种情况
  + 前端传来的参数名与方法对应的参数名不一致:指定@RequestParam的value为前端参数名，并将该注解作用于对应的方法参数上
  + 一个key对应多个值:使用List来接收，使用@RequestParam指定List接收的key的名称
  + 使用实体类接收:要求**前端参数名与方法实体类的属性名必须一致**（不需要注解）
+ [样例](../源码/SpringMVC/HelloMVC/src/main/java/com/springmvc/example/controller/ParamController.java)

---

#### ②路径参数

+ @PathVariable注解是用于将通过通配符得到的值注入到方法参数中去的，但通配符也可以得到param参数，因此它也能接收
  + value属性用来指定要注入的通配符的名称，该名称**必须与通配符中写的名称一致**
  + required与上面的@RequestParam一致
+ [样例](../源码/SpringMVC/HelloMVC/src/main/java/com/springmvc/example/controller/ParamController.java)

---

#### ③JSON参数

+ 为了接收参数，我们需要在配置类上**加上@EnableWebMvc注解**
  + 该注解可以自动提供RequestMappingHandlerMapping和RequestMappingHandlerAdapter的bean对象，就不需要我们再手动提供了
  + 该注解可以在接收到JSON类型参数时自动解析JSON，从而避免出现415错误
+ 接下来创建一个该JSON串对应的实体类，**实体类的属性需要与JSON的key一致**
+ 在对应的方法内使用该实体类对象接收JSON，并**使用@RequestBody注解将JSON值注入**
+ [样例](../源码/SpringMVC/GetParam/src/main/java/com/springmvc/example/controller/ParamController.java)

---

#### ④Cookie、Session与请求头参数

+ @CookieValue注解用来读取Cookie内的指定字段的值，可以使用value属性指定要读取的key的字段名
+ @SessionAttribute注解用来读取session内的指定key的值，可以使用value属性指定要读取的key的字段名
+ @RequestHeader注解用来读取请求头内的指定字段的值，使用value属性指定要读取的key的字段名，这里是请求头的[参数列表](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Accept)
+ [样例](../源码/SpringMVC/GetParam/src/main/java/com/springmvc/example/controller/ParamController.java)

---

#### ⑤原生API对象

+ SpringMVC支持我们直接在方法内声明Servlet的原生API对象并调用它们，其中:
  + 在Request域中，我们可以**通过方法参数的ServletRequest、Map、Model、ModelMap、ModelAndView等对象来直接获得**
  + 在Session域中，我们可以**通过方法参数的HttpSession对象来获得**
  + 在Application域中，我们可以**直接在类中声明一个ServletContext对象，然后给它一个@AutoWired注解直接注入**

|对象所属类|作用|
|:---:|:---:|
|ServletRequest|请求信息对象|
|ServletResponse|响应信息对象|
|HttpSession|会话对象，该会话对象永不为空|
|InputStream|请求信息的字节输入流对象|
|Reader|请求信息的字符输入流对象|
|OutputStream|响应信息的字节输出流对象|
|Writter|相应信息的字符输出流对象|
|Map/Model/ModelMap|request域对象|
|Errors、BindingResult|验证中的错误对象和数据绑定结果对象|

---

### （五）响应数据

+ handler在接收到参数并进行处理后，需要向前端响应数据，它可以这样响应:
  + 响应对应的静态资源
  + 响应页面跳转
  + 返回JSON数据
  + 请求转发与重定向

#### ①返回模板视图

+ 现在的Web开发分为两种:
  + 前后端分离:这是**目前的主流趋势**。前后端分离开发可以提高开发效率，降低代码耦合并提高项目的可拓展性和维护性
  + 混合开发模式:以jsp为代表的开发模式，它将Java代码内嵌在HTML中，来生成一个动态页面，现在该模式已经逐渐过时
+ 这里演示如何在老的jsp项目中返回模板视图:
  + 首先先创建一个jsp页面，jsp页面建议创建在WEB-INF目录下，该目录下的资源无法被外部直接访问，比较安全
  + **让配置类实现WebMvcConfigurer接口**，该接口定义了一系列的方法来支持我们开启一些SpringMVC默认关闭的。我们可以重写对应的方法来开启对应的功能
  + 重写configureViewResolvers(ViewResolverRegistry registry)方法，来使SpringMVC支持对jsp的视图模板数据返回
  + 在方法内调用`registry.jsp("/WEB-INF/views/",".jsp");`来使SpringMVC支持对jsp的视图模板数据返回
  + 在cotroller层创建类，使用相关注解来配置方法，最后直接返回想返回的模板数据即可
+ [配置类示例](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/config/Config.java)
+ [控制类示例](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/JSPSample.java)
+ [jsp页面示例](../源码/SpringMVC/Response/src/main/webapp/WEB-INF/view/index.jsp)

---

#### ②返回静态资源

+ DispathcerServlet在向HandlerMapping确认地址映射时，如果路径不匹配，默认就返回404，但是前端在请求静态资源时，应该不需要经过路径映射而直接得到响应
+ 前端在请求静态资源失败时，原因是静态资源并没有被@RequestMapper注解作用，而只有实现@RequestMapper注解，才能被HandlerMapping记住其映射
+ 为了让前端能够正常请求到静态资源，我们继续**在配置类中实现WebMvcConfigurer接口的configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)方法**
+ 在方法内执行`configurer.enable();`语句来使前端正确请求到静态资源，即使SpringMVC开启静态资源匹配
+ 该方法的原理是，DispatcherServlet在发现HandlerMapping未成功映射后，会再向DefaultServleHttpRequestHandler寻找路径映射，而该类是专门寻找静态资源的
+ [配置类示例](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/config/Config.java)

---

#### ③转发与重定向

+ 转发需要handler方法返回以`forwar:`开头的字符串，而重定向需要handler方法返回以`redirect:`开头的字符串
+ 如果是项目下的路径，**直接写classpth路径（webapp与java目录同级，都是classpath路径）即可，不需要写项目根路径**
+ **如果转发或重定向的是方法，后面的路径跟的是对应方法的@RequestMapping的映射路径，不是相对于classpath的路径**
+ [控制类示例](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/JSPSample.java)

---

#### ④返回JSON数据

+ 想实现返回JSON数据需要**配置类实现WebMvcConfigurer接口并实现@EnableWebMvc注解**
+ 确保**jackson依赖被导入**
+ 确保方法或方法所在类**被@ResponseBody注解作用**，该注解的作用是**使方法返回值接收JSON转换器处理并不通过视图解析器**
+ [控制类样例](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/ReturnJSON.java)
+ 注意
> + @RestController注解相当于@Controller+@ResponseBody
> + 一般来说，返回任意类型的都可以，返回前都会被转换成JSON字符串

---

### （六）Restful开发模式

+ RESTful（Representational State Transfer）是一种软件架构风格，用于设计网络应用程序和服务之间的通信。它是一种基于标准 HTTP 方法的简单和轻量级的通信协议，广泛应用于现代的Web服务开发
+ 通过遵循 RESTful 架构的设计原则，可以构建出易于理解、可扩展、松耦合和可重用的 Web 服务。RESTful API 的特点是简单、清晰，并且易于使用和理解，它们使用标准的 HTTP 方法和状态码进行通信，不需要额外的协议和中间件
+ 总而言之，**RESTful 是一种基于 HTTP 和标准化的设计原则的软件架构风格，用于设计和实现可靠、可扩展和易于集成的 Web 服务和应用程序**

#### ①Restful风格特点

+ 每个URI都是一个资源的唯一标识，**该资源是一个，而不能是一组**
+ 客户端通过get、post、put、delete请求分别实现查询、保存、更新和删除操作
+ 资源的表现形式需要是JSON或XML文件
+ 客户端与服务端之间的交互在请求之间是无状态的，从客户端到服务端的每个请求都必须包含理解请求所必需的信息

---

#### ②设计规范

|请求方式|对应操作|
|:---:|:---:|
|GET|查询操作|
|POST|保存操作|
|PUT|更新操作|
|DELETE|删除操作|

|操作|传统请求路径|Restful风格请求路径|
|:---:|:---:|:---:|
|保存|/CRUD/saveEmp|/CRUD/emp|
|删除|/CRUD/removeEmp?empId=2|/CRUD/emp/2|
|更新|/CRUD/updateEmp|/CRUD/emp|
|查询|/CRUD/editEmp?empId=2|/CRUD/emp/2|

+ 把原本的请求路径由携带动标识修改为名词，将URI整体作为资源的唯一标识
+ 根据接口的具体实现功能，选择具体的请求方式
+ 路径参数应该用于指定资源的唯一标识或者 ID，而请求参数应该用于指定查询条件或者操作参数
+ **请求参数应该限制在 10 个以内**，过多的请求参数可能导致接口难以维护和使用
+ 对于**敏感信息，最好使用 POST 和请求体来传递参数**

---

#### ③示例

+ 数据结构： User {id 唯一标识,name 用户名，age 用户年龄}
+ 功能分析:
  + 用户数据分页展示功能（条件：page 页数 默认1，size 每页数量 默认 10）
  + 保存用户功能
  + 根据用户id查询用户详情功能
  + 根据用户id更新用户数据功能
  + 根据用户id删除用户数据功能
  + 多条件模糊查询用户功能（条件：keyword 模糊关键字，page 页数 默认1，size 每页数量 默认 10）

|功能|请求路径|请求方式|参数|备注|
|:---:|:---:|:---:|:---:|:---:|
|分页查询|/user/|GET|page=1<br>size=10|URI必须对应的是一个资源，但是分页查询得到的是一批资源，需要使用param|
|保存用户|/user|POST|用户信息|无|
|根据id查询|/user/{id}|GET|无参|无|
|根据id更新|/user/{id}|PUT|用户信息|无|
|根据id删除|/user/{id}|DELETE|无|无|
|模糊查询|/user|GET|keyword=xxx<br>page=xxx<br>size=xxx|URI必须对应的是一个资源，但是模糊查询得到的是一批资源，需要使用param|

+ 注意:
> + **路径重复是URI和请求方式都重复才叫重复**

---

### （七）异常处理

+ 异常处理有两种方式:
  + 第一种方式（编程式异常处理）是在代码内部显式的调用try-catch-finally语句来处理异常，该语句会**导致代码混杂在业务代码中**，且团队协作时不同的人编写的针对相同异常处理的逻辑可能会不同，**不利于代码维护，且可读性较差**
  + 另一种方式（声明式异常处理）是通过配置的方式统一处理程序运行时出现的异常，**声明式异常处理可以使代码更加简洁、易于维护和扩展**
+ 想使用声明式异常处理，我们需要:
  + 创建一个异常处理的控制类，并**使用@ControllerAdvice注解作用于该类，该注解可以声明作用类为异常处理的控制类**
  + @RestControllerAdvice注解相当于@ControllerAdvice+@ResponseBody注解
  + 接下来**在异常处理控制类内的方法上使用@ExceptionHadler注解，传入指定异常的Class对象,来声明该方法用来处理什么异常**
    + 如果某个控制类的方法出现了异常，但是异常控制类的方法中仅有该异常父类的处理方法，那么该方法会被执行
    + 如果有具体的异常类方法，那么具体的类方法会执行而其异常父类的处理方法不会执行
    + 也就是说，**具体的异常类处理方法比其父类异常类处理方法优先级更高**
  + 在控制类中手动抛出异常，查看处理情况
+ [异常处理类](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/ExceptionClass.java)
+ [异常控制类](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/ExceptionClassSample.java)

---

### （八）拦截器

#### ①拦截器概念

+ 在JavaWeb中，过滤器仅能在**请求到达控制前和控制类返回后进行一些操作**，局限性较大
+ SpringMVC提供了拦截器，它可以**在SpringMVC负责请求的任意的地方对请求进行拦截**并进行一些操作
  + 拦截器可以在handler方法执行前后以及在渲染视图后执行，它相对于拦截器更加灵活

![拦截器拦截位置](../文件/图片/SpringMVC图片/拦截器拦截位置.png)


|名称|拦截情况|过滤情况|放行情况|工作平台|拦截范围|IoC容器是否支持|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|过滤器|把请求拦截住，并执行一些操作再放行|针对请求进行统一处理|针对请求执行了相关操作后，如果请求满足条件，那么放行|工作在Servlet容器内|能够拦截整个Web应用|需要调用专门的工具方法才能得到IoC容器的支持|
|拦截器|^|^|^|工作在SpringMVC的基础上|能够拦截整个SpringMVC负责的请求|本身受IoC容器管理，当然支持|

---

#### ②拦截器的使用

+ 首先先创建一个拦截器类，该类需要**实现HandlerInterceptor接口**，并实现接口的三个方法
+ 让配置类**实现WebMvcConfigurer接口，实现addInterceptors(InterceptorRegistry registry)方法**
  + 调用registry.addInterceptor()方法，传入我们刚才创建的拦截器对象，该配置会使所有方法都被拦截器拦截
  + 在上面的基础上，我们可以再调用addPathPatterns方法（可以链式调用），并向方法传入多个字符串匹配路径来精确的设置哪些方法需要被拦截
  + excludePathPatterns（可以链式调用）方法可以指定哪些方法被排除在拦截范围内，也是向方法内传入指定字符串即可

|方法|参数|作用|返回值|返回值类型|异常|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)|request:请求对象<br>response:响应对象<br>handler:handler方法对象|在handler方法执行前执行|返回true代表放行，false代表不放行|boolean|Exception|无|
|postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)|request:请求对象<br>response:响应对象<br>handler:handler方法对象<br>modelAndView:不知道干嘛的|在handler方法**正常执行完毕后**执行|无返回值|void|Exception|**如果handler方法执行时出现异常,那么该方法不会执行**|
|afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)|request:请求对象<br>response:响应对象<br>handler:handler方法对象<br>ex:不知道干嘛的|在逻辑上是在视图解析器解析完毕后执行，但**返回值不经过视图解析器时，也会执行**|无返回值|void|Exception|无|

~~~java

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        //将拦截器添加到Springmvc环境,默认拦截所有Springmvc分发的请求
        registry.addInterceptor(new Process01Interceptor());
        
        //精准匹配,设置拦截器处理指定请求 路径可以设置一个或者多个,为项目下路径即可
        //addPathPatterns("/common/request/one") 添加拦截路径
        registry.addInterceptor(new Process01Interceptor()).addPathPatterns("/common/request/one","/common/request/tow");
        
        
        //排除匹配,排除应该在匹配的范围内排除
        //addPathPatterns("/common/request/one") 添加拦截路径
        //excludePathPatterns("/common/request/tow"); 排除路径,排除应该在拦截的范围内
        registry.addInterceptor(new Process01Interceptor())
                .addPathPatterns("/common/request/*")
                .excludePathPatterns("/common/request/tow");
    }

~~~

---

### （九）参数校验

+ JSR 303 是 Java 为 Bean 数据合法性校验提供的标准框架，它已经包含在 JavaEE 6.0 标准中
+ 该标准规定了一些用于校验的注解

|注解|作用|备注|
|:---:|:---:|:---:|
|@Null|必须为null|无|
|@NotNull|必须非空|无|
|@AssertTrue|必须为true|无|
|@AssertFalse|必须为false|无|
|@Digits(integer,fratction)|限制数值范围在指定区间|**作用于数值**|
|@DecimalMax(value)|设置上限|无|
|@DecimalMin(value)|设置下限|无|
|@Max(value)|设置上限|无|
|@Min(value)|设置下限|无|
|@Pattern(value)|正则匹配|无|
|@Past|必须是过去的日期|**作用于日期类**|
|@Future|必须是未来的日期|^|
|@Size(max,min)|限制字符长度在指定区间内|无|

+ Hibernate是对JSR 303 的一个参考实现，下面是它自定义的一些注解

|注解|作用|备注|
|:---:|:---:|:---:|
|@Email|校验邮箱格式|无|
|@NotEmpty|非空|**作用于String类型属性**|
|@NotBlank|去首尾空白字符后非空|^|
|@Length|标注值字符串大小必须在指定的范围内|^|
|@Range|作用值必须在指定范围内|**作用于数值类型**|

+ 首先我们需要创建一个实体类，在实体类内的属性上加一些校验注解
+ 接下来在对应的控制类上添加方法，**在实体类对象前加上@Validated注解**，同时，**BindingResult对象必须紧紧跟在被校验对象的后面**
+ 测试

+ [实体类](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/pojo/User.java)
+ [控制类](../源码/SpringMVC/Response/src/main/java/com/springmvc/example/controller/ValidationController.java)

---

## 三、整合

+ SpringMVC是SSM的最后一个学习内容，这里将描述如何将SSM整合在一个项目内

### （一）基本概述

+ 首先需要明确，**SSM整合需要两个IoC容器**
  + 实际上只需要一个IOC容器就可以完成整合，但使用两个IOC有以下好处:
    + 分离关注点：通过初始化两个容器，可以将各个层次的关注点进行分离。这种分离使得各个层次的组件能够更好地聚焦于各自的责任和功能。
    + 解耦合：各个层次组件分离装配不同的IoC容器，这样可以进行解耦。这种解耦合使得各个模块可以独立操作和测试，提高了代码的可维护性和可测试性
    + 灵活配置：通过使用两个容器，可以为每个容器提供各自的配置，以满足不同层次和组件的特定需求。每个配置文件也更加清晰和灵活
+ 这两个IOC容器分别是root容器与web容器
  + web容器用来盛放controller层与SpringMVC相关的核心组件
  + root容器用来盛放service、AOP、mapper、mybatis、dataSource等相关组件
  + **web组件是root组件的子容器，子容器可以访问父容器的组件，但不能反过来**
+ 对于配置类相关问题，一般**建议编写三个配置类，分别作用于controller、service和mapper**

---

### （二）整合流程

#### ①依赖导入

+ 首先需要确保依赖导入

~~~xml

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <spring.version>6.1.5</spring.version>
        <servlet.version>6.0.0</servlet.version>
        <lombok.version>1.18.32</lombok.version>
        <jackson.version>2.17.0</jackson.version>
        <hibernate-validator.version>8.0.1.Final</hibernate-validator.version>
        <junit.version>5.10.2</junit.version>
        <annotation.version>2.1.1</annotation.version>
        <mysql-connection.version>8.0.33</mysql-connection.version>
        <druid.version>1.2.22</druid.version>
        <mybatis.version>3.5.16</mybatis.version>
        <PageHelper.version>6.1.0</PageHelper.version>
        <mybatis-spring.version>3.0.3</mybatis-spring.version>

    </properties>

    <dependencies>
        <!-- Spring核心框架 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <!--spring aop依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
        </dependency>
        <!--spring aspects依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
        </dependency>
        <!-- spring jdbc封装及事务支持依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
        </dependency>
        <!-- springMVC依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
        </dependency>
        <!-- Servlet依赖 -->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
        </dependency>
        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- jackson依赖 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
        <!-- hibernate校验依赖 -->
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>
        <!-- JDK注解拓展，有一些依赖注入的注解 -->
        <dependency>
            <groupId>jakarta.annotation</groupId>
            <artifactId>jakarta.annotation-api</artifactId>
        </dependency>
        <!-- spring junit兼容依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
        <!-- junit依赖，用来测试 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
        </dependency>
        <!-- MySQL数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- Druid连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <!-- Mybatis依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
        </dependency>
        <!-- pageHelper分页插件 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
        </dependency>
        <!-- mybatis适配spring依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
        </dependency>
    </dependencies>

~~~

---

#### ②创建配置类

+ 首先是Controller层相关的配置类，它需要实现:
  + 声明为配置类
  + 开启组件扫描
  + 提供HandlerAdatper和HandlerMapping对象
  + 提供视图解析器
  + 提供JSON转换功能
  + 提供静态资源访问

~~~java

    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.*;


    /**
        实现controller层相关配置类需要做如下配置:
            声明为配置类
            开启组件扫描
            提供HandlerAdapter与HandlerMapping
            静态资源处理
            开启jsp视图解析
            实现json转换
            配置拦截器
    */
    @Configuration  // 声明配置类
    @ComponentScan({"com.springmvc.example.controller","com.springmvc.example.exception"})  // 开启组件扫描
    @EnableWebMvc  // 提供HandlerAdapter与HandlerMapping并实现JSON转换

    // 实现WebMvcConfigurer来进一步配置
    public class ControllerConfig implements WebMvcConfigurer {

        // 开启jsp视图解析
        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            registry.jsp("/WEB-INF/view/",".jsp");
        }

        // 开启静态资源处理
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }

        // 该方法用于配置拦截器
        @Override
        public void addInterceptors(InterceptorRegistry registry) {

        }
    }

~~~

+ 接下来是Service相关的配置类，该配置类需要实现:
  + 声明配置类
  + 开启组件扫描
  + 开启事务管理
  + 提供事务管理对象
  + 开启AOP的AspectJ自动代理以支持AOP

~~~java

    @Configuration  //  声明为配置类
    @ComponentScan("com.springmvc.example.service")  // 开启组件扫描
    @EnableAspectJAutoProxy  // 开启AspectJ自动代理以支持AOP操作
    @EnableTransactionManagement  // 开启Spring的事务管理
    public class ServiceConfig {

        @Bean  // 向IOC容器提供事务管理对象
        public TransactionManager transactionManager(DataSource dataSource){
            DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
            dataSourceTransactionManager.setDataSource(dataSource);
            return dataSourceTransactionManager;
        }

    }

~~~

+ 然后是mapper层相关配置类，该配置类需要实现:
  + 声明配置类
  + 组件扫描
  + 提供Druid连接池对象
  + 提供SqlSessionFactoryBean对象以支持自动生成sqlSession对象
  + 提供MapperScannerConfigurer对象以支持自动生成Mapper代理对象
  + 使用代码实现mybatis-config.xml相关的配置
  + 提供数据库连接的必要参数
+ 该配置类与整合mybatis相关，详情参考[下面](#mybatisConfig)
<br>
<br>
<br>

+ 最后是实现了AbstractAnnotationConfigDispatcherServletInitializer类的初始化类  
  + 从最开始的SpringMVC开始，我们就一直在配置该类及实现它的三个方法
  + 现在详细说明一下三个方法的用处:

|方法|用处|备注|
|:---:|:---:|:---:|
|getRootConfigClasses|返回一个Class[]对象，对象内的元素是rootIOC容器内应该盛放的配置类的Class对象|无|
|getServletConfigClasses|返回一个Class[]对象，对象内的元素是webIOC容器内应该盛放的配置类的Class对象|无|
|getServletMappings|返回一个String[]对象，对象内的元素是DispatcherServlet拦截的请求路径，一般设置为 `/` ，表示全部拦截|

~~~java

    import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

    public class InitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
        @Override
        // 该方法返回的数组对象内需要包含rootIOC容器的配置类的Class对象
        protected Class<?>[] getRootConfigClasses() {
            return new Class[]{ServiceConfig.class, MapperConfig.class};
        }

        @Override
        // 该方法返回的数组对象需要包含webIOC容器需要的配置类的Class对象
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{ControllerConfig.class};
        }

        @Override
        // 用于指定DispatcherServlet拦截的请求路径，/ 是全都拦截
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
    }


~~~

---

#### ③整合mybatis

+ 使用Mybatis来进行数据库操作调用的API如下:

~~~java
    //1.读取外部配置文件
    InputStream ips = Resources.getResourceAsStream("mybatis-config.xml");

    //2.创建sqlSessionFactory
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(ips);

    //3.创建sqlSession
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //4.获取mapper代理对象
    EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
    //5.数据库方法调用
    int rows = empMapper.deleteEmpById(1);
    System.out.println("rows = " + rows);
    //6.提交和回滚
    sqlSession.commit();
    sqlSession.close();
~~~

+ 在上面的例子中，可以看到:
  + SqlSessionFactoryBuilder是一个工具类对象，它的用处就是构建一个SqlSessionFactory对象，**用完就扔，不应该放进IOC容器内**
  + SqlSessionFactory对象可以得到数据库的会话对象SqlSession，**它是可以复用的，需要把它放入IOC容器内**
  + SqlSession对象是进行数据库操作时的会话对象，**每个线程都应该有它自己的 SqlSession 实例，也就是说SqlSession的实例不是线程安全的，因此它无法被共享。**，不应该放入IOC容器对象内
  + Mapper对象是由对应的SqlSession对象得到的，因此从技术上说Mapper对象的最大作用域应该与SqlSession一致，不应该放入IOC容器内。但是**从使用的角度来说，业务类（service）需要注入mapper接口，所以mapper应该交给ioc容器管理**
+ 综上，我们需要把SqlSessionFactory与Mapper对象放入IOC容器内进行管理
+ 同时，为了简化我们整合mybatis配置的相关操作，mybatis提供了对应的**SqlSessionFactoryBean**和**MapperScannerConfigurer**来分别简化我们提供SqlSessionFactory的bean与Mapper相关的bean的过程
+ 接下来开始进行mybatis的整合:
<br>
+ 首先创建数据库连接的jdbc.properties文件，里面存放实现数据库连接所需的必要配置

~~~properties

    jdbc.url=jdbc:mysql://localhost:3306/atguigudb
    jdbc.driver=com.mysql.cj.jdbc.Driver
    jdbc.username=root
    # yyy对应的是密码，使用时修改为对应真实密码
    jdbc.password=yyy

~~~

+ 实现mybatis的相关配置，它有两种实现方式
  + 使用mybatis-config.xml
  + **使用代码进行配置(推荐)**
+ 首先是使用myabtis-config.xml进行配置，首先创建一个xml文件:
  + 环境变量与连接池的部分应该舍去，因为我们要使用Druid连接池并已在proerties文件内写好了jdbc连接的相关配置

~~~xml

    <?xml version="1.0" encoding="UTF-8" ?>

    <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <settings>
            <!-- 启用数据库表字段的自动小驼峰命名转换 -->
            <setting name="mapUnderscoreToCamelCase" value="true"/>
            <setting name="logImpl" value="STDOUT_LOGGING"/>
            <setting name="autoMappingBehavior" value="FULL"/>
        </settings>

        <typeAliases>
            <!-- 设定该包下自动获得别名，别名为首字母小写的类名，如果有Alias注解，那么别名为注解内设置的值 -->
            <package name="com.springmvc.example.pojo"/>
        </typeAliases>

        <mappers>
            <!-- Mapper注册：指定Mybatis映射文件的具体位置 -->
            <!-- mapper标签：配置一个具体的Mapper映射文件 -->
            <!-- resource属性：指定Mapper映射文件的实际存储位置，这里需要使用一个以类路径根目录为基准的相对路径 -->
            <!--    对Maven工程的目录结构来说，resources目录下的内容会直接放入类路径，所以这里我们可以以resources目录为基准 -->
            <!-- 该路径是相对于类路径的，而不是相对于当前配置文件路径的 -->
            <mapper resource="com/springmvc/example/mapper/EmployeeMapper.xml"/>
        </mappers>
    </configuration>

~~~

+ 接下来实现代码，详情代码已经在配置类里面实现了

<a id="mybatisConfig"></a>

~~~java

    import com.alibaba.druid.pool.DruidDataSource;
    import org.mybatis.spring.SqlSessionFactoryBean;
    import org.mybatis.spring.mapper.MapperScannerConfigurer;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.*;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.core.io.Resource;

    import javax.sql.DataSource;


    /**
         实现mapper层相关配置类需要做如下配置:
        声明为配置类
        开启组件扫描
        从properties文件内读取jdbc连接所需的相关资源
        提供数据库连接池对象、Mybatis的SqlSessionFactoryBean对象、Mybatis的MapperScannerConfigurer对象
        ...
    */

    @Configuration  // 声明为配置类
    @ComponentScan("com.springmvc.example.mapper")  // 开启组件扫描
    @PropertySource(value = "classpath:jdbc.properties")  // 从properties文件内读取jdbc连接所需的相关资源
    public class MapperConfig {
        private String url;
        private String username;
        private String password;
        private String driver;

        @Bean
        // 提供数据库连接池相关对象，这里提供Druid连接池对象
        public DataSource dataSource(
            // 使用@Value注解把从properties文件内读取到的值赋给相关属性，需要使用${}插值表达式
            // 如果在方法外声明属性，再在方法内使用属性，那么得到的值为空
            // 出现该情况的原因是被@Bean注解作用的方法执行顺序早于被@Value注解作用的属性赋值顺序，因此方法执行时属性还未被注入
            // 可以直接在方法参数列表传入参数，再使用@Value注入，这样就可以得到值了
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password,
            @Value("${jdbc.driver}") String driver

        ){
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driver);
            // 顺便给属性赋个值
            this.url=url;
            this.username=username;
            this.password=password;
            this.driver=driver;
            return dataSource;
        }

        @Bean
        // 提供SqlSessionFactoryBean对象，该对象可以自动帮助我们生成对应的sqlSession对象
        public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
            // 实例化SqlSessionFactoryBean对象
            SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
            // 设置Druid连接池
            sqlSessionFactoryBean.setDataSource(dataSource);
            Resource resource=new ClassPathResource("mybatis-config.xml");
            sqlSessionFactoryBean.setConfigLocation(resource);
            return sqlSessionFactoryBean;
        }

        @Bean
        // 提供MapperScannerConfigurer对象，该对象通过扫描指定的包，可以向我们提供对应mapper接口的代理对象
        public MapperScannerConfigurer mapperScannerConfigurer(){
            MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage("com.springmvc.example.mapper");
            return mapperScannerConfigurer;
        }


    }

~~~

+ 如果采用第二种方式，那么只需要向sqlSessionFactoryBean配置相关信息即可:

~~~java
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        // 实例化SqlSessionFactoryBean对象
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        // 设置Druid连接池
        sqlSessionFactoryBean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        // 开启数据库字段名的自动小驼峰映射
        configuration.setMapUnderscoreToCamelCase(true);
        // 开启resultMap的自动映射
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        // 开启日志功能
        configuration.setLogImpl(StdOutImpl.class);

        // 将设置项配置进sqlSessionFactoryBean内
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 给指定的包内的类起别名，注意需要起别名的是pojo包内的类，不是mapper包内的类
        sqlSessionFactoryBean.setTypeAliasesPackage("com.springmvc.example.pojo");

        // 配置pageHelper插件
        PageInterceptor pageInterceptor=new PageInterceptor();
        Properties properties=new Properties();
        properties.put("helperDialect","mysql");
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.addPlugins(pageInterceptor);

        // 添加对应的xml映射文件
        sqlSessionFactoryBean.addMapperLocations(new ClassPathResource("com/springmvc/example/mapper/EmployeeMapper.xml"));

        return sqlSessionFactoryBean;
    }
~~~

---

#### ④控制类与相关类创建

+ 控制类需要进行请求映射和相关操作:

~~~java

    @RestController
    @RequestMapping("/page")
    public class SSMController {
        @Autowired
         // 自动注入服务层对象，需要使用接口类型声明对象，不然会报错
        private EmployeeService employeeService;

        @GetMapping("/{currentPage}/{pageSize}")
        public Result getAll(@PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize){
            return employeeService.getPage(currentPage, pageSize);
        }
    }

~~~

+ 定义异常处理类

~~~java
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    // 声明类为异常处理类
    @RestControllerAdvice
    public class SSMException {

        @ExceptionHandler(Exception.class)
        public String exceptionHandler(Exception e){
            e.printStackTrace();
            return "something wrong";
        }

    }
~~~

+ 在utils包下生命两个类，一个用于整合最终的数据，便于JSON转换，另一个用来承载页对象:
+ 由于这两个类在各个业务都要用到，因此放到utils包下

~~~java
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Data  // 提供一系列方法
    @NoArgsConstructor  // 提供无参构造
    @AllArgsConstructor  // 提供全参构造
    public class Page {
        private Integer currentPage;
        private Integer pageSize;
        private Integer dataCount;
        private Long totalCount;
        private List list;
    }
~~~

~~~java
    import lombok.Data;

    @Data  // 加上getter和setter方法，在SpringMVC调用JSON转换器转换JSON的过程时要用到
    public class Result {
        private int code=200;
        private boolean falg=true;

        private Object data;

        public static Result ok(Object data){
            Result r=new Result();
            r.data=data;
            return r;
        }
    }
~~~

---

#### ⑤服务层与相关类创建

+ 服务类一般需要写接口，再根据接口写实现类:

~~~java

    import com.springmvc.example.utils.Result;


    public interface EmployeeService {
        Result getPage(int currentPage, int pageSize);
    }

~~~

+ 实现类:

~~~java

    import com.github.pagehelper.PageHelper;
    import com.github.pagehelper.PageInfo;
    import com.springmvc.example.mapper.EmployeeMapper;
    import com.springmvc.example.pojo.Employee;
    import com.springmvc.example.service.EmployeeService;
    import com.springmvc.example.utils.Page;
    import com.springmvc.example.utils.Result;
    import com.springmvc.example.utils.Result;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    @Service
    @Transactional
    public class EmployeeServiceImpl implements EmployeeService {
        @Autowired
        private EmployeeMapper employeeMapper;

        @Override
        public Result getPage(int currentPage, int pageSize) {
            PageHelper.startPage(currentPage,pageSize);
            List<Employee> employees = employeeMapper.selectAll();
            PageInfo<Employee> info=new PageInfo<>(employees);
            Page page=new Page(currentPage,pageSize,info.getSize(),info.getTotal(),info.getList());
            return Result.ok(page);
        }
    }

~~~

---

#### ⑥mapper层相关

+ mapper层写接口就好，然后在对应的xml文件内实现对应的CRUD方法

~~~java

    import com.springmvc.example.pojo.Employee;

    import java.util.List;

    public interface EmployeeMapper {
        List<Employee> selectAll();
    }

~~~

+ 对应的xml文件

~~~xml

    <?xml version="1.0" encoding="UTF-8" ?>

    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.springmvc.example.mapper.EmployeeMapper">
        <select id="selectAll" resultType="employee">
            select first_name,last_name,email from employees
        </select>

    </mapper>

~~~

+ 实体类放在pojo包下:

~~~java

    import lombok.Data;
    import org.apache.ibatis.type.Alias;

    @Data
    public class Employee {
        private String firstName;
        private String lastName;
        private String email;
    }

~~~

---

### （三）跨域问题

+ 在前后端分离项目中，经常会出现跨域问题:
  + 跨域问题的出现，是由于浏览器的同源策略导致的，同源策略是浏览器的一种保护机制:
    + 当浏览器网页主动向某一地址发送请求时，**浏览器会先查看该请求的协议、域名与端口号是否一致，只要有一个不一致，那么就不接收响应数据，完全一致才接收**
    + 不接收响应数据的原因**是浏览器认为网页想要获取与它不同源的数据，这是非法的**，简单来说就是浏览器不允许网页拿取与它非同源的数据，错的是网页，而不是后端
  + 而在前后端分离项目中，端口号肯定是不能一致的，因此一定会出现跨域问题
  + 为了解决跨域问题，SpringMVC提供了@CrossOrigin注解，该注解可以作用于方法和类上，用来解决跨域问题
    + 作用于方法上时，说明该方法支持跨域
    + 作用于类上时，说明类中的全部方法都支持跨域
  + 在配置了相关注解后，由于被请求方主动接受请求方的请求，因此浏览器就不会管了

---

## 部分内容汇总

### （一）注解汇总

|注解|作用|备注|
|:---:|:---:|:---:|
|@EnableWebMvc|1.自动提供RequestMappingHandlerMapping和RequestMappingHandlerAdapter的bean对象<br>2.在接收到JSON类型参数时自动解析JSON，避免出现415错误|无|
|@RequestMapping|指定映射路径与支持的请求类型等|无|
|@ResponseBody|使方法返回值受对应转换器处理并不通过视图解析器解析|无|
|@{Get\|Post\|Put\|Delete\|Patch}Mapping|指定不同请求类型的映射路径|无|
|@RequestParam|接收get参数和请求体参数|无|
|@PathVariable|接收路径参数|无|
|@RequestBody|接收JSON参数|**它无法接收同名的param参数**|
|@Cookie|得到Cookie携带的指定值|无|
|@SessionAttribute|得到session内的指定值|无|
|@RequestHeader|读取请求头内的指定字段的值|无|
|@RestController|@Controller+@ResponseBody|无|
|@ControllerAdvice|声明异常处理类|无|
|@RestControllerAdvice|@ControllerAdvice+@ResponseBody|无|
|@Validated|针对实体类对象进行校验|无|
|@CrossOrigin|解决跨域问题|无|


