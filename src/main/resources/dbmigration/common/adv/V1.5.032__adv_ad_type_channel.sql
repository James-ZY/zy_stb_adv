SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_type_channel`;
CREATE TABLE `ad_type_channel` (
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `ad_channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '电视频道ID',
  PRIMARY KEY (`ad_type_id`,`ad_channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='类型-频道表';