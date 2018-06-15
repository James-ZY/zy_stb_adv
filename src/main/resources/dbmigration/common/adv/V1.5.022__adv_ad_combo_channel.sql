SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_combo_channel`;
CREATE TABLE `ad_combo_channel` (
 `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告套餐ID',
  `ad_channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '电视频道ID',
   PRIMARY KEY (`ad_combo_id`,`ad_channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐-频道表';