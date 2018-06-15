SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_control_adelement`;
CREATE TABLE `ad_control_adelement` (
 `ad_control_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '素材ID',
  `ad_adv_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告ID',
   PRIMARY KEY (`ad_control_id`,`ad_adv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告-素材表';