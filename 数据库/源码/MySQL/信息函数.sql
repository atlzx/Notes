SELECT USER(), CURRENT_USER(), SYSTEM_USER(),SESSION_USER();

SELECT CHARSET('ABC');

SELECT COLLATION('ABC');

  SET @uuid = UUID();
  SELECT @uuid,uuid_to_bin(@uuid),uuid_to_bin(@uuid,TRUE);