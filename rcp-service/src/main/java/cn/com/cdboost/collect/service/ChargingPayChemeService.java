package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ChargingUseDetailedDto;
import cn.com.cdboost.collect.dto.SchemePofitableDto;
import cn.com.cdboost.collect.dto.param.ChargerSchemeEditParam;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.param.OffOnSchemeParam;
import cn.com.cdboost.collect.dto.response.ChargingSchemeInfo;
import cn.com.cdboost.collect.model.ChargingPayCheme;

import java.util.List;

/**
 * 充值方案信息表接口
 */
public interface ChargingPayChemeService extends BaseService<ChargingPayCheme> {
    //根据项目唯一标识查询月卡充值方案
    List<ChargingPayCheme> queryMonthSchemeList();

    // 充电页面，查询价格方案
    List<ChargingPayCheme> querySchemeList4ChargePage(String projectGuid);

    List<ChargingPayCheme> querySchemeList4ActivityPage();

    //根据项目标识查询方案
    ChargingSchemeInfo queryDetailByProjectGuid(String projectGuid);

    //启用停用充电方案
    void offOnScheme(OffOnSchemeParam param, Integer id);

    void edit(ChargerSchemeEditParam param, Integer id);

    //方案营收分析统计
    List<SchemePofitableDto> countPofitable(ChargerSchemeQueryVo queryVo);

    //方案使用记录
    List<ChargingUseDetailedDto> shemeUseList(ChargerSchemeQueryVo queryVo);

    ChargingPayCheme queryMonthCardScheme();
}
