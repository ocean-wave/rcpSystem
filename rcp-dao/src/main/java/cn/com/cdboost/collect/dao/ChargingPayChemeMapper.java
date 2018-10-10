package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ChargingUseDetailedDto;
import cn.com.cdboost.collect.dto.SchemePofitableDto;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.model.ChargingPayCheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargingPayChemeMapper extends CommonMapper<ChargingPayCheme> {
    //统计方案使用情况
    List<SchemePofitableDto> countPofitable(ChargerSchemeQueryVo queryVo);

    //查询方案使用记录
    List<ChargingUseDetailedDto> shemeUseList(ChargerSchemeQueryVo queryVo);

    Integer queryTotal(ChargerSchemeQueryVo queryVo);

    //查询项目下功率
    List<ChargingPayCheme> queryPower(@Param("projectGuid") String projectGuid);

    //批量修改
    void batchUpdate(List<ChargingPayCheme> list);
}