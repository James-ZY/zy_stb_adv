DELETE from ad_district_category;
alter table ad_district_category  add column `type` varchar(30);

--顶级节点
INSERT INTO `ad_district_category`  VALUES ('1', '0', '0,', 'India', '1', NULL, '2016-12-08 00:00:00', NULL, NULL, 'system default data', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('2', '0', '0,', '中国', '2', NULL, '2016-12-08 00:00:00', NULL, NULL, 'system default data', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('3', '0', '0,', 'Pakistan', '3', NULL, '2016-12-08 00:00:00', NULL, NULL, 'system default data', '0', 'Pakistan');

--印度
INSERT INTO `ad_district_category`  VALUES ('010', '1', '0,1,', 'Andhra Pradesh', 'IND010', NULL, NULL, 'admin', '2018-04-16 18:28:07', 'Andhra Pradesh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('010010', 'IND010', '0,1,IND010,', 'Anantapur', 'IND010010', NULL, NULL, 'admin', '2018-03-19 16:27:57', 'Anantapur', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('010020', 'IND010', '0,1,IND010,', 'Chittoor', 'IND010020', NULL, NULL, 'admin', '2018-03-19 16:28:09', 'Chittoor', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('010010010', 'IND010010', '0,1,IND010,IND010010,', 'Anantapur', 'IND010010010', NULL, NULL, 'admin', '2018-03-19 16:31:18', 'Anantapur', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('010010020', 'IND010010', '0,1,IND010,IND010010,', 'Atmakur', 'IND010010020', NULL, NULL, 'admin', '2018-03-19 16:31:27', 'Atmakur', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('010010030', 'IND010010', '0,1,IND010,IND010010,', 'Bukkarayasamudram', 'IND010010030', NULL, NULL, 'admin', '2018-03-19 16:31:35', 'Bukkarayasamudram', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('020010', 'IND020', '0,1,IND020,', 'Tawang', 'IND020010', NULL, NULL, 'admin', '2018-03-19 16:30:17', 'Tawang', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('020020', 'IND020', '0,1,IND020,', 'West Kameng', 'IND020020', NULL, NULL, 'admin', '2018-03-19 16:30:28', 'West Kameng', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('030010', 'IND030', '0,1,IND030,', 'Baksa', 'IND030010', NULL, NULL, 'admin', '2018-03-19 16:30:48', 'Baksa', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('030020', 'IND030', '0,1,IND030,', 'Barpeta', 'IND030020', NULL, NULL, 'admin', '2018-03-19 16:30:57', 'Barpeta', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('050010', 'IND050', '0,1,IND050,', 'Balod', 'IND050010', NULL, NULL, 'admin', '2018-03-19 16:29:00', 'Balod', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('050020', 'IND050', '0,1,IND050,', 'Baloda Bazar', 'IND050020', NULL, NULL, 'admin', '2018-03-19 16:29:08', 'Baloda Bazar', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('050030', 'IND050', '0,1,IND050,', 'Balrampur', 'IND050030', NULL, NULL, 'admin', '2018-03-19 16:29:16', 'Balrampur', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('020', '1', '0,1,', 'Arunachal Pradesh', 'IND020', NULL, NULL, 'admin', '2018-03-19 16:27:34', 'Arunachal Pradesh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('030', '1', '0,1,', 'Assam', 'IND030', NULL, NULL, 'admin', '2018-03-19 16:27:43', 'Assam', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('040', '1', '0,1,', 'Bihar', 'IND040', NULL, NULL, 'admin', '2018-03-19 16:28:32', 'Bihar', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('050', '1', '0,1,', 'Chhattisgarh', 'IND050', NULL, NULL, 'admin', '2018-03-19 16:28:42', 'Chhattisgarh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('060', '1', '0,1,', 'Goa', 'IND060', NULL, NULL, 'admin', '2018-03-20 15:15:28', 'Goa', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('070', '1', '0,1,', 'Gujarat', 'IND070', NULL, NULL, 'admin', '2018-03-20 15:15:37', 'Gujarat', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('080', '1', '0,1,', 'Haryana', 'IND080', NULL, NULL, 'admin', '2018-03-20 15:15:44', 'Haryana', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('090', '1', '0,1,', 'Himachal Pradesh', 'IND090', NULL, NULL, 'admin', '2018-03-20 15:15:51', 'Himachal Pradesh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('100', '1', '0,1,', 'Jammu and Kashmir', 'IND100', NULL, NULL, 'admin', '2018-03-20 15:15:59', 'Jammu and Kashmir', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('110', '1', '0,1,', 'Jharkhand', 'IND110', NULL, NULL, 'admin', '2018-03-20 15:16:07', 'Jharkhand', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('120', '1', '0,1,', 'Karnataka', 'IND120', NULL, NULL, 'admin', '2018-03-20 15:16:13', 'Karnataka', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('130', '1', '0,1,', 'Kerala', 'IND130', NULL, NULL, 'admin', '2018-03-20 15:16:20', 'Kerala', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('140', '1', '0,1,', 'Madhya Pradesh', 'IND140', NULL, NULL, 'admin', '2018-03-20 15:16:27', 'Madhya Pradesh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('150', '1', '0,1,', 'Maharashtra', 'IND150', NULL, NULL, 'admin', '2018-03-20 15:16:32', 'Maharashtra', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('160', '1', '0,1,', 'Manipur', 'IND160', NULL, NULL, 'admin', '2018-03-20 15:16:39', 'Manipur', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('170', '1', '0,1,', 'Meghalaya', 'IND170', NULL, NULL, 'admin', '2018-03-20 15:16:45', 'Meghalaya', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('180', '1', '0,1,', 'Mizoram', 'IND180', NULL, NULL, 'admin', '2018-03-20 15:16:51', 'Mizoram', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('190', '1', '0,1,', 'Nagaland', 'IND190', NULL, NULL, 'admin', '2018-03-20 15:16:57', 'Nagaland', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('200', '1', '0,1,', 'Odisha', 'IND200', NULL, NULL, 'admin', '2018-03-20 15:17:04', 'Odisha', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('210', '1', '0,1,', 'Punjab', 'IND210', NULL, NULL, 'admin', '2018-03-20 15:17:38', 'Punjab', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('220', '1', '0,1,', 'Rajasthan', 'IND220', NULL, NULL, 'admin', '2018-03-20 15:17:44', 'Rajasthan', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('230', '1', '0,1,', 'Sikkim', 'IND230', NULL, NULL, 'admin', '2018-03-20 15:17:50', 'Sikkim', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('240', '1', '0,1,', 'Tamil Nadu', 'IND240', NULL, NULL, 'admin', '2018-03-20 15:17:56', 'Tamil Nadu', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('250', '1', '0,1,', 'Tripura', 'IND250', NULL, NULL, 'admin', '2018-03-20 15:18:03', 'Tripura', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('260', '1', '0,1,', 'Uttar Pradesh', 'IND260', NULL, NULL, 'admin', '2018-03-20 15:18:11', 'Uttar Pradesh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('270', '1', '0,1,', 'Uttarakhand', 'IND270', NULL, NULL, 'admin', '2018-03-20 15:18:16', 'Uttarakhand', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('280', '1', '0,1,', 'West Bengal', 'IND280', NULL, NULL, 'admin', '2018-03-20 15:18:22', 'West Bengal', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('290', '1', '0,1,', 'Telangana', 'IND290', NULL, NULL, 'admin', '2018-03-20 15:18:27', 'Telangana', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('300', '1', '0,1,', 'Andaman and Nicobar', 'IND300', NULL, NULL, 'admin', '2018-03-20 15:18:33', 'Andaman and Nicobar', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('310', '1', '0,1,', 'Chandigarh', 'IND310', NULL, NULL, 'admin', '2018-03-20 15:18:39', 'Chandigarh', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('320', '1', '0,1,', 'Dadra and Nagar Haveli', 'IND320', NULL, NULL, 'admin', '2018-03-20 15:18:45', 'Dadra and Nagar Haveli', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('330', '1', '0,1,', 'Daman and Diu', 'IND330', NULL, NULL, 'admin', '2018-03-20 15:18:51', 'Daman and Diu', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('340', '1', '0,1,', 'Lakshadweep', 'IND340', NULL, NULL, 'admin', '2018-03-20 15:18:57', 'Lakshadweep', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('350', '1', '0,1,', 'NCT Delhi', 'IND350', NULL, NULL, 'admin', '2018-03-20 15:19:04', 'NCT Delhi', '0', 'India');
INSERT INTO `ad_district_category`  VALUES ('360', '1', '0,1,', 'Puducherry', 'IND360', NULL, NULL, 'admin', '2018-03-20 15:19:10', 'Puducherry', '0', 'India');

--中国
INSERT INTO `ad_district_category`  VALUES ('010', '2', '0,2,', '北京', 'CHN010', NULL, NULL, 'admin', '2018-04-16 17:06:31', 'BEIJING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('020', '2', '0,2,', '上海', 'CHN020', NULL, NULL, 'admin', '2018-04-16 17:06:43', 'SHANGHAI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('030', '2', '0,2,', '天津', 'CHN030', NULL, NULL, 'admin', '2018-04-16 17:06:54', 'TIANJIN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('040', '2', '0,2,', '重庆', 'CHN040', NULL, NULL, 'admin', '2018-04-16 17:07:04', 'CHONGQING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('050', '2', '0,2,', '广东省', 'CHN050', NULL, NULL, 'admin', '2018-04-16 17:07:12', 'GUANGDONG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('060', '2', '0,2,', '江苏省', 'CHN060', NULL, NULL, 'admin', '2018-04-16 17:07:26', 'JIANGSU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('070', '2', '0,2,', '浙江省', 'CHN070', NULL, NULL, 'admin', '2018-04-16 17:07:53', 'ZHEJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('080', '2', '0,2,', '安徽省', 'CHN080', NULL, NULL, 'admin', '2018-04-16 17:08:10', 'ANHUI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('090', '2', '0,2,', '福建省', 'CHN090', NULL, NULL, 'admin', '2018-04-16 17:08:20', 'FUJIAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('100', '2', '0,2,', '甘肃省', 'CHN100', NULL, NULL, 'admin', '2018-04-16 17:08:30', 'GANSU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('110', '2', '0,2,', '广西', 'CHN110', NULL, NULL, 'admin', '2018-04-16 17:08:37', 'GUANGXI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('120', '2', '0,2,', '贵州省', 'CHN120', NULL, NULL, 'admin', '2018-04-16 17:08:46', 'GUIZHOU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('130', '2', '0,2,', '海南省', 'CHN130', NULL, NULL, 'admin', '2018-04-16 17:09:11', 'HAINAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('140', '2', '0,2,', '河北省', 'CHN140', NULL, NULL, 'admin', '2018-04-16 17:09:22', 'HEBEI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('150', '2', '0,2,', '河南省', 'CHN150', NULL, NULL, 'admin', '2018-04-16 17:09:30', 'HENAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('160', '2', '0,2,', '黑龙江省', 'CHN160', NULL, NULL, 'admin', '2018-04-16 17:09:41', 'HEILONGJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('170', '2', '0,2,', '湖北省', 'CHN170', NULL, NULL, 'admin', '2018-04-16 17:09:49', 'HUBEI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('180', '2', '0,2,', '湖南省', 'CHN180', NULL, NULL, 'admin', '2018-04-16 17:10:00', 'HUNAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('190', '2', '0,2,', '吉林省', 'CHN190', NULL, NULL, 'admin', '2018-04-16 17:10:19', 'JILIN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('200', '2', '0,2,', '江西省', 'CHN200', NULL, NULL, 'admin', '2018-04-16 17:10:28', 'JIANGXI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('210', '2', '0,2,', '辽宁省', 'CHN210', NULL, NULL, 'admin', '2018-04-16 17:10:36', 'LIAONING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('220', '2', '0,2,', '内蒙古', 'CHN220', NULL, NULL, 'admin', '2018-04-16 17:10:49', 'NEIMENGGU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('230', '2', '0,2,', '宁夏', 'CHN230', NULL, NULL, 'admin', '2018-04-16 17:10:59', 'NINGXIA', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('240', '2', '0,2,', '青海省', 'CHN240', NULL, NULL, 'admin', '2018-04-16 17:11:08', 'QINGHAI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('250', '2', '0,2,', '山东省', 'CHN250', NULL, NULL, 'admin', '2018-04-16 17:11:20', 'SHANDONG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('260', '2', '0,2,', '山西省', 'CHN260', NULL, NULL, 'admin', '2018-04-16 17:11:41', 'SHANXI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('270', '2', '0,2,', '陕西省', 'CHN270', NULL, NULL, 'admin', '2018-04-16 17:11:54', 'SHANXI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280', '2', '0,2,', '四川省', 'CHN280', NULL, NULL, 'admin', '2018-04-16 17:12:03', 'SICHUAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('290', '2', '0,2,', '西藏', 'CHN290', NULL, NULL, 'admin', '2018-04-16 17:12:16', 'XIZANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('300', '2', '0,2,', '新疆', 'CHN300', NULL, NULL, 'admin', '2018-04-16 17:12:29', 'XINJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('310', '2', '0,2,', '云南省', 'CHN310', NULL, NULL, 'admin', '2018-04-16 17:12:37', 'YUNNAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('320', '2', '0,2,', '香港', 'CHN320', NULL, NULL, 'admin', '2018-04-16 17:12:46', 'HONGKONG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('330', '2', '0,2,', '澳门', 'CHN330', NULL, NULL, 'admin', '2018-04-16 17:12:55', 'MACAO', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('340', '2', '0,2,', '台湾', 'CHN340', NULL, NULL, 'admin', '2018-04-16 17:13:03', 'TAIWAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010010', 'CHN010', '0,2,CHN010,', '东城区', 'CHN010010', NULL, NULL, 'admin', '2018-04-16 17:24:51', 'DONGCHENG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010020', 'CHN010', '0,2,CHN010,', '西城区', 'CHN010020', NULL, NULL, 'admin', '2018-04-16 17:25:00', 'XICHENG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010030', 'CHN010', '0,2,CHN010,', '朝阳区', 'CHN010030', NULL, NULL, 'admin', '2018-04-16 17:25:58', 'CHAOYANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010040', 'CHN010', '0,2,CHN010,', '海淀区', 'CHN010040', NULL, NULL, 'admin', '2018-04-16 17:26:06', 'HAIDIAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010050', 'CHN010', '0,2,CHN010,', '石景山', 'CHN010050', NULL, NULL, 'admin', '2018-04-16 17:26:15', 'SHIJINGSHAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010060', 'CHN010', '0,2,CHN010,', '门头沟', 'CHN010060', NULL, NULL, 'admin', '2018-04-16 17:26:23', 'MENTOUGOU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010070', 'CHN010', '0,2,CHN010,', '丰台区', 'CHN010070', NULL, NULL, 'admin', '2018-04-16 17:26:30', 'FENGTAI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010080', 'CHN010', '0,2,CHN010,', '房山区', 'CHN010080', NULL, NULL, 'admin', '2018-04-16 17:26:36', 'FANGSHAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010090', 'CHN010', '0,2,CHN010,', '大兴区', 'CHN010090', NULL, NULL, 'admin', '2018-04-16 17:26:44', 'DAXING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010100', 'CHN010', '0,2,CHN010,', '通州区', 'CHN010100', NULL, NULL, 'admin', '2018-04-16 17:26:50', 'TONGZHOU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010110', 'CHN010', '0,2,CHN010,', '顺义区', 'CHN010110', NULL, NULL, 'admin', '2018-04-16 17:27:01', 'SHUNYI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010120', 'CHN010', '0,2,CHN010,', '平谷区', 'CHN010120', NULL, NULL, 'admin', '2018-04-16 17:27:08', 'PINGGU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010130', 'CHN010', '0,2,CHN010,', '昌平区', 'CHN010130', NULL, NULL, 'admin', '2018-04-16 17:27:15', 'CHANGPING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010140', 'CHN010', '0,2,CHN010,', '怀柔区', 'CHN010140', NULL, NULL, 'admin', '2018-04-16 17:27:22', 'HUAIROU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010150', 'CHN010', '0,2,CHN010,', '延庆县', 'CHN010150', NULL, NULL, 'admin', '2018-04-16 17:27:30', 'YANQING', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('010160', 'CHN010', '0,2,CHN010,', '密云县', 'CHN010160', NULL, NULL, 'admin', '2018-04-16 17:27:38', 'MIYUN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010', 'CHN280', '0,2,CHN280,', '成都', 'CHN280010', NULL, NULL, 'admin', '2018-04-16 17:29:59', 'CHENGDU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010010', 'CHN280010', '0,2,CHN280,CHN280010,', '成华区', 'CHN280010010', NULL, NULL, 'admin', '2018-04-16 17:31:02', 'CHENGHUA', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010020', 'CHN280010', '0,2,CHN280,CHN280010,', '武侯区', 'CHN280010020', NULL, NULL, 'admin', '2018-04-16 17:31:11', 'WUHOU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010030', 'CHN280010', '0,2,CHN280,CHN280010,', '青羊区', 'CHN280010030', NULL, NULL, 'admin', '2018-04-16 17:31:23', 'QINGYANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010040', 'CHN280010', '0,2,CHN280,CHN280010,', '锦江区', 'CHN280010040', NULL, NULL, 'admin', '2018-04-16 17:31:32', 'JINJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010050', 'CHN280010', '0,2,CHN280,CHN280010,', '金牛区', 'CHN280010050', NULL, NULL, 'admin', '2018-04-16 17:31:43', 'JINNIU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010060', 'CHN280010', '0,2,CHN280,CHN280010,', '龙泉驿', 'CHN280010060', NULL, NULL, 'admin', '2018-04-16 17:31:52', 'LONGQUANYI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010070', 'CHN280010', '0,2,CHN280,CHN280010,', '青白江', 'CHN280010070', NULL, NULL, 'admin', '2018-04-16 17:32:00', 'QINGBAIJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010080', 'CHN280010', '0,2,CHN280,CHN280010,', '新都区', 'CHN280010080', NULL, NULL, 'admin', '2018-04-16 17:32:09', 'XINDU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010090', 'CHN280010', '0,2,CHN280,CHN280010,', '双流县', 'CHN280010090', NULL, NULL, 'admin', '2018-04-16 17:32:16', 'SHUANGLIU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010100', 'CHN280010', '0,2,CHN280,CHN280010,', '郫县', 'CHN280010100', NULL, NULL, 'admin', '2018-04-16 17:32:27', 'PIXIAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010110', 'CHN280010', '0,2,CHN280,CHN280010,', '温江区', 'CHN280010110', NULL, NULL, 'admin', '2018-04-16 17:32:44', 'WENJIANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010120', 'CHN280010', '0,2,CHN280,CHN280010,', '金堂县', 'CHN280010120', NULL, NULL, 'admin', '2018-04-16 17:32:55', 'JINTANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010130', 'CHN280010', '0,2,CHN280,CHN280010,', '高新区', 'CHN280010130', NULL, NULL, 'admin', '2018-04-16 17:33:05', 'GAOXINQU', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280010140', 'CHN280010', '0,2,CHN280,CHN280010,', '崇州市', 'CHN280010140', NULL, NULL, 'admin', '2018-04-16 17:33:18', 'CHONGZHOUSHI', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280020', 'CHN280', '0,2,CHN280,', '绵阳', 'CHN280020', NULL, NULL, 'admin', '2018-04-16 17:30:21', 'MIANYANG', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280030', 'CHN280', '0,2,CHN280,', '乐山', 'CHN280030', NULL, NULL, 'admin', '2018-04-16 17:30:34', 'LESHAN', '0', 'China');
INSERT INTO `ad_district_category`  VALUES ('280040', 'CHN280', '0,2,CHN280,', '泸州', 'CHN280040', NULL, NULL, 'admin', '2018-04-16 17:30:42', 'LUZHOU', '0', 'China');

