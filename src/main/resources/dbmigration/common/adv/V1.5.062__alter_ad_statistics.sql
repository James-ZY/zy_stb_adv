ALTER TABLE ad_statistics  ADD  system_type int(11) COLLATE utf8_bin DEFAULT NULL COMMENT '扫描手机的操作系统（0安卓 1IOs 2其他）';
ALTER TABLE ad_statistics  ADD  boss_user_code varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'boss登录用户code';
ALTER TABLE ad_statistics  ADD  is_scan int(11) COLLATE utf8_bin DEFAULT 0 COMMENT '是否是跳转的广告(0播放记录 1扫描记录)';