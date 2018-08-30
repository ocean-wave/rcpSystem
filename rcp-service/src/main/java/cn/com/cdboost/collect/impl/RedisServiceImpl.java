package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.*;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 本次中天redis相关的服务接口实现类
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private OrgService orgService;
    @Autowired
    private MeterRelationService meterRelationService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceInfoDeviceStateService deviceInfoDeviceStateService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserOrgService userOrgService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<OrgCacheVo> queryOrgTree(Long orgNo) {
        String key = RedisKeyConstant.ORG_TREE_PREFIX + orgNo;
		String treeJson = redisUtil.get(key, "");
		if (!"".equals(treeJson)) {
		    logger.info("命中缓存，直接从缓存中返回");
            List<OrgCacheVo> orgCacheVos = JSON.parseArray(treeJson, OrgCacheVo.class);
            return orgCacheVos;
        }

        List<Org> orgs = orgService.queryTreeByOrgNo(orgNo);
		List<OrgCacheVo> cacheVos = Lists.newArrayList();
        for (Org org : orgs) {
            OrgCacheVo cacheVo = new OrgCacheVo();
            BeanUtils.copyProperties(org,cacheVo);
            cacheVos.add(cacheVo);
        }

        // 加入缓存
        redisUtil.set(key,JSON.toJSONString(cacheVos));
        return cacheVos;
    }

    @Override
    public Set<OrgCacheVo> queryAllTreeNode(Set<Long> orgNoSet) {
        Set<OrgCacheVo> dataSet = Sets.newHashSet();
        for (Long orgNo : orgNoSet) {
            List<OrgCacheVo> orgCacheVoList = this.queryOrgTree(orgNo);
            dataSet.addAll(orgCacheVoList);
        }
        return dataSet;
    }

    @Override
    public Set<Long> queryParentAndCurrentOrgNoList(Set<Long> orgNoSet) {
        Set<String> keys = Sets.newHashSet();
        for (Long orgNo : orgNoSet) {
            String key = RedisKeyConstant.ORG_PARENT_PREFIX + orgNo;
            keys.add(key);
        }

        Map<Long, List<Long>> parentOrgMap = redisUtil.queryParentOrgMap(keys);

        Set<Long> dataSet = Sets.newHashSet();
        // 记录未被缓存命中的orgNo
        Set<Long> tempSet = Sets.newHashSet();
        if (parentOrgMap == null || parentOrgMap.isEmpty()) {
            tempSet.addAll(orgNoSet);
        } else {
            for (Long orgNo : orgNoSet) {
                List<Long> list = parentOrgMap.get(orgNo);
                if (list == null) {
                    tempSet.add(orgNo);
                    continue;
                }
                dataSet.addAll(list);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            Map<Long, List<Org>> parentListMap = orgService.queryParentListMap(tempSet);
            Map<Long,List<Long>> redisMap = Maps.newHashMap();
            for (Map.Entry<Long, List<Org>> entry : parentListMap.entrySet()) {
                List<Long> list = Lists.newArrayList();
                List<Org> value = entry.getValue();
                for (Org org : value) {
                    list.add(org.getOrgNo());
                    dataSet.add(org.getOrgNo());
                }
                Long orgNo = entry.getKey();
                redisMap.put(orgNo,list);
            }

            // 设置redis
            redisUtil.setParentOrgList(redisMap);
        }

        // 合并参数组织
        dataSet.addAll(orgNoSet);
        return dataSet;
    }

    @Override
    public List<Long> queryParentOrgNo(Long orgNo) {
        String key = RedisKeyConstant.ORG_PARENT_PREFIX + orgNo;
        String str = redisUtil.get(key, "");
        if (!"".equals(str)) {
            logger.info("命中缓存，直接从缓存中返回");
            List<Long> orgNoList = JSON.parseArray(str, Long.class);
            return orgNoList;
        }

        List<Org> orgs = orgService.queryParent(orgNo);
        List<Long> parentList = Lists.newArrayList();
        for (Org org : orgs) {
            parentList.add(org.getOrgNo());
        }

        // 加入缓存
        redisUtil.set(key,JSON.toJSONString(parentList));
        return parentList;
    }

    @Override
    public Set<Long> queryParentOrgNo(Set<Long> orgNoSet) {
        Set<String> keys = Sets.newHashSet();
        for (Long orgNo : orgNoSet) {
            String key = RedisKeyConstant.ORG_PARENT_PREFIX + orgNo;
            keys.add(key);
        }

        Map<Long, List<Long>> parentOrgMap = redisUtil.queryParentOrgMap(keys);
        Set<Long> dataSet = Sets.newHashSet();
        // 记录未被缓存命中的orgNo
        Set<Long> tempSet = Sets.newHashSet();
        if (parentOrgMap == null || parentOrgMap.size() == 0) {
            tempSet.addAll(orgNoSet);
        } else {
            for (Long orgNo : orgNoSet) {
                List<Long> list = parentOrgMap.get(orgNo);
                if (list == null) {
                    tempSet.add(orgNo);
                    continue;
                }
                dataSet.addAll(list);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            Map<Long,List<Long>> map = Maps.newHashMap();
            // 查询数据库
            for (Long orgNo : tempSet) {
                List<Org> orgs = orgService.queryParent(orgNo);
                List<Long> orgList = Lists.newArrayList();
                for (Org org : orgs) {
                    orgList.add(org.getOrgNo());
                }
                map.put(orgNo,orgList);

                // 加入返回列表
                dataSet.addAll(orgList);
            }

            // 设置redis
            redisUtil.setParentOrgList(map);
        }

        return dataSet;
    }

    @Override
    public List<OrgCacheVo> queryChildOrg(Long orgNo) {
        String key = RedisKeyConstant.ORG_CHILD_PREFIX + orgNo;
        String treeJson = redisUtil.get(key, "");
        if (!"".equals(treeJson)) {
            logger.info("命中缓存，直接从缓存中返回");
            List<OrgCacheVo> orgCacheVos = JSON.parseArray(treeJson, OrgCacheVo.class);
            return orgCacheVos;
        }

        List<Org> children = orgService.queryChildren(orgNo);
        List<OrgCacheVo> cacheVos = Lists.newArrayList();
        for (Org org : children) {
            OrgCacheVo cacheVo = new OrgCacheVo();
            BeanUtils.copyProperties(org,cacheVo);
            cacheVos.add(cacheVo);
        }

        // 加入缓存
        redisUtil.set(key,JSON.toJSONString(cacheVos));
        return cacheVos;
    }

    @Override
    public Set<Long> queryDataOrgs(Set<Long> orgNoSet) {
        Set<String> keys = Sets.newHashSet();
        for (Long orgNo : orgNoSet) {
            String key = RedisKeyConstant.ORG_CHILD_PREFIX + orgNo;
            keys.add(key);
        }

        Map<Long, List<Long>> dataOrgMap = redisUtil.queryDataOrgMap(keys);
        Set<Long> dataSet = Sets.newHashSet();
        // 记录未被缓存命中的orgNo
        Set<Long> tempSet = Sets.newHashSet();
        if (dataOrgMap == null || dataOrgMap.isEmpty()) {
            tempSet.addAll(orgNoSet);
        } else {
            for (Long orgNo : orgNoSet) {
                List<Long> list = dataOrgMap.get(orgNo);
                if (list == null) {
                    tempSet.add(orgNo);
                    continue;
                }
                dataSet.addAll(list);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            for (Long orgNo : tempSet) {
                // 查询数据库并设置缓存
                List<OrgCacheVo> orgCacheVoList = this.queryChildOrg(orgNo);
                for (OrgCacheVo orgCacheVo : orgCacheVoList) {
                    dataSet.add(orgCacheVo.getOrgNo());
                }
            }
        }
        return dataSet;
    }

    @Override
    public Map<Long, Boolean> queryOrgChildren(Set<Long> orgNoSet) {
        Set<String> keys = Sets.newHashSet();
        for (Long orgNo : orgNoSet) {
            String key = RedisKeyConstant.ORG_EXIST_CHILD_PREFIX + orgNo;
            keys.add(key);
        }

        Map<Long, Boolean> orgChildMap = redisUtil.queryOrgChildMap(keys);

        Map<Long,Boolean> dataMap = Maps.newHashMap();
        // 记录未被缓存命中的orgNo
        Set<Long> tempSet = Sets.newHashSet();
        if (orgChildMap == null || orgChildMap.isEmpty()) {
            tempSet.addAll(orgNoSet);
        } else {
            for (Long orgNo : orgNoSet) {
                Boolean flag = orgChildMap.get(orgNo);
                if (flag == null) {
                    tempSet.add(orgNo);
                    continue;
                }
                dataMap.put(orgNo,flag);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<Org> orgs = orgService.queryNextChild(tempSet);

            // 按父节点分组
            ImmutableListMultimap<Long, Org> childNodeMap = Multimaps.index(orgs, new Function<Org, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable Org org) {
                    return org.getpOrgNo();
                }
            });

            // 加入返回Map
            Map<Long,Boolean> redisMap = Maps.newHashMap();
            for (Long orgNo : tempSet) {
                ImmutableList<Org> childOrgs = childNodeMap.get(orgNo);
                if (CollectionUtils.isEmpty(childOrgs)) {
                    redisMap.put(orgNo,Boolean.FALSE);
                } else {
                    redisMap.put(orgNo,Boolean.TRUE);
                }
            }
            // 放入返回map
            dataMap.putAll(redisMap);

            // 设置redis
            redisUtil.setOrgChild(redisMap);
        }

        return dataMap;
    }

    @Override
    public List<OrgCacheVo> queryOrgCacheList(Collection<Long> orgNos) {
        Set<String> keys = Sets.newHashSet();
        for (Long orgNo : orgNos) {
            String key = RedisKeyConstant.ORG_PREFIX + orgNo;
            keys.add(key);
        }
        Map<Long, OrgCacheVo> orgCacheMap = redisUtil.queryOrgCacheMap(keys);

        List<OrgCacheVo> dataList = Lists.newArrayList();
        // 记录未被缓存命中的orgNo
        Set<Long> tempSet = Sets.newHashSet();
        if (orgCacheMap == null && orgCacheMap.size() == 0) {
            tempSet.addAll(orgNos);
        } else {
            for (Long orgNo : orgNos) {
                OrgCacheVo orgCacheVo = orgCacheMap.get(orgNo);
                if (orgCacheVo == null) {
                    tempSet.add(orgNo);
                    continue;
                }
                // 命中的加入返回列表
                dataList.add(orgCacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<Long> orgNoList = Lists.newArrayList(tempSet);
            List<OrgCacheVo> orgCacheVos = this.queryOrgCacheList(orgNoList);

            // 加入返回列表
            dataList.addAll(orgCacheVos);

            // 设置redis
            redisUtil.setOrgCacheList(orgCacheVos);
        }

        return dataList;
    }

    private List<OrgCacheVo> queryOrgCacheList(List<Long> orgNoList) {
        List<OrgCacheVo> dataList = Lists.newArrayList();
        List<Org> orgs = orgService.batchQueryByOrgNos(orgNoList);
        for (Org org : orgs) {
            OrgCacheVo cacheVo = new OrgCacheVo();
            BeanUtils.copyProperties(org,cacheVo);
            dataList.add(cacheVo);
        }
        return dataList;
    }

    @Override
    public Map<String,CustomerCacheVo> queryCustomerCacheMap(Set<String> customerNoSet) {
        Set<String> keySet = new HashSet<>();
        for (String customerNo : customerNoSet) {
            String key = RedisKeyConstant.CUSTOMER_PREFIX + customerNo;
            keySet.add(key);
        }

        Map<String, CustomerCacheVo> customerCacheMap = redisUtil.queryCustomerCacheMap(keySet);
        List<CustomerCacheVo> dataList = Lists.newArrayList();
        // 记录未被缓存命中的customerNo
        Set<String> tempSet = Sets.newHashSet();
        if (customerCacheMap == null || customerCacheMap.size() == 0) {
            tempSet.addAll(customerNoSet);
        } else {
            for (String customerNo : customerNoSet) {
                CustomerCacheVo cacheVo = customerCacheMap.get(customerNo);
                if (cacheVo == null) {
                    tempSet.add(customerNo);
                    continue;
                }
                // 命中的加入返回列表
                dataList.add(cacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<String> list = Lists.newArrayList(tempSet);
            List<CustomerCacheVo> customerCacheVos = this.queryCustomerCache(list);

            // 加入返回列表
            dataList.addAll(customerCacheVos);

            // 设置redis缓存
            redisUtil.setCustomerCacheList(customerCacheVos);
        }

        Map<String, CustomerCacheVo> map = this.customerCacheList2Map(dataList);
        return map;
    }

    private List<CustomerCacheVo> queryCustomerCache(List<String> customerNoList) {
        List<CustomerCacheVo> dataList = Lists.newArrayList();
        Set<String> set = Sets.newHashSet(customerNoList);
        List<CustomerInfo> customerInfoList = customerInfoService.batchQueryByCustomerNos(set);
        for (CustomerInfo customerInfo : customerInfoList) {
            CustomerCacheVo cacheVo = new CustomerCacheVo();
            BeanUtils.copyProperties(customerInfo,cacheVo);
            dataList.add(cacheVo);
        }
        return dataList;
    }

    private Map<String,CustomerCacheVo> customerCacheList2Map(List<CustomerCacheVo> list) {
        ImmutableMap<String, CustomerCacheVo> map = Maps.uniqueIndex(list, new Function<CustomerCacheVo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CustomerCacheVo customerCacheVo) {
                return customerCacheVo.getCustomerNo();
            }
        });
        return map;
    }

    @Override
    public Map<String,DeviceRelationCacheVo> queryDeviceRelationMap(List<String> cnoList) {
        Set<String> keySet = new HashSet<>();
        for (String cno : cnoList) {
            String key = RedisKeyConstant.DEVICE_RELATION_PREFIX + cno;
            keySet.add(key);
        }
        Map<String, DeviceRelationCacheVo> deviceRelationMap = redisUtil.getDeviceRelationMap(keySet);
        List<DeviceRelationCacheVo> dataList = Lists.newArrayList();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (deviceRelationMap == null || deviceRelationMap.size() == 0) {
            tempSet.addAll(cnoList);
        } else {
            for (String cno : cnoList) {
                DeviceRelationCacheVo cacheVo = deviceRelationMap.get(cno);
                if (cacheVo == null) {
                    tempSet.add(cno);
                    continue;
                }
                dataList.add(cacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<String> list = Lists.newArrayList(tempSet);
            List<DeviceRelationCacheVo> relationCacheVos = this.queryReleationCacheVo(list);

            // 加入返回列表
            dataList.addAll(relationCacheVos);

            // 设置redis
            redisUtil.setDeviceRelationList(relationCacheVos);
        }

        Map<String, DeviceRelationCacheVo> map = this.deviceRelationListToMap(dataList);
        return map;
    }

    private Map<String,DeviceRelationCacheVo> deviceRelationListToMap(List<DeviceRelationCacheVo> relationCacheVos) {
        ImmutableMap<String, DeviceRelationCacheVo> map = Maps.uniqueIndex(relationCacheVos, new Function<DeviceRelationCacheVo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceRelationCacheVo deviceRelationCacheVo) {
                return deviceRelationCacheVo.getMeterCno();
            }
        });
        return map;
    }

    @Override
    public Map<String, DeviceCacheVo> queryDeviceCacheMap(List<String> cnoList) {
        Set<String> keySet = new HashSet<>();
        for (String cno : cnoList) {
            String key = RedisKeyConstant.DEVICE_PREFIX + cno;
            keySet.add(key);
        }
        Map<String, DeviceCacheVo> deviceCacheMap = redisUtil.getDeviceCacheMap(keySet);
        List<DeviceCacheVo> dataList = Lists.newArrayList();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (deviceCacheMap == null || deviceCacheMap.size() ==0) {
            tempSet.addAll(cnoList);
        } else {
            for (String cno : cnoList) {
                DeviceCacheVo cacheVo = deviceCacheMap.get(cno);
                if (cacheVo == null) {
                    tempSet.add(cno);
                    continue;
                }
                dataList.add(cacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<String> list = Lists.newArrayList(tempSet);
            List<DeviceCacheVo> deviceCacheVos = this.queryDeviceCacheVo(list);

            // 加入返回列表
            dataList.addAll(deviceCacheVos);

            // 设置redis
            redisUtil.setDeviceCacheList(deviceCacheVos);
        }

        Map<String, DeviceCacheVo> map = this.deviceCacheListToMap(dataList);
        return map;
    }

    @Override
    public Map<String, DeviceStateCacheVo> queryDeviceStateMap(Collection<String> cnos) {
        Set<String> keySet = new HashSet<>();
        for (String cno : cnos) {
            String key = RedisKeyConstant.DEVICE_STATE_PREFIX + cno;
            keySet.add(key);
        }
        Map<String, DeviceStateCacheVo> deviceStateMap = redisUtil.getDeviceStateMap(keySet);
        Map<String, DeviceStateCacheVo> dataMap = Maps.newHashMap();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (deviceStateMap == null || deviceStateMap.isEmpty()) {
            tempSet.addAll(cnos);
        } else {
            for (String cno : cnos) {
                DeviceStateCacheVo cacheVo = deviceStateMap.get(cno);
                if (cacheVo == null) {
                    tempSet.add(cno);
                    continue;
                }
                dataMap.put(cno,cacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<String> list = Lists.newArrayList(tempSet);
            List<DeviceInfoDeviceState> deviceStates = deviceInfoDeviceStateService.batchQueryByCnos(list);

            // 加入返回map
            List<DeviceStateCacheVo> cacheVos = Lists.newArrayList();
            for (DeviceInfoDeviceState deviceState : deviceStates) {
                DeviceStateCacheVo cacheVo = new DeviceStateCacheVo();
                BeanUtils.copyProperties(deviceState,cacheVo);
                cacheVos.add(cacheVo);
                dataMap.put(deviceState.getCno(),cacheVo);
            }

            // 设置缓存
            redisUtil.setDeviceStateCollection(cacheVos);
        }
        return dataMap;
    }

    @Override
    public Map<String, Boolean> queryDeviceChildren(Set<String> cnoSet) {
        Set<String> keys = Sets.newHashSet();
        for (String cno : cnoSet) {
            String key = RedisKeyConstant.DEVICE_CHILD_PREFIX + cno;
            keys.add(key);
        }

        Map<String, Boolean> deviceChildMap = redisUtil.queryDeviceChildMap(keys);

        Map<String,Boolean> dataMap = Maps.newHashMap();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (deviceChildMap == null || deviceChildMap.size() == 0) {
            tempSet.addAll(cnoSet);
        } else {
            for (String cno : cnoSet) {
                Boolean flag = deviceChildMap.get(cno);
                if (flag == null) {
                    tempSet.add(cno);
                    continue;
                }
                dataMap.put(cno,flag);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<MeterRelation> childNodeList = meterRelationService.queryChildNode(tempSet);

            // 按父节点分组
            ImmutableListMultimap<String, MeterRelation> childNodeMap = Multimaps.index(childNodeList, new Function<MeterRelation, String>() {
                @Nullable
                @Override
                public String apply(@Nullable MeterRelation meterRelation) {
                    return meterRelation.getpMeterCno();
                }
            });

            // 加入返回Map
            Map<String,Boolean> redisMap = Maps.newHashMap();
            for (String cno : tempSet) {
                ImmutableList<MeterRelation> childRelations = childNodeMap.get(cno);
                if (CollectionUtils.isEmpty(childRelations)) {
                    redisMap.put(cno,Boolean.FALSE);
                } else {
                    redisMap.put(cno,Boolean.TRUE);
                }
            }
            // 放入返回map
            dataMap.putAll(redisMap);

            // 设置redis
            redisUtil.setDeviceChild(redisMap);
        }
        return dataMap;
    }

    @Override
    public List<MenuCacheVo> queryMenuByMenuIds(Collection<Long> menuIds) {
        Set<String> keys = Sets.newHashSet();
        for (Long menuId : menuIds) {
            String key = RedisKeyConstant.MENU_PREFIX + menuId;
            keys.add(key);
        }

        Map<Long, MenuCacheVo> menuCacheMap = redisUtil.queryMenuCacheMap(keys);
        List<MenuCacheVo> dataList = Lists.newArrayList();
        // 记录未被缓存命中的cno
        Set<Long> tempSet = Sets.newHashSet();
        if (menuCacheMap == null || menuCacheMap.isEmpty()) {
            tempSet.addAll(menuIds);
        } else {
            for (Long menuId : menuIds) {
                MenuCacheVo menuCacheVo = menuCacheMap.get(menuId);
                if (menuCacheVo == null) {
                    tempSet.add(menuId);
                    continue;
                }
                dataList.add(menuCacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            // 查询数据库
            List<Long> menuIdList = Lists.newArrayList(tempSet);
            List<Menu> list = menuService.queryMenusByMenuIds(menuIdList);

            List<MenuCacheVo> menuCacheVos = Lists.newArrayList();
            for (Menu menu : list) {
                MenuCacheVo cacheVo = new MenuCacheVo();
                BeanUtils.copyProperties(menu,cacheVo);
                menuCacheVos.add(cacheVo);
            }
            dataList.addAll(menuCacheVos);

            // 设置redis
            redisUtil.setMenuCacheList(menuCacheVos);
        }
        return dataList;
    }

    @Override
    public Set<Long> queryUserOrgNoByUserId(Integer userId) {
        Set<Long> dataSet = Sets.newHashSet();
        String key = RedisKeyConstant.USER_ORGS_PREFIX + userId;
        String s = redisUtil.get(key, "");
        if (!"".equals(s)) {
            logger.info("命中缓存，直接从缓存中返回");
            List<Long> orgNos = JSON.parseArray(s, Long.class);
            dataSet.addAll(orgNos);
            return dataSet;
        }

        // 查询数据库
        List<UserOrg> userOrgs = userOrgService.queryByUserId(userId);
        Set<Long> orgNoSet = Sets.newHashSet();
        for (UserOrg userOrg : userOrgs) {
            orgNoSet.add(userOrg.getOrgNo());
        }

        // 查询当前组织以及该组织的所有孩子节点
        Set<Long> allDataOrgs = this.queryDataOrgs(orgNoSet);

        // 设置缓存
        List<Long> list = Lists.newArrayList(allDataOrgs);
        redisUtil.set(key,JSON.toJSONString(list));
        return allDataOrgs;
    }

    @Override
    public Map<String, List<String>> queryDeviceChildMap(Collection<String> parentCnos) {
        Set<String> keys = Sets.newHashSet();
        for (String parentCno : parentCnos) {
            String key = RedisKeyConstant.DEVICE_RELATION_CHILD_PREFIX + parentCno;
            keys.add(key);
        }

        Map<String, List<String>> relationChildMap = redisUtil.queryDeviceRelationChildMap(keys);
        Map<String, List<String>>  dataMap = Maps.newHashMap();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (relationChildMap == null || relationChildMap.isEmpty()) {
            tempSet.addAll(parentCnos);
        } else {
            for (String parentCno : parentCnos) {
                List<String> list = relationChildMap.get(parentCno);
                if (list == null) {
                    tempSet.add(parentCno);
                    continue;
                }
                dataMap.put(parentCno,list);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            Map<String,List<String>> cacheMap = Maps.newHashMap();
            for (String cno : tempSet) {
                List<String> cnoList = meterRelationService.queryChildCnoList(cno);
                cacheMap.put(cno,cnoList);
                dataMap.put(cno,cnoList);
            }

            // 设置缓存
            redisUtil.setDeviceRelationChildMap(cacheMap);
        }
        return dataMap;
    }

    @Override
    public Map<String, DeviceMapCacheVo> queryDeviceMapCacheMap(Collection<String> cnos) {
        Set<String> keys = Sets.newHashSet();
        for (String cno : cnos) {
            String key = RedisKeyConstant.DEVICE_MAP_PREFIX + cno;
            keys.add(key);
        }

        Map<String, DeviceMapCacheVo> cacheVoMap = redisUtil.queryDeviceMapCacheMap(keys);
        Map<String, DeviceMapCacheVo> dataMap = Maps.newHashMap();
        // 记录未被缓存命中的cno
        Set<String> tempSet = Sets.newHashSet();
        if (cacheVoMap == null || cacheVoMap.isEmpty()) {
            tempSet.addAll(cnos);
        } else {
            for (String cno : cnos) {
                DeviceMapCacheVo deviceMapCacheVo = cacheVoMap.get(cno);
                if (deviceMapCacheVo == null) {
                    tempSet.add(cno);
                    continue;
                }
                dataMap.put(cno,deviceMapCacheVo);
            }
        }

        // 有未被缓存命中的，查数据库，并设置缓存
        if (!tempSet.isEmpty()) {
            List<String> cnoList = Lists.newArrayList(tempSet);
            List<CustomerDevMap> devMaps = customerDevMapService.batchQueryByCnos(cnoList);

            // 放入返回map
            List<DeviceMapCacheVo> cacheVoList = Lists.newArrayList();
            for (CustomerDevMap devMap : devMaps) {
                DeviceMapCacheVo cacheVo = new DeviceMapCacheVo();
                BeanUtils.copyProperties(devMap,cacheVo);
                dataMap.put(devMap.getCno(),cacheVo);
                cacheVoList.add(cacheVo);
            }

            // 设置缓存
            redisUtil.setDeviceMapCacheList(cacheVoList);
        }

        return dataMap;
    }

    private Map<String,DeviceCacheVo> deviceCacheListToMap(List<DeviceCacheVo> list) {
        ImmutableMap<String, DeviceCacheVo> immutableMap = Maps.uniqueIndex(list, new Function<DeviceCacheVo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceCacheVo deviceCacheVo) {
                return deviceCacheVo.getCno();
            }
        });
        return immutableMap;
    }

    private List<DeviceCacheVo> queryDeviceCacheVo(List<String> cnoList) {
        List<DeviceCacheVo> dataList = Lists.newArrayList();
        List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
        for (DeviceInfo deviceInfo : deviceInfos) {
            DeviceCacheVo cacheVo = new DeviceCacheVo();
            BeanUtils.copyProperties(deviceInfo,cacheVo);
            dataList.add(cacheVo);
        }

        // 查询参数表信息
        List<DeviceMeterParam> meterParamList = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        // 分组
        ImmutableMap<String, DeviceMeterParam> meterParamMap = Maps.uniqueIndex(meterParamList, new Function<DeviceMeterParam, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceMeterParam deviceMeterParam) {
                return deviceMeterParam.getCno();
            }
        });

        // 设置重点表字典
        for (DeviceCacheVo deviceCacheVo : dataList) {
            DeviceMeterParam meterParam = meterParamMap.get(deviceCacheVo.getCno());
            deviceCacheVo.setIsImportant(meterParam.getIsImportant());
        }
        return dataList;
    }

    private List<DeviceRelationCacheVo> queryReleationCacheVo(List<String> cnoList) {
        List<DeviceRelationCacheVo> dataList = Lists.newArrayList();
        List<MeterRelation> meterRelations = meterRelationService.batchQueryByMeterCnos(cnoList);
        for (MeterRelation meterRelation : meterRelations) {
            DeviceRelationCacheVo cacheVo = new DeviceRelationCacheVo();
            BeanUtils.copyProperties(meterRelation,cacheVo);
            dataList.add(cacheVo);
        }
        return dataList;
    }
}
