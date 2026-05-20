USE atguigudb;

-- 返回job_id与员工号为141的员工相同，salary比员工号为143的员工多的员工姓名，job_id和工资

select last_name,job_id,salary
from employees
where job_id=(
	select job_id
	from employees
	where employee_id=141
)
AND
salary>(
	select salary
	from employees
	where employee_id=143
);

-- 如果子查询查出来的是空值，那么子查询不返回任何行

SELECT last_name, job_id
FROM   employees
WHERE  job_id =
                (SELECT job_id
                 FROM   employees
                 WHERE  last_name = 'Haas');

-- 返回公司工资最少的员工的last_name,job_id和salary

select last_name,job_id,salary
from employees
where salary=(
	select min(salary)
	from employees
);