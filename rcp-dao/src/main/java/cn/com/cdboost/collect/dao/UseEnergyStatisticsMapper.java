package cn.com.cdboost.collect.dao;


import cn.com.cdboost.collect.dto.response.BuildingUseEnergyData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UseEnergyStatisticsMapper {

    // 按楼栋统计用电量
    List<BuildingUseEnergyData> statisticsByBuildingNo(@Param("orgNo") Long orgNo,
                                                       @Param("queryDate") String queryDate,
                                                       @Param("deviceType") String deviceType);
}
