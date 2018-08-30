package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.CustomerInfo4App;
import cn.com.cdboost.collect.model.CustomerDevMap;

import java.util.List;

public interface CustomerDevMapMapper extends CommonMapper<CustomerDevMap> {

    /**
     * 根据cno数组查询对应的客户信息
     * @param cnoList
     * @return
     */
    List<CustomerInfo4App> selectCustomerInfosByCnos(List<String> cnoList);

    // 查询所有
    List<DeviceMapCacheVo> queryAll();
}