package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.WithdrawCashListDto;
import cn.com.cdboost.collect.dto.WithdrawCashListInfo;
import cn.com.cdboost.collect.model.ChargingWithdrawCash;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargingWithdrawCashMapper extends CommonMapper<ChargingWithdrawCash> {

    List<WithdrawCashListInfo> withdrawCashList(@Param("withdrawCashListDto") WithdrawCashListDto withdrawCashListDto);
}