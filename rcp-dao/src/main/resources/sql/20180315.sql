INSERT INTO `em_s_dict` (`id`, `dict_id`, `dict_code`, `dict_name`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('12', '12', '12', '异常类型', '水电气表异常类型', '0', '2018-03-15 11:51:13', '1');
INSERT INTO `em_s_dictitem` (`id`, `dict_item_id`, `dict_code`, `dict_item_value`, `dict_item_name`, `p_dict_item_id`, `tree_level`, `is_lowest`, `sort_no`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('22', '22', '12', '充值异常', '充值异常', '0', '1', '0', '1', '', '0', '2018-03-15 11:48:49', '1');
INSERT INTO `em_s_dictitem` (`id`, `dict_item_id`, `dict_code`, `dict_item_value`, `dict_item_name`, `p_dict_item_id`, `tree_level`, `is_lowest`, `sort_no`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('23', '23', '12', '电池异常', '电池异常', '0', '1', '0', '1', '', '0', '2018-03-15 11:49:12', '1');
INSERT INTO `em_s_dictitem` (`id`, `dict_item_id`, `dict_code`, `dict_item_value`, `dict_item_name`, `p_dict_item_id`, `tree_level`, `is_lowest`, `sort_no`, `remark`, `create_user_id`, `create_time`, `is_enabled`) VALUES ('24', '24', '12', '电量异常', '电量异常', '0', '1', '0', '1', '', '0', '2018-03-15 11:49:36', '1');


-- 将系统管理放到统计查询菜单下面，执行后记得修改一下sort_no字段值
INSERT INTO `em_b_menu` (`id`, `menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('20', '100050', '统计查询', '0', 'accountAnalyse', '/Main/StatisticsQuery', '1', '5', '2018-03-21 17:13:08', '0', '1', NULL);
INSERT INTO `em_b_menu` (`id`, `menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('21', '100051', '综合查询', '100050', 'comprehensive', '/Main/TotalSearch', '1', '1', '2018-03-21 17:14:19', '0', '1', NULL);
INSERT INTO `em_b_menu` (`id`, `menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('22', '100052', '用能排行', '100050', 'ranking', '/Main/Ranking', '1', '2', '2018-03-21 17:15:09', '0', '1', NULL);
INSERT INTO `em_b_menu` (`id`, `menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('23', '100053', '异常事件', '100050', 'ExceptionAccount', '/Main/ExceptionAccount', '1', '3', '2018-03-21 17:16:05', '0', '1', NULL);
INSERT INTO `em_b_menu` (`id`, `menu_id`, `menu_title`, `p_menu_id`, `menu_icon`, `menu_url`, `is_last`, `sort_no`, `create_time`, `create_user_id`, `is_enabled`, `description`) VALUES ('24', '100054', '重点用户', '100050', 'SpecialAttention', '/Main/SpecialAttention', '1', '4', '2018-03-21 17:16:45', '0', '1', NULL);

INSERT INTO `em_b_action` (`id`, `action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('27', '27', '异常等级1', '0', '2018-03-22 10:15:19', '0', '1');
INSERT INTO `em_b_action` (`id`, `action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('28', '28', '异常等级2', '0', '2018-03-22 10:15:36', '0', '1');
INSERT INTO `em_b_action` (`id`, `action_id`, `action_name`, `action_flag`, `create_time`, `create_user_id`, `is_enabled`) VALUES ('29', '29', '异常处理', '0', '2018-03-22 10:15:52', '0', '1');

INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`   , `create_time`, `create_user_id`) VALUES ('58', '100051', '4', '浏览', '2018-03-22 10:16:55', '0');
INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('59', '100052', '4', '浏览', '2018-03-22 10:17:22', '0');
INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('60', '100053', '27', '异常等级1', '2018-03-22 10:17:48', '0');
INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('61', '100053', '28', '异常等级2', '2018-03-22 10:18:57', '0');
INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('62', '100053', '29', '异常处理', '2018-03-22 10:19:15', '0');
INSERT INTO `em_b_menuaction` (`id`, `menu_id`, `action_id`, `action_name`, `create_time`, `create_user_id`) VALUES ('63', '100054', '4', '浏览', '2018-03-22 10:19:50', '0');

INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100051', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100052', '4');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100053', '27');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100053', '28');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100053', '29');
INSERT INTO `em_b_roleright` (`role_id`, `menu_id`, `action_id`) VALUES ('1', '100054', '4');

INSERT INTO `em_d_fee_pricesols` (`id`, `price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('3', '201803231', '普通用水', '3', '1', '2018-03-23 11:10:35', '1', NULL, '1', '普通用水方案', '2018-03-01');
INSERT INTO `em_d_fee_pricesols` (`id`, `price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('4', '201803232', '商业用水', '4', '1', '2018-03-23 11:11:39', '1', NULL, '1', '商业用水方案', '2018-03-01');
INSERT INTO `em_d_fee_pricesols` (`id`, `price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('5', '201803233', '普通用气', '5', '1', '2018-03-23 11:12:32', '1', NULL, '1', '普通用气方案', '2018-03-01');
INSERT INTO `em_d_fee_pricesols` (`id`, `price_sols_code`, `price_sols_name`, `dict_item_value`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_enabled`, `sols_remark`, `effective_date`) VALUES ('6', '201803234', '商业用气', '6', '1', '2018-03-23 11:13:06', '1', NULL, '1', '商业用气方案', '2018-03-01');
