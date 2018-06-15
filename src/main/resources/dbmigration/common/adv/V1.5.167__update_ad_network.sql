alter TABLE ad_network ADD is_control_all char(1);
alter table ad_combo_district modify column ad_self_district_id varchar(64) NULL;
alter table ad_operator_district modify column ad_self_district_id varchar(64) NULL;
alter table ad_network_district modify column ad_self_district_id varchar(64) NULL;

