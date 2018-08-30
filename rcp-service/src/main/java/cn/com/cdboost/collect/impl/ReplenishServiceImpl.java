package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.aop.SystemServiceLog;
import cn.com.cdboost.collect.dao.ReplenishMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.CustomerInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * PC端补抄管理服务接口实现类
 */
@Service("replenishService")
public class ReplenishServiceImpl implements ReplenishService {
    private static final Logger logger = LoggerFactory.getLogger(ReplenishServiceImpl.class);

    @Autowired
    private ReplenishMapper replenishMapper;
    @Autowired
    private MeterSuppQueryService meterSuppQueryService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;
    @Autowired
    private MeterCollectItemService meterCollectItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Override
    public List<MeterSuppCstValueText> queryReplenishMeter(MeterSuppCstQueryVo queryVo) {
        String deviceNo = queryVo.getDeviceNo();
        if (deviceNo == null) {
            queryVo.setDeviceNo("");
        }

        String customerName = queryVo.getCustomerName();
        if (customerName == null) {
            queryVo.setCustomerName("");
        }

        String customerAddr = queryVo.getCustomerAddr();
        if (customerAddr == null) {
            queryVo.setCustomerAddr("");
        }

        String customerContact = queryVo.getCustomerContact();
        if (customerContact == null) {
            queryVo.setCustomerContact("");
        }

        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            queryVo.setMeterUserNo("");
        }

        String dataType = queryVo.getDataType();
        if (dataType == null) {
            queryVo.setDataType("");
        }

        String collectDate = queryVo.getCollectDate();
        if (collectDate == null) {
            queryVo.setCollectDate("");
        }

        String deviceType = queryVo.getDeviceType();
        if (deviceType == null) {
            queryVo.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
        }

        List<MeterSuppCstValueText> list = Lists.newArrayList();
        List<BaseHandheldReplenishDTO> dtoList = replenishMapper.meterSuppCstSearch(queryVo);
        if(!CollectionUtils.isEmpty(dtoList)){
            for (BaseHandheldReplenishDTO dto : dtoList){
                // 设置设备编号
                dto.setDeviceNo(CNoUtil.getNo(dto.getCno()));
                MeterSuppCstValueText text = new MeterSuppCstValueText();
                text.setValue(dto);
                text.setText("用户姓名：" + dto.getCustomerName() + "|门牌编号：" + dto.getPropertyName() + "|用户地址：" + dto.getCustomerAddr() + "|表计户号：" + dto.getMeterUserNo());
                list.add(text);
            }
        }

        return list;
    }

    @Override
    @Transactional
    public void createReplenishWorkOrder(ReplenishWorkOrderStrJsonVo strJsonVo, Integer userId) throws BusinessException{
        ReplenishWorkOrderParamVo paramVo = new ReplenishWorkOrderParamVo();
        paramVo.setTaskNo(UuidUtil.getUuid());
        paramVo.setUserId(userId);
        paramVo.setStrJson(JSON.toJSONString(strJsonVo));
        replenishMapper.meterSuppTaskAdd(paramVo);
        String result = paramVo.getResult();
        if ("2".equals(result)) {
            throw new BusinessException("已经添加");
        } else if ("3".equals(result)) {
            throw new BusinessException("解析json数据失败");
        } else if ("4".equals(result)) {
            throw new BusinessException("解析meters节点失败");
        } else if ("0".equals(result)){
            throw new BusinessException("存储过程返回0,失败");
        }
    }

    @Override
    public List<WorkOrderDetialDTO> replenishWorkOrderList(MeterSuppTaskGetQueryVo queryVo) {
        String taskContent = queryVo.getTaskContent();
        if (taskContent == null) {
            queryVo.setTaskContent("");
        }

        String taskFlag = queryVo.getTaskFlag();
        if (taskFlag == null) {
            queryVo.setTaskFlag("");
        }
        List<WorkOrderDetialDTO> dtoList = replenishMapper.meterSuppTaskGet(queryVo);

        return dtoList;
    }

    @Override
    public List<ReplenishDataDTO> replenishDataDetial(MeterSuppTaskDetailQueryVo queryVo) {
        List<ReplenishDataDTO> dtoList = replenishMapper.meterSuppTaskDetail(queryVo);
        if (!CollectionUtils.isEmpty(dtoList)) {
            for (ReplenishDataDTO dto : dtoList) {
                BigDecimal balance = dto.getBalance();
                dto.setBalance(MathUtil.setPrecision(balance));

                BigDecimal payMoney = dto.getPayMoney();
                dto.setPayMoney(MathUtil.setPrecision(payMoney));

                BigDecimal pr0 = dto.getPr0();
                dto.setPr0(MathUtil.setPrecision(pr0));
            }
        }
        return dtoList;
    }

    @Override
    public List<MeterSuppQueryInfo> requestWorkOrderList(String taskNo) {
        List<MeterSuppQueryInfo> returnList = Lists.newArrayList();
        List<MeterSuppQuery> list = meterSuppQueryService.queryByTaskNo(taskNo);

        if (CollectionUtils.isEmpty(list)) {
            return returnList;
        }
        // 查询冗余字段
        Set<Integer> userIdSet = Sets.newHashSet();
        for (MeterSuppQuery query : list) {
            userIdSet.add(query.getQueryUserId());
        }

        // 用户查询
        Map<Integer,User> userMap = Maps.newHashMap();
        List<Integer> ids = Lists.newArrayList(userIdSet);
        Set<Long> orgNoSet = Sets.newHashSet();
        List<User> users = userService.batchQueryByIds(ids);
        for (User user : users) {
            userMap.put(user.getId(),user);
            orgNoSet.add(user.getOrgNo());
        }

        // 查询组织
        List<Long> orgNoList = Lists.newArrayList(orgNoSet);
        List<Org> orgs = orgService.batchQueryByOrgNos(orgNoList);
        Map<Long,Org> orgMap = Maps.newHashMap();
        for (Org org : orgs) {
            orgMap.put(org.getOrgNo(),org);
        }

        // 设置冗余字段值
        for (MeterSuppQuery query : list) {
            MeterSuppQueryInfo queryInfo = new MeterSuppQueryInfo();
            queryInfo.setQueryTime(query.getQueryTime());
            Integer queryUserId = query.getQueryUserId();
            User user = userMap.get(queryUserId);
            if (user != null) {
                queryInfo.setQueryUserName(user.getUserName());
                queryInfo.setUserMobile(user.getUserMobile());
                Long orgNo = user.getOrgNo();
                Org org = orgMap.get(orgNo);
                queryInfo.setOrgName(org.getOrgName());
            }
            returnList.add(queryInfo);
        }

        return returnList;
    }

    @Override
    @SystemServiceLog
    public List<WorkOrder> queryWorkOrder(long userId, int pageIndex, int pageSize, StringBuilder total) {
        Map<String, Object> searchMap = Maps.newHashMap();
        searchMap.put("userId",userId);
        searchMap.put("pageIndex",pageIndex);
        searchMap.put("pageSize",pageSize);
        searchMap.put("rowCount", "");
        List<WorkOrder> workOrders = replenishMapper.queryWorkOrder(searchMap);
        // 获取返回的条数
        total.append(searchMap.get("rowCount"));
        return workOrders;
    }

    @Override
    @SystemServiceLog
    public List<WorkOrderDetail> queryWorkOrderDetail(int pageIndex, int pageSize, String taskNo, StringBuilder total) {
        Map<String, Object> searchMap = Maps.newHashMap();
        searchMap.put("pageIndex",pageIndex);
        searchMap.put("pageSize",pageSize);
        searchMap.put("taskNo",taskNo);
        searchMap.put("rowCount", "");
        List<List<?>> lists = replenishMapper.queryWorkOrderDetail(searchMap);
        // 客户档案信息
        List<CustomerInfo> first = (List<CustomerInfo>)lists.get(0);

        // 已采集数据信息
        List<CollectData> second = (List<CollectData>)lists.get(1);
        ImmutableListMultimap<String, CollectData> collectDataMultMap = Multimaps.index(second, new Function<CollectData, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CollectData collectData) {
                return collectData.getCno();
            }
        });

        // 采集项信息
        List<CollectItem> third = (List<CollectItem>)lists.get(2);
        ImmutableMap<String, CollectItem> collectItemMap = Maps.uniqueIndex(third, new Function<CollectItem, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CollectItem collectDataItem) {
                return collectDataItem.getReadType();
            }
        });

        List<WorkOrderDetail> workOrderDetails = Lists.newArrayList();
        for (CustomerInfo customerInfo : first) {
            WorkOrderDetail workOrderDetail = new WorkOrderDetail();
            // 客户档案数据
            BeanUtils.copyProperties(customerInfo,workOrderDetail);
            // 构造已采集数据以及采集项信息
            String cno = customerInfo.getCno();
            ImmutableList<CollectData> collectDataList = collectDataMultMap.get(cno);
            // 记录电表上一次采集数据值，key=cno+readType，value=readeValue
            Map<String,String> map = Maps.newHashMap();
            List<CollectDataItem> collectDataItems = Lists.newArrayList();
            for (CollectData collectData : collectDataList) {
                Integer dataFlag = collectData.getDataFlag();
                if (dataFlag == 0) {
                    // 上一次采集数据
                    String key = collectData.getCno() + "_" + collectData.getReadType();
                    map.put(key,collectData.getReadValue());
                    continue;
                }
                CollectDataItem item = new CollectDataItem();
                // 复制采集数据
                BeanUtils.copyProperties(collectData,item);

                // 复制采集项数据
                String readType = collectData.getReadType();
                CollectItem collectItem = collectItemMap.get(readType);
                BeanUtils.copyProperties(collectItem,item);

                collectDataItems.add(item);
            }

            // 设置上一次采集数据值
            for (CollectDataItem dataItem : collectDataItems) {
                String key = dataItem.getCno() + "_" + dataItem.getReadType();
                String tempValue = map.get(key);
                if (tempValue != null) {
                    dataItem.setLastReadValue(tempValue);
                }
            }

            workOrderDetail.setCollectDataItems(collectDataItems);
            workOrderDetails.add(workOrderDetail);
        }

        // 获取返回的条数
        total.append(searchMap.get("rowCount"));
        return workOrderDetails;
    }

    @Override
    @SystemServiceLog
    @Transactional(propagation = Propagation.REQUIRED)
    public int uploadCollectResult(UploadCollectResult collectResult,String userId) {
        int result = -1;
        try {
            // 存放工单数据
            List<Map<String,Object>> taskList = Lists.newArrayList();
            // 存放工单设备详情
            List<Map<String,Object>> taskDetailList = Lists.newArrayList();
            // 存放设备采集数据
            List<Map<String,Object>> taskDatalList = Lists.newArrayList();

            List<WorkOrder4Upload> datas = collectResult.getDatas();
            for (WorkOrder4Upload data : datas) {
                // 构造任务工单数据
                Map<String,Object> map = Maps.newHashMap();
                map.put("taskNo",data.getTaskNo());
                map.put("deviceType",data.getDeviceType());
                map.put("taskContent",data.getTaskContent());
                map.put("startTime", DateUtil.formatDate(data.getStartTime()));
                map.put("endTime",DateUtil.formatDate(data.getEndTime()));
                map.put("meterCount",data.getMeterCount());
                map.put("flag",data.getFlag());
                taskList.add(map);

                List<WorkOrderDetail4Upload> workOrderDetails = data.getWorkOrderDetails();
                for (WorkOrderDetail4Upload detail : workOrderDetails) {
                    // 构造任务工单设备详情
                    Map<String,Object> map2 = Maps.newHashMap();
                    map2.put("customerNo",detail.getCustomerNo());
                    map2.put("taskNo",data.getTaskNo());
                    map2.put("cno",detail.getCno());
                    map2.put("suppTime",DateUtil.formatDate(detail.getSuppTime()));
                    map2.put("groupGuid",detail.getGroupGuid());
                    map2.put("collectSort",detail.getCollectSort());
                    map2.put("flag",detail.getFlag());
                    map2.put("dataSrc",detail.getDataSrc());
                    map2.put("errCode",detail.getErrCode());
                    map2.put("errInfo",detail.getErrInfo());
                    taskDetailList.add(map2);

                    String flag = detail.getFlag();
                    Integer errCode = detail.getErrCode();
                    if (errCode != 0 || "0".equals(flag)) {
                        continue;
                    }

                    List<CollectDataItem4Upload> collectDataItems = detail.getCollectDataItems();
                    for (CollectDataItem4Upload item : collectDataItems) {
                        // 构造设备采集数据
                        Map<String,Object> map3 = Maps.newHashMap();
                        map3.put("cno",detail.getCno());
                        map3.put("mrFlag",item.getMrFlag());
                        map3.put("readType",item.getReadType());
                        map3.put("readValue",item.getReadValue());
                        map3.put("groupGuid",item.getGroupGuid());
                        map3.put("collectTime",DateUtil.formatDate(item.getCollectTime()));
                        map3.put("dataSrc",detail.getDataSrc());
                        taskDatalList.add(map3);
                    }
                }
            }

            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("userId",userId);
            dataMap.put("strTaskJson", JSON.toJSONString(taskList));
            dataMap.put("strTaskdetailJson",JSON.toJSONString(taskDetailList));
            dataMap.put("strTaskdataJson",JSON.toJSONString(taskDatalList));
            dataMap.put("result", "");
            logger.info("ReplenishServiceImpl : uploadCollectResult Mapper 入参：" + JSON.toJSONString(dataMap));
            replenishMapper.uploadCollectResult(dataMap);
            result = Integer.parseInt(dataMap.get("result").toString());
        } catch (Exception e) {
            logger.error("uploadCollectResult异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    @Override
    public List<WorkOrderDetail> appCreateNewWorkOrderDetail(List<String> cnoList) {
        // 查询客户信息
        List<CustomerInfo4App> customerInfo4Apps = customerDevMapService.selectCustomerInfosByCnos(cnoList);

        // 查询设备信息
        List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
        ImmutableMap<String, DeviceInfo> deviceInfoImmutableMap = Maps.uniqueIndex(deviceInfos, new Function<DeviceInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceInfo deviceInfo) {
                return deviceInfo.getCno();
            }
        });

        // 查询电表规约
        List<DeviceMeterParam> deviceMeterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        Map<String,DeviceMeterParam> meterParamMap = Maps.newHashMap();
        Set<String> paramFlagSet = Sets.newHashSet();
        for (DeviceMeterParam deviceMeterParam : deviceMeterParams) {
            meterParamMap.put(deviceMeterParam.getCno(),deviceMeterParam);
            paramFlagSet.add(deviceMeterParam.getParamFlag());
        }

        // 批量查询设备参数配置表信息
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);

        // 查询电表采集项
        List<MeterCollectItem> collectItems = meterCollectItemService.queryByDeviceType(DeviceType.ELECTRIC_METER.getCode());
        ImmutableMap<String, MeterCollectItem> collectItemImmutableMap = Maps.uniqueIndex(collectItems, new Function<MeterCollectItem, String>() {
            @Nullable
            @Override
            public String apply(@Nullable MeterCollectItem meterCollectItem) {
                return String.valueOf(meterCollectItem.getReadType());
            }
        });

        List<WorkOrderDetail> workOrderDetails = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(customerInfo4Apps)) {
            for (CustomerInfo4App info4App : customerInfo4Apps) {
                WorkOrderDetail orderDetail = new WorkOrderDetail();
                BeanUtils.copyProperties(info4App,orderDetail);
                String cno = info4App.getCno();
                // 设置安装地址
                DeviceInfo deviceInfo = deviceInfoImmutableMap.get(cno);
                if (deviceInfo != null) {
                    orderDetail.setInstallAddr(deviceInfo.getInstallAddr());
                }

                // 设置表规约,表地址
                DeviceMeterParam deviceMeterParam = meterParamMap.get(cno);
                if (deviceMeterParam != null) {
                    DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(deviceMeterParam.getParamFlag());
                    orderDetail.setCommRule(String.valueOf(deviceMeterConfig.getCommRule()));
                    orderDetail.setCommAddr(deviceMeterParam.getCommAddr());
                }

                List<CollectDataItem> collectDataItems = Lists.newArrayList();
                for (MeterCollectItem collectItem : collectItems) {
                    CollectDataItem dataItem = new CollectDataItem();
                    int readType = collectItem.getReadType();
                    MeterCollectItem meterCollectItem = collectItemImmutableMap.get(String.valueOf(readType));
                    dataItem.setReadType(String.valueOf(meterCollectItem.getReadType()));
                    dataItem.setCollectName(meterCollectItem.getCollectName());
                    dataItem.setMrFlag(meterCollectItem.getMrFlag());
                    dataItem.setDataModel(meterCollectItem.getDataModel());
                    dataItem.setMeterType(String.valueOf(meterCollectItem.getMeterType()));
                    dataItem.setDeviceType(meterCollectItem.getDeviceType());
                    collectDataItems.add(dataItem);
                }
                orderDetail.setCollectDataItems(collectDataItems);
                workOrderDetails.add(orderDetail);
            }
        }

        return workOrderDetails;
    }

    @Override
    public List<MakeupDataDto> queryMakeupData(MakeupDataVo queryVo) {
        return replenishMapper.queryMakeupData(queryVo);
    }

    @Override
    @Transactional
    public int manualRecordData(CreateManualRecordParam param,Integer userId) {
        CreateManualRecordParamVo createManualRecordParamVo = new CreateManualRecordParamVo();
        //设置数据库查询参数
        createManualRecordParamVo.setCustomerNo(param.getCustomerNo());
        //设置传入存储过程json
        createManualRecordParamVo.setColdataJson(JSON.toJSONString(param.getManualRecordVoList()));
        createManualRecordParamVo.setUserId(userId);
        //设置后台实时算费GUID
        createManualRecordParamVo.setQueueGuid(UUID.randomUUID().toString().replace("-", ""));
        replenishMapper.manualRecordData(createManualRecordParamVo);
        return createManualRecordParamVo.getResult();
    }
}
