package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ChargingICPayDto;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingPay;

import java.util.List;

/**
 * 客户充值记录信息接口
 */
public interface ChargingPayService extends BaseService<ChargingPay> {

    // 根据openId,查询该用户的所有充值记录
    List<ChargingPay> queryListByOpenId(String openId);

    // 根据payFlag查询
    ChargingPay queryByPayFlag(String payFlag);

    // 根据customerGuid 查询
    List<ChargingPay> queryByCustomerGuid(String customerGuid);

    //ic卡充值记录
    List<ChargingICPayDto> queryICCardPayList(ChargerICCardQueryVo queryVo);

    // 查询最近3条IC卡充值记录
    List<ChargingPay> queryRecentNlist(String cardId,Integer number);
}
