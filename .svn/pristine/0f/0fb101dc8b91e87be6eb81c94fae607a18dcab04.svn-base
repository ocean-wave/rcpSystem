package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.CollectRecordDTO;
import cn.com.cdboost.collect.dto.MoteAnalyzeInfo;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryVo;
import cn.com.cdboost.collect.dto.response.RoundDataInfo;
import cn.com.cdboost.collect.dto.response.SucRateByCjqInfo;
import cn.com.cdboost.collect.model.FreezeAnalyze;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FreezeAnalyzeMapper extends CommonMapper<FreezeAnalyze> {
    /**
     * 汇总结算周期内日冻结的采集情况
     * @param userId
     * @param deviceType
     * @return
     */
    List<CollectRecordDTO> queryFreezeRate(@Param("userId") String userId, @Param("deviceType") String deviceType);

    /**
     * 获取采集日冻结召测分析数据
     * @param queryVo
     * @return
     */
    List<MoteAnalyzeInfo> queryFreezeGroupByCjq(FreeDayByCollGetQueryVo queryVo);

    /**
     * 统计抄收轮次百分比
     * @param jzqNo
     * @param freezeDate
     * @return
     */
    List<RoundDataInfo> queryRoundPercent(@Param("jzqNo") String jzqNo, @Param("freezeDate") String freezeDate);
    /**
     * 统计采集器采集成功率
     *
     * @param jzqNo
     * @param moteEui
     * @param freezeDate
     * @return
     */
    List<SucRateByCjqInfo> querySucRateByCjq(@Param("jzqNo") String jzqNo,
                                             @Param("moteEui") String moteEui,
                                             @Param("freezeDate") String freezeDate);
}