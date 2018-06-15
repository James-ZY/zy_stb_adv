alter TABLE ad_controller ADD ad_roll_flag INT(4);
update ad_controller ac set ac.ad_roll_flag = 1 where ac.ad_type_id = 5;

