



--3. 修改表  --> ALTER TABLE 
DESC myemp1;

-- 3.1 添加一个字段
ALTER TABLE myemp1
ADD salary DOUBLE(10,2); --默认添加到表中的最后一个字段的位置

ALTER TABLE myemp1
ADD phone_number VARCHAR(20) FIRST;

ALTER TABLE myemp1
ADD email VARCHAR(45) AFTER emp_name;

-- 3.2 修改一个字段：数据类型、长度、默认值（略）
ALTER TABLE myemp1
MODIFY emp_name VARCHAR(25) ;

ALTER TABLE myemp1
MODIFY emp_name VARCHAR(35) DEFAULT 'aaa';

-- 3.3 重命名一个字段
ALTER TABLE myemp1
CHANGE salary monthly_salary DOUBLE(10,2);

ALTER TABLE myemp1
CHANGE email my_email VARCHAR(50);

-- 3.4 删除一个字段
ALTER TABLE myemp1
DROP COLUMN my_email;

--4. 重命名表
--方式1：
RENAME TABLE myemp1
TO myemp11;

DESC myemp11;

--方式2：
ALTER TABLE myemp2
RENAME TO myemp12;

DESC myemp12;

--5. 删除表
--不光将表结构删除掉，同时表中的数据也删除掉，释放表空间
DROP TABLE IF EXISTS myemp2;

DROP TABLE IF EXISTS myemp12;

--6. 清空表

--清空表，表示清空表中的所有数据，但是表结构保留。

SELECT * FROM employees_copy;

TRUNCATE TABLE employees_copy;

SELECT * FROM employees_copy;

DESC employees_copy;

--7. DCL 中 COMMIT 和 ROLLBACK
-- COMMIT:提交数据。一旦执行COMMIT，则数据就被永久的保存在了数据库中，意味着数据不可以回滚。
-- ROLLBACK:回滚数据。一旦执行ROLLBACK,则可以实现数据的回滚。回滚到最近的一次COMMIT之后。

--8. 对比 TRUNCATE TABLE 和 DELETE FROM 
-- 相同点：都可以实现对表中所有数据的删除，同时保留表结构。
-- 不同点：
--	TRUNCATE TABLE：一旦执行此操作，表数据全部清除。同时，数据是不可以回滚的。
--	DELETE FROM：一旦执行此操作，表数据可以全部清除（不带WHERE）。同时，数据是可以实现回滚的。

/*
9. DDL 和 DML 的说明
  ① DDL的操作一旦执行，就不可回滚。指令SET autocommit = FALSE对DDL操作失效。(因为在执行完DDL
    操作之后，一定会执行一次COMMIT。而此COMMIT操作不受SET autocommit = FALSE影响的。)
  
  ② DML的操作默认情况，一旦执行，也是不可回滚的。但是，如果在执行DML之前，执行了 
    SET autocommit = FALSE，则执行的DML操作就可以实现回滚。

*/
-- 演示：DELETE FROM 
--1)
COMMIT;
--2)
SELECT *
FROM myemp3;
--3)
SET autocommit = FALSE;
--4)
DELETE FROM myemp3;
--5)
SELECT *
FROM myemp3;
--6)
ROLLBACK;
--7)
SELECT *
FROM myemp3;

-- 演示：TRUNCATE TABLE
--1)
COMMIT;
--2)
SELECT *
FROM myemp3;
--3)
SET autocommit = FALSE;
--4)
TRUNCATE TABLE myemp3;
--5)
SELECT *
FROM myemp3;
--6)
ROLLBACK;
--7)
SELECT *
FROM myemp3;

----------------------------------------------
--9.测试MySQL8.0的新特性：DDL的原子化

CREATE DATABASE mytest;

USE mytest;

CREATE TABLE book1(
book_id INT ,rr
book_name VARCHAR(255)
);

SHOW TABLES;

DROP TABLE book1,book2;

SHOW TABLES;