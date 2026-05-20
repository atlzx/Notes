USE atguigudb;

-- 执行下面的sql语句可以看出:除法运算或者有浮点数参与的运算，结果是小数

SELECT 100, 100 + 0, 100 - 0, 100 + 50, 100 + 50 -30, 100 + 35.5, 100 - 35.5 FROM dual

-- 除以0得到NULL

SELECT 100, 100 * 1, 100 * 1.0, 100 / 1.0, 100 / 2,100 + 2 * 5 / 2,100 /3, 100 DIV 0 FROM dual;

-- 求余运算得到的结果与被余数的正负符号相同

SELECT 12 % 3, 12 MOD 5 , 12 MOD -5 ,-12 % 5 , -12 % -5 FROM dual;

-- 一般来说，如果参与运算的值之间有数值，那么mysql会尽量将参与运算的值全都转换为数值进行运算
-- 但如果转换不了，就统一转换为0进行处理
-- 'a'是转换不了的，因此被转换为0
-- 如果是两个字符串之间进行比较，那么没有这个问题，它们会通过其编码值进行比较
-- NULL与任何值进行比较都是NULL

SELECT 1 = 1, 1 = '1', 1 = 0, 'a' = 'a', 0 = 'ab' , (5 + 3) = (2 + 6), '' = NULL , NULL = NULL; 

-- <=>运算符可以区分NULL，也就是两个NULL进行相等判断时返回1
-- 该运算符与 IS NULL效果是一致的
-- 与NULL判断相关的有 IS NULL、ISNULL函数、<=>、IS NOT NULL四个

SELECT NULL <=> NULL ;
SELECT NULL IS NULL;
SELECT NULL IS NOT NULL, 'a' IS NOT NULL,  1 IS NOT NULL; 
SELECT NULL IS NULL, ISNULL(NULL), ISNULL('a'), 1 IS NULL;

-- 最小值函数LEAST与最大值函数GREATEST会返回最小值和最大值
-- 参数中存在NULL时返回NULL

SELECT LEAST (1,0,2), LEAST('b','a','c'), LEAST(1,NULL,2);
SELECT GREATEST(1,0,2), GREATEST('b','a','c'), GREATEST(1,NULL,2);

-- BETWEEN .... AND ... 通常用来判断值是否处在某一区间内

SELECT 1 BETWEEN 0 AND 1, 10 BETWEEN 11 AND 12, 'b' BETWEEN 'a' AND 'c';

-- IS 关键字用来判断值是否在某一集合内

SELECT employee_id, last_name, salary, manager_id
FROM   employees
WHERE  manager_id IN (100, 101, 201);

-- LIKE关键字用来进行模糊匹配
-- _用来表示一个字符
-- %用来表示0个或多个字符

-- 下面的sql语句筛选的是第二个字符是o的名字
SELECT last_name
FROM   employees
WHERE  last_name LIKE '_o%';

-- 筛选名字中既有a也有k的名字
-- 下面给出两种实现方式
-- 由于MySQL并未完全实现SQL规范，导致匹配时不区分大小写

SELECT last_name
FROM   employees
-- WHERE  last_name LIKE '%a%k%' OR last_name LIKE '%k%a%';
WHERE last_name LIKE '%a%' AND last_name LIKE '%k%';


-- ESCAPE关键字用来指定转义字符，指定后，默认的'\'转义字符会失效

-- 筛选以'IT_'开头的字符,指定$为转义字符
SELECT job_id
FROM   jobs
WHERE  job_id LIKE 'IT$_%' escape '$';

-- REGEXP关键字用来执行正则匹配

-- 匹配 开头为字符s、字符串结尾最后一个字符是t、字符串中包含`hk`
SELECT 'shkstart' REGEXP '^s', 'shkstart' REGEXP 't$', 'shkstart' REGEXP 'hk';

-- 位运算不怎么常用

SELECT 12 & 5, 12 | 5,12 ^ 5 FROM DUAL;

-- 逻辑运算符的&&优先级大于||，使用时应注意，它并不会遵从从左到右执行的原则

SELECT employee_id,salary FROM employees
WHERE NOT (salary >= 9000 AND salary <= 12000);


