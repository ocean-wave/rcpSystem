package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.UserConstant;
import cn.com.cdboost.collect.dao.RoleMapper;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.Role;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.model.UserRole;
import cn.com.cdboost.collect.service.RoleRightService;
import cn.com.cdboost.collect.service.RoleService;
import cn.com.cdboost.collect.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 角色服务接口实现类
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleRightService roleRightService;
	@Autowired
	private UserRoleService userRoleService;

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void addRole(Role role, List<RoleRight> roleRights) throws BusinessException{
		Role param = new Role();
		param.setRoleName(role.getRoleName());
		Role role1 = roleMapper.selectOne(param);
		if (role1 != null) {
			throw new BusinessException("角色已存在");
		}

		// 插入角色
		roleMapper.insertSelective(role);

		// 批量插入角色权限列表
		Integer roleId = role.getId();
		for (RoleRight roleRight : roleRights) {
			roleRight.setRoleId(Long.valueOf(roleId));
		}
		roleRightService.insertList(roleRights);
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void deleteByRoleId(Integer roleId) throws BusinessException {
		/**
		 * 删除角色需要判断该角色下面是否有用户，
		 * 如果有用户则不允许删除该角色，并提示；
		 * 如果没有用户则允许删除该角色。
		 */
		List<UserRole> userRoles = userRoleService.queryByRoleId(Long.valueOf(roleId));
		if (!CollectionUtils.isEmpty(userRoles)) {
			throw new BusinessException("该角色存在用户，请先删除用户后删除角色!");
		}

		//删除角色-权限表记录
		roleRightService.deleteByRoleId(Long.valueOf(roleId));

		//删除角色表记录
		roleMapper.deleteByPrimaryKey(roleId);
	}



	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void update(Role role, List<RoleRight> rightList) throws BusinessException {
		roleRightService.deleteByRoleId(Long.valueOf(role.getId()));

		//批量插入角色权限表
		for (RoleRight roleRight : rightList) {
			roleRight.setRoleId(Long.valueOf(role.getId()));
		}
		roleRightService.insertList(rightList);
	}

	@Override
	public List<Role> listRole(Long roleId,Integer isSystem){
		//判断是否为系统用户
		if(isSystem == UserConstant.IsSystem.ISSYSTEM.getStatus()){
			return roleMapper.selectAll();
		}else {
			//判断是否有系统管理员角色
			if(roleId == UserConstant.IsAdminRole.ISADMINROLE.getStatus()){
				return roleMapper.selectAll();
			}else {
				Role role = new Role();
				role.setId(roleId.intValue());
				return roleMapper.select(role);
			}
		}
	}
	@Override
	public List<RoleRight> getRolePermissionByRoleId(long roleId) {
		List<RoleRight> select = roleRightService.queryByRoleId(roleId);
		return select;
	
	}

	@Override
	public List<RoleRight> queryRolePermissions(long menuId, long roleId) {
		return roleRightService.queryByRoleIdAndMenuId(roleId,menuId);
	}
	
}
