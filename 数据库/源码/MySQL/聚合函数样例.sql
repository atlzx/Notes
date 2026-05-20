
-- COUNT、MAX、MIN、AVG、SUM等函数都不会处理NULL
-- 但如果参与运算的值全是NULL,会返回一个NULL
-- 使用COUNT(*)、COUNT(1)可以有效地避免这一问题

SELECT COUNT(commission_pct)
FROM   employees
WHERE  department_id = 50;

SELECT MAX(commission_pct)
FROM   employees
WHERE  department_id = 50;

SELECT MAX(manager_id)
from employees;