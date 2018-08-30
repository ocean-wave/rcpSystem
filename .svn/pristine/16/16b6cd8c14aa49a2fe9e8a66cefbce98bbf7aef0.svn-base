package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.response.MenuActionInfo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.MenuAction;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.service.MenuActionService;
import cn.com.cdboost.collect.service.RoleService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/menuAction")
public class MenuActionController {

	@Autowired
	private MenuActionService menuActionService;
	@Autowired
	private RoleService roleService;

	/**
	 * 获取所有的菜单动作数据
	 * @return
	 */
	@SystemControllerLog(description = "获取所有的菜单动作数据")
	@RequestMapping("/queryAllMenuAction")
	@ResponseBody
	public String queryAllMenuAction() {
		Result<List<MenuActionInfo>> result = new Result<>();
		List<MenuAction> menuActions = menuActionService.getAllMenuAction();
		List<MenuActionInfo> menuActionInfos = Lists.newArrayList();
		if (CollectionUtils.isEmpty(menuActions)) {
			result.setData(menuActionInfos);
			return JSON.toJSONString(result);
		}

		// 返回前端需要的字段
		for (MenuAction menuAction : menuActions) {
			MenuActionInfo info = new MenuActionInfo();
			BeanUtils.copyProperties(menuAction,info);
			menuActionInfos.add(info);
		}
		result.setData(menuActionInfos);

		return JSON.toJSONString(result);
	}

	/**
	 * 根据菜单id，查询对应角色下拥有的动作信息
	 * @param session
	 * @param menuId
	 * @return
	 */
	@SystemControllerLog(description = "根据菜单id，查询对应角色下拥有的动作信息")
	@RequestMapping("/queryRolePermissions")
	@ResponseBody
	public String queryRolePermissions(HttpSession session, @RequestParam Long menuId) {
		Result<List<RoleRight>> result = new Result<>();
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

		List<RoleRight> rolePermissions = roleService.queryRolePermissions(menuId, user.getRoleId());
		result.setData(rolePermissions);

		return JSON.toJSONString(result);
	}
}
