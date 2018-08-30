package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.MonthSumData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonthSumDataMapper extends CommonMapper<MonthSumData> {
    // 统计N月用电记录
    List<MonthSumData> getNMonthSumDataByCno(@Param("cno") String cno, @Param("n") int n);
}