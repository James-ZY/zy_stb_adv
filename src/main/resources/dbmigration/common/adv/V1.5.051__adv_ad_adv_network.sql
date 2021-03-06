SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_adv_network`;
CREATE TABLE `ad_adv_network` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `adv_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告Id',
  `client_id`  varchar(64) COLLATE utf8_bin NOT NULL COMMENT '客户端Id',
  `ad_status` int(11) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_sell_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='同步表';