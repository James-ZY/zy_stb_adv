SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_advertiser_combo`;
CREATE TABLE `ad_advertiser_combo` (
  `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告套餐ID',
  `ad_advertiser_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告商ID',
   PRIMARY KEY (`ad_combo_id`,`ad_advertiser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告套餐--广告商';