SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_type`;
CREATE TABLE `ad_type` (
  `ad_type_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型ID',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `ad_type_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告类型名称',
  `ad_type_description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '广告类型描述',
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