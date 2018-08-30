package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.MeterDayPower;
import cn.com.cdboost.collect.model.SummryData;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * 电表更换的其他参数项目服务接口
 */
public interface MeterDayPowerService extends BaseService<MeterDayPower> {

    SummryData querydatabyCustomerCno(String deviceCno, String customerNo) throws ParseException;

    BigDecimal currentDay(String deviceCno, String customerNo) ;
    BigDecimal lastDay(String deviceCno, String customerNo) ;
}
