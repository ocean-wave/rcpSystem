package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.CustomerInfoCost;

import java.util.Collection;
import java.util.List;

/**
 * 客户档案费用表服务
 */
public interface CustomerInfoCostService extends BaseService<CustomerInfoCost> {

    // 根据用户唯一标识查询
    CustomerInfoCost queryByCustomerNo(String customerNo);

    // 根据customerNo，更新实体中非空的字段
    int updateByCustomerNoSelective(CustomerInfoCost param);

    // 批量查询用户费用记录
    List<CustomerInfoCost> batchQueryByCustomerNos(Collection<String> customerNos);
}
