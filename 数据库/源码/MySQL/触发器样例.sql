-- 创建名称为after_insert的触发器，向test_trigger数据表插入数据之后，向test_trigger_log数据表中插入after_insert的日志信息。

CREATE TABLE test_trigger (
id INT PRIMARY KEY AUTO_INCREMENT,
t_note VARCHAR(30)
);
CREATE TABLE test_trigger_log (
id INT PRIMARY KEY AUTO_INCREMENT,
t_log VARCHAR(30)
);


delimiter //

create trigger after_insert
AFTER INSERT
ON test_trigger
FOR EACH ROW
BEGIN
	insert into test_trigger_log values(NEW.id,NEW.t_note);  -- 插入时，待插入的数据使用NEW来表示
END // 

delimiter ;

insert into test_trigger(t_note) values('测试1'),('测试2');

select * from test_trigger_log;


-- 创建名称为after_delete的触发器，删除test_trigger数据表的数据之后，同步删除test_trigger_log数据表内的信息。

delimiter //

create trigger after_delete
after DELETE
ON test_trigger
for each ROW

BEGIN

	DELETE from test_trigger_log where t_log=OLD.t_note;  -- 在删除时，删除前的数据使用OLD来表示

END //

delimiter ;

delete from test_trigger;
