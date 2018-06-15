SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_combo_district`;
CREATE TABLE `ad_combo_district` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告运营商ID',
  `ad_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '区域ID',
  `ad_self_district_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '自定义区域ID',
   PRIMARY KEY (`id`),
   KEY `ad_combo_id` (`ad_combo_id`),
   KEY `ad_district_id` (`ad_district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐-区域表';

alter TABLE ad_combo ADD ad_area varchar(500);
alter TABLE ad_combo ADD send_mode char(1) DEFAULT 1;


