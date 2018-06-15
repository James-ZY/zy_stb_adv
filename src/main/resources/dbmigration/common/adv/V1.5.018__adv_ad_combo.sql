DROP TABLE IF EXISTS `ad_combo`;
CREATE TABLE `ad_combo` (
  `ad_combo_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '套餐Id',
  `ad_combo_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `ad_is_flag` int(11) COLLATE utf8_bin DEFAULT 0 COMMENT '是否跟频道相关',
  `start_time` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '广告每天播放开始的时间（小时）',
  `end_time`  varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '广告每天播放结束时刻（数据库保存格式类似09:00:59）',
   `week`  varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '日期规划，频道相关的时候默认保存的值是（1,2,3,4,5,6,7）',
  `show_time` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '展示时间',
  `show_count` int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '展示次数',
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型id',
  `status` int(11) COLLATE utf8_bin DEFAULT 0 COMMENT '套餐状态',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
 `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_combo_id` (`ad_combo_id`),
  KEY `ad_combo_del_flag` (`del_flag`)
   
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告套餐表';