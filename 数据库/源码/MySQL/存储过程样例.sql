-- 举例1：创建存储过程select_all_data()，查看 emps 表的所有数据

DELIMITER $
CREATE PROCEDURE select_all_data()
BEGIN
SELECT * 
FROM emps;
END $
DELIMITER ;


-- 创建存储过程show_min_salary()，查看“emps”表的最低薪资值。并将最低薪资通过OUT参数“ms”输出


DELIMITER //
CREATE PROCEDURE show_min_salary(OUT ms DOUBLE)
BEGIN
SELECT MIN(salary) INTO ms -- 使用INTO关键字将值赋值给ms
FROM emps;
END //
DELIMITER ;


-- 创建存储过程show_someone_salary()，查看“emps”表的某个员工的薪资，并用IN参数empname输入员工姓名。

DELIMITER //
CREATE PROCEDURE show_someone_salary(IN empname VARCHAR(20))
BEGIN
SELECT salary 
FROM emps 
WHERE ename = empname;
END //
DELIMITER ;


-- 创建存储过程show_mgr_name()，查询某个员工领导的姓名，并用INOUT参数“empname”输入员工姓名，输出领导的姓名。


DELIMITER //
CREATE PROCEDURE show_mgr_name(INOUT empname VARCHAR(20))
BEGIN
SELECT ename INTO empname 
FROM emps
WHERE eid = (SELECT MID FROM emps WHERE ename=empname);
END //
DELIMITER ;
