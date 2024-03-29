-- 创建一个名称为“InsertDataWithCondition”的存储过程，代码如下。在存储过程中，定义处理程序，捕获sqlstate_value值，
-- 当遇到sqlstate_value值为23000时，执行EXIT操作，并且将@proc_value的值设置为-1。


delimiter //

create procedure InsertDataWithCondition()

BEGIN

	declare ProcessException CONDITION FOR SQLSTATE '23000';
	
	declare continue HANDLER for ProcessException set @proc_value=-1;
	
	signal SQLSTATE '23000' set MESSAGE_TEXT='哐哐报错';
	
END //

delimiter ;

set @proc_value=1;

select @proc_value;

call InsertDataWithCondition();