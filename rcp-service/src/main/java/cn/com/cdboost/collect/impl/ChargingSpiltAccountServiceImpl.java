package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.ChargingSpiltAccountMapper;
import cn.com.cdboost.collect.model.ChargingSpiltAccount;
import cn.com.cdboost.collect.service.ChargingSpiltAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 分账记录表服务接口实现类
 */
@Service
public class ChargingSpiltAccountServiceImpl extends BaseServiceImpl<ChargingSpiltAccount> implements ChargingSpiltAccountService {

    @Autowired
    private ChargingSpiltAccountMapper chargingSpiltAccountMapper;

    @Override
    public List<ChargingSpiltAccount> queryNeedProcessRecord(Date beginTime, Date endTime) {
        Condition condition = new Condition(ChargingSpiltAccount.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andNotEqualTo("status",1);
        criteria.andBetween("createTime",beginTime,endTime);

        return chargingSpiltAccountMapper.selectByCondition(condition);
    }
}
