update sys_dict s set label='审核中' where s.type='adv_status' and s.`value`='1' and s.dict_locale=0;
update sys_dict s set label='Under Review' where s.type='adv_status' and s.`value`='1' and s.dict_locale=1;