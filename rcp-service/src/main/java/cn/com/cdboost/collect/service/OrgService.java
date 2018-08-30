package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.OrgTreeInfo;
import cn.com.cdboost.collect.model.Org;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 组织服务接口
 */
public interface OrgService extends BaseService<Org>{
    // 按用户id，查询
    List<OrgTreeInfo> queryByUserId(Long userId,String orgNo);

    // 按组织编号，查询
    Org queryByOrgNo(Long orgNo);

    // 按orgNos列表，批量查询
    List<Org> batchQueryByOrgNos(List<Long> orgNos);

    // 查询某个组织以及该组织下的所有组织
    List<Org> queryChildren(Long orgNo);

    // 查询某个组织所在的整个树
    List<Org> queryTreeByOrgNo(Long orgNo);

    // 根据当前组织编号,查询跟当前节点相关的父组织
    List<Org> queryParent(Long orgNo);

    // 根据组织集合，查询每个组织的父组织列表，并以map形式返回
    Map<Long,List<Org>> queryParentListMap(Set<Long> orgNoSet);

    // 根据组织集合，查询该组织下一级的孩子节点
    List<Org> queryNextChild(Set<Long> orgNoSet);

    // 根据当前节点集合，查询所有父节点和自身组织信息
    List<Org> queryParentAndCurrent(Set<Long> orgNoSet);

    List<Org> queryAll();

    int updateByorgNo(String orgNo,String orgName,Integer id,String pOrgNo);
    int deletebyorgNo(Long orgNo);
    int addByorgNo(String porgNo,String orgName,Integer id);

    // 根据组织查询用户的管辖权限
    List<Long> queryDataOrg(Collection<Long> orgNos);
}
