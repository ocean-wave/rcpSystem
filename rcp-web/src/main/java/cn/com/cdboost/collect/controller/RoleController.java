package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.MenuActionParam;
import cn.com.cdboost.collect.dto.param.RoleAddParam;
import cn.com.cdboost.collect.dto.param.RoleEditParam;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.Role;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.service.RoleService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
* @Description:角色操作Controller层
*@author linyang
*@date 2017年5月15日
*/
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserLogService userLogService;

	// 角色添加
	@Auth(menuID=100041L,actionID=1L)
	@SystemControllerLog(description = "角色添加")
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(HttpSession session, @Valid @RequestBody RoleAddParam param){
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"角色控制","roleName",param.getRoleName(),"添加角色:["+param.getRoleName()+"]", JSON.toJSONString(param));

		Role role = new Role();
		role.setCreateUserId(Long.valueOf(currentUser.getId()));
		role.setDescription(param.getDescription());
		role.setRoleName(param.getRoleName());

		List<MenuActionParam> menuActionList = param.getMenuActionList();
		if (CollectionUtils.isEmpty(menuActionList)) {
			result.error("该角色对应的菜单动作列表数据为空");
			return JSON.toJSONString(result);
		}
		List<RoleRight> roleRightList = this.getRoleRightList(menuActionList);
		roleService.addRole(role,roleRightList);

		result.setMessage("新增成功");
		return JSON.toJSONString(result);
	}


	// 角色删除
	@Auth(menuID=100041L,actionID=3L)
	@SystemControllerLog(description = "角色删除")
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(HttpSession session, @RequestParam Integer roleId){
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		Role role = roleService.selectByPrimaryKey(roleId);
		roleService.deleteByRoleId(roleId);
		result.setMessage("删除成功");
		userLogService.create(currentUser.getId(), Action.DELETE.getActionId(),"角色控制","roleId", String.valueOf(roleId),"删除角色:["+role.getRoleName()+"]" , String.valueOf(roleId));
		return JSON.toJSONString(result);
	}

	// 角色修改
	@Auth(menuID=100041L,actionID=2L)
	@SystemControllerLog(description = "角色修改")
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpSession session,@Valid @RequestBody RoleEditParam param){
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"角色控制","roleId", String.valueOf(param.getRoleId()),"修改角色["+param.getRoleName()+"]信息" ,JSON.toJSONString(param));

		Role role = new Role();
		role.setCreateUserId(Long.valueOf(currentUser.getId()));
		role.setDescription(param.getDescription());
		role.setRoleName(param.getRoleName());
		role.setId(param.getRoleId());
		List<RoleRight> roleRightList = this.getRoleRightList(param.getMenuActionList());
		roleService.update(role,roleRightList);

		result.setMessage("修改成功");
		return JSON.toJSONString(result);
	}

	/**
	 * 查询所有角色
	 * @return
	 */
	@Auth(menuID={100041L,100042L},actionID={1L,2L,3L,4L})
	@SystemControllerLog(description = "查询所有角色")
	@RequestMapping("/queryAll")
	@ResponseBody
	public String queryAll(HttpSession session) {
		Result<List<Role>> result = new Result<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		List<Role> roles = roleService.listRole(currentUser.getRoleId(),currentUser.getIsSystem());
		result.setData(roles);

		return JSON.toJSONString(result);
	}

	/**
	 * 根据角色id，查询对应的菜单动作权限列表
	 * @param roleId
	 * @return
	 */
	@Auth(menuID=100041L,actionID={1L,2L,3L,4L})
	@SystemControllerLog(description = "根据角色id，查询对应的菜单动作权限列表")
	@RequestMapping("/queryRolePermissions")
	@ResponseBody
	public String queryRolePermissions(@RequestParam Integer roleId) {
		Result<List<RoleRight>> result = new Result<>();
		List<RoleRight> rolePermissions = roleService.getRolePermissionByRoleId(Long.valueOf(roleId));
		result.setData(rolePermissions);

		return JSON.toJSONString(result);
	}

	private List<RoleRight> getRoleRightList(List<MenuActionParam> menuActionList) {
		List<RoleRight> roleRights = Lists.newArrayList();
		for (MenuActionParam actionParam : menuActionList) {
			RoleRight roleRight = new RoleRight();
			roleRight.setMenuId(actionParam.getMenuId());
			roleRight.setActionId(actionParam.getActionId());
			roleRights.add(roleRight);
		}
		return roleRights;
	}
	
}
