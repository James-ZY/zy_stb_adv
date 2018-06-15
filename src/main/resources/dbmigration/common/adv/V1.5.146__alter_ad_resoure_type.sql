alter TABLE ad_resoure_type ADD roll_flag INT(4);
update ad_resoure_type ac set ac.roll_flag = 1 where ac.ad_type_id = 5;
INSERT INTO `ad_resoure_type`  VALUES ('21', '0.05', '0', '30', '100','720', '7200', NULL, '15', '0', '0', '0', 'jpg,png,gif,bmp', '0', '5', '2017-04-11 15:09:32', 'admin', 'admin', '2017-12-15 14:46:22', NULL, '0','2');
INSERT INTO `ad_resoure_type`  VALUES ('22', '0.1', '0', '32', '60','720', '7200', NULL, '15', '0', '0', '1', 'jpg,png,gif,bmp', '0', '5', '2017-04-11 15:09:59', 'admin', 'admin', '2017-12-15 14:46:42', NULL, '0','2');


