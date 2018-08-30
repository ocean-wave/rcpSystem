package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.CustomerInfoCost;
import cn.com.cdboost.collect.model.MeterDayPower;
import cn.com.cdboost.collect.model.SummryData;
import cn.com.cdboost.collect.service.CostCalculateService;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.service.CustomerInfoCostService;
import cn.com.cdboost.collect.service.MeterDayPowerService;
import cn.com.cdboost.collect.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * 汇总信息
 */
@Service
public class MeterDayPowerServiceImpl extends BaseServiceImpl<MeterDayPower> implements MeterDayPowerService {

    @Autowired
    MeterDayPowerMapper meterDayPowerMapper;
    @Autowired
    CustomerDevMapService customerDevMapService;
    @Autowired
    CostCalculateService costCalculateService;
    @Autowired
    CustomerInfoCostService customerInfoCostService;
    @Override
    public SummryData querydatabyCustomerCno(String deviceCno, String customerNo) throws ParseException {
        BigDecimal currentDay = currentDay(deviceCno, customerNo);
        BigDecimal lastDay = lastDay(deviceCno, customerNo);
        BigDecimal currentMonth = costCalculateService.currentMonth(deviceCno, customerNo);
        BigDecimal lastMonth = costCalculateService.lastMonth(deviceCno, customerNo);
        CustomerInfoCost customerInfoCost=customerInfoCostService.queryByCustomerNo(customerNo);
        SummryData summryData=new SummryData();
        BigDecimal remainAmount = customerInfoCost.getRemainAmount();
        if(remainAmount==null){
            summryData.setBalance(null);
        }else {
            summryData.setBalance(remainAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        if(currentDay.intValue()==-1){
            currentDay=null;
        }
        summryData.setCurrentDayElect(currentDay);
        if(currentMonth.intValue()==-1){
            currentMonth=null;
        }
        summryData.setCurrentMonthElect(currentMonth);
        if(lastDay.intValue()==-1){
            lastDay=null;
        }
        summryData.setLastDayElect(lastDay);
        if(lastMonth.intValue()==-1){
            lastMonth=null;
        }
        summryData.setLastMonthElect(lastMonth);
        summryData.setPayCount(customerInfoCost.getPayCount());
        summryData.setPayMoney(customerInfoCost.getTotalAmount());
        if(customerInfoCost.getCalcTime()!=null){
            summryData.setLastFlushTime(DateUtil.formatDate(customerInfoCost.getCalcTime()));
        }
        return summryData;
    }

    @Override
    public BigDecimal currentDay(String deviceCno, String customerNo) {
        CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
        String currentDay = DateUtil.getCurrentDayDate();
        Condition condition = new Condition(MeterDayPower.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("calcData",currentDay);
        criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
        criteria.andEqualTo("customerNo", customerNo);
        criteria.andEqualTo("calcDataType",1);
        condition.setOrderByClause(" id desc limit 1");
        List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(select)){
            MeterDayPower meterDayPower = select.get(0);
            return meterDayPower.getDayEqValue();
        }
        return BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal lastDay(String deviceCno, String customerNo) {
        CustomerDevMap customerDevMap = customerDevMapService.queryByCno(deviceCno);
        String lastDayDate = DateUtil.getLastDayDate();
        Condition condition = new Condition(MeterDayPower.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("calcData",lastDayDate);
        criteria.andEqualTo("meterUserNo", customerDevMap.getMeterUserNo());
        criteria.andEqualTo("customerNo", customerNo);
        criteria.andEqualTo("calcDataType",0);
        condition.setOrderByClause(" id desc ");
        List<MeterDayPower> select = meterDayPowerMapper.selectByCondition(condition);
        BigDecimal dayEqValue=new BigDecimal(0);
        for (MeterDayPower meterDayPower : select) {
            if(meterDayPower.getDayEqValue()==null||meterDayPower.getDayEqValue().intValue()==-1){
                meterDayPower.setDayEqValue(BigDecimal.valueOf(0));
            }
            dayEqValue = dayEqValue.add(meterDayPower.getDayEqValue());
        }
        return dayEqValue;
    }
}
