package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ElectricChartDto;
import cn.com.cdboost.collect.dto.EnergyEfficiencyDetailDto;
import cn.com.cdboost.collect.dto.EnergyEfficiencyDetailListDto;
import cn.com.cdboost.collect.dto.VillageElectricityDto;
import cn.com.cdboost.collect.dto.param.ChartsQueryVo;
import cn.com.cdboost.collect.dto.param.DayElectricInfoResponseParam;
import cn.com.cdboost.collect.dto.param.EnergyEfficiencyQueryVo;
import cn.com.cdboost.collect.dto.param.MeterDayPowerQueryVo;
import cn.com.cdboost.collect.model.MeterDayPower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeterDayPowerMapper extends CommonMapper<MeterDayPower> {
    List<DayElectricInfoResponseParam> queryHistoryElectricAmount(@Param("customerNo") String customerNo,
                                                                  @Param("start") String start,
                                                                  @Param("end") String end
    );

    List<DayElectricInfoResponseParam> queryDayElectricInfo(@Param("customerNo") String customerNo,
                                                            @Param("calcData") String calcData
    );

    List<EnergyEfficiencyDetailListDto> queryLastPowerDetail(MeterDayPowerQueryVo queryVo);

    List<VillageElectricityDto> queryVillageList(EnergyEfficiencyQueryVo queryVo);

    List<ElectricChartDto> electricCount(ChartsQueryVo queryVo);


    List<EnergyEfficiencyDetailDto> queryDeviceDetail(EnergyEfficiencyQueryVo queryVo);

    Integer queryMeterNum(ChartsQueryVo queryVo);
}