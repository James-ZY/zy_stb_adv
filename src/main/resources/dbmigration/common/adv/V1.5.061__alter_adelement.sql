
ALTER TABLE ad_element  ADD  ad_category_id varchar(64) COLLATE utf8_bin NOT NULL COMMENT '广告分类ID';
ALTER TABLE ad_element  ADD  audit_user_id varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人';