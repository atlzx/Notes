
-- 声明存储过程“update_salary_by_eid1”，定义IN参数emp_id，输入员工编号。判断该员工薪资如果低于8000元并且入职时间超过5年，就涨薪500元；否则就不变。


use atguigudb;

desc employees;

delimiter //

create procedure update_salary_by_eid1(IN emp_id int)

BEGIN

	declare `time` int default 0; 
	declare sal double default 0.0; 

	-- 	DATEDIFF函数返回两个日期之间相差的天数
	select DATEDIFF(CURRENT_DATE(),hire_date)/365 into `time`
	from employees 
	where employee_id=emp_id;
	
	
	select salary into sal
	from employees 
	where employee_id=emp_id;
	
	if sal<=8000 and `time`>5 then
		update employees set salary=salary+500 where employee_id=emp_id;
	end if;

END//


delimiter ;


drop procedure update_salary_by_eid1;


select * from employees;

set @emp_id = 105;

call update_salary_by_eid1(@emp_id);


-- 声明存储过程“update_salary_by_eid3”，定义IN参数emp_id，输入员工编号。判断该员工薪资如果低于9000元，就更新薪资为9000元；
-- 薪资如果大于等于9000元且低于10000的，但是奖金比例为NULL的，就更新奖金比例为0.01；其他的涨薪100元。


delimiter //

create procedure update_salary_by_eid3(IN emp_id int)

BEGIN

	declare `time` int default 0; 
	declare sal double default 0.0; 
	declare com_pct double default null;

	-- 	DATEDIFF函数返回两个日期之间相差的天数
	select DATEDIFF(CURRENT_DATE(),hire_date)/365 into `time`from employees where employee_id=emp_id;
	select salary into sal from employees where employee_id=emp_id;
	select commission_pct into com_pct from employees where employee_id=emp_id;
	
	-- 根据尝试，分支结构可以嵌套	
	case 
		when sal<9000 THEN
			update employees set salary=9000 where employee_id=emp_id;
		when sal>=9000 and sal<10000 THEN
			if com_pct is null THEN
				update employees set commission_pct=0.01 where employee_id=emp_id;
			end if;
		ELSE
			update employees set salary=salary+100 where employee_id=emp_id;
	end case;

END//

delimiter ;

call update_salary_by_eid3(105);
call update_salary_by_eid3(103);
call update_salary_by_eid3(100);

drop procedure update_salary_by_eid3;

