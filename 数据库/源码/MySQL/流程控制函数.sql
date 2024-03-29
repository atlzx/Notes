


SELECT IF(1 > 0,'正确','错误')    
->正确


SELECT IFNULL(null,'Hello Word')
->Hello Word










SELECT CASE 
　　WHEN 1 > 0
　　THEN '1 > 0'
　　WHEN 2 > 0
　　THEN '2 > 0'
　　ELSE '3 > 0'
　　END
->1 > 0


SELECT CASE 1 
　　WHEN 1 THEN '我是1'
　　WHEN 2 THEN '我是2'
ELSE '你是谁'







SELECT employee_id,salary, 
				  CASE 
				  WHEN salary>=15000 THEN '高薪' 
				  WHEN salary>=10000 THEN '潜力股'  
				  WHEN salary>=8000 THEN '屌丝' 
				  ELSE '草根' 
				  END  "描述"
FROM employees; 


SELECT oid,`status`, CASE `status` 
						WHEN 1 THEN '未付款' 
						WHEN 2 THEN '已付款' 
						WHEN 3 THEN '已发货'  
						WHEN 4 THEN '确认收货'  
						ELSE '无效订单' 
						END 
FROM t_order;