alter TABLE sys_param ADD can_update INT(4);
update sys_param ac set ac.can_update = 0 where ISNULL(ac.can_update);

INSERT INTO `sys_param`  VALUES ('5bd88a5336314f83983d6bdfc324dba2', 'CU_BPTS', 'DEFAULT', '10', '1', NULL, '2018-03-08 19:00:25', 'admin', '2018-03-09 11:40:44', NULL, '0', '1');
INSERT INTO `sys_param`  VALUES ('b36525b41545424ab5a38382f3762203', 'CU_BPTT', 'DEFAULT', '30', '1', 'admin', '2018-03-08 19:00:25', 'admin', '2018-03-09 11:40:53', NULL, '0', '1');
