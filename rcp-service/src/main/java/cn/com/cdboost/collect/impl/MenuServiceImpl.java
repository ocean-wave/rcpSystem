package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MenuMapper;
import cn.com.cdboost.collect.model.Menu;
import cn.com.cdboost.collect.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 菜单服务接口实现类
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> listAllMenus() {
		Menu param = new Menu();
		param.setIsEnabled(1);
		return menuMapper.select(param);
	}

	@Override
	public List<Menu> queryMenusByMenuIds(List<Long> menuIds) {
		Condition condition = new Condition(Menu.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("menuId",menuIds);
		return menuMapper.selectByCondition(condition);
	}
}
