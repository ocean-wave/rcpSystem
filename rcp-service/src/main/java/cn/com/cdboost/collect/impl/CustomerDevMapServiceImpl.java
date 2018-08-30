package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.CostCalculateMapper;
import cn.com.cdboost.collect.dao.CustomerDevMapMapper;
import cn.com.cdboost.collect.dao.DayMeterSumMapper;
import cn.com.cdboost.collect.dao.DeviceMeterConfigMapper;
import cn.com.cdboost.collect.dto.CustomerInfo4App;
import cn.com.cdboost.collect.dto.DeviceInfoResponse;
import cn.com.cdboost.collect.dto.param.DaySettlementParam;
import cn.com.cdboost.collect.dto.response.DaySettlementResponse;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户与设备映射表接口服务实现类
 */
@Service("customerDevMapService")
public class CustomerDevMapServiceImpl extends BaseServiceImpl<CustomerDevMap> implements CustomerDevMapService {

    @Autowired
    private CostCalculateMapper CostCalculateMapper;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerDevMapMapper customerDevMapMapper;
    @Autowired
    private DeviceMeterConfigMapper deviceMeterConfigMapper;
    @Autowired
    private DayMeterSumMapper dayMeterSumMapper;
    @Override
    public PageInfo queryDaySettlement(DaySettlementParam daySettlementParam,List<Long> orgNoList) {
        List<DaySettlementResponse> result=Lists.newArrayList();
        PageHelper.startPage(daySettlementParam.getPageNumber(),daySettlementParam.getPageSize()," t.id desc");
        List<DayMeterSum> dayMeterSums = dayMeterSumMapper.queryByUserOrgDay(daySettlementParam,orgNoList);
        PageInfo pageInfo=new  PageInfo(dayMeterSums);
        List<DayMeterSum> list = pageInfo.getList();
        for (DayMeterSum dayMeterSum : list) {
            DaySettlementResponse daySettlementResponse=new DaySettlementResponse();
            BigDecimal dayMoney = dayMeterSum.getDayMoney();
            if(dayMoney==null||dayMeterSum.getDayMoney().intValue()==-1){
                daySettlementResponse.setCalcMoney("");
            }else{
                daySettlementResponse.setCalcMoney(String.valueOf(dayMeterSum.getDayMoney()));
            }
            daySettlementResponse.setCollectDate(DateUtil.formatDate(dayMeterSum.getCreateTime()));
            BigDecimal dayEqValue = dayMeterSum.getDayPower();
            if (dayEqValue==null||dayEqValue.intValue()==-1){
                daySettlementResponse.setEqValue(BigDecimal.valueOf(0));
            }else{
                daySettlementResponse.setEqValue(dayEqValue);
            }
            daySettlementResponse.setEqTime(DateUtil.formatDay(dayMeterSum.getCollectDate()));
            CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(dayMeterSum.getCustomerNo());
            daySettlementResponse.setCustomerName(customerInfo.getCustomerName());
            daySettlementResponse.setCustomerAddr(customerInfo.getCustomerAddr());
            daySettlementResponse.setCustomerContact(customerInfo.getCustomerContact());
            daySettlementResponse.setPropertyName(customerInfo.getPropertyName());
            daySettlementResponse.setMeterUserNo(dayMeterSum.getMeterUserNo());
            daySettlementResponse.setDeviceNo(CNoUtil.getDeviceNoByCno(dayMeterSum.getCno()));
            result.add(daySettlementResponse);
        }
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    public PageInfo queryMonthSettlement(DaySettlementParam daySettlementParam, List<Long> orgNoList) throws ParseException {
        List<DaySettlementResponse> result=Lists.newArrayList();
        String startDay = DateUtil.getInputStartMonthDate(daySettlementParam.getStartDate());
        String endDay = DateUtil.getInputEndMonthDate(daySettlementParam.getEndDate());
        daySettlementParam.setStartDate(startDay);
        daySettlementParam.setEndDate(endDay);
        PageHelper.startPage(daySettlementParam.getPageNumber(),daySettlementParam.getPageSize(),"t.id desc");
        List<CostCalculate> costCalculates = CostCalculateMapper.queryByUserOrgMonth(daySettlementParam, orgNoList);
        PageInfo pageInfo=new  PageInfo(costCalculates);
        List<CostCalculate> list = pageInfo.getList();
        for (CostCalculate costCalculate : list) {
            DaySettlementResponse daySettlementResponse=new DaySettlementResponse();
            BigDecimal calcMoney = costCalculate.getCalcMoney();
            if (calcMoney==null||calcMoney.intValue()==-1){
                daySettlementResponse.setCalcMoney("");
            }else{
                daySettlementResponse.setCalcMoney(String.valueOf(calcMoney));
            }
            daySettlementResponse.setCollectDate(DateUtil.formatDate(costCalculate.getCreateTime()));
            BigDecimal eqValue = costCalculate.getEqValue();
            if (eqValue==null||eqValue.intValue()==-1){
                daySettlementResponse.setEqValue(BigDecimal.valueOf(0));
            }else{
                daySettlementResponse.setEqValue(eqValue);
            }
            daySettlementResponse.setEqTime(DateUtil.formatDay(costCalculate.getCalcData()));
            CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(costCalculate.getCustomerNo());
            daySettlementResponse.setCustomerName(customerInfo.getCustomerName());
            daySettlementResponse.setCustomerAddr(customerInfo.getCustomerAddr());
            daySettlementResponse.setCustomerContact(customerInfo.getCustomerContact());
            daySettlementResponse.setPropertyName(customerInfo.getPropertyName());
            daySettlementResponse.setMeterUserNo(costCalculate.getMeterUserNo());
            daySettlementResponse.setDeviceNo(CNoUtil.getDeviceNoByCno(costCalculate.getCno()));
            result.add(daySettlementResponse);
        }
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    public List queryByFactory(String meterFactory,String deviceType) {
        List maplist=Lists.newArrayList();
        DeviceMeterConfig deviceMeterConfig=new DeviceMeterConfig();
        deviceMeterConfig.setMeterFactory(meterFactory);
        deviceMeterConfig.setDeviceType(Integer.parseInt(deviceType));
        List<DeviceMeterConfig> select = deviceMeterConfigMapper.select(deviceMeterConfig);
        for (DeviceMeterConfig meterConfig : select) {
            Map map= Maps.newHashMap();
            map.put("meterMode",meterConfig.getMeterMode());
            map.put("paramFlag",meterConfig.getParamFlag());
            map.put("meterCategory",meterConfig.getMeterCategory());
            map.put("commRule",meterConfig.getCommRule());
            map.put("commFactorCnt",meterConfig.getCommFactorCnt());
            map.put("meterType",meterConfig.getMeterType());
            map.put("deviceType",meterConfig.getDeviceType());
            map.put("keyGrade",meterConfig.getKeyGrade());
            map.put("meterUserName",meterConfig.getMeterUserName());
            map.put("meterUserPwd",meterConfig.getMeterUserPwd());
            map.put("isValveControl",meterConfig.getIsValveControl());
            map.put("commBaudrate",meterConfig.getCommBaudrate());
            maplist.add(map);
        }

        return maplist;
    }



    @Override
    @Transactional
    public void updateByCnoSelective(CustomerDevMap devMap) throws BusinessException{
        String cno = devMap.getCno();
        if (StringUtils.isEmpty(cno)) {
            throw new BusinessException("cno不能为空");
        }

        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("cno",cno);
        customerDevMapMapper.updateByConditionSelective(devMap,condition);
    }

    @Override
    public CustomerDevMap queryByCno(String cno) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCno(cno);
        param.setIsChange(0);
        return customerDevMapMapper.selectOne(param);
    }

    @Override
    public CustomerDevMap queryByCno4ChangeDetail(String cno) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCno(cno);
        return customerDevMapMapper.selectOne(param);
    }

    @Override
    @Transactional
    public void batchDeleteByCustomerNos(List<String> customerNos) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("customerNo",customerNos);
        customerDevMapMapper.deleteByCondition(condition);
    }

    @Override
    public int deleteByCustomerNo(String customerNo) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        return customerDevMapMapper.delete(param);
    }

    @Override
    public List<CustomerDevMap> batchQueryByCustomerNos(List<String> customerNos) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("customerNo",customerNos);
        criteria.andEqualTo("isChange",0);
        return customerDevMapMapper.selectByCondition(condition);
    }

    @Override
    public List<CustomerDevMap> query4DeleteByCustomerNo(String customerNo) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        param.setIsChange(0);
        return customerDevMapMapper.select(param);
    }

    @Override
    public List<CustomerDevMap> queryByCustomerNo(String customerNo) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        param.setIsChange(0);
        return customerDevMapMapper.select(param);
    }

    @Override
    public List<CustomerDevMap> batchQueryByCnos(List<String> cnos) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno",cnos);
        return customerDevMapMapper.selectByCondition(condition);
    }

    @Override
    public List<CustomerInfo4App> selectCustomerInfosByCnos(List<String> cnoList) {
        return customerDevMapMapper.selectCustomerInfosByCnos(cnoList);
    }

    @Override
    public Boolean checkMeterUserNoExist4Add(String deviceType,String meterUserNo) {
        CustomerDevMap param = new CustomerDevMap();
        param.setDeviceType(deviceType);
        param.setMeterUserNo(meterUserNo);
        param.setIsChange(0);
        CustomerDevMap devMap = customerDevMapMapper.selectOne(param);
        if (devMap != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean checkMeterUserNoExist4Edit(String cno, String meterUserNo) {
        String deviceType = cno.substring(0, 2);
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("deviceType",deviceType);
        criteria.andEqualTo("meterUserNo",meterUserNo);
        criteria.andEqualTo("isChange",0);
        criteria.andNotEqualTo("cno",cno);
        List<CustomerDevMap> customerDevMaps = customerDevMapMapper.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(customerDevMaps)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public List<DeviceInfoResponse> queryDeviceInfo(String customerNo, String deviceType) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        param.setDeviceType(deviceType);
        param.setIsChange(0);
        List<CustomerDevMap> customerDevMaps = customerDevMapMapper.select(param);
        List<DeviceInfoResponse> list = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(customerDevMaps)) {
            for (CustomerDevMap customerDevMap : customerDevMaps) {
                DeviceInfoResponse response = new DeviceInfoResponse();
                String cno = customerDevMap.getCno();
                String meterUserNo = customerDevMap.getMeterUserNo();
                response.setValue(meterUserNo + "," + cno);
                String deviceNo = CNoUtil.getDeviceNoByCno(cno);
                response.setText(deviceNo);
                list.add(response);
            }
        }
        return list;
    }

    @Override
    public CustomerDevMap queryByMeterUserNo(String deviceType, String meterUserNo) {
        CustomerDevMap param = new CustomerDevMap();
        param.setMeterUserNo(meterUserNo);
        param.setDeviceType(deviceType);
        param.setIsChange(0);
        return customerDevMapMapper.selectOne(param);
    }

    @Override
    public CustomerDevMap queryByParams(String deviceType, String meterUserNo, Set<String> customerNos) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("deviceType",deviceType);
        criteria.andEqualTo("meterUserNo",meterUserNo);
        criteria.andEqualTo("isChange",0);
        criteria.andIn("customerNo",customerNos);
        List<CustomerDevMap> devMaps = customerDevMapMapper.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(devMaps)) {
            return devMaps.get(0);
        }
        return null;
    }

    @Override
    public List<CustomerDevMap> queryByParams(String deviceType, Set<String> customerNos) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("deviceType",deviceType);
        criteria.andIn("customerNo",customerNos);
        criteria.andEqualTo("isChange",0);
        List<CustomerDevMap> devMaps = customerDevMapMapper.selectByCondition(condition);
        return devMaps;
    }

    @Override
    public List<CustomerDevMap> queryByParams(String customerNo, String deviceType) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("deviceType",deviceType);
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andEqualTo("isChange",0);
        List<CustomerDevMap> devMaps = customerDevMapMapper.selectByCondition(condition);
        return devMaps;
    }

    @Override
    public CustomerDevMap queryByCustomerNoAndCno(String customerNo, String cno) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        param.setIsChange(0);
        return customerDevMapMapper.selectOne(param);
    }

    @Override
    public List<CustomerDevMap> queryList(Set<String> deviceTypeSet, Set<String> meterUserNoSet) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("deviceType",deviceTypeSet);
        criteria.andIn("meterUserNo",meterUserNoSet);
        criteria.andEqualTo("isChange",0);
        List<CustomerDevMap> devMaps = customerDevMapMapper.selectByCondition(condition);
        return devMaps;
    }

    @Override
    public List<CustomerDevMap> queryListByDeviceType(String deviceType, Set<String> meterUserNoSet) {
        Condition condition = new Condition(CustomerDevMap.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("deviceType",deviceType);
        criteria.andIn("meterUserNo",meterUserNoSet);
        criteria.andEqualTo("isChange",0);
        List<CustomerDevMap> devMaps = customerDevMapMapper.selectByCondition(condition);
        return devMaps;
    }

    @Override
    public List<DeviceMapCacheVo> queryAll() {
        return customerDevMapMapper.queryAll();
    }
}
