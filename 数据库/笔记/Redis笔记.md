# Redis基础笔记

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
    + 默认`requirepass`后面加上自己设置的密码，**这个密码就是之后Redis连接要用到的密码**
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

### （一）String类型

+ [官网文档](https://redis.io/docs/data-types/strings/)
+ 字符串是最Redis基本的数据类型，它可以包含任何数据，包括图片或者序列化的对象
+ 一个redis中字符串value最大为512M

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`set key value [NX\|XX] [GET] [EX 秒值\|PX 毫秒值\|EXAT unix时间戳值(秒)\|PXAT unix时间戳值(毫秒)\|KEEPTTL]`|NX:对应键不存在时，创建一个键值对<br>XX:对应键存在时，创建一个键值对<br>GET:返回指定键原本的value值<br>EX:以秒为单位设置过期时间<br>PX:以毫秒为单位设置过期时间<br>EXAT:以以秒为单位的Unix时间戳设置过期时间<br>PXAT:以以毫秒为单位的Unix时间戳设置过期时间<br>KEEPTTL:保留之前的键值对设置的过期时间|修改或添加一个键值对|如果上一个键值对有过期时间，而本次未指定过期时间，那么修改后的键值对过期时间将被覆盖|
|`setnx <key> <value>`|key:键<br>value:值|如果对应的键不存在，再设置键值对|无|
|`get key`|无参|得到对应的键所对应的值|无|
|`mset key1 value1 [key2 value2 ....]`|无参|批量设置键值对|无|
|`mget key1 [key2...]`|无参|批量获取键对应的值|无|
|`mesetnx key1 value1 [key2 value2 ....]`|无参|批量设置键值对，且这些键值对必须在之前都不存在|只要这些键有一个存在，那么就都不会执行|
|`getrange key start end`|start:开始下标<br>end:结束下标|截取字符串，**截取的区间是[start,end]**|可以传入负数|
|`setrange key offset value`|offset:要进行覆盖的开始下标<br>value:替换的新值|将key对应的value从开始下标起替换为新值，即替换部分值|无|
|`incr key`|无参|自增+1|需要字符串是纯整数|
|`incrby key number`|number:增加的值|自增对应的number值|^|
|`decr key`|无参|自减-1|^|
|`decrby key number`|number:减少的值|自减对应的number值|^|
|`strlen key`|无参|获得字符串长度|无|
|`append key value`|value:值|字符串的拼接|无|
|`getset key value`|value:值|先get再set|无|

---

### （二）List

+ 简单的字符串列表，按照插入顺序排序，我们**可以在头部插入，也可以在尾部插入**。它的底层实际上是一个双端链表。因此我们通过索引下标操作中间的节点性能会较差
+ 最多可以包含2^32-1个元素

![list结构](../文件/图片/Redis/list结构.jpg)

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`lpush list value1 [value2....]`|无参|在头部连续插入对应的值|无|
|`rpush list value1 [value2 ....]`|无参|在尾部连续插入对应的值|无|
|`lrange list start end`|start:开始下标<br>end:结束下标|列表的切片，**截取的区间是[start,end]**|可以传入负数|
|`lpop list`|无参|删除指定列表的头部元素并返回|无|
|`rpop list`|无参|删除指定列表的尾部元素并返回|无|
|`lindex list index`|index:指定的索引|按照索引获取元素值（从头到尾）|无|
|`llen list`|无参|得到列表的元素数量|无|
|`lrem key count element`|count:指定要删除的数量<br>element:要删除的元素|从头到尾删除对应数量的element|无|
|`ltrim key start stop`|start:开始下标<br>end:结束下标|仅使列表保留切片区间内的值,**截取的区间是[start,end]**|可以传入负数|
|`rpoplpush list1 list2`|list1:源列表<br>list2:目的列表|移除源列表的最后一个元素，并将该元素添加到目的列表并返回|无|
|`lset key index value`|index:下标<br>value:新值|为指定下标的元素赋值|无|
|`linsert key {before\|after} pivot element`|pivot:指定的标志元素<br>element:要插入的值|在指定的标志元素前面或后面插入新值|无|

---

### （三）Hash

+ 省流:`Map<String,Map<Object,Object>>`

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`hset key field1 value1 [field2 value2]`|fieldn valuen:键值对|向key对应的Map中添加值|无|
|`hget key field`|field:Map中的键|得到key对应的Map中的键对应的值|无|
|`hmset key field1 value1 [field2 value2]`|fieldn valuen:键值对|向key对应的Map中添加值|当前版本的hmset功能已被hset取代，该命令已弃用|
|`hmget key field1 [field2]`|field:Map中的键|得到key对应的Map中的多个键对应的值|无|
|`hgetall key`|无参|得到key对应的Map中的全部键值对|无|
|`hdel key field1 [field2]`|field:Map中的键|通过传入键的方式批量删除key对应的Map中的键值对|无|
|`hlen key`|无参|获取对应key对应的Map的键值对数量|无|
|`hexists key field`|field:Map中的键|判断指定的键是否存在|无|
|`hkeys key`|无参|获取Map中全部的键|无|
|`kvals key`|无参|获取Map中全部的值|无|
|`hincrby key field increment`|field:Map中的键<br>increment:自增值，需要是**整数**|给Map中的键对应的值自增指定的值|无|
|`hincrbyfloat key field increment`|field:Map中的键<br>increment:自增值，需要是**浮点数**|^|无|
|`hsetnx key field value`|field value:键值对|如果Map中不存在，就进行赋值|无|

---

### （四）Set

+ 不重复的无序集合
+ Set是通过哈希表实现的，因此添加、删除和查找的时间复杂度都是O(1)

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`sadd key value1 [value2...]`|无参|向set中添加value|无|
|`smembers key`|无参|遍历输出对应set的值|无|
|`sismember key value`|value:要检验的值|检查对应的值是否在member|无|
|`srem key value1 [value2]`|value:要删除的元素|批量删除set中的元素|无|
|`scard key`|无参|统计set中的元素个数|无|
|`srandmember key number`|number:指定要取出的元素|从set中随机取出指定数量的元素|不会使set中的元素被删除|
|`spop key number`|number:指定要删除的元素|从set中随机删除指定数量的元素并返回|无|
|`smove key1 key2 value`|key1:源set<br>key2:目的set<br>value:源set的值|将key1中的对应值取出并添加进key2中|无|
|`sdiff key1 [key2 ...]`|无参|计算多个set的差集，从左到右进行运算|无|
|`sunion key1 [key2 ...]`|无参|计算集合的并集，从左到右进行运算|无|
|`sinter key1 [key2 ...]`|无参|计算集合的交集，从左到右进行运算|无|
|`SINTERCARD [numkeys] key1 [key2...] [limit count]`|numkeys:由输入的key个数决定<br>count:限制交集的最大元素数量，若取交集时元素数量到达该阈值，那么直接退出并返回|返回各set集交集的结果内包含的元素数量|该命令是7.0新增命令|

---

### （五）ZSet

+ sorted set(ZSet)在set的基础上，使每个set元素关联一个分数，使set集合依据该分数进行排序。这个分数不是数学上的分子分母的分数，是score那个意思的分数

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`zadd key score1 value1 [score2 value2]`|scoren valuen:与分数相关联的元素值|向ZSet中添加元素|无|
|`zrange key min max [withscores]`|min:最小索引<br>max:最大索引|输出指定区间内的元素，**截取的区间是[min,max]**。如果写了withscores，那么连着分数一起输出|无|
|`zrevrange key min max [withscores]`|^|倒序输出指定区间内的元素，**截取的区间是[min,max]**。如果写了withscores，那么连着分数一起输出|无|
|`zrangebyscore key min max [withscores] [limit offset count]`|min:最小索引<br>max:最大索引<br>offset:截取偏移量<br>count:截取的最大数量|获取指定分数区间内（[min,max]）的元素，如果想有一方开区间，在前面加一个`(`即可，如果有withscores那么连着分数一起输出；如果有limit那么还要进行切片|无|
|`ZREMRANGEBYRANK <key> <start> <stop>`|key:zset的键<br>start:开始下标<br>stop:结束下标|删除按照分数从大到小排序的zset区间为`[start,stop]`之内的元素|无|
|`zscore key value`|value:值|获得对应值关联的分数|无|
|`zcard key`|无参|获取集合中元素的数量|无|
|`zrem key value1 [value2]`|valuen:要删除的值|批量删除对应的元素|无|
|`zincrby key count value`|count:自增的值<br>value:要自增的值|给指定的zset中的元素自增值顶的值|无|
|`ZCOUNT key min max`|min:最小分数值<br>max:最大分数值|获得指定**分数区间**内的元素个数|无|
|`zmpop numkeys key1 [key2....] {min\|max} [count number]`|numkeys:该值取决于后面key的个数<br>number:指定删除并输出的元素数量|**从左到右开始寻找，找到第一个非空的zset集合**，并依据min和max来决定删除该集合并输出的元素，依据number来决定删除的元素数量。**如果第一个非空集合都删空了也没有达到number的指标，那么直接退出**|该命令是7.0新增命令<br>|
|`zrank key value [withscore]`|无参|获得value的顺序下标值|无|
|`zrevrank key value [withscore]`|无参|获得value的逆序下标值|无|

---

### （六）Bitmap

+ Bitmap由0和1状态表现的二进制位的bit数组，它的本质是一个数组

![bitmap结构](../文件/图片/Redis/bitmap结构.jpg)

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`setbit key offset value`|offset:数组下标<br>value:指定位，只能是0或1|指定数组下标的位值|无|
|`getbit key offset`|offset:数组下标|得到对应下标的值|无|
|`strlen key`|无参|统计字节的占用量，每8位组成一个字节|无|
|`bitcount key [start end [byte\|bit]]`|start:开始下标<br>end:结束下标|统计指定区间内值是1的有多少个，如果没有写明区间默认是统计整个数组|无|
|`bitop operation destkey key1 [key2...]`|destkey:存储运算结果的bitmap<br>operation:共有四种模式,and、or、xor、not分别对应与运算、或运算、异或运算和取反运算|对一个或多个bitmap执行位运算|无|

---

### （七）HyperLogLog

+ HyperLogLog是用来做基数统计的算法，就是去重统计
  + 基数指一个集合中不重复的元素个数
+ 该类型不会存储数据，且有一定的误差(0.81%)，但其性能优异
+ HyperLogLog在处理大数据去重统计具有内存占用方面的显著优势，它只需要占用12KB内存，就可以统计接近2^64个不同元素的基数

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`pfadd key value1 [value2 ...]`|valuen:元素值|将指定元素添加到HyperLogLog中进行统计|无|
|`pfcount key1 [key2...]`|无参|将多个HyperLogLog取并集并统计去重元素的个数|无|
|`pfmerge destkey key1 [key2...]`|destkey:接收结果的HyperLogLog|将多个HyperLogLog合并为一个|无|

#### ①实现概述

+ [参考](https://blog.csdn.net/2301_82241859/article/details/137119275)

+ 实现概述:
  + HyperLogLog利用了伯努利实验的特性，伯努利实验就是在同样的条件下重复地、相互独立地进行的一种随机试验。最典型的例子就是抛硬币，其条件相同且出现正反面的概率都是0.5
  + 而**Redis中的哈希算法会得到一个64比特位的值**，每个比特位只有1或0，**只要其哈希算法足够好，那么每个位出现1或0的概率就可以认为是相同的**
  + 这64位的哈希值需要分出后14位来表示该数据所属的槽（2^14=16384），剩下的前50位用来进行HyperLogLog的概率算法计算，它的计算方式是:**从右向左数，数到第一个位为1的值为止，统计所数的位数**，比如某值的哈希的前50位为:`...010010000`，那么从右向左数，第一个1在第五位，因此值为5，而出现该情况的概率为1/32（每个位数出现的概率都是0.5，那么右边连续出现4个0，第五位出现一个1的概率即`((1/2)^4)*(1/2)=1/32`）
  + 如果槽位上出现了多个数据，那么就需要统计这些数据对应的哈希值中通过概率算法计算出来值的最大值，**在极端情况下，就会出现左边第一位是1，剩下的位数全是0的情况，此时概率算法计算出来的值就是50，50需要使用6个比特位才能表示（2^6=64），因此每个槽中概率算法值的最大值都需要使用6个比特位来存储**，那么**一个HyperLogLog数据类型占用的大小就是`16384*6/8/1024=12KB`(16384表示16384个槽位，6表示存储每个槽的概率算法值需要的比特位数，8即1B=8bit，1024即1KB=1024B)**
  + 最后，Redis会计算这16384个槽位所计算出来的概率算法值的调和平均值，最终得到了最终的HyperLogLog值，因为把各个槽位的概率算法值进行平均，可以降低误差
  ![HyperLogLog结构图示](../文件/图片/Redis/HyperLogLog结构图示.png)
+ HyperLogLog的典型应用场景是:为了统计某网站的UV(Unique Visitor独立访客)，使用HyperLogLog对访客进行去重统计

---

#### ②稀疏结构与密集结构

+ [参考](https://blog.csdn.net/2301_82241859/article/details/137119275)

+ 当redis刚创建完一个hyperloglog结构的时候，其中的所有bit都为0。为了避免重复数据对存储空间的浪费，redis使用了几种特殊的数据结构来表示重复数据:
  + ZERO : 一字节，表示连续多少个桶计数为0，前两位为标志00，后6位表示有多少个桶，最大为64
  + XZERO : 两个字节，表示连续多少个桶计数为0，前两位为标志01，后14位表示有多少个桶，最大为16384
  + VAL : 一字节，表示连续多少个桶的计数为多少，前一位为标志1，四位表示连桶内计数，所以最大表示桶的计数为32。后两位表示连续多少个桶
  + ZERO和XZERO的区别在于如果连续为0的桶数量小于64个的时候，就没必要用14个bit来表示数量，进一步节约空间
  ![HyperLogLog稀疏结构与密集结构](../文件/图片/Redis/HyperLogLog稀疏结构与密集结构.png)
+ 当redis创建完一个新的hyperloglog结构时，因为其中的所有bit都为0，所以并不需要实际使用12kb的空间存放16384个0，而是用2个字节的XZERO来表示:
  ![HyperLogLog-XZERO](../文件/图片/Redis/HyperLogLog-XZERO.png)
+ 经过用户的少数几次访问后，redis可能用如下结构存储:
  ![HyperLogLog稀疏结构与密集结构1](../文件/图片/Redis/HyperLogLog稀疏结构与密集结构图例1.png)
+ 当满足如下条件时，就会从稀疏结构不可逆地变成密集结构:
  1. 任意一个val结构存储的值达到33，超出了能存储的最大值
  2. 稀疏结构的总字节数超过3000字节

---

### （八）GEO

+ Geospatial主要用来存储地理位置信息，它可以有效解决传统数据库对于地理空间表示缺陷的问题:
  + 查询性能问题:如果并发高，数据量大这种查询是要搞垮数据库的
  + 我们有时希望得到以我们为中心的圆的地理位置信息，而不是以我们为中心的矩形的地理位置信息
  + 精准度问题:地球不是平面坐标系，而是一个圆球，这种矩形计算在长距离计算时会有很大误差
+ GEO底层实际上是使用zset进行存储的

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`geoadd key longitude latitude value [longitude latitude value ...]`|longitude:经度信息<br>latitude<br>纬度信息<br>value:该位置的名称|添加位置信息|无|
|`geopos key value1 [value2]`|valuen:位置名称|根据位置名称得到位置的经纬度信息|无|
|`geodist key value1 value2 {m\|km\|ft\|mi}`|value1:位置名称1<br>value2:位置名称2<br>m:以米为单位<br>km:以千米为单位<br>ft:以英寸为单位<br>mi:以英里为单位|得到两个给定位置之间的距离|无|
|`georadius key longitude latitude radius {m\|km\|ft\|mi} [withcoord] [withdist] [withhash] [count number [any]]`|longitude:中心的经度信息<br>latitude:中心的纬度信息<br>radius:半径距离<br>number:限制返回数量<br>withcoord:连带返回匹配项与指定中心的距离<br>withdist:连带返回匹配项的经纬度坐标<br>withhash:连带返回匹配项的经纬度哈希映射|以给定的经纬度为中心，返回与中心的距离不超过给定最大距离的所有元素位置|从Redis6.2.0开始，此命令被弃用，它的作用被GEOSEARCH和GEOSEARCHSTORE替代|
|`GEOSEARCH key {FROMMEMBER member \| FROMLONLAT longitude latitude} {BYRADIUS radius {m\|km\|ft\|mi} \| BYBOX width height {m\|km\|ft\|mi}}[ASC \| DESC] [COUNT count [ANY]] [WITHCOORD] [WITHDIST] [WITHHASH]`|member:指定的在key中的位置名称<br>longitude:中心的经度信息<br>latitude:中心的纬度信息<br>radius:半径距离<br>width:宽度<br>height:长度<br>asc:结果升序排序<br>desc:结果降序排序<br>count:限制返回数量，一旦匹配到指定数量就会直接返回<br>withcoord:连带返回匹配项与指定中心的距离<br>withdist:连带返回匹配项的经纬度坐标<br>withhash:连带返回匹配项的经纬度哈希映射|以给定的位置或经纬度坐标为中心，选择矩形匹配或半径匹配方式查找区间内的位置|于Redis6.2.0被引入|
|`GEOSEARCHSTORE destination source <FROMMEMBER member \| FROMLONLAT longitude latitude> <BYRADIUS radius <M \| KM \| FT \| MI> \| BYBOX width height <M \| KM \| FT \| MI>> [ASC \| DESC] [COUNT count [ANY]] [STOREDIST]`|storedist:存储结果的GEO对象，其余参数见上|此命令类似于 GEOSEARCH，但将结果存储在目标键中|无|
|`GEOHASH key [member [member ...]]`|member:位置名称|批量计算给定的位置名称所对应经纬度的哈希值并返回|于Redis6.2.0被引入|

---

### （九）Stream

+ 在Redis5.0之前，Redis共有两种消息队列的方案:
  + List实现消息队列:常用来做异步队列使用，将需要延后处理的任务结构体序列化成字符串塞进Redis的列表，另一个线程从这个列表中轮询数据进行处理
  ![List实现消息队列](../文件/图片/Redis/List实现消息队列.png)
  + 发布订阅(pub/sub)模式:没有办法保证持久化，如果中途出现故障，消息就会丢失
  ![发布订阅模式](../文件/图片/Redis/发布订阅模式.png)
+ 在Redis5.0之后，Redis引入了一个新的数据类型Stream来解决当时Redis面临的消息队列的问题
  ![Stream底层结构](../文件/图片/Redis/Stream结构.png)
  + Stream使用一个链表，将所有加入进来的消息都串起来，每个消息都有一个唯一的ID和对应的内容

|字段|作用|
|:---:|:---:|
|Message Content|消息内容|
|Consumer group|消费组，通过XGROUP CREATE命令创建，同一个消费组可以有多个消费者|
|Last_delivered_id|游标，每个消费组会有一个游标last_delivered_id,任意一个消费者读取了消息都会使游标last_delivered_id往前移动|
|Consumer|消费者，消费组中的消费者|
|Pending_ids|消费者会有一个状态变量，用于记录被当前消费已读取但未ack的消息id,如果客户端没有ack，这个变量里面的消息id会越来越多，一旦某个消息被ack它就开始减少。这个pending_ids变量在Redis官方被称为PEL(Pending Entries List)，记录了当前已经被客户端读取的消息，但是还没有ack(Acknowledge character:确认字符)，它用来确保客户端至少消费了消息一次，而不会在网络传输的中途丢失了没处理|

|特殊符号|作用|
|:---:|:---:|
|-、+|最小和最大可能出现的id|
|$|$表示只消费新的消息，当前流中最大的Id，可用于将要到来的信息|
|`>(大于号)`|用于XREADGROUP命令，表示迄今还没有发送给组中使用者的信息，会更新消费者组的最后Id|
|*|用于XADD命令，让系统自动生成Id|

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`xadd key {* \| id} field1 value1 [field2 value2 ...]`|fieldn valuen:表示消息属性的键值对|添加消息队列到末尾|无|
|`XRANGE key start end [COUNT count]`|start:起始的Stream的id，可以使用`-`替代<br>end:结束的Stream的id，可以使用`+`替代<br>count:限制切片的最大元素数量|Stream的切片|无|
|`xrevrange key end start [COUNT count]`|start:起始的Stream的id，可以使用`-`替代<br>end:结束的Stream的id，可以使用`+`替代<br>count:限制切片的最大元素数量|Stream的倒序切片|无|
|`xdel key id [id ....]`|id:Stream的id|批量删除Stream中的指定id的元素|无|
|`xlen key`|无参|查看当前key的元素数量|无|
|`xtrim key {maxlen\|minid}`|maxlen:限制Stream的最大元素数量,从开始到最大数量时，超过数量的元素会被丢弃<br>minid:限制Stream的最小id，比该id小的元素会被丢弃|对Stream长度进行截取，可以通过限制最大数量或限制最小id来进行截取|
|`xread [COUNT count] [BLOCK milliseconds] STREAMS key [key...] id [id...]`|count:限制读取的消息数量<br>milliseconds:该参数不写默认是不会阻塞的，设置为0代表永远阻塞，直到收到消息再解除阻塞。设置大于0表示阻塞对应毫秒值<br>id:**对于非阻塞情况下**，它表示只要id比该id大，就会被读取（想读取全部，可以取`0-0`）。但当该值取`$`时，它表示读取Stream最大的id的后面的消息，如果没有返回NIL。**对于阻塞情况下**，如果当前的限制条件（限制条件生效的方式和非阻塞的情况一致）取不到一条信息，那么就会等到直到收到消息返回或时间到了返回NIL，如果有满足条件的信息，那么就返回。**对应位置的id对应着对应位置的key的限制条件**|无|
|`xgroup create key group {id\|$}`|id:指定开始消费的id，即在该id之后的消息会被消费；如果取`$`表示只消费新消息|创建消费组|无|
|`XGROUP DESTROY key group`|group:指定的消费组|删除指定的消费组|无|
|`XGROUP SETID key group {id \| $}`|group:指定的消费组<br>id:指定开始消费的id，即在该id之后的消息会被消费；如果取`$`表示只消费新消息|修改消费组消费的起始id|无|
|`xreadgroup GROUP group consumer [COUNT count] STREAMS key [key ....] id [id....]`|group:指定的消费组<br>consumer:指定要进行消费的消费者<br>count:限制读取的数量<br>key:要读取的目标stream<br>id:取`>`时，表示从第一条尚未被消费的消息开始读取，取详细id时，读取该id以后的未消费信息，取0时，表示读取所有未被消费的消息|让指定组的指定消费者消费消息|不同消费组的消费者可以消费同一条消息，但是同一个组内的消费者消费完以后，同组的其它消费者就不能消费消息了|
|`XPENDING key group [start end count [consumer]]`|group:指定消费组<br>start:最小id<br>end:最大id<br>count:限制查询数量<br>consumer:指定查询的消费者|查询指定消费组内所有消费者或指定消费者在指定区间内的指定数量的消费的相关信息(状态为已读取，但尚未确认)|如果读取的消息已被确认处理完毕，那么不会显示|
|`xack key group id [id...]`|group:指定消费组<br>id:指定已处理完的消息id|确认指定的消息已处理完成|无|
|`xinfo STREAM key `|无参|打印流的详细信息|无|

![xpending输出结构](../文件/图片/Redis/xpending输出结构.png)



---

### （十）Bitfield

+ Bitfield能够将很多小整数存到一个长度较大的位图中，或将一个非常庞大的键分割为多个较小的键来存储，从而高效的使用内存

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`bitfield key set type offset value`|type:模式<br>offset:偏移量，表示模式后的区间范围<br>value:要替换的值|将指定bitfield以type模式开始，将接下来offset位用value替换|会返回修改前的原值|
|`bitfield key [get type offset]`|type:模式，其中`u<数字>`表示无符号整型，u8表示使用8位存储无符号整型，`i<数字>`表示有符号整型，i8表示使用8位存储无符号整型<br>offset:偏移量，表示模式后的区间范围<br>|得到指定区间内的位值|无|
|`bitfield key [incrby type offset increment]`|type:模式<br>offset:偏移量，表示模式后的区间范围<br>increment:自增的值|使对应区间内的值自增对应的值|无|
|`overflow [wrap\|sat\|fail]`|wrap:循环溢出处理，如果最大值溢出那么就从头开始填，最小值溢出就从尾开始填<br>sat:最大值溢出时，取最大值，最小值溢出时取最小值<br>fail:出现溢出情况时返回空值，表示命令未执行|处理溢出|无|

---

## 四、持久化

+ 由于Redis的数据处理和操作是在内存中的，如果出现宕机，会导致严重的数据丢失。因此，Redis也提供了数据持久化的功能
+ Redis的持久化方式主要有三种:
  + RDB(Redis DataBase)
  + AOF(Append Only File)
  + 混合使用

### （一）RDB

#### ①持久化配置与恢复

+ RDB持久化就是以指定的时间间隔执行数据集的时间点快照，它会在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是快照。这样一来即使故障宕机，快照文件也不会丢失，数据的可靠性也就得到了保证。该快照文件就被称为RDB

|配置项|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`save <seconds> <frequency> [<seconds> <frequency>]`|seconds frequency:**在seconds秒内修改key达frequency次及以上**，或者**两次修改key的时间间隔大于seconds秒**，那么就进行一次持久化|设置持久化策略|Redis6.0.16版本前，使用该方式配置|
|`dir <filepath>`|filepath:设置rdb文件的存放路径，默认存放在`./`文件夹下（相对于配置文件）|设置rdb文件的存放路径|无|
|`dbfilename <filename>`|filename:rdb文件的名称，相对路径是相对于dir的路径|设置rdb文件的名称|无|
|`save`|无参|以阻塞的方式进行持久化|无|
|`bgsave`|无参|以非阻塞的方式进行持久化|**推荐**|
|`lastsave`|无参|获得最后一次执行持久化的时间戳|无|
|`stop-writes-on-bgsave-error {yes\|no}`|无参|默认yes，如果配置成no，表示不在乎数据不一致或者有其他的手段发现和控制这种不一致，那么在快照写入失败时，也能确保redis继续接受新的请求|无|
|`rdbcompression {yes\|no}`|无参|默认yes，对于存储到磁盘中的快照，可以设置是否进行压缩存储。如果是的话，Redis会采用LZF算法进行压缩。如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能|无|
|`rdbchecksum {yes\|no}`|无参|默认yes，在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能|
|`rdb-del-sync-files {yes\|no}`|无参|在没有持久化的情况下删除复制中使用的RDB文件。默认情况下no，此选项是禁用的。|无|

+ 除save所指定的持久化策略，Redis在**执行shutdown或flush时也会自动进行持久化**，执行flush时，会产生一个空的rdb文件，无意义
+ 我们也可以通过`save`或`bgsave`手动进行持久化处理，推荐使用`bgsave`，因为它不会导致阻塞，它的实现原理是Redis会调用LinuxAPI让它产生一个和父进程相同的子进程，让该进程专门负责进行持久化处理
+ 如果我们想利用备份文件恢复数据，只需要将备份文件扔进Redis安装目录并启动服务即可

---

#### ②优劣

+ 优势:
  + 适合大规模的数据恢复
  + 按照业务定时备份
  + 对数据完整性和一致性要求不高
  + RDB文件在内存中的加载速度要比AOF快很多
+ 劣势
  + 在一定间隔时间做一次备份，所以如果redis意外down掉的话，就会丢失从当前至最近一次快照期间的数据，快照之间的数据会丢失
  + 内存数据的全量同步，如果数据量太大会导致IO严重影响服务器性能
  + RDB依赖于主进程的fork，在更大的数据集中，这可能会导致服务请求的瞬间延迟。fork的时候内存中的数据被克隆了一份，大致2倍的膨胀性，需要考虑

![RDB数据丢失](../文件/图片/Redis/RDB数据丢失.jpg)

---

#### ③RDB相关

+ **RDB文件的生成时机**:
  + 配置文件中默认的文件快照配置
  + 手动save、bgsave
  + 执行flush、flushall
  + 执行shutdown且未开启AOF持久化
  + 主从复制时，主节点自动触发
+ **禁用RDB**:
  + 使用`redis-cli config set value ""`
  + 手动修改配置文件的`save`项，将值赋一个空串:`save ""`
+ 检查修复RDB文件
  + 进入到Redis安装目录，执行`redis-check-rdb`命令:`redis-check-rdb rdb文件路径`

![RDB总结](../文件/图片/Redis/RDB总结.jpg)

---

<a id="AOF"></a>

### （二）AOF

#### ①概述

+ AOF以日志的形式来记录每个写操作，它会将Redis执行过的所有写指令记录下来，只许追加文件但是不可以改写文件。而Redis在启动时会读取该文件重新构建数据，即Redis在启动时就按照AOF记录的操作执行一次
+ AOF的工作流程如下:
  + 客户端向Redis服务器发送请求
  + Redis执行请求，并向AOF缓冲区写入对应指令，AOF缓冲区存在的目的是**当这些命令达到一定量以后再写入磁盘，避免频繁的磁盘IO操作**
  + 缓冲区根据同步文件的三种写回策略将命令写入磁盘上的AOF文件
  + 随着写入AOF内容的增加为避免文件膨胀，会根据规则进行命令的合并(又称AOF重写)，从而起到AOF文件压缩的目的
  + 当Redis Server服务器重启的时候会队AOF文件载入数据

  ![AOF工作流程](../文件/图片/Redis/AOF工作流程.jpg)

---

#### ②配置

|配置项|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`appendfsync {always\|everysec\|no}`|always:每次写操作时，都直接进行同步<br>everysec:每秒同步一次<br>no:把同步时机丢给操作系统处理|设置AOF同步文件的策略|无|
|`appendonly {yes\|no}`|略|设置AOF是否开启|无|
|`appenddirname <dirname>`|dirname:装AOF文件的文件夹名称|指定装AOF文件的文件夹名称|该配置项在Redis7才有，在Redis7之前，AOF文件都是与RDB文件放在一起的。该配置使得AOF的保存路径变成了dir+appenddirname|
|`appendfilename <filename>`|filename:AOF文件名称，默认为appendonly.aof|设置AOF文件名称|无|
|`auto-aof-rewrite-percentage <number>`|number:百分比的数值，想80%就写80|指定当前的AOF文件是上一个AOF文件的百分之多少时，执行重写|auto-aof-rewrite-percentage和auto-aof-rewrite-min-size**同时满足限制条件时，才会触发重写**|
|`auto-aof-rewrite-min-size <number>`|number:设定上限|指定当前AOF大小超过指定值时，执行重写|^|

![AOF的三种同步策略](../文件/图片/Redis/AOF三种同步策略.jpg)

---

#### ③特性与优劣

+ Redis7使AOF有了自己独有的文件夹，且文件从1个被拆分为3个
  + MP-AOF方案将一个AOF文件拆成了三个文件:
    + BASE:表示基础AOF，它一般由子进程通过重写产生，最多只有一个
    + INCR:表示增量AOF,它一般会在AOFRW开始执行时被创建，该文件可能存在多个
    + HISTORY:表示历史AOF，它由BASE和INCR AOF变化而来，每次AOFRW成功完成时，本次AOFRW之前对应的BASE和INCR AOF都将变为HISTORY，**HISTORY类型的AOF会被Redis自动删除**
  + 为了管理这些AOF文件，Redis引入了一个manifest(清单)文件来跟踪、管理这些AOF，另外，为了便于AOF备份和拷贝，我们将所有的AOF文件和manifest文件放入一个单独的文件目录中，目录名由appenddirname配置决定
+ 优势
  + 更好的保护数据不丢失、性能高、可做紧急恢复
+ 劣势
  + 相同数据集的数据而言aof文件要远大于rdb文件，恢复速度慢于rdb
  + aof运行效率要慢于rdb，每秒同步策略效率较好，不同步效率和rdb相同

---

#### ④AOF触发、重写与修复

+ AOF将在如下条件下被触发
  + 满足配置文件中的选项后，Redis会记录上次重写时地AOF大小
  + 默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时
  + 也可以使用`bgrewriteaof`命令手动触发
+ **重写**
  + AOF在写入时，由于记录的是命令操作，导致随着时间推移，AOF文件会较RDB文件来说更大。为了解决该问题，Redis使用重写机制来进行AOF的内容压缩。
  + 重写就是把AOF中多次重复修改的键去掉，然后加一个该键最后修改的值上去来减少文件大小
  + **AOF文件重写并不是对原文件进行重新整理，而是直接读取服务器现有的键值对，然后用一条命令去代替之前记录这个键值对的多条命令，生成一个新的文件后去替换原来的AOF文件**，这就会导致base.aof文件和incr.aof文件变成了新的，具体表现为它们的序号发生了变化，一般都是加了1
  + 重写原理
    + 在重写开始前，redis会创建一个“重写子进程”，这个子进程会读取现有的AOF文件，并将其包含的指令进行分析压缩并写入到一个临时文件中
    + 与此同时，主进程会将新接收到的写指令一边累积到内存缓冲区中，一边继续写入到原有的AOF文件中，这样做是保证原有的AOF文件的可用性，避免在重写过程中出现意外
    + 当“重写子进程”完成重写工作后，它会给父进程发一个信号，父进程收到信号后就会将内存中缓存的写指令追加到新AOF文件中
    + 当追加结束后，redis就会用新AOF文件来代替旧AOF文件，之后再有新的写指令，就都会追加到新的AOF文件中
    + 重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似
+ **修复**
  + 使用`redis-check-aof --fix 文件路径`进行修复，注意不要丢掉`--fix`那个参数

![AOF总结](../文件/图片/Redis/AOF总结.jpg)

---

### （三）混合持久化

+ 开启混合持久化很简单:
  + 先把`aof-use-rdb-preamble`的值置为yes
  + 然后配置`appendonly`为yes开启AOF
+ 混合持久化，就是RDB镜像做全量持久化，AOF做增量持久化。先使用RDB进行快照存储，然后使用AOF持久化记录所有的写操作，当重写策略满足或手动触发重写的时候，将最新的数据存储为新的RDB记录。这样的话，重启服务的时候会从RDB和AOF两部分恢复数据，既保证了数据完整性，又提高了恢复数据的性能。简单来说:**混合持久化方式产生的文件一部分是RDB格式，一部分是AOF格式。AOF包括了RDB头部+AOF混写**

![混合持久化图例](../文件/图片/Redis/混合持久化图例.jpg)

+ 在Redis重启时，会先查看是否存在AOF，如果存在就执行AOF。如果不存在查看是否存在RDB，存在就执行，不存在就完蛋

![混合持久化加载顺序](../文件/图片/Redis/混合持久化加载顺序.jpg)

---

## 五、事务

### （一）概述

+ 事务就是在一次连接数据库的会话中，执行的相关操作。它是一组逻辑操作单元，使数据从一种状态变换到另一种状态
+ Redis事务与传统的关系型数据库(如MySQL)有极大的不同:
  + 单独的隔离操作:Redis的事务仅仅是保证事务里的操作会被连续独占的执行，redis命令执行是单线程架构，**在执行完事务内所有指令前是不可能再去同时执行其他客户端的请求的**
  + 没有隔离级别的概念:因为**事务提交前任何指令都不会被实际执行**，也就不存在”事务内的查询要看到事务里的更新，在事务外查询不能看到”这种问题了
  + 不保证原子性:Redis的事务不保证原子性，也就是不保证所有指令同时成功或同时失败，只有决定是否开始执行全部指令的能力，**没有执行到一半进行回滚的能力**
  + 排它性:**Redis保证一个事务内的命令依次执行，而不会被其它命令插入**

---

### （二）使用

|命令|作用|备注|
|:---:|:---:|:---:|
|`multi`|表示事务开始|无|
|`exec`|将事务开始以后记录的所有操作依次执行|无|
|`discard`|取消事务，放弃执行事务块内的所有命令|无|
|`watch key [key....]`|监视一个或多个key，如果事务执行前这些key被其它命令改动，那么事务将被打断|无|
|`unwatch`|取消对key的监视|无|

+ Redis的事务在multi执行后，我们之后所写的操作是先经过语法检查，然后加入到一个队列中去，在执行exec命令时才真正执行
  + 如果语法检查不通过，那么将会报错
+ Redis的事务执行总体可以划分为:
  + 正常执行:事务正常执行了，并没有出什么问题
  + 放弃事务:执行了discard命令，事务被取消，事务队列中的所有操作都不会执行
  + 全体连坐:如果某个操作未通过语法检查，或者由于什么原因在exec执行前出现了错误，那么事务将被取消，此时执行exec会报错
  + 执行到底:如果语法检查未查出错误，那么执行时即使出错也会继续执行，出错的就操作失败，但不影响其它操作继续执行
  + 监控执行:使用watch进行监控时，Redis使用乐观锁来判断该key是否在事务执行时被别的事务所修改了，如果被修改，那么事务整体执行失败。另外，在客户端连接丢失的时候，监控也会被取消。但如果在exec执行前执行了unwatch，那么即使key已被修改，事务依然会执行，因为监控已经在执行前被取消了

---

## 六、管道

+ edis是一种**基于客户端-服务端模型**以及请求/响应协议的TCP服务。一个请求会遵循以下步骤
  + 客户端向服务端发送命令分四步(发送命令→命令排队→命令执行-返回结果)，并监听Socket返回，通常以阻塞模式等待服务端响应
  + 服务端处理命令，并将结果返回给客户端。 上述两步称为: Round Trip Time(简称RTT,数据包往返于两端的时间)

![Redis客户端服务端交互](../文件/图片/Redis/Redis客户端与服务端交互.jpg)

+ 如果同时需要执行大量的命令，那么就要等待上一条命令应答后再执行，这中间不仅仅多了RTT (Round Time Trip) ，而且还频繁调用系统IO， 发送网络请求，同时需要redis调用多次read()和write()系统方法，系统方法会将数据从用户态转移到内核态，这样就会对进程上下文有比较大的影响了，性能不太好。这时候Redis管道就出现了
+ **管道(pipeline)可以一次性发送多条命令给服务端**
  + 服务端依次处理完完毕后，通过一条响应一次性将结果返回，通过减少客户端与redis的通信次数来实现降低往返延时时间。
  + pipeline实现的原理是队列，先进先出特性就保证数据的顺序性。
  + 管道相当于对命令进行顺序的批处理

![Redis管道图例](../文件/图片/Redis/Redis管道图例.png)

+ 我们可以将命令存在一个txt文件，然后执行管道命令统一处理

~~~bash
  cat test.txt | redis-cli -a 123456 --pipe
~~~

+ pipeline与原生批量命令对比
  + 原生批量命令是原子性（如：mset，mget），**pipeline是非原子性**
  + 原生批量命令一次只能执行一种命令，**pipeline支持批量执行不同命令**
  + 原生批命令是服务端实现，而**pipeline需要服务端与客户端共同完成**
+ Pipeline 与事务对比
  + 事务具有原子性，管道不具有原子性
  + 管道一次性将多条命令发送到服务器，事务是一条一条发的，事务只有在接收到exec命令后才会执行，管道不会
  + 执行事务时会阻塞其他命令的执行，而执行管道中的命令时不会
+ Pipeline 注意事项
  + pipeline缓冲的指令只是会依次执行，**不保证原子性，如果执行中指令发生异常，将会继续执行后续的指令**
  + 使用pipeline组装的命令个数不能太多，不然数据量过大客户端阻塞的时间可能过久，同时服务器也被迫回复一个队列答复，占用很多内存

---

## 七、发布订阅

### （一）概述

+ 发布订阅是一种消息通信模式：发送者(PUBLISH)发送消息，订阅者(SUBSCRIBE)接收消息，可以实现进程间的消息传递
  + Redis客户端可以订阅任意数量的频道，类似我们微信关注多个公众号
  ![订阅图例](../文件/图片/Redis/订阅图例.jpg)
  + 当有新消息通过publish命令发送给频道channel1时，订阅客户端都会收到消息
  ![发布图例](../文件/图片/Redis/发布图例.jpg)
  + 发布订阅实际上就是一个轻量级的队列，只不过数据不会被持久化，一般用来处理实时性较高的异步信息
  ![发布订阅总结](../文件/图片/Redis/发布订阅总结.jpg)

---

### （二）常用命令

|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`psubscribe pattern [pattern]`|pattern:匹配模式，可以通过*或者?进行匹配|订阅一个或多个满足匹配规则的频道|推荐先订阅再发布，**订阅前发布的消息是收不到的**|
|`pubsub channels [pattern]`|^|查看频道的信息|无|
|`SUBSCRIBE channel [channel ...]`|channel:频道|订阅一个或多个频道|无|
|`PUBLISH channel message`|channel:指定频道<br>message:消息|发送消息到指定频道|无|
|`PUBSUB NUMSUB [channel [channel ...]]`|channel:指定频道|查看某个或某些频道有多少订阅者|无|
|`PUBSUB NUMPAT`|无参|​只统计使用PSUBSCRIBE命令执行的返回客户端订阅的唯一模式的数量|无|
|`UNSUBSCRIBE [channel [channel ...]]`|channel:指定频道|退订指定的频道|无|
|`PUNSUBSCRIBE [pattern [pattern ...]]`|pattern:匹配模式|退订所有符合匹配模式的频道|无|

---

### （三）小结

+ 发布订阅可以实现消息中间件MQ的功能，通过发布订阅实现消息的引导和分流。但是**不推荐使用该功能，专业的事情交给专业的中间件处理，redis就做好分布式缓存功能**
+ 缺点:
  + 发布的消息在Redis系统中不能持久化，因此，必须先执行订阅，在等待消息发布。如果先发布了消息，那么该消息由于没有订阅者，**消息将被直接丢弃**
  + 消息只管发送，对于发布者而言消息是即发即失，不管接受，也没有ACK机制，**无法保证消息的消费成功**
  + 以上的缺点导致Redis的Pub/Sub模式就像个小玩具，在生产环境中几乎无用武之地，为此Redis5.0版本新增了Stream数据结构，不但支持多播，还支持数据持久化，相比Pub/Sub更加的强大

---

<a id="masterSlave"></a>

## 八、主从复制

+ 主从复制可以:
  + 实现读写分离:master负责写，一众slave负责读
  + 支持容灾恢复:一个节点数据出现问题，可以从其它节点得到数据进行同步
  + 实现数据备份:
  + 水平扩容支持高并发

### （一）一主二从

+ 就是多个Redis服务器一起处理业务，一般有一台负责写，多台负责读。负责写的当主机，负责读的当从机
+ 实现主从复制需要修改如下配置:
  + daemonize yes
  + 注释 bind 127.0.0.1
  + protected-mode no
  + requirepass设置密码
  + port指定端口（可选）
  + dir指定当前工作目录（可选）
  + pidfile指定pid文件名称（可选）
  + logfile指定日志文件名称（可选）
  + dbfilename指定rdb文件名称（可选）
  + [AOF相关配置](#AOF)（可选）
  + masterauth配置连接主机时需要的密码（**从机配置项，必须**）
  + replicaof配置连接的主机和主机的redis服务器所占用的端口（**从机配置项，必须**）。或者该项我们可以不配置，在我们的从服务器（如果不配置启动后是主服务器，因为它还没有指定一个主服务器当主机）启动后，使用`slaveof 主机IP 主机redis端口号`来找一个主机当主服务器
+ 接下来启动服务器，**先启动主机，再启动从机**
  + 从机启动以后，我们可以在主机的日志中找到主从配置是否成功的信息:
  ![主从复制日志输出](../文件/图片/Redis/主从复制日志输出.png)
+ 运行`info replication`命令可以得到主从复制的相关信息
+ 在使用配置文件配置的情况下（也就是在配置文件中配置了replicaof）
  + 主服务器既能读又能写，从服务器只能写
  + 主服务器的写入操作会被从服务器同步
  + 如果从服务器挂掉，而主服务器继续写入，那么从服务器在恢复以后会同步之前的数据，也就是从主服务器中拿到它不工作的这段时间内主服务器修改的数据
  + 如果主服务器挂掉，那么从服务器会等待主服务器恢复
+ 在使用`slaveof`命令配置主从的前提下
  + 主服务器既能读又能写，从服务器只能写
  + 主服务器的写入操作会被从服务器同步
  + 如果主服务器挂掉，那么从服务器会等待主服务器恢复
  + 如果从服务器挂掉再启动，那么它不会再作为之前主机的从服务器了，而是又变成自己做主了，因为它的配置文件中并没有配置相关的主从复制内容

### （二）薪火相传

+ 就是主机的从机能当别的从机的主机
+ 这样做可以减轻master的负担，但是会增加延迟，因为树的叉减少了，深度就会增加

![薪火相传示例](../文件/图片/Redis/薪火相传示例.png)

+ 这个可以直接用`slaveof`直接实现

---

### （三）反客为主

+ 即从机自己独立出去成为主机
+ 使用`slaveof no one`命令实现

---

### （四）主从复制流程

1. slave启动成功（假设通过配置连接）并连接到master后，会向master发送一个sync命令，且slave第一次全新连接master时，将执行一次完全同步，slave自身的全部数据将被master传来的同步数据覆盖
2. master节点收到sync命令后，会在后台保存快照，同时在保存快照期间将收到的修改命令缓存起来，等快照保存完毕将快照数据和所有缓存的命令一并发送给slave
3. slave收到数据和缓存命令后，将数据存盘并加载到内存，完成复制初始化
4. master通过向slave定时发送ping包，以确认slave是否存活,该配置可以通过`repl-ping-replica-period`配置项修改
5. 稳定下来以后，master继续将收集到的修改命令自动依次传送给slave,完成同步
6. slave下线再上线时，master会检查backlog日志文件中的offset字段（master和slave都会保存一个复制要用的offset，保存于backlog,类似于进度条，还有一个masterId。），master会从slave下线的offset字段开始，把后面的数据传送给slave,类似断点续传

![主从复制流程图例](../文件/图片/Redis/主从复制流程图例.png)

---

### （五）主从复制缺点

+ 所有的写操作在master上进行，然后同步到slave中，如果系统非常繁忙，延迟问题会变得严重，而slave机器数量的增加会使该问题更加严重
+ 另外，如果master出现了故障导致崩溃，那么slave就只能在那里干等着，因为它们不会默认自动再选出一个master

---

## 九、哨兵监控

+ 为了避免master挂掉导致整个redis主从复制架构出现问题，Redis官方提出了哨兵监控的解决方案
+ 哨兵监控有如下功能:
  + 主从监控:哨兵可以监控redis主从机是否正常运行
  + 消息通知:哨兵可以将故障转移的结果发送给客户端
  + 故障转移: 如果master出现故障，将进行主从切换，从剩下的Slave中选一个当master，保证主从架构的正常运行
  + 配置中心:客户端通过连接哨兵来获得当前Redis服务器的主节点地址

### （一）配置

+ 在我们安装的redis目录中找到`sentinel.conf`文件，将它拷贝一份，放到配置目录中
+ 修改一些配置，或者直接创建一个conf文件，具体要写的配置如下:

~~~conf
  bind 0.0.0.0
  daemonize yes
  protected-mode no
  port 26379
  logfile "sentinel26379.log"
  pidfile "/var/run/redis-sentinel26379.pid"
  dir "/home/study/redis/data"
  sentinel monitor mymaster xxx 6379 2
  sentinel auth-pass mymaster 123456
~~~

+ 修改conf文件名，因为默认占用的是26379端口，因此可以叫`sentinel26379.conf`
+ 再搞俩文件出来，分别命名为`sentinel26380`和`sentinel26381.conf`
+ 接下来是主从复制的配置，[详情见上](#masterSlave)。这里需要给master也配置一下masterauth，方便之后测试，否则可能会报错
+ 接下来启动master和slave，之后执行`redis-sentinel 配置文件路径`来启动redis哨兵（哨兵一般都是单独运行在一个服务器上的，一个服务器运行一个，但是由于服务器成本限制，可以把哨兵跟master放在一起。
+ 启动完成以后，打开sentinel相关的配置文件，发现redis自己向配置文件写了一些东西。打开日志，发现日志也多了一些东西

---

### （二）测试

+ 现在我们在master的redis上面加点键值对，然后shutdown一下
+ 接下来转到slave,get一下刚才的键值对，它就会报`not connected`，这是正常现象，是哨兵机制为了调整主从结构而导致的正常情况，因此我们可以再get一下，发现就好了
+ 接下来对两个slave执行`info replication`，发现它们其中有一个已经变成master了
+ 再启动原来的master,发现它已经不是master了，已经变成slave了
+ 打开原master的redis配置文件，发现哨兵在它的配置文件最后添加了一些额外配置，包括RDM的生成策略、主机的ip和端口号还有其他一些东西

![哨兵监控图例1](../文件/图片/Redis/哨兵监控图例1.png)

+ 打开当前master的配置文件，发现哨兵把它配置文件配置的`masterauth`移除掉了
+ 哨兵对配置文件的改动保证当前master在下一次启动时依旧是master,而原master在重启后依旧为slave

---

### （三）运行流程与选举原理

+ 假定现在有一主两从，三个哨兵:

![哨兵监控图例2](../文件/图片/Redis/哨兵监控图例2.png)

+ 在master失效后，哨兵会自动在slave中推选出一个新的master,然后让原master和其它slave都认新上台的master做主机，然后进行数据同步
+ **主观下线**(SDown):主观下线就是说如果Redis服务器在哨兵配置文件中的`down-after-milliseconds`给定的毫秒数之内没有回应某一哨兵的PING命令或者返回一个错误消息， 那么这个哨兵会主观的(单方面的)认为这个master不可以用了，`down-after-milliseconds`的默认值是30000(单位：毫秒)，即30s。**主观下线可能会受到网络波动、延迟等网络因素的影响**，因此还需要进行客观下线的判断
+ **客观下线**(ODown):即有的哨兵认定master主观下线以后，需要在哨兵集群内进行投票，当多个哨兵达成一致意见数到达指定数量时，才认定此master。该配置由`sentinel monitor <master-name> <ip> <redis-port> <quorum>`来配置，其中的`quorum`来决定客观下线的最小票数，即至少要quorum个哨兵同意后，该master才会被认定为ODown，然后执行下线和故障转移操作
+ **选举领导哨兵**:主节点被宣布为判断客观下线后，各哨兵就会进行协商，先选举出一个领导哨兵(leader)，然后由该leader执行故障迁移
  + 选举流程可以在对应leader哨兵的日志中找到
  ![哨兵监控图例3](../文件/图片/Redis/哨兵监控图例3.png)
  + 哨兵leader是根据Raft算法算出来的
  ![哨兵监控图例4](../文件/图片/Redis/哨兵监控图例4.png)
  + Raft算法的基本思路是**先到先得**:即在一轮选举中，哨兵A向B发送成为领导者的申请，如果B没有同意过其他哨兵，则会同意A成为领导者，然后给A投票
  + 在上图中，哨兵1先给2和3发送了请求，然后它就得到了两票，而2先给1发的请求，所以1要把票投给2，但是2给3发的请求比1给3发的请求要晚，所以3不会投票给2。3给1和2发的请求是最晚的，因此3没有任何票
+ **领导哨兵执行故障迁移**:
  + 首先选出一个新的master:它依次根据slave的优先级（通过slave-priority、replica-priority等配置设置，数字越小优先级越高）高的、复制偏移量offset最大的从节点、最小run ID的从节点（按字典序，ASCii码比较）来选出新的master
  + 哨兵leader通过`slaveof`命令来使其它slave的master指向新的master，并对新的master执行`slaveof no one`操作将其提升为master
  + 让原master降级为slave，也指向新的master

---

### （四）常用配置项

|配置项|参数|作用|备注|
|:---:|:---:|:---:|:---:|
|`sentinel monitor <master-name> <ip> <redis-port> <quorum>`|master-name:标识指定master的称呼<br>ip:master所在服务器的ip<br>redis-port:redis占用的端口<br>quorum:客观下线需要的的最小票数|设置哨兵对指定master的监控和客观下线需要的最小票数|无|
|`sentinel auth-pass <master-name> <password>`|master-name:标识指定master的称呼<br>password:连接master需要的密码，对应requirepass|配置连接指定master需要的密码|无|
|`sentinel down-after-milliseconds <master-name> <milliseconds>`|master-name:标识指定master的称呼<br>milliseconds:指定判断主观下线的最大毫秒数，即哨兵未收到master回复多少毫秒时判定为主观下线|设置判断主观下线的时间指标|无|
|`sentinel parallel-syncs <master-name> <nums>`|master-name:标识指定master的称呼<br>nums:允许并行同步的slave个数|限制允许并行同步的slave个数|无|
|`sentinel failover-timeout <master-name> <milliseconds>`|master-name:标识指定master的称呼<br>milliseconds:指定判断故障转移失效的时间，单位毫秒|设置故障转移的超时时间|无|
|`sentinel notification-script <master-name> <script-path>`|master-name:标识指定master的称呼<br>script-path:执行的脚本|配置当某一事件发生时所需要执行的脚本|无|
|`sentinel client-reconfig-script <master-name> <script-path>`|master-name:标识指定master的称呼<br>script-path:执行的脚本|配置客户端重新配置主节点参数脚本|无|

---

### （五）使用建议

+ 哨兵节点的数量应该为多个，烧饼本身应该集群，保证高可用
+ 哨兵节点的数量应该是奇数
+ 各个哨兵节点的配置应该一致
+ 如果哨兵节点部署在Docker等容器里面，尤其要注意端口的正确映射
+ 哨兵集群+主从复制，并**不能保证数据零丢失**

---

## 十、集群

### （一）概述

+ Redis集群是当前Redis推荐的方案，它是一个提供在多个Redis节点间共享数据的程序集
+ **Redis集群并不保证强一致性**，这意味着在特定条件下，Redis集群可能会丢掉一些被系统收到的写入请求命令

![Redis集群示例1](../文件/图片/Redis/Redis集群示例1.png)

+ **Redis集群支持存在多个master**，每个master又可以挂载多个slave
  + 实现了读写分离
  + 支持数据的高可用
  + 支持海量数据的读写存储操作
+ 由于Cluster自带哨兵的故障转移机制，内置了高可用的支持，**无需再使用哨兵功能**
+ 客户端只需要连接Redis集群的一个节点，便可以进行操作
+ 槽位slot负责分配到各个物理服务节点，由对应的集群来负责维护节点、插槽和数据之间的关系

---

### （二）槽位与分片

#### ①槽

+ Redis集群并没有使用一致性hash,而是引入了哈希槽
  + Redis集群有16384个哈希槽，每个key通过CRC16校验后对16384取模来决定放置在哪个槽中，集群的 每个节点负责一部分槽

  ![Redis集群示例2](../文件/图片/Redis/Redis集群示例2.png)

---

#### ②分片

+ 使用Redis集群时，存储的数据会分散到多台Redis机器上，称为分片。集群中的每个实例都被认为是整个数据的一个分片
+ 为了找到给定的分片，需要对key进行CRC16校验，然后对总分片数量取模，然后使用确定性哈希函数来确保给定的key将多次映射到同一个分片中

![Redis集群示例3](../文件/图片/Redis/Redis集群示例3.png)

---

#### ③槽位映射算法

+ 目前业界有三种解决方案，它们分别是**哈希取余分区**、**一致性哈希算法分区**和**哈希槽分区**
  + **哈希取余分区**:
    + 就是有多少台Redis节点就对多少取余
    + 优点:简单粗暴，可以保证数据大致平均的分摊到每个Redis节点上，保证了负载均衡，达到了分而治之的目的
    + 缺点:由于节点数写死了，再进行扩容和缩容就变得麻烦，同一个key在扩容或缩容所映射到的节点可能会发生变化，因为取模的数变了。另外如果某台Redis机器宕机了，也会导致hash取余全部数据重新洗牌
  + **一致性哈希算法分区**:
    + 该算法于1997年由麻省理工学院提出，设计目的就是为了解决上面的哈希取余算法导致的数据变动和映射问题
    + 首先需要构建一个哈希环，一致性哈希算法是对2^32进行取模的，因此哈希环的范围就是0~2^32-1
    ![Redis集群示例4](../文件/图片/Redis/Redis集群示例4.png)
    + 接下来将各个Redis服务器节点按照哈希算法进行一次映射，将它们映射到哈希环的对应下标下，这样就能确定每台机器在哈希环上的位置了
    ![Redis集群示例5](../文件/图片/Redis/Redis集群示例5.png)
    + 最后是key存储的映射，当想存储一个key时，先使用与服务器节点相同的确定性哈希函数计算出该key所在的哈希环映射，然后按顺时针方向“行走”，直到碰到第一台Redis服务器，然后把它装进该服务器中
    ![Redis集群示例6](../文件/图片/Redis/Redis集群示例6.png)
    + 优点:
      + **容错性强**:如果一台服务器发生故障，只需要继续顺时针寻找在哈希环上的下一台服务器即可。
      + **拓展性强**:插入新的节点X时，X在A到B之间，那么只需要把A到X之间的数据从B迁移到X即可
    + 缺点:该算法无法保证节点是平均分布在哈希环中的，因此容易出现因节点分布不均匀而导致数据大量存储在一个或几个服务器中，而其余服务器则仅存储了少量数据
    ![Redis集群示例7](../文件/图片/Redis/Redis集群示例7.png)
  + **哈希槽分区**:
    + 哈希槽就是在数据和服务器之间加了一层哈希槽，用于管理数据与节点之间的关系，相当于节点中放的是槽，而槽中存放的是数据
    ![哈希槽分区示例1](../文件/图片/Redis/哈希槽分区示例1.png)
    + **槽解决的是粒度问题**，相当于把粒度变大了，这样便于数据移动。**哈希解决的是映射问题**，使用key的哈希值来计算所在的槽，便于数据分配
    + Redis规定一个集群有16384个哈希槽，而官方推荐节点不应该超过1000个。这些哈希槽会被**分配给集群中的所有主节点**
    + 当想存储一个key时，会根据哈希算法对key求哈希值并对16384取模，这样就能决定key会被放在哪个槽中，因为槽的数量是固定的，因此一个key始终能映射到对应的槽，这样就解决了数据移动的问题
    ![哈希槽的计算示例](../文件/图片/Redis/哈希槽的计算示例.png)

---

#### ④最大槽数16384原因

+ CRC16算法所产生的哈希值有16bit,也就是可以得到2^16=65536个值，但是哈希槽的数量是16384，原因如下:
  + master每隔一段时间会发送心跳包，该心跳包发送的信息之一是它本身负责的槽信息，它的大小由其负责的槽数量决定（槽数量/8），如果槽有65536个，那么需要发送65536/8/1024（C++一个char字符占用一个字节）=8kb的槽信息，这会导致**发送心跳包需要的消息头太大，浪费带宽**
  ![心跳包示例](../文件/图片/Redis/心跳包示例1.png)
  + Redis的主节点数量一般不会超过1000个，因为集群节点越多，心跳包的消息体内携带的数据越多。如果节点过1000个，也会导致网络拥堵。因此Redis官方也不推荐节点数量超过1000个，**对于1000个以内的节点，16384个哈希槽已经够用了**，没有必要再拓展到65536个
  + Redis主节点的配置信息中它所负责的哈希槽是通过一张bitmap的形式来保存的，在传输过程中会对bitmap进行压缩，但是如果bitmap的填充率slots / N很高的话(slots表示哈希槽总量，N表示当前节点数)，bitmap的压缩率就很低。 **如果节点数很少，而哈希槽数量很多的话，bitmap的压缩率就很低**。 

---

### （三）搭建集群

+ 由于服务器限制，这里每个服务器上跑两个redis来模拟三主三从集群
  + 先进行文件配置，酌情修改:

  ~~~conf
    bind 0.0.0.0
    daemonize yes
    protected-mode no
    port 6382
    logfile "cluster6382.log"
    pidfile cluster6382.pid
    dir ../data
    dbfilename dump6382.rdb
    appendonly yes
    appendfilename "appendonly6382.aof"
    requirepass 123456
    masterauth 123456

    cluster-enabled yes
    cluster-config-file nodes-6382.conf
    cluster-node-timeout 5000
  ~~~

  + 接下来启动各redis服务器:`redis-server xxx.conf`
  + 然后`redis-cli -a 123456 --cluster create --cluster-replicas 1 xxx:6381 xxx:6382 xxx:6383 xxx:6384 xxx:6385 xxx:6386`来搭建集群
    + xxx表示各节点所在服务器的IP地址
    + 搭建之前确保端口开放，**每个集群节点需要两个端口**，如果服务器要占用6379端口，那么还需要一个16379（占用端口+10000）端口来与集群总线连接，用来进行节点的失败检测、配置更新、故障转移等
    + 密码需要写自己的redis设置的密码
    + IP需要写当前redis实际所在的IP,端口也一样
    + `--cluster create`是创建集群实例列表的命令，选项后跟`IP:PORT IP:PORT IP:PORT`格式
    + `--cluster-replicas`指定复制因子，即每个主节点需要多少个从节点
  + 然后跟着它的指引来。如果卡在命令开始或者`Waiting for the cluster to join`大概率是因为服务器端口未开放
  ![Redis集群搭建图例](../文件/图片/Redis/Redis集群搭建图例.png)
  + 接下来使用`info replication`、`cluster info`、`cluster nodes`来确认集群的状态

---

### （四）集群读写与主从容错

+ **集群读写**
  + 如果我们依然像以前一样连接某一台redis实例时，那么在进行读写操作时就会出现问题，大致就是我们想存储的key经过哈希槽映射算法计算出来的值不归这个节点管，因此它就会提示我们去对应的节点进行操作
  + 为了解决该问题，我们可以在连接时指定`-c`参数，来进行路由优化，从而解决该问题
+ **主从切换**
  + 集群自带了哨兵的功能，因此可以自动地进行主从切换，如果主机挂掉，那么从机就会变成主机，而之后恢复的主机就变成了从机
  + 在原主机上执行`cluster failover`可以重新变成主机

---

### （五）扩容与缩容

+ **扩容**:
  + 首先我们再启动俩redis服务，配置文件与之前的一样，设新启动的端口分别是6387和6388
  + 接下来使用`redis-cli -a <password> --cluster add-node <addHost>:<addPort> <clusterHost>:<clusterPort>`来将6387的redis服务器加到集群里，但是加集群需要集群里面的一个节点处理，因此还需要提供一个集群里面的节点的ip和端口号，大概就是这个新来的是某个老节点推荐进去的这个意思
  + 使用`redis-cli -a <password> --cluster check <host>:<port>`来检查当前的集群节点状态，可以得到各节点的职责、ip、端口、节点id、分配的槽数等信息，需要提供一个主节点的ip和端口
  + 检查后发现新加进来的这个玩意没分配到槽，因此需要给它分配槽
  + 使用`redis-cli -a <password> --cluster reshard <host>:<port>`来给新来的分配槽，host和port写一个主节点的host和port就行
    + 首先它会问`How many slots do you want to move (from 1 to 16384)?`，写一个具体数值进去
    + 接下来问`What is the receiving node ID?`，把我们想扩容的redis服务器id写进去，，这个玩意可以通过上面的查看节点状态的代码运行结果得到
    + 接下来有个下图的玩意，它是让你选择要拨给刚才指定的id对应的主机哈希槽的源主节点，也就是指定都是谁出一些槽给新来的节点。写`all`意味着除该id对应的主节点，所有其它主节点都要匀一些槽出来给它
    ![集群扩容图例1](../文件/图片/Redis/集群扩容图例1.png)
    + 然后会输出一个plan,他会先规划好哪些槽位需要分配，然后问`Do you want to proceed with the proposed reshard plan (yes/no)?`，写yes
    + 等着分配完，分配完以后可以再`--cluster check`一下看看是不是分配成功了
  + 分配完槽位以后，再`redis-cli -a <password> --cluster add-node <addHost>:<addPort> <clusterHost>:<clusterPort> --cluster-slave --cluster-master-id <id>`，最后那个id是要认的master的id
  + 最后`redis-cli -a <password> --cluster check <host>:<port>`检查一下，确认扩容成功
+ **缩容**:
  + 缩容和扩容的步骤是相反的，也就是删掉从节点->主节点归还哈希槽->删去主节点
  + 使用`redis-cli -a <password> --cluster del-node <host>:<port> <id>`，host和port随便指一个节点，id指向需要删除的节点
  + 删完以后`redis-cli -a <password> --cluster check <host>:<port>`检查一下
  + 接下来归还哈希槽:`redis-cli -a <password> --cluster reshard <host>:<port>`
    + 基本上是与扩容的步骤一致的，只是选择哪些节点需要分槽时，只提供想删除的主节点id就行了，然后写入`done`继续
  + 归还完成以后，可以发现归还的主节点已经变成它归还的节点的从节点了
  + 最后删除该节点，完成

---

### （六）注意事项

+ **通识占位符**
  + 在集群下，如果各key不在同一个槽位下，无法使用`mset`、`mget`等操作
  + 可以通过`{}`来定义同一个组的概念，使key中{}内相同内容的键值对放到一个slot槽位去。如下图所示
  ![通识占位符图例](../文件/图片/Redis/通识占位符图例.png)

---

# Redis高阶

## 一、单线程与多线程

+ Redis的版本不同，其架构也是不同的，因此其是否支持多线程取决于Redis的版本。这也类似于JDK5才支持泛型，JDK8才支持lambda表达式一样
  + 在Redis6之前的版本，Redis都仅支持单线程，貌似Redis4版本新增了多线程异步删除的功能，但是没有实锤
  + Redis6公开支持了多线程

### （一）单线程特性

#### ①执行流程

+ Redis的网络IO和键值对读写是由一个线程来完成的，Redis在处理客户端的请求时包括获取 (socket 读)、解析、执行、内容返回 (socket 写) 等都由一个顺序串行的主线程处理，这就是所谓的“单线程”。这也是Redis对外提供键值存储服务的主要流程

![Redis单线程执行流程](../文件/图片/Redis/Redis单线程执行流程.png)

---

#### ②单线程快的原因

+ 基于内存操作:Redis的所有操作都是在内存中进行的，内存操作效率高
+ 数据结构简单:简单的数据结构的查找和操作的时间复杂度大部分都是O(1)，效率较高
+ IO多路复用和非阻塞IO:Redis通过IO多路复用来监听多个socket连接客户端，这样就能够通过一个线程来处理多个请求，减少线程切换带来的开销，同时也避免了IO的阻塞操作
+ 避免上下文切换:单线程不会出现多线程环境下出现的死锁问题，同时也不需要加锁，也避免了多线程竞争、线程切换带来的开销

---

#### ③使用单线程的原因

1. 使用单线程模型是Redis的开发和维护更简单，因为单线程模型方便开发和调试
2. 即使使用单线程模型也并发的处理多客户端的请求，主要使用的是IO多路复用和非阻塞IO
3. 对于Redis系统来说，主要的性能瓶颈是内存或者网络带宽而并非CPU

---

#### ④面临的问题

+ 单线程环境下如果想要删除一个对应的value非常大的key时，那么单线程的架构会使Redis线程进入卡顿状态，使其无法再响应后面到来的请求
+ 而等待了一段时间后该大Key才被删除，那么在高并发环境下，一个单线程的环境可能会处理不过来这么多请求从而导致越来越卡
+ 上面的问题的解决方案就是:
  + Redis4.0时新增了`unlink`、`flushdb async`、`flushall async`等命令或选项，使得Redis可以在删除key或清除库时，把这些操作分配给子线程来进行操作，从而减少主线程的阻塞时间，减少性能消耗和稳定性问题

---

#### ⑤IO多路复用

+ 在Unix网络编程中，目前存在五种IO模型
  + 阻塞IO（Blocking IO，简称BIO）
  + 非阻塞IO（NoneBlocking IO，即NIO）
  + IO多路复用（IO Multiplexing）
  + 信号驱动IO（signal driven IO）
  + 异步IO（asynchronous IO）
+ 其中的IO多路复用就可以实现一个服务端进程可以同时监听多个IO事件，从而处理多个文件描述符，即一个线程来处理多个客户端的连接
  + **文件描述符（FileDescriptor）**是对IO资源的抽象标识符，它用来标识一个IO资源，是一个非负整数值。当程序打开一个现有文件或者创建一个新文件时，操作系统内核就会向进程返回一个文件描述符，**这一概念主要适用于Unix、Linux操作系统**
  + IO多路复用会将用户socket对应的文件描述符(FileDescriptor)注册进epoll,然后epoll会帮助监听哪些socket上有消息到达，这样就避免了大量的无用操作。此时的socket应该采用非阻塞模式。这样，整个过程只在调用select、poll、epoll这些调用的时候才会阻塞，收发客户消息是不会阻塞的，整个进程或者线程就被充分利用起来，这就是**事件驱动模式**。
  + 单线程会通过记录跟踪每一个socket的状态来同时管理多个IO流，使得一个服务端进程可以同时处理多个连接请求，目的是尽量多的提高服务器的吞吐能力
+ 实现IO多路复用的模型有三种，可以分select->poll->epoll三个阶段来描述
+ 举例:
  + 一个监考老师(1个线程)负责监考30名学生(多个连接请求)，等待他们解答完毕后验收他们的试卷，老师可以选择下面的收卷方式:
  + 轮询:先收A的，再收B的，再收C的...，但是这中间只要有一个学生没写完，老师就要一直等下去(线程阻塞)，直到该学生写完才能收他的卷子
  + 1对1服务:老师不讲武德，叫来了其它监考老师（创建新线程），一个监考老师负责一个学生（一个分身线程处理一个请求）。
  + 响应式处理:老师就站在讲台前面等，学生写完以后自己举手（事件驱动），老师看到举手的学生后就把他的试卷收走。
  + 上面的举例中的三个解决方案，对应着单线程处理请求的三种解决方式，可以很明显的看出，轮询的方式效率太低，而且容易被阻塞，1对1服务实际上已经用了多线程了，但是请求数量变多时，对应的线程数量也会变多，在高并发请求下会占用大量的系统资源。而**通过事件驱动的响应式处理方式既能够保留单线程处理，又不至于被阻塞**

---

### （二）多线程特性

+ 从Redis6开始，Redis正式支持了多线程，但是它的“支持多线程”体现在使用多线程处理网络请求，但是对于读写操作的命令，Redis依然采用单线程进行处理，这是因为CPU一般并不是Redis出现瓶颈的原因，而内存和网络处理才是。另外继续使用单线程进行操作，就不需要保证考虑多线程在并发操作上带来的一系列问题了

#### ①执行流程

+ 由于Redis6才支持多线程，因此其执行流程已经整合了单线程使用的IO多路复用了
+ 它的执行流程分为四个阶段:
  + 第一阶段:服务端和客户端建立Socket连接，并分配处理线程
    + 主线程会接收建立连接的请求，获取Socket并把Socket放入全局等待队列，紧接着通过轮询的方式将Socket连接分配给IO线程
  + 第二阶段:IO线程读取并解析请求
    + 主线程把Socket分配给IO线程后，就会进入阻塞状态，等待IO线程完成客户端的请求读取和解析，由于有多个线程在并发处理，因此该过程很快就会完成
  + 第三阶段:主线程执行请求操作
    + 等待IO线程解析完请求，主线程会以单线程的方式执行这些请求操作。这里主线程可以利用IO多路复用的特性，哪个IO线程解析完了，它就执行哪个
  + 第四阶段:IO线程回写Socket和主线程清空全局队列
    + 主线程执行完请求操作以后，会把需要返回的结果写入缓存区，然后主线程会阻塞等待IO线程，把这些结果回写到Socket中并返回给客户端，IO线程回写Socket时，也是有多个线程在并发执行的，因此回写的速度也很快，等待IO线程回写Socket完毕，主线程就会清空全局队列，等待客户端的后续请求
+ 由于Redis的主线程利用了IO多路复用的技术，配合多线程将耗时的Socket的读取、解析和写入单独分配给子线程，使得主线程能够高效的处理多个连接请求
  + IO多路复用，简要来说就是利用单个线程去监听多个Socket，并在某个Socket可读、可写时得到通知，充分利用CPU资源。目前的IO多路复用都是用的epoll模式实现，它会在通知用户进程Socket就绪的同时，把已就绪的Socket写入用户空间，就不需要监听线程挨个遍历Socket来判断是否就绪了，提升了性能

![redis网络模型](../文件/图片/Redis/redis网络模型.png)

---

#### ②开启多线程支持

+ Redis虽然支持多线程，但是默认该配置是不生效的，需要我们主动去开启
  + `io-threads`配置项用于配置多线程的支持线程个数，线程个数并不是越大越好，一般要小于CPU核数，4核建议取值2-3，8核建议取值6
  + `io-thread-do-reads`配置项用于配置redis支持多线程，只有把该项置为yes才意味着多线程正式开启

---

## 二、BigKey问题

### （一）MoreKey问题演示

+ 在说明BigKey问题之前，先演示一下MoreKey问题，也就是键值对非常多的情况
+ 首先执行如下命令将插入100w条键值对数据命令写入到文件:

~~~bash
  for((i=1;i<=100*10000;i++)); do echo "set k$i v$i" >> /home/study/redis/operation/bigkey.txt ;done;
~~~

+ 接下来使用管道让Redis执行这100w条数据操作命令

~~~bash
  cat /home/study/redis/operation/bigkey.txt | redis-cli -a 123456 --pipe
~~~

+ 插入完毕后，使用`keys *`命令来输出这些键值对
  + 可以看到该命令的输出非常慢，这意味着在执行该命令时主线程会一直执行下去直到操作执行完毕，而在此期间其它请求无法执行操作
  + 这种情况对于我们来说是不想看到的，因为一个操作而使Redis在长时间陷入阻塞很明显会降低效率，而且可能会造成巨大损失

---

### （二）禁用危险命令

+ 上面的问题演示充分的表明了在键值对数量多的话，使用一些命令可能是危险的，我们希望把这些命令禁止掉
+ 通过配置文件的`rename-command`配置项可以配置想要禁止的命令，其原理就是把该命令重命名为一个空字符串，使其无法发挥效果
+ 例:

~~~bash
  # rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52
  #
  # It is also possible to completely kill a command by renaming it into
  # an empty string:
  #
  # rename-command CONFIG ""
  rename-command KEYS ""
  rename-command FLUSHDB ""
  rename-command FLUSHALL ""
~~~

---

### （三）scan命令

+ 禁用`keys *`命令以后，程序的安全性提升了，但是如果想要进行遍历的话，就会出现问题
+ Redis提供了`scan <cursor> [match <pattern>] [count <count>]`
  + cursor:游标值，当该值为0时表示遍历已结束，该值为非负整数值时表示下一个遍历集合的游标，下次遍历就需要使用上次遍历所提供的游标值。如果是开始遍历，那么游标值是0
  + pattern:匹配规则，用来过滤或者指定想查找的key
  + count:一次遍历想获得的数量，`scan`返回的结果集的数量会尽量向该值靠拢
  + `scan`命令会返回两个值，第一个值是下一次遍历需要的游标值，第二个值是数组，表示当前遍历出来的结果
+ 除了`scan`外，redis还提供了`sscan`、`zscan`和`hscan`等命令用来遍历set、hash和ZSet

|pattern通配符|作用|备注|
|:---:|:---:|:---:|
|`*`|匹配零个或多个字符|无|
|`?`|匹配单个字符|无|
|`[...]`|匹配括号内的任意一个字符|无|
|`[^...]`|匹配不在括号内的任意一个字符|无|

---

### （四）BigKey的判定与处理

#### ①判断BigKey

+ 一般的，我们认为key对应的值
  + 若是String类型，那么大于10Mb就是BigKey
  + 若是Hash、List、Set、ZSet类型，那么元素个数大于5000就是BigKey

---

#### ②产生与危害

+ BigKey的出现，一般都是随着事件的推移，该值变得越来越大导致的
+ BigKey的危害主要有
  + 内存不均，集群迁移困难
  + 超时删除，大Key在删除时阻塞线程导致其他操作一直等待
  + 造成网络流量的阻塞

---

#### ③发现与删除

+ 使用`redis-cli`的`--bigkeys`参数项可以输出每种数据结构最大的key，同时给出每种数据类型的键值个数+平均大小
+ 使用`memory usage`命令能够计算每个键值所占用的字节数
+ 如果想删除BigKey的话，那么需要分数据结构进行讨论:
  + String:一般使用`del`直接删，如果是BigKey,那么使用`unlink`
  + hash:使用`hscan`每次获取少量的hash内部的属性，然后将属性逐个删除，等属性全删完再删掉key
  + list:使用`ltrim`进行渐进式删除
  + set:使用`sscan`每次获取部分元素，再使用`srem`命令删除每个元素
  + zset:使用`zscan`命令每次获取部分元素，再使用`zremrangebyrank`命令删除每个元素

---

#### ④生产调优

+ 通过Redis4新增的`unlink`、`flushdb async`、`flushall async`命令可以以非阻塞的方式删除键或清空库
+ 另外，也可以通过设置lazy-free配置来进行惰性删除的相关配置
  + 下面的配置除最后两项外，其他项都是系统在隐式的进行删除key，它与用户显式调用命令不同，这些操作默认都是单线程阻塞删除数据的
  + `lazyfree-lazy-eviction`:当redis内存使用到达maxmemory并设置有淘汰策略时，是否采用非阻塞方式进行删除
  + `lazyfree-lazy-expire`:当redis内的键到达超时时间时，是否采用采用非阻塞方式进行删除
  + `lazyfree-lazy-server-del`:当执行像rename之类的指令时，它会隐式的删除对应的key，此时删除时是否以非阻塞方式删除
  + `replica-lazy-flush`:从机根据主机进行数据同步时，需要先调用`flushall`来清除自己的库，该配置用来决定从机在flush时是否以非阻塞方式删除
  + `lazyfree-lazy-user-del`:设置数据是否在调用`del`时要与调用`unlink`命令时的行为保持一致
  + `lazyfree-lazy-user-flush`:当用户执行`flushdb`、`flushall`等命令但未指定`async`或`sync`参数时，该配置项用来决定是否进行异步删除
+ `lazy-free`是一种内存管理策略，用于减少Redis在执行删除操作时的内存碎片化，并优化内存的使用效率。具体来说，使用该策略时，当前线程并不会释放该键所占用的内存空间，而是将其释放内存空间的任务交给后台的异步线程去处理
  + 该策略可以减少内存碎片化、提高程序执行的性能并简化内存管理，但是可能会导致内存占用增加

---


## 三、缓存双写一致性

### （一）缓存双写一致性的理解

+ 由于Redis一般是充当MySQL的缓存角色的，当MySQL中的字段数据需要发生变化时，Redis与MySQL中的数据就会发生数据不一致的现象，这是不可避免的
  + 如果Redis中有数据，那么它需要与数据库中的值相同
  + 如果Redis中没有数据，那么需要从MySQL中回写数据
+ 对于缓存来说，它按照功能可以细分为两种:
  + 只读缓存:缓存中的数据都是提前写死的，不能写只能读。但是该类型的缓存并不是主流，使用Redis的厂家一般也不会用这种方式进行缓存
  + 读写缓存:
    + 同步直写:更新完数据库以后，Redis缓存也要同步修改
    + 异步缓写:业务容许数据库数据发生变动一定时间以后再作用于Redis,如仓库、物流系统等，此时如果出现异常就需要进行重试重写，必要时使用kafka、RabbitMQ等中间件配合操作
+ 对于程序来说，它需要先查看Redis中是否存在缓存
  + 如果存在直接拿去用
  + 如果没有需要从MySQL拿，并将数据回写到Redis
  + 为了实现这个功能，需要使用**双检加锁策略**进行数据的读取:

<a id="doubleCheckAndLock"></a>

~~~java
  // 搞一个锁
  ReentrantLock lock=new ReentrantLock();
  ...
  String value=redisTemplate.opsForValue().get(key);  // 尝试从Redis中获得数据
  // 如果是空的，那么加锁同步
  if(value==null){
    lock.lock();
    // 第二次检查redis中的数据是否为空
    value=redisTemplate.opsForValue().get(key);
    // 如果还是空的，那么从MySQL中获得数据，并将数据回写到redis中
    if(value==null){
      value=dao.getInfo();
      redisTemplate.opsForValue().setIfAbsent(key,value,7L,TimeUnit.DAYS);
      return value;
    }else{
      return value;
    }
  }
  // 这里是value第一次获得就不为空的情况，那么直接返回
  return value;
~~~
  + 双检加锁机制的第一次检查如果为非空，那么说明服务器在处理其它请求时就已经把数据回写到redis了，或者redis本来就有数据。如果是第一种情况，那么就意味着回写后接下来的请求不需要再获取锁进行同步处理了，直接返回值即可，这是第一次检查的作用，它可以避免线程在其它线程会写完毕后还要同步执行降低效率
  + 第二次检查时已经进入了同步执行了，此时执行的线程可能是第一个获取到锁的，也可能是第一次检查发现redis里面没有数据后等着获取锁的线程，当其它线程同步执行完毕后获取到了锁。如果是第一种情况，那么它就意味着第二次检查肯定也是空的，那么它会回写到redis中，接下来等待获取锁的一众线程获取到锁以后，再次第二次检查就发现redis有数据了，直接返回。第二次检查可以避免多个线程重复的回写数据库中的数据，提高并发执行的效率
+ 目前业界对于缓存一致性的问题，主要有下面的解决方案:
  + 同步删除
  + 延时双删
  + 监听binlog日志删除
  + 三重删除

---

### （二）缓存一致性同步更新策略

+ [参考](https://blog.csdn.net/weixin_45433817/article/details/137750937)
+ 主要有四种更新策略:
  + **先更新数据库，再更新缓存**
    + 异常情况1:更新完毕了数据库，但是数据库中的数据回写Redis出现异常失败了，此时会导致Redis和MySQL的数据出现不一致
    + 异常情况2:高并发环境下，2个线程交替执行，线程A将某MySQL字段更新为了90，而此时时间片轮转到了线程B，线程B将MySQL字段更新为了80，然后把数据回写到了Redis，时间片再次轮转到线程A，线程A也把它事务的字段值回写到了Redis，此时Redis中的值为90，MySQL中的值为80
  + **先更新缓存，再更新数据库**
    + 其实是和上面更新策略的异常情况2一样的问题，也会导致数据的不一致，因为无法控制线程执行的具体先后顺序
    + 另外并不推荐该方式，因为MySQL这种关系型数据库一般都作为业务的底单数据库，其数据具有最终解释性
  + **先删除缓存，再更新数据库**
    + 该策略的执行步骤是这样的:
      + 对于有写操作的线程来说，需要先删除缓存，然后更新数据库
      + 对于只有读操作的线程来说，如果缓存为空，就要从数据库里面取值，然后回写redis
    + 异常情况:也是2个线程交替执行，线程A已经删掉了缓存，然后更新了数据库但是还没有commit,此时时间片轮转到线程B执行，线程B来看缓存没有，因为已经被线程A删了，然后就去数据库读，此时线程A还未完成commit，**线程B通过MVCC的ReadView拿到了线程A修改之前的数据，然后把它回写到了redis**。时间片再转到线程A，线程A完成了commit,此时MySQL的数据是已经修改完成的，但是Redis里面却是修改之前的，而且会一直这样下去，因为两个线程都执行完了
    + 该异常情况实际上是可以解决的，解决方案就是**延时双删策略**
      + 延时双删策略就是线程A在删除掉redis缓存并执行完更新以后，休眠一定时间等待线程B执行完毕，再删一次redis的缓存。这样就可以保证其它线程在读取数据时依然会发现redis缓存为空，就会去数据库会写数据，此时回写的数据就是正确的了
  + **先更新数据库，再删除缓存**
    + 该策略的执行步骤是这样的:
      + 对于有写操作的线程来说，需要更新数据库，再删除缓存
      + 对于只有读操作的线程来说，如果缓存为空，就要从数据库里面取值，然后回写redis
    + 异常情况1:也是2个线程交替执行，线程A正在更新数据库，时间片轮转到线程B执行，此时线程B发现有缓存，因此取走了缓存的旧值，接下来又轮到线程A执行，线程A执行完毕。该异常情况主要是更新线程来不及删除缓存时，其它线程可能会取走缓存旧值，但是**该情况并不致命，因为等缓存删完以后数据就会重新一致**
    + 异常情况2:2个线程交替执行，线程A需要更新两次数据库，而且这是两个事务，它第一次更新完数据库并删除缓存后，轮到了线程B执行，线程B此时发现没有缓存，就读取了MySQL中的数据，再轮转到线程A执行，线程A第二次更新数据库并删除缓存，完成了它的工作，最后线程B将读取到的数据回写到Redis。我们发现，线程B回写的数据与MySQL最终持有的数据。**该情况虽然问题较为严重，但是出现的概率极低**
    ![Redis先更新数据库再删除缓存异常情况图例2](../文件/图片/Redis/Redis先更新数据库再删除缓存异常情况图例2.png)
    + 该异常情况的解决方案就是借助其它消息中间件如RabbitMQ，将要删除的缓存值或者是要更新的数据库值暂存到消息队列中，当程序更新失败时，就从消息队列中重新拿对应的值尝试更新，成功了就把他们从消息队列中除去。如果重试次数到达一定数量还未成功，就向业务层报告错误，通知运维人员手动处理
    ![缓存一致性先更新MySQL再删除缓存异常解决方案图例](../文件/图片/Redis/缓存一致性先更新MySQL再删除缓存异常解决方案图例.png)
    + 该策略为了保证数据一致性，实际上是牺牲了一定的时间，因此具有一定的延后性
      + 如流量充值可能先发短信，实际充值可能滞后5分钟
+ 结合四种更新策略，推荐的策略是**先更新数据库，再删除缓存**
  + 首先是更新数据库或者先更新缓存，再更新另一方，都可能出现严重的数据不一致现象，而且该异常之后如果没有手动干预就会一直这样下去
  + 使用先删除缓存，再更新数据库这一策略时，想解决该异常需要进行延时双删，但是**延时双删的线程等待时间不好计算**
  + 而使用**先更新数据库，再删除缓存**的策略虽然会在短时间内出现数据不一致现象，但是之后是可以自动纠正过来的（不出现异常的情况下）
+ 如果一定要实现数据的读取一致性的需求，那么就需要在更新数据库时，暂停Redis对客户端的并发读请求，等待更新完毕并删除缓存后再允许并发读请求继续，这理论上可以达到该效果，但**实际上，分布式难以达到实时一致性，一般都是争取实现最终一致性**。

![缓存一致性四种更新策略对比](../文件/图片/Redis/缓存一致性四种更新策略对比.png)

---

### （三）监听binlog日志删除缓存策略

#### ①策略详情

+ [参考](https://blog.csdn.net/weixin_45433817/article/details/137750937)
+ 该策略通过监听MySQL的binlog日志来进行缓存的删除，**这也是目前解决缓存和数据库不一致问题的主流方案**
![监听binlog删除缓存图例](../文件/图片/Redis/监听binlog删除缓存图例.png)
+ **异常情况**:
  + 假设表A在进行数据库拆分，部分读新库、部分读老库、增量数据同步双写，在灰度切读阶段，我们还是优先保障老库的流程，因此还是先写老库，如果写压力比较大，数据同步可能需要达到几秒，因此会出现以下问题:
  ![监听binlog删除缓存的异常情况图例1](../文件/图片/Redis/监听binlog删除缓存的异常情况图例1.png)
  + 不过该问题出现的概率极低，因此**整体来说该方案没有太大问题**
+ **缺点**:
  + **脏数据窗口时间相比于同步删除(先更新库再删除缓存)来说较长**。在收到binlog之前，他中间要经过：binlog从主库同步到从库、binlog从库到binlog监听组件、binlog从监听组件发送到MQ、消费MQ消息，这些操作都是有一定耗时的，可能是几十毫秒甚至几百毫秒。而同步删除可能1毫秒就搞定了
  + 该方案强依赖于监听binlog的组件，**如果该组件出现故障，则会导致大量脏数据**。

---

#### ②canal安装与启动


+ canal是阿里开源的产品，其主要用途是基于MySQL数据库增量日志解析，提供增量数据订阅和消费，它就是使用的该策略来实现缓存双写一致性的
+ 利用canal，我们可以实现Redis缓存与MySQL之间数据变动的双写一致性
+ [github地址](https://github.com/alibaba/canal)
+ 这里以CentOS配合JDK17演示如何安装与启动canal:
  + 首先[下载](https://github.com/alibaba/canal/releases)一个版本，这里演示时下载的是v1.1.6版本
  + 下载`canal.developer-1.1.6-xxxxx.tar.gz`包到CentOS系统内
  + 解压
  + 打开其目录下的`/conf/example/instance.properties`文件，编辑如下配置(建议在编辑前备份一份原文件):

~~~java
  // 下面配置都在instance.properties里面存在，直接找一下就能找到
  // 该配置写要连接的MySQL路径，为host+port，例:8.96.133.25:3306
  canal.instance.master.address
  // 该配置写连接MySQL需要使用的用户名
  canal.instance.dbUsername
  // 该配置写连接MySQL需要用到的密码
  canal.instance.dbPassword
~~~
  + 接下来进入`/bin`目录，执行`./startup.sh`运行canal。如果想重启，那么执行`restart.sh`，如果想停止，执行`stop.sh`
  + 运行完成后可能会出现如下输出:

  ![canal启动图例1](../文件/图片/Redis/canal启动图例1.png)
  + 接下来去`/logs/canal`中查看`canal.log`日志来检查是否开启成功，日志文件输出为下图表示启动成功
  ![canal启动图例2](../文件/图片/Redis/canal启动图例2.png)
  + 然后去`/logs/example`中查看`example.log`的输出:
  ![canal启动图例3](../文件/图片/Redis/canal启动图例3.png)
  + 若输出不一致，详见[可能出现的问题](#canalProblem)

---

#### ③Java的canal业务程序Demo

+ [pom.xml](../../java/源码/Redis/pom.xml)
+ [配置文件](../../java/源码/Redis/Redis-Canal-Demo/src/main/resources/application.properties)
+ [程序demo](../../java/源码/Redis/Redis-Canal-Demo/src/main/java/com/example/redis/example/RedisCanalExample.java)
+ [RedisUtils工具类](../../java/源码/Redis/Redis-Canal-Demo/src/main/java/com/example/redis/utils/RedisUtils.java)
+ 对于connect.subscribe方法，其可以通过正则表达式来进行canal需要监听的MySQL表的区分

~~~
  mysql 数据解析关注的表，Perl正则表达式.
  多个正则之间以逗号(,)分隔，转义符需要双斜杠(\\) 
  常见例子：
  1.  所有表：.*   or  .*\\..*
  2.  canal schema下所有表： canal\\..*
  3.  canal下的以canal打头的表：canal\\.canal.*
  4.  canal schema下的一张表：canal.test1
  5.  多个规则组合使用：canal\\..*,mysql.test1,mysql.test2 (逗号分隔)
~~~


<a id="canalProblem"></a>

#### ④可能出现的问题

+ **Unrecognized VM option 'AggressiveOpts' Error: Could not create the Java Virtual Machine. Error: A fatal exception has occurred. Program will exit.**
  + 出现该问题的原因是Java版本不对，其根本原因是JVM的AggressiveOpts参数项在JDK11时被弃用，并在JDK13的版本及以后的版本中如果出现该参数项导致JVM无法启动
  + 解决方法有两个
    1. 改变Java版本，推荐改成JDK8这种稳定版本
    2. 修改`startup.sh`内的代码（**推荐**），只需要查找一下`AggressiveOpts`出现的位置，然后把该参数项删除就可以了，接下来再启动就不会报错了
+ **日志文件显示详细的报错信息在`/bin/xxxx.log`文件内，找到该文件并输出，在开头看到了下面的话**

  ~~~log
    # There is insufficient memory for the Java Runtime Environment to continue.
    # Native memory allocation (mmap) failed to map 2147483648 bytes for committing reserved memory.
    # Possible reasons:
    #   The system is out of physical RAM or swap space
    #   The process is running with CompressedOops enabled, and the Java Heap may be blocking the growth of the native heap
    # Possible solutions:
    #   Reduce memory load on the system
    #   Increase physical memory or swap space
    #   Check if swap backing store is full
    #   Decrease Java heap size (-Xmx/-Xms)
    #   Decrease number of Java threads
    #   Decrease Java thread stack sizes (-Xss)
    #   Set larger code cache with -XX:ReservedCodeCacheSize=
    #   JVM is running with Zero Based Compressed Oops mode in which the Java heap is
    #     placed in the first 32GB address space. The Java Heap base address is the
    #     maximum limit for the native heap growth. Please use -XX:HeapBaseMinAddress
    #     to set the Java Heap base and to place the Java Heap above 32GB virtual address.
    # This output file may be truncated or incomplete.
    #
    #  Out of Memory Error (os_linux.cpp:2790), pid=30393, tid=30394
    #
    # JRE version:  (17.0.11+7) (build )
    # Java VM: Java HotSpot(TM) 64-Bit Server VM (17.0.11+7-LTS-207, mixed mode, sharing, tiered, compressed oops, compressed class ptrs, g1 gc, linux-amd64)
    # No core dump will be written. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
  ~~~
  + 这段话的意思是在JVM启动时内存无法给JVM分配这么大的内存空间，像此处报错就是在声明无法为JVM分配2G内存空间
  + 解决方法也很简单，修改JVM需要的内存空间，也是在`startup.sh`文件中改
  ![canal启动报错图例1](../文件/图片/Redis/canal启动报错图例1.png)

---

### （四）三重删除策略

+ [参考](https://blog.csdn.net/weixin_45433817/article/details/137750937)
+ 无论是先更新数据库再删缓存，还是延时双删，还是通过监听MySQL的binlog日志，有大大小小的问题。三重删除策略则是根据这些方案的基础上一步步优化过来的
+ 采用三重删除策略，首先先更新数据库然后删缓存（一删），接着通过监听MySQL的binlog日志推送消息到消息中间件，让消费者读取消息删除缓存（二删），同时还发送延迟校验的消息，消费者在收到消息后，再进行缓存与数据库的一致性校验，在修复脏数据以后，删除缓存（三删）。
+ 与其它解决方案相比，该方案的链路更长，不过从解决缓存双写一致性问题的角度来看，该方案是最佳方案
![三重删除策略图例1](../文件/图片/Redis/三重删除策略图例.png)

---

## 四、布隆过滤器

+ 布隆过滤器于1970年由布隆提出，是一种非常节省空间的概率数据结构，**运行速度快，占用内存小，但是有一定的误判率且无法删除元素**
  + 特点:
    + 如果布隆过滤器判断某个数据不在过滤器中，那么肯定不在
    + 如果布隆过滤器判断某个数据在过滤器中，那么可能会在。
    + 如果数据在布隆过滤器中，那么布隆过滤器肯定能够判断出来
    + 如果数据不在布隆过滤器中，那么布隆过滤器不一定判断成功
  + 优点:
    + 支持海量数据场景下高效判断元素是否存在
    + 存储空间小，并且节省空间，不存储数据本身，仅存储hash结果取模运算后的位标记
    + 不存储数据本身，比较适合某些保密场景
  + 缺点:
    + 不存储数据本身，所以只能添加但不可删除，因为删掉元素会导致误判率增加
    + 由于存在hash碰撞，匹配结果如果是“存在于过滤器中”，实际不一定存在
    + 当容量快满时，hash碰撞的概率变大，插入、查询的错误率也就随之增加了
  + 问题:
    + 不支持计数，同一个元素可以多次插入，但效果和插入一次相同
    + 由于错误率影响hash函数的数量，当hash函数越多，每次插入、查询需做的hash操作就越多

### （一）数据结构

+ [参考](https://blog.csdn.net/wuhuayangs/article/details/121830094)

+ 布隆过滤器是由一个固定大小的二进制向量或者位图（bitmap）和一系列映射函数组成的
+ 对于长度为 m 的位数组，在初始状态时，它所有位置都被置为0，如下图所示
![布隆过滤器图例1](../文件/图片/Redis/布隆过滤器图例1.png)
+ 当一个元素加入布隆过滤器中的时候，会进行如下操作:
  + 使用布隆过滤器中的哈希函数对元素值进行计算，得到哈希值（有几个哈希函数得到几个哈希值）
  + 根据得到的哈希值，在位数组中把对应下标的值置为1
  + 如下图所示:
  ![布隆过滤器图例2](../文件/图片/Redis/布隆过滤器图例2.png)
  + 接着再添加一个值 “xinlang”，哈希函数的值是3、5、8，如下图所示:
  ![布隆过滤器图例3](../文件/图片/Redis/布隆过滤器图例3.png)
+ 当布隆过滤器查询元素时
  + 对给定元素再次进行相同的哈希计算
  + 得到哈希值之后判断位数组中的每个元素是否都为 1，如果值都为 1，那么说明这个值存在布隆过滤器当中，如果存在一个值不为 1，说明该元素不在布隆过滤器中
  + 例如我们查询 “cunzai” 这个值是否存在，哈希函数返回了 1、5、8三个值，如下图所示:
  ![布隆过滤器图例4](../文件/图片/Redis/布隆过滤器图例4.png)
  + 结果得到三个 1 ，说明 “cunzai” 是有**可能存在**的，这是因为哈希函数可能会出现哈希碰撞，即使用相同的哈希函数计算不同数据的哈希值时，它们的哈希值可能是相同的，典型的例子就是HashMap得数据结构是数组+链表+红黑树而不是单纯的数组，因为哈希碰撞的原因数组得对应下标可能因为哈希碰撞而导致多个值映射到同一个下标，因此才会有链表和优化其结构的红黑树
  + 如下图所示:
  ![布隆过滤器图例5](../文件/图片/Redis/布隆过滤器图例5.png)
  + 当向布隆过滤器添加的元素数量大于其声明的容量时，哈希碰撞的概率会越来越大，插入、查询的错误率也就随之增加了。因此**使用时最好不要让实际元素的数量大于初始化数量，一次性给够避免扩容，当实际元素超过初始化数量时，应该对布隆过滤器进行重建**，重新分配一个初始化容量更大的布隆过滤器，再将历史元素批量添加进去
+ 当想删除布隆过滤器中的元素时，这是肯定不可以的，因为删除会出现问题，比如上面添加元素的bit位5被两个变量的哈希值共同覆盖的情况下，一旦我们删除其中一个值。例如`xinlang`而将其置位0，那么下次判断另一个值例如`baidu`是否存在的话，会直接返回false，而实际上我们并没有删除它，这就导致了误判的问题

---

### （二）应用场景

+ 解决缓存穿透问题，和redis结合bitmap使用
  + 缓存穿透指
+ 黑名单校验，识别垃圾邮件
+ 安全连接网址，全球上10亿的网址判断

---

## 五、缓存问题

![缓存问题总结](../文件/图片/Redis/缓存问题总结.png)

### （一）缓存预热

+ `缓存预热`指根据业务分析或统计数据，确定热点数据（经常被访问的数据），并将其提前加载到缓存中的策略。这样，在实际请求到达程序时，热点数据已经存在于缓存中，从而减少了缓存穿透和缓存击穿的情况，也缓解了SQL服务器的压力。
+ 我们可以使用@PostConstruct注解来在项目启动时加载一些缓存数据

---

### （二）缓存雪崩

+ `缓存雪崩`指缓存中的大量数据失效或者缓存服务器宕机，导致大量请求直接打在数据库上，引起数据库压力过大甚至宕机
+ 它的解决方式有多种:
  + 设置key不过期
  + 多缓存结合预防雪崩，可以使用ehcache+Redis结合的方式（ehcache是一个Java缓存框架，它可以将缓存数据存放于JVM中的内存中来实现本地缓存，相比于Redis缓存，其性能和响应速度更优秀）。即添加多级缓存
  + 利用Redis集群保证高可用
  + 使用服务降级策略，如利用Hystrix或Sentinel实现服务降级，避免对数据库的多次访问
  + 使用阿里云-Redis云数据库，**人民币玩家限定**

---

### （三）缓存穿透

+ `缓存穿透`指当缓存没有某个数据时，导致大量请求去访问数据库，然而数据库也没有该数据，此时请求直接都去访问数据库，仿佛缓存根本不存在一样给数据库带来压力。
+ 缓存穿透是黑客的攻击手段之一，它的解决方法有:
  + 如果数据库也没有，也回写数据，使数据对应value的值为null，这样再有请求过来，就可以直接使用缓存的数据了
    + 但是如果是黑客蓄意攻击，那么它可能每次请求的数据都是不一样且缓存和数据库中都没有的，再搭配上高频率的请求，不仅Redis因为不断地数据库回写导致占用的内存越来越多，而且数据库还是会直接被大量的请求访问
  + 使用布隆过滤器来在请求到达缓存前先执行一次过滤，如果通过过滤再放行，不通过直接拦截住并返回，[使用Guava的布隆过滤器的样例](../../java/源码/Redis/Redis-BloomFilter-Demo/src/main/java/com/example/redis/demo/GuavaBloomFilterDemo.java)
  ![Guava布隆过滤器源码简单分析图例1](../文件/图片/Redis/Guava布隆过滤器源码简单分析图例1.png)

|归属|方法|参数|描述|返回值|返回值类型|异常|备注|样例|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**com.google.common.hash.BloomFilter<T>(Guava依赖包下的类)**|`create(Funnel<? super T> funnel, long expectedInsertions, double fpp)`|funnel:与类的泛型T的详细类型一致的过滤策略，可以通过Funnels工具类提供<br>expectedInsertions:声明该布隆过滤器能够容纳的数据数量<br>fpp:声明容错率，容错率越低，占用的内存开销和使用的hash函数越多，因此时间和空间占用就越多|创建一个布隆过滤器对象|>|BloomFilter|无|无|[样例](../../java/源码/Redis/Redis-BloomFilter-Demo/src/main/java/com/example/redis/demo/GuavaBloomFilterDemo.java)|
|^|`put(T object)`|object:想加入的数据|向布隆过滤器新添加一个数据|若布隆过滤器的比特位发生了变化那么返回true|boolean|无|无|^|
|^|`mightContain(T object)`|object:想检验的数据|使用布隆过滤器检验对应数据是否存在|存在返回true|boolean|无|无|^|

---

### （四）缓存击穿

+ `缓存击穿`指当缓存中的某个数据过期以后，大量访问该数据的请求去直接访问数据库，该情况被称为缓存击穿
+ 该问题的解决方案有:
  + 差异化失效时间:使用两层缓存，例如热点数据每1分钟刷新一次，A缓存用来在热点未刷新时充当常规缓存，B缓存用来在A缓存被删除但还未回写到缓存时，如果此时有请求来访问，延迟更新的B缓存将临时充当缓存的作用
  ![缓存击穿差异化失效时间解决方案图例](../文件/图片/Redis/缓存击穿差异化失效时间解决方案图例.png)
  + 互斥更新，采用双检加锁策略，双检加锁的代码详见[缓存一致性](#doubleCheckAndLock)
  + 逻辑过期:也是用锁，与互斥更新的区别是获取不到锁会返回旧数据
  ![逻辑过期图例](../文件/图片/Redis/逻辑过期图例.png)

---

### （五）缓存淘汰策略

+ 当Redis的key过期时，也需要对应的缓存过期淘汰策略来决定如何将这些key删除
+ 当Redis的内存容量达到maxMemory时，Redis会根据设置的缓存淘汰策略来删除一些它认为的“不重要的”key。引起Redis内存容量达到maxMemory的原因有很多，其中就有**缓存过期淘汰策略可能无法将过期的key全部删除**这一原因
+ 因此，这里会分别描述缓存过期淘汰策略和缓存淘汰策略

#### ①触发前置要求

+ Redis的缓存淘汰策略的触发是需要一定的要求的，即当Redis的内存容量达到maxMemory时，策略才会被执行。否则一般情况下Redis并不会去执行淘汰策略
  + 配置文件的`maxmemory`配置项以及`config set maxmemory <value>`分别是通过配置文件和命令的方式来修改当前Redis的maxMemory的值，使用命令修改仅在本次Redis服务运行时生效，重启就不会生效了
  + 通过`config get maxmemory`可以得到当前的maxMemory的值，默认的值是0，该值为0时，在64位操作系统下表示不限制内存大小，而在32位操作系统下则表示最大为3Gb
  + 通过`info memory`和`config get maxmemory`可以查看当前Redis的内存使用情况
+ 而缓存过期淘汰策略的前置很简单，就是key过期

---

#### ②过期淘汰策略

+ 过期淘汰策略有三种:
  + 直接删除:该删除方式是以阻塞线程的方式删除的，因此可能会导致删除阻塞时间过长影响后端业务代码执行
  + 惰性删除:该删除方式是key在过期后并不立即删除，而是有请求再访问的时候再将对应的key删除，好处是可以减少直接删除带来的阻塞影响，坏处是如果过期以后再也没有请求尝试访问该key，那么该key就永远也不会删除了
  + 定期删除:就是定时删除，到时间随机抽取部分过期了的key然后删除
+ 在Redis7的版本，Redis默认使用定期删除+直接删除的方式来释放key，理由是控制异步删除过期key的`lazyfree-lazy-expire`值为`no`以及`dynamic-hz`配置为`yes`且`hz`配置项默认值为10。**这里并不是很确定，因为只是单从配置文件方面来看的，网上有很多帖子都说Redis默认使用定期删除+异步删除的方式来释放key，但仅仅是提了一嘴，并没有给出详细的配置默认值证据，如[这个](https://blog.csdn.net/ldw201510803006/article/details/126093439)、[这个](https://blog.csdn.net/weixin_42201180/article/details/129150967)跟[这个](https://blog.csdn.net/weixin_70757494/article/details/137108047)**
+ 由于直接删除对于高并发环境下难以接受，因此目前一般都使用惰性删除+定期删除配合使用的，但是这样就会导致可能出现key过期了但长期未删除导致Redis可用的内存空间进一步减少，时间一长，Redis占用内存就会逼近maxMemory配置项。为了解决这一问题，Redis提供了**内存淘汰策略**

---

<a id="maxmemoryPolicy"></a>

#### ③内存淘汰策略

+ 内存淘汰算法:
  + LRU算法:最近最少引用算法，即它仅会比较各个值最近一次被访问的时间来决定谁被淘汰，时间戳小的被淘汰
  ![LRU算法图例](../文件/图片/Redis/LRU算法图例.png)
  + LFU算法:最不常使用算法，即它会比较各个值全局的被访问频率，频率最低的被淘汰，若两个key频率一样，那么淘汰时间戳小的数据
  + TTL算法:删除所有过期的值
  + random算法:随即删除

+ 当前Redis提供的缓存淘汰策略有:
  + noeviction:摆烂，再碰到引起内存超标的命令都会返回error
  + allkeys-lru:对所有的key使用LRU算法进行删除，优先删除掉最近最不常使用的key，用以保存新数据
  + volatile-lru:对所有设置了过期时间的key使用LRU算法进行删除
  + allkeys-random:对所有key随机删除
  + volatile-random:对所有设置了过期时间的key随机删除
  + volatile-ttl:删除马上要过期的key
  + allkeys-lfu:对所有key使用LFU算法进行删除
  + volatile-lfu:对所有设置了过期时间的key使用LFU
+ 我们可以通过手动设置配置文件的`maxmemory-policy`配置项的值来设置Redis的内存淘汰策略
+ 也可以通过`config set maxmemory-policy`来设置此次运行时Redis的内存淘汰策略
+ 对于缓存淘汰策略的选择:
  + 如果所有的key都最近总被使用，可以选择`allkeys-lru`策略
  + 如果所有的key的访问概率都差不多是相同的，那么可以考虑使用`allkeys-random`策略
  + 如果对数据有足够的了解，能够为key指定过期时间，那么可以选择`volatile-ttl`策略

---

## 六、分布式锁

+ Redis除了用来充当缓存，还可以当分布式锁来使
+ 当存在不同的微服务来使用相同的资源时，在高并发环境下可能会导致该资源的值出现不满足一致性的情况，而此时这些不同的微服务并不在同一个JVM上执行，就导致synchronized和JUC的锁没办法对它们进行同步，因为这些东西只能使在同一个JVM内执行操作的线程同步
+ 为了解决该问题，我们需要寻找一个多个JVM都可以访问到的相同的互斥资源作为锁来使这些微服务实现同步，Redis就可以充当该角色，因此Redis除作为缓存外还能够实现分布式锁的功能

### （一）分布式锁的实现

+ 想实现分布式锁，需要满足以下几点:
  + 具有互斥性，即同一时间内只能有一个微服务的一个线程持有该锁
  + **实现JUC的Lock接口的规范**
  + **加锁时自旋与设置超时时间**，用以避免出现服务持有锁后宕机以及考虑其它线程未能抢到锁的情况
  + **使用Lua脚本保证上锁和解锁的原子性**
  + 上锁后，为了避免线程在持有锁期间未执行完的情况，在锁快过期时需要创建一个守护线程来自动为**锁续期**
  + **解锁时只能删自己的锁**，不能删别人的锁
  + 如果需要**满足可重入锁特性**，那么需要锁为hash类型，它至少需要记录该锁当前被持有的量以及持有当前锁的线程和服务信息。如果仅仅只是为了达到分布式锁的目的，那么String类型就够了
  + 为了使锁具备拓展性，可以利用工厂模式来根据参数指定要得到的锁对象
+ [分布式锁样例(尚硅谷版)](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/customer/AtguiguRedisDistributedLock.java)
+ [分布式锁工厂样例(尚硅谷版)](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/customer/AtguiguDistributedLockFactory.java)
+ [分布式锁样例(自己改的)](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/customer/RedisDistributedLock.java)
+ [分布式锁工厂样例(自己改的)](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/customer/DistributedLockFactory.java)

---

### （二）Redisson

+ Redisson是一个为开发者提供了大量关于Redis分布式特性的常用工具类的框架，在分布式锁方面，它实现了Redis官方的RedLock算法

#### ①RedLock算法

+ 上面我们自己自定义的分布式锁都有一个很明显的缺点，这个缺点并不是我们的代码导致的，而是Redis本身导致的
  + 如果我们把锁放在了一个主从架构的集群中，那么当程序获取锁并将锁放在主机中，此时主机宕机，而从机还未从主机同步对应数据时，就会出现**程序认为自己持有锁，但是新上任的主机并未持有该锁的数据的情况**，此时再有线程来获取锁，由于新上任的主机并没有之前锁的数据，导致该线程也获取到了锁，这就导致分布式锁的排他性无法被保证，从而导致严重的并发问题
  ![集群环境下分布式锁面临的问题](../文件/图片/Redis/集群环境下分布式锁面临的问题.png)
+ 基于上面的问题，Redis之父提出了RedLock红锁算法:
  + 时间的单位为毫秒
  + 其实就是把一些Redis从集群中抽离出去，并使它们每个都是单独的主机（即他们与其它redis服务并不构成主从、集群等任何关系，他们都是独立的），在程序获取锁时，需要从这些独立的主机去申请获得锁，这些主机都要申请，且每次申请的key和其值都是相同的。**当许可给程序锁的主机数量占这些独立的主机总量达到一定程度(设主机数量为n，容错的最大机器数x一般为x=(n-1)/2，因此总数推荐为奇数)且有效时间合法时，才算上锁成功**
  + 尝试获取锁时，考虑到这些主机可能会出现故障导致无法响应，需要划定一个超时获取锁的时间，它可以防止程序在某一主机上申请的锁花费时间过长的情况。另外，**程序需要计算出来向每个主机申请锁需要花费的时间，并最终将初始有效时间与花费时间的总数相减，最终得到实际程序持有锁的有效时间**
  + 如果程序最终被判定为未持有锁，那么**它需要尝试向所有主机释放锁**（即使有的主机未分配给它锁）

---

#### ②Redisson分布式锁的使用

+ Redisson对象的创建[参考此处](https://github.com/redisson/redisson/wiki/2.-Configuration/#22-declarative-configuration)的单实例配置和集群配置
+ 业务代码的编写[参考此处](https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers/#81-lock)
+ **实际上，RedLock现在已经被Redisson官方标记为过时且不推荐使用了，此处示例其实使用的是RLock**
+ [Config配置代码示例](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/config/RedisConfig.java)
+ [Controller层代码示例](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/controller/RedisDistributedLockController.java)
+ [Service层代码示例](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/service/RedLockService.java)
+ 测试时，可能会出现如下报错:
![Redisson报错](../文件/图片/Redis/Redisson报错.png)
  + 如果出现报错，那么需要把finally代码块的内容改为[Service层代码示例](../../java/源码/Redis/Redis-DistributedLock-Demo/src/main/java/com/example/redis/service/RedLockService.java)所展示的那样

---

## 七、五大类型源码及底层实现

### （一）基础结构

+ Redis的具体结构如下图所示:
![Redis结构图例1](../文件/图片/Redis/Redis结构图例1.png)
+ Redis有16个数据库，每个数据库主要有两个dict类，一个dict用来记录失效时间，另一个用来存储数据，此处重点说明用来存储数据的dict
  + 存储数据的dict有两个指针，一个指向dictType，表示dict的具体类型，另一个指向dictht类型的数组
  + dictht又有一个双重指针指向哈希表，哈希表中的每个元素都是dictEntry类型的元素
  + 最终dictEntry通过指针指向key与value，其中key是redis自定义的字符串类型，而value是redisObject类型
  + dict和dictType的结构位于dict.h，dictEntry的结构位于dict.c，redisObject的结构位于server.h
  ![redis结构关系图例](../文件/图片/Redis/redis结构关系图例.png)
+ 下面的代码截取时版本较新，dictHT貌似在截取时已经被移除了

~~~c
  typedef struct redisDb {
    kvstore *keys;              /* The keyspace for this DB */
    kvstore *expires;           /* Timeout of keys with a timeout set */
    ebuckets hexpires;          /* Hash expiration DS. Single TTL per hash (of next min field to expire) */
    dict *blocking_keys;        /* Keys with clients waiting for data (BLPOP)*/
    dict *blocking_keys_unblock_on_nokey;   /* Keys with clients waiting for
                                             * data, and should be unblocked if key is deleted (XREADEDGROUP).
                                             * This is a subset of blocking_keys*/
    dict *ready_keys;           /* Blocked keys that received a PUSH */
    dict *watched_keys;         /* WATCHED keys for MULTI/EXEC CAS */
    int id;                     /* Database ID */
    long long avg_ttl;          /* Average TTL, just for stats */
    unsigned long expires_cursor; /* Cursor of the active expire cycle. */
    list *defrag_later;         /* List of key names to attempt to defrag one by one, gradually. */
} redisDb;

struct dict {
    dictType *type;
    dictEntry **ht_table[2];
    unsigned long ht_used[2];
    long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    /* Keep small vars at end for optimal (minimal) struct padding */
    unsigned pauserehash : 15; /* If >0 rehashing is paused */
    unsigned useStoredKeyApi : 1; /* See comment of storedHashFunction above */
    signed char ht_size_exp[2]; /* exponent of size. (size = 1<<exp) */
    int16_t pauseAutoResize;  /* If >0 automatic resizing is disallowed (<0 indicates coding error) */
    void *metadata[];
  };

  typedef struct dictType {
    /* Callbacks */
    uint64_t (*hashFunction)(const void *key);
    void *(*keyDup)(dict *d, const void *key);
    void *(*valDup)(dict *d, const void *obj);
    int (*keyCompare)(dict *d, const void *key1, const void *key2);
    void (*keyDestructor)(dict *d, void *key);
    void (*valDestructor)(dict *d, void *obj);
    int (*resizeAllowed)(size_t moreMem, double usedRatio);
    void (*rehashingStarted)(dict *d);
    void (*rehashingCompleted)(dict *d);
    size_t (*dictMetadataBytes)(dict *d);
    void *userdata;
    unsigned int no_value:1;
    unsigned int keys_are_odd:1;
    uint64_t (*storedHashFunction)(const void *key);
    int (*storedKeyCompare)(dict *d, const void *key1, const void *key2);
    void (*onDictRelease)(dict *d);
  } dictType;

  struct dictEntry {
      void *key;
      union {
          void *val;
          uint64_t u64;
          int64_t s64;
          double d;
      } v;
      struct dictEntry *next;     /* Next entry in the same hash bucket. */
  };

  struct redisObject {
    unsigned type:4;
    unsigned encoding:4;
    unsigned lru:LRU_BITS; /* LRU time (relative to global lru_clock) or
                            * LFU data (least significant 8 bits frequency
                            * and most significant 16 bits access time). */
    int refcount;
    void *ptr;
  };  

~~~

---

### （二）redisObject

+ redisObject用于表示value值的具体类型，并使用指针指向对应的值。它类似于Java的Object类是所有类的隐式父类，它的作用也差不多是redis的各个数据类型的父类
+ 这里着重关注一下redisObject结构:
  + type:对象的类型，包括OBJ_STRING、OBJ_LIST、OBJ_HASH、OBJ_SET、OBJ_ZSET
  + encoding:表示具体的数据结构
  + lru:24位，对象最后一次被命令程序访问的时间，与内存回收有关
  + refcount:引用计数，当该值为0时，表示该对象已经不被任何对象引用，可以进行垃圾回收
  + ptr:指向对象实际的内存结构
+ 我们可以使用`debug object <key>`命令来查看对应的键的细节，但是此方法默认是不允许使用的，需要将`enable-debug-command`配置项置为yes才能使
+ 得到的细节中，各个参数的意义是:
  + Value at:内存地址
  + refcount：引用次数
  + encoding:物理编码类型
  + serializedlength: 序列化后的长度(注意这里的长度是序列化后的长度，保存为rdb文件时使用了该算法，不是真正存贮在内存的大小),会对字串做一些可能的压缩以便底层优化
  + lru：记录最近使用时间戳
  + lru_seconds_idle：空闲时间

---

### （三）String类型

#### ①物理编码方式

+ String类型有三种物理编码方式:
  + int:保存64位整型，即long类型的整数，区间在[-1*2^63,2^93-1]，默认值是0，该数字最多19位。它只能表示整数，无法表示浮点数，浮点数是通过字符串保存的





---

# 汇总

## 一、常用命令汇总

|分类|命令|参数|作用|备注|
|:---:|:---:|:---:|:---:|:---:|
|文档|[文档](https://redis.io/commands/)|
|**基本命令**|`keys *`|无参|显示当前库中全部的key|无|
|^|`exists key1 [key2 ...]`|^|判断给出的key有多少个是存在的，返回数值|无|
|^|`type key`|^|查看key的类型|无|
|^|`debug object <key>`|key:键<br>查看对应键的值对应的数据结构|无|
|^|`del key1 [key2...]`|^|删除指定的key|如果删除的key较大，那么会阻塞后续的操作|
|^|`scan <cursor> [pattern <pattern>] [count <count>]`|cursor:游标值，第一次遍历时需要0<br>pattern:只有满足该匹配规则的key才会被匹配<br>count:该次遍历返回的集合中的元素数量，返回值不一定准确，它会尽量的向该值靠拢|遍历键值对|无|
|^|`unlink key1 [key2...]`|^|非阻塞删除指定的key|仅仅将keys从keyspace元数据中删除，真正的删除会在后续的异步操作中删除|无|
|^|`ttl key`|^|查看指定的key还有多长时间（单位:秒）过期，-1表示永不过期，-2表示已过期|无|
|^|`expire key 时间`|^|给指定的key设置过期时间|无|
|^|`move key 库序号`|^|将指定的key移到对应序号的库中，默认的库序号是0~15|无|
|^|`select 库序号`|^|切换数据库序号，默认为0|无|
|^|`dbsize`|^|查看当前序号的库的key数量|无|
|^|`flushdb`|^|清空当前库|无|
|^|`flushall`|^|清空全部库|无|
|^|`config get <config>`|config:想查看的配置项|得到配置文件的对应配置值|无|
|^|`config set <key> <value>`|key:配置项，参考下面的配置项汇总<br>value:配置项的值|设置配置项的值|**它仅会在本次服务启动时生效，下次就会失效**|
|**连接、启动redis**|`redis-server [configpath]`|configpath:配置文件相对于当前终端所在目录的路径|根据指定配置文件的内容启动redis服务|无|
|^|`redis-cli -a <password> [-p <port>] [--raw\|--charset <code>] [-c] [--bigkeys]`|password:密码<br>port:指定端口，默认为6379，如果没改端口，可以不用写<br>code:字符集编码|连接redis，可以指定端口，且可以指定是否显示原始字节码(可以查看中文)或者指定字符集编码<br>-c:开启路由，解决集群节点没办法向不归自己管的槽进行操作的问题<br>--bigkeys:输出每种数据结构最大的key，同时给出每种数据类型的键值个数+平均大小|无|
|**帮助命令**|`help {@string\|@list\|@hash...}`|>|列出对应数据类型的语法|也可以输入具体命令来查看指定命令的语法|
|**RDB持久化**|`save`|无参|以阻塞的方式进行持久化|无|
|^|`bgsave`|无参|以非阻塞的方式进行持久化|**推荐**|
|^|`lastsave`|无参|获得最后一次执行持久化的时间戳|无|
|^|`redis-check-rdb <filepath>`|filepath:RDB文件路径|检查并修复RDB文件|无|
|**AOF持久化**|`bgrewriteaof`|无参|手动执行AOF持久化|无|
|^|`redis-check-aof --fix <filepath>`|filepath:文件路径|检查并修复AOF文件|无|
|**主从复制**|`slaveof <host> <port>`|host:主机IP<br>port:redis主机占用端口|指定本redis服务器的master|无|
|**集群**|`redis-cli -a <password> --cluster create --cluster-replicas <number> <host> <port> [<host> <port>]`|password:连接redis客户端需要的密码<br>number:指定复制因子，即每个主节点需要多少个从节点<br>host:连接端口<br>port:redis占用的端口|创建集群|无|
|^|`cluster info`|无参|查看集群整体的信息|无|
|^|`cluster nodes`|无参|查看集群各节点的信息|无|
|^|`cluster failover`|无参|使原master在故障转移后重新变成master|无|
|^|`redis-cli -a <password> --cluster add-node <addHost>:<addPort> <clusterHost>:<clusterPort>[ --cluster-slave --cluster-master-id <id>]`|password:密码<br>addHost:要添加的redis所在ip<br>addport:要添加的redis占用的端口<br>clusterHost:集群中已存在的redis所在ip<br>clusterPort:集群中已存在的redis所占用的端口<br>id:master的id|添加redis服务器进入集群[并认master]|无|
|^|`redis-cli -a <password> --cluster del-node <host>:<port> <id>`|password:密码<br>host:集群中已存在的redis所在ip<br>port:集群中已存在的redis所占用的端口<br>id:想删除的在集群中的redis服务器的id|将指定id的redis服务器移除集群|无|
|^|`redis-cli -a <password> --cluster check <host>:<port>`|password:密码<br>host:集群中已存在的redis所在ip<br>port:集群中已存在的redis所占用的端口|查看当前集群中的节点状态|无|
|^|`redis-cli -a <password> --cluster reshard <host>:<port>`|password:密码<br>host:集群中已存在的redis所在ip<br>port:集群中已存在的redis所占用的端口|进行哈希槽的分配|无|
|^|`cluster keyslot <key>`|key:想查看的key|得到指定key对应的哈希槽序号|无|
|^|`cluster countkeysinslot <number>`|number:槽位序号|判断槽位是否被占用，1表示占用，0表示未占用|无|
|^|`cluster keyslot <key>`|key:想查询的键|查看指定键会映射到哪个哈希槽中|无|
|**BigKey**|`MEMORY USAGE <key> [SAMPLES <count>]`|key:想判断的键<br>count:需要进行嵌套采样的值得数量（该值作用在嵌套数据类型上）|得到指定键及其对应值占用的字节数|无|

---

## 二、配置项汇总

|分类|配置项|参数|作用|备注|
|:---:|:---:|:---:|:---:|:---:|
|**初始化**|`requirepass <password>`|password:密码|设置redis密码|无|
|^|`daemonize {yes\|no}`|无参|不知道，但是要设成yes|无|
|^|`protected-mode {yes\|no}`|无参|不知道，但是要设成no|无|
|^|`bind`|不知道|不知道，但是要注掉|无|
|**RDB数据持久化**|`save <seconds> <frequency> [<seconds> <frequency>]`|seconds frequency:**在seconds秒内修改key达frequency次及以上**，或者**两次修改key的时间间隔大于seconds秒**，那么就进行一次持久化|设置持久化策略|Redis6.0.16版本前，使用该方式配置|
|^|`dir <filepath>`|filepath:设置rdb文件的存放路径，默认存放在`./`文件夹下（相对于配置文件）|设置rdb文件的存放路径|无|
|^|`dbfilename <filename>`|filename:rdb文件的名称，相对路径是相对于dir的路径|设置rdb文件的名称|无|
|^|`stop-writes-on-bgsave-error {yes\|no}`|无参|默认yes，如果配置成no，表示不在乎数据不一致或者有其他的手段发现和控制这种不一致，那么在快照写入失败时，也能确保redis继续接受新的请求|无|
|^|`rdbcompression {yes\|no}`|无参|默认yes，对于存储到磁盘中的快照，可以设置是否进行压缩存储。如果是的话，Redis会采用LZF算法进行压缩。如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能|无|
|^|`rdbchecksum {yes\|no}`|无参|默认yes，在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能|
|**AOF数据持久化**|`appendfsync {always\|everysec\|no}`|always:每次写操作时，都直接进行同步<br>everysec:每秒同步一次<br>no:把同步时机丢给操作系统处理|设置AOF同步文件的策略|无|
|^|`appendonly {yes\|no}`|略|设置AOF是否开启|无|
|^|`appenddirname <dirname>`|dirname:装AOF文件的文件夹名称|指定装AOF文件的文件夹名称|该配置项在Redis7才有，在Redis7之前，AOF文件都是与RDB文件放在一起的。该配置使得AOF的保存路径变成了dir+appenddirname|
|^|`appendfilename <filename>`|filename:AOF文件名称，默认为appendonly.aof|设置AOF文件名称|无|
|^|`auto-aof-rewrite-percentage <number>`|number:百分比的数值，想80%就写80|指定当前的AOF文件是上一个AOF文件的百分之多少时，执行重写|auto-aof-rewrite-percentage和auto-aof-rewrite-min-size**同时满足限制条件时，才会触发重写**|
|^|`auto-aof-rewrite-min-size <number>`|number:设定上限|指定当前AOF大小超过指定值时，执行重写|^|
|^|`rdb-del-sync-files {yes\|no}`|无参|在没有持久化的情况下删除复制中使用的RDB文件。默认情况下no，此选项是禁用的。|无|
|**主从复制**|`masterauth <password>`|password:密码|设置slave连接master时的密码|无|
|^|`replicaof <host> <port>`|host:主机ip<br>port:端口号|设置本服务器的master所在的主机IP和占用的端口号|无|
|^|`repl-ping-replica-period {yes\|no}`|无|设置master每次向slave发送ping包进行心跳检测的频率，单位秒|无|
|**集群**|`cluster-enabled`|无参|开启集群|无|
|^|`cluster-config-file <filename>`|filename:指定集群信息配置文件|指定集群信息配置文件|**这并不是运行redis服务的配置文件，需要保证二者名称不冲突**|
|^|`cluster-node-timeout <time>`|time:超时时间|节点互连超时时间，单位:毫秒|无|
|^|`cluster failover`|无参|使原master在变成slave之后再恢复成master|无|
|^|`cluster-require-full-coverage {yes\|no}`|默认是yes|集群是否完整才对外服务，即如果集群中的主节点有的失效了，那么集群将不对外提供服务|无|
|^|`cluster-announce-ip <host>`|host:redis服务器所在的公网IP|使在云服务器上运行的redis服务器暴露在公网IP下|**如果不配置可能会报`Unable to connect to xxx:yyy .... connection timed out after zzzzms`的错误**|
|**多线程**|`io-threads`|无参|配置多线程的支持线程个数|线程个数并不是越大越好，一般要小于CPU核数，4核建议取值2-3，8核建议取值6|
|^|`io-thread-do-reads`|无参|该项置为yes时表示开启多线程|无|
|**BigKey**|`rename-command <operation> ""`|operation:操作命令|将指定命令通过重命名为空字符串来禁止该命令|无|
|**惰性释放**|`lazyfree-lazy-eviction {yes\|no}`|无参|当redis内存使用到达maxmemory并设置有淘汰策略时，是否采用非阻塞方式进行删除，默认为no|无|
|^|`lazyfree-lazy-expire {yes\|no}`|^|当redis内的键到达超时时间时，是否采用采用非阻塞方式进行删除，默认为no|无|
|^|`lazyfree-lazy-server-del {yes\|no}`|^|当执行像rename之类的指令时，它会隐式的删除对应的key，此时删除时是否以非阻塞方式删除，默认为no|无|
|^|`replica-lazy-flush {yes\|no}`|^|从机根据主机进行数据同步时，需要先调用`flushall`来清除自己的库，该配置用来决定从机在flush时是否以非阻塞方式删除，默认为no|无|
|^|`lazyfree-lazy-user-del {yes\|no}`|^|设置数据是否在调用`del`时要与调用`unlink`命令时的行为保持一致，默认为no|无|
|^|`lazyfree-lazy-user-flush {yes\|no}`|^|当用户执行`flushdb`、`flushall`等命令但未指定`async`或`sync`参数时，该配置项用来决定是否进行异步删除，默认为no|无|
|**debug相关**|`enable-debug-command`|无参|允许使用debug相关的命令|无|

---

## 三、面试题汇总

### （一）线程问题

1. Redis是多线程还是单线程?
   + Redis6及以后是多线程，之前是单线程
   + Redis6及之后的多线程实际上是将网络请求的解析和回写使用多线程进行处理了，实际上的请求操作的执行依然是主线程这一单线程执行的
   + Redis4时增加了`unlink`、`flushdb async`、`flushall async`，这些操作是使用额外的线程进行的，但是该版本对于网络请求的处理依然是单线程的
2. IO多路复用?
   + 使用事件驱动的方式，让单线程可以同时处理多个请求
3. Redis为什么快
   + 基于内存操作:Redis的所有操作都是在内存中进行的，内存操作效率高
   + 数据结构简单:简单的数据结构的查找和操作的时间复杂度大部分都是O(1)，效率较高
   + IO多路复用和非阻塞IO:Redis通过IO多路复用来监听多个socket连接客户端，这样就能够通过一个线程来处理多个请求，减少线程切换带来的开销，同时也避免了IO的阻塞操作
   + 避免上下文切换:单线程不会出现多线程环境下出现的死锁问题，同时也不需要加锁，也避免了多线程竞争、线程切换带来的开销
   + Redis6以后新增了多线程的支持，使用多线程来进行网络请求的解析和回写，进一步提高了操作效率
4. redis网络模型?
  + redis使用IO多路复用+事件派发机制+多线程处理请求的方式来进行网络连接的处理
  ![redis网络模型](../文件/图片/Redis/redis网络模型.png)

---

### （二）BigKey问题

1. 如何在海量key中找到某一固定前缀的key
   + 使用`scan`命令，配合`pattern`进行查找
2. 如何生产上限制`keys *`、`flushdb`、`flushall`等危险命令以防止误删误用
   + 在配置文件中配置`rename-command`来禁用这些命令
3. `memory usage`命令干嘛的
   + 计算指定键值所占用的字节数，用来查找与发现BigKey
4. BigKey多大算大，如何发现，怎么删除，如何处理
   + 见上面的BigKey笔记
5. 是否做过BigKey调优，惰性释放lazyfree?
   + lazyfree就是在配置文件中配置对应的`lazyfree-xxx-xxx`配置项为true，主要针对内存达到maxmemory、从机同步数据时执行flush删除数据、设置的键超时、执行的操作内部要隐式删除数据这四种情况来配置是否进行异步删除
6. 生产上Redis数据库有1000w记录，如何遍历，能否使用`keys *`
   + 使用`scan`、`sscan`、`hscan`、`zscan`等

---

### （三）缓存双写一致性问题

1. 如何解决双写缓存一致性问题
   + 发现数据不一致时，将数据库中的数据值回写到Redis中，一般都是写操作先更新MySQL中的值并删除Redis的对应缓存，然后读操作再把MySQL中的值回写回去
2. 处理双写一致性，先动缓存Redis还是数据库MySQL?为什么
   + 建议先动数据库MySQL，因为MySQL中存储的数据具有最终解释权，Redis大部分情况下只是充当MySQL的缓存的作用
3. 是否做过延时双删，有哪些问题
   + 难以确定延时的具体时间
   + 吞吐量可能会下降（通过CompletableFuture另起一个线程执行第二次删除操作可以解决该问题）
4. 微服务查询Redis缓存时没有该值，但是MySQL有，为了保证数据双写一致性回写Redis应该注意什么
   + 双检加锁，使用同步的方式，一旦一个线程回写完成，其它线程就不必再执行加锁回写的操作了，直接读取数据就行
5. 是否了解双检加锁策略，如何避免缓存击穿问题
   + 使用**双检加锁策略**，当缓存数据不存在时，通过使用JUC的锁或使用synchronized的方式来同步对数据库执行操作对缓存进行回写，避免短时间内对数据库的多次访问增加数据库压力
   + 为了避免缓存击穿问题，还可以**使用两层缓存结构做到差异化失效时间**
6. Redis和MySQL双写必定会出现纰漏，做不到强一致性，如何保证最终一致性
   + 延时双删
   + 使用MQ，更新完数据库以后发送消息，消费端收到消息后删除缓存
   + canal监听binlog并删除缓存
   + 三重删除策略

---

### （四）数据类型

1. 抖音电商直播，主播介绍的商品有评论，1个商品对应了1系列的评论，如何排序+展现+取前10条记录
  + 使用zset
2. 用户在手机App上的签到打卡信息:1天对应1系列用户的签到记录，新浪微博、钉钉打卡签到，来没来如何统计?
  + 使用bitmap，按位存储
3. 应用网站上的网页访问信息:1个网页对应1系列的访问点击，淘宝网首页，每天有多少人浏览首页?
  + 使用HyperLogLog进行基数统计
4. 你们公司系统上线后，说一下UV、PV、DAU分别是多少?
  + 这个实际考的是这些傻逼缩写我们知不知道是什么意思，详见[名词解释](#definition)

---

### （五）布隆过滤器

1. 现有50亿个电话号码，现有10万个电话号码，如何要快速准确的判断这些电话号码是否已经存在?
2. 判断是否存在，是否了解过布隆过滤器
3. 安全连接网址，全球数十亿的网址判断
4. 黑名单校验识别垃圾邮件、白名单校验识别合法用户进行后续处理

+ 以上问题都是用布隆过滤器解决的

---

### （六）缓存问题

1. 缓存预热、雪崩、穿透、击穿分别是什么，你遇到过那个情况?
  + 详见[名词解释](#definition)
2. 缓存预热你是怎么做的
  + 在项目启动时使用@PostConstruct将部分数据先加载到缓存
3. 如何避免或减少缓存雪崩
  + 设置key不过期
  + 使用Redis集群
  + 多缓存结合预防雪崩，可以使用ehcache+Redis结合的方式（ehcache是一个Java缓存框架，它可以将缓存数据存放于JVM中的内存中来实现本地缓存，相比于Redis缓存，其性能和响应速度更优秀）
  + 使用服务降级策略，如利用Hystrix或Sentinel实现服务降级，避免对数据库的多次访问
  + 使用阿里云Redis云数据库
4. 穿透和击穿有什么区别，他俩是一个意思吗
  + 缓存穿透指请求去访问一个缓存中不存在的数据，然后又去数据库找，数据库也找不到，导致大量请求直接访问数据库，造成数据库压力变大
  + 缓存击穿指缓存的一个热点数据突然因过期失效，导致大量数据去直接访问数据库，导致数据库压力上升
5. 缓存穿透和缓存击穿有什么解决方案
  + 穿透
    + 使用布隆过滤器（实际存在那么一定判断存在，实际不存在可能会判断存在，即存在误判）
    + 如果MySQL也是空的，那么把null数据回写到Redis缓存中(缺点:内存增大，且可能会出现数据不一致的情况)
  + 击穿
    + 差异过期时间
    + 使用双检加锁机制机制降级数据库压力
    + 逻辑过期
    ![逻辑过期图例](../文件/图片/Redis/逻辑过期图例.png)
6. 如果出现了缓存不一致现象，有哪些修补方案:
  + 先更新数据库，再删除缓存（同步删除） 
  + 延迟双删
  + 使用canal监听数据库binglog日志进行更新并删除缓存

---

### （七）Redis分布式锁

1. Redis除缓存，还能用来干嘛?
  + 分布式Session
  + 分布式锁
  + 计数器、点赞
  + 位统计、签到、打卡
  + 购物车
  + 轻量级消息队列
  + 抽奖
  + 差集交集并集、用户关注、可能认识的人、附近的人
  + 热点新闻、热搜排行榜
2. Redis做分布式锁的时候有需要注意的问题吗
  + 防死锁、排他性、独占性、高可用、可重入性、不能释放别人占用的锁
3. 分布式锁是否使用`setnx`命令实现，这个是最合适的吗？如何考虑可重入问题
  + 这个不能实现可重入性，可以使用hash结构来实现可重入的目的
4. 如果Redis是单点部署的，会带来什么问题
  + 单点的宕机引来的服务的灾难、数据丢失
  + 单点服务器内存瓶颈，无法无限纵向扩容
5. Redis集群模式下，主从模式，CAP会有什么问题
  + Redis是AP（满足可用性(Available)、分区容错性(Partition Tolerance)而不满足一致性(Consistency)）
  + 由于该情况，在集群模式下，当主机被写而还没有将数据同步到从机时，主机宕机了，此时新上台的主机就未得到对应的数据，导致数据丢失而不满足一致性
  + 他可能会导致分布式锁冲突而导致并发问题，另外数据丢失可能导致大量请求打到数据库上，导致缓存雪崩等问题
6. 简单介绍一下RedLock
  + Redis官方提供的一种分布式锁的解决方案
  + 将一些Redis服务与集群隔开，让他们各自独立，请求分布式锁时，需要对这些独立的Redis服务各自请求一遍，当获得锁的数量占比达到一定比例时，才算加锁成功，如果加锁失败，那么逐个取消全部的Redis服务上的分布式锁。加锁成功后，锁的实际生效时间应为声明占用时间-获取锁占用时间
7. Redis分布式锁如何续期，看门狗是什么
  + 新起一个定时器，每隔1/3的锁生效时间就去判断锁是否还存在，如果存在就使用Lua脚本执行续期
  + 看门狗就是专门给分布式锁续期的机制

---

### （八）缓存淘汰策略

1. 生产上redis的最大内存设置为多少
  + 一般设置为最大物理内存的四分之三
2. 如何配置、修改Redis的最大内存
  + 使用`maxmemory`配置项，或`config set maxmemory`
3. 内存满了怎么办
  + 采用缓存淘汰策略
4. redis清理内存的方式，定期删除和惰性删除?
  + 它默认会定期以每秒10次的频率来定期随机删除过期的key
  + 惰性删除，就是key过期时之后并不立刻删除，在其过期后再有请求访问才删除
5. 缓存淘汰策略有哪些，分别是什么，你用哪个
  + [见上](#maxmemoryPolicy)
6. Redis的LRU是否了解
  + 就是查看内存中的key最近一次被访问的时间，时间戳最小的会被淘汰
7. LRU和LFU算法的区别
  + 一个是查看最近一次被访问的时间，一个是查看全局被访问的频率
8. 数据库有1000w数据，但是redis只能存20w数据，如何保证这些数据都是热点数据
  + 使用`allkeys-lru`内存淘汰策略，这样留下来的都是热点数据

---

<a id="definition"></a>

## 四、名词解释

+ **UV、PV、DAU**
  + 独立访客（Unique Visitor，UV）
  + 页面访问量（Page View）
  + 日活跃用户（Daily Active User）
+ **缓存穿透**
  + 当缓存没有该数据时，大量去访问数据库，仿佛缓存根本不存在一样给数据库带来压力，该情况被称为缓存穿透
+ **缓存击穿**
  + 当缓存中的某个数据过期以后，大量访问该数据的请求去直接访问数据库，该情况被称为缓存击穿
  + 缓存击穿强调的是大量请求访问单个数据
+ **缓存雪崩**
  + 指缓存中的大量数据失效或者缓存服务器宕机，导致大量请求直接打在数据库上，引起数据库压力过大甚至宕机
  + 缓存雪崩与缓存击穿的区别在于缓存雪崩是大量请求访问不同数据
+ **缓存预热**
  + 根据业务分析或统计数据，确定热点数据（经常被访问的数据），并将其提前加载到缓存中的策略。这样，在实际请求到达程序时，热点数据已经存在于缓存中，从而减少了缓存穿透和缓存击穿的情况，也缓解了SQL服务器的压力。

if redis.call('exists',KEYS[1])==0 or redis.call('hexists',KEYS[1],KEYS[2])==1 then redis.call('hincrby',KEYS[1],KEYS[2]) redis.call('expire',KEYS[1],ARGV[1]) return 1 else return 0 end

if redis.call('hexists',KEYS[1],KEYS[2])==0 then return nil elseif redis.call('hincrby',KEYS[1],KEYS[2],-1)==0 then redis.call('del',KEYS[1]) return 1 else return 0 end

if redis.call('hexists',KEYS[1],KEYS[2])==1 then redis.call('expire',KEYS[1],ARGV[1]) return 1 else return 0 end