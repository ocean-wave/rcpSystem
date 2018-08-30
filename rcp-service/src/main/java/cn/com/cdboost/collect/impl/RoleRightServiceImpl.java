package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.RoleRightMapper;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.service.RoleRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色功能权限表服务接口实现类
 */
@Service
public class RoleRightServiceImpl extends BaseServiceImpl<RoleRight> implements RoleRightService{
    @Autowired
    private RoleRightMapper roleRightMapper;

    @Override
    public List<RoleRight> queryByRoleId(Long roleId) {
        RoleRight param = new RoleRight();
        param.setRoleId(roleId);
        return roleRightMapper.select(param);
    }

    @Override
    public List<RoleRight> queryByRoleIdAndMenuId(Long roleId, Long menuId) {
        RoleRight param = new RoleRight();
        param.setRoleId(roleId);
        param.setMenuId(menuId);
        return roleRightMapper.select(param);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId) {
        RoleRight param = new RoleRight();
        param.setRoleId(roleId);
        roleRightMapper.delete(param);
    }
}
