/* 创建索引时，以 PUT /{index}开头，index表示索引名，索引名的所有字母必须小写
     后面带着的JSON配置是可选的
        settings用来配置索引本身的一些配置
        mappings用来设置索引的结构   
  */
    PUT /products
    // 创建的json配置如下，不用在意js语法的前缀，只看对象内容即可
    const createIndex={
        "settings": {
            "number_of_shards": 3,  // 设置主分片数量（默认值为 1）
            "number_of_replicas": 2,  // 设置每个主分片的副本数量（默认值为 1）
            "refresh_interval": "30s",  // 设置刷新索引的时间间隔，默认为1s，设置为-1可以禁止自动刷新，设置为20s表示每20s刷新一次
            "max_result_window":10000,  // max_result_window用来设置查询结果的最大窗口大小（默认值为 10000）
            "max_refresh_listeners":10000,  // max_refresh_listeners用来设置每个分片的最大刷新监听器数量（默认值为 1000）
            // analysis用来自定义分词配置
            "analysis": {
                // analyzer用来自定义分词器
                "analyzer": {
                    "my_analyzer": {
                        /*
                            分词器的type有多种格式可选
                            standard: 标准分词器，按单词边界分割文本并去除标点符号。
                            simple: 简单分词器，按非字母字符分割文本并将字母转为小写。
                            whitespace: 空格分词器，按空格分割文本，不进行其他处理。
                            stop: 停用词分词器，在 simple 基础上移除停用词。
                            keyword: 关键词分词器，将整个文本作为一个单独的词项。
                            pattern: 模式分词器，使用正则表达式分割文本。
                            language: 语言分词器，针对特定语言进行分词。
                            icu_tokenizer: ICU 分词器，支持多语言文本的分词。
                            ngram: N-gram 分词器，生成指定长度的字符序列。
                            edge_ngram: Edge N-gram 分词器，生成从词首开始的字符序列。
                            uax_url_email: UAX URL Email 分词器，将 URL 和电子邮件地址视为单个词项。
                            path_hierarchy: 路径层次分词器，按文件路径分隔符分割文本。
                        */
                        "type": "pattern",
                        "tokenizer": "standard",
                        "filter": ["lowercase", "my_filter"]
                    }
                },
                // filter用来自定义过滤器
                "filter": {
                    "my_filter": {
                        "type": "stop",
                        "stopwords": ["a", "the"]
                    }
                }
            }
        },
        // mappings用来设置索引的结构
        "mappings": {
            "dynamic": "strict",  // dynamic用来控制动态映射行为，为true表示自动映射新字段（默认值）,false表示忽略新字段，strict表示遇到新字段时抛出异常
            // properties用来定义索引结构
            "properties": {
                // 结构下面的key就是JSON数据的key名称
                "name": {
                    "type": "text",  // 设置类型，类型有很多种
                    "analyzer": "my_analyzer",  // 指定分词器
                    "index": false,  // index设置该键是否为索引字段（默认值为 true）
                    "search_analyzer":"my_analyzer",  // search_analyzer用来指定搜索时的分词器
                    "format":"yyyy-MM-dd HH:mm:ss",  // format用来指定日期格式（仅适用于 date 类型）
                    "store":true,  // store表示是否单独存储字段值（默认值为 false）
                    /* 
                        fields可以为字段定义多字段类型，它有如下作用:
                            支持一个字段适配多种搜索方式:一个字段可以支持全文搜索(text)和精确匹配(keyword)
                            支持一个字段采用不同分词器:比如字段为英文的话使用英文分词器，为中文则采用中文分词器
                            支持数值类型多样:如price字段可以支持浮点型，也能支持整形
                            支持一个字段的duoz
                    */
                    "fields": {
                        // 如果指定fields的话里面需要写key，在查询时若要使用它的话就需要用字段名.子字段名的方式调用
                        // 如调用下面这个字段就需要使用 q=name.keyword:aaa 来查询
                        "keyword": {
                            "type": "keyword",
                            "analyzer": "simple"
                        }
                    },
                    // 如果要定义嵌套对象，需要将type置为nested，再用properties进行嵌套
                    "properties": {
                        "name": { "type": "text" },
                        "age": { "type": "integer" }
                    }
                }
            }
        }
    }

// POST /_aliases settings命令的设置项
const aliasIndex={
    // actions:操作列表，即别名的操作列表数组
    "actions":[
        {
            // 这里可以写add和remove两种操作
            // add就是添加索引别名，remove就是移除索引别名
            "add":{
                "index":"products",  // index:索引的正式名称
                "alias":"products_alias"  // alias:索引的别名
            }
        }
    ]
}