package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FreezeAnalyzeMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryVo;
import cn.com.cdboost.collect.dto.response.RoundDataInfo;
import cn.com.cdboost.collect.dto.response.RoundPercentInfo;
import cn.com.cdboost.collect.dto.response.SucRateByCjqInfo;
import cn.com.cdboost.collect.model.FreezeAnalyze;
import cn.com.cdboost.collect.model.FreezeLog;
import cn.com.cdboost.collect.service.FreezeAnalyzeService;
import cn.com.cdboost.collect.service.FreezeLogService;
import cn.com.cdboost.collect.util.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 采集分析服务接口实现类
 */
@Service("freezeAnalyzeChartService")
public class FreezeAnalyzeServiceImpl extends BaseServiceImpl<FreezeAnalyze> implements FreezeAnalyzeService {

    @Autowired
    private FreezeAnalyzeMapper freezeAnalyzeMapper;
    @Autowired
    private FreezeLogService freezeLogService;

    @Override
    public List<RoundChartVo> queryRoundChart(String jzqNo, String date) {
        List<FreezeLog> roundList = freezeLogService.queryByParams(jzqNo, date);
        List<RoundChartVo> lists = Lists.newArrayList();
        if (roundList != null && roundList.size() > 0) {
            for (FreezeLog round : roundList) {
                if (round.getMeterCnt() == 0 || round.getReadySend() == 0) {
                    continue;
                }

                RoundChartVo chartVo = new RoundChartVo();
                chartVo.setTotal(round.getMeterTotal());
                chartVo.setRound(round.getRound());
                int cnt = round.getMeterCnt() - round.getMeterSuccCnt();
                if (cnt > 0) {
                    chartVo.setFailCnt(cnt);
                } else {
                    chartVo.setFailCnt(0);
                }

                chartVo.setSuccRate(Double.parseDouble(new DecimalFormat("#.####").format((double) (round.getMeterTotal() - round.getMeterCnt() + round.getMeterSuccCnt()) / round.getMeterTotal())));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                chartVo.setRunTime(String.format("%s-%s", sdf.format(round.getStTime()), sdf.format(round.getEdTime())));
                chartVo.setRemark(round.getRemark());
                lists.add(chartVo);
            }
        }

        return lists;
    }

    @Override
    public List<CollectRecordDTO> querySucRateChart(String deviceType, Long userId) {
        List<CollectRecordDTO> dtoList = freezeAnalyzeMapper.queryFreezeRate(String.valueOf(userId),deviceType);
        return dtoList;
    }

    @Override
    public List<MoteAnalyzeResponse> queryDataGroupByCjq(FreeDayByCollGetQueryVo queryVo) {
        String updateTime = queryVo.getUpdateTime();
        if (updateTime == null) {
            queryVo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

        String collectNo = queryVo.getCollectNo();
        if (collectNo == null) {
            queryVo.setCollectNo("");
        }

        List<MoteAnalyzeInfo> moteList = freezeAnalyzeMapper.queryFreezeGroupByCjq(queryVo);
        List<MoteAnalyzeResponse> list = Lists.newArrayList();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        if (!CollectionUtils.isEmpty(moteList)) {
            for (MoteAnalyzeInfo info : moteList) {
                MoteAnalyzeResponse response = new MoteAnalyzeResponse();
                BeanUtils.copyProperties(info,response);
                Date startTime = info.getStartTime();
                Integer startRound = info.getStartRound();
                if (startRound != null && startTime != null) {
                    String time = format.format(startTime);
                    response.setStartTime(startRound + "-" + time);
                }
                Date endTime = info.getEndTime();
                Integer endRound = info.getEndRound();
                if (endRound != null && endTime != null) {
                    String time = format.format(endTime);
                    response.setEndTime(endRound + "-" + time);
                }

                list.add(response);
            }
        }

        return list;
    }

    @Override
    public List<RoundPercentInfo> queryRoundPercent(String jzqNo, String freezeDate) {
        List<RoundPercentInfo> list = Lists.newArrayList();
        List<RoundDataInfo> roundDataInfos = freezeAnalyzeMapper.queryRoundPercent(jzqNo, freezeDate);
        if (!CollectionUtils.isEmpty(roundDataInfos)) {
            Integer totalMeter = 0;
            Integer otherMeterCnt = 0;
            Integer otherMoteCnt = 0;
            for (RoundDataInfo roundDataInfo : roundDataInfos) {
                totalMeter += roundDataInfo.getMeterCnt();
                Integer runRound = roundDataInfo.getRunRound();
                if (runRound >= 4) {
                    // 轮次大于等于4时，前端显示成其他
                    Integer meterCnt = roundDataInfo.getMeterCnt();
                    otherMeterCnt += meterCnt;
                    Integer moteCnt = roundDataInfo.getMoteCnt();
                    otherMoteCnt += moteCnt;
                } else {
                    RoundPercentInfo info = new RoundPercentInfo();
                    BeanUtils.copyProperties(roundDataInfo,info);
                    list.add(info);
                }
            }

            if (otherMeterCnt > 0 || otherMoteCnt > 0) {
                RoundPercentInfo info = new RoundPercentInfo();
                info.setRound(4);
                info.setMoteCnt(otherMoteCnt);
                info.setMeterCnt(otherMeterCnt);
                info.setRunRound(4);
                list.add(info);
            }

            RoundPercentInfo roundPercentInfo=new RoundPercentInfo();
            roundPercentInfo.setRunRound(1);
            roundPercentInfo.setMeterCnt(0);
            roundPercentInfo.setMoteCnt(0);
            if (!list.contains(roundPercentInfo)) {
                list.add(roundPercentInfo);
            }
            RoundPercentInfo roundPercentInfo1 = new RoundPercentInfo();
            roundPercentInfo1.setMeterCnt(0);
            roundPercentInfo1.setMoteCnt(0);
            roundPercentInfo1.setRunRound(2);
            if (!list.contains(roundPercentInfo1)) {
                list.add(roundPercentInfo1);
            }
            RoundPercentInfo roundPercentInfo2 = new RoundPercentInfo();
            roundPercentInfo2.setMeterCnt(0);
            roundPercentInfo2.setMoteCnt(0);
            roundPercentInfo2.setRunRound(3);
            if (!list.contains(roundPercentInfo2)) {
                list.add(roundPercentInfo2);
            }

            // 计算前端返回
            for (RoundPercentInfo info : list) {
                Integer meterCnt = info.getMeterCnt();
                if (totalMeter > 0) {
                    info.setPercent(String.valueOf(( new BigDecimal(meterCnt * 100).divide(new BigDecimal(totalMeter),2,BigDecimal.ROUND_HALF_UP))));
                } else {
                    info.setPercent("0");
                }
                StringBuffer sb = new StringBuffer();
                Integer runRound = info.getRunRound();
                if (runRound != null && runRound == 4) {
                    // 其他轮
                    sb.append("更多轮-");
                } else {
                    sb.append(info.getRunRound() + "轮-");
                }

                sb.append("采:").append(info.getMoteCnt());
                sb.append("表:").append(meterCnt);
                sb.append("(").append(info.getPercent()).append(")");
                info.setLabel(sb.toString());
                info.setRound(info.getRunRound());
            }
        }

        list.sort(Comparator.comparing(RoundPercentInfo::getRunRound));
        return list;
    }

    @Override
    public List<SucRateByCjqVo> querySucRateByCjq(String jzqNo, String moteEui,Integer dayCnt) {
        if (StringUtil.isEmpty(jzqNo)) {
            jzqNo = "99999999";
        }
        int dayCntTemp = 7;
        if (dayCnt != null) {
            dayCntTemp = dayCnt;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * dayCntTemp);
        String freezeDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        List<SucRateByCjqVo> returnList = Lists.newArrayList();
        List<SucRateByCjqInfo> sucRateByCjqInfos = freezeAnalyzeMapper.querySucRateByCjq(jzqNo, moteEui, freezeDate);
        if (!CollectionUtils.isEmpty(sucRateByCjqInfos)) {
            for (SucRateByCjqInfo cjqInfo : sucRateByCjqInfos) {
                SucRateByCjqVo cjqVo = new SucRateByCjqVo();
                Date freezeDay = cjqInfo.getFreezeDay();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cjqVo.setDateTime(sdf.format(freezeDay));
                int meterCnt = cjqInfo.getMeterCount();
                int succCnt = 0;
                Integer succCount = cjqInfo.getSuccCount();
                if (succCount != null) {
                    succCnt = succCount;
                }

                cjqVo.setMeterCnt(meterCnt);
                cjqVo.setRoundCnt(cjqInfo.getRunRound());
                if (meterCnt > 0) {
                    cjqVo.setFailCnt(meterCnt - succCnt);
                    cjqVo.setSucRate(new DecimalFormat("#0.00").format(((double) succCnt / meterCnt) * 100));
                } else {
                    cjqVo.setFailCnt(0);
                    cjqVo.setSucRate("0");
                }
                cjqVo.setGetWay("");
                returnList.add(cjqVo);
            }
        }
        return returnList;
    }
}
