DROP TABLE IF EXISTS `ad_postion`;
CREATE TABLE `ad_postion` (
  `position_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '位置Id',
  `position_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '位置名称',
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `velocity` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '移动速度',
  `end_point_y` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '结束坐标y',
  `end_point_x` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '结束坐标x',
  `begin_point_y` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '起始坐标y',
  `begin_point_x`int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '起始坐标x',
  `is_flag` int(11) DEFAULT '0' COMMENT '位置形式（0坐标 1轨迹）',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_postion_id` (`position_id`),
  KEY `ad_postion_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告位置表';