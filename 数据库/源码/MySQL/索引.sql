
use atguigudb;

drop table if exists index1;

create table if not exists index1(
	user_id int primary key auto_increment,
	user_name varchar(20) not null,
	address varchar(50) unique not null,
	index my_index(user_name),  -- 创建一个普通索引
	/*
		创建一个联合索引，并提供升降序要求
		可以看到我们给索引的字段的先后顺序依次是user_id,user_name,address
		那么索引的默认的排序依据就是先排user_id,碰到user_id相同时再看user_name,user_name相同时再看address
	*/
	index my_index2(user_id asc,user_name desc,address asc)
);

-- 使用ALTER关键字进行索引的创建
alter table index1 add index my_index3(user_id asc,user_name desc);

-- 使用create关键字进行索引的创建
create index my_index4 on index1(user_name desc);

-- 查看指定表上的索引
show index from index1;

-- 使用DROP关键字进行索引删除
DROP index my_index4 on index1;

-- 使用ALTER关键字进行索引的删除
alter table index1 drop index my_index3;