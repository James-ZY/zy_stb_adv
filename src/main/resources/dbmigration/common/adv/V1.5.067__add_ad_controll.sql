alter table ad_controller ADD ad_roll_text_size  int(11) COLLATE utf8_bin  DEFAULT NUll COMMENT '滚动广告的文字大小';
 alter table ad_controller ADD ad_roll_text_color  varchar(64) COLLATE utf8_bin  DEFAULT NULL COMMENT '滚动广告的文字颜色';
 alter table ad_controller ADD ad_is_purity int(11) COLLATE utf8_bin  DEFAULT NUll COMMENT '滚动广告的背景（0纯色 1图片）';
 alter table ad_controller ADD ad_roll_background  varchar(255) COLLATE utf8_bin  DEFAULT NULL COMMENT '滚动的背景（纯色保存为rgb的值，图片保存为路径）';
 