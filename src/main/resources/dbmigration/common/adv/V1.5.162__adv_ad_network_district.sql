SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_network_district`;
CREATE TABLE `ad_network_district` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_network_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告发送器ID',
  `ad_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '区域ID',
  `ad_self_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '自定义区域ID',
   PRIMARY KEY (`id`),
   KEY `ad_network_id` (`ad_network_id`),
   KEY `ad_district_id` (`ad_district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发送器-区域表';

alter TABLE ad_network ADD ad_area varchar(500);



