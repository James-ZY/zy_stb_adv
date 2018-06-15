DROP TABLE IF EXISTS `ad_range`;
CREATE TABLE `ad_range` (
  `range_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID（仅限于频道相关的广告）',
  `end_y` int(11)   NOT NULL COMMENT '最大坐标y',
  `end_x` int(11)   NOT NULL COMMENT '最大坐标x',
  `begin_y` int(11)  NOT NULL COMMENT '最小坐标y',
  `begin_x`int(11) NOT NULL COMMENT '最小坐标x',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '是否启用（0未启用 1启用）',
   `flag` int(11) NOT NULL DEFAULT '0' COMMENT '分辨率格式（0标清 1高清）',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_postion_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='坐标范围表';