package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.cache.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 本次中天redis相关的服务接口
 */
public interface RedisService {
    /**
     * 根据orgNo查询与该orgNo相关的整个树节点
     * @param orgNo
     * @return
     */
    List<OrgCacheVo> queryOrgTree(Long orgNo);

    /**
     * 根据组织集合，查询与给组织相关的所有树节点
     * @param orgNoSet
     * @return
     */
    Set<OrgCacheVo> queryAllTreeNode(Set<Long> orgNoSet);

    /**
     * 根据组织集合，查询对应的所有父节点的组织以及自身的缓存
     * @param orgNoSet
     * @return
     */
    Set<Long> queryParentAndCurrentOrgNoList(Set<Long> orgNoSet);

    /**
     * 根据orgNo查询与该组织相关的所有父节点
     * @param orgNo
     * @return
     */
    List<Long> queryParentOrgNo(Long orgNo);

    /**
     * 根据orgNo集合，查询该组织的所有父节点组织集合
     * @param orgNoSet
     * @return
     */
    Set<Long> queryParentOrgNo(Set<Long> orgNoSet);

    /**
     * 查询当前组织以及该组织的所有孩子节点
     * @param orgNo
     * @return
     */
    List<OrgCacheVo> queryChildOrg(Long orgNo);

    /**
     * 查询组织的数据权限集合
     * @param orgNoSet
     * @return
     */
    Set<Long> queryDataOrgs(Set<Long> orgNoSet);

    /**
     * 查询组织下是否存在孩子节点
     * @param orgNoSet
     * @return
     */
    Map<Long,Boolean> queryOrgChildren(Set<Long> orgNoSet);

    /**
     * 根据orgNos集合，查询对应的组织
     * @param orgNos
     * @return
     */
    List<OrgCacheVo> queryOrgCacheList(Collection<Long> orgNos);

    /**
     * 根据用户唯一标识，查询用户基本信息
     * @param customerNoSet
     * @return key=customerNo,value=CustomerCacheVo
     */
    Map<String,CustomerCacheVo> queryCustomerCacheMap(Set<String> customerNoSet);

    /**
     * 查询设备上下级关系记录
     * @param cnoList
     * @return key=cno,value=DeviceRelationCacheVo
     */
    Map<String,DeviceRelationCacheVo> queryDeviceRelationMap(List<String> cnoList);

    /**
     * 查询设备信息缓存相关信息
     * @return key=cno,value=DeviceCacheVo
     */
    Map<String,DeviceCacheVo> queryDeviceCacheMap(List<String> cnoList);

    /**
     * 查询依赖设备状态相关信息
     * @param cnos
     * @return
     */
    Map<String,DeviceStateCacheVo> queryDeviceStateMap(Collection<String> cnos);

    /**
     * 查询设备是否存在孩子节点
     * @param cnoSet
     * @return
     */
    Map<String,Boolean> queryDeviceChildren(Set<String> cnoSet);

    /**
     * 根据menuId集合
     * @param menuIds
     * @return
     */
    List<MenuCacheVo> queryMenuByMenuIds(Collection<Long> menuIds);

    /**
     * 根绝用户id，查询用户拥有的组织数据权限
     * @param userId
     * @return
     */
    Set<Long> queryUserOrgNoByUserId(Integer userId);

    /**
     * 查询设备挂靠的所有孩子节点
     * @return
     */
    Map<String,List<String>> queryDeviceChildMap(Collection<String> parentCnos);

    /**
     * 根据cnos集合，查询对应的客户设备关系记录缓存，以map结构返回
     * @param cnos
     * @return
     */
    Map<String,DeviceMapCacheVo> queryDeviceMapCacheMap(Collection<String> cnos);
}
