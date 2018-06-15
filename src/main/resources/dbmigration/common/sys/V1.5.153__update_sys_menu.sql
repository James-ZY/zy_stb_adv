
-- 回退已修改的菜单
UPDATE sys_menu set href='/adv/adStatistic/advPlayCount' where href ='/adv/adStatistic/advClickRecordChart';
UPDATE sys_menu set href='/adv/adStatistic/advPlayDetailQuery' where href ='/adv/adStatistic/advPlayRecordChart';
-- 新增统计报表菜单
INSERT INTO `sys_menu` VALUES ('872afa11c1004228ac078dfe102602ec', '3fbe0a7d18d44fde97375478aa090207', '0,1,58,3fbe0a7d18d44fde97375478aa090207,', 'menu_chart_of_sell_adv', '/adv/sell/comboSellStats', '', 'briefcase', '40', '1', '0', '', 'admin', '2018-03-06 11:41:36', 'admin', '2018-03-06 11:43:01', null, '0');
INSERT INTO `sys_menu` VALUES ('29429eed174146f680c75e3027488975', '3fbe0a7d18d44fde97375478aa090207', '0,1,58,3fbe0a7d18d44fde97375478aa090207,', 'menu_chart_of_selltime_sum', '/adv/sell/comboSellValidTime', '', 'repeat', '40', '1', '0', '', 'admin', '2018-03-06 11:40:14', 'admin', '2018-03-06 11:42:53', null, '0');
INSERT INTO `sys_menu` VALUES ('b9860e9a22bc48c6a29800816d29becc', '3fbe0a7d18d44fde97375478aa090207', '0,1,58,3fbe0a7d18d44fde97375478aa090207,', 'menu_chart_of_selltime_slot', '/adv/sell/comboSellTimeSlot', '', 'time', '35', '1', '0', '', 'admin', '2018-03-06 11:38:40', 'admin', '2018-03-06 12:40:30', null, '0');
INSERT INTO `sys_menu` VALUES ('9224d8aba7b64505b7c5564dc8416b56', '3fbe0a7d18d44fde97375478aa090207', '0,1,58,3fbe0a7d18d44fde97375478aa090207,', 'menu_chart_of_play', '/adv/adStatistic/advPlayRecordChart', '', 'play', '30', '1', '0', '', 'admin', '2018-03-06 11:37:05', 'admin', '2018-03-06 11:42:30', null, '0');
INSERT INTO `sys_menu` VALUES ('dc3e4bf521934b76b3cdaa8d94bb410a', '3fbe0a7d18d44fde97375478aa090207', '0,1,58,3fbe0a7d18d44fde97375478aa090207,', 'menu_chart_of_click', '/adv/adStatistic/advClickRecordChart', '', 'check', '30', '1', '0', '', 'admin', '2018-03-06 11:36:15', 'admin', '2018-03-06 11:42:37', null, '0');
INSERT INTO `sys_menu` VALUES ('3fbe0a7d18d44fde97375478aa090207', '58', '0,1,58,', 'menu_chart_of_statistics', '', '', '', '35', '1', '0', '', 'admin', '2018-03-06 10:58:38', 'admin', '2018-03-06 10:59:26', null, '0');
