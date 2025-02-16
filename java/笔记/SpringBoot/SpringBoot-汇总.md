# 配置汇总

## 一、配置项汇总

### （一）配置文件

|分组|配置|作用|值|备注|
|:---:|:---:|:---:|:---:|:---:|
|**常用**|server.port|指定端口|默认8080|无|
|^|spring.application.name|指定项目名称|字符串|不知道有什么用，**它和前端进行请求时需要带的上下文路径是不一样的**|
|**调试**|debug|开启调试模式，终端会打印开启了哪些自动配置|布尔值，默认为false|无|
|**日志**|logging.level.{root\|sql\|web\|类的全类名\|自定义组名}|指定全局/sql组/web组/类/自定义组的日志级别|字符串值|无|
|^|logging.group.自定义组名|将多个类划分为一个组|全类名|无|
|^|logging.file.name|指定日志输出的文件|文件路径|也可以写路径，如果是相对路径，那么是相对于项目所在目录的|
|^|loggging.file.path|指定日志输出的路径|文件路径|优先级没有logging.file.name高|
|^|logging.logback.rollingpolicy.file-name-pattern|指定日志归档的命名格式，默认值是`${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz`|配置规则|从配置项可以看出，只有使用Logback才能使用该配置|
|^|logging.logback.rollingpolicy.clean-history-on-start|应用启动前是否清除以前日志文件|布尔值，默认为false|^|
|^|logging.logback.rollingpolicy.max-file-size|指定每个日志文件的最大大小|数值|^|
|^|logging.logback.rollingpolicy.total-size-cap|指定日志文件总大小超过指定大小后，就删除旧的日志文件|大小，默认为0B|^|
|^|logging.logback.rollingpolicy.max-history|日志文件保存的最大天数|数值，默认7，单位天|^|
|**静态资源**|spring.mvc.static-path-pattern|用来**设置匹配的前端请求静态资源的路径**|字符串值|无|
|^|spring.mvc.webjars-path-pattern|用来**设置匹配的前端请求webjars资源的路径**|字符串值|无|
|^|spring.web.resources.static-locations|配置用来设置后端处理静态资源要寻找的目录，**它会覆盖掉SpringBoot默认配置的四个路径**|字符串值|**针对webjars的路径匹配依然有效，因为根据源码，webjars相关的路径匹配被单独配置了，而该项配置与webjars的路径匹配没有关系**|
|^|spring.web.resources.add-mappings|开启静态资源映射|默认为true|无|
|^|spring.web.resources.cache.period|配置浏览器使用资源的大概时间|数值，单位秒|**如果配置了控制项，该配置会被覆盖**|
|^|spring.web.resources.cache.use-last-modified|配置是否在浏览器找服务器请求资源前，先发送请求确认资源是否发生了更改|布尔值|无|
|^|spring.web.resources.cache.cachecontrol.max-age|配置浏览器使用缓存的最大时间，在此期间，浏览器会使用缓存加载资源|数值，单位秒|无|
|^|spring.web.resources.cache.cachecontrol.cache-public|设置是否共享缓存|布尔值|无|
|**路径匹配**|spring.mvc.pathmatch.matching-strategy|设置路径匹配原则|无|
|^|server.servlet.context-path|设置项目的上下文路径|路径字符串|详见[问题汇总](问题汇总.md)|
|**内容协商**|spring.mvc.contentnegotiation.favor-parameter|设置SpringBoot开启基于路径参数的内容协商|布尔值，默认false|无|
|^|spring.mvc.contentnegotiation.parameter-name|指定通过参数内容协商传递返回类型的参数名|字符串值|无|
|^|spring.mvc.contentnegotiation.media-types.{type}=aaa/bbb|type是我们给这个媒体类型起的名字，这个名字是用来路径传参的时候携带的值，比如`spring.mvc.contentnegotiation.media-types.yaml=text/yaml`,那么路径传参的时候请求参数就是`type=yaml`|媒体类型|无|
|Thymeleaf|spring.thymeleaf.prefix|指定thymeleaf的匹配前缀|默认是`classpath:/templates/`|无|
|^|spring.thymeleaf.suffix|指定thymeleaf的匹配后缀|默认是`.html`|无|
|^|spring.thymeleaf.check-template|在响应前确认对应模板是否存在，不存在会报错|布尔值，默认为true|无|
|^|spring.thymeleaf.check-template-location|在响应前确认模板所在路径是否存在，不存在会报错|布尔值，默认为true|无|
|^|spring.thymeleaf.cache|如果浏览器已经缓存了该模板，那么就让浏览器用缓存|布尔值，默认为true|无|
|**国际化**|spring.messages.basename|指定国际化默认的配置文件路径|路径|不仅要指定路径，还要指定文件的前缀|
|^|spring.messages.encoding|指定国际化默认的配置文件的解码方式|编码格式|无|
|**错误处理**|server.error.path|设定默认的错误视图寻找路径|默认值为`/error`|无|
|^|server.error.include-stacktrace|是否允许报错信息携带异常堆栈信息|always:总是携带<br>on_param:不知道干嘛的<br>never:默认值，从不携带|无|
|^|server.error.include-binding-errors|是否允许携带errors属性|always:总是携带<br>on_param:不知道干嘛的<br>never:默认值，从不携带|无|
|^|server.error.include-exception|是否允许携带异常全类名|true/false，默认为false|无|
|^|server.error.include-message|是否允许携带异常描述|always:总是携带<br>on_param:不知道干嘛的<br>never:默认值，从不携带|无|
|^|spring.mvc.problemdetails.enabled|是否允许返回`application/problem+json`格式的数据|布尔值，默认false|无|
|**数据库**|spring.datasource.url|指定连接的数据库地址|地址值|无|
|^|spring.datasource.username|指定数据库用户名|字符串|无|
|^|spring.datasource.password|指定数据库密码|字符串|无|
|^|spring.datasource.driver-class-name|指定数据库驱动全类名|全类名|无|
|^|spring.datasource.type|指定连接池全类名|全类名|无|
|**Mybatis**|mybatis.configuration.map-underscore-to-camel-case|开启Mybatis驼峰命名映射|布尔值，默认为true(不开启)|无|
|^|mybatis.mapper-locations|指定mapper对应的xml文件路径映射|路径映射，一般为`classpath:mapper/*.xml`|无|
|^|mybatis.type-aliases-package|指定实体类别名的包类路径|路径，一般为`com.example.xxx.pojo`|无|
|^|mybatis.configuration.log-impl|指定Mybatis使用指定的日志门面输出SQL执行语句|可选项很多,一般使用`org.apache.ibatis.logging.stdout.StdOutImpl`|指定时需要指定类的全类名|
|**Mybatis-Plus**|mybatis-plus.mapper-locations|指定mapper接口对应的mapper.xml文件的位置，默认为`classpath:/mapper/**/*.xml`|classpath:xxxx|无|
|^|mybatis-plus.configuration.map-underscore-to-camel-case|是否开启小驼峰命名映射，默认开启|布尔值|无|
|^|mybatis-plus.configuration.log-impl|指定对应日志在SQL执行时进行输出，默认没有配置|日志类全路径|无|
|^|mybatis-plus.type-aliases-package|指定实体类别名的包类路径|路径，一般为`com.example.xxx.pojo`|无|
|**banner**|spring.banner.location|指定读取的banner文件|路径|无|
|^|spring.main.banner-mode|指定banner的显示模式|off:不显示<br>log:使用日志显示<br>console:控制台输出|无|
|**配置隔离**|spring.profiles.active|指定要开启的环境|一个或多个环境名|需要动态切换的环境使用它指定|
|^|spring.profiles.default|指定默认的环境|环境名|默认是default|
|^|spring.profiles.include|指定包含的环境|一个或多个环境名|一般把基础的环境，也就是无论什么情况都用到的环境加入到这里面|
|^|spring.profiles.group.{groupName}|配置环境组,groupName是组的名称|一个或多个环境名|无|
|**文件上传**|spring.servlet.multipart.enabled|开启文件上传功能，默认就是开启的|布尔值|无|
|^|spring.servlet.multipart.max-file-size|限制单文件上传的最大大小|格式:`xxMB`，默认为1M|无|
|^|spring.servlet.multipart.max-request-size|限制单次请求上传的文件总量大小|格式:`xxMB`，默认为10M|无|
|**Redis**|spring.data.redis.host|配置redis所在的服务器ip|ip号|无|
|^|spring.data.redis.port|配置redis所使用的端口号|端口号|无|
|^|spring.data.redis.database|指定Redis要连接的数据库序号|数值|无|
|^|spring.data.redis.password|配置连接redis需要的密码|字符串|无|
|^|spring.data.redis.lettuce.pool.max-active|设置最大连接池最大数量|数值|
|^|spring.data.redis.lettuce.pool.max-wait|连接池阻塞的最大等待时间|`xxxms`，如:`-1ms`|
|^|spring.data.redis.lettuce.pool.max-idle|连接池中的最大空闲连接|数值|
|^|spring.data.redis.lettuce.pool.min-idle|连接池中的最小空闲连接|数值|
|**Redis集群**|spring.data.redis.cluster.nodes|要连接的集群节点|以`<host>:<port>`的方式提供，如果有多个，用逗号隔开，例:`6.53.11.126:6381,6.53.11.126:6382`|无|
|^|spring.data.redis.lettuce.cluster.refresh.period|设置Redis连接的定时刷新频率，该配置项能够通过刷新及时感应到集群中各个节点的状态变化|数值，单位是毫秒|无|
|^|spring.data.redis.cluster.max-redirects|设置最大重定向次数|数值|无|
|^|spring.data.redis.lettuce.cluster.refresh.adaptive|是否开启集群拓扑动态感应刷新|布尔值|无|
|**jackson**|spring.jackson.date-formate|指定自定义的时间格式|[参考](../文件/图片/Java图片/自定义日期格式规范表.png)|无|
|^|spring.jackson.time-zone|指定时区|格式:`GMT+<number>`,number就是时区的具体值|无|
|**SpringCloud-Consul**|spring.cloud.consul.host|指定consul服务所在的host|默认为localhost|无|
|^|spring.cloud.consul.port|指定consul服务所占用的端口|默认为8500|无|
|^|spring.cloud.consul.discovery.service-name|指定对应微服务模块服务发现的名称|一般与spring.application.name保持一致|无|
|**SpringCloud-OpenFeign**|spring.cloud.openfeign.client.config.default.connect-timeout|设置连接的超时时间，单位毫秒|默认为2s|无|
|^|spring.cloud.openfeign.client.config.default.read-timeout|设置连接完成后，等待服务的超时时间，单位毫秒|默认60s|无|
|^|spring.cloud.openfeign.client.config.`<serviceName>`.read-timeout|设置更细粒度的相关配置，serviceName表示对应的服务模块在Consul上注册的注册名|单位毫秒|无|
|^|spring.cloud.openfeign.httpclient.hc5.enabled|启用httpclient5配置，用以替代OpenFeign默认使用的没有连接池、性能和效率比较低的JDK自带的HttpURLConnection|布尔值|无|
|^|spring.cloud.openfeign.compression.request.enabled|开启请求压缩|布尔值|无|
|^|spring.cloud.openfeign.compression.request.mime-types|设置要进行压缩的请求参数类型|mime类型，如果有多个用逗号隔开|无|
|^|spring.cloud.openfeign.compression.request.min-request-size|请求大小超过该值时，进行压缩|数值|无|
|^|spring.cloud.openfeign.compression.response.enabled|开启响应压缩|布尔值|无|
|^|logging.level.<refrence>|设置日志级别（这个是SpringBoot的日志输出的日志级别，不是OpenFeign的日志级别）|只能设置为`debug`|无|

+ 改进版配置项汇总

|分组|配置项|参数|作用|值|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|
|**SpringCloud-Resilience4j**|resilience4j.circuitbreaker.configs.`<configKey>`.failure-rate-threshold|configKey:自定义设置的名称|设置当多少比例（百分比）的请求失败时，进行服务熔断。|数值，如设置为50表示请求失败比例达到50%就进行服务熔断|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.sliding-window-type|^|设置进行熔断判断的模式|`TIME_BASED`:按一定时间进行计算<br>`COUNT_BASED`:按一定数量进行计算|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.slow-call-duration-threshold|^|设置慢调用的评判标准，即一次请求执行超过多长时间算慢调用|例:`5s`|**慢调用是执行慢，但是执行成功了，不能算进请求失败里**<br>`TIME_BASED`模式下才生效|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.slow-call-rate-threshold|^|设置当慢调用占单位时间内请求的多少比例时，进行服务熔断|数值，如设置为50表示慢调用比例达到50%就进行服务熔断|`TIME_BASED`模式下才生效|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.sliding-window-size|^|设置滑动窗口大小，如果是`TIME_BASED`模式，那么该值表示每隔多长时间（单位秒）就进行一次熔断判断，如果是`COUNT_BASED`模式，该值表示距离上一次判断后，请求累计到达多少次时就进行一次熔断判断|数值|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.minimum-number-of-calls|^|设置进行熔断判断的最小样本|数值|一般与上面的sliding-window-size配置保持一致|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.permitted-number-of-calls-in-half-open-state|^|设置断路器转换到半开状态时放行的请求数|数值|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.automatic-transition-from-open-to-half-open-enabled|^|是否启用让断路器在开启后，自动过渡到半开状态。如果设置为false，那么就是在该服务收到调用才尝试过渡|布尔值|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.wait-duration-in-open-state|^|设置断路器到达OPEN状态时间隔多长时间转为HALF_OPEN状态|例:`5s`|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.record-exceptions|^|如果请求执行时出现的异常在该设置下的异常集合内，会被断路器认定为一次请求失败|异常的全类名|无|
|^|resilience4j.circuitbreaker.configs.`<configKey>`.ignore-exceptions|如果请求执行时出现的异常在该设置下的异常集合内，不会被断路器认定为一次请求失败|^|^|
|^|resilience4j.circuitbreaker.instances.`<serviceName>`.base-config|serviceName:服务模块在Consul上面所注册的名字|指定该服务模块所遵循的Resilience设置集|即上面的`configKey`|无|
|^|resilience4j.thread-pool-bulkhead.configs.`<configKey>`.core-thread-pool-size|configKey:自定义设置的名称|设置线程池的核心线程的数量|数值|无|
|^|resilience4j.thread-pool-bulkhead.configs.`<configKey>`.max-thread-pool-size|^|设置线程池最大的线程数量|数值|无|
|^|resilience4j.thread-pool-bulkhead.configs.`<configKey>`.queue-capacity|^|设置线程满了以后，承载后续请求的队列容量|数值|无|
|^|resilience4j.bulkhead.configs.`<configKey>`.max-concurrent-calls|^|设置该服务模块允许并发执行的最大数量|数值|无|
|^|resilience4j.bulkhead.configs.`<configKey>`.max-wait-duration|并发数达到上限时，再有请求来，若达到该值，那么自动进行服务降级处理|例:`2s`|无|
|^|resilience4j.thread-pool-bulkhead.instances.`<serviceName>`.base-config|serviceName:服务模块在Consul上面所注册的名字|指定该服务模块所遵循的Resilience设置集|即上面的`configKey`|无|
|^|resilience4j.ratelimiter.configs.`<configKey>`.limit-for-period|configKey:自定义设置的名称|在一次刷新周期内，允许执行的最大请求数|数值|无|
|^|resilience4j.ratelimiter.configs.`<configKey>`.limit-refresh-period|^|限流器每隔limitRefreshPeriod刷新一次，将允许处理的最大请求数量重置为limitForPeriod|例:`5s`|无|
|^|resilience4j.ratelimiter.configs.`<configKey>`.timeout-duration|^|线程等待其执行权限的最大时间，若超过该时间，执行服务降级|例:`5s`|无|
|**RabbitMQ**|spring.rabbitmq.host|无参|配置RabbitMQ服务所在IP|ip地址|无|
|^|spring.rabbitmq.port|无参|配置RabbitMQ服务所在端口|端口号|无|
|^|spring.rabbitmq.username|无参|连接RabbitMQ服务所需用户名|用户名|无|
|^|spring.rabbitmq.password|无参|连接RabbitMQ服务所需密码|密码|无|
|^|spring.rabbitmq.virtual-host|无参|要连接的RabbitMQ的Virtual-Host名|virtual-host名|无|
|^|spring.rabbitmq.listener.simple.acknowledge-mode|无参|设置消费者处理消息的确认模式，默认是无论是否消费成功都返回ack，设置为`manual`表示消费者需要编码手动确认ack|参考`org.springframework.amqp.core.AcknowledgeMode`枚举类|无|
|^|spring.rabbitmq.listener.simple.prefetch|无参|设置消费者一次最多可以拿多少个消息进行消费|数值|无|
|^|spring.rabbitmq.publisher-confirm-type|无参|设置交换机的消息确认模式|参考`org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType`枚举类|无|
|^|spring.rabbitmq.publisher-returns|无参|是否启用消息转发结果确认，即确认交换机转发的请求是否到达了消息队列|布尔值，true表示启用|无|

---

## 二、注解汇总

|分组|注解|作用|主要作用范围|备注|
|:---:|:---:|:---:|:---:|:---:|
|**项目启动**|@SpringBootApplication|声明对应类为配置类并自动配置|类|无|
|**组件注册**|@Configuration|声明对应类为配置类|类|无|
|^|@SpringBootConfiguration|声明对应类为SpringBoot项目的配置类|类|其实跟上面的注解没有区别|
|^|@ServletComponentScan|配置扫描的包，使配置的包路径下的被@WebServlet、@WebFilter注解作用的类在IOC容器初始化时生成一个实例并加入IOC容器对象|类|无|
|^|@Service|^|作用于Service层|无|
|^|@Controller|^|作用于Controller层|无|
|^|@Repository|^|作用在Dao层|无|
|^|@Bean|使方法返回值作为bean加入到IOC容器内|方法|无|
|^|@Order|指定bean初始化或AOP执行的优先级（顺序）|类|值越小，加载顺序越靠前|
|^|@Scope|声明该类型的bean是单实例还是多实例|方法|无|
|^|@Controller/@Service/@Repository/@Conponent|声明对应类属于控制层/服务层/DAO层/其它层，并将其纳入IOC容器管理|类|无|
|^|@Import|指定对应类受IOC容器管理|类|一般用于将第三方包下的类纳入IOC容器管理|
|^|@ComponentScan|开启组件扫描|类|作用于配置类上|
|^|@Lazy|指定组件懒加载|配置类中的bean方法|^|
|^|@DependOn|指定依赖加载对象|无|^|
|^|@PreDestroy|指定销毁方法|^|该注解来源于`jakarta`包|
|^|@PostConstruct|指定初始化方法|^|^|
|^|@Value|注入配置文件值或能够通过解析字符串解析出来的值|类属性、方法属性|它的注入方式有多种:1.如果直接写@Value("aaa")，那么注入的值是字符串aaa<br>2.如果写@Value("${xxx.yyy.zzz}")那么会把配置文件中对应的配置项的值注入进去|
|^|@Autowared|根据byType模式匹配对应的引用数据类型对象并注入|^|1.该注解无法自动装配JDK自带的数据类型<br>**不能作用于测试类**|
|^|@Qualifier|使自动装配按照byName的方式匹配，且依据的是该注解内指定的name值进行匹配|^|无|
|^|@Resource|依据 指定的name -> byName -> byType的模式依次匹配对应的引用数据类型对象并注入|^|1.该注解来源于`jakarta`包<br>2.**不能作用于测试类**|
|**请求处理**|@RequestMapping|指定映射路径与支持的请求类型等|类、方法|无|
|^||@{Get\|Post\|Put\|Delete\|Patch}Mapping|指定不同请求类型的映射路径|^|无|
|^|@ControllerAdvice|声明异常处理类|类|无|
|^|@RestControllerAdvice|@ControllerAdvice+@ResponseBody|^|无|
|^|@CrossOrigin|解决跨域问题|类与方法|无|
|^|@RequestParam|接收get参数和请求体参数|方法属性|无|
|^|@PathVariable|接收路径参数|^|无|
|^|@RequestBody|接收请求体参数，并将其中的属性映射为方法中接收前端请求参数的属性对应的引用数据类型对象|^|1.**它无法接收同名的param参数**<br>2.它无法实现对MultipartFile的注入|
|^|@Cookie|得到Cookie携带的指定值|^|无|
|^|@SessionAttribute|得到session内的指定值|^|无|
|^|@RequestHeader|读取请求头内的指定字段的值|^|无|
|^|@Validated|针对实体类对象进行校验|^|无|
|^|@ResponseBody|使方法返回值受对应转换器处理并不通过视图解析器解析|方法|无|
|**异常处理**|@ControllerAdvice|声明异常处理类|类|无|
|^|@RestControllerAdvice|@ControllerAdvice+@ResponseBody|类|无|
|**条件注解**|@ConditionalOnClass|若类路径下存在该类，那么触发指定行为|类/方法|触发指定行为需要利用其他注解实现，如加入IOC容器需要@Bean注解|
|^|@ConditionalOnMissingClass|若类路径下不存在该类，那么触发指定行为|^|^|
|^|@ConditionalOnBean|若IOC容器内存在指定的bean,那么触发指定行为|^|^|
|^|@ConditionalOnMissingBean|如果容器中不存在这个Bean（组件）,那么触发指定行为|^|^|
|**属性绑定**|@ConfigurationProperties|声明组件的属性和配置文件内key的前缀以使得配置文件中对应的值向对应属性进行注入|类|该注解生效必须**使作用类被@Component及相关注解作用或被配置类的@EnableConfigurationProperties指定**，且**对应的实体类需要有getter和setter方法**<br>该注解生效的时机貌似是bean创建时检查|
|^|@NestedConfigurationProperty|如果该属性被此注解作用，那么SpringBoot会让其所属类的前缀继承该属性所在类的前缀|属性|例:我想写一个配置`test1.test2.test3`让它注入到Test1类的Test2类型的属性test2的属性test3，那么就需要在Test1类上加上注解@ConfigurationProperties，并在属性test2上加上@NestedConfigurationProperty|
|^|@EnableConfigurationProperties|指定某些类是属性绑定类|类|应作用于配置类|
|^|@PropertySource|读取外部指定路径的properties文件内容|类|无|
|**Jackson**|@JacksonXmlRootElement|声明对应类可被转换为xml格式|类|无|
|**日志**|@Slf4j|被该注解作用的类中的方法内，都默认可以得到一个实现了SLF4J日志门面的日志对象|类|该注解来自于Lombok|
|**Mybatis**|@MapperScan|指定mapper接口所在的包，用于创建mapper的代理对象|类|无|
|^|@Param|指定mapper最终能看到的参数名称|方法参数|无|
|^|@Alias|指定类在mapper文件中的别名|数据库对应实体类|无|
|**事务**|@EnableTransactionManagement|让SpringMVC支持声明式事务管理（即支持不通过xml的方式使@Transactional注解生效）|配置类|SpringBoot通过自动装配已经自动实现了其功能|
|^|@Transactional|在方法执行时开启事务，方法执行出现异常时会自动回滚|类或方法，作用在类上表示该类下的所有方法都统一开启事务|无|
|**配置隔离**|@Profile|在开启指定环境后，类或方法上的注解才生效|类或方法|无|
|**junit**|@SpringBootTest|执行测试时会启动SpringBoot项目进行测试|类|无|
|^|其它相关Junit注解详见[其它依赖笔记](其它依赖笔记.md)|
|**自定义starter**|@ConfigurationProperties|设置Properties配置类的一些常见配置，如对应的配置文件前缀|类|无|
|**Swagger**|@Tag|声明Controller类的作用，与配置类内的配置相对应|类|无|
|^|@Operation|描述方法作用|方法|无|
|^|@Schema|描述实体类作用，以及它各个属性的作用|类、属性|name属性会修改swagger初始发送的json的key,用title才是描述作用|
|^|@Parameter|描述参数作用|方法|无|
|^|@Parameters|描述参数作用|方法|无|
|^|@ApiResponse|描述响应状态码等|方法|无|

+ 改进版汇总表

|分组|注解|作用|参数|参数作用|参数值|参数备注|注解主要作用范围|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**Jackson**|@JsonIgnoreProperties|指定类序列化时忽略的序列化的字段，或者反序列化时忽略JSON串中存在而Java实体类中不存在而导致的报错|ignoreUnknown|是否在反序列化时忽略JSON串中存在而Java实体类中不存在而导致的报错|true/false|无|属性、类|无|
|^|@JsonProperty|指定序列化和反序列化时，实体类的指定属性对应的JSON串的key名称，这样可以实现属性和JSON串中的key名称不同时也可以映射|value|JSON串中key的实际名称|字符串|无|属性、方法、参数|无|
|^|@JsonNaming|指定序列化和反序列化时对属性采取的命名策略|value|指定序列化和反序列化时对属性采取的命名策略|`Class<? extends PropertyNamingStrategy>`对象|**由于jackson默认采取的命名策略是小驼峰命名法映射，此映射策略在处理一些属性时可能会导致错误的映射从而导致@RequestBody在映射时明明传了正确的值但是却在对应属性映射了null(如映射`pCategotyId`，2.13.5版本的jackson就会将其映射为`pcategoryId`)，因此可能需要手动指定映射策略**<br>在`PropertyNamingStrategies`接口中可以拿到当前jackson提供的所有命名策略|类|无|
|**时间格式处理**|@JsonFormat|同时指定前端传过来的时间格式以及后端返回给前端的时间格式|pattern|自定义格式|字符串|无|属性|无|
|^|^|^|timezone|指定时间的时区|格式:`GMT+<number>`|无|属性|无|
|^|@DateTimeFormat|将前端传来的字符串格式时间转为Date相关类型时间|pattern|指定前端需传来的时间格式|字符串|**前端传来的格式必须与该格式保持一致，否则会报错**|属性、方法参数|无|
|**定时任务**|@EnableScheduling|开启Spring Task的定时任务支持|>|>|>|无|类|无|
|^|@Scheduled|给对应方法设置定时任务|cron|使用cron表达式定义定时频率|字符串|无|方法|无|
|^|^|^|fixedRate|距离该方法上一次调用指定毫秒数后，下一次调用执行|数值|一旦到达相隔时间，无论上个方法是否执行完毕下个方法都会执行，因此可能会产生重复调用的问题|^|^|
|^|^|^|fixedDelay|在上一次方法执行完毕后，等待指定时间再调用下一次该方法|数值|无|^|^|
|**SpringCloud-Consul**|@EnableDiscoveryClient|开启微服务模块的服务发现|>|>|无|无|类|无|
|**SpringCloud-LoadBalancer**|@LoadBalanced|使RestTemplate对象支持负载均衡|>|>|无|无|方法、参数、属性|无|
|**SpringCloud-OpenFeign**|@FeignClient|声明接口为OpenFeign接口API|value|指定该接口API对应的微服务模块在Consul上面的注册名，从而指向该模块|注册名|无|类|无|
|^|^|^|path|指定请求的共同前缀|字符串|无|^|^|
|^|^|^|url|直接指定请求URL前缀，需要把`http://`这样的前缀也带上|字符串|无|^|^|
|^|@EnableFeignClients|开启OpenFeign功能|>|>|无|无|类|无|
|**SpringCloud-Resilience4j**|@CircuitBreaker|使对应方法被断路器监听，并在出现问题时可以触发服务熔断和服务降级|name|配置断路器要监听的服务模块的调用，即该值对应的服务模块的调用行为会被断路器监听，并在出现问题时执行服务熔断和服务降级|对应服务模块在Consul上面的注册名|无|方法|无|
|^|^|^|fallback|指定服务降级要调用的fallback方法|fallback方法的名称（字符串）|无|^|^|
|^|@Bulkhead|使对应方法能够经过舱壁隔离处理|name|该值对应的服务模块的调用行为会被断路器监听，并在出现问题时执行服务熔断和服务降级|对应服务模块在Consul上面的注册名|无|方法|无|
|^|^|^|fallback|指定服务降级要调用的fallback方法|fallback方法的名称（字符串）|无|^|^|
|^|^|^|type|舱壁隔离的方式|信号量(`Bulkhead.Type.SEMAPHORE`)和线程池(`Bulkhead.Type.THREADPOOL`)|无|^|^|
|^|@RateLimiter|使对应方法能够经过限流处理|name|该值对应的服务模块的调用行为会被断路器监听，并在出现问题时执行服务熔断和服务降级|对应服务模块在Consul上面的注册名|无|方法|无|
|^|^|^|fallback|指定服务降级要调用的fallback方法|fallback方法的名称（字符串）|无|^|^|
|**RabbitMQ**|@RabbitListener|使指定方法能够消费消息队列中的消息|bindings|指定要监听的队列|@QueueBinding注解组成的数组对象|**该属性可以详细指定要监听的队列，如果队列、交换机等不存在，那么会自动创建**|方法|无|
|^|^|^|queues|指定要监听的队列名|队列名组成的字符串数组|该属性仅用于指定要监听的队列，没有自动创建的效果|^|^|
|^|@QueueBinding|指定要监听的队列的详细信息|value|指定队列信息|@Queue注解对象|无|作为@RabbitListener注解的bindings属性存在|无|
|^|^|^|exchange|指定能向队列转发消息的交换机信息|@Exchange注解对象|无|^|^|
|^|^|^|key|指定路由键|字符串|无|^|^|
|^|@Queue|指定队列信息|value|指定队列名称|字符串|无|作为@QueueBinding的value属性存在|无|
|^|^|^|duration|是否将数据持久化处理|**字符串类型的布尔值**|无|^|^|
|^|@Exchange|value|指定交换机名称|字符串|无|作为@QueueBinding的exchange属性存在|无|
|^|^|arguments|指定交换机参数|@Argument注解组成的数组对象|无|^|^|
|^|@Argument|name|参数名|一般为`alternate-exchange`，用来指定交换机的备用交换机|无|作为@QueueBinding、@Exchange、@Queue的arguments属性存在|无|
|^|^|value|参数值|默认为字符串|无|^|^|
|^|^|type|参数值的类型|默认为`java.lang.String`|无|^|^|

+ [组件注册注解样例](../../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)
+ [条件注解样例](../../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)
+ [实体类属性绑定样例](../../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/pojo/Person.java)
+ [配置类属性绑定样例](../../源码/SpringBoot/SpringBootInitializrDemo/src/main/java/com/springboot/example/springbootinitializrdemo/config/MyConfig.java)

---

## 三、参数汇总

---

## 四、路径汇总

+ 项目的路径配置很乱，因此在此处汇总所有路径相关的知识点

### （一）类路径classpath

+ 类路径，即classPath下的路径，不同的类加载器的classpath都不相同
  + 与**项目最相关的是ApplicationClassLoader，它负责加载依赖jar包和我们自己编写的项目类**

|ClassLoader|作用|获取方式|classpath路径|备注|
|:---:|:---:|:---:|:---:|:---:|
|BootStrapClassLoader|加载Java原生自带的类|由于是C++编写的，因此无法获取，即使获取也是拿到null|`JAVA_HOME/jre/lib/rt.jar`或`sun.boot.class.path`路径|无|
|ExtensionClassLoader|从`java.ext.dirs`系统属性所指定的目录中加载类库，或从JDK的安装目录的`jre/lib/ext`子目录下加载类库，如果用户创建的`JAR`放在此目录下，也会自动由扩展类加载器加载。|不知道|见作用|无|
|**ApplicationClassLoader**|加载我们项目直接编写的类，以及直接依赖的jar包|1.任何一个项目类的Class对象调用`getClassLoader`方法都是该类加载器<br>2.`Thread.currentThread().getContextClassLoader()`<br>3.`ClassLoader.getSystemClassLoader()`|SpringBoot项目内，从`resource`或`main`包开始，就是`classpath`的起始位置|

+ 在java中，如果**想获取一个项目类或依赖类**，比如想获得全类名为`org.springframework.boot.autoconfigure.SpringBootApplication`的类，那么可以通过如下手段获取:
  + **在javaApi层面，所有用来得到资源的路径参数都需要以`/`(不是File.separator)。因为按`.`分割的是包，而资源是文件系统的东西，统一按`/`分隔**
  + 另外，使用原生的JavaApi获取资源不需要区分`classpath`和`classpath*`

~~~java
    // 无论何种操作系统，分隔符必须为 '/'，因此在生成路径时，不能用File.separator代替
    URL resource = ClassLoader.getSystemResource("org/springframework/boot/autoconfigure/SpringBootApplication.class");
~~~

---

### （二）路径匹配策略

+ 路径匹配策略的格式一般为`[前缀] xxx{.|/}yyy{.|/}zzz`，可以看到它由三部分组成:
  + **路径前缀**:常用的可选项有:`classpath:`、`classpath*:`、`file:`、`http:`
  + **路径**:遍历到的目录的名称，可以搭配一些匹配策略语法
  + **分隔符**:分为两种，文件路径分隔符(**无论是什么操作系统，都必须是`/`**)和包分隔符(`.`)
+ `classpath`与`classpath*`前缀
  + classpath:即当前项目，它只会匹配当前项目的，而不会匹配项目之外的东西（比如依赖）
  + classpath*:不仅匹配当前项目，还会匹配项目之外的东西
+ 路径语法
  + 以下是Ant风格路径匹配策略，也是目前主流的匹配语法
  + Spring5.3时，添加了新的PathPatternParser的路径匹配策略，其效率更高。可以通过`spring.mvc.pathmatch.matching-strategy`配置手动切换路径匹配策略

|分类|通配符|作用|备注|例|
|:---:|:---:|:---:|:---:|:---:|
|按`/`相隔|?|匹配任意一个字符|无|`/pages/t?st.html` 匹配 `/pages/test.html`|
|^|*|匹配一层路径的零个或多个字符|无|`/*/test.html` 匹配 `/pages/test.html`|
|^|**|匹配零层或多层路径|**如果使用PathPatternParser，它只能写在路径最后，AntPathMatcher则无此限制**|`/pages/**`匹配`/pages/test/page.html`|
|^|{name}|取出对应路径的字段值|无|`/{page}/test.html`匹配`/pages/test.html`，读取到的值为name=pages|
|^|{name:[a-z]}|取出对应路径满足后面的正则表达式的值|`/{page:[a-z]}/test.html`匹配`/pages/test.html`，但不匹配`/pages1/test.html`|
|^|{*path}|从当前路径开始截取，直到最后|**需要写在路径最后**|`/resources/{*file}`匹配`/resources/images/file.png`，读取到的值为file=/images/file.png|
|^|[]|匹配对应的字符集合|无|无|
|按`.`相隔|*|匹配一层路径或匹配任意字符|`com.example.*.Test` 匹配 `com.example.aa.Test`<br>`com.example.aa.*st` 匹配 `com.example.aa.Test`|无|
|^|**|匹配多层路径|无|无|

+ **前缀使用场景**:
  + PathMatchingResourcePatternResolver类解析时
  + 配置静态资源映射时
+ **分隔符应用场景**
  + 使用`/`分割:
    + @RequestMapping
    + 配置拦截器路径
    + Mybatis和Mybatis-plus对于xml文件所在路径的配置
    + 使用框架或者原生的JavaApi去具体获取对应资源时
  + 使用`.`分割
    + 使用@MapperScan注解指定路径
    + 使用@ComponentScan注解指定路径



---

## 五、通配符

### （一）时间格式

![日期时间格式自定义规范表](../../文件/图片/Java图片/自定义日期格式规范表.png)

---