package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户设备关系缓存
 */
@Service
public class DeviceMapCacheLoadImpl implements CacheLoad {
    private static final Logger logger = LoggerFactory.getLogger(DeviceMapCacheLoadImpl.class);

    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 查询所有客户设备关系
        List<DeviceMapCacheVo> cacheVos = customerDevMapService.queryAll();
        long end1 = System.currentTimeMillis();
        logger.info("数据库查询客户设备关系完毕，耗时{}秒 ", (end1 - start) / 1000);

        long start2 = System.currentTimeMillis();
        // redis设置
        redisUtil.setDeviceMapCacheList(cacheVos);
        long end2 = System.currentTimeMillis();
        logger.info("客户设备关系缓存redis设置完毕，耗时{}秒 ", (end2 - start2) / 1000);

        long end = System.currentTimeMillis();
        logger.info("加载客户设备关系缓存完毕，耗时{}秒 ", (end - start) / 1000);
    }

    @Override
    public void deleteCache() {

    }
}
