DROP TABLE IF EXISTS `ad_type`;
CREATE TABLE `ad_type` (
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `ad_type_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型名称',
  `ad_type_description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '广告类型描述',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `is_flag` int(11) NOT NULL DEFAULT '0' COMMENT '是否跟频道相关',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0图片类型 1视频类型',
  `is_move` int(11) NOT NULL DEFAULT '0' COMMENT '0不可以移动 1可以移动',
  `is_position` int(11) NOT NULL DEFAULT '0' COMMENT '0不需要 1需要',
  PRIMARY KEY (`id`),
  KEY `ad_ad_type_id` (`ad_type_id`),
  KEY `ad_ad_type_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告类型表';
INSERT INTO `ad_type` VALUES ('-1', '0', '0,', 'adv_type_display_styles', null, '-1', '1', '2016-11-22 10:56:38', '1', '2016-11-22 10:56:49', '顶级广告类型', '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('1', '-1', '0,-1,', 'adv_type_boot_up_picture', '', '1', null, null, 'admin', '2017-01-11 16:02:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('2', '-1', '0,-1,', 'adv_type_corner_picture', '', '2', null, null, 'admin', '2017-01-11 15:29:33', null, '0', '1', '0', '0', '1');
INSERT INTO `ad_type` VALUES ('3', '-1', '0,-1,', 'adv_type_channel_switching', '', '3', null, null, 'admin', '2017-01-11 15:29:52', null, '0', '1', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('4', '-1', '0,-1,', 'adv_type_pop_up_picture', '', '4', null, null, 'admin', '2017-01-11 15:30:26', null, '0', '1', '0', '0', '1');
INSERT INTO `ad_type` VALUES ('5', '-1', '0,-1,', 'adv_type_rolling_advertisment', '', '5', null, null, 'admin', '2017-01-11 15:30:19', null, '0', '1', '0', '1', '1');
INSERT INTO `ad_type` VALUES ('6', '-1', '0,-1,', 'adv_type_boot_up_video', '', '6', null, null, 'admin', '2017-01-11 15:44:03', null, '0', '0', '1', '0', '0');
INSERT INTO `ad_type` VALUES ('7', '-1', '0,-1,', 'adv_type_prompt_window', '', '7', null, null, 'admin', '2017-01-12 14:42:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('0', '7', '0,-1,7,', 'adv_type_no_signal', '', '7-0', null, null, 'admin', '2017-01-12 14:42:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('1', '7', '0,-1,7,', 'adv_type_no_channel', '', '7-1', 'admin', '2016-11-22 15:23:42', 'admin', '2017-01-12 14:42:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('2', '7', '0,-1,7,', 'adv_type_no_entitlement', '', '7-2', 'admin', '2016-11-22 15:31:02', 'admin', '2017-01-12 14:42:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('3', '7', '0,-1,7,', 'adv_type_others', '', '7-3', 'admin', '2016-11-22 15:43:24', 'admin', '2017-01-12 14:42:31', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('8', '-1', '0,-1,', 'adv_type_menu_picture', '', '8', null, null, 'admin', '2017-01-11 15:32:29', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('0', '8', '0,-1,8,', 'adv_type_main_menu', '', '8-0', 'admin', '2016-11-22 15:46:21', 'admin', '2017-01-11 15:32:53', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('1', '8', '0,-1,8,', 'adv_type_volume_bar', '', '8-1', 'admin', '2016-11-22 15:46:53', 'admin', '2017-01-11 15:33:06', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('2', '8', '0,-1,8,', 'adv_type_channel_list', '', '8-2', 'admin', '2016-11-22 15:47:45', 'admin', '2017-01-11 15:33:22', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('3', '8', '0,-1,8,', 'adv_type_EPG_list', '', '8-3', null, null, 'admin', '2017-01-11 15:33:34', null, '0', '0', '0', '0', '0');
INSERT INTO `ad_type` VALUES ('9', '-1', '0,-1,', 'adv_type_broadcasting_backgroud', '', '9', null, null, 'admin', '2017-01-12 11:57:17', null, '0', '0', '0', '0', '0');