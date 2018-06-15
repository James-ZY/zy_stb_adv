SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_statistics`;
CREATE TABLE `ad_statistics` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `adv_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '广告ID',
  `stb_serial_number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '机顶盒序列号',
  `smartcard_id` varchar(255) COLLATE utf8_bin DEFAULT '0' COMMENT '智能卡ID',
  `play_start_date` datetime DEFAULT NULL COMMENT '广告播放开始时间',
  `play_end_date` datetime DEFAULT NULL COMMENT '广告播放结束时间',
  `duration` int(11) DEFAULT NULL COMMENT '持续时间',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_statistics_id` (`adv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告统计表';