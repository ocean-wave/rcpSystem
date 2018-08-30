package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ChargeRecordListDto;
import cn.com.cdboost.collect.dto.ChargeRecordListInfo;
import cn.com.cdboost.collect.dto.ChargingICPayDto;
import cn.com.cdboost.collect.dto.chargerApp.ChargeMoneyHistoryDto;
import cn.com.cdboost.collect.dto.chargerApp.vo.HistoryVo;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingPay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargingPayMapper extends CommonMapper<ChargingPay> {
    List<ChargeRecordListInfo> chargeRecordList(@Param("chargeRecordListDto") ChargeRecordListDto chargeRecordListDto);
    /**
     * 用户充值记录查询
     * @param historyVo
     * @return
     */
    List<ChargeMoneyHistoryDto> queryCharge(HistoryVo historyVo);

    /**
     * 获取充值记录总数
     * @param historyVo
     * @return
     */
     Long queryChargeTotal(HistoryVo historyVo);

    List<ChargingICPayDto> queryICCardPayList(ChargerICCardQueryVo queryVo);

    Integer queryICCardPayTotal(ChargerICCardQueryVo queryVo);
}