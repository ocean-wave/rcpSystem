package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.EmDImpElecdataMapper;
import cn.com.cdboost.collect.dto.ImpCollectionAnalysisDTO;
import cn.com.cdboost.collect.dto.KeyCollectDTO;
import cn.com.cdboost.collect.dto.param.KeyCollectVo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.model.DayImpSum;
import cn.com.cdboost.collect.model.EmDImpElecdata;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.MathUtil;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 采集分析服务接口实现类
 */
@Service("keyCollectionService")
public class KeyCollectionServiceImpl extends BaseServiceImpl<EmDImpElecdata> implements KeyCollectionService {
    @Autowired
    private EmDImpElecdataMapper emDImpElecdataMapper;
    @Autowired
    private DayImpSumService dayImpSumService;
    @Autowired
    private RedisService redisService;

    @Override
    public List<ImpCollectionAnalysisDTO> queryKeyCollectionOriginalNew(Integer userId,String startDate) throws ParseException {
        // 查询用户管辖权限
        Set<Long> orgNoSet = redisService.queryUserOrgNoByUserId(userId);
        List<Long> orgNoList = Lists.newArrayList(orgNoSet);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdf.parse(startDate);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 1);

        Date tomorrow = c.getTime();
        String endDate= sdf.format(tomorrow);

        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        List<DayImpSum> dayImpSums = dayImpSumService.queryByParam(deviceType, startDate, endDate, orgNoList);
        // 分组
        ImmutableListMultimap<String, DayImpSum> multimap = Multimaps.index(dayImpSums, new Function<DayImpSum, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DayImpSum dayImpSum) {
                return dayImpSum.getQueueguid();
            }
        });

        // 汇总
        List<ImpCollectionAnalysisDTO> dataList = Lists.newArrayList();
        for (Map.Entry<String, Collection<DayImpSum>> entry : multimap.asMap().entrySet()) {
            String queueGuid = entry.getKey();
            ImmutableList<DayImpSum> list = multimap.get(queueGuid);
            Integer meterCnt = 0;     // 总户数
            Integer successCnt = 0;   // 总成功数
            Integer failCount = 0;    // 失败数
            Integer radioSuccCnt = 0; // 广播成功数
            Integer callSuccCnt = 0;  // 召测成功数
            for (DayImpSum dayImpSum : list) {
                meterCnt += dayImpSum.getMeterCnt();
                successCnt += dayImpSum.getSuccessCnt();
                failCount += dayImpSum.getFailCount();
                radioSuccCnt += dayImpSum.getRadioSuccCnt();
                callSuccCnt += dayImpSum.getCallSuccCnt();
            }
            DayImpSum dayImpSum = list.get(0);
            ImpCollectionAnalysisDTO dto = new ImpCollectionAnalysisDTO();
            dto.setCollectTime(dayImpSum.getCollectTime());
            dto.setSumCount(meterCnt);
            dto.setFailCount(failCount);

            // 重新计算成功率,乘以100后的百分比，并保留2位小数
            BigDecimal radioSuccRate = MathUtil.divide(radioSuccCnt * 100,meterCnt);
            dto.setRadioRate(String.valueOf(radioSuccRate));

            BigDecimal callSuccRate = MathUtil.divide(callSuccCnt * 100,meterCnt);
            dto.setCallRate(String.valueOf(callSuccRate));

            BigDecimal successRate = MathUtil.divide(successCnt * 100,meterCnt);
            dto.setSuccessRate(String.valueOf(successRate));
            dto.setQueueGuid(queueGuid);
            dataList.add(dto);
        }

        return dataList;
    }

    @Override
    public List<KeyCollectDTO> queryKeyCollectSucc(KeyCollectVo queryVo) {
        String queueGuid = queryVo.getQueueGuid();
        if (queueGuid == null) {
            queryVo.setQueueGuid("");
        }
        List<KeyCollectDTO> list = emDImpElecdataMapper.queryKeyCollectSucc(queryVo);
        return list;
    }

    @Override
    public List<KeyCollectDTO> queryKeyCollectFail(KeyCollectVo queryVo) {
        String queueGuid = queryVo.getQueueGuid();
        if (queueGuid == null) {
            queryVo.setQueueGuid("");
        }
        List<KeyCollectDTO> list = emDImpElecdataMapper.queryKeyCollectFail(queryVo);
        return list;
    }
}
