INSERT INTO `sys_dict` VALUES ('521439bdfa5b4b0595f0c72a2937e4d5', 'NO', '0', 'adv_resource_enable', '素材参数是否启用', '10', 'admin', '2017-04-01 10:39:37', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('679cc255e7404d629ad3d5e60f5666ba', 'YES', '1', 'adv_resource_enable', '素材参数是否启用', '20', 'admin', '2017-04-01 10:39:53', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('84ba61a4fc374feba1e50354a761e35b', '是', '1', 'adv_resource_enable', '素材参数是否启用', '20', 'admin', '2017-04-01 10:39:14', null, null, null, '0', '0');
INSERT INTO `sys_dict` VALUES ('c6fb7adf53b949e2829edc89b22be793', '否', '0', 'adv_resource_enable', '素材参数是否启用', '10', 'admin', '2017-04-01 10:38:58', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('bf48f1c456294b0a873536b83059f0a3', 'gif', 'gif', 'adv_resource_image_format', '广告类型支持的图片格式', '20', 'admin', '2017-04-01 10:35:03', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('37d45f01c23a4ca68d7cde5347bd0867', 'gif', 'gif', 'adv_resource_image_format', '广告类型支持的图片格式', '20', 'admin', '2017-04-01 10:31:04', 'admin', '2017-04-01 10:31:15', null, '0', '0');
INSERT INTO `sys_dict` VALUES ('c7105403c73c409ca46e8b6585afff3e', 'bmp', 'bmp', 'adv_resource_image_format', '广告类型支持的图片格式', '25', 'admin', '2017-04-01 10:35:19', 'admin', '2017-04-01 10:35:26', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('12e1150c1d22407b8490593e5740cc96', 'bmp', 'bmp', 'adv_resource_image_format', '广告类型支持的图片格式', '25', 'admin', '2017-04-01 10:32:18', null, null, null, '0', '0');
INSERT INTO `sys_dict` VALUES ('dd309ea628ac4dd082fee190c5ead702', 'jpg', 'jpg', 'adv_resource_image_format', '广告类型支持的图片格式', '10', 'admin', '2017-04-01 10:30:09', null, null, null, '0', '0');
INSERT INTO `sys_dict` VALUES ('3cd7845d22444e639c3b866ce10b7472', 'jpg', 'jpg', 'adv_resource_image_format', '广告类型支持的图片格式', '10', 'admin', '2017-04-01 10:33:45', 'admin', '2017-04-01 10:33:55', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('5a64e0b986b04780abe49a5883a8afe8', 'png', 'png', 'adv_resource_image_format', '广告类型支持的图片格式', '15', 'admin', '2017-04-01 10:34:44', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('b246a2b93c9e4b0b855452ccbe253ffe', 'png', 'png', 'adv_resource_image_format', '广告类型支持的图片格式', '15', 'admin', '2017-04-01 10:31:57', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('360fcb2d2b5846daa3c77acaa0d2d6e5', 'ts', 'ts', 'adv_resource_vedio_format', '开机视频支持的视频格式', '10', 'admin', '2017-04-01 10:36:28', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('a5ad43565dce4dfc88ed97b4cae4034b', 'ts', 'ts', 'adv_resource_vedio_format', '开机视频支持的视频格式', '10', 'admin', '2017-04-01 10:36:10', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('e0a2c6dd591d40fc99e4ed8d0443e666', '素材参数管理', '素材参数管理', 'menu_material_param_manage', '菜单--素材参数管理', '10', 'admin', '2017-04-01 10:49:19', null, null, null, '0', '0');
INSERT INTO `sys_dict` VALUES ('f9df93e6486d4b2ea381bff037a85332', 'Material Param Manage', 'Material Param Manage', 'menu_material_param_manage', '菜单--素材参数管理', '10', 'admin', '2017-04-01 10:49:56', null, null, null, '0', '1');

INSERT INTO `sys_menu` VALUES ('126', '3', '0,1,2,3,', 'menu_material_param_manage', '/adv/resource', '', 'picture', '80', '1', '0', '', 'admin', '2017-04-01 10:51:52', 'admin', '2017-04-01 10:52:37', null, '0');
INSERT INTO `sys_menu` VALUES ('129', '126', '0,1,2,3,126,', 'menu_delete', '', '', '', '30', '0', '0', 'adv:adResourceOfType:edit', 'admin', '2017-04-01 10:54:13', null, null, null, '0');

INSERT INTO `sys_menu` VALUES ('128', '126', '0,1,2,3,126,', 'menu_update', '', '', '', '20', '0', '0', 'adv:adResourceOfType:edit', 'admin', '2017-04-01 10:53:59', null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('127', '126', '0,1,2,3,126,', 'menu_view', '', '', '', '10', '0', '0', 'adv:adResourceOfType:view', 'admin', '2017-04-01 10:53:07', null, null, null, '0');

INSERT INTO `sys_role_menu` VALUES ('admin', '126');
INSERT INTO `sys_role_menu` VALUES ('admin', '127');
INSERT INTO `sys_role_menu` VALUES ('admin', '128');
INSERT INTO `sys_role_menu` VALUES ('admin', '129');
 