SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_operator_district`;
CREATE TABLE `ad_operator_district` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_operator_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告运营商ID',
  `ad_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '区域ID',
  `ad_self_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '自定义区域ID',
   PRIMARY KEY (`id`),
   KEY `ad_operator_id` (`ad_operator_id`),
   KEY `ad_district_id` (`ad_district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='运营商-区域表';

alter table ad_operators modify column ad_area varchar(500);

