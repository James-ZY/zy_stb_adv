DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `href` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接',
  `target` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `is_show` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否在菜单中显示',
  `type` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '类型',
  `permission` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

INSERT INTO `sys_menu` VALUES ('1', '0', '0,', 'menu_root_menu', null, null, null, '0', '1', '0', null, '1', '2015-06-02 18:09:56', '1', '2015-06-02 18:09:56', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '0,1,2,3,', 'menu_dictionary_management', '/sys/dict/', '', 'th-list', '60', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:50', null, '0');
INSERT INTO `sys_menu` VALUES ('100', '44', '0,1,44,', 'menu_advertisement_category', '', '', 'tint', '85', '1', '0', '', 'admin', '2016-12-08 11:54:40', 'admin', '2017-01-09 16:24:30', null, '0');
INSERT INTO `sys_menu` VALUES ('101', '119', '0,1,44,100,119,', 'menu_view', '', '', '', '10', '0', '0', 'sys:category:view', 'admin', '2016-12-08 11:55:15', 'admin', '2017-01-09 16:25:00', null, '0');
INSERT INTO `sys_menu` VALUES ('102', '119', '0,1,44,100,119,', 'menu_update', '', '', '', '20', '0', '0', 'sys:category:edit', 'admin', '2016-12-08 11:55:46', 'admin', '2017-01-09 16:25:00', null, '0');
INSERT INTO `sys_menu` VALUES ('103', '119', '0,1,44,100,119,', 'menu_delete', '', '', '', '30', '0', '0', 'sys:category:edit', 'admin', '2016-12-08 11:56:28', 'admin', '2017-01-09 16:25:00', null, '0');
INSERT INTO `sys_menu` VALUES ('104', '105', '0,1,58,105,', 'menu_statistics_of_play', '/adv/adStatistic/advPlayDetailQuery', '', 'list', '10', '1', '0', '', 'admin', '2016-12-12 16:30:54', 'admin', '2017-01-09 16:30:53', null, '0');
INSERT INTO `sys_menu` VALUES ('105', '58', '0,1,58,', 'menu_statistics_of_ads', '', '', '', '15', '1', '0', '', 'admin', '2016-12-15 11:12:43', 'admin', '2017-01-09 16:30:30', null, '0');
INSERT INTO `sys_menu` VALUES ('106', '105', '0,1,58,105,', 'menu_statistics_of_review', '/adv/adelement/auditStatictis/query', '', '', '30', '1', '0', '', 'admin', '2016-12-15 11:16:25', 'admin', '2017-01-09 16:31:21', null, '0');
INSERT INTO `sys_menu` VALUES ('107', '51', '0,1,58,45,51,', 'menu_view', '', '', '', '30', '0', '0', 'combo:statistics:operation:view', 'admin', '2016-12-15 11:14:04', 'admin', '2017-01-09 16:29:28', null, '0');
INSERT INTO `sys_menu` VALUES ('108', '112', '0,1,58,105,112,', 'menu_view', '', '', '', '30', '0', '0', 'adv:click:view', 'admin', '2016-12-20 10:14:29', 'admin', '2017-01-09 16:31:10', null, '0');
INSERT INTO `sys_menu` VALUES ('109', '88', '0,1,58,45,88,', 'menu_view', '', '', '', '30', '0', '0', 'advtiser:buy:view', 'admin', '2016-12-15 11:14:52', 'admin', '2017-01-09 16:29:39', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '10', '0,1,2,3,10,', 'menu_view', null, null, null, '30', '0', '0', 'sys:dict:view', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:50', null, '0');
INSERT INTO `sys_menu` VALUES ('110', '89', '0,1,58,45,89,', 'menu_view', '', '', '', '30', '0', '0', 'channelCombo:sell:view', 'admin', '2016-12-15 11:15:27', 'admin', '2017-01-09 16:30:05', null, '0');
INSERT INTO `sys_menu` VALUES ('111', '106', '0,1,58,105,106,', 'menu_view', '', '', '', '30', '0', '0', 'adv:aduit:statistic:view', 'admin', '2016-12-15 11:16:36', 'admin', '2017-01-09 16:31:21', null, '0');
INSERT INTO `sys_menu` VALUES ('112', '105', '0,1,58,105,', 'menu_statistics_of_click', '/adv/adStatistic/advPlayCount', '', '', '25', '1', '0', 'adv:click:view', 'admin', '2016-12-20 10:13:50', 'admin', '2017-01-09 16:31:10', null, '0');
INSERT INTO `sys_menu` VALUES ('113', '104', '0,1,58,105,104,', 'menu_view', '', '', '', '10', '0', '0', 'adv:play:view', 'admin', '2016-12-12 16:44:51', 'admin', '2017-01-09 16:30:53', null, '0');
INSERT INTO `sys_menu` VALUES ('114', '49', '0,1,44,49,', 'menu_export', '', '', '', '40', '0', '0', 'sys:advertiser:export', 'admin', '2016-12-29 17:25:20', 'admin', '2017-01-09 16:21:57', null, '0');
INSERT INTO `sys_menu` VALUES ('115', '39', '0,1,44,38,36,39,', 'menu_import', '', '', '', '40', '0', '0', 'sys:advertiser:import', 'admin', '2016-12-29 14:40:19', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('116', '36', '0,1,44,38,36,', 'menu_import', '', '', '', '50', '0', '0', 'sys:operators:import', 'admin', '2016-12-29 16:47:44', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('117', '54', '0,1,44,49,54,', 'menu_import', '', '', '', '50', '0', '0', 'sys:advertiser:import', 'admin', '2016-12-29 17:24:53', 'admin', '2017-01-09 16:22:21', null, '0');
INSERT INTO `sys_menu` VALUES ('118', '36', '0,1,44,38,36,', 'menu_export', '', '', '', '30', '0', '0', 'sys:operators:export', 'admin', '2016-12-29 14:39:52', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('119', '100', '0,1,44,100,', 'menu_category_management', '/adv/category', '', '', '30', '1', '0', '', 'admin', '2016-12-30 10:33:25', 'admin', '2017-01-09 16:25:00', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '10', '0,1,2,3,10,', 'menu_update', null, null, null, '30', '0', '0', 'sys:dict:edit', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:50', null, '0');
INSERT INTO `sys_menu` VALUES ('120', '82', '0,1,44,82,', 'menu_display_coordinate_range_management', '/adv/range', '', '', '30', '1', '0', '', 'admin', '2016-12-30 10:31:00', 'admin', '2017-01-09 16:22:56', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '2', '0,1,2,', 'menu_sys_user', '', '', '', '970', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:16:57', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '13', '0,1,2,13,', 'menu_user_level_management', '/sys/office/', '', 'th-list', '40', '1', '0', '', null, null, 'admin', '2017-01-09 16:17:23', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '17', '0,1,2,13,17,', 'menu_view', null, null, null, '30', '0', '0', 'sys:office:view', null, null, 'admin', '2017-01-09 16:17:23', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '17', '0,1,2,13,17,', 'menu_update', null, null, null, '30', '0', '0', 'sys:office:edit', null, null, 'admin', '2017-01-09 16:17:23', null, '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '0,1,', 'menu_system_management', '', '', '', '200', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:16:36', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '13', '0,1,2,13,', 'menu_user_management', '/sys/user/?role=', '', 'user', '30', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:09', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '20', '0,1,2,13,20,', 'menu_view', null, null, null, '30', '0', '0', 'sys:user:view', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:09', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '20', '0,1,2,13,20,', 'menu_update', null, null, null, '30', '0', '0', 'sys:user:edit', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:09', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '0,1,', 'menu_my_panel', '', '', 'user', '101', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:15:30', null, '0');
INSERT INTO `sys_menu` VALUES ('28', '27', '0,1,27,', 'menu_personal_information', '', '', '', '990', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:15:56', null, '0');
INSERT INTO `sys_menu` VALUES ('29', '28', '0,1,27,28,', 'menu_personal_information', '/sys/user/info', '', 'user', '30', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:16:05', null, '0');
INSERT INTO `sys_menu` VALUES ('3', '2', '0,1,2,', 'menu_system_configuration', '', '', '', '980', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:39', null, '0');
INSERT INTO `sys_menu` VALUES ('30', '28', '0,1,27,28,', 'menu_change_password', '/sys/user/modifyPwd', '', 'lock', '40', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:16:20', null, '0');
INSERT INTO `sys_menu` VALUES ('31', '2', '0,1,2,', 'menu_log_management', '', '', '', '985', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:19:22', null, '0');
INSERT INTO `sys_menu` VALUES ('32', '31', '0,1,2,31,', 'menu_log_query', '/sys/log', '', 'pencil', '30', '1', '0', 'sys:log:view', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:19:32', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '40', '0,1,2,3,40,', 'menu_delete', '', '', '', '40', '0', '0', 'sys:type:edit', 'admin', '2016-06-02 18:14:15', 'admin', '2017-01-09 16:19:11', null, '0');
INSERT INTO `sys_menu` VALUES ('34', '36', '0,1,44,38,36,', 'menu_update', '', '', '', '20', '0', '0', 'sys:operators:edit', 'admin', '2016-05-28 18:27:21', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '66', '0,1,43,66,', '广告投放', '', '', '', '30', '1', '0', '', 'admin', '2016-05-19 16:00:01', 'admin', '2017-01-09 16:27:27', null, '1');
INSERT INTO `sys_menu` VALUES ('36', '38', '0,1,44,38,', 'menu_TV_operator_management', '/adv/operators', '', 'film', '10', '1', '0', '', 'admin', '2016-05-28 18:26:27', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('37', '40', '0,1,2,3,40,', 'menu_update', '', '', '', '30', '0', '0', 'sys:type:edit', 'admin', '2016-06-02 18:14:36', 'admin', '2017-01-09 16:19:11', null, '0');
INSERT INTO `sys_menu` VALUES ('38', '44', '0,1,44,', 'menu_TV_operator', '', '', 'user', '10', '1', '0', '', 'admin', '2016-05-28 18:24:20', 'admin', '2017-01-09 16:21:19', null, '0');
INSERT INTO `sys_menu` VALUES ('39', '36', '0,1,44,38,36,', 'menu_view', '', '', '', '10', '0', '0', 'sys:operators:view', 'admin', '2016-05-28 18:26:59', 'admin', '2017-01-09 16:21:30', null, '0');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,1,2,3,', 'menu_menu_management', '/sys/menu/', '', 'list-alt', '30', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:52', null, '0');
INSERT INTO `sys_menu` VALUES ('40', '3', '0,1,2,3,', 'menu_display_styles_management', '/adv/type', '', '', '70', '1', '0', '', 'admin', '2016-06-02 17:28:28', 'admin', '2017-01-09 16:19:11', null, '0');
INSERT INTO `sys_menu` VALUES ('41', '54', '0,1,44,49,54,', 'menu_update', '', '', '', '20', '0', '0', 'sys:advertiser:edit', 'admin', '2016-06-07 16:24:34', 'admin', '2017-01-09 16:22:21', null, '0');
INSERT INTO `sys_menu` VALUES ('42', '47', '0,1,47,', 'menu_broadcasting_system', '', '', '', '10', '1', '0', '', 'admin', '2016-05-19 15:55:34', 'admin', '2017-01-09 16:20:24', null, '0');
INSERT INTO `sys_menu` VALUES ('43', '1', '0,1,', 'menu_advertisement_management', '', '', '', '450', '1', '0', '', 'admin', '2016-05-19 15:58:54', 'admin', '2017-01-09 16:27:09', null, '0');
INSERT INTO `sys_menu` VALUES ('44', '1', '0,1,', 'menu_operation_management', '', '', '', '400', '1', '0', '', 'admin', '2016-05-19 15:56:35', 'admin', '2017-01-09 16:20:57', null, '0');
INSERT INTO `sys_menu` VALUES ('45', '58', '0,1,58,', 'menu_statistics_of_package', '', '', '', '10', '1', '0', '', 'admin', '2016-05-19 16:52:26', 'admin', '2017-01-09 16:29:13', null, '0');
INSERT INTO `sys_menu` VALUES ('46', '50', '0,1,47,42,50,', 'menu_update', '', '', '', '30', '0', '0', 'sys:network:edit', 'admin', '2016-05-28 16:56:50', 'admin', '2017-01-09 16:20:40', null, '0');
INSERT INTO `sys_menu` VALUES ('47', '1', '0,1,', 'menu_network management', '', '', '', '300', '1', '0', '', 'admin', '2016-05-19 15:54:20', 'admin', '2017-01-09 16:19:52', null, '0');
INSERT INTO `sys_menu` VALUES ('48', '66', '0,1,43,66,', 'menu_adv_review', '/adv/adelement/audit/query', '', '', '20', '1', '0', '', 'admin', '2016-05-19 15:59:37', 'admin', '2017-01-09 16:28:07', null, '0');
INSERT INTO `sys_menu` VALUES ('49', '44', '0,1,44,', 'menu_advertiser', '', '', '', '25', '1', '0', '', 'admin', '2016-05-19 16:00:39', 'admin', '2017-01-09 16:21:57', null, '0');
INSERT INTO `sys_menu` VALUES ('5', '4', '0,1,2,3,4,', 'menu_view', null, null, null, '30', '0', '0', 'sys:menu:view', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:52', null, '0');
INSERT INTO `sys_menu` VALUES ('50', '42', '0,1,47,42,', 'menu_broadcasting_system_management', '/adv/network', '', 'hdd', '10', '1', '0', '', 'admin', '2016-05-28 15:51:32', 'admin', '2017-01-09 16:20:40', null, '0');
INSERT INTO `sys_menu` VALUES ('51', '45', '0,1,58,45,', 'menu_statistics_of_package_sale', '/adv/combo/comboOperation', '', 'hdd', '30', '1', '0', '', 'admin', '2016-05-19 16:53:28', 'admin', '2017-01-09 16:29:28', null, '0');
INSERT INTO `sys_menu` VALUES ('52', '60', '0,1,87,60,', 'menu_package_management', '/adv/combo', '', '', '10', '1', '0', '', 'admin', '2016-05-19 16:47:20', 'admin', '2017-01-09 16:26:25', null, '0');
INSERT INTO `sys_menu` VALUES ('53', '66', '0,1,43,66,', 'menu_publish', '/adv/adelement', '', '', '10', '1', '0', '', 'admin', '2016-05-19 15:59:17', 'admin', '2017-01-09 16:27:51', null, '0');
INSERT INTO `sys_menu` VALUES ('54', '49', '0,1,44,49,', 'menu_advertiser_management', '/adv/advertiser', '', '', '10', '1', '0', '', 'admin', '2016-05-19 16:45:48', 'admin', '2017-01-09 16:22:21', null, '0');
INSERT INTO `sys_menu` VALUES ('55', '47', '0,1,47,', 'web门户', '', '', '', '30', '1', '0', '20', 'admin', '2016-05-28 15:51:15', 'admin', '2017-01-09 16:19:52', null, '1');
INSERT INTO `sys_menu` VALUES ('56', '38', '0,1,44,38,', 'menu_channel_management', '/adv/channel', '', 'map-marker', '30', '1', '0', '', 'admin', '2016-06-06 15:58:03', 'admin', '2017-01-09 16:21:45', null, '0');
INSERT INTO `sys_menu` VALUES ('57', '56', '0,1,44,38,56,', 'menu_view', '', '', '', '30', '0', '0', 'sys:channel:view', 'admin', '2016-06-06 15:58:43', 'admin', '2017-01-09 16:21:45', null, '0');
INSERT INTO `sys_menu` VALUES ('58', '1', '0,1,', 'menu_analysis_of_statistics', '', '', '', '500', '1', '0', '', 'admin', '2016-05-19 16:50:02', 'admin', '2017-01-09 16:28:55', null, '0');
INSERT INTO `sys_menu` VALUES ('59', '54', '0,1,44,49,54,', 'menu_add', '', '', '', '15', '0', '0', 'sys:advertiser:edit', 'admin', '2016-06-07 16:24:17', 'admin', '2017-01-09 16:22:21', null, '0');
INSERT INTO `sys_menu` VALUES ('6', '4', '0,1,2,3,4,', 'menu_update', null, null, null, '30', '0', '0', 'sys:menu:edit', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:17:52', null, '0');
INSERT INTO `sys_menu` VALUES ('60', '87', '0,1,87,', 'menu_advertisement_package', '', '', '', '20', '1', '0', '', 'admin', '2016-05-19 15:58:32', 'admin', '2017-01-09 16:26:07', null, '0');
INSERT INTO `sys_menu` VALUES ('61', '52', '0,1,87,60,52,', 'menu_update', '', '', '', '30', '0', '0', 'sys:combo:edit', 'admin', '2016-06-12 17:18:10', 'admin', '2017-01-09 16:26:25', null, '0');
INSERT INTO `sys_menu` VALUES ('62', '52', '0,1,87,60,52,', 'menu_delete', '', '', '', '40', '0', '0', 'sys:combo:edit', 'admin', '2016-06-12 17:18:31', 'admin', '2017-01-09 16:26:25', null, '0');
INSERT INTO `sys_menu` VALUES ('63', '64', '0,1,87,60,64,', 'menu_update', '', '', '', '30', '0', '0', 'sys:sell:edit', 'admin', '2016-06-16 11:40:44', 'admin', '2017-01-09 16:26:39', null, '0');
INSERT INTO `sys_menu` VALUES ('64', '60', '0,1,87,60,', 'menu_package_sale', '/adv/sell', '', '', '20', '1', '0', '', 'admin', '2016-06-16 11:40:11', 'admin', '2017-01-09 16:26:39', null, '0');
INSERT INTO `sys_menu` VALUES ('65', '64', '0,1,87,60,64,', 'menu_delete', '', '', '', '20', '0', '0', 'sys:sell:edit', 'admin', '2016-06-16 11:41:09', 'admin', '2017-01-09 16:26:39', null, '0');
INSERT INTO `sys_menu` VALUES ('66', '43', '0,1,43,', 'menu_advertisement_management', '', '', '', '10', '1', '0', '', 'admin', '2016-06-25 09:42:09', 'admin', '2017-01-09 16:27:27', null, '0');
INSERT INTO `sys_menu` VALUES ('67', '53', '0,1,43,66,53,', 'menu_update', '', '', '', '15', '0', '0', 'sys:adv:edit', 'admin', '2016-06-27 11:08:52', 'admin', '2017-01-09 16:27:51', null, '0');
INSERT INTO `sys_menu` VALUES ('68', '53', '0,1,43,66,53,', 'menu_add', '', '', '', '20', '0', '0', 'sys:adv:edit', 'admin', '2016-06-27 11:09:12', 'admin', '2017-01-09 16:27:51', null, '0');
INSERT INTO `sys_menu` VALUES ('69', '70', '0,1,44,82,70,', 'menu_update', '', '', '', '30', '0', '0', 'adv:position:edit', 'admin', '2016-06-29 15:54:01', 'admin', '2017-01-09 16:23:50', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '3', '0,1,2,3,', 'menu_role_management', '/sys/role/', '', 'lock', '50', '1', '0', '', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:08', null, '0');
INSERT INTO `sys_menu` VALUES ('70', '82', '0,1,44,82,', 'menu_display_coordinate_management', '/adv/position', '', 'align-center', '80', '1', '0', '', 'admin', '2016-06-29 15:53:31', 'admin', '2017-01-09 16:23:50', null, '0');
INSERT INTO `sys_menu` VALUES ('71', '70', '0,1,44,82,70,', 'menu_delete', '', '', '', '40', '0', '0', 'adv:position:edit', 'admin', '2016-06-29 15:54:18', 'admin', '2017-01-09 16:23:50', null, '0');
INSERT INTO `sys_menu` VALUES ('72', '73', '0,1,2,3,73,', 'menu_update', '', '', '', '30', '0', '0', 'adv:program:edit', 'admin', '2016-07-20 16:43:53', 'admin', '2017-01-09 16:17:39', null, '1');
INSERT INTO `sys_menu` VALUES ('73', '3', '0,1,2,3,', '外部程序管理', '/adv/program', '', 'leaf', '90', '1', '0', 'adv:program:view', 'admin', '2016-07-20 16:39:26', 'admin', '2017-01-09 16:17:39', null, '1');
INSERT INTO `sys_menu` VALUES ('74', '73', '0,1,2,3,73,', 'menu_delete', '', '', '', '40', '0', '0', 'adv:program:edit', 'admin', '2016-07-20 16:44:16', 'admin', '2017-01-09 16:17:39', null, '1');
INSERT INTO `sys_menu` VALUES ('75', '79', '0,1,43,77,79,', 'menu_review', '', '', '', '40', '0', '0', 'adv:material:audit', 'admin', '2016-07-26 15:43:09', 'admin', '2017-01-09 16:28:40', null, '1');
INSERT INTO `sys_menu` VALUES ('76', '79', '0,1,43,77,79,', 'menu_update', '', '', '', '15', '0', '0', 'adv:material:edit', 'admin', '2016-07-26 15:41:40', 'admin', '2017-01-09 16:28:40', null, '0');
INSERT INTO `sys_menu` VALUES ('77', '43', '0,1,43,', 'menu_materials_management', '', '', '', '15', '1', '0', '', 'admin', '2016-07-26 15:39:56', 'admin', '2017-01-09 16:28:26', null, '0');
INSERT INTO `sys_menu` VALUES ('78', '79', '0,1,43,77,79,', 'menu_take', '', '', '', '30', '0', '0', 'adv:material:claim', 'admin', '2016-07-26 15:42:39', 'admin', '2017-01-09 16:28:40', null, '1');
INSERT INTO `sys_menu` VALUES ('79', '77', '0,1,43,77,', 'menu_advertisment_materials', '/adv/control', '', '', '10', '1', '0', '', 'admin', '2016-07-26 15:41:04', 'admin', '2017-01-09 16:28:40', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '7', '0,1,2,3,7,', 'menu_view', null, null, null, '30', '0', '0', 'sys:role:view', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:08', null, '0');
INSERT INTO `sys_menu` VALUES ('80', '79', '0,1,43,77,79,', 'menu_delete', '', '', '', '20', '0', '0', 'adv:material:delete', 'admin', '2016-07-26 15:42:02', 'admin', '2017-01-09 16:28:40', null, '0');
INSERT INTO `sys_menu` VALUES ('81', '120', '0,1,44,82,120,', 'menu_update', '', '', '', '15', '0', '0', 'sys:range:edit', 'admin', '2016-08-08 18:25:53', 'admin', '2017-01-09 16:22:56', null, '0');
INSERT INTO `sys_menu` VALUES ('82', '44', '0,1,44,', 'menu_display', '', '', '', '75', '1', '0', '', 'admin', '2016-08-08 18:24:55', 'admin', '2017-01-09 16:22:41', null, '0');
INSERT INTO `sys_menu` VALUES ('83', '81', '0,1,44,82,120,81,', 'menu_delete', '', '', '', '20', '0', '0', 'sys:range:edit', 'admin', '2016-08-08 18:26:22', 'admin', '2017-01-09 16:22:56', null, '1');
INSERT INTO `sys_menu` VALUES ('84', '120', '0,1,44,82,120,', 'menu_delete', '', '', '', '20', '0', '0', 'sys:range:edit', 'admin', '2016-08-08 18:27:14', 'admin', '2017-01-09 16:22:56', null, '0');
INSERT INTO `sys_menu` VALUES ('85', '48', '0,1,43,66,48,', 'menu_review', '', '', '', '20', '0', '0', 'sys:adv:audit', 'admin', '2016-08-11 14:51:35', 'admin', '2017-01-09 16:28:07', null, '0');
INSERT INTO `sys_menu` VALUES ('86', '48', '0,1,43,66,48,', 'menu_take', '', '', '', '10', '0', '0', 'sys:adv:audit', 'admin', '2016-08-11 14:50:59', 'admin', '2017-01-09 16:28:07', null, '0');
INSERT INTO `sys_menu` VALUES ('87', '1', '0,1,', 'menu_package_management', '', '', '', '420', '1', '0', '', 'admin', '2016-09-26 10:41:34', 'admin', '2017-01-09 16:25:27', null, '0');
INSERT INTO `sys_menu` VALUES ('88', '45', '0,1,58,45,', 'menu_statistics_of_advertiser_purchase', '/adv/sell/comboSellNumber', '', '', '30', '1', '0', '', 'admin', '2016-11-05 17:37:53', 'admin', '2017-01-09 16:29:39', null, '0');
INSERT INTO `sys_menu` VALUES ('89', '45', '0,1,58,45,', 'menu_time_range_analysis_of_package_sales', '/adv/sell/channleComboSellList', '', 'glass', '50', '1', '0', '', 'admin', '2016-11-09 14:47:23', 'admin', '2017-01-09 16:30:05', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '7', '0,1,2,3,7,', 'menu_update', null, null, null, '30', '0', '0', 'sys:role:edit', null, '2015-06-02 18:09:56', 'admin', '2017-01-09 16:18:08', null, '0');
INSERT INTO `sys_menu` VALUES ('90', '52', '0,1,87,60,52,', 'menu_view', '', '', '', '10', '0', '0', 'sys:combo:view', 'admin', '2016-11-15 18:07:52', 'admin', '2017-01-09 16:26:25', null, '0');
INSERT INTO `sys_menu` VALUES ('91', '70', '0,1,44,82,70,', 'menu_view', '', '', '', '10', '0', '0', 'adv:position:view', 'admin', '2016-11-15 18:02:00', 'admin', '2017-01-09 16:23:50', null, '0');
INSERT INTO `sys_menu` VALUES ('92', '53', '0,1,43,66,53,', 'menu_view', '', '', '', '10', '0', '0', 'sys:adv:view', 'admin', '2016-11-15 18:09:40', 'admin', '2017-01-09 16:27:51', null, '0');
INSERT INTO `sys_menu` VALUES ('93', '64', '0,1,87,60,64,', 'menu_view', '', '', '', '10', '0', '0', 'sys:sell:view', 'admin', '2016-11-15 17:59:17', 'admin', '2017-01-09 16:26:39', null, '0');
INSERT INTO `sys_menu` VALUES ('94', '48', '0,1,43,66,48,', 'menu_view', '', '', '', '5', '0', '0', 'sys:adv:aduit:view', 'admin', '2016-11-15 18:10:33', 'admin', '2017-01-09 16:28:07', null, '0');
INSERT INTO `sys_menu` VALUES ('95', '50', '0,1,47,42,50,', 'menu_view', '', '', '', '10', '0', '0', 'sys:network:view', 'admin', '2016-11-15 18:02:41', 'admin', '2017-01-09 16:20:40', null, '0');
INSERT INTO `sys_menu` VALUES ('96', '120', '0,1,44,82,120,', 'menu_view', '', '', '', '10', '0', '0', 'sys:range:view', 'admin', '2016-11-15 18:00:25', 'admin', '2017-01-09 16:22:56', null, '0');
INSERT INTO `sys_menu` VALUES ('97', '40', '0,1,2,3,40,', 'menu_view', '', '', '', '10', '0', '0', 'sys:type:view', 'admin', '2016-11-15 18:00:03', 'admin', '2017-01-09 16:19:11', null, '0');
INSERT INTO `sys_menu` VALUES ('98', '54', '0,1,44,49,54,', 'menu_view', '', '', '', '10', '0', '0', 'sys:advertiser:view', 'admin', '2016-11-15 18:06:53', 'admin', '2017-01-09 16:22:21', null, '0');
INSERT INTO `sys_menu` VALUES ('99', '79', '0,1,43,77,79,', 'menu_view', '', '', '', '10', '0', '0', 'adv:material:view', 'admin', '2016-11-15 18:08:53', 'admin', '2017-01-09 16:28:40', null, '0');

  