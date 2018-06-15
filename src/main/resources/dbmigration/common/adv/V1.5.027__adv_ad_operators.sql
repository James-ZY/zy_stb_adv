/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50705
Source Host           : localhost:3306
Source Database       : adv

Target Server Type    : MYSQL
Target Server Version : 50705
File Encoding         : 65001

Date: 2016-05-24 10:05:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ad_channel`
-- ----------------------------
DROP TABLE IF EXISTS `ad_operators`;
CREATE TABLE `ad_operators` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ad_operators_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '电视运营商id',
  `ad_operators_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT'电视运营商名称',
  `ad_password` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `ad_contact` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '联系人',
  `ad_mobilephone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `ad_telphone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `ad_area` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '地区',
  `ad_number` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '订户数',
  `ad_net_work_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '广告发送器ID',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `ad_operators_id` (`ad_operators_id`),
  KEY `ad_operators_id_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电视运营商';

-- ----------------------------
-- Records of ad_channel
-- ----------------------------
