alter TABLE ad_network ADD online_status char(1);
UPDATE ad_network an set an.online_status = 0 where ISNULL(an.online_status); 