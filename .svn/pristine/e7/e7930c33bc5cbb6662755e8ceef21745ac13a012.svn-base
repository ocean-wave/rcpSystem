package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.CostCalculate;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * 用户每天的费用信息表服务
 */
public interface CostCalculateService extends BaseService<CostCalculate> {

    BigDecimal currentMonth(String deviceCno, String customerNo) throws ParseException;
    BigDecimal lastMonth(String deviceCno, String customerNo) ;
    // 查询最后一次算费记录
    CostCalculate queryLastRecord(String customerNo, String cno);
}
