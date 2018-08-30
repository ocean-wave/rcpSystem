package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChartsQueryVo;
import cn.com.cdboost.collect.dto.param.EnergyEfficiencyQueryVo;
import cn.com.cdboost.collect.dto.param.MeterDayPowerQueryVo;
import cn.com.cdboost.collect.model.MeterDayPower;

import java.util.List;

/**
 * 能效分析接口
 */
public interface EnergyEfficiencyService extends BaseService<MeterDayPower> {
    List<EnergyEfficiencyDetailListDto> queryLastPowerDetail(MeterDayPowerQueryVo queryVo);

    List<VillageElectricityDto> queryVillageList(EnergyEfficiencyQueryVo queryVo);

    ElectricChartsInfo electricCount(ChartsQueryVo queryVo);

    List<ElectricityCountDto> queryDeviceList(EnergyEfficiencyQueryVo queryVo);

    List<EnergyEfficiencyDetailDto> queryDeviceDetail(EnergyEfficiencyQueryVo queryVo);

    Integer queryMeterNum(ChartsQueryVo queryVo);
}
