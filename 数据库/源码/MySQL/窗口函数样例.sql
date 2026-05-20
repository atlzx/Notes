-- 查询 goods 数据表中每个商品分类下价格降序排列的各个商品信息。


CREATE TABLE goods(
id INT PRIMARY KEY AUTO_INCREMENT,
category_id INT,
category VARCHAR(15),
NAME VARCHAR(30),
price DECIMAL(10,2),
stock INT,
upper_time DATETIME
);

INSERT INTO goods(category_id,category,NAME,price,stock,upper_time)
VALUES
(1, '女装/女士精品', 'T恤', 39.90, 1000, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '连衣裙', 79.90, 2500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '卫衣', 89.90, 1500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '牛仔裤', 89.90, 3500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '百褶裙', 29.90, 500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '呢绒外套', 399.90, 1200, '2020-11-10 00:00:00'),
(2, '户外运动', '自行车', 399.90, 1000, '2020-11-10 00:00:00'),
(2, '户外运动', '山地自行车', 1399.90, 2500, '2020-11-10 00:00:00'),
(2, '户外运动', '登山杖', 59.90, 1500, '2020-11-10 00:00:00'),
(2, '户外运动', '骑行装备', 399.90, 3500, '2020-11-10 00:00:00'),
(2, '户外运动', '运动外套', 799.90, 500, '2020-11-10 00:00:00'),
(2, '户外运动', '滑板', 499.90, 1200, '2020-11-10 00:00:00');

select * from goods;

-- 窗口函数会对partition by指定的字段进行分组，并在分组后对每个组指定的order by字段进行排序
-- 排序和分组结果会影响到查询到的表，如果碰到了多个窗口函数按照了不同分组依据的操作，那么遵循后面覆盖前面的原则
select ROW_NUMBER() over (partition by category_id order by price desc) as aaa,RANK() over (partition by stock order by price asc) as bbb,category_id,category,`NAME`,price,stock,upper_time
from goods;
-- 这是另外一种写法
select ROW_NUMBER() over w,category_id,category,`NAME`,price,stock,upper_time
from goods
window w as (partition by category_id order by price desc);

-- 计算 goods 数据表中名称为“女装/女士精品”的类别下的商品的PERCENT_RANK值。

-- PERCENT_RANK函数会遵循(Rank值-1)/(当前行-1)进行运算，而rank不会跳过重复的序号，即会出现rank值为1,1,3的情况
select PERCENT_RANK() over (PARTITION by category_id order by price desc),category_id,category,`NAME`,price,stock,upper_time
from goods
where category='女装/女士精品';

-- 查询goods数据表中小于或等于当前价格的比例。
-- CUME_DIST函数专门用于查找当前组内数据表中小于或等于当前价格的比例
select CUME_DIST() over (PARTITION by category_id order by price desc),category_id,category,`NAME`,price,stock,upper_time
from goods;

-- LAG(expr,n)函数返回每组相对于本行的上n行的字段的值。
-- 查询goods数据表中前一个商品价格与当前商品价格的差值

select LAG(price,1) over (PARTITION by category_id order by price desc)-price,category_id,category,`NAME`,price,stock,upper_time
from goods;

-- FIRST_VALUE函数返回每组对应字段的第一个数值
select FIRST_VALUE(price) over (PARTITION by category_id order by price desc),category_id,category,`NAME`,price,stock,upper_time
from goods;

-- NTH_VALUE用来返回第n个字段的值

select NTH_VALUE(price,2) over (PARTITION by category_id order by price desc),category_id,category,`NAME`,price,stock,upper_time
from goods;

-- NTILE将每组数据平分为n组数据，如果无法平分，前面的分组数量都大于等于后面的
select NTILE(3) over (PARTITION by category_id order by price desc),category_id,category,`NAME`,price,stock,upper_time
from goods;