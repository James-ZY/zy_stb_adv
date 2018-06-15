SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `ad_category`;
CREATE TABLE `ad_category` (
  `category_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告分类ID',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `category_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '广告分类名称',
   `grade` char(1) COLLATE utf8_bin NOT NULL COMMENT  '广告类分类级别',
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `ad_ad_category_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='广告分类表';