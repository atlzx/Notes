// 这里是query查询条件
const queryParam={
    "query":{
        // match表示对指定字段执行全文搜索，支持分词
        "match":{
            // 这里是字段名
            "name":{
                "query": "Laptop",  // query用来指定搜索的关键词
                "operator": "and",  // operator用来指定匹配条件，默认为or，可选and
                "analyzer": "haha"  // analyzer指定分词器
            }
        },
        // match_phrase用来对指定字段进行短语匹配，要求关键词按顺序出现
        "match_phrase":{
            "name":"High performance Laptop"  // 关键词以空格分隔
        },
        // multi_match用来在多个字段中搜索相同的关键词
        "multi_match":{
            "query": "Laptop",  // query用来指定关键词
            "fields": ["name", "description"],  // fields指定要搜索的字段列表
            "type": "best_fields"  // type用来指定匹配类型，可选 best_fields、most_fields、phrase
        },
        // term用来针对指定字段执行精准匹配
        "term":{
            "category": "Electronics"  // 使用 字段名:关键词的形式
        },
        // terms用来针对指定字段匹配多个值
        "terms":{
            "category": ["Electronics", "Computers"]  // 多个值的情况使用数组
        },
        // range用来对指定字段进行范围匹配
        "range":{
            /* 
                范围匹配支持如下四个键
                gte 大于等于
                lte 小于等于
                gt 大于
                lt 小于
            */
            "price": {
                "gte": 1000,
                "lte": 2000
            }
        },
        // bool用来进行布尔匹配
        "bool":{
            // must:必须匹配的条件
            "must": [
                { "match": { "name": "Laptop" } }
            ],
            // must_not:必须不匹配的条件
            "must_not": [
                { "range": { "price": { "gte": 2000 } } }
            ],
            // should:可选匹配的条件
            "should": [
                { "match": { "description": "gaming" } }
            ],
            // filter:过滤条件
            "filter": [
                { "term": { "category": "Electronics" } }
            ]
        },
        // nested用于查询嵌套对象或数组字段
        "nested":{
            "path": "tags",  // path用来指定嵌套字段的基本路径
            // query指定嵌套字段的查询条件
            "query": {
                "term": {
                    "tags.name": "gaming"
                }
            }
        },
        // aggs用于对文档进行聚合操作
        "aggs": {
            // aggs下面的key指定的是最终返回的聚合字段名
            "avg_price": {
                /*
                    里面写聚合操作类型，包含:
                        terms:桶聚合
                        avg:平均
                        sum:求和
                        count:计数
                        min:取最小值
                        max:取最大值
                        stats:计算字段的统计信息，包括 count、min、max、avg 和 sum
                        cardinality:计算不重复的字段值数量
                        range:根据数值范围分组
                        date_range:根据日期范围分组
                        histogram:根据数值间隔分组
                        date_histogram:根据日期间隔分组
                */
                "avg": {
                    "field": "price"
                }
            },
            "categories": {
                "terms": {
                    "field": "category",  // field用来指定分组字段
                    "size": 5  // size用来限制返回的桶数量，默认为10
                }
            },
            // 范围分组
            "price_ranges": {
                "range": {
                    "field": "price",
                    "ranges": [
                        { "to": 1000 },
                        { "from": 1000, "to": 2000 },
                        { "from": 2000 }
                    ]
                }
            },
            // 日期范围分组
            "sales_ranges": {
                "date_range": {
                    "field": "sale_date",
                    "ranges": [
                        { "to": "now-1M/M" },
                        { "from": "now-1M/M", "to": "now" }
                    ]
                }
            },
            // 数值间隔分组
            "price_histogram": {
                "histogram": {
                    "field": "price",
                    "interval": 500  // interval指定间隔
                }
            },
            // 日期间隔分组
            "sales_over_time": {
                "date_histogram": {
                    "field": "sale_date",
                    "interval": "month"  // interval指定间隔，可选 day、month、year
                }
            },
            // 嵌套聚合，它要等待上面的一般聚合都执行完毕后再执行
            // 比如当它和上面的category分组聚合放在一起时，就意思就是先按category分组，然后计算每组的price的平均值
            "aggs": {
                "avg_price": {
                    "avg": {
                        "field": "price"
                    }
                }
            },
        },
    },
    // 这里不属于query的内容，但是也算查询条件
    // sort用来对搜索结果进行排序，这是一个数组，表示可以多字段进行排序
    "sort":[
        // 格式: 字段名:排序方式
        {"price":"asc"}
    ],
    // from和size指定分页，from下标从0开始，size表示分页跨度多大
    "from": 0,
    "size": 10
};

