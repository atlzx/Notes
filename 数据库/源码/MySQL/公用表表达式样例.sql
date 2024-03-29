
-- 查询员工所在的部门的详细信息

with dep_tab
as (
	select *
	from departments
)
select * from employees e left join dep_tab d on e.department_id=d.department_id;

-- 递归查询

with recursive cte
as (
	select employee_id,last_name,manager_id,1 as n from employees where manager_id is null
	union ALL
	select e.employee_id,e.last_name,e.manager_id,n+1 as n from employees e join cte d on e.manager_id=d.employee_id
)
select * from cte where n>=3;