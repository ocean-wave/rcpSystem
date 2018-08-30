package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.DayImpSum;

import java.util.List;

/**
 * 汇总重点用户的周期采集成功率服务
 */
public interface DayImpSumService extends BaseService<DayImpSum> {

    List<DayImpSum> queryByParam(String deviceType,String startDate,String endDate,List<Long> orgNoList);
}
