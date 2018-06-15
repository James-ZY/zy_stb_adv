update ad_combo c set c.picture_interval = 10,c.picture_times = 1 where c.ad_type_id = 5 and ISNULL(c.picture_interval);

UPDATE `ad_resoure_type` SET  `file_max_size`='0.05', `file_min_size`='0', `width_min`='30', `width_max`='500', `high_min`='30', `high_max`='200', `format`='jpg,png,gif,bmp' WHERE (`id`='51a36c2c08094075bb7a6d146cbe2f98');


alter table ad_element modify column sd_show_param VARCHAR(1000);
alter table ad_element modify column hd_show_param VARCHAR(1000);
