
use atguigudb;




-- 这是最简单的select语句
select 3*2,2-1;

-- 该语句与上面的语句是等价的
-- dual关键字表示伪表
select 3*2,2-1 from dual;

-- *表示所有的列，因此下面语句的含义就是查询该表，内容包含所有列的信息

select * from employees;

-- 列的别名:
  -- 方式一:使用 列名(空格)别名的方式
	-- 方式二:使用as关键字，该as关键字全称为alias
	-- 方式三:使用双引号,MySQL并没有严格实现SQL语法标准，因此MySQL中使用单引号括起来也是可以的，但不要这样做
	
SELECT last_name AS name, commission_pct comm
FROM   employees;

SELECT last_name "Name", salary*12 "Annual Salary"
FROM   employees;

-- 去除重复行可以使用distinct关键字进行
	-- 当distinct作用于一个列时，它表示对该列进行去重。作用于多个列时，表示对每个列属性组成的集合去重，因此作用于多个列时可能会出现单列重复现象
	-- 当各列的查询行数不一致时，会报错

-- 正常查询会出现重复数据
SELECT department_id
FROM   employees;
-- 使用distinct关键字可以去重
SELECT DISTINCT department_id
FROM   employees;

-- 这会导致salary对应的行数与department_id经去重后对应的行数不匹配，报错
-- 换句话说，distinct关键字应该写在所有列名的前面
SELECT salary, DISTINCT department_id 
FROM employees;

-- distinct作用于多个列时可能会出现单列重复现象，但每行整体都是不一样的
SELECT DISTINCT department_id,salary 
FROM employees;

-- 如果使用空值进行运算，那么结果必定为空值NULL，因为NULL不等同于0，也不等同于''和'null'

SELECT employee_id,salary,commission_pct,
12 * salary * (1 + commission_pct) "annual_sal"
FROM employees;

-- 反引号进行反关键字化
-- 可以看到我们的表里面有一个叫order的表，但是这个表与sql内的order关键字冲突
-- 为了让mysql知道我们想操作的是这个表而不是使用这个关键字，我们需要将它用一对反引号括起来

-- 这是错误示范
SELECT * FROM ORDER;

-- 正解

SELECT * FROM `ORDER`;

-- select语句可以进行常数查询，也就是增加固定的常数列

select '尚硅谷' as corporation, last_name FROM employees;
select '尚硅谷' as corporation;  # 表结构完全是常数查询时，可以忽略from 表名的语法，此时实际上查询得到的是伪表，即上面的dual关键字所对应的表 


-- 使用description或desc关键字可以显示数据库表的信息

DESCRIBE employees;
desc employees;

-- where 子句可以进行条件过滤以筛选出来符合条件的数据
-- 它必须与from语句挨在一起
-- 语法: select 列名 from 表名 where 筛选条件

SELECT employee_id, last_name, job_id, department_id
FROM   employees
WHERE  department_id = 90 ;