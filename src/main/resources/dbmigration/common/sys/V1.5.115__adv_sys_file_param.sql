DROP TABLE IF EXISTS `sys_file_param`;
CREATE TABLE `sys_file_param` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_type_id`  varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型id',
  `flag` int(11) COLLATE utf8_bin NOT NULL COMMENT '（0标清 1高清）',
  `amount` int(11) COLLATE utf8_bin NOT NULL COMMENT '（数量）',
  `enable` int(11) COLLATE utf8_bin NOT NULL  DEFAULT '0' COMMENT '是否启用（0启用 1不启用） ',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统文件参数设置表';