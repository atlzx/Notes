-- 查询所有部门的名字，location_id，员工数量和平均工资，并按平均工资降序

SELECT department_name, location_id, COUNT(employee_id), AVG(salary) avg_sal 
FROM employees e RIGHT 
JOIN departments d 
ON e.`department_id` = d.`department_id` 
GROUP BY department_name, location_id 
ORDER BY avg_sal DESC;

-- WHERE子句不能调用聚合函数

SELECT   department_id, AVG(salary)
FROM     employees
WHERE    AVG(salary) > 8000
GROUP BY department_id;

-- 改用HAVING子句可以解决这一问题

SELECT   department_id, MAX(salary)
FROM     employees
GROUP BY department_id
HAVING   MAX(salary)>10000 ;