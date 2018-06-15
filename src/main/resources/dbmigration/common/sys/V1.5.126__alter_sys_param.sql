DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `param_type` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '参数类型',
  `param_key` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '参数键',
  `param_value` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '参数值',
  `enable` int(11) COLLATE utf8_bin NOT NULL  DEFAULT '0' COMMENT '是否启用（0启用 1不启用） ',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数表';


