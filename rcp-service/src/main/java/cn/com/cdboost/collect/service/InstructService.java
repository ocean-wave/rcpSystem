package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.CancelOffParam;
import cn.com.cdboost.collect.dto.PaymentParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.DeviceMeterConfig;
import cn.com.cdboost.collect.model.DeviceMeterParam;
import cn.com.cdboost.collect.model.MeterReadQueue;
import com.example.clienttest.client.AbsBaseDataObject;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * 中间键指令接口
 */
public interface InstructService {
    // 立即算费指令
    int Instantcalculate(CancelOffParam param);
    // 取消关断指令
    int CancelOff(CancelOffParam param);

    // 同步用户档案
    int syncCustomerDevices(String guid,Long currentUserId,List<String> cnoList);

    int SendCollectInstruct(MeterReadQueue meterReadQueue);

    // 电表远程充值操作
    int PaymentStruct(PaymentParam param);

    // 批量抄表
    int SendCollectInstructList(Map<String, DeviceMeterParam> meterParamMap,ImmutableMap<String, DeviceMeterConfig> meterConfigMap, List<MeterReadQueue> meterReadQueues, String sessionId);

    // 手机端批量抄表
    int sendCollectInstructList4App(ImmutableMap<String, DeviceMeterConfig> meterConfigMap,Map<String,DeviceMeterParam> meterParamMap,List<MeterReadQueue> meterReadQueues);

    // 清理resultInfo
    void clearResultInfo(String guid);

    // 批量通断操作
    int SendOnOffInstructList(List<MeterReadQueue> meterReadQueues, String onOff, String sessionId);

    // 采集器重启或初始化(1成功, -1指令下发失败, -2 指令执行失败)
    int cjqRebootOrInitialize(String jzqAddr, String cjqAddr, int operateType);

    void converterReboot(String jzqAddr, String cjqAddr) throws BusinessException;

    void converterInit(String jzqAddr, String cjqAddr) throws BusinessException;

    // 读/设LoRaWAN转换器时钟
    Map<String, Object> readOrSetTime(String jzqAddr, String cjqAddr, int operateType);

    String readConverterTime(String jzqAddr, String cjqAddr) throws BusinessException;

    void setConverterTime(String jzqAddr, String cjqAddr) throws BusinessException;

    /**
     * 集中器重启
     * @param jzqNo
     * @throws BusinessException
     */
    int jzqReboot(String jzqNo) throws BusinessException;

    /**
     * 集中器初始化
     * @param jzqNo
     * @throws BusinessException
     */
    int jzqInitialize(String jzqNo);

    int jzqReadCustomerInfo(String queueGuid,String jzqAddr, String commSetupSn);

    // 查询批量任务状态
    int queryBatchTaskStatus(String guid);

    // 查询单个任务状态
    AbsBaseDataObject querySingleTaskStatus(String guid);
}
