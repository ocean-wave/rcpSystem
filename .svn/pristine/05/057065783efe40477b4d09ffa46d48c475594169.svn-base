package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterInitPowerMapper;
import cn.com.cdboost.collect.model.MeterInitPower;
import cn.com.cdboost.collect.service.MeterInitPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 初始电能示值服务实现类
 */
@Service
public class MeterInitPowerServiceImpl extends BaseServiceImpl<MeterInitPower> implements MeterInitPowerService{

    @Autowired
    private MeterInitPowerMapper meterInitPowerMapper;

    @Override
    public MeterInitPower queryByParam(String customerNo, String cno) {
        MeterInitPower param = new MeterInitPower();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        return meterInitPowerMapper.selectOne(param);
    }
}
