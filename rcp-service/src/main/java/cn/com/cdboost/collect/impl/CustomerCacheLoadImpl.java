package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户档案缓存加载实现
 */
@Service
public class CustomerCacheLoadImpl implements CacheLoad {
    private static final Logger logger = LoggerFactory.getLogger(CustomerCacheLoadImpl.class);

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 查询所有客户档案
        List<CustomerCacheVo> customerCacheVos = customerInfoService.queryAll();
        long end1 = System.currentTimeMillis();
        logger.info("数据库查询所有客户档案缓存，耗时{}秒 ", (end1 - start) / 1000);

        // 设置缓存
        long start2 = System.currentTimeMillis();
        redisUtil.setCustomerCacheList(customerCacheVos);
        long end2 = System.currentTimeMillis();
        logger.info("设置redis缓存，耗时{}秒 ", (end2 - start2) / 1000);

        long end = System.currentTimeMillis();
        logger.info("加载客户档案相关缓存完毕，耗时{}秒 ", (end - start) / 1000);
    }

    @Override
    public void deleteCache() {

    }
}
