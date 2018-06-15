alter table ad_version modify column version_id varchar(40);
UPDATE `ad_version` SET `version_id`='5.1.0.13-20180614_beta', `create_by`='admin', `create_date`='2018-06-014 10:00:00' WHERE `id`='1';