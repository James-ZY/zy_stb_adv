alter TABLE ad_element ADD velocity INT(4);
update ad_element ae LEFT JOIN ad_combo ac on ae.ad_combo_id = ac.id set ae.velocity = 2 where ac.ad_type_id = 5;

