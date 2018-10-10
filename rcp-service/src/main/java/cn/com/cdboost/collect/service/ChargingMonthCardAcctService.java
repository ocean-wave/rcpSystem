package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.param.AccountOperateVo;
import cn.com.cdboost.collect.model.ChargingMonthCardAcct;

import java.util.List;

/**
 * 充电月卡账户接口
 */
public interface ChargingMonthCardAcctService extends BaseService<ChargingMonthCardAcct>{
    // 查询用户可用的月卡账户信息
    List<ChargingMonthCardAcct> queryUseableMonthCard(String customerGuid,List<String> list);

    // 查询用户可用的所有月卡账户
    List<ChargingMonthCardAcct> queryUseableMonthCardList(String customerGuid);

    // 查询该用户购买的月卡账户（不管是否过期，还是次数不够，都查出来）
    List<ChargingMonthCardAcct> queryUserBuyMonthCard(String customerGuid,List<String> list);

    // 查询用户是否存在此方案的，可用的月卡账户
    ChargingMonthCardAcct queryUseableMonthCard(String customerGuid,String schemeGuid);

    // 根据参数查询用户月卡信息
    ChargingMonthCardAcct queryByParams(String customerGuid,String schemeGuid);

    void updateAccount(ChargingMonthCardAcct param, AccountOperateVo operateVo);


}
