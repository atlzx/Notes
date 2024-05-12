use atguigudb;

-- ***********************************************************************子查询优化***************************************************************************************

#4. 子查询的优化

#创建班级表中班长的索引
CREATE INDEX idx_monitor ON class(monitor);

#查询班长的信息
EXPLAIN SELECT * FROM student stu1
WHERE stu1.`stuno` IN (
SELECT monitor
FROM class c
WHERE monitor IS NOT NULL
);

EXPLAIN SELECT stu1.* FROM student stu1 JOIN class c 
ON stu1.`stuno` = c.`monitor`
WHERE c.`monitor` IS NOT NULL;

#查询不为班长的学生信息
EXPLAIN SELECT a.* 
FROM student a 
WHERE  a.stuno  NOT  IN (
			SELECT monitor FROM class b 
			WHERE monitor IS NOT NULL);


EXPLAIN SELECT a.*
FROM  student a LEFT OUTER JOIN class b 
ON a.stuno =b.monitor
WHERE b.monitor IS NULL;



-- ***********************************************************************排序优化***************************************************************************************



#5. 排序优化
#删除student和class表中的非主键索引
CALL proc_drop_index('atguigudb2','student');
CALL proc_drop_index('atguigudb2','class');
drop index stu_idx_ on student;

SHOW INDEX FROM student;
SHOW INDEX FROM class;


-- 这里不会使用索引，决定权在优化器手里，它可能认为使用索引耗费的成本比FileSort要大把
EXPLAIN SELECT * FROM student ORDER BY age,classid;
-- 加了LIMIT语句之后，就使用索引了
EXPLAIN SELECT * FROM student ORDER BY age,classid LIMIT 10; 

#过程二：order by时不limit，索引失效
#创建索引  
CREATE  INDEX idx_age_classid_name ON student (age,classid,NAME);


#不限制,索引失效
EXPLAIN  SELECT * FROM student ORDER BY age,classid; 
-- 这里使用了索引覆盖
EXPLAIN  SELECT age,classid,name,id FROM student ORDER BY age,classid;

#增加limit过滤条件，使用上索引了。
EXPLAIN  SELECT * FROM student ORDER BY age,classid LIMIT 10;  


#过程三：order by时顺序错误，索引失效

#创建索引age,classid,stuno
CREATE  INDEX idx_age_classid_stuno ON student (age,classid,stuno); 

#以下哪些索引失效?
EXPLAIN  SELECT * FROM student ORDER BY classid LIMIT 10;  -- 根本就不存在该索引，相当于失效

EXPLAIN  SELECT * FROM student ORDER BY classid,NAME LIMIT 10;   -- 这个也一样

EXPLAIN  SELECT * FROM student ORDER BY age,classid,stuno LIMIT 10;  -- 这个不会失效

EXPLAIN  SELECT * FROM student ORDER BY age,classid LIMIT 10;  -- 这个也不会失效

EXPLAIN  SELECT * FROM student ORDER BY age LIMIT 10;  -- 这个也不会失效，根据key_len字段来看，它使用了

-- order by时规则不一致, 索引失效 （顺序错，不索引；方向反，不索引）

EXPLAIN  SELECT * FROM student ORDER BY age DESC, classid ASC LIMIT 10;  -- 这个排序依据与索引不一致，不会使用索引

EXPLAIN  SELECT * FROM student ORDER BY classid DESC, NAME DESC LIMIT 10;  -- 这个跟上面一样

EXPLAIN  SELECT * FROM student ORDER BY age ASC,classid DESC LIMIT 10;  -- 一样

EXPLAIN  SELECT * FROM student ORDER BY age DESC, classid DESC LIMIT 10;  -- 一样

-- 无过滤，不索引

EXPLAIN  SELECT * FROM student WHERE age=45 ORDER BY classid;  -- 使用了索引，但是从key_len来看，只是用了索引的age字段

EXPLAIN  SELECT * FROM student WHERE  age=45 ORDER BY classid,NAME;  -- 使用了索引，但是从key_len来看，只是用了索引的age字段

EXPLAIN  SELECT * FROM student WHERE  classid=45 ORDER BY age;  -- 没使索引，因为根本就没有索引

EXPLAIN  SELECT * FROM student WHERE  classid=45 ORDER BY age LIMIT 10;  -- 使用了索引，而且从key_len来看使用了全部的索引

CREATE INDEX idx_cid ON student(classid);
EXPLAIN  SELECT * FROM student WHERE  classid=45 ORDER BY age;

#实战：测试filesort和index排序
CALL proc_drop_index('atguigudb2','student');

EXPLAIN SELECT * FROM student WHERE age = 30 AND stuno <101000 ORDER BY NAME ;

#方案一: 为了去掉filesort我们可以把索引建成

CREATE INDEX idx_age_name ON student(age,NAME);
-- 使用了索引，但是从key_len看，仅使用了age字段。从Extra字段来看，出现了Using index condition，意味着使用了索引下推
EXPLAIN SELECT * FROM student WHERE age = 30 AND stuno <101000 ORDER BY NAME ;

#方案二：

CREATE INDEX idx_age_stuno_name ON student(age,stuno,NAME);
-- 使用了索引，但是从key_len看，仅使用了age和stuno字段。从Extra字段来看，出现了Using index condition，意味着使用了索引下推
EXPLAIN SELECT * FROM student WHERE age = 30 AND stuno <101000 ORDER BY NAME ;-- Using index condition; Using filesort