package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.CostCalculateMapper;
import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.dto.param.DayElectricInfoResponseParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.param.DayElectricInfoParam;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.DayElectricInfoService;
import cn.com.cdboost.collect.service.DeviceInfoService;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.vo.DayElectricInfoResponseVo;
import cn.com.cdboost.collect.result.vo.Result;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
@Service
public class DayElectricInfoServiceImpl implements DayElectricInfoService {
    @Autowired
    CustomerDevMapService customerDevMapService;
    @Autowired
    private CostCalculateMapper costCalculateMapper;
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    CustomerInfoService customerInfoService;
    @Autowired
    MeterDayPowerMapper meterDayPowerMapper;

    @Override
    public List<DayElectricInfoResponseVo> queryDayElectricInfo(DayElectricInfoParam dayElectricInfoParam) {
        CustomerInfo customerInfo=new CustomerInfo();
        customerInfo.setPropertyName(dayElectricInfoParam.getAddrCode());
        customerInfo = customerInfoService.selectOne(customerInfo);
        if(customerInfo==null){
            throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
        }
        List<DayElectricInfoResponseParam> costCalculates = meterDayPowerMapper.queryDayElectricInfo(customerInfo.getCustomerNo(),DateUtil.CurrentDate());
        if(costCalculates==null||costCalculates.size()==0){
            throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
        }
        DayElectricInfoResponseParam dayElectricInfoResponseParam=new DayElectricInfoResponseParam();
        List list= Lists.newArrayList();
        BigDecimal power=new BigDecimal(0);
        BigDecimal readValue=new BigDecimal(0);
        for (DayElectricInfoResponseParam costCalculate : costCalculates) {
            if(costCalculate.getPower()==-1){
                power=power.add(BigDecimal.valueOf(0));
            }else {
                power=power.add(BigDecimal.valueOf(costCalculate.getPower()));
            }
            if(costCalculate.getReadValue()==-1){
                readValue=readValue.add(BigDecimal.valueOf(0));
            }else {
                readValue=readValue.add(BigDecimal.valueOf(costCalculate.getReadValue()));
            }
        }
        dayElectricInfoResponseParam.setDate(DateUtil.CurrentDate());
        dayElectricInfoResponseParam.setPower(power.floatValue());
        dayElectricInfoResponseParam.setReadValue(readValue.floatValue());
        list.add(dayElectricInfoResponseParam);
        return list;
    }
}
