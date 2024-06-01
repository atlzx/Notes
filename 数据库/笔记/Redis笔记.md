# Redis笔记

## 一、概述

+ Redis(Remote Dictionary Server)是一款开源的、使用ANSIC语言编写的高性能的键值型数据库，它支持多种丰富的数据类型，且数据存储在内存中。它支持事务、持久化、LUA脚本、发布/订阅、缓存淘汰、流技术等多种功能，提供了主从模式、Redis Sentinel和Redis Cluster集群架构方案
+ Redis一般与MySQL一起使用，**充当缓存的作用**

### （一）与MySQL的比较

+ Redis属于NoSQL数据库，是键值型数据库。MySQL是关系型数据库
+ Redis的数据操作主要在内存，MySQL的数据主要存储在磁盘
+ Redis在计数器、排行榜等方面要明显优于MySQL
+ Redis通常用于一些特定场景，需要与MySQL一起配合使用

---

### （二）功能

+ Redis有许多功能
  + 它可以作为缓存，在MySQL之前响应数据:用于提升性能
  + 内存存储和持久化:​ Redis支持异步将内存中的数据写到硬盘上，同时不影响继续服务
  + 高可用架构搭配:单机、主从、哨兵、集群
  + 预防缓存击穿、穿透、雪崩
  + 支持分布式锁
  + 支持队列
  + 排行榜+点赞功能性能高

![Redis功能概览](../文件/图片/Redis/Redis功能概览.jpg)

---

### （三）优势

+ 在内存中操作数据，性能优秀
+ 支持丰富的数据类型
+ 支持数据的持久化，避免突发事件导致数据丢失
+ 支持数据备份，即使用主从架构

![Redis优势](../文件/图片/Redis/Redis优势.jpg)

---

### （四）版本

+ Redis从2009年诞生，目前的Redis版本是7

![Redis版本](../文件/图片/Redis/Redis版本.jpg)

+ Redis版本的命名规则是:
  + 版本号第二位如果是奇数，则为非稳定版本，如2.7、2.9、3.1
  + **版本号第二位如果是偶数**，则为**稳定版本**，如2.6、2.8、3.2
  + 当前奇数版本就是下一个稳定版本的开发版，如2.9版本就是3.0版本的开发版本
+ 我们可以通过redis.io[官网](https://download.redis.io/releases/)来下载自己感兴趣的版本进行源码阅读

---

## 二、安装与卸载

### （一）安装

+ 这里以CentOS7为例进行安装
+ Redis安装需要依赖如下环境:
  + gcc编译器:我们可以通过`gcc -v`来查看当前gcc的版本，一般CentOS都自带gcc编译器，如果没有，就执行`yum -y install gcc-c++`进行安装
+ 接下来开始安装
  + 使用cd命令进入到想存放redis压缩包的文件夹内
  + 使用`wget https://download.redis.io/releases/redis-7.0.2.tar.gz`命令下载Redis压缩包，想查阅版本有哪些[点击这里](https://download.redis.io/releases/)
  + 使用`tar -zxvf redis压缩包路径 [-C 目标路径]`来解压缩Redis压缩包
  + 进入压缩好的redis目录，执行`make && make install`来进行安装，当出现`It's a good idea to run 'make test'`就说明安装好了，安装时需要确保网络通畅。安装默认放入默认安装目录`/usr/local/bin`目录中，它相当于Windows系统C盘的`C://ProgramFiles`文件夹。到这里我们的Redis就安装好了，但是我们还需要进行一些配置
  + 对应的配置文件在解压完的redis目录中，该文件名叫`redis.conf`，我们一般不会修改这里的配置文件，而是会拷贝一份。使用cp命令把这玩意拷到一个目录里面
  + 接下来使用vim对它进行编辑
    + 默认`daemonize no`改为`daemonize yes`
    + 默认`protected-mode yes`改为`protected-mode no`，将保护模式关闭以让外界可以连接到Redis
    + 默认`bind 127.0.0.1`改为直接注释掉(默认bind 127.0.0.1只能本机访问)或改成本机IP，否则影响远程IP连接
    + 默认requirepass后面加上自己设置的密码，**这个密码就是之后Redis连接要用到的密码**
  + 接下来就可以启动了:
    + `redis-server 我们自己写的配置文件路径`来启动Redis服务。我们可以输入`ps -ef|grep redis|grep -v grep`来查看redis服务的详情
    + `redis-cli -a 密码 -p [端口号]`来连接Redis。端口号可以不写，不写默认是6379。因为Redis默认占用6379端口号
    + 连接后可能会出现一段报错信息:`Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.`。不用管它
    + 接下来输入`set k1 helloWorld`放一个键值对进去，再输入`get k1`它就可以输出`helloWorld`了
    + 输入`ping`,显示`pong`时，代表安装成功
    + 输入`quit`可以退出Redis连接
  + 如果我们想关闭服务器
    + 如果在连接中，直接使用`shutdown`命令，关闭服务
    + 如果在连接之外，使用`redis-cli -a 123456 shutdown`来关闭单实例服务
    + 如果在连接之外，使用`redis-cli -p 6379 shutdown`来指定端口进行关闭

---

### （二）卸载

+ 首先停止`redis-server`服务
  + `redis-cli -a 123456 shutdown`
+ 接下来删除`/usr/local/bin`目录中所有与redis相关的文件
  + `rm -rf /usr/local/bin/redis-*`
+ 这样就删除成功了

---

## 三、数据类型

+ Redis现在共有[十大数据类型](https://redis.io/docs/data-types/)，它们分别是:
  + String
    + 即字符串，**Redis最基本的数据类型。**
    + String类型是二进制安全的，它可以包含任何数据，包括图片或者序列化的对象。
    + 一个redis中字符串value最大为512M
  + List
    + 即**简单的字符串列表，按照插入顺序排序**
    + 我们可以在头部插入，也可以在尾部插入。它的底层实际上是一个双端链表
    + 最多可以包含2^32-1个元素
  + Hash
    + 是string类型的field（字段）和value（值）的映射表，**Hash特别适合用户存储对象**
    + Redis中每个Hash可以存储2^32-1个键值对（40多亿）
  + Set
    + 是**String类型的值组成的无序、不重复的数据集合**。集合对象的编码可以是inset和HashTable
    + Set是通过哈希表实现的，因此添加、删除和查找的时间复杂度都是O(1)
    + 集合中最大的成员数为2^32-1
  + sorted Set(ZSet)
    + **可以排序的Set集合**，它的每个元素都关联着一个double类型的分数，通过元素关联的分数来为元素进行排序
    + ZSet中的元素是不重复的，但Set元素关联的分数可以重复
    + 它也是通过哈希表实现的，因此添加、删除和查找的时间复杂度都是O(1)
  + Geospatial
    + Redis GEO**主要用于存储地理位置信息**，并对存储的信息进行操作
    + 操作包括:添加地理位置的坐标、获取地理位置的坐标、计算两个位置之间的距离
  + HyperLogLog
    + HyperLogLog是用来做基数统计的算法，其优点是**在输入元素的数量或者体积非常非常大时，计算基数所需要的空间总是固定且是很小的**
    + 在Redis里面，每个HyperLogLog键只需要花费12KB内存，就可以计算接近2^64个不同元素的基数
    + 但是，因为HyperLogLog只会根据输入元素来计算基数，而不会存储输入元素本身，所以HyperLogLog不能像集合那样，返回输入的各个元素
  + Bitmap
    + 由0和1状态表现的二进制位的bit数组
  + Bitfield
    + 通过bitfield命令我们可以一次性对多个比特位域进行操作
  + Stream
    + Redis Stream是Redis5.0版本新增加的数据结构，主要用于消息队列
    + Redis Stream提供了消息的持久化和主备复制功能，可以让任何客户端访问任何时刻的数据，并且能记住每一个客户端的访问位置，还能保证消息不丢失。以弥补Redis无法记录历史消息的缺点

---

# 汇总

## 一、常用命令汇总

|分类|命令|作用|备注|
|:---:|:---:|:---:|:---:|
|**基本命令**|`keys *`|显示当前库中全部的key|无|