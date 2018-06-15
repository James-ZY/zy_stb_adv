ALTER TABLE ad_combo DROP COLUMN start_time; 
ALTER TABLE ad_combo DROP COLUMN end_time; 
ALTER TABLE ad_combo  ADD  start_hour int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的开始小时';
ALTER TABLE ad_combo  ADD  start_minutes int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的开始分钟';
ALTER TABLE ad_combo  ADD  start_second int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的开始秒数';
ALTER TABLE ad_combo  ADD  end_hour int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的结束小时';
ALTER TABLE ad_combo  ADD  end_minutes int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的结束分钟';
ALTER TABLE ad_combo  ADD  end_second int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '套餐销售时间段的结束分钟';