USE atguigudb;


-- ORDER BY 子句可以用来排序，其中ASC表示升序排序，不写默认为升序。DESC为降序排序
-- 多级排序，下面以department_id升序排序(不写排序方式默认ASC)，再salary降序排序为例

SELECT last_name, department_id, salary
FROM   employees
ORDER BY department_id, salary DESC;


-- 下面的语句会报错,因为WHERE子句不认识sal,也就是说WHERE子句不能使用列的别名
-- 这是因为给列取别名的操作是在WHERE子句执行后才进行的,也就是说MySQL执行时并不是从上往下顺序执行的
SELECT last_name, department_id, salary as sal
FROM   employees
WHERE sal>2000
ORDER BY department_id, sal DESC;


-- LIMIT子句用来针对查询得到的结果进行切片，因此它需要写在查询语句的最后
-- MySQL8.0引入了OFFSET来指定对应的偏移量
-- 因此,下面两个情况得到的结果是一致的

SELECT * FROM employees LIMIT 20,10;
SELECT * FROM employees LIMIT 10 OFFSET 20;

