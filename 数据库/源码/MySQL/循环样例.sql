
-- 当市场环境变好时，公司为了奖励大家，决定给大家涨工资。声明存储过程“update_salary_loop()”，声明OUT参数num，输出循环次数。
-- 存储过程中实现循环给大家涨薪，薪资涨为原来的1.15倍。直到全公司的平均薪资达到13000结束。并统计循环次数。


delimiter //

create procedure update_salary_loop(OUT num int)

begin

	-- 局部变量使用DECLARE关键字进行声明
	-- 局部变量的定义与声明必须写在begin的最开始，不然会报错
	declare current_avg_sal double default 0;
	declare count int default 0;
	
	label:loop
		select avg(salary) into current_avg_sal from employees;
		if current_avg_sal>13000 then
			leave label;
		ELSE
			UPDATE employees set salary = salary*1.15;
			set count=count+1;
		end if;
	
	end loop label;
	
	set num=count;

end //

delimiter ;

select avg(salary) from employees;

set @num = 0;

call update_salary_loop(@num);

drop PROCEDURE update_salary_loop;

select @num;



-- 市场环境不好时，公司为了渡过难关，决定暂时降低大家的薪资。声明存储过程“update_salary_while()”，声明OUT参数num，输出循环次数。
-- 存储过程中实现循环给大家降薪，薪资降为原来的90%。直到全公司的平均薪资达到5000结束。并统计循环次数。


delimiter //

create procedure update_salary_while(OUT num int)

BEGIN

	-- 	局部变量使用DECLARE关键字进行声明
	declare avg_sal double default 0;
	declare count int default 0;

	select avg(salary) into avg_sal from employees;

	while avg_sal>5000 DO
		update employees set salary=salary*0.9;
		-- 不要忘记每次循环都更新avg_sal,不然会导致无限循环		
		select avg(salary) into avg_sal from employees;
		set count=count+1;
	END while;
	
	set num=count;

END //

delimiter ;

set @num=0;

call update_salary_while(@num);

select @num;

select avg(salary) from employees;

drop procedure update_salary_while;

-- 当市场环境变好时，公司为了奖励大家，决定给大家涨工资。声明存储过程“update_salary_repeat()”，声明OUT参数num，输出循环次数。
-- 存储过程中实现循环给大家涨薪，薪资涨为原来的1.1倍。直到全公司的平均薪资达到12000结束。并统计循环次数。

delimiter //

create procedure update_salary_repeat(OUT num int)

BEGIN

	declare count int default 0;
	declare avg_sal double default 0;
	
	select avg(salary) into avg_sal from employees;
	
	-- repeat循环无论如何都会至少执行一次
	repeat
		update employees set salary=salary*1.1;
		select avg(salary) into avg_sal from employees;
		set count=count+1;
	UNTIL avg_sal>=12000  -- 这里不要加分号
	end repeat;
	set num=count;

END //

delimiter ;

set @num=0;

call update_salary_repeat(@num);

select avg(salary) from employees;

select @num;

drop PROCEDURE update_salary_repeat;


-- 下面是leave和iterate样例


-- 当市场环境不好时，公司为了渡过难关，决定暂时降低大家的薪资。声明存储过程“leave_while()”，声明OUT参数num，输出循环次数，
-- 存储过程中使用WHILE循环给大家降低薪资为原来薪资的90%，直到全公司的平均薪资小于等于10000，并统计循环次数。

delimiter //

create procedure leave_while(OUT num int)

super_label:BEGIN
	

	-- 	局部变量使用DECLARE关键字进行声明
	declare avg_sal double default 0;
	declare count int default 0;
	
	select num;  -- 传入的num实际上是个null，在被赋值时才会被查询到值

	select avg(salary) into avg_sal from employees;

	label:while TRUE DO
		if avg_sal<=10000 THEN
			leave super_label;  -- leave可以通过begin的标签直接使存储过程结束
		END if;
		update employees set salary=salary*0.9;
		select avg(salary) into avg_sal from employees;
		set count=count+1;
	END while;
	
	set num=count;

END //

delimiter ;

set @num=0;

select @num;

call leave_while(@num);

select avg(salary) from employees;

select @num;

drop procedure leave_while;

-- 定义局部变量n，初始值为0。循环结构中执行n + 1操作。
-- 如果n < 10，则继续执行循环；
-- 如果n > 15，则退出循环结构；

delimiter //

create procedure test_iterate(out num int)

BEGIN

	declare n int default 0;
	declare count int default 0;

	while_label:while TRUE DO
		set n=n+1;
		set count=count+1;
		if n<10 THEN
			ITERATE while_label;
		elseif n>15 THEN
			leave while_label;
		END if;
		
		select n;  -- 该select语句在10<=n<=15的时候会执行，即执行6次
	end while;
	
	set num=count;

end //

delimiter ;

set @num=0;

call test_iterate(@num);

select @num;

drop procedure test_iterate;
