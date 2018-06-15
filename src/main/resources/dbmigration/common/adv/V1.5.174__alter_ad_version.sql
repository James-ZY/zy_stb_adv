DROP TABLE IF EXISTS `ad_version`;
CREATE TABLE `ad_version` (
   version_id varchar(20) COLLATE utf8_bin NOT NULL COMMENT '版本ID',
  `company_name` varchar(30)   NOT NULL COMMENT '公司名称',
  `company_logo` varchar(100)   NOT NULL COMMENT '公司logo',
  `company_url` varchar(30)   NOT NULL COMMENT '公司url',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_version_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告系统版本表';

INSERT INTO `ad_version` VALUES ('V5.1.0.10', 'Gospell', '/advs/static/images/icon/new-logo.png', 'http://www.gospell.com/', '1', 'admin', '2018-05-10 11:05:47', NULL, NULL, 'VersionInfo', '0');


