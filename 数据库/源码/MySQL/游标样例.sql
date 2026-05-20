
-- 创建存储过程“get_count_by_limit_total_salary()”，声明IN参数limit_total_salary，DOUBLE类型；声明OUT参数total_count，INT类型。
-- 函数的功能可以实现累加薪资最高的几个员工的薪资值，直到薪资总和达到limit_total_salary参数的值，返回累加的人数给total_count。


delimiter //

create procedure get_count_by_limit_total_salary(in limit_total_salary double,out total_count int)

BEGIN

	declare sum int default 0;
	declare curr_sal int default 0;
	declare count int default 0;
	
	declare sal_cursor cursor for select salary from employees order by salary desc;
	open sal_cursor;
	
	while sum<limit_total_salary DO
		fetch sal_cursor into curr_sal; 
		set sum=sum+curr_sal;
		set count=count+1;
	END while;
	
	set total_count=count;
	
END //

delimiter ;

set @limit_total_salary=100000;

set @total_count=0;

call get_count_by_limit_total_salary(@limit_total_salary,@total_count);

select @total_count;

drop procedure get_count_by_limit_total_salary;

select * from employees order by salary desc;

update employees set salary=salary*(rand()*1.5+0.5);