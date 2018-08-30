package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeeOnOffMapper;
import cn.com.cdboost.collect.dto.FeeOnOffDetailInfo;
import cn.com.cdboost.collect.dto.FeeOnOffInfo;
import cn.com.cdboost.collect.dto.FeeOnOffStatusListInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.InstructCode;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN08Object;
import com.example.clienttest.client.ResultInfo;
import com.example.clienttest.clientfuture.ClientManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 远程通断记录服务接口实现类
 */
@Service("feeOnOffService")
public class FeeOnOffServiceImpl extends BaseServiceImpl<FeeOnOff> implements FeeOnOffService {
    private static final Logger logger = LoggerFactory.getLogger(FeeOnOffServiceImpl.class);

    @Autowired
    private FeeOnOffMapper feeOnOffMapper;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;

    @Override
    public List<FeeOnOffInfo> query(CstOnOffGetQueryVo queryVo) {
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

        // 用户户号
        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            queryVo.setMeterUserNo("");
        }

        // 读取采集数据
        List<FeeOnOffInfo> lists = feeOnOffMapper.query(queryVo);
        return lists;
    }
    @Override
    public List<FeeOnOffInfo> queryNew(CstOnOffGetQueryNewVo queryVo) {
        // 读取采集数据
        List<FeeOnOffInfo> lists = feeOnOffMapper.queryNew(queryVo);
        return lists;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Integer onOff(FeeOnOffParam param) throws BusinessException{
        List<MeterReadQueue> list = Lists.newArrayList();

        String guid = param.getGuid();
        int onOff = param.getOnOff();
        String remark = param.getReason();
        Integer userId = param.getUserId();
        Date date = param.getDate();
        String sessionId = param.getSessionId();

        List<OnOffMeterVo> meters = param.getMeters();
        for (OnOffMeterVo meterVo : meters) {
            FeeOnOff feeOnOff = new FeeOnOff();
            feeOnOff.setCustomerNo(meterVo.getCustomerNo());
            String deviceCno = meterVo.getDeviceCno();
            feeOnOff.setCno(deviceCno);
            feeOnOff.setOnOff(onOff);
            feeOnOff.setCreateUserId(Long.valueOf(userId));
            feeOnOff.setCreateTime(new Date());
            feeOnOff.setRemark(remark);
            feeOnOff.setQueueGuid(guid);

            // 插入通断记录
            feeOnOffMapper.insertSelective(feeOnOff);

            // 先查询设备信息
            DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(deviceCno);
            if (deviceInfo == null) {
                throw new BusinessException("设备信息不存在");
            }
            // 更新设备信息中通断记录
            // 待更新的对象
            DeviceInfo updateInfo = new DeviceInfo();
            updateInfo.setId(deviceInfo.getId());
            updateInfo.setOnOffOptUser(Long.valueOf(userId));
            updateInfo.setLastOnOffTime(new Date());
            deviceInfoService.updateByPrimaryKeySelective(updateInfo);

            // 实时采集对象
            MeterReadQueue meterReadQueue = new MeterReadQueue();
            String jzqCno = meterVo.getJzqCno();
            meterReadQueue.setJzqCno(jzqCno);
            meterReadQueue.setMeterCno(deviceCno);
            meterReadQueue.setCreateUserId(Long.valueOf(userId));
            meterReadQueue.setQueueGuid(guid);
            meterReadQueue.setJzqNo(jzqCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setMeterNo(deviceCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setCreateTime(date);

            DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(deviceCno);
            if(deviceMeterParam.getCommPort() == 32){
                if(deviceMeterParam.getMoteType().equals("C")){
                    meterReadQueue.setOverTime(20);
                }else {
                    meterReadQueue.setOverTime(10);
                }
            }else {
                meterReadQueue.setOverTime(60);
            }

            MeterReadQueue queueParam = new MeterReadQueue();
            queueParam.setMeterCno(deviceCno);
            queueParam.setQueueGuid(guid);
            MeterReadQueue queue = meterReadQueueService.selectOne(queueParam);
            if (queue != null) {
                //判断guid是否存在
                MeterReadQueue updateParam = new MeterReadQueue();
                updateParam.setId(queue.getId());
                updateParam.setReadStatus(0);
                updateParam.setUpdateTime(new Date());
                updateParam.setCreateTime(date);
                meterReadQueueService.updateByPrimaryKeySelective(updateParam);
            } else {
                meterReadQueueService.insertSelective(meterReadQueue);
            }
            list.add(meterReadQueue);
        }

        instructService.clearResultInfo(guid);
        int sendResult = instructService.SendOnOffInstructList(list,String.valueOf(onOff),sessionId);
        logger.info("第一次采集指令sendResult：" + sendResult);
        if(sendResult != InstructCode.Success.getValue()){
            for (MeterReadQueue meterReadQueue : list) {
                MeterReadQueue updateParam = new MeterReadQueue();
                updateParam.setId(meterReadQueue.getId());
                updateParam.setReadStatus(2);
                updateParam.setUpdateTime(new Date());
                updateParam.setCreateTime(date);
                meterReadQueueService.updateByPrimaryKeySelective(updateParam);
            }
        }
        return sendResult;
    }

    @Override
    public FeeOnOffStatusListInfo queryStatus(String guid,String deviceType,long userId,String createTime) {
        FeeOnOffStatusListInfo info = new FeeOnOffStatusListInfo();
        List<String> cnoList = Lists.newArrayList();

        ResultInfo resultInfo = ClientManager.getMulReadMeterStatus(guid);
        logger.info("远程通断状态查询结果：" + JSON.toJSONString(resultInfo));

        boolean isUpdate = resultInfo.isUpdate();
        info.setStatus(resultInfo.getStatus());
        info.setIsUpdate(isUpdate);
        info.setTotal(resultInfo.getTotal());
        info.setDealNum(resultInfo.getDealNum());
        info.setUndealNum(resultInfo.getUndealNum());
        info.setSuccessfulNum(resultInfo.getSuccessfulNum());
        info.setFailNum(resultInfo.getFailAFN08List().size());

        if(resultInfo.getStatus() != 101){
            instructService.clearResultInfo(guid);
        }
        List<FeeOnOffInfo> list;
        //if(isUpdate){
            list = this.queryByGuid(guid,1,createTime);
            info.setSuccessList(list);

            ArrayList<AFN08Object> afn08ObjectList = resultInfo.getFailAFN08List();
            if(!CollectionUtils.isEmpty(afn08ObjectList)){
                for (AFN08Object afn08Object:afn08ObjectList) {
                    cnoList.add(CNoUtil.CreateCNo(StringUtil.getDeviceType(afn08Object.getMoteType()),afn08Object.getDbdz()));
                }
            }
            info.setCnoList(cnoList);
        //}
        return info;
    }

    @Override
    public List<FeeOnOffInfo> queryResult(CstOnOffOptRstGetQueryVo queryVo) {
        String guid = queryVo.getGuid();
        if (guid == null) {
            queryVo.setGuid("");
        }

        String deviceType = queryVo.getDeviceType();
        if (deviceType == null) {
            queryVo.setDeviceType("");
        }

        // 1标识成功 2标识失败
        String dataFlag = queryVo.getDataFlag();
        if (dataFlag == null) {
            queryVo.setDataFlag("1");
        }

        String date = queryVo.getDate();
        if (date == null) {
            queryVo.setDate("");
        }

        Integer pageNumber = queryVo.getPageNumber();
        if (pageNumber == null) {
            queryVo.setPageNumber(1);
        }

        Integer pageSize = queryVo.getPageSize();
        if (pageSize == null) {
            queryVo.setPageSize(20);
        }

        List<FeeOnOffInfo> lists = feeOnOffMapper.queryResult(queryVo);
        return lists;
    }

    @Override
    public List<FeeOnOffInfo> queryHistory(CstOnOffByNoGetQueryVo queryVo) {
        List<FeeOnOffInfo> lists = feeOnOffMapper.queryHistory(queryVo);
        return lists;

    }

    @Override
    public List<FeeOnOffDetailInfo> queryHistory4Single(OnOffQueryVo queryVo) {
        String customerNo = queryVo.getCustomerNo();
        // 查用户档案
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);

        // 查用户档案设备关联信息
        CustomerDevMap devMapParam = new CustomerDevMap();
        devMapParam.setCustomerNo(customerNo);
        devMapParam.setDeviceType(queryVo.getDeviceType());
        String meterUserNo = queryVo.getMeterUserNo();
        if (!StringUtils.isEmpty(meterUserNo)) {
            devMapParam.setMeterUserNo(meterUserNo);
        }
        List<CustomerDevMap> devMaps = customerDevMapService.select(devMapParam);
        List<String> cnoList = Lists.newArrayList();
        for (CustomerDevMap devMap : devMaps) {
            cnoList.add(devMap.getCno());
        }

        // 查询通断记录
        Condition condition = new Condition(FeeOnOff.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andIn("cno",cnoList);
        criteria.andBetween("createTime",queryVo.getStartTime(),queryVo.getEndTime());
        // 设置分页信息
        PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize(),"id desc");
        List<FeeOnOff> feeOnOffs = feeOnOffMapper.selectByCondition(condition);
        // 设置分页总条数
        PageInfo<FeeOnOff> pageInfo = new PageInfo<>(feeOnOffs);
        queryVo.setTotal(pageInfo.getTotal());

        List<FeeOnOffDetailInfo> dataList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(feeOnOffs)) {
            Set<Integer> userIdSet = Sets.newHashSet();
            for (FeeOnOff feeOnOff : feeOnOffs) {
                Long createUserId = feeOnOff.getCreateUserId();
                userIdSet.add(createUserId.intValue());
            }

            // 批量查询用户信息
            List userIdList = Lists.newArrayList(userIdSet);
            List<User> users = userService.batchQueryByIds(userIdList);
            ImmutableMap<Integer, User> userImmutableMap = Maps.uniqueIndex(users, new Function<User, Integer>() {
                @Nullable
                @Override
                public Integer apply(@Nullable User user) {
                    return user.getId();
                }
            });

            // 设置值
            for (FeeOnOff feeOnOff : feeOnOffs) {
                FeeOnOffDetailInfo detailInfo = new FeeOnOffDetailInfo();
                detailInfo.setCustomerName(customerInfo.getCustomerName());
                detailInfo.setCustomerAddr(customerInfo.getCustomerAddr());
                String deviceNo = CNoUtil.getNo(feeOnOff.getCno());
                detailInfo.setDeviceNo(deviceNo);
                detailInfo.setOnOff(feeOnOff.getOnOff());
                detailInfo.setRemark(feeOnOff.getRemark());
                detailInfo.setCreateTime(feeOnOff.getCreateTime());
                detailInfo.setStatus(feeOnOff.getSendFlag());
                Integer userId = Integer.valueOf(feeOnOff.getCreateUserId().intValue());
                User user = new User();
                //判断是否为后台自动拉闸
                if (userId == 0){
                    user.setUserName("后台自动拉闸");
                } else {
                    user = userImmutableMap.get(userId);
                }
                detailInfo.setUserName(user.getUserName());
                dataList.add(detailInfo);
            }
        }
        return dataList;
    }

    @Override
    public boolean stopCollectList(String guid) {
        boolean result =  ClientManager.stopReadMeter(guid);
        instructService.clearResultInfo(guid);
        return result;
    }


    @Override
    public List<FeeOnOffInfo> queryByGuid(String guid,Integer status,String createTime) {
        return feeOnOffMapper.queryByGuid(guid,status,createTime);
    }
}
