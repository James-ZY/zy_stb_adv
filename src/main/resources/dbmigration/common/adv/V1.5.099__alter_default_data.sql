delete from sys_role;

delete from sys_user_role;

delete from sys_user;

delete from sys_role_menu;
/*
	默认角色
*/
INSERT INTO `sys_role` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '2', 'role_operations_staff', '2', 'admin', '2017-05-12 09:54:41', 'admin', '2017-05-12 10:44:02', null, '0');
INSERT INTO `sys_role` VALUES ('8e9272bce41245b8a26b687fdf389012', '4', 'role_audit_staff', '2', 'admin', '2017-05-12 10:01:07', 'admin', '2017-05-12 10:45:37', null, '0');
INSERT INTO `sys_role` VALUES ('96229d2708fa471d8c184d5873479067', '6', 'role_adv_amin', '2', 'admin', '2017-05-12 10:06:20', 'admin', '2017-05-12 10:47:30', null, '0');
INSERT INTO `sys_role` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '4', 'role_audit_admin', '2', 'admin', '2017-05-12 09:59:54', 'admin', '2017-05-12 10:46:14', null, '0');
INSERT INTO `sys_role` VALUES ('996ed5181e564505a3e11db16320dee0', '3', 'role_network_admin', '2', 'admin', '2017-05-12 09:58:23', 'admin', '2017-05-12 10:44:51', null, '0');
INSERT INTO `sys_role` VALUES ('a1fee893101045c39cf716999613cd00', '2', 'role_operations_admin', '2', 'admin', '2017-05-12 09:53:10', 'admin', '2017-05-12 10:44:12', null, '0');
INSERT INTO `sys_role` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '6', 'role_adv_staff', '2', 'admin', '2017-05-12 10:06:50', 'admin', '2017-05-12 10:47:43', null, '0');
INSERT INTO `sys_role` VALUES ('a987aa10701d42fb90351831ce8b066d', '5', 'role_adv_manage_admin', '2', 'admin', '2017-05-12 10:02:21', 'admin', '2017-05-12 10:47:05', null, '0');
INSERT INTO `sys_role` VALUES ('ad63a342e56c456ab3d9f207c340698d', '3', 'role_network_staff', '2', 'admin', '2017-05-12 09:58:46', 'admin', '2017-05-12 10:45:03', null, '0');
INSERT INTO `sys_role` VALUES ('admin', '1', 'role_superadmin', '2', null, '2015-06-02 18:09:56', 'admin', '2017-05-12 10:43:01', null, '0');
INSERT INTO `sys_role` VALUES ('f3c7180ef92f47928947da305a42146c', '5', 'role_adv_manage_staff', '2', 'admin', '2017-05-12 10:03:03', 'admin', '2017-05-12 10:47:18', null, '0');


/*
角色与菜单之间的关系
*/

INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '1');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '100');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '101');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '102');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '103');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '104');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '105');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '106');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '107');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '108');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '109');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '110');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '111');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '112');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '113');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '115');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '116');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '118');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '119');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '120');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '2');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '27');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '28');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '29');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '30');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '31');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '32');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '34');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '36');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '38');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '39');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '43');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '44');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '45');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '51');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '52');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '53');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '58');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '60');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '61');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '62');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '63');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '64');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '65');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '66');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '67');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '68');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '69');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '70');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '71');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '76');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '77');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '79');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '80');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '81');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '82');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '84');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '87');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '88');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '89');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '90');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '91');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '92');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '93');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '96');
INSERT INTO `sys_role_menu` VALUES ('12a47d9dd6e345bfa7bcc4e3fd369d00', '99');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '1');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '2');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '27');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '28');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '29');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '30');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '31');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '32');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '43');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '48');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '66');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '85');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '86');
INSERT INTO `sys_role_menu` VALUES ('8e9272bce41245b8a26b687fdf389012', '94');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '1');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '13');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '2');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '20');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '21');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '22');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '27');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '28');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '29');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '30');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '31');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '32');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '43');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '53');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '60');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '64');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '66');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '67');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '68');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '76');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '77');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '79');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '80');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '87');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '92');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '93');
INSERT INTO `sys_role_menu` VALUES ('96229d2708fa471d8c184d5873479067', '99');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '1');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '105');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '106');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '111');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '13');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '2');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '20');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '21');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '22');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '27');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '28');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '29');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '30');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '31');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '32');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '43');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '48');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '58');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '66');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '85');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '86');
INSERT INTO `sys_role_menu` VALUES ('987c5f8a792643e98cfd379a2e447d2d', '94');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '1');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '13');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '2');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '20');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '21');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '22');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '27');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '28');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '29');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '30');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '31');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '32');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '42');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '46');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '47');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '50');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '56');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '57');
INSERT INTO `sys_role_menu` VALUES ('996ed5181e564505a3e11db16320dee0', '95');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '1');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '104');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '105');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '106');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '107');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '108');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '109');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '110');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '111');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '112');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '113');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '115');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '116');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '118');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '120');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '13');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '2');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '20');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '21');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '22');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '27');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '28');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '29');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '30');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '31');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '32');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '34');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '36');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '38');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '39');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '43');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '44');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '45');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '51');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '52');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '53');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '58');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '60');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '61');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '62');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '63');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '64');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '65');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '66');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '67');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '68');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '69');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '70');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '71');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '76');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '77');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '79');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '80');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '81');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '82');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '84');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '87');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '88');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '89');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '90');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '91');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '92');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '93');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '96');
INSERT INTO `sys_role_menu` VALUES ('a1fee893101045c39cf716999613cd00', '99');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '1');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '2');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '27');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '28');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '29');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '30');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '31');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '32');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '43');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '53');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '60');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '64');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '66');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '67');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '68');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '76');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '77');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '79');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '80');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '87');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '92');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '93');
INSERT INTO `sys_role_menu` VALUES ('a7a43a837bf74f398cd7e3834ca43fb5', '99');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '1');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '114');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '117');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '13');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '2');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '20');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '21');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '22');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '27');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '28');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '29');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '30');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '31');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '32');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '41');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '44');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '49');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '54');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '59');
INSERT INTO `sys_role_menu` VALUES ('a987aa10701d42fb90351831ce8b066d', '98');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '1');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '2');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '27');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '28');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '29');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '30');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '31');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '32');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '42');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '46');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '47');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '50');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '56');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '57');
INSERT INTO `sys_role_menu` VALUES ('ad63a342e56c456ab3d9f207c340698d', '95');
INSERT INTO `sys_role_menu` VALUES ('admin', '1');
INSERT INTO `sys_role_menu` VALUES ('admin', '10');
INSERT INTO `sys_role_menu` VALUES ('admin', '100');
INSERT INTO `sys_role_menu` VALUES ('admin', '101');
INSERT INTO `sys_role_menu` VALUES ('admin', '102');
INSERT INTO `sys_role_menu` VALUES ('admin', '103');
INSERT INTO `sys_role_menu` VALUES ('admin', '104');
INSERT INTO `sys_role_menu` VALUES ('admin', '105');
INSERT INTO `sys_role_menu` VALUES ('admin', '106');
INSERT INTO `sys_role_menu` VALUES ('admin', '107');
INSERT INTO `sys_role_menu` VALUES ('admin', '108');
INSERT INTO `sys_role_menu` VALUES ('admin', '109');
INSERT INTO `sys_role_menu` VALUES ('admin', '11');
INSERT INTO `sys_role_menu` VALUES ('admin', '110');
INSERT INTO `sys_role_menu` VALUES ('admin', '111');
INSERT INTO `sys_role_menu` VALUES ('admin', '112');
INSERT INTO `sys_role_menu` VALUES ('admin', '113');
INSERT INTO `sys_role_menu` VALUES ('admin', '114');
INSERT INTO `sys_role_menu` VALUES ('admin', '115');
INSERT INTO `sys_role_menu` VALUES ('admin', '116');
INSERT INTO `sys_role_menu` VALUES ('admin', '117');
INSERT INTO `sys_role_menu` VALUES ('admin', '118');
INSERT INTO `sys_role_menu` VALUES ('admin', '119');
INSERT INTO `sys_role_menu` VALUES ('admin', '12');
INSERT INTO `sys_role_menu` VALUES ('admin', '120');
INSERT INTO `sys_role_menu` VALUES ('admin', '126');
INSERT INTO `sys_role_menu` VALUES ('admin', '127');
INSERT INTO `sys_role_menu` VALUES ('admin', '128');
INSERT INTO `sys_role_menu` VALUES ('admin', '129');
INSERT INTO `sys_role_menu` VALUES ('admin', '13');
INSERT INTO `sys_role_menu` VALUES ('admin', '17');
INSERT INTO `sys_role_menu` VALUES ('admin', '18');
INSERT INTO `sys_role_menu` VALUES ('admin', '19');
INSERT INTO `sys_role_menu` VALUES ('admin', '2');
INSERT INTO `sys_role_menu` VALUES ('admin', '20');
INSERT INTO `sys_role_menu` VALUES ('admin', '21');
INSERT INTO `sys_role_menu` VALUES ('admin', '22');
INSERT INTO `sys_role_menu` VALUES ('admin', '27');
INSERT INTO `sys_role_menu` VALUES ('admin', '28');
INSERT INTO `sys_role_menu` VALUES ('admin', '29');
INSERT INTO `sys_role_menu` VALUES ('admin', '3');
INSERT INTO `sys_role_menu` VALUES ('admin', '30');
INSERT INTO `sys_role_menu` VALUES ('admin', '31');
INSERT INTO `sys_role_menu` VALUES ('admin', '32');
INSERT INTO `sys_role_menu` VALUES ('admin', '33');
INSERT INTO `sys_role_menu` VALUES ('admin', '34');
INSERT INTO `sys_role_menu` VALUES ('admin', '36');
INSERT INTO `sys_role_menu` VALUES ('admin', '37');
INSERT INTO `sys_role_menu` VALUES ('admin', '38');
INSERT INTO `sys_role_menu` VALUES ('admin', '39');
INSERT INTO `sys_role_menu` VALUES ('admin', '4');
INSERT INTO `sys_role_menu` VALUES ('admin', '40');
INSERT INTO `sys_role_menu` VALUES ('admin', '41');
INSERT INTO `sys_role_menu` VALUES ('admin', '42');
INSERT INTO `sys_role_menu` VALUES ('admin', '43');
INSERT INTO `sys_role_menu` VALUES ('admin', '44');
INSERT INTO `sys_role_menu` VALUES ('admin', '45');
INSERT INTO `sys_role_menu` VALUES ('admin', '46');
INSERT INTO `sys_role_menu` VALUES ('admin', '47');
INSERT INTO `sys_role_menu` VALUES ('admin', '48');
INSERT INTO `sys_role_menu` VALUES ('admin', '49');
INSERT INTO `sys_role_menu` VALUES ('admin', '5');
INSERT INTO `sys_role_menu` VALUES ('admin', '50');
INSERT INTO `sys_role_menu` VALUES ('admin', '51');
INSERT INTO `sys_role_menu` VALUES ('admin', '52');
INSERT INTO `sys_role_menu` VALUES ('admin', '53');
INSERT INTO `sys_role_menu` VALUES ('admin', '54');
INSERT INTO `sys_role_menu` VALUES ('admin', '56');
INSERT INTO `sys_role_menu` VALUES ('admin', '57');
INSERT INTO `sys_role_menu` VALUES ('admin', '58');
INSERT INTO `sys_role_menu` VALUES ('admin', '59');
INSERT INTO `sys_role_menu` VALUES ('admin', '6');
INSERT INTO `sys_role_menu` VALUES ('admin', '60');
INSERT INTO `sys_role_menu` VALUES ('admin', '61');
INSERT INTO `sys_role_menu` VALUES ('admin', '62');
INSERT INTO `sys_role_menu` VALUES ('admin', '63');
INSERT INTO `sys_role_menu` VALUES ('admin', '64');
INSERT INTO `sys_role_menu` VALUES ('admin', '65');
INSERT INTO `sys_role_menu` VALUES ('admin', '66');
INSERT INTO `sys_role_menu` VALUES ('admin', '67');
INSERT INTO `sys_role_menu` VALUES ('admin', '68');
INSERT INTO `sys_role_menu` VALUES ('admin', '69');
INSERT INTO `sys_role_menu` VALUES ('admin', '7');
INSERT INTO `sys_role_menu` VALUES ('admin', '70');
INSERT INTO `sys_role_menu` VALUES ('admin', '71');
INSERT INTO `sys_role_menu` VALUES ('admin', '76');
INSERT INTO `sys_role_menu` VALUES ('admin', '77');
INSERT INTO `sys_role_menu` VALUES ('admin', '79');
INSERT INTO `sys_role_menu` VALUES ('admin', '8');
INSERT INTO `sys_role_menu` VALUES ('admin', '80');
INSERT INTO `sys_role_menu` VALUES ('admin', '81');
INSERT INTO `sys_role_menu` VALUES ('admin', '82');
INSERT INTO `sys_role_menu` VALUES ('admin', '84');
INSERT INTO `sys_role_menu` VALUES ('admin', '85');
INSERT INTO `sys_role_menu` VALUES ('admin', '86');
INSERT INTO `sys_role_menu` VALUES ('admin', '87');
INSERT INTO `sys_role_menu` VALUES ('admin', '88');
INSERT INTO `sys_role_menu` VALUES ('admin', '89');
INSERT INTO `sys_role_menu` VALUES ('admin', '9');
INSERT INTO `sys_role_menu` VALUES ('admin', '90');
INSERT INTO `sys_role_menu` VALUES ('admin', '91');
INSERT INTO `sys_role_menu` VALUES ('admin', '92');
INSERT INTO `sys_role_menu` VALUES ('admin', '93');
INSERT INTO `sys_role_menu` VALUES ('admin', '94');
INSERT INTO `sys_role_menu` VALUES ('admin', '95');
INSERT INTO `sys_role_menu` VALUES ('admin', '96');
INSERT INTO `sys_role_menu` VALUES ('admin', '97');
INSERT INTO `sys_role_menu` VALUES ('admin', '98');
INSERT INTO `sys_role_menu` VALUES ('admin', '99');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '1');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '114');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '117');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '2');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '27');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '28');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '29');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '30');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '31');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '32');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '41');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '44');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '49');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '54');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '59');
INSERT INTO `sys_role_menu` VALUES ('f3c7180ef92f47928947da305a42146c', '98');

 
/*
	角色名称在字典管理里面进行国际化
*/
INSERT INTO `sys_dict` VALUES ('0d8ae1ce70134cca83359e362641c009', 'Superadmin', 'Superadmin', 'role_superadmin', 'role_superadmin', '10', 'admin', '2017-05-12 10:49:41', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('da00249044864fe182fb8d3d8b1cc8ec', '超级管理员', '超级管理员', 'role_superadmin', '角色--超级管理员', '10', 'admin', '2017-05-12 10:49:16', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('51c642a3a5b447a0b8ea8c143a187072', 'Operations Staff', 'Operations Staff', 'role_operations_staff', 'role -- Operations Staff', '10', 'admin', '2017-05-12 10:50:35', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('bb3fb25e17c94b678400dc5c216b81b2', '运营管理职员', '运营管理职员', 'role_operations_staff', '角色--运营管理职员', '10', 'admin', '2017-05-12 10:50:03', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('dddf8eae22ac43c9be07ec9528ddd2da', 'Operations admin', 'Operations admin', 'role_operations_admin', 'role -- Operations admin', '10', 'admin', '2017-05-12 10:51:06', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('eda2ac43c9be07ec9528ddd2da', '运营管理管理员', '运营管理管理员', 'role_operations_admin', '角色--运营管理职员', '10', 'admin', '2017-05-12 10:51:06', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('27a3b6bd5f5645de93f4e1ca675016c8', 'Network admin', 'Network admin', 'role_network_admin', 'role --Network admin', '10', 'admin', '2017-05-12 10:51:58', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('a2c135f79b404e5fb36ef48317548496', '网络管理员', '网络管理员', 'role_network_admin', '角色--网络管理员', '10', 'admin', '2017-05-12 10:51:37', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('e0e2566ff80a4d84a8afb46d961bf592', 'Network Staff', 'Network Staff', 'role_network_staff', 'role--Network Staff', '10', 'admin', '2017-05-12 10:52:39', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('2500a50db71e4db59bc85f3c60cdacdf', '网络管理职员', '网络管理职员', 'role_network_staff', '角色--网络管理职员', '10', 'admin', '2017-05-12 10:52:16', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('d1005799d6d846aeb7be9def37a4c74c', 'Aduit Staff', 'Aduit Staff', 'role_audit_staff', 'role -- Aduit Staff', '10', 'admin', '2017-05-12 10:53:01', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('e84164725e9844c592e49eac0a095ddf', '内容审核职员', '内容审核职员', 'role_audit_staff', '角色--内容审核职员', '10', 'admin', '2017-05-12 10:53:17', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('00c03dd7e8e8469fa9e7d35eac2a6c8c', 'Audit admin', 'Audit admin', 'role_audit_admin', 'role --Audit admin', '10', 'admin', '2017-05-12 10:53:51', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('d4fa99743f224ea6b1918ed9deb6f868', '内容审核管理员', '内容审核管理员', 'role_audit_admin', '角色--内容审核管理员', '10', 'admin', '2017-05-12 10:53:34', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('3bb2efea934141e59a74ed71c4fc83fb', '广告商管理员', '广告商管理员', 'role_adv_manage_admin', '角色--广告商管理员', '10', 'admin', '2017-05-12 10:54:22', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('a6cff8a8beff4ec39f3a06a10ded9ae9', 'Advertisers administrator', 'Advertisers administrator', 'role_adv_manage_admin', 'role --Advertisers administrator', '10', 'admin', '2017-05-12 10:55:18', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('1b58bfc08ee54b7499842cb6b8ec653a', '广告商管理职员', '广告商管理职员', 'role_adv_manage_staff', '角色--广告商管理职员', '10', 'admin', '2017-05-12 10:55:37', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('3049f07318824cc5b00b463f37776e30', 'Advertisers management staff', 'Advertisers management staff', 'role_adv_manage_staff', 'role--Advertisers management staff', '10', 'admin', '2017-05-12 10:56:03', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('be5d127cbcc441b081def11d30960ead', '广告承包商管理员', '广告承包商管理员', 'role_adv_amin', '角色--广告承包商管理员', '10', 'admin', '2017-05-12 10:56:56', null, null, null, '0', '0');

INSERT INTO `sys_dict` VALUES ('1a5dac28db1e4278ad819e5991155707', 'Advertising contractors administrator', 'Advertising contractors administrator', 'role_adv_amin', 'role --Advertising contractors administrator', '10', 'admin', '2017-05-12 10:56:39', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('4849e1e467244de4b1c7590194966843', 'Advertising the contractor staff', 'Advertising the contractor staff', 'role_adv_staff', 'role --Advertising the contractor staff', '10', 'admin', '2017-05-12 10:57:23', null, null, null, '0', '1');

INSERT INTO `sys_dict` VALUES ('a294ab668729453384ece4d53d00edb8', '广告承包商职员', '广告承包商职员', 'role_adv_staff', '角色-广告承包商职员', '10', 'admin', '2017-05-12 10:57:44', null, null, null, '0', '0');



/* 
	默认用户
*/ 
INSERT INTO `sys_user` VALUES (null, null, null, '4', '4', '0780594a270a448180316537cd485678', 'audit_admin', 'e0ecb6a79152ffd28bd414cda2f4fc0e4b18fb4738831410fd59db8d', 'audit administrator', '', '', '', '2', '127.0.0.1', '2017-05-12 10:14:51', 'admin', '2017-05-12 10:12:01', null, null, '', '0', null, null, '0');
INSERT INTO `sys_user` VALUES (null, null, null, '5', '5', '42694d2404ad45c5a6013d5d5fcbe56d', 'adv_admin', '66b3da821c2a049692515ccfac8c342f0a8986a8c9ac70658895fcd4', 'advertisers administrator', '', '', '', '2', '127.0.0.1', '2017-05-12 10:13:29', 'admin', '2017-05-12 10:13:19', null, null, '', '0', null, null, '0');
INSERT INTO `sys_user` VALUES (null, null, null, '3', '3', '82f79c3eeeb74147bf9fa5ee11a26ab0', 'network_admin', '40fba2bb0a1626e1f536201d082569ec32c4a72ff1971d96a35a673a', 'network administrator', '', '', '', '2', '127.0.0.1', '2017-05-12 10:14:24', 'admin', '2017-05-12 10:09:50', 'admin', '2017-05-12 10:12:13', '', '0', null, null, '0');
INSERT INTO `sys_user` VALUES (null, null, null, '2', '2', 'a4ff43d280864914b76da8d1a680d196', 'operation_admin', '4568737413e5e61c3b51236a5d0b73f6662cf7f8fdffbc75401e2c0e', 'operations administrator', '', '', '', '2', '127.0.0.1', '2017-05-12 10:16:07', 'admin', '2017-05-12 10:10:59', 'admin', '2017-05-12 10:12:07', '', '0', null, null, '0');
INSERT INTO `sys_user` VALUES (null, null, null, '1', '1', 'admin', 'admin', '5bbeeac36c116166392cac02f3b79d53a39a0c61fc83df0b02657d72', '系统超级管理员', null, null, null, null, '127.0.0.1', '2017-05-12 11:10:53', 'admin', '2016-05-30 00:00:00', null, null, null, '0', null, null, '0');

/* 
	默认用户与角色之间的关系
*/
INSERT INTO `sys_user_role` VALUES ('0780594a270a448180316537cd485678', '987c5f8a792643e98cfd379a2e447d2d');
INSERT INTO `sys_user_role` VALUES ('42694d2404ad45c5a6013d5d5fcbe56d', 'a987aa10701d42fb90351831ce8b066d');
INSERT INTO `sys_user_role` VALUES ('82f79c3eeeb74147bf9fa5ee11a26ab0', '996ed5181e564505a3e11db16320dee0');
INSERT INTO `sys_user_role` VALUES ('a4ff43d280864914b76da8d1a680d196', 'a1fee893101045c39cf716999613cd00');
INSERT INTO `sys_user_role` VALUES ('admin', 'admin');


 