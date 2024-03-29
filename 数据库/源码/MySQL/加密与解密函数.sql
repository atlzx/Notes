SELECT PASSWORD('mysql'), PASSWORD(NULL);

SELECT md5('123');

SELECT SHA('Tom123');

SELECT ENCODE('mysql', 'mysql');

SELECT DECODE(ENCODE('mysql','mysql'),'mysql');