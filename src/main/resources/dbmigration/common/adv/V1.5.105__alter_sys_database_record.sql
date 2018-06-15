DROP TABLE IF EXISTS `sys_database_record`;
CREATE TABLE `sys_database_record` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `record_name` varchar(50) DEFAULT NULL COMMENT '文件名字',
  `record_path` varchar(100) DEFAULT NULL COMMENT '存储路径',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  CHARSET=utf8 COLLATE=utf8_bin COMMENT='数据库备份记录表';

INSERT INTO `task_detail`  VALUES ('1', '测试手动设置任务1', 'testQuartzTask', '1', '0 */5 10 * * ?', 'com.gospell.aas.service.quartz.PutEndTask', 'dataBaseManage', '测试手动设置任务1', 'admin', '2017-07-01 14:43:11', 'admin', '2017-07-10 09:58:23', '123', '0');
INSERT INTO `task_detail`  VALUES ('2', '测试手动设置任务2', 'testQuartzTask132', '0', '0 17 18 * * ?', 'com.gospell.aas.service.quartz.PutEndTask', 'dataBaseManage', '测试手动设置任务23', 'admin', '2017-07-05 14:43:11', 'admin', '2017-07-06 09:23:13', '123', '0');
INSERT INTO `task_detail`  VALUES ('3', '测试手动设置任务3', 'testQuartzTask', '0', '0 */5 17 * * ?', 'com.gospell.aas.service.quartz.PutEndTask', 'dataBaseManage', '测试手动设置任务1', 'admin', '2017-07-05 09:42:03', 'admin', '2017-07-06 09:23:19', NULL, '0');
