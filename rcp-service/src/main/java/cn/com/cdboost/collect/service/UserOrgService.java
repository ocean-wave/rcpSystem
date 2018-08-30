package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.UserOrg;

import java.util.Collection;
import java.util.List;

/**
 * 用户数据权限表接口
 */
public interface UserOrgService extends BaseService<UserOrg>{
    /**
     * 根据用户id，删除
     * @param userId
     * @return
     */
    int deleteByUserId(Integer userId);

    /**
     * 根据用户id集合，批量删除
     * @param userIds
     * @return
     */
    int batchDeleteByUserIds(Collection<Integer> userIds);

    /**
     * 根据用户id集合，批量查询用户的数据权限信息
     * @param userIds
     * @return
     */
    List<UserOrg> batchQueryByUserIds(Collection<Integer> userIds);

    /**
     * 根据用户id查询对应的用户组织数据权限
     * @param userId
     * @return
     */
    List<UserOrg> queryByUserId(Integer userId);

    /**
     * 查询所有用户的管辖权限
     * @return
     */
    List<UserOrg> queryAllUserOrg();
}
