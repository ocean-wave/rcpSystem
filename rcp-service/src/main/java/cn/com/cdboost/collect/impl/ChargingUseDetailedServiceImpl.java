package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.dao.ChargingDeviceMapper;
import cn.com.cdboost.collect.dao.ChargingUseDetailedMapper;
import cn.com.cdboost.collect.dto.ChargingICUseDto;
import cn.com.cdboost.collect.dto.ListElectricDto;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.service.ChargingUseDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充电桩使用记录接口实现类
 */
@Service
public class ChargingUseDetailedServiceImpl extends BaseServiceImpl<ChargingUseDetailed> implements ChargingUseDetailedService {

    @Autowired
    private ChargingUseDetailedMapper chargingUseDetailedMapper;
    @Autowired
    private ChargingDeviceMapper chargingDeviceMapper;

    @Override
    public List<ChargingICUseDto> queryICCardUseList(ChargerICCardQueryVo queryVo) {
        List<ChargingICUseDto> chargingICUseDtos = chargingUseDetailedMapper.queryICCardUseList(queryVo);
        Integer total = chargingUseDetailedMapper.queryICCardUseTotal(queryVo);
        queryVo.setTotal(total.longValue());
        return chargingICUseDtos;
    }

    @Override
    public List<ListElectricDto> queryMonthList(String beginTime, String endTime, List<String> meterNos) {
        return chargingUseDetailedMapper.queryPowerAndFeeList(beginTime,endTime, meterNos);
    }

    @Override
    public ChargingUseDetailed queryChargingRecordByOpenId(String openId) {
        ChargingUseDetailed param = new ChargingUseDetailed();
        param.setOpenNo(openId);
        param.setState(0);
        return chargingUseDetailedMapper.selectOne(param);
    }

    @Override
    public ChargingUseDetailed queryChargingRecordByCustomerGuid(String customerGuid) {
//        ChargingUseDetailed param = new ChargingUseDetailed();
//        param.setCustomerGuid(customerGuid);
//        param.setState(0);
//        return chargingUseDetailedMapper.selectOne(param);
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerGuid",customerGuid);
        criteria.andEqualTo("state",0);
        criteria.andNotEqualTo("openMeans", ChargeAppConstant.OpenMeansConstant.ICCARD.getType());
        List<ChargingUseDetailed> list = chargingUseDetailedMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ChargingUseDetailed useDetailed = list.get(0);
        return useDetailed;
    }

    @Override
    public ChargingUseDetailed queryChargingRecordByPlieGuid(String chargingPlieGuid) {
        ChargingUseDetailed param = new ChargingUseDetailed();
        param.setChargingPlieGuid(chargingPlieGuid);
        param.setState(0);
        return chargingUseDetailedMapper.selectOne(param);
    }

    @Override
    public ChargingUseDetailed queryRecentUseRecord(String openId) {
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("openNo",openId);
        criteria.andEqualTo("state",1);
        condition.setOrderByClause("id DESC limit 1");
        List<ChargingUseDetailed> list = chargingUseDetailedMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ChargingUseDetailed chargingUseDetailed = list.get(0);
        return chargingUseDetailed;
    }

    @Override
    public ChargingUseDetailed queryRecentUseRecordByCustomerGuid(String customerGuid) {
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerGuid",customerGuid);
        criteria.andEqualTo("state",1);
        condition.setOrderByClause("id DESC limit 1");
        List<ChargingUseDetailed> list = chargingUseDetailedMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ChargingUseDetailed chargingUseDetailed = list.get(0);
        return chargingUseDetailed;
    }

    @Override
    public ChargingUseDetailed queryChargingRecordByChargingGuid(String chargingGuid) {
        ChargingUseDetailed param = new ChargingUseDetailed();
        param.setChargingGuid(chargingGuid);
        return chargingUseDetailedMapper.selectOne(param);
    }

    @Override
    public List<ChargingUseDetailed> queryByCustomerGuid(String customerGuid) {
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerGuid",customerGuid);
        criteria.andEqualTo("state",1);
        condition.setOrderByClause("id DESC");
        return chargingUseDetailedMapper.selectByCondition(condition);
    }

    @Override
    @Transactional
    public void updateUseTime(String deviceNo,String port, Integer useTime) {
        //通过deviceNo查询设备
        ChargingDevice param = new ChargingDevice();
        param.setDeviceNo(deviceNo);
        //param.setPort(Integer.parseInt(port));
        ChargingDevice chargingDevice = chargingDeviceMapper.selectOne(param);
        //修改实际使用时间
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("chargingPlieGuid",chargingDevice.getChargingPlieGuid());
        criteria.andEqualTo("state",0);
        ChargingUseDetailed chargingUseDetailed = new ChargingUseDetailed();
        chargingUseDetailed.setUseTime(useTime);
        chargingUseDetailedMapper.updateByConditionSelective(chargingUseDetailed,condition);
    }

    @Override
    @Transactional
    public void updateUsePower(String deviceNo, String port, String usePower) {
        if (!"0".equals(usePower) || !"".equals(usePower)){
            //通过deviceNo查询设备
            ChargingDevice param = new ChargingDevice();
            param.setDeviceNo(deviceNo);
            //param.setPort(Integer.parseInt(port));
            ChargingDevice chargingDevice = chargingDeviceMapper.selectOne(param);
            //修改实际使用电量
            Condition condition = new Condition(ChargingUseDetailed.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("chargingPlieGuid",chargingDevice.getChargingPlieGuid());
            criteria.andEqualTo("state",0);
            ChargingUseDetailed chargingUseDetailed = new ChargingUseDetailed();
            chargingUseDetailed.setUsePower(BigDecimal.valueOf(Double.parseDouble(usePower)));
            chargingUseDetailedMapper.updateByConditionSelective(chargingUseDetailed,condition);
        }
    }

    @Override
    public List<ChargingUseDetailed> queryRecentNlist(String cardId, Integer number) {
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("openNo",cardId);
        criteria.andEqualTo("state",1);
        condition.setOrderByClause("end_time desc limit " + number);
        return  chargingUseDetailedMapper.selectByCondition(condition);
    }

    @Override
    public List<ChargingUseDetailed> queryChargingIcCards(String customerGuid) {
        ChargingUseDetailed param = new ChargingUseDetailed();
        param.setCustomerGuid(customerGuid);
        param.setOpenMeans(ChargeAppConstant.OpenMeansConstant.ICCARD.getType());
        param.setState(0);
        return chargingUseDetailedMapper.select(param);
    }

    @Override
    public ChargingUseDetailed queryRecentRecord(String customerGuid, String cardId) {
        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerGuid",customerGuid);
        criteria.andEqualTo("openNo",cardId);
        condition.setOrderByClause("id desc limit 1");
        List<ChargingUseDetailed> list = chargingUseDetailedMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ChargingUseDetailed useDetailed = list.get(0);
        return useDetailed;
    }
}
