alter TABLE ad_controller ADD ad_version VARCHAR(20);
update ad_controller set ad_version = 'v1.0' WHERE ISNULL(ad_version);