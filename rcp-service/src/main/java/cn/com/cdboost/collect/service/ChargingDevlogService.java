package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.CurveQueryInfo;
import cn.com.cdboost.collect.dto.HeartDto;
import cn.com.cdboost.collect.dto.param.ChargerDeviceQueryVo;
import cn.com.cdboost.collect.model.ChargingDevlog;

import java.util.List;

/**
 * 充电设备日志接口
 */
public interface ChargingDevlogService extends BaseService<ChargingDevlog> {
    /**
     * 查询电压电流公率曲线
     * @param sessionId
     * @param chargingPlieGuid
     * @param chargingGuid
     * @param queryFlag  1--监控页曲线；2--统计页曲线
     * @return
     */
    CurveQueryInfo queryCurve(String sessionId, String chargingPlieGuid, String chargingGuid, Integer queryFlag);

    /**
     * 查询最近一条异常
     * @param chargingGuid
     * @return
     */
    ChargingDevlog queryRecentError(String chargingGuid);

    /**
     * 查询最近一条警告记录且短信状态未发送
     * @param chargingGuid
     * @return
     */
    ChargingDevlog queryRecentAlarm(String chargingGuid);

    /**
     * 查询最近一条断电记录且短信未发状态
     * @param chargingGuid
     * @return
     */
    ChargingDevlog queryRecentStop(String chargingGuid);


    ChargingDevlog queryRecentEvent(String chargingGuid,Integer messageType);
    /**
     * 根据chargingGuid更新短信的状态
     * @param chargingGuid
     */
    void updateSmsStatus(String chargingGuid);

    /**
     * 根据设备guid查询最近100条心跳
     * @param chargingPlieGuid
     * @return
     */
    List<HeartDto> queryHeartList(String chargingPlieGuid);

    /**
     * 查询设备每次使用记录的心跳
     * @param queryVo
     * @return
     */
    List<HeartDto> queryHeartBychargingGuid(ChargerDeviceQueryVo queryVo);

    List<ChargingDevlog> queryByChargingGuid(String chargingGuid);
}
