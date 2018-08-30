package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ChargingICUseDto;
import cn.com.cdboost.collect.dto.ListElectricDto;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingUseDetailed;

import java.util.List;

/**
 * 充电桩使用记录接口
 */
public interface ChargingUseDetailedService extends BaseService<ChargingUseDetailed> {

    List<ListElectricDto> queryMonthList(String beginTime, String endTime, List<String> meterNos);

    // 根据openId查询充电中记录
    ChargingUseDetailed queryChargingRecordByOpenId(String openId);

    // 根据customerGuid查询充电中的记录
    ChargingUseDetailed queryChargingRecordByCustomerGuid(String customerGuid);

    // 根据充电桩设备唯一标识，查询充电中记录
    ChargingUseDetailed queryChargingRecordByPlieGuid(String chargingPlieGuid);

    // 根据openId查询最近一次充电记录
    ChargingUseDetailed queryRecentUseRecord(String openId);

    // 根据customerGuid查询该用户最近一次充电记录
    ChargingUseDetailed queryRecentUseRecordByCustomerGuid(String customerGuid);

    //根据chargingGuid查询使用记录
    ChargingUseDetailed queryChargingRecordByChargingGuid(String chargingGuid);

    // 根据customerGuid查询所有使用记录，按id降序排列
    List<ChargingUseDetailed> queryByCustomerGuid(String customerGuid);

    void updateUseTime(String deviceNo, String port, Integer useTime);

    /**
     * 修改已使用电量
     * @param deviceNo
     * @param port
     * @param usePower
     */
    void updateUsePower(String deviceNo, String port, String usePower);

    //ic卡使用记录
    List<ChargingICUseDto> queryICCardUseList(ChargerICCardQueryVo queryVo);

    List<ChargingUseDetailed> queryRecentNlist(String cardId,Integer number);
}
