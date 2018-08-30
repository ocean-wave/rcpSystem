package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.CustomerinfoCostMapper;
import cn.com.cdboost.collect.model.CustomerInfoCost;
import cn.com.cdboost.collect.service.CustomerInfoCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * 客户档案费用表服务实现类
 */
@Service
public class CustomerInfoCostServiceImpl extends BaseServiceImpl<CustomerInfoCost> implements CustomerInfoCostService {

    @Autowired
    private CustomerinfoCostMapper customerInfoCostMapper;

    @Override
    public CustomerInfoCost queryByCustomerNo(String customerNo) {
        CustomerInfoCost param = new CustomerInfoCost();
        param.setCustomerNo(customerNo);
        return customerInfoCostMapper.selectOne(param);
    }

    @Override
    public int updateByCustomerNoSelective(CustomerInfoCost param) {
        Condition condition = new Condition(CustomerInfoCost.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",param.getCustomerNo());
        return customerInfoCostMapper.updateByConditionSelective(param,condition);
    }

    @Override
    public List<CustomerInfoCost> batchQueryByCustomerNos(Collection<String> customerNos) {
        Condition condition = new Condition(CustomerInfoCost.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("customerNo",customerNos);
        return customerInfoCostMapper.selectByCondition(condition);
    }
}
