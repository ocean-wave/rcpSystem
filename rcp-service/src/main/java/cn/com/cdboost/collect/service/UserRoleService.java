package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.model.UserRole;

import java.util.List;

/**
 * 用户角色管理表服务接口
 */
public interface UserRoleService extends BaseService<UserRole>{

    // 按角色id，查询
    List<User> queryByRoleId(Long roleId);

    // 按用户id，查询
    UserRole queryByUserId(Long userId);

    // 按照userIds列表，批量删除
    void batchDeleteByUserIds(List<Integer> userIds);

    // 按照用户id，更新param中非空的字段
    void updateSelectiveByUserId(Long userId,UserRole param);
}
