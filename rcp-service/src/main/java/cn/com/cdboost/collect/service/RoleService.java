package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.Role;
import cn.com.cdboost.collect.model.RoleRight;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService extends BaseService<Role>{
	void addRole(Role role, List<RoleRight> roleRights) throws BusinessException;

	/**
	 * 根据id删除角色
	 * @param roleId
	 * @throws BusinessException
	 */
	void deleteByRoleId(Integer roleId) throws BusinessException;
	/**
	*@Description:更新角色信息
	*@param role menuIDs menuActionIDs
	*@throws Exception 
	*/
	void update(Role role, List<RoleRight> rightList) throws BusinessException;
	/**
	*@Description:查询所有角色
	*@return List<Role>
	*@throws Exception 
	*/
	List<Role> listRole(Long roleId,Integer isSystem);

	/**
	*@Description:查询角色的权限
	*@param roleId
	*@return List<RolePermission>
	*@throws Exception 
	*/
	List<RoleRight> getRolePermissionByRoleId(long roleId);

	/**
	*@Description:根据菜单id与角色id查询权限
	*@param menuId
	*@param roleId
	*@return List<RolePermission>
	*@throws Exception 
	*/
	List<RoleRight> queryRolePermissions(long menuId, long roleId);
	
}
