package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.UserRoleMapper;
import cn.com.cdboost.collect.model.UserRole;
import cn.com.cdboost.collect.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 用户角色管理表服务接口实现类
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> queryByRoleId(Long roleId) {
        UserRole param = new UserRole();
        param.setRoleId(roleId);
        return userRoleMapper.select(param);
    }

    @Override
    public UserRole queryByUserId(Long userId) {
        UserRole param = new UserRole();
        param.setUserId(userId);
        return userRoleMapper.selectOne(param);
    }

    @Override
    @Transactional
    public void batchDeleteByUserIds(List<Integer> userIds) {
        Condition condition = new Condition(UserRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("userId",userIds);
        userRoleMapper.deleteByCondition(condition);
    }

    @Override
    @Transactional
    public void updateSelectiveByUserId(Long userId, UserRole param) {
        Condition condition = new Condition(UserRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("userId",userId);
        userRoleMapper.updateByConditionSelective(param,condition);
    }
}
