-- 第10章_创建和管理表

SELECT * 
FROM `order`;

-- 1. 创建和管理数据库

--1.1 如何创建数据库
--方式1：
CREATE DATABASE mytest1;  -- 创建的此数据库使用的是默认的字符集

--查看创建数据库的结构
SHOW CREATE DATABASE mytest1;

--方式2：显式了指名了要创建的数据库的字符集
CREATE DATABASE mytest2 CHARACTER SET 'gbk';

--
SHOW CREATE DATABASE mytest2;

--方式3（推荐）：如果要创建的数据库已经存在，则创建不成功，但不会报错。
CREATE DATABASE IF NOT EXISTS mytest2 CHARACTER SET 'utf8';

--如果要创建的数据库不存在，则创建成功
CREATE DATABASE IF NOT EXISTS mytest3 CHARACTER SET 'utf8';

SHOW DATABASES;

--1.2 管理数据库
--查看当前连接中的数据库都有哪些
SHOW DATABASES;

--切换数据库
USE atguigudb;

--查看当前数据库中保存的数据表
SHOW TABLES;

--查看当前使用的数据库
SELECT DATABASE() FROM DUAL;

--查看指定数据库下保存的数据表
SHOW TABLES FROM mysql;

--1.3 修改数据库
--更改数据库字符集
SHOW CREATE DATABASE mytest2;

ALTER DATABASE mytest2 CHARACTER SET 'utf8';

--1.4 删除数据库
--方式1：如果要删除的数据库存在，则删除成功。如果不存在，则报错
DROP DATABASE mytest1;

SHOW DATABASES;

--方式2：推荐。 如果要删除的数据库存在，则删除成功。如果不存在，则默默结束，不会报错。
DROP DATABASE IF EXISTS mytest1;

DROP DATABASE IF EXISTS mytest2;