-- 若employees表中employee_id与job_history表中employee_id相同的数目不小于2，输出这些相同id的员工的employee_id,last_name和其job_id

SELECT e.employee_id, last_name,e.job_id
FROM   employees e 
WHERE  2 <= (
   SELECT COUNT(*)
   FROM   job_history 
   WHERE  employee_id = e.employee_id
);


-- 查询公司管理者的employee_id，last_name，job_id，department_id信息

-- 方法1

SELECT DISTINCT e2.employee_id,e2.last_name,e2.job_id,e2.department_id
FROM employees e1
JOIN employees e2
ON e1.manager_id = e2.employee_id;

-- 方法2

SELECT employee_id,last_name,job_id,department_id
FROM employees
WHERE employee_id IN (
	SELECT DISTINCT manager_id
	FROM employees
);

-- 方法3

SELECT employee_id, last_name, job_id, department_id
FROM   employees manager
WHERE  EXISTS ( 
	SELECT *
  FROM   employees worker
  WHERE  worker.manager_id = manager.employee_id
);

-- 查询departments表中，不存在于employees表中的部门的department_id和department_name

SELECT department_id, department_name
FROM departments d
WHERE NOT EXISTS (
	SELECT 'X'
	FROM   employees
	WHERE  department_id = d.department_id);
				 
				 
						 
		 