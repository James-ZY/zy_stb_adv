alter TABLE ad_controller ADD ad_roll_text_font VARCHAR(64);
alter TABLE ad_controller ADD ad_roll_text_bold INT(4);
alter TABLE ad_controller ADD ad_roll_text_italic INT(4);
update ad_controller ac set ac.ad_roll_text_font = '宋体',ac.ad_roll_text_bold = 0,ac.ad_roll_text_italic = 0 where ac.ad_type_id = 5;

