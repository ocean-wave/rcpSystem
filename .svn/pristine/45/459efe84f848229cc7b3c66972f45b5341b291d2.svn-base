-- 组织
INSERT INTO `em_b_org` (`org_no`, `org_name`, `p_org_no`, `first_pinyin`, `full_pinyin`, `level_code`, `level`, `sort_no`, `is_enabled`, `create_time`, `create_user_id`) VALUES ('1000', '成都博高', '0', 'cdbg', 'chengdubogao', '/1000', '1', '0', '1', '2017-08-10 15:11:34', '0');

-- 用户
INSERT INTO `em_b_user` (`user_name`, `login_name`, `user_password`, `user_mobile`, `user_mail`, `is_enabled`, `create_time`, `create_user_id`, `remark`, `org_no`, `is_system`) VALUES ('系统管理员', 'admin', 'e60d4b85258759b4e0d3142fc5c6662b', '13612345678', '123@163.com', '0', '2017-08-16 16:48:51', '0', '', '1000', '1');

-- 角色
INSERT INTO `em_b_role` (`role_name`, `description`, `create_time`, `create_user_id`, `is_enabled`, `is_system`) VALUES ('系统管理员', '', '2017-06-06 08:54:27', '0', '1', '0');

-- 菜单
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100010', '数据采集', '0', 'collect', '/Main/Collection', '0', '1', '2017-05-16 16:23:07', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100011', '实时数据', '100010', 'realData', '/Main/Measure', '1', '5', '2017-05-16 16:25:46', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100012', '历史数据', '100010', 'historicalData', '/Main/HistoricalData', '1', '6', '2017-05-16 16:26:41', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100013', '远程通断', '100020', 'onOff', '/Main/RemoteControl', '1', '1', '2017-05-16 16:27:22', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100014', '采集分析', '100010', 'dataAnalyze', '/Main/freezeData', '1', '7', '2017-09-08 18:07:54', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100015', '补抄管理', '100010', 'collect', '/Main/Test', '1', '7', '2017-09-08 18:07:54', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100020', '费控管理', '0', 'costControl', '/Main/Fee', '0', '2', '2017-05-16 16:23:50', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100021', '缴费记录', '100020', 'payment', '/Main/Payment', '1', '2', '2017-05-19 11:05:22', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100022', '用户开户', '100020', 'account', '/Main/Account', '1', '2', '2017-07-04 16:24:57', '0', '1', '100021');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100023', '电价方案', '100020', 'price', '/Main/Price', '1', '3', '2017-05-19 11:05:22', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100024', '充值缴费', '100020', 'pay', '/Main/ChargePrice', '1', '0', '2017-08-01 16:49:43', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100030', '档案管理', '0', 'file', '/Main/Record', '0', '3', '2017-05-16 16:22:10', '0', '1', '123');
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100031', '用户档案', '100030', 'user', '/Main/Staff', '1', '11', '2017-07-04 16:24:57', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100032', '设备档案', '100030', 'equipment', '/Main/Equipment', '1', '12', '2017-07-04 16:24:57', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100033', '换表记录', '100030', 'changeMeter', '/Main/ChangeMeter', '1', '18', '2017-10-31 10:31:58', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100040', '系统管理', '0', 'systemSet', '/Main/System', '0', '14', '2017-05-19 11:05:57', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100041', '角色管理', '100040', 'role', '/Main/Role', '1', '15', '2017-05-19 11:05:57', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100042', '人员管理', '100040', 'renyuan', '/Main/Users', '1', '16', '2017-05-19 11:05:57', '0', '1', NULL);
INSERT INTO `em_b_menu` (`menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('100043', '系统参数', '100040', 'set', '/Main/Configuration', '1', '17', '2017-05-19 11:05:57', '0', '1', NULL);

-- 动作
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('1', '添加', '0', '2017-06-13 10:43:38', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('2', '修改', '0', '2017-06-13 10:43:38', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('3', '删除', '0', '2017-06-13 10:43:38', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('4', '浏览', '0', '2017-06-13 10:43:38', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('5', '实时召测', '0', '2017-06-16 17:38:30', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('6', '下载', '0', '2017-06-19 17:26:07', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('7', '发送短信', '0', '2017-06-19 17:27:07', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('8', '下发档案', '0', '2017-06-19 17:27:50', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('9', '上传文件', '0', '2017-06-19 17:35:31', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('10', '充值', '0', '2017-08-01 17:06:18', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('11', '远程充值', '0', '2017-08-01 17:07:08', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('12', '本地充值', '0', '2017-08-01 17:07:54', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('13', '通断', '0', '2017-08-04 13:58:09', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('14', '开户', '0', '2017-08-04 14:34:52', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('15', '补卡', '0', '2017-08-04 14:35:07', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('16', '集中器重启', '0', '2017-08-04 14:41:52', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('17', '集中器初始化', '0', '2017-08-04 14:42:12', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('18', '撤销记录', '0', '2017-08-23 16:06:10', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('19', '重新开户', '0', '2017-08-29 16:47:25', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('20', '购电卡工具', '0', '2017-08-30 11:42:53', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('21', '注销用户', '0', '2017-09-01 15:12:29', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('22', '换表', '0', '2017-10-16 15:26:12', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('23', '采集器重启', '0', '2017-11-21 09:40:33', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('24', '采集器初始化', '0', '2017-11-21 09:40:53', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('25', '采集器读时钟', '0', '2017-11-21 10:00:16', '0', '1');
INSERT INTO `em_b_action` (`action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('26', '采集器设时钟', '0', '2017-11-21 10:00:36', '0', '1');

-- 菜单动作关联数据
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100011', '4', '浏览', '2017-07-04 17:25:19', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100011', '5', '实时召测', '2017-07-04 17:25:19', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100011', '6', '下载', '2017-07-04 17:25:19', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100011', '7', '发送短信', '2017-07-04 17:25:19', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100012', '4', '浏览', '2017-07-04 17:26:09', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100012', '6', '下载', '2017-07-04 17:26:09', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '1', '添加', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '2', '修改', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '3', '删除', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '4', '浏览', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '8', '下发档案', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '9', '上传文件', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '1', '添加', '2017-07-04 17:27:44', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '2', '修改', '2017-07-04 17:27:44', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '3', '删除', '2017-07-04 17:27:44', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '4', '浏览', '2017-07-04 17:27:44', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100041', '1', '添加', '2017-07-04 17:30:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100041', '2', '修改', '2017-07-04 17:30:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100041', '3', '删除', '2017-07-04 17:30:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100041', '4', '浏览', '2017-07-04 17:30:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100042', '1', '添加', '2017-07-04 17:31:03', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100042', '2', '修改', '2017-07-04 17:31:03', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100042', '3', '删除', '2017-07-04 17:31:03', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100042', '4', '浏览', '2017-07-04 17:31:03', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100043', '2', '修改', '2017-07-04 17:31:16', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100021', '4', '浏览', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100023', '4', '浏览', '2017-07-04 17:27:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100013', '13', '通断', '2017-08-09 11:25:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100043', '4', '浏览', '2017-07-04 17:31:03', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '6', '下载', '2017-08-01 09:10:09', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '14', '开户', '2017-08-09 11:15:38', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '16', '集中器重启', '2017-08-09 11:17:13', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '17', '集中器初始化', '2017-08-09 11:17:08', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100022', '14', '开户', '2017-08-09 11:21:46', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100022', '15', '补卡', '2017-08-09 11:20:56', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100021', '6', '下载', '2017-08-23 16:09:07', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100021', '15', '补卡', '2017-08-23 16:09:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100021', '18', '撤销记录', '2017-09-01 15:45:05', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100024', '11', '远程充值', '2017-08-28 18:32:06', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100024', '12', '购电卡充值', '2017-08-30 17:44:08', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100022', '20', '购电卡工具', '2017-08-30 11:44:07', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100022', '19', '重新开户', '2017-08-30 11:45:08', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100024', '5', '实时召测', '2017-08-31 00:11:49', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100031', '21', '注销用户', '2017-09-01 15:14:22', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100014', '4', '浏览', '2017-09-08 18:10:20', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100015', '1', '添加', '2017-09-22 10:02:23', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100015', '2', '修改', '2017-09-22 10:03:04', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100015', '3', '删除', '2017-09-22 10:03:15', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100015', '4', '浏览', '2017-09-22 10:03:28', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100033', '22', '换表', '2017-10-30 12:09:00', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100033', '4', '浏览', '2017-10-31 10:20:00', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '23', '采集器重启', '2017-08-09 11:17:13', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '24', '采集器初始化', '2017-08-09 11:17:13', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '25', '采集器读时钟', '2017-11-21 09:58:06', '0');
INSERT INTO `em_b_menuaction` (`menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('100032', '26', '采集器设时钟', '2017-11-21 09:59:37', '0');

-- 角色菜单动作初始数据
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100011', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100011', '5');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100011', '6');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100011', '7');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100012', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100012', '6');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100013', '13');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100014', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100015', '1');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100015', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100015', '3');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100015', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100021', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100021', '6');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100021', '15');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100021', '18');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100022', '14');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100022', '15');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100022', '19');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100022', '20');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100023', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100024', '5');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100024', '11');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100024', '12');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100025', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100025', '22');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '1');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '3');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '6');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '8');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '9');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '14');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100031', '21');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '1');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '3');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '16');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '17');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '23');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '24');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '25');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100032', '26');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100033', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100033', '22');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100041', '1');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100041', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100041', '3');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100041', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100042', '1');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100042', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100042', '3');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100042', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100043', '2');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100043', '4');


-- 字典表
INSERT INTO `em_s_dict` (`dict_id`, `dict_code`, `dict_name`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('1', '1', '用电类型', '用电类型', '0', '2017-07-06 17:30:40', '1');

-- 字典项表
INSERT INTO `em_s_dictitem` (`dict_item_id`, `dict_code`, `dict_item_value`, `dict_item_name`, `p_dict_item_id`, `tree_level`, `is_lowest`, `sort_no`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('1', '1', '1', '普通用电', '0', '1', '0', '1', '', '0', '2017-07-10 17:09:41', '1');
INSERT INTO `em_s_dictitem` (`dict_item_id`, `dict_code`, `dict_item_value`, `dict_item_name`, `p_dict_item_id`, `tree_level`, `is_lowest`, `sort_no`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('2', '1', '2', '商业用电', '0', '1', '0', '1', '', '0', '2017-07-10 17:10:42', '1');

-- 电价方案表
INSERT INTO `em_d_fee_pricesols` (`price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('201708011', '商业用电', '2', '0', '2017-08-01 13:57:18', '0', '2017-08-10 12:04:06', '1', '商业用电方案', '2017-08-01');
INSERT INTO `em_d_fee_pricesols` (`price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('201708012', '普通用电', '1', '0', '2017-08-01 14:06:52', '0', '2017-08-14 17:14:25', '1', '普通用电方案', '2017-08-01');

-- 费率参数项表
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050101', '费率01电价(尖)', '1', '元', '04', '1', '1', '1');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050102', '费率02电价(峰) ', '2', '元', '04', '1', '2', '1');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050103', '费率03电价(平) ', '3', '元', '04', '1', '3', '1');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050104', '费率04电价(谷) ', '4', '元', '04', '1', '4', '1');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050201', '费率01电价(尖) ', '5', '元', '04', '1', '1', '2');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050202', '费率02电价(峰) ', '6', '元', '04', '1', '2', '2');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050203', '费率03电价(平) ', '7', '元', '04', '1', '3', '2');
INSERT INTO `em_d_fee_priceitem` (`item_code`, `item_name`, `sort_no`, `unit`, `device_type`, `is_enabled`, `group_flag`, `item_flag`) VALUES ('04050204', '费率04电价(谷) ', '8', '元', '04', '1', '4', '2');

-- 电价方案详细信息
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050101', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050201', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050102', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050202', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050103', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050203', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050104', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708011', '04050204', '1.42');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050101', '0.6263');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050201', '0.6262');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050102', '0.6263');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050202', '0.6262');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050103', '0.6263');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050203', '0.6263');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050104', '0.6263');
INSERT INTO `em_d_fee_pricesolsdtl` (`price_sols_code`, `item_code`, `data_value`) VALUES ('201708012', '04050204', '0.6263');

-- 系统配置表
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('8', 'serviceIp', '10.10.1.250', '', '1');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('9', 'servicePort', '7000', '', '1');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('10', 'apn', 'CMNET', '', '1');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('13', 'sysName', '社区能源管理平台', '', '2');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('14', 'balanceTime', '28', '', '2');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('15', 'payAddr', '四川省成都市武侯区武兴三路18号', '', '3');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('16', 'companyName', '龙湖时代天街', '', '3');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('17', 'version', '1.0', NULL, '2');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('18', 'CustomerID', '1', NULL, '4');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('19', 'AppEUI', 'aabbee', 'loraAppEUI', '5');
INSERT INTO `em_s_sysconfig` (`config_id`, `config_name`, `config_value`, `remark`, `config_type`) VALUES ('20', 'AppKey', '000000', 'loraAppKey', '5');

-- dlt645表
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00010000', '00010000', '4', '4', '2', '30', '正向有功总');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00010100', '00010100', '4', '4', '2', '30', '正向有功尖');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00010200', '00010200', '4', '4', '2', '30', '正向有功峰');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00010300', '00010300', '4', '4', '2', '30', '正向有功平');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00010400', '00010400', '4', '4', '2', '30', '正向有功谷');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('0001ff00', '00010000,00010100,00010200,00010300,00010400', '20', '4', '2', '30', '正向组合有功');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('00900200', '00900200', '4', '4', '2', '30', '剩余金额');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('03330201', '03330201', '2', '2', '0', '30', '上一次累计购电次数');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('03330601', '03330601', '4', '4', '2', '30', '上一次累计购电金额');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('04000101', '04000101', '4', '4', '0', '30', '年月日星期');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('04000102', '04000102', '3', '3', '0', '30', '时分秒');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('05060001', '05060001', '5', '5', '0', '30', '上一次日冻结时间');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('05060101', '05060101,05060102,05060103,05060104,05060105', '20', '4', '2', '30', '上一次日冻结电能数据');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('070000FF', '070000FF', '12', '12', '0', '30', '随机数2(4)ESAM序列号(8)');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('9010', '9010', '4', '4', '2', '1', '正向有功总');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('9011', '9011', '4', '4', '2', '1', '正向有功尖');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('9012', '9012', '4', '4', '2', '1', '正向有功峰');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('9013', '9013', '4', '4', '2', '1', '正向有功平');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('9014', '9014', '4', '4', '2', '1', '正向有功谷');
INSERT INTO `l_d_dlt645` (`di`, `di_split`, `data_len`, `data_item_len`, `data_item_dec_pos`, `gy`, `di_desc`) VALUES ('901f', '9010,9011,9012,9013,9014', '20', '4', '2', '1', '正向组合有功');

