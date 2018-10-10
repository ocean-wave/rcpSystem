package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dto.param.FeeOnOffParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.DeviceMeterParam;
import cn.com.cdboost.collect.param.CustomerOnOffParam;
import cn.com.cdboost.collect.param.FeeOnOffQueryParam;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.vo.OnOffMeterVo;
import cn.com.cdboost.collect.vo.Result;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 中天拉合闸dubbo服务接口实现类
 */
@Service
public class CustomerOnOffServiceImpl implements CustomerOnOffService {
    @Autowired
    FeeOnOffService feeOnOffService;
    @Autowired
    CustomerDevMapService customerDevMapService;
    @Autowired
    DeviceMeterParamService deviceMeterParamService;
    @Autowired
    CustomerInfoService customerInfoService;
    @Override
    public Integer customerOnOff(String id,FeeOnOffQueryParam queryParam) {

        FeeOnOffParam param = new FeeOnOffParam();
        BeanUtils.copyProperties(queryParam, param);
        param.setUserId(1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date date =  calendar.getTime();
        param.setDate(date);
        //String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        param.setSessionId(id);
        return  feeOnOffService.onOff(param);

    }

    @Override
    public FeeOnOffQueryParam customerOnOffParam(CustomerOnOffParam customerOnOffParam) {
        List<OnOffMeterVo> list= Lists.newArrayList();
        if(!StringUtils.isEmpty(customerOnOffParam.getMeterNo())){
            CustomerDevMap  customerDevMap = customerDevMapService.queryByCno(CNoUtil.CreateCNo(customerOnOffParam.getDeviceType(), customerOnOffParam.getMeterNo()));
            if(customerDevMap==null){
                throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
            }
            DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(customerDevMap.getCno());
            OnOffMeterVo onOffMeterVo=new OnOffMeterVo();
            onOffMeterVo.setCustomerNo(customerDevMap.getCustomerNo());
            onOffMeterVo.setJzqCno(deviceMeterParam.getJzqCno());
            onOffMeterVo.setDeviceCno(deviceMeterParam.getCno());
            list.add(onOffMeterVo);
        }else {
            CustomerInfo customerInfo=new CustomerInfo();
            customerInfo.setPropertyName(customerOnOffParam.getAddrCode());
            customerInfo = customerInfoService.selectOne(customerInfo);
            if(customerInfo==null){
                throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
            }
            List<CustomerDevMap> customerDevMaps = customerDevMapService.queryByCustomerNo(customerInfo.getCustomerNo());
            for (CustomerDevMap customerDevMap : customerDevMaps) {
                DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(customerDevMap.getCno());
                OnOffMeterVo onOffMeterVo=new OnOffMeterVo();
                onOffMeterVo.setCustomerNo(customerDevMap.getCustomerNo());
                onOffMeterVo.setJzqCno(deviceMeterParam.getJzqCno());
                onOffMeterVo.setDeviceCno(deviceMeterParam.getCno());
                list.add(onOffMeterVo);
            }

        }
        FeeOnOffQueryParam feeOnOffQueryParam=new FeeOnOffQueryParam();
        feeOnOffQueryParam.setGuid(UUID.randomUUID().toString());
        feeOnOffQueryParam.setOnOff(customerOnOffParam.getOnOff());
        feeOnOffQueryParam.setReason("1".equals(customerOnOffParam.getOnOff())?"付费通电":"欠费断电");
        feeOnOffQueryParam.setMeters(list);
        return feeOnOffQueryParam;
    }
}
