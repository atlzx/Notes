

-- 使用JOIN ON 进行多表查询
-- on子句是连接的依据，因此它会在表连接时起作用
-- 表完成连接以后，WHERE语句将会对新连接的表进行过滤，得到过滤后的表
SELECT last_name,department_name
FROM employees e LEFT JOIN departments d
ON e.department_id=d.department_id
WHERE department_name IS NULL
ORDER BY last_name;

-- UNION ALL不会执行去重操作
SELECT employee_id,last_name,department_name
FROM employees e LEFT  JOIN departments d
ON e.department_id=d.department_id
UNION ALL
SELECT employee_id,last_name,department_name
FROM employees e RIGHT JOIN departments d
ON e.department_id=d.department_id
WHERE e.department_id is null;

-- UNION会执行去重操作，因此效率较UNION ALL更低
SELECT employee_id,last_name,department_name
FROM employees e LEFT  JOIN departments d
ON e.department_id=d.department_id
where d.department_id is NULL
UNION
SELECT employee_id,last_name,department_name
FROM employees e RIGHT JOIN departments d
ON e.department_id=d.department_id
WHERE e.department_id is null;

-- C99规定了使用NATURAL JOIN进行自然连接的语句
-- 它会自动查询两个表内相同的属性名下的字段是否相等，然后做等值连接
SELECT employee_id,last_name,department_name
FROM employees e NATURAL JOIN departments d;

-- 上面的自然连接语句等价于下面的语句:
SELECT employee_id,last_name,department_name
FROM employees e,departments d
where e.department_id=d.department_id and e.manager_id=d.manager_id;

-- C99规定了使用USING指定连接依据
-- 使用USING指定两表连接的属性名，要求两个表的属性名必须一致

SELECT employee_id,last_name,department_name
FROM employees e join departments d
USING(department_id,manager_id);
