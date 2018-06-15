DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `web_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户公司网站',
  `contacts` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `icp_icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
   `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属机构',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `advertiser_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '广告商id',
  PRIMARY KEY (`id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';