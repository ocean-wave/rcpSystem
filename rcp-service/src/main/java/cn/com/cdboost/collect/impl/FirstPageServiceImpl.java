package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.dao.SysConfigMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.GetForMonthQueryVo;
import cn.com.cdboost.collect.dto.response.StaticCountInfo;
import cn.com.cdboost.collect.service.FirstPageService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 系统首页服务接口实现类
 */
@Service("firstPageService")
public class FirstPageServiceImpl implements FirstPageService {
    private static final Logger logger = LoggerFactory.getLogger(FirstPageServiceImpl.class);

    @Autowired
    private SysConfigMapper sysConfigMapper;


    @Override
    public StaticCountInfo pageStaticCount(long userId) {
        Map<String, Object> searchMap = Maps.newHashMap();
        searchMap.put("userId", userId);
        sysConfigMapper.pageStaticCount(searchMap);

        StaticCountInfo info = new StaticCountInfo();
        info.setMeterErrCount(searchMap.get("meterErrCount").toString());
        info.setOffCount(searchMap.get("offCount").toString());
        info.setCstErr(searchMap.get("cstErr").toString());
        info.setCstTotal(searchMap.get("cstTotal").toString());

        return info;
    }

    /**
     * 页面汇总剩余金额的分布
     *
     * @param userId
     * @return
     */
    @Override
    public AnalAmountDTO pageAnalAmount(Long userId, String deviceType) {
        AnalAmountDTO analAmountDTO = sysConfigMapper.pageAnalAmount(String.valueOf(userId),deviceType);
        return analAmountDTO;
    }

    /**
     * 汇总当月的电、水、气的使用量
     * @param userId
     * @return
     */
    @Override
    public PageSumDataGetInfo pageSumDataGet(Long userId) {
        Map<String, Object> searchMap = Maps.newHashMap();
        searchMap.put("userId", userId);
        searchMap.put("startTime", "");
        searchMap.put("endTime", "");
        List<SumDataGetDTO> dtoList = sysConfigMapper.pageSumDataGet(searchMap);

        String startTime = searchMap.get("startTime").toString();
        String endTime = searchMap.get("endTime").toString();

        PageSumDataGetInfo info = new PageSumDataGetInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        info.setList(dtoList);

        return info;
    }

    /**
     * 查询一年内每月的电、水、气的使用量
     *
     * @param customerNo
     * @return
     */
    @Override
    public GetForYearInfo sumGetForMonth(String customerNo, String deviceType, Long userId) {
        if (StringUtils.isEmpty(customerNo)) {
            customerNo = "";
        }
        GetForYearInfo yearInfo = new GetForYearInfo();
        // 统计今年
        StatisticalYearDataInfo thisYear = this.get(customerNo, deviceType, userId, 0);
        yearInfo.setThisYear(thisYear);

        // 统计去年
        StatisticalYearDataInfo lastYear = this.get(customerNo, deviceType, userId, -1);
        yearInfo.setLastYear(lastYear);
        return yearInfo;
    }

    private StatisticalYearDataInfo get(String customerNo,String deviceType, Long userId, Integer flag) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, flag);
        int year = now.get(Calendar.YEAR);
        GetForMonthQueryVo queryVo = new GetForMonthQueryVo();
        queryVo.setYear(String.valueOf(year));
        queryVo.setDeviceType(deviceType);
        queryVo.setUserId(String.valueOf(userId));
        queryVo.setCustomerNo(customerNo);
        List<SumDataGetDTO> dataList = sysConfigMapper.sumGetForMonth(queryVo);
        StatisticalYearDataInfo dataInfo = new StatisticalYearDataInfo();
        dataInfo.setYear(year);
        dataInfo.setDataList(dataList);
        return dataInfo;
    }

    /**
     * 汇总当月电量和上月电表的数据及环比
     *
     * @param userId
     * @return
     */
    @Override
    public SumMonthPerInfo pageSumMonthPer(Long userId, String deviceType) {
        Map<String, Object> searchMap = Maps.newHashMap();
        searchMap.put("userId", userId);
        searchMap.put("deviceType", deviceType);

        sysConfigMapper.pageSumMonthPer(searchMap);
        SumMonthPerInfo info = new SumMonthPerInfo();
        info.setLastMonth(searchMap.get("lastMonth").toString());
        String currentMonth = searchMap.get("currentMonth").toString();
        if(!StringUtils.isEmpty(currentMonth)){
            BigDecimal currentMonthVal=new BigDecimal(currentMonth);
            if(currentMonthVal.intValue()<0){
                searchMap.put("currentMonth",0);
            }
        }
        info.setCurrentMonth(searchMap.get("currentMonth").toString());
        info.setPercent(searchMap.get("percent").toString());
        String currentDay = searchMap.get("currentDay").toString();
        if(!StringUtils.isEmpty(currentDay)){
            BigDecimal currentMonthVal=new BigDecimal(currentDay);
            if(currentMonthVal.intValue()<0){
                searchMap.put("currentDay",0);
            }
        }
        info.setCurrentDay(String.valueOf(searchMap.get("currentDay")));
        return info;
    }


}
