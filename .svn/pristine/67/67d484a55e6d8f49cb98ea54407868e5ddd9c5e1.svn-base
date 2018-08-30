package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.dto.CancelOffParam;
import cn.com.cdboost.collect.dto.PaymentParam;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.ReplenishEnum;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.DeviceMeterConfig;
import cn.com.cdboost.collect.model.DeviceMeterParam;
import cn.com.cdboost.collect.model.MeterReadQueue;
import cn.com.cdboost.collect.model.SysConfig;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DIFormatUtil;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.*;
import com.example.clienttest.clientfuture.ClientManager;
import com.google.common.base.Function;
import com.google.common.collect.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 中间键指令接口实现类
 */
@Service("instructService")
public class InstructServiceImpl implements InstructService {
    private static final Logger logger = LoggerFactory.getLogger(InstructServiceImpl.class);

    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public int Instantcalculate(CancelOffParam param) {
        logger.info("InstructServiceImpl - Instantcalculate query: param: " + JSONObject.fromObject(param));
        try {
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            AFN17Object afn17Object = new AFN17Object(
                    param.getGuid(),     // GUID
                    "17",
                    param.getJzqNo(),
                    sessionId);
            int sendState = ClientManager.sendAFN17Msg(afn17Object);
            logger.info("InstructServiceImpl - Instantcalculate 返回结果: " + sendState);
            return sendState;
        }catch (Exception e){
            throw new BusinessException("指令发送失败");
        }

    }

    @Override
    public int CancelOff(CancelOffParam param) {
        logger.info("InstructServiceImpl - CancelOff query: param: " + JSONObject.fromObject(param));
        try {
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            AFN16Object afn16Object = new AFN16Object(
                    param.getGuid(),
                    "16",
                    param.getJzqNo(),
                    param.getCustomerNo(),
                    param.getCno(),
                    sessionId);
            int sendState = ClientManager.sendAFN16Msg(afn16Object);
            logger.info("InstructServiceImpl - CancelOff 返回结果: " + sendState);
            return sendState;
        }catch (Exception e){
            throw new BusinessException("指令发送失败");
        }

    }

    @Override
    @Transactional
    public int syncCustomerDevices(String guid,Long currentUserId, List<String> cnoList) {
        // 批量查询参数表信息
        List<DeviceMeterParam> meterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        List<MeterReadQueue> readQueueList = Lists.newArrayList();
        Set<String> paramFlagSet = Sets.newHashSet();
        for (DeviceMeterParam meterParam : meterParams) {
            MeterReadQueue meterReadQueue = new MeterReadQueue();
            String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
            meterReadQueue.setJzqNo(jzqNo);
            meterReadQueue.setJzqCno(meterParam.getJzqCno());
            String meterNo = CNoUtil.getNo(meterParam.getCno());
            meterReadQueue.setMeterNo(meterNo);
            meterReadQueue.setMeterCno(meterParam.getCno());
            meterReadQueue.setQueueGuid(guid);
            meterReadQueue.setCreateTime(new Date());
            meterReadQueue.setCreateUserId(currentUserId);
            meterReadQueue.setReadStatus(0);
            readQueueList.add(meterReadQueue);

            paramFlagSet.add(meterParam.getParamFlag());
        }

        // 批量查询设备参数配置表信息
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);

        // 把下发信息放入队列表
        meterReadQueueService.insertList(readQueueList);

        // 批量更新对应参数表send_flag=0
        deviceMeterParamService.batchUpdateSendFlag2Zero(cnoList);

        // 查询系统AppEUI
        SysConfig sysConfig = sysConfigService.queryByConfigName("AppEUI");

        // 按集中器分组
        ImmutableListMultimap<String, DeviceMeterParam> listMultimap = Multimaps.index(meterParams, new Function<DeviceMeterParam, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceMeterParam deviceMeterParam) {
                return deviceMeterParam.getJzqCno();
            }
        });

        // 构造中间件指令对象
        ArrayList<AFN03Object> afn03Objects = new ArrayList<>();
        for (Map.Entry<String, Collection<DeviceMeterParam>> entry : listMultimap.asMap().entrySet()) {
            String jzqCno = entry.getKey();
            Collection<DeviceMeterParam> value = entry.getValue();
            ArrayList<AFN03Node> afn03Nodes = Lists.newArrayList();
            for (DeviceMeterParam param : value) {
                DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(param.getParamFlag());
                AFN03Node afn03Node = new AFN03Node(param.getMoteType(),
                        String.valueOf(param.getDeviceType()),
                        String.valueOf(param.getCommSetupSn()),
                        String.valueOf(param.getCommPointCode()),
                        String.valueOf(deviceMeterConfig.getCommBaudrate()),
                        String.valueOf(param.getCommPort()),
                        String.valueOf(deviceMeterConfig.getCommRule()),
                        param.getCommAddr(),
                        String.valueOf(deviceMeterConfig.getCommFactorCnt()),
                        param.getCommCollectionNo());
                afn03Nodes.add(afn03Node);
            }


            String jzqNo = CNoUtil.getJzqNoByJzqCno(jzqCno);
            AFN03Node afn03Node = afn03Nodes.get(0);
            AFN03Object afn03Object = new AFN03Object(guid,
                    jzqNo,
                    sysConfig.getConfigValue(),
                    afn03Node.getPort(),
                    afn03Nodes);
            afn03Objects.add(afn03Object);
        }

        int result = 0;
        try {
            result = ClientManager.sendAFN03Msg(afn03Objects);
            logger.info("下发客户档案中间件返回result=" + result);
        } catch (Exception e) {
            logger.error("下发客户档案中间件异常：",e);
        }

        return result;
    }

    /**
     * @param meterReadQueue
     * @return
     * @Description 发送实时采集指令
     */
    @Override
    public int SendCollectInstruct(MeterReadQueue meterReadQueue) {
        // 获取电表参数
        DeviceMeterParam devParaMeter = deviceMeterParamService.queryEffectiveParamByCno(meterReadQueue.getMeterCno());
        if (StringUtil.isEmpty(meterReadQueue.getJzqNo())) {
            String jzqCNo = devParaMeter.getJzqCno();
            if (!StringUtils.isEmpty(jzqCNo) && jzqCNo.length() >= 9) {
                meterReadQueue.setJzqNo(jzqCNo.substring(jzqCNo.length() - 9));
                meterReadQueue.setJzqCno(jzqCNo);
            } else {
                return 0;
            }
        }
        meterReadQueue.setMeterNo(devParaMeter.getCommAddr());
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        // 查询设备参数配置信息
        DeviceMeterConfig deviceMeterConfig = deviceMeterConfigService.queryByParamFlag(devParaMeter.getParamFlag());
        AFN04Object afn04Object = setAFN04Object(meterReadQueue, devParaMeter, deviceMeterConfig,sessionId);
        ArrayList<AFN04Object> listAfn04Object = new ArrayList<AFN04Object>();
        listAfn04Object.add(afn04Object);
        int sendState = ClientManager.sendMulCheckMeter(listAfn04Object);
        logger.info("InstructServiceImpl - SendCollectInstruct 返回结果: " + sendState);
        return sendState;
    }

    @Override
    public int SendCollectInstructList(Map<String, DeviceMeterParam> meterParamMap, ImmutableMap<String, DeviceMeterConfig> meterConfigMap,List<MeterReadQueue> meterReadQueues, String sessionId) {
        int result = 1;
        ArrayList<AFN04Object> afn04Objectlist = Lists.newArrayList();
        for (MeterReadQueue meterReadQueue : meterReadQueues) {
            DeviceMeterParam meterParam = meterParamMap.get(meterReadQueue.getMeterCno());
            DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterParam.getParamFlag());
            AFN04Object afn04Object = setAFN04Object(meterReadQueue, meterParam, deviceMeterConfig,sessionId);
            // 转换器实时抄表，sendMode=1
            Integer collectDevType = meterParam.getCollectDevType();
            Integer deviceType = meterParam.getDeviceType();
            if(Integer.valueOf(DeviceType.WATER_METER.getCode()).equals(deviceType)) {
                // 水表
                if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                    afn04Object.setSendMode(1);
                }
            } else if(Integer.valueOf(DeviceType.GAS_METER.getCode()).equals(deviceType)) {
                // 气表
                if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                    afn04Object.setSendMode(1);
                }
            }
            afn04Objectlist.add(afn04Object);
        }
        if (!CollectionUtils.isEmpty(afn04Objectlist)) {
            try {
                result = ClientManager.sendMulCheckMeter(afn04Objectlist);
            } catch(Exception e){
                throw new BusinessException("前置机未链接");
            }
        }
        logger.info("InstructServiceImpl - SendCollectInstructList 返回结果: " + result);

        return result;
    }

    @Override
    public int sendCollectInstructList4App(ImmutableMap<String, DeviceMeterConfig> meterConfigMap,Map<String,DeviceMeterParam> meterParamMap,List<MeterReadQueue> meterReadQueues) {
        ArrayList<AFN04Object> afn04Objectlist = Lists.newArrayList();
        int result = ReplenishEnum.ParamCheck.METER_READ_QUEUES_IS_EMPTY.getCode();
        if (!CollectionUtils.isEmpty(meterReadQueues)) {
            for (MeterReadQueue meterReadQueue : meterReadQueues) {
                String meterCno = meterReadQueue.getMeterCno();
                DeviceMeterParam deviceMeterParam = meterParamMap.get(meterCno);
                DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterCno);
                // 发送实时抄表指令
                AFN04Object afn04Object = new AFN04Object(meterReadQueue.getQueueGuid(), // 抄表GUID
                        meterReadQueue.getGroupGuid(), // groupGuid
                        meterReadQueue.getJzqNo(), // 集中器编号
                        StringUtil.getMoteType(deviceMeterParam.getCno().substring(0, 2)),
                        deviceMeterParam.getMoteEui(),
                        deviceMeterParam.getCommAddr(), // 电表地址
                        String.valueOf(deviceMeterParam.getCommPort()), // 抄表端口
                        String.valueOf(deviceMeterConfig.getCommRule()), // 抄表规约
                        String.valueOf(deviceMeterConfig.getCommBaudrate()), // 波特率
                        meterReadQueue.getDi645(), // DI
                        meterReadQueue.getDataFormat(), // 数据格式
                        String.valueOf(meterReadQueue.getOverTime()), // 超时时间
                        String.valueOf(deviceMeterParam.getIsImportant())
                );
                afn04Objectlist.add(afn04Object);
            }

            result = ClientManager.sendMulCheckMeter(afn04Objectlist);
        }
        logger.info("InstructServiceImpl - sendCollectInstructList4App 返回结果: " + result);

        return result;
    }

    // 远程充值接口
    @Override
    public int PaymentStruct(PaymentParam param) {
        logger.info("InstructServiceImpl - PaymentStruct query: param: " + JSONObject.fromObject(param));
        try {
            AFN09Object afn09Object = new AFN09Object(
                    param.getPayGuid(),     // 抄表GUID
                    param.getJzqNo(),       // 集中器编号
                    param.getMoteEUI(),
                    param.getCommAddr(),    // 电表地址
                    param.getMeterUserNo(),  //表计户号
                    param.getMoteType(),
                    param.getPayCount(),    //充值次数
                    param.getOverTime(),
                    param.getCommPort(),    // 抄表端口
                    param.getPayMoney());
            int sendState = ClientManager.sendAFN09Msg(afn09Object);
            logger.info("InstructServiceImpl - PaymentStruct 返回结果: " + sendState);
            return sendState;
        }catch (Exception e){
            throw new BusinessException("充值失败");
        }

    }


    @Override
    public void clearResultInfo(String guid) {
        logger.info("InstructServiceImpl - clearResultInfo query: " + guid);
        ClientManager.clearResultInfo(guid);
    }

    @Override
    public int SendOnOffInstructList(List<MeterReadQueue> meterReadQueues, String onOff, String sessionId) {
        ArrayList<AFN08Object> afn08Objectlist = Lists.newArrayList();
        for (MeterReadQueue meterReadQueue : meterReadQueues) {
            DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(meterReadQueue.getMeterCno());
            afn08Objectlist.add(setAFN08Object(meterReadQueue, deviceMeterParam, onOff, sessionId));
        }
        int result = ClientManager.sendMulAFN08Msg(afn08Objectlist);
        return result;
    }

    private AFN04Object setAFN04Object(MeterReadQueue meterReadQueue, DeviceMeterParam devParaMeter,DeviceMeterConfig meterConfig, String sessionId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 发送实时抄表指令
        AFN04Object afn04Object = new AFN04Object(meterReadQueue.getQueueGuid(), // 抄表GUID
                meterReadQueue.getGroupGuid(), // groupGuid
                meterReadQueue.getJzqNo(), // 集中器编号
                devParaMeter.getCno().substring(0, 2),
                devParaMeter.getMoteEui(),
                devParaMeter.getCommAddr(), // 电表地址
                String.valueOf(devParaMeter.getCommPort()), // 抄表端口
                String.valueOf(meterConfig.getCommRule()), // 抄表规约
                String.valueOf(meterConfig.getCommBaudrate()), // 波特率
                meterReadQueue.getDi645(), // DI
                DIFormatUtil.diForamt(meterReadQueue.getDi645()), // 数据格式
                String.valueOf(meterReadQueue.getOverTime()), // 超时时间
                String.valueOf(meterReadQueue.getCreateUserId()),
                sdf.format(meterReadQueue.getCreateTime()),
                sessionId,
                String.valueOf(meterReadQueue.getIsImportant())
        );
        return afn04Object;
    }

    private AFN08Object setAFN08Object(MeterReadQueue meterReadQueue, DeviceMeterParam devParaMeter, String onOff, String sessionId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 发送实时抄表指令
        String moteEUI = devParaMeter.getMoteEui();
        if (StringUtils.isEmpty(moteEUI)) {
            moteEUI = devParaMeter.getCommAddr();
        }
        DeviceMeterConfig deviceMeterConfig = deviceMeterConfigService.queryByParamFlag(devParaMeter.getParamFlag());
        AFN08Object afn08Object = new AFN08Object(meterReadQueue.getQueueGuid(), // 抄表GUID
                meterReadQueue.getJzqNo(), // 集中器编号
                moteEUI,
                devParaMeter.getCommAddr(), // 电表地址
                StringUtil.getMoteType(devParaMeter.getCno().substring(0, 2)),
                String.valueOf(meterReadQueue.getOverTime()), // 超时时间
                String.valueOf(devParaMeter.getCommPort()), // 抄表端口
                onOff,
                String.valueOf(meterReadQueue.getCreateUserId()),
                sdf.format(meterReadQueue.getCreateTime()),
                sessionId,
                "02/"+deviceMeterConfig.getMeterUserPwd()+"/"+deviceMeterConfig.getMeterUserName()+"/"+deviceMeterConfig.getSwitchOnCmd()+"/"+deviceMeterConfig.getSwitchOffCmd()
        );
        return afn08Object;
    }

    //采集器重启或初始化(1成功, -1指令下发失败, -2 指令执行失败)
    @Override
    public int cjqRebootOrInitialize(String jzqAddr, String cjqAddr, int operateType) {
        int result;
        String queueGuid = UuidUtil.getUuid();
        AFN11Object aFN11Object = this.getAFN11Object(queueGuid, jzqAddr, cjqAddr, operateType);
        result = ClientManager.sendAFN11Msg(aFN11Object);
        if(result != 1){
            result = -1;
            return result;
        }

        do{
            AbsBaseDataObject dataObject = ClientManager.getClientFutureStatus(queueGuid);
            result = dataObject.getStatus();
            if (result != 1 && result != 101) {
                result = -2;
                break;
            } else if(result == 1){
                result = 1;
                break;
            }
        }while (result == 101);

        return result;
    }

    @Override
    public void converterReboot(String jzqAddr, String cjqAddr) throws BusinessException{
        String queueGuid = UuidUtil.getUuid();
        AFN11Object aFN11Object = this.getAFN11Object(queueGuid, jzqAddr, cjqAddr, 1);
        int result = ClientManager.sendAFN11Msg(aFN11Object);
        if(result != 1){
            throw new BusinessException("指令下发失败");
        }

        AbsBaseDataObject dataObject = ClientManager.getClientFutureStatus(queueGuid);
        int futureStatus = dataObject.getStatus();
        while (futureStatus == 101) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AbsBaseDataObject clientFutureStatus = ClientManager.getClientFutureStatus(queueGuid);
            futureStatus = clientFutureStatus.getStatus();
        }

        if (futureStatus != 1) {
            throw new BusinessException("指令执行失败");
        }
    }

    @Override
    public void converterInit(String jzqAddr, String cjqAddr) throws BusinessException {
        String queueGuid = UuidUtil.getUuid();
        AFN11Object aFN11Object = this.getAFN11Object(queueGuid, jzqAddr, cjqAddr, 2);
        int result = ClientManager.sendAFN11Msg(aFN11Object);
        if(result != 1){
            throw new BusinessException("指令下发失败");
        }

        AbsBaseDataObject dataObject = ClientManager.getClientFutureStatus(queueGuid);
        int futureStatus = dataObject.getStatus();
        while (futureStatus == 101) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AbsBaseDataObject clientFutureStatus = ClientManager.getClientFutureStatus(queueGuid);
            futureStatus = clientFutureStatus.getStatus();
        }

        if (futureStatus != 1) {
            throw new BusinessException("指令执行失败");
        }
    }

    @Override
    public Map<String, Object> readOrSetTime(String jzqAddr, String cjqAddr, int operateType) {
        Map<String, Object> resultMap = Maps.newHashMap();

        String queueGuid = UuidUtil.getUuid();
        AFN12Object aFN12Object = this.getAFN12Object(queueGuid, jzqAddr, cjqAddr, operateType);
        int result =  ClientManager.sendAFN12Msg(aFN12Object);
        if(result != 1){
            resultMap.put("resultCode", 0);
            resultMap.put("msg","指令下发失败");
            return resultMap;
        }

        ResultLoraWanClock resultLoraWanClock;
        do {
            resultLoraWanClock = ClientManager.getResultLoraWanClockStatus(queueGuid);
            if (resultLoraWanClock.getState() != 1 && resultLoraWanClock.getState() != 101) {
                resultMap.put("resultCode", 0);
                if (operateType == 1) {
                    resultMap.put("msg", "设置时钟返回失败");
                } else {
                    resultMap.put("msg", "读时钟返回失败");
                }
                break;
            } else if(resultLoraWanClock.getState() == 1){
                resultMap.put("resultCode", 1);
                resultMap.put("msg", "成功");
                if (resultLoraWanClock.getFlag().equals("0")) {
                    resultMap.put("data", resultLoraWanClock.getClock());
                }
                break;
            }
        }while (resultLoraWanClock.getState() == 101);

        return resultMap;
    }

    @Override
    public String readConverterTime(String jzqAddr, String cjqAddr) {
        String queueGuid = UuidUtil.getUuid();
        AFN12Object aFN12Object = this.getAFN12Object(queueGuid, jzqAddr, cjqAddr, 0);
        int result =  ClientManager.sendAFN12Msg(aFN12Object);
        if(result != 1){
            throw new BusinessException("指令下发失败");
        }

        ResultLoraWanClock resultLoraWanClock = ClientManager.getResultLoraWanClockStatus(queueGuid);
        int state = resultLoraWanClock.getState();
        while (state == 101) {
            try {
                // 休眠一秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = resultLoraWanClock.getState();
        }

        if (state != 1) {
            throw new BusinessException("读时钟返回失败");
        }
        String time = resultLoraWanClock.getClock();
        return time;
    }

    @Override
    public void setConverterTime(String jzqAddr, String cjqAddr) throws BusinessException {
        String queueGuid = UuidUtil.getUuid();
        AFN12Object aFN12Object = this.getAFN12Object(queueGuid, jzqAddr, cjqAddr, 1);
        int result =  ClientManager.sendAFN12Msg(aFN12Object);
        if(result != 1){
            throw new BusinessException("指令下发失败");
        }

        ResultLoraWanClock resultLoraWanClock = ClientManager.getResultLoraWanClockStatus(queueGuid);
        int state = resultLoraWanClock.getState();
        while (state == 101) {
            try {
                // 休眠一秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = resultLoraWanClock.getState();
        }

        if (state != 1) {
            throw new BusinessException("设置时钟返回失败");
        }
    }

    @Override
    public int jzqReboot(String jzqNo) throws BusinessException{
        try {
            String guid = UuidUtil.getUuid();
            int retCode = ClientManager.sendAFN01Msg(new BaseAFNObject(guid, "01", jzqNo));
            return retCode;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }
    }

    @Override
    public int jzqInitialize(String jzqNo) {
        try{
            String guid = UuidUtil.getUuid();
            int retCode = ClientManager.sendAFN02Msg(new BaseAFNObject(guid, "02", jzqNo));
            return retCode;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }

    }

    @Override
    public int jzqReadCustomerInfo(String queueGuid, String jzqAddr, String commSetupSn) {
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        logger.info("发送的sessionId=" + sessionId);
        AFN14Object afn14Object = new AFN14Object(queueGuid,"14",jzqAddr,commSetupSn,sessionId);
        int msg = ClientManager.sendAFN14Msg(afn14Object);
        return msg;
    }

    @Override
    public int queryBatchTaskStatus(String guid) {
        ResultInfo resultInfo = ClientManager.getMulReadMeterStatus(guid);
        logger.info("中间件返回ResultInfo=" + JSON.toJSONString(resultInfo));
        // 0表示未完成，1表示已完成
        int result = ClientConstant.TaskProcessStatus.NO.getStatus();
        int status = resultInfo.getStatus();
        if (status != 101) {
            // 表示该任务已经结束
            result = ClientConstant.TaskProcessStatus.YES.getStatus();
        }
        return result;
    }

    @Override
    public AbsBaseDataObject querySingleTaskStatus(String guid) {
        AbsBaseDataObject dataObject = ClientManager.getClientFutureStatus(guid);
        return dataObject;
    }

    private AFN11Object getAFN11Object(String queueGuid, String jzqAddr, String cjqAddr, int operateType) {
        AFN11Object aFN11Object = new AFN11Object(queueGuid, "11", jzqAddr, cjqAddr, String.valueOf(operateType));
        return aFN11Object;

    }

    private AFN12Object getAFN12Object(String queueGuid, String jzqAddr, String cjqAddr, int operateType) {
        String clock = "";
        if(operateType == 1){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            clock = format.format(new Date());
        }
        AFN12Object aFN12Object = new AFN12Object(queueGuid, "12", jzqAddr, cjqAddr, String.valueOf(operateType), clock);

        return aFN12Object;

    }
}
