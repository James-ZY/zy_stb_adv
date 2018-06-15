alter table ad_element ADD audit_date  datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '审核时间';
alter table ad_element ADD claim_date  datetime COLLATE utf8_bin  DEFAULT NULL COMMENT '认领时间';