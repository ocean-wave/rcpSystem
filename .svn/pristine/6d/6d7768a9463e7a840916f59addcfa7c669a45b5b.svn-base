package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.CustomerinfoCostMapper;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.CustomerInfoCost;
import cn.com.cdboost.collect.param.RemainAmount;
import cn.com.cdboost.collect.param.RemainAmountResponse;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.FeePayService;
import cn.com.cdboost.collect.service.RemainAmountService;
import cn.com.cdboost.collect.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
@Service
public class RemainAmountServiceImpl implements RemainAmountService {

    @Autowired
    CustomerInfoService customerInfoService;
    @Autowired
    FeePayService feePayService;
    @Autowired
    CustomerinfoCostMapper customerinfoCostMapper;
    @Override
    public RemainAmountResponse queryRemainAmount(RemainAmount remainAmount) {
        CustomerInfo customerInfo=new CustomerInfo();
        CustomerInfoCost customerInfoCost =new CustomerInfoCost();
        customerInfo.setPropertyName(remainAmount.getAddrCode());
        customerInfo=customerInfoService.selectOne(customerInfo);
        if(customerInfo==null){
            throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
        }
        customerInfoCost.setCustomerNo(customerInfo.getCustomerNo());
        customerInfoCost=customerinfoCostMapper.selectOne(customerInfoCost);
        RemainAmountResponse remainAmountResponse=new RemainAmountResponse();
        remainAmountResponse.setBalance(customerInfoCost.getRemainAmount().floatValue());
        remainAmountResponse.setCollectDate(customerInfoCost.getCalcTime());
        return remainAmountResponse;
    }
}
