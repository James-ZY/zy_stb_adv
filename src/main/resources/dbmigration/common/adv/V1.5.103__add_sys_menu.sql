INSERT INTO `sys_menu` VALUES ('130', '32', '0,1,2,31,32,', 'menu_export', '', '', '', '30', '0', '0', 'sys:log:export', 'admin', '2017-06-30 17:28:32', 'admin', '2017-07-03 17:04:52', NULL, '0');
INSERT INTO `sys_menu` VALUES ('131', '52', '0,1,87,60,52,', 'menu_export', '', '', '', '40', '0', '0', 'sys:combo:export', 'admin', '2017-07-03 11:13:47', 'admin', '2017-07-03 17:04:56', NULL, '0');
INSERT INTO `sys_menu` VALUES ('132', '2', '0,1,2,', 'menu_database_management', '', '', '', '985', '1', '0', '', 'admin', '2017-07-03 16:10:52', 'admin', '2017-07-03 17:04:51', NULL, '0');
INSERT INTO `sys_menu` VALUES ('133', '132', '0,1,2,132,', 'sys_task_manage', '/sys/task', '', 'hdd', '10', '1', '0', 'sys:task:view', 'admin', '2017-07-03 16:28:35', 'admin', '2017-07-07 16:38:45', NULL, '0');
INSERT INTO `sys_menu` VALUES ('134', '133', '0,1,2,132,133,', 'menu_update', '', '', '', '10', '0', '0', 'sys:task:edit', 'admin', '2017-07-04 16:16:32', 'admin', '2017-07-07 16:38:45', NULL, '0');
INSERT INTO `sys_menu` VALUES ('135', '133', '0,1,2,132,133,', 'menu_forbid', '', '', '', '20', '0', '0', 'sys:task:edit', 'admin', '2017-07-04 16:17:08', 'admin', '2017-07-07 16:38:45', NULL, '0');
INSERT INTO `sys_menu` VALUES ('136', '133', '0,1,2,132,133,', 'menu_start', '', '', '', '30', '0', '0', 'sys:task:edit', 'admin', '2017-07-04 16:17:27', 'admin', '2017-07-07 16:38:45', NULL, '0');
INSERT INTO `sys_menu` VALUES ('137', '132', '0,1,2,132,', 'menu_backup_restore', '/sys/database', '', 'th-list', '20', '1', '0', 'sys:database:view', 'admin', '2017-07-06 17:23:03', 'admin', '2017-07-06 17:37:08', NULL, '0');
INSERT INTO `sys_menu` VALUES ('138', '137', '0,1,2,132,137,', 'sys_database_backup', '', '', '', '10', '0', '0', 'sys:database:edit', 'admin', '2017-07-06 17:30:54', 'admin', '2017-07-06 17:37:08', NULL, '0');
INSERT INTO `sys_menu` VALUES ('139', '137', '0,1,2,132,137,', 'sys_database_restore', '', '', '', '20', '0', '0', 'sys:database:edit', 'admin', '2017-07-06 17:31:27', 'admin', '2017-07-06 17:37:08', NULL, '0');



update `sys_menu` set sort = 986 where id = 31;
update `sys_menu` set sort = 987 where id = 121;