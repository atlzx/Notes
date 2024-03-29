

use atguigudb;

delimiter //

-- 创建自定义函数，写上DETERMINISTIC以声明具有明确返回值以避免报错

CREATE FUNCTION test_fn1(a int,b int)
returns int
DETERMINISTIC

begin
		DECLARE result int default 0;
		set result=a+b;
		return result; 
END //

delimiter ;

select test_fn1(10,20);

-- 删除自定义函数

DROP FUNCTION test_fn1;

-- 修改自定义函数的属性
-- 自定义函数的代码无法修改
alter function test_fn1 no SQL;

show create function test_fn1;

alter function test_fn1 MODIFIES SQL DATA no SQL reads sql data;

