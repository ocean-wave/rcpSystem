package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.DaySettlementParam;
import cn.com.cdboost.collect.model.DayMeterSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DayMeterSumMapper extends CommonMapper<DayMeterSum> {
    List<DayMeterSum> queryByUserOrgDay(@Param("daySettlementParam")DaySettlementParam daySettlementParam, @Param("orgNoList") List<Long> orgNoList);
}