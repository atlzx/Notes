use atguigudb;


-- ****************************************************************************数据准备***********************************************************************************

create table if not exists index_sample1(
	id int primary key auto_increment,
	name varchar(20) not null,
	age int not null
);


create table if not exists index_sample2(
	id int primary key auto_increment,
	name varchar(20) not null,
	age int not null
);

insert into index_sample1(name,age) values('aaa',10),('bbb',10),('ccc',10),('ddd',10),('eee',10),('fff',10),('ggg',10),('hhh',10),('iii',10);
insert into index_sample2(name,age) values('jjj',10),('kkk',10),('lll',10),('mmm',10),('nnn',10),('ooo',10),('ppp',10),('qqq',10);

-- ****************************************************内连接与外连接************************************************************************

-- 如果我们进行左外连接，连接依据还不是主键，那么结果就是两个表的查询类型全是ALL，不过被联动表改成了Using join buffer (hash join)，即使用了内存加快访问速度
explain select * from index_sample1 i1 left join index_sample2 i2 on i1.name=i2.name;
-- 现在给被联动表添加对应的字段索引，再分析发现优化器使用了被联动表的索引
create index idx_name on index_sample2(name);
-- 现在让我们删掉被联动表的索引，再给联动表加上索引，再进行测试，我们会发现i1并没有使用这个索引，因为它是联动表，它本来就要遍历表内的每一个数据
drop index idx_name on index_sample2;
create index idx_name on index_sample1(name);
-- 接下来查看内连接的(在保持index_sample1表的索引不删除的前提下)
-- 我们可以看到原来在左外连接的联动表跑到了被联动表的位置，因此可以得出结论:如果连接双方仅有一个有索引，那么有索引的那一方会成为被联动表
explain select * from index_sample1 i1 inner join index_sample2 i2 on i1.name=i2.name;
-- 接下来把index_sample1表的索引去掉，再进行一次内连接。以进行对比
-- 发现去掉了索引，index_sample1就变成联动表了，因此并不是内连接使得联动表发生了变化，而是索引使得联动表发生了变化
drop index idx_name on index_sample1;
explain select * from index_sample1 i1 inner join index_sample2 i2 on i1.name=i2.name;
-- 接下来向两个表都添加索引，再进行一次内连接
create index idx_name on index_sample2(name);
create index idx_name on index_sample1(name);
-- 可以发现执行的最后结果依然是index_sample1表成为了联动表，出现此现象的原因是index_sample2表中的数据量比index_sample1表中的数据量小，优化器认为让数据量小的表做联动表，成本更低
-- 关于此处的解释，详见笔记下面的join语句原理
explain select * from index_sample1 i1 inner join index_sample2 i2 on i1.name=i2.name;
-- 接下来向index_sample2中插入两条数据，使得其数据量大于index_sample1
insert into index_sample2(name,age) values('rrr',10),('sss',10);
-- 再内连接，发现index_sample1表成为了联动表
explain select * from index_sample1 i1 inner join index_sample2 i2 on i1.name=i2.name;
-- 另外，我们再来看看左外连接会怎么样
-- 我们需要先删掉index_sample2的两行数据
delete from index_sample2 where name='rrr' or name='sss';
-- 可以看到在两个表都有索引的前提下，依然让index_sample1做了联动表
explain select * from index_sample1 i1 left join index_sample2 i2 on i1.name=i2.name;
