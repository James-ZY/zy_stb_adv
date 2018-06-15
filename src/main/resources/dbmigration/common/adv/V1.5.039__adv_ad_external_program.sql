DROP TABLE IF EXISTS `ad_external_program`;
CREATE TABLE `ad_external_program` (
  `ad_program_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '外部程序Id',
  `ad_program_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '外部程序名称',
  `ad_program_version` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '外部程序版本',
  `ad_program_path` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '外部程序路径',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_program_id` (`ad_program_id`),
  KEY `ad_program_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='外部程序管理表';