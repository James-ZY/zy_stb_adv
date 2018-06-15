DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `area_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属区域',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '机构名称',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '机构类型',
  `grade` char(1) COLLATE utf8_bin NOT NULL COMMENT '机构等级',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '1', '100000', 'user_level_advertising_provider', '1', '1', null, null, null, null, null, null, '1', '2015-12-12 10:39:41', '1', '2015-12-12 10:39:41', null, '0');
INSERT INTO `sys_office` VALUES ('2', '1', '0,1,', null, '001', 'user_level_operation_management', '1', '2', null, null, null, null, null, null, 'admin', '2016-05-31 16:32:47', 'admin', '2017-01-11 16:44:06', '', '0');
INSERT INTO `sys_office` VALUES ('3', '1', '0,1,', null, '002', 'user_level_network_management', '1', '2', null, null, null, null, null, null, 'admin', '2016-05-31 16:50:01', 'admin', '2017-01-11 16:44:21', '', '0');
INSERT INTO `sys_office` VALUES ('4', '1', '0,1,', null, '003', 'user_level_content_review', '1', '2', null, null, null, null, null, null, 'admin', '2016-05-31 16:51:05', 'admin', '2017-01-11 16:44:33', '', '0');
INSERT INTO `sys_office` VALUES ('5', '1', '0,1,', null, '004', 'user_level_advertiser_management', '1', '2', null, null, null, null, null, null, 'admin', '2016-05-31 16:51:35', 'admin', '2017-01-11 16:44:49', '', '0');
INSERT INTO `sys_office` VALUES ('6', '5', '0,1,5,', null, '005', 'user_level_advertiser', '1', '3', null, null, null, null, null, null, 'admin', '2016-05-31 16:52:15', 'admin', '2017-01-11 16:45:09', '', '0');
