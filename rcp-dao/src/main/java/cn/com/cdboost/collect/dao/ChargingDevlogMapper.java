package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.ChargingDevlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargingDevlogMapper extends CommonMapper<ChargingDevlog> {
    List<ChargingDevlog> queryCurve(@Param("chargingPlieGuid") String chargingPlieGuid, @Param("chargingGuid") String chargingGuid);
}