package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.ChargingWithdrawCash;

import java.util.List;
import java.util.Map;

/**
 * 充电用户体现记录表
 */
public interface ChargingWithdrawCashService extends BaseService<ChargingWithdrawCash> {

    /**
     * 根据guid查询
     * @param guid
     * @return
     */
    ChargingWithdrawCash queryByGuid(String guid);

    // 分页查询提现流水记录
    Map<String,Object> queryCashFlow(Integer appType,String openId, Integer pageNumber, Integer pageSize);

    // 查询所有提现中的记录
    List<ChargingWithdrawCash> queryCashFailFlows();
}
