SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `sys_help`;
CREATE TABLE `sys_help` (
  `file_path` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '帮助文档路径',
  `file_name` varchar(255) COLLATE utf8_bin COMMENT '文档大小',
  `file_size` varchar(64) COLLATE utf8_bin COMMENT '文档大小',
  `file_format` varchar(64) COLLATE utf8_bin  COMMENT '文档格式',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态（0失效 1有效）',
  `flag` int(11) NOT NULL  COMMENT '文档属于类别（0使用说明（暂时只有这一种状态））',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统帮助';