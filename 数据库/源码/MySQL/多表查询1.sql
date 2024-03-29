USE atguigudb;

-- 下面的多表查询会出现笛卡尔积现象

SELECT last_name, department_name
FROM employees, departments;

-- 案例：查询员工的姓名及其部门名称
-- 指定了正确的连接条件后，便不会再出现笛卡尔积问题
SELECT last_name, department_name
FROM employees, departments
WHERE employees.department_id = departments.department_id;

-- 可以像给列起别名一样给表起别名，起别名后必须使用别名
-- 当列名被多个表共有时，也需要指定该列名具体属于哪个表

SELECT e.employee_id, e.last_name, e.department_id,
       d.department_id, d.location_id
FROM   employees e , departments d
WHERE  e.department_id = d.department_id;

