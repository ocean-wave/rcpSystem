package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.*;
import cn.com.cdboost.collect.dto.BuildingTreeInfo;
import cn.com.cdboost.collect.dto.MainSubTreeInfo;
import cn.com.cdboost.collect.dto.OrgTreeNode;
import cn.com.cdboost.collect.dto.param.FuzzyQueryTreeVo;
import cn.com.cdboost.collect.dto.response.BuildInfoDto;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.dto.response.OnlineStatus;
import cn.com.cdboost.collect.dto.response.OtherMainSubDto;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.MeterRelation;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.TreeParser2;
import cn.com.cdboost.collect.util.TreeParser3;
import cn.com.cdboost.collect.util.TreeParser4;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 树形服务接口实现类
 */
@Service
public class TreeServiceImpl implements TreeService {
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private MeterRelationService meterRelationService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private DeviceInfoDeviceStateService deviceInfoDeviceStateService;
    @Autowired
    private RedisService redisService;



    @Override
    public List<BuildingTreeInfo> queryBuildingTree(Integer userId) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        // 查询组织所在树的所有节点
        Set<OrgCacheVo> orgCacheVos = redisService.queryAllTreeNode(dataOrgNos);

        // 转换成前端需要的值
        List<OrgCacheVo> orgCacheList = Lists.newArrayList(orgCacheVos);
        List<BuildingTreeInfo> treeInfos = this.convert(orgCacheList);

        // 查询楼栋信息
        List<BuildInfoDto> buildInfoDtos = customerInfoService.queryBuildInfo(dataOrgNos);

        // 按orgNo分组
        ImmutableListMultimap<Long, BuildInfoDto> buildInfoMap = Multimaps.index(buildInfoDtos, new Function<BuildInfoDto, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable BuildInfoDto buildInfoDto) {
                return buildInfoDto.getOrgNo();
            }
        });

        // 构造最末级节点
        List<BuildingTreeInfo> buildList = Lists.newArrayList();
        for (Map.Entry<Long, Collection<BuildInfoDto>> entry : buildInfoMap.asMap().entrySet()) {
            Long key = entry.getKey();
            Collection<BuildInfoDto> value = entry.getValue();
            for (BuildInfoDto infoDto : value) {
                BuildingTreeInfo treeInfo = new BuildingTreeInfo();
                treeInfo.setNodeType(2);
                treeInfo.setNodeName(infoDto.getBuildName());
                treeInfo.setpNodeNo(key);
                treeInfo.setNodeNo(Long.valueOf(infoDto.getBuildNo()));
                treeInfo.setHouseHolds(infoDto.getHouseHolds());
                treeInfo.setTotal(infoDto.getHouseHolds());
                buildList.add(treeInfo);
            }
        }

        // 合并
        treeInfos.addAll(buildList);

        // 树形结构返回
        List<BuildingTreeInfo> treeList = TreeParser3.getTreeList(0L, treeInfos);
        return treeList;
    }

    @Override
    public List<MainSubTreeInfo> queryMainSubTree(Integer userId, String deviceType,Integer isImportant,Integer onlineStatus) {
        // 查询组织权限下的总表信息
        List<MainSubDto> mainSubDtos;
        if (isImportant == 1) {
            mainSubDtos = this.queryMainSubDto4Imp(userId, deviceType);
        } else {
            mainSubDtos = this.queryMainSubDto4Common(userId, deviceType,onlineStatus);
        }


        Set<Long> tempSet = Sets.newHashSet();  // 记录总表所在的组织
        List<String> cnoList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            cnoList.add(mainSubDto.getMeterCno());
            tempSet.add(mainSubDto.getOrgNo());
        }

        // 查询总表所在组织的所有父组织
        Set<Long> all = redisService.queryParentAndCurrentOrgNoList(tempSet);
        List<OrgCacheVo> orgCacheVos = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVos);

        // 将设备表转换成前端需要的节点
        List<MainSubTreeInfo> deviceTreeList = this.convertMainSubByDeviceInfo(mainSubDtos,isImportant);

        // 合并组织节点和设备表节点
        orgTreeList.addAll(deviceTreeList);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    @Override
    public List<MainSubTreeInfo> queryMainSubTree4Common(Integer userId, String deviceType) {
        List<MainSubDto> mainSubDtos = this.queryMainSubDto4CommonUser(userId, deviceType);

        Set<Long> tempSet = Sets.newHashSet();  // 记录总表所在的组织
        List<String> cnoList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            cnoList.add(mainSubDto.getMeterCno());
            tempSet.add(mainSubDto.getOrgNo());
        }

        // 查询总表所在组织的所有父组织
        Set<Long> all = redisService.queryParentAndCurrentOrgNoList(tempSet);
        List<OrgCacheVo> orgCacheVos = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVos);

        // 将设备表转换成前端需要的节点
        List<MainSubTreeInfo> deviceTreeList = this.convertMainSubByDeviceInfo(mainSubDtos,0);

        // 合并组织节点和设备表节点
        orgTreeList.addAll(deviceTreeList);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    @Override
    public List<MainSubTreeInfo> queryMainSubTree4Imp(Integer userId, String deviceType) {
        List<MainSubDto> mainSubDtos = this.queryMainSubDto4Imp(userId, deviceType);

        // 记录总表所在的组织
        Set<Long> tempSet = Sets.newHashSet();
        List<String> cnoList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            cnoList.add(mainSubDto.getMeterCno());
            tempSet.add(mainSubDto.getOrgNo());
        }

        // 查询总表所在组织的所有父组织
        Set<Long> all = redisService.queryParentAndCurrentOrgNoList(tempSet);
        List<OrgCacheVo> orgCacheVos = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVos);

        // 将设备表转换成前端需要的节点
        List<MainSubTreeInfo> deviceTreeList = this.convertMainSubByDeviceInfo(mainSubDtos,1);

        // 合并组织节点和设备表节点
        orgTreeList.addAll(deviceTreeList);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    @Override
    public List<MainSubTreeInfo> queryDeviceMainSubTree(Integer userId, String deviceType,Integer onlineStatus) {
        List<MainSubDto> mainSubDtos = this.queryDeviceMainSubDto(userId, deviceType, onlineStatus);

        Set<Long> tempSet = Sets.newHashSet();  // 记录总表所在的组织
        List<String> cnoList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            cnoList.add(mainSubDto.getMeterCno());
            tempSet.add(mainSubDto.getOrgNo());
        }

        // 查询总表所在组织的所有父组织
        Set<Long> all = redisService.queryParentAndCurrentOrgNoList(tempSet);
        List<OrgCacheVo> orgCacheVos = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVos);

        // 将设备表转换成前端需要的节点
        List<MainSubTreeInfo> deviceTreeList = this.convertMainSubByDeviceInfo(mainSubDtos,0);

        // 合并组织节点和设备表节点
        orgTreeList.addAll(deviceTreeList);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    /**
     * 查询设备总表信息新
     * @param userId
     * @param deviceType
     * @param onlineStatus
     * @return
     */
    private List<MainSubDto> queryDeviceMainSubDto(Integer userId, String deviceType,Integer onlineStatus) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        // 查询总表信息
        List<Long> list = Lists.newArrayList(dataOrgNos);
        List<MainSubDto> mainSubDtos = meterRelationService.queryDeviceMainSubTree(deviceType,onlineStatus,list);
        if (CollectionUtils.isEmpty(mainSubDtos)) {
            return mainSubDtos;
        }

        Set<String> mainCnoSet = Sets.newHashSet();   // 需要筛选出所有的总表
        Map<String,Boolean> map = Maps.newHashMap();  // 记录总表是否有孩子节点
        for (MainSubDto mainSubDto : mainSubDtos) {
            if ("0".equals(mainSubDto.getpMeterCno())) {
                String meterCno = mainSubDto.getMeterCno();
                mainCnoSet.add(meterCno);
                map.put(meterCno,Boolean.FALSE);
            } else {
                String level = mainSubDto.getLevel();
                String tempStr = level.substring(1);
                String[] split = tempStr.split("/");
                mainCnoSet.add(split[0]);
                map.put(split[0],Boolean.TRUE);
            }
        }
        if (mainCnoSet.size() > 500) {
            throw new BusinessException("总表信息超过500条，请缩小查询条件");
        }

        // 查询总表信息
        mainSubDtos = this.constructMainSubDto(mainCnoSet,map,0);
        return mainSubDtos;
    }

    /**
     * 重点用户查询总表信息
     * @param userId
     * @param deviceType
     * @return
     */
    private List<MainSubDto> queryMainSubDto4Imp(Integer userId, String deviceType) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        // 查询总表信息
        List<Long> list = Lists.newArrayList(dataOrgNos);
        List<MainSubDto> mainSubDtos = meterRelationService.queryMeterRelation4Imp(deviceType, list);
        if (CollectionUtils.isEmpty(mainSubDtos)) {
            return mainSubDtos;
        }

        Set<String> mainSet = Sets.newHashSet();        // 记录总表
        Map<String,Boolean> map = Maps.newHashMap();    // 记录总表是否存在孩子节点
        for (MainSubDto mainSubDto : mainSubDtos) {
            String meterCno = mainSubDto.getMeterCno();
            if (!map.containsKey(meterCno)) {
                // 默认都不存在孩子节点
                map.put(meterCno,Boolean.FALSE);
            }

            String pMeterCno = mainSubDto.getpMeterCno();
            if ("0".equals(pMeterCno)) {
                mainSet.add(meterCno);
            } else {
                String level = mainSubDto.getLevel();
                String tempStr = level.substring(1);
                String[] split = tempStr.split("/");
                String cno = split[0];
                mainSet.add(cno);
                map.put(cno,Boolean.TRUE);
            }
        }

        if (mainSet.size() > 500) {
            // 最多只返回500条总表信息
            List<String> mainCnoList = Lists.newArrayList(mainSet);
            List<String> newList = mainCnoList.subList(0, 500);
            Set<String> newSet = Sets.newHashSet(newList);
            mainSubDtos = this.constructMainSubDto(newSet, map,1);
        } else {
            mainSubDtos = this.constructMainSubDto(mainSet, map,1);
        }

        return mainSubDtos;
    }

    /**
     * 设备列表树，查询其他设备（集中器，采集器，转换器）
     * @param userId
     * @param deviceType
     * @return
     */
    @Override
    public List<MainSubTreeInfo> queryMainSubTree4Ohter(Integer userId, String deviceType,String deviceNo,Integer onlineStatus) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        List<Long> orgNoList = Lists.newArrayList(dataOrgNos);
        List<OtherMainSubDto> otherMainSubDtos = deviceInfoService.queryOtherMainSubTree(deviceType,deviceNo,onlineStatus,orgNoList);
        Set<Long> orgNoSet = Sets.newHashSet();
        List<MainSubTreeInfo> deviceTree = Lists.newArrayList();
        for (OtherMainSubDto dto : otherMainSubDtos) {
            orgNoSet.add(dto.getOrgNo());
            MainSubTreeInfo info = new MainSubTreeInfo();
            info.setHasChild(false);
            info.setpNodeId(String.valueOf(dto.getOrgNo()));
            info.setNodeName(dto.getDeviceNo());
            info.setNodeType(2);
            info.setNodeId(dto.getCno());
            deviceTree.add(info);
        }

        // 查询总表所在组织的所有父组织
        Set<Long> all = redisService.queryParentAndCurrentOrgNoList(orgNoSet);
        List<OrgCacheVo> orgCacheVoList = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVoList);

        // 合并
        orgTreeList.addAll(deviceTree);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    private List<MainSubDto> queryMainSubDto4CommonUser(Integer userId, String deviceType) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        // 查询总表信息,最多返回500条
        List<Long> list = Lists.newArrayList(dataOrgNos);
        List<MainSubDto> mainSubDtos = meterRelationService.queryMainSubTree4CommonUser(deviceType, list);
        if (CollectionUtils.isEmpty(mainSubDtos)) {
            return mainSubDtos;
        }

        Set<String> customerNoSet = Sets.newHashSet();
        List<String> cnoList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            customerNoSet.add(mainSubDto.getCustomerNo());
            cnoList.add(mainSubDto.getMeterCno());
        }

        // 查询用户信息
        Map<String, CustomerCacheVo> customerCacheMap = redisService.queryCustomerCacheMap(customerNoSet);

        // 设置用户名称
        for (MainSubDto mainSubDto : mainSubDtos) {
            CustomerCacheVo cacheVo = customerCacheMap.get(mainSubDto.getCustomerNo());
            mainSubDto.setCustomerName(cacheVo.getCustomerName());
        }

        // 查询总表是否存在子表
        List<MeterRelation> childNode = meterRelationService.queryChildNode(cnoList);

        // 分组
        ImmutableListMultimap<String, MeterRelation> childNodeMap = Multimaps.index(childNode, new Function<MeterRelation, String>() {
            @Nullable
            @Override
            public String apply(@Nullable MeterRelation meterRelation) {
                return meterRelation.getpMeterCno();
            }
        });

        // 设置是否有孩子节点
        for (MainSubDto mainSubDto : mainSubDtos) {
            String meterCno = mainSubDto.getMeterCno();
            ImmutableList<MeterRelation> meterRelations = childNodeMap.get(meterCno);
            if (CollectionUtils.isEmpty(meterRelations)) {
                mainSubDto.setHasChild(Boolean.FALSE);
            } else {
                mainSubDto.setHasChild(Boolean.TRUE);
            }
        }
        return mainSubDtos;
    }


    @Override
    public List<MainSubDto> queryMainSubDto4Common(Integer userId, String deviceType, Integer onlineStatus) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        // 查询总表信息
        List<Long> list = Lists.newArrayList(dataOrgNos);
        List<MainSubDto> mainSubDtos = meterRelationService.queryMainDeviceList(deviceType, onlineStatus, list);
        if (CollectionUtils.isEmpty(mainSubDtos)) {
            return mainSubDtos;
        }
        if (onlineStatus != null) {
            // 需要筛选出所有的总表
            Set<String> mainCnoSet = Sets.newHashSet();
            Map<String,Boolean> map = Maps.newHashMap();  // 记录总表是否有孩子节点
            for (MainSubDto mainSubDto : mainSubDtos) {
                if ("0".equals(mainSubDto.getpMeterCno())) {
                    String meterCno = mainSubDto.getMeterCno();
                    mainCnoSet.add(meterCno);
                    map.put(meterCno,Boolean.FALSE);
                } else {
                    String level = mainSubDto.getLevel();
                    String tempStr = level.substring(1);
                    String[] split = tempStr.split("/");
                    mainCnoSet.add(split[0]);
                    map.put(split[0],Boolean.TRUE);
                }
            }
            if (mainCnoSet.size() > 500) {
                throw new BusinessException("总表信息超过500条，请缩小查询条件");
            }

            // 查询总表信息
            mainSubDtos = this.constructMainSubDto(mainCnoSet,map,0);

            // 查询总表在线状态
            List<String> cnoList = Lists.newArrayList();
            for (MainSubDto mainSubDto : mainSubDtos) {
                cnoList.add(mainSubDto.getMeterCno());
            }
            Map<String, OnlineStatus> onlineStatusMap = deviceInfoService.queryMainSubOnlineStatus(cnoList);

            for (MainSubDto mainSubDto : mainSubDtos) {
                OnlineStatus status = onlineStatusMap.get(mainSubDto.getMeterCno());
                mainSubDto.setOnlineStatus(status.getOnlineStatus());
            }
        } else {
            Set<String> customerNoSet = Sets.newHashSet();
            List<String> cnoList = Lists.newArrayList();
            for (MainSubDto mainSubDto : mainSubDtos) {
                customerNoSet.add(mainSubDto.getCustomerNo());
                cnoList.add(mainSubDto.getMeterCno());
            }

            // 查询用户信息
            Map<String, CustomerCacheVo> customerCacheMap = redisService.queryCustomerCacheMap(customerNoSet);

            // 设置用户名称
            for (MainSubDto mainSubDto : mainSubDtos) {
                CustomerCacheVo cacheVo = customerCacheMap.get(mainSubDto.getCustomerNo());
                mainSubDto.setCustomerName(cacheVo.getCustomerName());
            }

            // 查询总表是否存在子表
            List<MeterRelation> childNode = meterRelationService.queryChildNode(cnoList);

            // 分组
            ImmutableListMultimap<String, MeterRelation> childNodeMap = Multimaps.index(childNode, new Function<MeterRelation, String>() {
                @Nullable
                @Override
                public String apply(@Nullable MeterRelation meterRelation) {
                    return meterRelation.getpMeterCno();
                }
            });

            // 设置是否有孩子节点
            for (MainSubDto mainSubDto : mainSubDtos) {
                String meterCno = mainSubDto.getMeterCno();
                ImmutableList<MeterRelation> meterRelations = childNodeMap.get(meterCno);
                if (CollectionUtils.isEmpty(meterRelations)) {
                    mainSubDto.setHasChild(Boolean.FALSE);
                } else {
                    mainSubDto.setHasChild(Boolean.TRUE);
                }
            }
        }
        return mainSubDtos;
    }

    /**
     * 构造总表相关vo
     * @param mainCnoSet
     * @return
     */
    private List<MainSubDto> constructMainSubDto(Set<String> mainCnoSet,Map<String,Boolean> map, Integer isImportant) {
        List<String> cnoList = Lists.newArrayList(mainCnoSet);
        // 查询设备关系缓存
        Map<String, DeviceRelationCacheVo> deviceRelationMap = redisService.queryDeviceRelationMap(cnoList);

        // 查询设备信息缓存
        Map<String, DeviceCacheVo> deviceCacheMap = redisService.queryDeviceCacheMap(cnoList);
        Set<String> relyCnoSet = Sets.newHashSet();
        for (Map.Entry<String, DeviceCacheVo> entry : deviceCacheMap.entrySet()) {
            DeviceCacheVo value = entry.getValue();
            relyCnoSet.add(value.getRelyCno());
        }

        // 查询依赖设备状态
        Map<String, DeviceStateCacheVo> deviceStateMap = redisService.queryDeviceStateMap(relyCnoSet);

        Set<String> customerNoSet = Sets.newHashSet();
        for (Map.Entry<String, DeviceRelationCacheVo> entry : deviceRelationMap.entrySet()) {
            DeviceRelationCacheVo value = entry.getValue();
            customerNoSet.add(value.getCustomerNo());
        }

        // 查询用户信息缓存
        Map<String, CustomerCacheVo> customerCacheMap = redisService.queryCustomerCacheMap(customerNoSet);

        List<MainSubDto> dataList = Lists.newArrayList();
        for (Map.Entry<String, DeviceRelationCacheVo> entry : deviceRelationMap.entrySet()) {
            DeviceRelationCacheVo value = entry.getValue();
            MainSubDto dto = new MainSubDto();
            BeanUtils.copyProperties(value,dto);
            String cno = entry.getKey();
            // 设置设备编号,组织编号
            DeviceCacheVo deviceCacheVo = deviceCacheMap.get(cno);
            dto.setDeviceNo(deviceCacheVo.getDeviceNo());
            dto.setOrgNo(deviceCacheVo.getOrgNo());

            // 设置设备在线状态
            String relyCno = deviceCacheVo.getRelyCno();
            DeviceStateCacheVo cacheVo = deviceStateMap.get(relyCno);
            dto.setOnlineStatus(cacheVo.getIsOnline());

            // 设置是否重点用户表
            if (isImportant == 1) {
                dto.setIsImportant(deviceCacheVo.getIsImportant());
            }

            // 设置用户名称
            String customerNo = value.getCustomerNo();
            CustomerCacheVo customerCacheVo = customerCacheMap.get(customerNo);
            dto.setCustomerName(customerCacheVo.getCustomerName());

            // 设置总表是否存在孩子节点
            Boolean flag = map.get(cno);
            dto.setHasChild(flag);
            dataList.add(dto);
        }

        return dataList;
    }

    @Override
    public List<MainSubTreeInfo> queryNextNode(String nodeId, Integer isImportant,Integer onlineStatus) {
        List<MeterRelation> meterRelations;

        if (isImportant == 1) {
            // 查询nodeId节点下所有重点用户表
            meterRelations = meterRelationService.queryNextNodeImpDevice(nodeId);
        } else {
            if (onlineStatus == null) {
                // 直接查询下一级节点
                meterRelations = meterRelationService.queryChildNodeByParentCno(nodeId);
            } else {
                // 需要查询该节点的所有下级节点，满足在线状态条件的
                meterRelations = meterRelationService.queryChildNode(nodeId,onlineStatus);
            }
        }
        List<MainSubTreeInfo> treeInfoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(meterRelations)) {
            return treeInfoList;
        }

        Set<String> subSet = Sets.newHashSet();     // 存放nodeId的下一级节点
        Map<String,Boolean> map = new HashMap<>();  // 设备是否有孩子节点
        if (isImportant == 1) {
            // 是否有孩子节点
            for (MeterRelation meterRelation : meterRelations) {
                String level = meterRelation.getLevel();
                int i = level.indexOf("/" + nodeId);
                String tempStr = level.substring(i + 32);
                String[] split = tempStr.split("/");
                String subCno = split[0];
                subSet.add(subCno);
                int length = split.length;
                if (length == 1) {
                    map.put(subCno,Boolean.FALSE);
                } else if(length > 1) {
                    // 大于1
                    map.put(subCno,Boolean.TRUE);
                }
            }
        } else {
            if (onlineStatus == null) {
                for (MeterRelation meterRelation : meterRelations) {
                    subSet.add(meterRelation.getMeterCno());
                }

                // 查询子节点的下一级节点
                Map<String, Boolean> deviceChildrenMap = redisService.queryDeviceChildren(subSet);
                map.putAll(deviceChildrenMap);
            } else {
                // 是否有孩子节点
                for (MeterRelation meterRelation : meterRelations) {
                    String level = meterRelation.getLevel();
                    int i = level.indexOf("/" + nodeId);
                    String tempStr = level.substring(i + 32);
                    String[] split = tempStr.split("/");
                    String subCno = split[0];
                    subSet.add(subCno);
                    int length = split.length;
                    if (length == 1) {
                        map.put(subCno,Boolean.FALSE);
                    } else if(length > 1) {
                        // 大于1
                        map.put(subCno,Boolean.TRUE);
                    }
                }
            }
        }

        // 查询子节点
        List<String> cnoList = Lists.newArrayList(subSet);
        Map<String, DeviceRelationCacheVo> relationCacheVoMap = redisService.queryDeviceRelationMap(cnoList);
        Set<String> customerNoSet = Sets.newHashSet();
        for (Map.Entry<String, DeviceRelationCacheVo> entry : relationCacheVoMap.entrySet()) {
            DeviceRelationCacheVo value = entry.getValue();
            customerNoSet.add(value.getCustomerNo());
        }

        // 批量查询客户信息缓存
        Map<String, CustomerCacheVo> customerCacheMap = redisService.queryCustomerCacheMap(customerNoSet);

        // 批量查询设备信息缓存
        Map<String, DeviceCacheVo> deviceCacheMap = redisService.queryDeviceCacheMap(cnoList);

        // 查询子节点在线状态
        Set<String> relyCnoSet = Sets.newHashSet();
        for (Map.Entry<String, DeviceCacheVo> entry : deviceCacheMap.entrySet()) {
            DeviceCacheVo value = entry.getValue();
            relyCnoSet.add(value.getRelyCno());
        }

        // 批量查询依赖设备状态
        List<String> relyCnos = Lists.newArrayList(relyCnoSet);
        Map<String, Integer> stateMap = deviceInfoDeviceStateService.queryDeviceStateMap(relyCnos);

        // 查询设备相关信息
        List<MainSubDto> dataList = Lists.newArrayList();
        for (Map.Entry<String, DeviceRelationCacheVo> entry : relationCacheVoMap.entrySet()) {
            DeviceRelationCacheVo value = entry.getValue();
            String customerNo = value.getCustomerNo();
            MainSubDto dto = new MainSubDto();
            dto.setCustomerNo(customerNo);
            // 设置用户名称
            CustomerCacheVo customerCacheVo = customerCacheMap.get(customerNo);
            dto.setCustomerName(customerCacheVo.getCustomerName());

            // 设置设备相关信息
            String meterCno = entry.getKey();
            DeviceCacheVo deviceCacheVo = deviceCacheMap.get(meterCno);
            dto.setDeviceNo(deviceCacheVo.getDeviceNo());
            dto.setMeterCno(meterCno);

            if (isImportant == 1) {
                // 设置是否重点表
                dto.setIsImportant(deviceCacheVo.getIsImportant());
            }

            // 设置是否在线
            String relyCno = deviceCacheVo.getRelyCno();
            Integer integer = stateMap.get(relyCno);
            dto.setOnlineStatus(integer);

            // 设置是否存在孩子节点
            Boolean flag = map.get(meterCno);
            dto.setHasChild(flag);
            dto.setpMeterCno(nodeId);
            dto.setOrgNo(deviceCacheVo.getOrgNo());
            dataList.add(dto);
        }

        // 转换成前端需要的节点
        treeInfoList = this.convertMainSubByDeviceInfo(dataList,isImportant);
        // 直接返回，不用再递归组织成树结构了
        return treeInfoList;
    }

    @Override
    public List<MainSubTreeInfo> fuzzyQueryTree(FuzzyQueryTreeVo queryTreeVo) throws BusinessException {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(queryTreeVo.getUserId());

        List<Long> orgNoList = Lists.newArrayList(dataOrgNos);
        String deviceType = queryTreeVo.getDeviceType();
        Integer isImportant = queryTreeVo.getIsImportant();
        String deviceNo = queryTreeVo.getDeviceNo();
        Integer onlineStatus = queryTreeVo.getOnlineStatus();
        List<MainSubDto> mainSubDtos;
        if (isImportant == 1) {
            mainSubDtos = meterRelationService.fuzzyQueryTree4Imp(deviceType, deviceNo, orgNoList);
        } else {
            mainSubDtos = meterRelationService.fuzzyQueryTree(deviceType,deviceNo ,onlineStatus, orgNoList);
        }

        List<MainSubTreeInfo> dataList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(mainSubDtos)) {
            return dataList;
        }
        int size = mainSubDtos.size();
        if (size > 500) {
            throw new BusinessException("数据量超过500条,请缩小查询条件");
        }

        Set<String> totalSet = Sets.newHashSet();   // 记录本次模糊查询记录中，整个树相关的节点cno集合
        Set<Long> orgNoSet = Sets.newHashSet();     // 记录本次模糊查询记录中，涉及到的组织机构
        Map<String,Boolean> map = Maps.newHashMap();// 表是否存在孩子节点
        for (MainSubDto mainSubDto : mainSubDtos) {
            orgNoSet.add(mainSubDto.getOrgNo());
            String level = mainSubDto.getLevel();

            // 去掉第一个斜杠
            String tempStr = level.substring(1);
            String[] split = tempStr.split("/");
            int length = split.length;
            for (int i = 0; i < length; i++) {
                totalSet.add(split[i]);
                if (i != length -1) {
                    map.put(split[i],Boolean.TRUE);
                }
            }
        }

        // 需要将当前组织所在的父组织都查出来
        Set<Long> parentOrgSet = redisService.queryParentOrgNo(orgNoSet);

        // 合并所有组织
        Set<Long> all = Sets.newHashSet();
        all.addAll(orgNoSet);
        all.addAll(parentOrgSet);

        // 批量查询所有组织信息
        List<OrgCacheVo> orgCacheVos = redisService.queryOrgCacheList(all);

        // 转换成前端需要的值
        List<MainSubTreeInfo> orgTreeList = this.convertMainSubByOrgInfo(orgCacheVos);

        // 批量查询设备关联表缓存信息
        List<String> cnoList = Lists.newArrayList(totalSet);
        Map<String, DeviceRelationCacheVo> deviceRelationMap = redisService.queryDeviceRelationMap(cnoList);

        // 批量查询客户缓存信息
        Set<String> customerNoSet = Sets.newHashSet();
        Collection<DeviceRelationCacheVo> values = deviceRelationMap.values();
        for (DeviceRelationCacheVo cacheVo : values) {
            customerNoSet.add(cacheVo.getCustomerNo());
        }
        Map<String, CustomerCacheVo> customerCacheMap = redisService.queryCustomerCacheMap(customerNoSet);

        // 批量查询设备缓存信息
        Map<String, DeviceCacheVo> deviceCacheVoMap = redisService.queryDeviceCacheMap(cnoList);

        Map<String, DeviceStateCacheVo> deviceStateMap = null;
        if (onlineStatus != null) {
            // 查询设备在线状态
            Set<String> relyCnoSet = Sets.newHashSet();
            for (Map.Entry<String, DeviceCacheVo> entry : deviceCacheVoMap.entrySet()) {
                DeviceCacheVo value = entry.getValue();
                relyCnoSet.add(value.getRelyCno());
            }
            deviceStateMap = redisService.queryDeviceStateMap(relyCnoSet);
        }

        // 转换成前端要的树形节点
        List<MainSubTreeInfo> deviceTreeList = Lists.newArrayList();
        for (Map.Entry<String, DeviceRelationCacheVo> entry : deviceRelationMap.entrySet()) {
            String cno = entry.getKey();
            MainSubTreeInfo info = new MainSubTreeInfo();
            DeviceCacheVo deviceCacheVo = deviceCacheVoMap.get(cno);
            info.setNodeId(cno);
            info.setNodeType(2);
            DeviceRelationCacheVo value = entry.getValue();
            CustomerCacheVo customerCacheVo = customerCacheMap.get(value.getCustomerNo());
            String nodeName = deviceCacheVo.getDeviceNo() + "(" + customerCacheVo.getCustomerName() + ")";
            info.setNodeName(nodeName);
            String parentCno = value.getpMeterCno();
            if ("0".equals(parentCno)) {
                info.setpNodeId(String.valueOf(deviceCacheVo.getOrgNo()));
            } else {
                info.setpNodeId(parentCno);
            }

            // 对节点的hasChild属性进行设置
            Boolean flag = map.get(cno);
            if (flag != null) {
                info.setHasChild(flag);
            } else {
                info.setHasChild(false);
            }

            // 设置节点的在线状态
            if (onlineStatus != null) {
                String relyCno = deviceCacheVo.getRelyCno();
                DeviceStateCacheVo cacheVo = deviceStateMap.get(relyCno);
                info.setOnlineStatus(cacheVo.getIsOnline());
            }
            deviceTreeList.add(info);
        }

        // 合并组织跟设备树节点
        orgTreeList.addAll(deviceTreeList);

        // 转换成树形结构
        List<MainSubTreeInfo> treeList = TreeParser2.getTreeList("0", orgTreeList);
        return treeList;
    }

    @Override
    public Integer queryTotalDevice(Integer userId, String deviceType) {
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);
        List<Long> dataOrgList = Lists.newArrayList(dataOrgNos);
        Integer integer = deviceInfoService.queryTotalNum(deviceType, dataOrgList);
        return integer;
    }

    @Override
    public List<OrgTreeNode> queryOrgTreeByOrgNo(Long orgNo) {
        List<OrgTreeNode> dataList = Lists.newArrayList();
        List<OrgCacheVo> orgCacheVoList = redisService.queryOrgTree(orgNo);
        for (OrgCacheVo orgCacheVo : orgCacheVoList) {
            OrgTreeNode treeNode = new OrgTreeNode();
            BeanUtils.copyProperties(orgCacheVo,treeNode);
            dataList.add(treeNode);
        }

        // 转换成树形结构,并设置是否叶子节点
        List<OrgTreeNode> treeList = TreeParser4.getTreeList(0L, dataList);
        return treeList;
    }

    private List<MainSubTreeInfo> convertMainSubByOrgInfo(List<OrgCacheVo> orgs) {
        List<MainSubTreeInfo> dataList = Lists.newArrayList();
        for (OrgCacheVo org : orgs) {
            MainSubTreeInfo info = new MainSubTreeInfo();
            info.setNodeName(org.getOrgName());
            info.setNodeType(1);
            info.setpNodeId(String.valueOf(org.getpOrgNo()));
            info.setNodeId(String.valueOf(org.getOrgNo()));
            dataList.add(info);
        }
        return dataList;
    }

    private List<MainSubTreeInfo> convertMainSubByDeviceInfo(List<MainSubDto> mainSubDtos, Integer isImportant) {
        List<MainSubTreeInfo> dataList = Lists.newArrayList();
        for (MainSubDto mainSubDto : mainSubDtos) {
            MainSubTreeInfo info = new MainSubTreeInfo();
            info.setNodeName(mainSubDto.getDeviceNo() + "(" + mainSubDto.getCustomerName() + ")");
            Boolean hasChild = mainSubDto.getHasChild();
            info.setHasChild(hasChild);
            info.setNodeType(2);
            // 设置在线状态
            info.setOnlineStatus(mainSubDto.getOnlineStatus());
            // 设置父节点
            String pMeterCno = mainSubDto.getpMeterCno();
            if ("0".equals(pMeterCno)) {
                info.setpNodeId(String.valueOf(mainSubDto.getOrgNo()));
            } else {
                info.setpNodeId(mainSubDto.getpMeterCno());
            }

            if (isImportant == 1) {
                info.setIsImportant(mainSubDto.getIsImportant());
            }
            // 设置重点表
            info.setNodeId(mainSubDto.getMeterCno());
            dataList.add(info);
        }
        return dataList;
    }


    private List<BuildingTreeInfo> convert(List<OrgCacheVo> orgs) {
        List<BuildingTreeInfo> dataList = Lists.newArrayList();
        for (OrgCacheVo org : orgs) {
            BuildingTreeInfo  info = new BuildingTreeInfo();
            info.setNodeNo(org.getOrgNo());
            info.setpNodeNo(org.getpOrgNo());
            info.setNodeName(org.getOrgName());
            info.setNodeType(1);
            dataList.add(info);
        }
        return dataList;
    }
}
