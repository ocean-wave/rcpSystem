package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.MenuAction;

import java.util.List;

/**
 * 菜单动作服务接口
 */
public interface MenuActionService extends BaseService<MenuAction>{
	List<MenuAction> getAllMenuAction();
}
