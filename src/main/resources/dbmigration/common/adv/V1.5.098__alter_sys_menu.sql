update sys_menu m set m.parent_id='42'  where m.id='56';

update sys_menu m set m.parent_ids='0,1,47,42,'  where m.id='56';
 
update sys_menu m set m.parent_ids ='0,1,47,42,56,' where m.id='57';