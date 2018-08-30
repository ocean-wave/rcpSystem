package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.AnalAmountDTO;
import cn.com.cdboost.collect.dto.GetForYearInfo;
import cn.com.cdboost.collect.dto.PageSumDataGetInfo;
import cn.com.cdboost.collect.dto.SumMonthPerInfo;
import cn.com.cdboost.collect.dto.response.StaticCountInfo;

/**
 * 系统首页服务接口
 */
public interface FirstPageService {
    /**
     * 首页面汇总用户的档案未下发数量、异常数量等，根据用户ID查询
     * @param userId
     */
    StaticCountInfo pageStaticCount(long userId);

    /**
     * 页面汇总剩余金额的分布
     * @param userId
     * @return
     */
    AnalAmountDTO pageAnalAmount(Long userId, String deviceType);

    /**
     * 汇总当月的电、水、气的使用量
     * @param userId
     * @return
     */
    PageSumDataGetInfo pageSumDataGet(Long userId);

    /**
     * 查询一年内每月的电、水、气的使用量
     * @param customerNo
     * @return
     */
    GetForYearInfo sumGetForMonth(String customerNo, String deviceType, Long userId);


    /**
     * 汇总当月电量和上月电表的数据及环比
     * @param userId
     */
    SumMonthPerInfo pageSumMonthPer(Long userId, String deviceType);








}
