package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.CollectRecordDTO;
import cn.com.cdboost.collect.dto.MoteAnalyzeResponse;
import cn.com.cdboost.collect.dto.RoundChartVo;
import cn.com.cdboost.collect.dto.SucRateByCjqVo;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryVo;
import cn.com.cdboost.collect.dto.response.RoundPercentInfo;
import cn.com.cdboost.collect.model.FreezeAnalyze;

import java.util.List;

/**
 * 集分析服务接口
 */
public interface FreezeAnalyzeService extends BaseService<FreezeAnalyze>{
    /**
     * 汇总当日日冻结轮次曲线
     * @param jzqNo
     * @param date
     * @return
     */
    List<RoundChartVo> queryRoundChart(String jzqNo, String date);


    /**
     * 每日采集成功率
     * @param deviceType
     * @param userId
     * @return
     */
    List<CollectRecordDTO> querySucRateChart(String deviceType, Long userId);

    /**
     * 按采集器分组查询数据
     * @param queryVo
     * @return
     */
    List<MoteAnalyzeResponse> queryDataGroupByCjq(FreeDayByCollGetQueryVo queryVo);

    /**
     * 按采集轮次统计占比
     * @param jzqNo
     * @param freezeDate
     * @return
     */
    List<RoundPercentInfo> queryRoundPercent(String jzqNo, String freezeDate);

    /**
     * 统计采集器连续几天采集成功率
     * @param jzqNo
     * @param moteEui
     * @param dayCnt
     * @return
     */
    List<SucRateByCjqVo> querySucRateByCjq(String jzqNo, String moteEui,Integer dayCnt);
}
