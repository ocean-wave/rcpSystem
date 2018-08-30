package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ElectDetailDto;
import cn.com.cdboost.collect.dto.ElectDetailParamDto;
import cn.com.cdboost.collect.dto.param.DaySettlementParam;
import cn.com.cdboost.collect.model.CostCalculate;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CostCalculateMapper extends CommonMapper<CostCalculate> {
    List<ElectDetailDto> electDetailList(ElectDetailParamDto electDetailParamDto);
    List<CostCalculate> queryByUserOrgDay(@Param("daySettlementParam")DaySettlementParam daySettlementParam, @Param("orgNoList") List<Long> orgNoList);
    List<CostCalculate> queryByUserOrgMonth(@Param("daySettlementParam")DaySettlementParam daySettlementParam, @Param("orgNoList") List<Long> orgNoList);
    List<CostCalculate> electDetail(@Param("customerNo")String customerNo,@Param("lastBanlance")String lastBanlanceNext,@Param("banlanceDay")String banlanceDay);


}