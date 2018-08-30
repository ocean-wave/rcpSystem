package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.MeterReadQueue;
import org.apache.ibatis.annotations.Param;

public interface MeterReadQueueMapper extends CommonMapper<MeterReadQueue> {
    // 通过guid和sendFlag统计
    int countByGuidAndSendFlag(@Param("guid") String guid, @Param("sendFlag") int sendFlag);
}