package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.UseEnergyStatisticsMapper;
import cn.com.cdboost.collect.dto.HistogramDataInfo;
import cn.com.cdboost.collect.dto.param.HistogramDataQueryVo;
import cn.com.cdboost.collect.dto.response.BuildingUseEnergyData;
import cn.com.cdboost.collect.service.UseEnergyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用能统计服务接口
 */
@Service
public class UseEnergyStatisticsServiceImpl implements UseEnergyStatisticsService {
    @Autowired
    private UseEnergyStatisticsMapper useEnergyStatisticsMapper;

    @Override
    public HistogramDataInfo statistics(HistogramDataQueryVo queryVo) {
        // 按楼栋，查询起始时间的抄表数据
        Long orgNo = queryVo.getOrgNo();
        String startDate = queryVo.getStartDate();
        String deviceType = queryVo.getDeviceType();
        List<BuildingUseEnergyData> data = useEnergyStatisticsMapper.statisticsByBuildingNo(orgNo, startDate, deviceType);

        // 按楼栋，查询结束时间的抄表数据
        String endDate = queryVo.getEndDate();
        List<BuildingUseEnergyData> data2 = useEnergyStatisticsMapper.statisticsByBuildingNo(orgNo, endDate, deviceType);

        HistogramDataInfo dataInfo = new HistogramDataInfo();

        return null;
    }
}
