
-- ***********************************************************************准备工作*****************************************************************************************


CREATE TABLE `class` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`className` VARCHAR(30) DEFAULT NULL,
`address` VARCHAR(40) DEFAULT NULL,
`monitor` INT NULL ,
PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `student` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`stuno` INT NOT NULL ,
`name` VARCHAR(20) DEFAULT NULL,
`age` INT(3) DEFAULT NULL,
`classId` INT(11) DEFAULT NULL,
PRIMARY KEY (`id`)
#CONSTRAINT `fk_class_id` FOREIGN KEY (`classId`) REFERENCES `t_class` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

set global log_bin_trust_function_creators=1; # 不加global只是当前窗口有效。

#随机产生字符串
DELIMITER //
CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)
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
#假如要删除
#drop function rand_string;



#用于随机产生多少到多少的编号
DELIMITER //
CREATE FUNCTION rand_num (from_num INT ,to_num INT) RETURNS INT(11)
BEGIN
DECLARE i INT DEFAULT 0;
SET i = FLOOR(from_num +RAND()*(to_num - from_num+1)) ;
RETURN i;
END //
DELIMITER ;
#假如要删除
#drop function rand_num;



#创建往stu表中插入数据的存储过程
DELIMITER //
CREATE PROCEDURE insert_stu( START INT , max_num INT )
BEGIN
DECLARE i INT DEFAULT 0;
SET autocommit = 0; #设置手动提交事务
REPEAT #循环
SET i = i + 1; #赋值
INSERT INTO student (stuno, name ,age ,classId ) VALUES
((START+i),rand_string(6),rand_num(1,50),rand_num(1,1000));
UNTIL i = max_num
END REPEAT;
COMMIT; #提交事务
END //
DELIMITER ;
#假如要删除
#drop PROCEDURE insert_stu;


#执行存储过程，往class表添加随机数据
DELIMITER //
CREATE PROCEDURE `insert_class`( max_num INT )
BEGIN
DECLARE i INT DEFAULT 0;
SET autocommit = 0;
REPEAT
SET i = i + 1;
INSERT INTO class ( classname,address,monitor ) VALUES
(rand_string(8),rand_string(10),rand_num(1,100000));
UNTIL i = max_num
END REPEAT;
COMMIT;
END //
DELIMITER ;
#假如要删除
#drop PROCEDURE insert_class;

#执行存储过程，往class表添加1万条数据
CALL insert_class(10000);

#执行存储过程，往stu表添加50万条数据
CALL insert_stu(100000,500000);


DELIMITER //
CREATE PROCEDURE `proc_drop_index`(dbname VARCHAR(200),tablename VARCHAR(200))
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE ct INT DEFAULT 0;
DECLARE _index VARCHAR(200) DEFAULT '';
DECLARE _cur CURSOR FOR SELECT index_name FROM
information_schema.STATISTICS WHERE table_schema=dbname AND table_name=tablename AND
seq_in_index=1 AND index_name <>'PRIMARY' ;
#每个游标必须使用不同的declare continue handler for not found set done=1来控制游标的结束
DECLARE CONTINUE HANDLER FOR NOT FOUND set done=2 ;
#若没有数据返回,程序继续,并将变量done设为2
OPEN _cur;
FETCH _cur INTO _index;
WHILE _index<>'' DO
SET @str = CONCAT("drop index " , _index , " on " , tablename );
PREPARE sql_str FROM @str ;
EXECUTE sql_str;
DEALLOCATE PREPARE sql_str;
SET _index='';
FETCH _cur INTO _index;
END WHILE;
CLOSE _cur;
END //
DELIMITER ;

CALL proc_drop_index("dbname","tablename");

-- ************************************************************************索引失效案例************************************************************************************

-- 1.如果进行全值匹配，但未创建对应字段的索引，会导致索引失效。
-- 这是理所当然的，因为索引都不存在，优化器根本不知道去哪里找
explain select * from student where age=30;

-- drop index student_age on student;
-- 这里创建一下针对age的索引，再进行分析就可以发现优化器使用了索引
create index student_age on student(age);
show index from student;

-- 2.不满足最佳左前缀原则

create index stu_idx_ on student(age,classId,name);
drop index stu_idx_ on student;
-- 可以看到即使where子句中三个字段的条件对应的字段顺序与索引中定义的字段的顺序不一致，但他们是用and连接起来的，因此优化器能够认出来它可以使用对应索引
explain select * from student where classId=2 and name = 'abc' and age=20;
-- 这个玩意不满足最佳左前缀原则，因为它没有对应的age字段，因此不会使用索引
-- 原因就是在该索引的B+树结构中，最高优先级的排列字段是以age为首的，如果使用该索引对应的B+树，我们的WHERE子句并没有提供相对应的age的条件，这就使得我们无法利用WHERE子句提供的条件在该索引下进行age相关的树型查询操作，但是该索引下它必须让我们先提供age的值，才能准确的告诉我们我们想得到的数据具体在哪一个区间内。而我们没有age的相关条件，这就导致我们没办法利用该索引进行n分查找，从而导致利用该索引的情况下，实际比使用聚簇遍历成本还高，因为二级索引遍历再找到值之后，还需要执行回表操作。
explain select * from student where classId=2 and name = 'abc';

-- 3.计算、函数与类型转换

-- 上面的三种情况均会导致索引失效

create index student_name on student(name);
create index student_age on student(age);
drop index student_age on student;
drop index student_name on student;
-- 这个是能用索引的
explain select * from student where name like 'abc%';
-- 使用了函数的无法使用索引，因为优化器不知道经过函数处理之后的这个字段是个什么东西，因为我们的字段是建立在字段之上的，但是函数的返回值已经不是表的原生字段了
explain select * from student where left(name,3) = 'abc';
-- name是字符串的，传了一个常数，会发生自动类型转换
EXPLAIN SELECT * FROM student WHERE name=123;
-- 这他妈啥啊
EXPLAIN SELECT * FROM student WHERE age='aaa';

-- 4.范围条件右边的列索引失效(MySQL8.0.36测试，索引依旧生效，可能是低版本的是无效的)

create index stu_idx_ on student(age,classId,name);
drop index stu_idx_ on student;
-- 这特么是啥啊
EXPLAIN SELECT * FROM student WHERE student.age=30 AND student.classId>20 AND student.name ='abc';

-- 5.不等于(!= 或<>)索引失效
-- 示例
EXPLAIN SELECT * FROM student WHERE name!='123';
EXPLAIN SELECT * FROM student WHERE name='123';

-- 6.is not null索引失效

EXPLAIN SELECT * FROM student WHERE name is not null;
-- 对比组
EXPLAIN SELECT * FROM student WHERE name is null;

-- 7. like以通配符%开头索引失效

EXPLAIN SELECT * FROM student WHERE name like '%abc';
-- 对比组
EXPLAIN SELECT * FROM student WHERE name like 'abc%';

-- 8.OR 前后存在非索引的列，索引失效
-- 如果我们创建该索引，发现下面的示例依然是不会使用的
create index stu_age_classid on student(age,classid);
drop index stu_age_classid on student;
-- 由于age索引之前已经创建好了，现在只需要创建classid索引即可
create index stu_classid on student(classid);
drop index stu_classid on student;
-- 这个or操作需要单独的二级索引而不是联合索引
-- 也就是说，它需要age和classid的两个独立的二级索引
EXPLAIN SELECT SQL_NO_CACHE * FROM student WHERE age = 10 OR classid = 100;


