## 一、安装

+ 下面安装的三个玩意必须保证它们的**版本号是一致的**

### （一）ES数据库安装

+ 首先去[官网](https://www.elastic.co/downloads/elasticsearch)下载压缩包
+ 解压缩
+ 进入`conf`目录，找到`elasticsearch.yml`文件，配置`xpack.security.enabled: false`以关闭连接校验
+ 在`conf`目录，还可以设置ES数据库占用的内存（不设置会默认把内存吃满）
  + `jvm.options`文件中设置如下参数（不要有空格，想设置MB为单位后缀就是m，想设置GB为单位后缀就是g）:
    ~~~
        -Xms512m
        -Xmx512m
    ~~~
+ 进入`bin`目录执行`elasticsearch.bat`文件开启即可
+ 开启后打开`http://localhost:9200/`看见返回的JSON串即说明启动成功

### （二）Kibana安装

+ Kibana是ES官方推出的一款可视化连接ES数据库的操作工具
+ 去[官网](https://www.elastic.co/guide/en/kibana/current/install.html)下载压缩包
+ 解压缩
+ 进入`bin`目录执行`kibana.bat`文件开启即可（需要Node）
+ 开启后打开`http://localhost:5601/app/home#/`就能看见可视化界面了
  + 在侧边栏最下面的`Dev Tools`栏可以执行es命令
+ 问题:
  + `TypeError: Invalid character in header content ["kbn-name"]`
    + 在kibana的config文件夹找到`kibana.yml`配置文件，设置其配置项`server.name`为一个字符串，如`kibana`

---

### （三）IK分词器安装

+ IK分词器是一款开源的ES中文分词插件，这是其[github地址](https://github.com/infinilabs/analysis-ik)
+ 想安装的话，直接进入ES数据库目录，然后执行`bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/8.4.1`即可。最后面的版本号根据情况进行修改
+ 安装完成后，在ES的plugins和config目录下可以看到它:
  + 在config的`analysis-ik`目录下找到`IKAnalyzer.cfg.xml`文件，它可以用来指定我们的额外中文分词拓展配置
  + 像这样:

~~~xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
    <properties>
        <comment>IK Analyzer 扩展配置</comment>
        <!-- 
            在这里配置本地的拓展字典，文件后缀统一为 .dic，文件名就是key的名字，比如此处对应的文件就叫 ext_dict.dic文件
            这个文件直接创建在IKAnalyzer.cfg.xml文件的同级目录下即可
         -->
        <entry key="ext_dict"></entry>
        <!--用户可以在这里配置自己的扩展停止词字典-->
        <entry key="ext_stopwords"></entry>
        <!--用户可以在这里配置远程扩展字典 -->
        <!-- <entry key="remote_ext_dict">words_location</entry> -->
        <!--用户可以在这里配置远程扩展停止词字典-->
        <!-- <entry key="remote_ext_stopwords">words_location</entry> -->
    </properties>
~~~

---

## 二、使用

### 命令汇总



### （一）简介

+ ES的结构分为索引(库)、索引类型(表)、文档(行数据)和字段(表字段)
  + 随着版本更新，索引类型的概念正在被逐渐弱化。ElasticSearch6.x版本规定一个索引下仅能包含一个索引类型，从7.x版本开始，索引类型的概念已经被删除了
+ ES可以包含多个索引，一个索引可以包含多个类型，一个类型可以有多个文档，一个文档有多个字段

---

### （二）索引


## 三、汇总

### （一）命令汇总

|分类|命令|作用|参数|参数作用|备注|
|:---:|:---:|:---:|:---:|:---:|:---:|
|**索引操作**|`PUT /{index} {settings}`|创建/更新索引|index|索引名，**索引名的所有字母必须小写**|无|
|^|^|^|settings|详见[示例](../源码/elasticsearch/索引操作示例.js)|无|
|^|`GET _cat/indices?v`|查询所有索引库|无|无|无|
|^|`GET _cat/indices/{index}`|查看指定索引库|index|索引名|无|
|^|`DELETE {index}`|删除指定索引库|index|索引名|无|
|^|`POST /{index}/[{_refresh,_forcemerge,_cache/clear}]`|更新索引|index|索引名|无|
|^|^|^|_refresh|刷新索引|无|
|^|^|^|_forcemerge|强制合并索引分段|无|
|^|^|^|_cache/clear|清空索引的缓存|无|
|^|`POST /_aliases settings`|settings|详见[示例](../源码/elasticsearch/索引操作示例.js)|无|
|^|`GET /{index}/[{_shards,_health,_stats,_recovery}]`|查看指定索引的信息|index|索引名|无|
|^|^|^|_shards|获取索引的分片信息|无|
|^|^|^|_health|获取索引的健康状态|无|
|^|^|^|_stats|获取索引的统计信息，如文档数、存储大小等|无|
|^|^|^|_recovery|获取索引的恢复状态|无|
|^|`HEAD /{index}`|检查索引是否存在|index|索引名|无|
|**文档操作**|`PUT /{index}/_doc/{id} \{data\}`|添加 / 整体修改(就是把原数据覆盖) 文档数据|index|索引名|无|
|^|^|^|id|文档ID，即主键|如果没有指定，ES会自动生成|
|^|^|^|data|json格式的数据|无|
|^|`GET /{index}/_doc/{id}/{params}`|获得指定文档的数据|index|索引名|无|
|^|^|^|type|文档类型|无|
|^|^|^|id|文档ID，即主键|无|
|^|^|^|params|详见[请求路径参数](#requestParams)|无|
|^|`GET /_mget docs`|批量获得多个文档|docs|文档列表，是一个数组，数组的每个元素都需要指定`_index`和`_id`|无|
|^|`POST /{index}/_doc/{id}/_update`|局部修改，ES会比较新旧两个数据的异同并只会把修改的部分加上去|index|索引名|无|
|^|^|^|id|文档ID，即主键|无|
|^|^|^|_update|开启局部修改模式|无|
|^|`DELETE /{index}/_doc/{id}`|删除文档|index|索引名|无|
|^|^|^|id|文档ID，即主键|无|

---

### （二）字段类型

|字段类型|描述|
|:---:|:---:|
|`text`|全文搜索字段，支持分词|
|`keyword`|精确值字段，不支持分词|
|`long`|长整型字段|
|`integer`|整型字段|
|`short`|短整型字段|
|`byte`|字节字段|
|`double`|双精度浮点字段|
|`float`|单精度浮点字段|
|`date`|日期字段|
|`boolean`|布尔字段|
|`binary`|二进制字段|
|`object`|嵌套对象字段|
|`nested`|嵌套数组字段|

---

### （三）query查询条件

+ [文档查询条件示例](../源码/elasticsearch/文档查询条件示例.js)

---

<a id="requestParams"></a>

### （四）请求路径参数

|分组|路径参数|作用|值类型|备注|
|:---:|:---:|:---:|:---:|:---:|
|**获取文档**|`_source`|指定返回的字段，多个字段时用逗号隔开|字符串|无|
|^|`stored_fields`|返回存储在索引中的字段，多个字段时用逗号隔开|字符串|需要字段配置为`store: true`|
|^|`routing`|指定文档的路由值（用于分片路由）|字符串|无|
|^|`refresh`|在获取文档前刷新索引|布尔|无|
|^|`_source_includes`|指定包含的字段，多个字段时用逗号隔开|字符串|无|
|^|`_source_excludes`|指定排除的字段，多个字段时用逗号隔开|字符串|无|
|^|`docvalue_fields`|返回文档值字段，多个字段时用逗号隔开|字符串|无|
|^|`filter_path`|过滤返回的JSON内容，只返回指定该设置指定的字段名。多个字段时用逗号隔开|字符串|无|
|^|`human`|将数值字段（如大小、时间）转换为人类可读的格式|布尔|无|
|^|`pretty`|是否格式化返回的JSON数据，使其更好看|布尔|无|
|^|`error_trace`|在错误时返回详细的堆栈信息|布尔|无|
|^|`format`|指定返回结果格式（`json`、`yaml`）|字符串|无|
|^|`preference`|指定查询的首选分片或节点|字符串|无|
|^|`realtime`|是否实时获取文档|布尔|无|
|^|`version`|是否返回文档的版本号|布尔|无|
|^|`version_type`|指定版本类型（`external`、`external_gte`）|字符串|无|
|^|`if_seq_no`|基于序列号进行条件拉取|数值|无|
|^|`if_primary_term`|基于主分片任期号进行条件获取|数值|无|