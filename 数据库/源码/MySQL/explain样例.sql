use atguigudb;


-- ******************************************************************id、table与select_type字段**********************************************************************


-- *******************************************************************事前准备***************************************************************************************

  -- 创建测试表1
CREATE TABLE explain_sample1 (
id INT AUTO_INCREMENT,
key1 VARCHAR(100),
key2 INT,
key3 VARCHAR(100),
key_part1 VARCHAR(100),
key_part2 VARCHAR(100),
key_part3 VARCHAR(100),
common_field VARCHAR(100),
PRIMARY KEY (id),
INDEX idx_key1 (key1),
UNIQUE INDEX idx_key2 (key2),
INDEX idx_key3 (key3),
INDEX idx_key_part(key_part1, key_part2, key_part3)
);
	
  -- 创建测试表2

CREATE TABLE explain_sample2 (
id INT AUTO_INCREMENT,
key1 VARCHAR(100),
key2 INT,
key3 VARCHAR(100),
key_part1 VARCHAR(100),
key_part2 VARCHAR(100),
key_part3 VARCHAR(100),
common_field VARCHAR(100),
PRIMARY KEY (id),
INDEX idx_key1 (key1),
UNIQUE INDEX idx_key2 (key2),
INDEX idx_key3 (key3),
INDEX idx_key_part(key_part1, key_part2, key_part3)
);

  -- 解决函数的报错情况

set global log_bin_trust_function_creators=1; # 不加global只是当前窗口有效。

-- 创建存储过程

DELIMITER //
CREATE FUNCTION rand_string1(n INT)
RETURNS VARCHAR(255) #该函数会返回一个字符串
BEGIN
DECLARE chars_str VARCHAR(100) DEFAULT
'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ';
DECLARE return_str VARCHAR(255) DEFAULT '';
DECLARE i INT DEFAULT 0;
WHILE i < n DO
SET return_str =CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));
SET i = i + 1;
END WHILE;
RETURN return_str;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_s1 (IN min_num INT (10),IN max_num INT (10))
BEGIN
DECLARE i INT DEFAULT 0;
SET autocommit = 0;
REPEAT
SET i = i + 1;
INSERT INTO explain_sample1 VALUES(
(min_num + i),
rand_string1(6),
(min_num + 30 * i + 5),
rand_string1(6),
rand_string1(10),
rand_string1(5),
rand_string1(10),
rand_string1(10));
UNTIL i = max_num
END REPEAT;
COMMIT;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE insert_s2 (IN min_num INT (10),IN max_num INT (10))
BEGIN
DECLARE i INT DEFAULT 0;
SET autocommit = 0;
REPEAT
SET i = i + 1;
INSERT INTO explain_sample2 VALUES(
(min_num + i),
rand_string1(6),
(min_num + 30 * i + 5),
rand_string1(6),
rand_string1(10),
rand_string1(5),
rand_string1(10),
rand_string1(10));
UNTIL i = max_num
END REPEAT;
COMMIT;
END //
DELIMITER ;

CALL insert_s1(10001,10000);

CALL insert_s2(10001,10000);

-- **********************************************************id与table字段*************************************************************************

-- 查询的每一个记录都对应着一个表名(table字段)
explain select * from explain_sample1;

-- 在一个大的查询内，每个SELECT关键字都对应着一个id
explain select * from explain_sample1 where key1='a';

-- 有两个表，因此有两行，两个表是一个SELECT语句，因此id一样
-- 我们称左边连接的表叫驱动表，右边的表是非驱动表
explain select * from explain_sample1 inner join explain_sample2;
-- 有两个表，因此有两行。有两个SELECT语句，因此两行的id一致
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2) or key3 = 'a';
-- 我们所看到的结果是查询优化器对SQL语句进行优化后的结果，因此我们通过分析我们自己编写的SQL语句得到的结果可能与实际运行结果不符
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2 where common_field='a');
-- 使用union去重，可以看到多出一个union表，从Extra字段那里可以看到对此的描述是Using temporary，说明这是一个临时表
-- MySQL在进行UNION操作时，会生成临时表进行两表之间的拼接
explain select * from explain_sample1 union select * from explain_sample2;
-- UNION ALL不需要去重，因此没有临时表
explain select * from explain_sample1 union all select * from explain_sample2;

-- ******************************************select_type部分******************************************************

-- 普通的查询就是SIMPLE
explain select * from explain_sample1;
-- 连接查询也是SIMPLE
explain select * from explain_sample1 inner join explain_sample2;
-- 对于包含union或union all或者子查询的大查询来说，他是由几个小查询组成的。其中最左边的查询的select_type字段是PRIMARY
-- 那剩下的右边的小查询的select_type字段就是UNION
-- 另外，MySQL用来进行UNION操作而生成的临时表的select_type字段是UNION RESULT
explain select * from explain_sample1 union select * from explain_sample2;
-- UNION ALL不需要去重，因此没有临时表
explain select * from explain_sample1 union all select * from explain_sample2;
-- 在优化器优化时，优化器会尽量将子查询转变为连接查询，如果没有成功，且该子查询是不相关子查询也不能被物化，那么该子查询的第一个SELECT查询语句所查询的表的那个select_type字段就是SUBQUERY
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2) or key3 = 'a';
-- 如果该子查询的第一个SELECT查询语句还是一个相关子查询，那么所查询的表的那个select_type字段就是DEPENDENT SUBQUERY
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2 where explain_sample1.key2=explain_sample2.key2) or key3='a';
-- 在包含UNION或者UNION ALL的大查询中，如果各个小查询都依赖于外层查询的话，那除了最左边那个小查询外，其余的小查询的select_type字段都是DEPENDENT UNION
-- 这里子查询最左边的那个小查询是DEPENDENT SUBQUERY，我的理解是它后面是UNION，相当于它要和一个后面连接的SELECT语句是相关子查询的查询进行合并，因此它也与相关查询有关，因此为DEPENDENT SUBQUERY
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2 where key1='a' union select key1 from explain_sample1 where key1='b');
-- 对于包含派生表的查询，该派生表对应的子查询的select_type字段就是DERIVED
-- 派生表就是大查询所from的那个表是通过SELECT语句查询出来的，而不是数据库的原生表
explain select * from (select key1,count(*) as c from explain_sample1 group by key1)as derived_sample where c>1;
-- 当优化器在执行包含子查询的语句时，选择将子查询物化之后与外层查询进行连接查询时，此时子查询对应的select_type字段就是MATERIALIZED
-- 物化就是优化器意识到查询可以转换为一个有明确的值的集合（集合是不重复的，因此会去重），就会将其转换为集合然后与驱动表进行连接，最后进行查询。
explain select * from explain_sample1 where key1 in (select key1 from explain_sample2);


-- **********************************************************************type、possible_keys与key字段部分*******************************************************************


-- **********************************************************************准备工作******************************************************************************

-- 创建一个存储引擎是MyISAM的表，必须是MyISSAM引擎，不能是InnoDB
CREATE TABLE explain_sample3(i int) Engine=MyISAM;

insert into explain_sample3 values(1);  -- 仅插入一条数据

-- *************************************************possible_keys与key字段过于简单，看的时候就着type样例看吧***************************************************************

-- system是效率最高的
-- MyISSAM引擎会在表中记录当前记录的数量，但是InnoDB不会。当MyISSAM引擎的表仅一条数据且我们使用的是下面的语句时，执行器直接就知道就是这条数据，因此执行方式就是system
-- 换成InnoDB是不行的，因为InnoDB在最开始执行时不知道表里有几条数据
explain select * from explain_sample3;
-- 如果我们根据主键或者唯一二级索引列与常数进行匹配时，对单表的访问方法就是const
-- 注意，必须是常数，而不是常量
explain select * from explain_sample1 where id=10005;
explain select * from explain_sample1 where key2=10036;
-- 下面是使用的常量，可以看到它的type字段是ref,而不是const
explain select * from explain_sample1 where key1='a';
-- 可以看到我们这里写的也是常数，但是type确实ALL，而且key是空的，但是psooible_keys明明有关于key3的索引，说明优化器没有选择索引
-- 没有选择的原因是key3是一个VARCHAR型的变量，但是我们查询时指定的值不是字符串类型的，因此MySQL会调用函数进行类型转换。一旦进行了类型转换，优化器将不会选择索引
explain select * from explain_sample1 where key3=10035;

-- 在查询连接时，如果被驱动表是通过主键或者唯一二级索引列等值匹配的方式进行比较的（如果是联合索引，那么所有的索引列都需要进行比较），那么被驱动表的type字段是eq_ref
explain select * from explain_sample1 inner join explain_sample2 on explain_sample1.id=explain_sample2.id;
-- 当通过普通的二级索引列与常量进行等值匹配时来查询某个表，那么对该表的type可能是ref
-- 这里说了常量，常量是包括常数的，因此说的是type字段可能是ref
explain select * from explain_sample1 where key1='a';
-- 当对普通二级索引进行等值匹配查询，该索引列的值也可能是NULL时，那么对该表的type字段可能是ref_or_null
explain select * from explain_sample1 where key1='a' or key1 is null;
-- 单表访问方法时在某些场景下可以使用intersection（取交集）、union（取并集并去重）、Sort-Union（合并多个有序数据集）这三种索引合并的方式来执行查询，此时字段值是index_merge
-- 这的意思是优化器可能会使用多个索引来完成一个查询
-- 这个查询语句使用了或，就说明这两个字段都需要进行查询，正好这俩字段都有索引，那么俩索引就一起用了
-- 这个or操作很明显使用union（取并集并去重）更方便一些，直接通过索引把key1和key3满足条件的集合取出再取并集去重，就是最终结果，因此优化器选择了index_merge
explain select * from explain_sample1 where key1='a' or key3='a';
-- 如果我们把条件从or改成and，就会变成ref。而且可以看到，使用的是idx_key1索引
-- 这个语句结果是ref，可能是优化器觉得index_merge不好吧
explain select * from explain_sample1 where key1='a' and key3='a';
-- 如果优化器决定将IN子查询转换为EXISTS子查询，即优化器没办法把子查询转换成连接查询时，而且子查询可以使用到主键或唯一键索引进行等值匹配，那么该子查询的type字段就是unique_subquery
explain select * from explain_sample1 where key2 in (select id from explain_sample2 where explain_sample1.key1=explain_sample2.key1) or key3 ='a';
-- 如果使用索引获取某些范围区间的记录，那么查询的type字段可能会变为range
explain select * from explain_sample1 where key2 in ('a','b','c');
explain select * from explain_sample1 where key1 > 'a' and key1 < 'b';
-- 当我们可以使用索引覆盖，但需要扫描全部的索引记录时，查询的type字段就是index
explain select key_part2 from explain_sample1 where key_part3='a';
-- ALL就是全表扫描，说明没有索引可用
explain select * from explain_sample1;


-- 这他妈是个啥啊
explain select * from explain_sample1 where key2 in (select key2 from explain_sample2 where key2 >=10000);
-- 这他妈又是个啥啊
explain select * from explain_sample1 where key2 in (select key2 from explain_sample2 where key2 >=10036) or key3 ='a';

-- *********************************************************ref*******************************************************************************
-- ref是const，说明是常量
EXPLAIN SELECT * FROM explain_sample1 WHERE key1 = 'a';
-- 如果是与其他表的字段进行等值比较，那么就输出指定表的字段
EXPLAIN SELECT * FROM explain_sample1 INNER JOIN explain_sample2 ON explain_sample1.id = explain_sample2.id;
-- 如果是与函数返回值进行等值比较，那么就输出func表示函数
EXPLAIN SELECT * FROM explain_sample1 INNER JOIN explain_sample2 ON explain_sample2.key1 = UPPER(explain_sample1.key1);
-- ********************************************************filtered********************************************************************************

-- 这个rows在该查询语句表示预估SELECT * FROM explain_sample1 WHERE key1 > 'z' 所查询出来的结果
-- filtered值是上面的结果在进一步经过common_field = 'a'条件筛选后得到的结果占之前结果的比重
-- 公式就是 filtered=过滤后数据总量/过滤前数据总量，因此我们的实际结果行数就是 过滤前结果总量*filtered
EXPLAIN SELECT * FROM explain_sample1 WHERE key1 > 'z' AND common_field = 'a';

-- 它在连接查询中的参考价值比较大，在单表查询中参考意义比较小
-- 这个值越大越好，因为越大表示我们查询出来的数据越符合条件
EXPLAIN SELECT * FROM explain_sample1 INNER JOIN explain_sample2 ON explain_sample1.key1 = explain_sample2.key1 WHERE explain_sample1.common_field = 'a';

-- **********************************************************Extra字段********************************************************************************

-- No tables used表示该SELECT语句并没有使用任何的表
explain select 1;
-- Impossible WHERE表示该SELECT语句的WHERE过滤条件永远都是false
explain select * from explain_sample1 where 1!=1;
-- Using where 表示在全表扫描下使用WHERE过滤
EXPLAIN SELECT * FROM explain_sample1 WHERE common_field = 'a';
-- No matching min/max row 表示一个匹配的字段都没有，表示查询结果一行都没有
explain select min(key1) from explain_sample1 where key1='abcdfgh';
-- 现在向里面加一条，让它存在
insert into explain_sample1(key1,key2) values('abcdfgh',9999999);
-- Select tables optimized away 表示该表被优化了
explain select min(key1) from explain_sample1 where key1='abcdfgh';
-- Using index表示MySQL优化器动用了索引优化表的查询，它说明发生了覆盖索引。
-- 覆盖索引就是MySQL在通过索引查到了结果以后，它的数据项内已经存在了我们想要的字段值了，此时我们就不需要再去回表
-- 一般来说我们的数据项会存放二级索引的指定值和主键，只要我们的select语句查询这些字段，那么覆盖索引就能成立
EXPLAIN SELECT key1,id FROM explain_sample1 WHERE key1 = 'a';
-- Using index condition 表示使用了索引下推
-- 索引下推就是索引在查询 key1 > 'z'时找到了对应的索引值，但是并不立刻回表寻找数据，而是继续向下判断 key1 LIKE '%b' 的条件
-- 在判断完毕后，再把剩下的符合条件的值依据主键进行回表，然后得到结果
EXPLAIN SELECT * FROM explain_sample1 WHERE key1 > 'z' AND key1 LIKE '%b';
-- Using join buffer (hash join)说明被驱动表没办法通过索引加快其访问速度，然后MySQL为其专门开辟出一块内存来加快其访问速度的现象
EXPLAIN SELECT * FROM explain_sample1 INNER JOIN explain_sample2 ON explain_sample1.common_field = explain_sample2.common_field;
-- Not exists表示在连接时指定对应字段的过滤条件是字段为空，但是对应字段又限制了不能为空，因此导致矛盾，从而输出
EXPLAIN SELECT * FROM explain_sample1 LEFT JOIN explain_sample2 ON explain_sample1.key1 = explain_sample2.key1 WHERE explain_sample2.id IS NULL;
-- Using union(idx_key1,idx_key3)表示MySQL在查询过程中准备取索引结果的并集，括号内表示要取并集的两个索引的名称
-- 除此之外还有Using Intersect和Using sort_union
EXPLAIN SELECT * FROM explain_sample1 WHERE key1 = 'a' OR key3 = 'a';
-- Zero limit表示limit子句参数为0时的提示，来告诉我们这是无意义的
EXPLAIN SELECT * FROM explain_sample1 LIMIT 0;

-- Using filesort表示此次查询需要使用文件排序的方式进行查询
-- 这主要是出现在排序操作无法使用到索引的情况下
EXPLAIN SELECT * FROM explain_sample1 ORDER BY common_field LIMIT 10;


-- Using temporary表示MySQL使用了临时表来处理一些查询结果的数据
-- 在执行DISTINCT、GROUP BY、UNION等操作时，如果没办法使用索引进行处理的话，MySQL会考虑使用临时表来解决问题
-- 使用临时表并不是一种好的情况，因为建立与维护临时表需要很大的成本
-- 最好使用索引来替代掉使用临时表
EXPLAIN SELECT DISTINCT common_field FROM explain_sample1;
EXPLAIN SELECT common_field, COUNT(*) AS amount FROM explain_sample1 GROUP BY common_field;
EXPLAIN SELECT key1, COUNT(*) AS amount FROM explain_sample1 GROUP BY key1;







