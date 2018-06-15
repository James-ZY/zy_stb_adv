DROP TABLE IF EXISTS `ad_resoure_type`;
CREATE TABLE `ad_resoure_type` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `file_max_size` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '文件最小',
  `file_min_size` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '文件最大',
  `width_min` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '宽度最小值',
  `width_max` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '宽度最大值',
  `high_min` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '高度最小值',
  `high_max` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '高度最大值',
  `frame_min` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '帧数最小值',
  `frame_max` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '帧数最大值',
   `rate_min` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '码流最小值',
  `rate_max` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '码流最大值',
   `flag` int(11) COLLATE utf8_bin NOT NULL COMMENT '（0标清 1高清）',
  `format` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '图片格式或者视频格式',
  `enable` int(11) COLLATE utf8_bin NOT NULL  DEFAULT '0' COMMENT '是否启用（0启用 1不启用） ',
   `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_resoure_type_del` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告类型素材的大小表';