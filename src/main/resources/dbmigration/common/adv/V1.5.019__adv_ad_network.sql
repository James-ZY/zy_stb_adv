SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_network`;
CREATE TABLE `ad_network` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_network_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告发送器ID',
  `ad_netwrok_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '广告发送器名称',
  `ad_cpu` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'cpu状态',
  `ad_mermory` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '内存状态',
  `ad_port` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '端口号',
  `ad_way_encryption` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '传输加密方式',
  `ad_ip` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'ip',
  `ad_secret_key` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密钥',
   `ad_operators_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '电视运营商ID',
   `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_network_id` (`ad_network_id`),
  KEY `ad_network_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告发送器表';