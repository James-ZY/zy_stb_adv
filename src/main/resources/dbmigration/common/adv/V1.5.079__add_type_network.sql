
DROP TABLE IF EXISTS `ad_type_network`;
CREATE TABLE `ad_type_network` (
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `ad_network_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告发送器ID',
  PRIMARY KEY (`ad_type_id`,`ad_network_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告类型-广告发送器中间表';

