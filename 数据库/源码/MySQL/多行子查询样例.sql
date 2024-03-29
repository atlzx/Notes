-- 查询平均工资最低的部门id

-- 方法一:

SELECT department_id
FROM employees
GROUP BY department_id
HAVING AVG(salary) = (
			SELECT MIN(avg_sal)
			FROM (
				SELECT AVG(salary) avg_sal
				FROM employees
				GROUP BY department_id
				) dept_avg_sal
			)
			
			
-- 方法二

SELECT department_id
FROM employees
GROUP BY department_id
HAVING AVG(salary) <= ALL (
				SELECT AVG(salary) avg_sal
				FROM employees
				GROUP BY department_id
)

-- 方法三

select department_id,avg(salary) avg_sal
from employees
where department_id is not null
group by department_id
order by avg_sal asc
limit 0,1;
