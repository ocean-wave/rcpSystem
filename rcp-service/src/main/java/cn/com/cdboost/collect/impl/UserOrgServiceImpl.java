package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.UserOrgMapper;
import cn.com.cdboost.collect.model.UserOrg;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * 用户数据权限表接口实现类
 */
@Service
public class UserOrgServiceImpl extends BaseServiceImpl<UserOrg> implements UserOrgService {

    @Autowired
    private UserOrgMapper userOrgMapper;
    @Autowired
    private OrgService orgService;

    @Override
    public int deleteByUserId(Integer userId) {
        UserOrg param = new UserOrg();
        param.setUserId(userId);
        int num = userOrgMapper.delete(param);
        return num;
    }

    @Override
    public int batchDeleteByUserIds(Collection<Integer> userIds) {
        Condition condition = new Condition(UserOrg.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("userId",userIds);
        int num = userOrgMapper.deleteByCondition(condition);
        return num;
    }

    @Override
    public List<UserOrg> batchQueryByUserIds(Collection<Integer> userIds) {
        Condition condition = new Condition(UserOrg.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("userId",userIds);
        List<UserOrg> userOrgs = userOrgMapper.selectByCondition(condition);
        return userOrgs;
    }

    @Override
    public List<UserOrg> queryByUserId(Integer userId) {
        UserOrg param = new UserOrg();
        param.setUserId(userId);
        return userOrgMapper.select(param);
    }

    @Override
    public List<UserOrg> queryAllUserOrg() {
        List<UserOrg> userOrgs = userOrgMapper.selectAll();
        return userOrgs;
    }


}
