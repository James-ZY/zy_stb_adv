DROP TABLE IF EXISTS `task_detail`;
CREATE TABLE `task_detail` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) DEFAULT NULL COMMENT '任务分组',
  `job_status` varchar(5) DEFAULT NULL COMMENT '任务状态 0禁用 1启用 ',
  `cron_expression` varchar(200) DEFAULT NULL COMMENT '任务运行时间表达式',
  `bean_class` varchar(300) DEFAULT NULL COMMENT '任务执行类',
  `execute_method` varchar(100) DEFAULT NULL COMMENT '任务执行方法',
  `job_desc` varchar(500) DEFAULT NULL COMMENT '任务描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  CHARSET=utf8 COLLATE=utf8_bin COMMENT='自动调度表';