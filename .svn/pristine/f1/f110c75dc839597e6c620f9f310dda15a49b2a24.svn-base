package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.MonthSumData;

import java.util.List;

/**
 * 每月电量数据 接口服务
 */
public interface MonthSumDataService extends BaseService<MonthSumData> {
    // 查询半年电量统计
    List<MonthSumData> getNMonthSumDataByCno(String cno, int n);

    // 查询某年某月某个设备的用电量
    MonthSumData querySumData(int sumYear,int sumMonth,String cno);
}
