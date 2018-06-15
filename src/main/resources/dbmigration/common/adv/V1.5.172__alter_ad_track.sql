DROP TABLE IF EXISTS `ad_track`;
CREATE TABLE `ad_track` (
  `track_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `ad_type_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID（仅限于频道相关的广告）',
   ad_range_id varchar(50) COLLATE utf8_bin NOT NULL COMMENT '广告范围ID（）',
  `coordinates` varchar(255)   NOT NULL COMMENT '坐标集合',
  `show_time`int(11) NOT NULL COMMENT '总的显示时间',
  `bg_width`int(6) NOT NULL COMMENT '背景宽度',
  `bg_height`int(6) NOT NULL COMMENT '背景高度',
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
  KEY `ad_track_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告轨迹表';

ALTER  TABLE ad_combo  ADD track_mode  int(4);
ALTER  TABLE ad_combo  ADD hdtrack_id varchar(64);
ALTER  TABLE ad_combo  ADD sdtrack_id varchar(64);

UPDATE ad_combo c set c.track_mode = 1 where  c.ad_type_id = 4 and ISNULL(c.track_mode);