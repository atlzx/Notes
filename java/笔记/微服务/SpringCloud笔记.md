# SpringCloud笔记

## 一、概述

+ SpringCloud[官方文档](https://spring.io/projects/spring-cloud)
+ SpringCloudAlibaba[官方文档](https://sca.aliyun.com/)
+ **想使用SpringCloud,必须严格遵照官网提供的版本适配来搭配对应的依赖，否则可能会出现各种报错问题**

---

## 二、使用

### （一）consul

#### ①概述

+ 为什么使用consul:
  + 我们的微服务模块，如果想将请求转发给其它微服务模块，需要通过硬编码的方式记录下对应的微服务模块的模块服务host、端口和请求信息，如果对应的请求微服务模块的服务端口、host发生了变化，也需要同步修改硬编码。而随着模块数量的提升，硬编码将变得难以维护。
  + 如果系统中存在多个相同的微服务，无法实现微服务的负载均衡功能
+ Eureka:
  + Eureka已经停止更新
  + Eureka的自我保护机制，对初学者不友好
  + Eureka需要作为微服务的一个模块嵌入到微服务中去，但是这个玩意不是业务模块，我们希望该服务中心单独隔离出来，与微服务功能进行解耦
  + Nacos、consul等开源cloud组件的的崛起
+ consul能做什么
  + 服务发现:提供Http和DNS两种发现方式
  + 健康检测:支持多种方式，HTTP、TCP、Docker、Shell脚本定制化监控
  + KV存储:键值对的存储方式
  + 多数据中心:Consul支持多数据中心
  + 可视化Web界面

---

#### ②安装

+ [下载链接](https://developer.hashicorp.com/consul/install?product_intent=consul)
+ Windows它提供两种架构的下载，一种386(就是32位)，另一种是AMD64(一般如果电脑是64位的，下这个)
  + 不确定的通过`wmic cpu get AddressWidth`查看当前CPU处理位数
+ 下载好之后对压缩包进行解压，解压完以后就一个`consul.exe`文件，在该文件所在目录打开终端，输入`.\consul.exe --version`来查看版本情况。通过该步骤也可以判别下载的版本是否符合电脑CPU处理位数的版本
+ 如果安装成功，推荐把它加到环境变量里，即直接把它所在的目录路径加到环境变量的path中去
+ 输入`consul agent -dev`来以开发模式进行启动，启动后在[8500端口下](http://localhost:8500)可以看到如下界面，说明安装成功:

![consul服务界面](../../文件/图片/SpringCloud图片/consul服务界面.png)

---

#### ③服务注册与发现

+ 我们需要把我们的模块注册进consul，因此需要先引入依赖:

~~~xml
    <!-- Spring-Cloud-Consul依赖 -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>   
~~~

+ 接下来进行配置文件的配置:

~~~yaml
    spring:
        application:
            name: cloud-pay-service
        cloud:
            consul:
                # 默认端口就是localhost
                host: localhost  
                # port端口默认是8500
                port: 8500
                # 我们想访问要进行请求的服务名称
                discovery:
                    service-name: ${spring.application.name}
~~~

+ 接下来在项目启动类上或者配置类上加上@EnableDiscoveryClient来开启微服务模块的服务发现
+ 同时在Controller可以改变一下硬编码，由之前的写host、port和请求路径改为写对应微服务模块的服务名称，如`http://cloud-pay-service`
+ 然后启动模块，就能看到consul服务页已经加入了该微服务模块了，刚开始可能是红的，过一会就会变绿

![模块注册进consul](../../文件/图片/SpringCloud图片/模块注册进consul.png)

+ 尝试发送一个请求，如果此时使用了restTemplate进行请求转发，把它交给别的微服务模块处理，可能会导致问题
  + `java.net.UnknownHostException: xxx`
  + 这是因为consul默认开启负载均衡，但是我们的restTemplate却默认不支持负载均衡，因此我们需要在配置类中提供restTemplate的bean方法上添加@LoadBalanced让该restTemplate对象支持负载均衡，就没事了
