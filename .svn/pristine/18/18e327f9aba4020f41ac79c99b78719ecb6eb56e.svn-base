package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.CostCalculateMapper;
import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.model.CostCalculate;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.MeterDayPower;
import cn.com.cdboost.collect.service.CostCalculateService;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.service.SysConfigService;
import cn.com.cdboost.collect.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户每天的费用信息表服务实现类
 */
@Service
public class CostCalculateServiceImpl extends BaseServiceImpl<CostCalculate> implements CostCalculateService {

    @Autowired
    private CostCalculateMapper costCalculateMapper;
    @Autowired
    private MeterDayPowerMapper meterDayPowerMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private CustomerDevMapService customerDevMapService;

    @Override
    public BigDecimal currentMonth(String deviceCno, String customerNo)   {
        String balanceTime = sysConfigService.queryByConfigName("balanceTime").getConfigValue();
        String currentDateTime = DateUtil.CurrentDateTime();
        String currentDateTimeInt=DateUtil.CurrentDateTimeInt();
        String nextDayTime = DateUtil.nextDayTime();
        String currentMonthBalanceDayTime = DateUtil.getCurrentMonthBalanceDayTime(Integer.valueOf(balanceTime));
        String lastMonthBalanceTime = DateUtil.getLastMonthBalanceDayTime(Integer.valueOf(balanceTime));
        String nextMonthBalanceDayTime = DateUtil.getNextMonthBalanceDayTime(Integer.valueOf(balanceTime));
        int i = DateUtil.compareDate(currentDateTime, currentMonthBalanceDayTime);
        if(i==-1){
            Condition condition = new Condition(CostCalculate.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andLessThan("calcData",currentMonthBalanceDayTime);
            criteria.andGreaterThanOrEqualTo("calcData",lastMonthBalanceTime);
            CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
            criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria.andEqualTo("customerNo", customerNo);
            criteria.andEqualTo("calcDataType",0);
            criteria.andCondition("calc_data<>cast(current_timestamp as date)");
            condition.setOrderByClause(" id desc ");
            List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
            BigDecimal dayEqValue=new BigDecimal(0);
            for (MeterDayPower costCalculate : select) {
                if(costCalculate.getDayEqValue()==null){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValue=dayEqValue.add(costCalculate.getDayEqValue());
            }
            Condition condition1 = new Condition(CostCalculate.class);
            Example.Criteria criteria1 = condition1.createCriteria();
            criteria1.andLessThan("calcData",nextDayTime);
            criteria1.andGreaterThanOrEqualTo("calcData",currentDateTimeInt);
            criteria1.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria1.andEqualTo("customerNo", customerNo);
            criteria1.andEqualTo("calcDataType",1);
            condition1.setOrderByClause(" id desc limit 1 ");
            List<MeterDayPower> select1 = meterDayPowerMapper.selectByCondition(condition1);
            BigDecimal dayEqValueInTime=new BigDecimal(0);
            for (MeterDayPower costCalculate : select1) {
                if(costCalculate.getDayEqValue()==null){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValueInTime=dayEqValueInTime.add(costCalculate.getDayEqValue());
            }
            BigDecimal currentMonth = dayEqValue.add(dayEqValueInTime).setScale(2, BigDecimal.ROUND_HALF_UP);
            currentMonth = getBigDecimal(deviceCno, customerNo, currentDateTimeInt, nextDayTime, currentMonth);
            return currentMonth;
        }else {
            Condition condition = new Condition(CostCalculate.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andLessThan("calcData",nextMonthBalanceDayTime);
            criteria.andGreaterThanOrEqualTo("calcData",currentMonthBalanceDayTime);
            CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
            criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria.andEqualTo("customerNo", customerNo);
            criteria.andEqualTo("calcDataType",0);
            criteria.andCondition("calc_data<>cast(current_timestamp as date)");
            condition.setOrderByClause(" id desc ");
            List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
            BigDecimal dayEqValue=new BigDecimal(0);
            for (MeterDayPower costCalculate : select) {
                if(costCalculate.getDayEqValue()==null||costCalculate.getDayEqValue().intValue()==-1){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValue=dayEqValue.add(costCalculate.getDayEqValue());
            }
            Condition condition1 = new Condition(CostCalculate.class);
            Example.Criteria criteria1 = condition1.createCriteria();
            criteria1.andLessThan("calcData",nextDayTime);
            criteria1.andGreaterThanOrEqualTo("calcData",currentDateTimeInt);
            criteria1.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria1.andEqualTo("customerNo", customerNo);
            criteria1.andEqualTo("calcDataType",1);
            condition1.setOrderByClause(" id desc limit 1 ");
            List<MeterDayPower> select1 = meterDayPowerMapper.selectByCondition(condition1);
            BigDecimal dayEqValueInTime=new BigDecimal(0);
            for (MeterDayPower costCalculate : select1) {
                if(costCalculate.getDayEqValue()==null||costCalculate.getDayEqValue().intValue()==-1){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValueInTime=dayEqValueInTime.add(costCalculate.getDayEqValue());
            }
            BigDecimal currentMonth = dayEqValue.add(dayEqValueInTime).setScale(2, BigDecimal.ROUND_HALF_UP);
            currentMonth = getBigDecimal(deviceCno, customerNo, currentDateTimeInt, nextDayTime, currentMonth);
            return currentMonth;
        }
    }

    private BigDecimal getBigDecimal(String deviceCno, String customerNo, String currentDateTimeInt, String nextDayTime, BigDecimal currentMonth) {
        if(currentMonth.intValue()==0){
            Condition condition2 = new Condition(CostCalculate.class);
            Example.Criteria criteria2 = condition2.createCriteria();
            criteria2.andLessThan("calcData",nextDayTime);
            criteria2.andGreaterThanOrEqualTo("calcData",currentDateTimeInt);
            CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
            criteria2.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria2.andEqualTo("customerNo", customerNo);
            criteria2.andEqualTo("calcDataType",0);
            condition2.setOrderByClause(" id desc limit 1 ");
            List<MeterDayPower> select2 = meterDayPowerMapper.selectByCondition(condition2);
            for (MeterDayPower costCalculate : select2) {
                if(costCalculate.getDayEqValue()==null){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                currentMonth=currentMonth.add(costCalculate.getDayEqValue());
            }
        }
        return currentMonth;
    }

    @Override
    public BigDecimal lastMonth(String deviceCno, String customerNo) {
        String balanceTime = sysConfigService.queryByConfigName("balanceTime").getConfigValue();
        String currentDateTime = DateUtil.CurrentDateTime();
        String currentMonthBalanceDayTime = DateUtil.getCurrentMonthBalanceDayTime(Integer.valueOf(balanceTime));
        String lastMonthBalanceTime = DateUtil.getLastMonthBalanceDayTime(Integer.valueOf(balanceTime));
        int i = DateUtil.compareDate(currentDateTime, currentMonthBalanceDayTime);
        if(i==-1){
            String lastlastMonthBalanceDayTime=DateUtil.getLastlastMonthBalanceDayTime(Integer.valueOf(balanceTime));
            Condition condition = new Condition(CostCalculate.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andLessThan("calcData",lastMonthBalanceTime);
            criteria.andGreaterThanOrEqualTo("calcData",lastlastMonthBalanceDayTime);
            CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
            criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria.andEqualTo("customerNo", customerNo);
            criteria.andEqualTo("calcDataType",0);
            condition.setOrderByClause(" id desc ");
            List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
            BigDecimal dayEqValue=new BigDecimal(0);
            for (MeterDayPower costCalculate : select) {
                if(costCalculate.getDayEqValue()==null||costCalculate.getDayEqValue().intValue()==-1){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValue=dayEqValue.add(costCalculate.getDayEqValue());
            }
            BigDecimal currentMonth = dayEqValue.setScale(2, BigDecimal.ROUND_HALF_UP);
            return currentMonth;
        }else {
            Condition condition = new Condition(CostCalculate.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andLessThan("calcData",currentMonthBalanceDayTime);
            criteria.andGreaterThanOrEqualTo("calcData",lastMonthBalanceTime);
            CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
            criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
            criteria.andEqualTo("customerNo", customerNo);
            criteria.andEqualTo("calcDataType",0);
            condition.setOrderByClause(" id desc ");
            List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
            BigDecimal dayEqValue=new BigDecimal(0);
            for (MeterDayPower costCalculate : select) {
                if(costCalculate.getDayEqValue()==null||costCalculate.getDayEqValue().intValue()==-1){
                    costCalculate.setDayEqValue(BigDecimal.valueOf(0));
                }
                dayEqValue=dayEqValue.add(costCalculate.getDayEqValue());
            }
            BigDecimal currentMonth = dayEqValue.setScale(2, BigDecimal.ROUND_HALF_UP);
            return currentMonth;
        }
    }

    @Override
    public CostCalculate queryLastRecord(String customerNo, String cno) {
        Condition condition = new Condition(CostCalculate.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andEqualTo("cno",cno);
        condition.setOrderByClause("id desc  limit 1");
        List<CostCalculate> costCalculates = costCalculateMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(costCalculates)) {
            return null;
        }
        CostCalculate costCalculate = costCalculates.get(0);
        return costCalculate;
    }
}
