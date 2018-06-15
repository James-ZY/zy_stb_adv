SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_advertiser_role`;
CREATE TABLE `ad_advertiser_role` (
 `ad_role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `ad_advertiser_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告商ID',
   PRIMARY KEY (`ad_role_id`,`ad_advertiser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-广告商表';