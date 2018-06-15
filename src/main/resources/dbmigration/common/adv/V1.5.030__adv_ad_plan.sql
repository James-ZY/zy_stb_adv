SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_plan`;
CREATE TABLE `ad_plan` (
  `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告套餐ID',
  `ad_adv_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告ID',
  `ad_start_time` datetime COLLATE utf8_bin NOT NULL COMMENT '开始时间',
  `duration` int(11) COLLATE utf8_bin NOT NULL COMMENT '持续时间(以天来衡量)',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_combo_channel_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告发布规划表';