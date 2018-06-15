SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_combo_network`;
CREATE TABLE `ad_combo_network` (
  `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告套餐ID',
  `ad_network_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告发送器ID',
  PRIMARY KEY (`ad_combo_id`,`ad_network_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐-广告发送器表';