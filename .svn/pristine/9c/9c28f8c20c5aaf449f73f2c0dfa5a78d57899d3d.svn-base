package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.RoleRight;

import java.util.List;

/**
 * 角色功能权限表服务接口
 */
public interface RoleRightService extends BaseService<RoleRight> {
    // 根据roleId查询
    List<RoleRight> queryByRoleId(Long roleId);

    // 按角色id，菜单id查询
    List<RoleRight> queryByRoleIdAndMenuId(Long roleId, Long menuId);

    // 根据角色id删除
    void deleteByRoleId(Long roleId);
}
