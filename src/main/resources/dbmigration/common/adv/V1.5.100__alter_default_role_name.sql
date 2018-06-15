update sys_dict s set s.label='Super System Admin' , s.`value`='Super System Admin' where s.type='role_superadmin' and s.dict_locale=1;

update sys_dict s set s.label='Operation Management User' , s.`value`='Operation Management User' where s.type='role_operations_staff' and s.dict_locale=1;

update sys_dict s set s.label='Operaton Management Admin' , s.`value`='Operaton Management Admin' where s.type='role_operations_admin' and s.dict_locale=1;

update sys_dict s set s.label='Network Management Admin' , s.`value`='Network Management Admin' where s.type='role_network_admin' and s.dict_locale=1;

update sys_dict s set s.label='Network Management User' , s.`value`='Network Management User' where s.type='role_network_staff' and s.dict_locale=1;

update sys_dict s set s.label='Content Review User' , s.`value`='Content Review User' where s.type='role_audit_staff' and s.dict_locale=1;

update sys_dict s set s.label='Content Review Admin' , s.`value`='Content Review Admin' where s.type='role_audit_admin' and s.dict_locale=1;

update sys_dict s set s.label='Advertiser Management Admin' , s.`value`='Advertiser Management Admin' where s.type='role_adv_manage_admin' and s.dict_locale=1;

update sys_dict s set s.label='Advertiser Management User' , s.`value`='Advertiser Management User' where s.type='role_adv_manage_staff' and s.dict_locale=1;

update sys_dict s set s.label='Advertiser Admin' , s.`value`='Advertiser Admin' where s.type='role_adv_amin' and s.dict_locale=1;

update sys_dict s set s.label='Advertiser  User' , s.`value`='Advertiser  User' where s.type='role_adv_staff' and s.dict_locale=1;