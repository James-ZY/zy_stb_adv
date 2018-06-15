-- 修改广告点击次数统计报表menu url
UPDATE sys_menu set href='/adv/adStatistic/advClickRecordChart' where href ='/adv/adStatistic/advPlayCount';
-- 修改广告播放时长统计报表menu url
UPDATE sys_menu set href='/adv/adStatistic/advPlayRecordChart' where href ='/adv/adStatistic/advPlayDetailQuery';