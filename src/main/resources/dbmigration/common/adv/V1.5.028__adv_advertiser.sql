SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_advertiser`;
CREATE TABLE `ad_advertiser` (
  `business_license_number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '营业执照注册号',
  `business_license` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '营业执照存放路径图片',
  `industry` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '行业',
  `industry_aptitude` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '行业资质',
  `advertiser_id`  varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告商id',
  `name`varchar(255) COLLATE utf8_bin NOT NULL COMMENT '广告商名称',
  `advertiser_type` int(11) COLLATE utf8_bin DEFAULT 1 COMMENT '广告商类型',
  `contacts` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `web_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '网站名称',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公司地址',
  `phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '固定电话',
  `mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
   `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
 `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_advertiser_id` (`advertiser_id`),
  KEY `aad_advertiser_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告商';