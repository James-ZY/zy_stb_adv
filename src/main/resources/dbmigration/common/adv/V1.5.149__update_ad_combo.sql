update ad_combo as ac INNER JOIN 
(SELECT a.id,a.ad_combo_name,min(a.start_date) valid_start_time ,max(a.end_date)  valid_end_time
from (SELECT s.start_date,s.end_date,c.ad_combo_name,c.id from ad_combo c left join ad_sell s on c.id = s.ad_combo_id
where  c.ad_is_flag = 0
)a
GROUP BY a.id
ORDER BY a.id) as b   on ac.id = b.id set ac.valid_start_time = b.valid_start_time ,ac.valid_end_time = b.valid_end_time;


UPDATE ad_combo ac set ac.valid_start_time = '2017-01-01',ac.valid_end_time = DATE_SUB(CURDATE(),INTERVAL 1 DAY) where ac.ad_is_flag =0 and ISNULL(ac.valid_start_time); 