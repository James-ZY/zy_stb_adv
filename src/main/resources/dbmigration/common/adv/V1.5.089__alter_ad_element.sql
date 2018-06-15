ALTER TABLE ad_element  ADD  ad_is_sd int(11) NOT NULL default 0 COMMENT '是否启用标清 （0不启用 1启用）';
ALTER TABLE ad_element  ADD  ad_is_hd int(11) NOT NULL default 0 COMMENT '是否启用高清 （0不启用 1启用）';