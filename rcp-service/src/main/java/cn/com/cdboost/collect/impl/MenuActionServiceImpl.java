package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MenuActionMapper;
import cn.com.cdboost.collect.model.MenuAction;
import cn.com.cdboost.collect.service.MenuActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单动作服务接口实现类
 */
@Service("menuActionService")
public class MenuActionServiceImpl extends BaseServiceImpl<MenuAction> implements MenuActionService {
	@Autowired
	private MenuActionMapper menuActionMapper;

	@Override
	public List<MenuAction> getAllMenuAction() {
		return menuActionMapper.selectAll();
	}
}
