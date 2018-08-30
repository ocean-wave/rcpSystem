package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.cache.MenuCacheVo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.response.MenuTreeInfo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.Menu;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.service.MenuService;
import cn.com.cdboost.collect.service.RedisService;
import cn.com.cdboost.collect.service.RoleRightService;
import cn.com.cdboost.collect.util.TreeParser;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/menu")
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleRightService roleRightService;
	@Autowired
	private RedisService redisService;


	// 查询系统中所有启用的菜单，并按照树形层级关系返回
	@SystemControllerLog(description = "查询系统中所有启用的菜单，并按照树形层级关系返回")
	@RequestMapping("/queryAllEnabledMenus")
	@ResponseBody
	public String queryAllEnabledMenus() {
		Result<List<MenuTreeInfo>> result = new Result<>();
		List<Menu> menus = menuService.listAllMenus();

		// 转换成前端需要的值
		List<MenuTreeInfo> menuTreeInfos = this.conver(menus);

		// 按树形层级返回
		List<MenuTreeInfo> treeList = TreeParser.getTreeList(0L, menuTreeInfos);
		result.setData(treeList);

		return JSON.toJSONString(result);
	}

	// 根据用户角色，查询用户拥有的菜单信息
	@SystemControllerLog(description = "根据用户角色，查询用户拥有的菜单信息")
	@RequestMapping(value = "queryMenusByUserRole" , method = RequestMethod.POST)
	@ResponseBody
	public String queryMenusByUserRole(HttpSession session) {
		Result<List<MenuTreeInfo>> result = new Result<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		Long roleId = currentUser.getRoleId();
		List<RoleRight> roleRights = roleRightService.queryByRoleId(roleId);
		if (CollectionUtils.isEmpty(roleRights)) {
			List<MenuTreeInfo> emptyList = Lists.newArrayList();
			result.setData(emptyList);
			return JSON.toJSONString(result);
		}

		Set<Long> menuIdSet = Sets.newHashSet();
		for (RoleRight roleRight : roleRights) {
			menuIdSet.add(roleRight.getMenuId());
		}

		// 子菜单批量查询
		List<MenuCacheVo> menuCacheVos = redisService.queryMenuByMenuIds(menuIdSet);
		Set<Long> all = Sets.newHashSet();
		for (MenuCacheVo menuCacheVo : menuCacheVos) {
			String levelCode = menuCacheVo.getLevelCode();
			// 去掉第一个斜杠
			String tempStr = levelCode.substring(1);
			String[] split = tempStr.split("/");
			for (String menuId : split) {
				all.add(Long.valueOf(menuId));
			}
		}

		// 查询所有菜单
		List<MenuCacheVo> allMenus = redisService.queryMenuByMenuIds(all);

		// 排序
		Collections.sort(allMenus, new Comparator<MenuCacheVo>() {
			@Override
			public int compare(MenuCacheVo t1, MenuCacheVo t2) {
				return t1.getSortNo()- t2.getSortNo();
			}
		});

		// 转换成前端需要的值
		List<MenuTreeInfo> menuTreeInfos = Lists.newArrayList();
		for (MenuCacheVo cacheVo : allMenus) {
			MenuTreeInfo info = new MenuTreeInfo();
			BeanUtils.copyProperties(cacheVo,info);
			info.setParentMenuId(cacheVo.getpMenuId());
			menuTreeInfos.add(info);
		}

		// 按树形层级返回
		List<MenuTreeInfo> treeList = TreeParser.getTreeList(0L, menuTreeInfos);
		result.setData(treeList);

		return JSON.toJSONString(result);
	}

	/**
	 * 转换成前端需要的VO
	 * @param list
	 * @return
	 */
	private List<MenuTreeInfo> conver(List<Menu> list) {
		List<MenuTreeInfo> treeInfoList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(list)) {
			for (Menu menu : list) {
				MenuTreeInfo info = new MenuTreeInfo();
				BeanUtils.copyProperties(menu,info);
				info.setParentMenuId(menu.getpMenuId());
				treeInfoList.add(info);
			}
		}
		return treeInfoList;
	}

}
