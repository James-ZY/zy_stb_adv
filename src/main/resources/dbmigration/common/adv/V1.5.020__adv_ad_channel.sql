DROP TABLE IF EXISTS `ad_channel`;
CREATE TABLE `ad_channel` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '频道',
  `ad_channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '频道ID',
  `ad_channel_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '频道名称',
  `ad_channel_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '频道类型',
  `ad_network_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告发送器ID',
   `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_channel_id` (`ad_channel_id`),
  KEY `ad_channel_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='频道表';