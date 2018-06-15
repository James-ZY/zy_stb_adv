SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_stb`;
CREATE TABLE `ad_stb` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `smartcard_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '智能卡ID',
  `upload_play_record` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '上传的字符串',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_smartcard_id` (`smartcard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='机顶盒ID与上传的字符串表';