package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.cache.DeviceRelationCacheVo;
import cn.com.cdboost.collect.cache.DeviceStateCacheVo;
import cn.com.cdboost.collect.model.MeterRelation;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.DeviceInfoDeviceStateService;
import cn.com.cdboost.collect.service.DeviceInfoService;
import cn.com.cdboost.collect.service.MeterRelationService;
import cn.com.cdboost.collect.util.RedisUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 设备缓存加载实现类
 */
@Service
public class DeviceCacheLoadImpl implements CacheLoad {
    private static final Logger logger = LoggerFactory.getLogger(DeviceCacheLoadImpl.class);

    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private MeterRelationService meterRelationService;
    @Autowired
    private DeviceInfoDeviceStateService deviceInfoDeviceStateService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 1、查询所有设备缓存信息
        List<DeviceCacheVo> deviceCacheVos = deviceInfoService.queryAllDeviceCache();
        long end1 = System.currentTimeMillis();
        logger.info("查询所有设备，耗时{}秒 ", (end1 - start) / 1000);

        // 2、设置设备基本信息缓存
        long start2 = System.currentTimeMillis();
        redisUtil.setDeviceCacheList(deviceCacheVos);
        long end2 = System.currentTimeMillis();
        logger.info("设备保存到redis，耗时{}秒 ", (end2 - start2) / 1000);

        // 3、设置设备关系缓存
        long start3 = System.currentTimeMillis();
        List<MeterRelation> meterRelations = meterRelationService.queryAll();
        long end3 = System.currentTimeMillis();
        logger.info("查询所有的设备关系，耗时{}秒 ", (end3 - start3) / 1000);

        long start4 = System.currentTimeMillis();
        List<DeviceRelationCacheVo> dataList = Lists.newArrayList();
        for (MeterRelation meterRelation : meterRelations) {
            DeviceRelationCacheVo cacheVo = new DeviceRelationCacheVo();
            BeanUtils.copyProperties(meterRelation,cacheVo);
            dataList.add(cacheVo);
        }
        long end4 = System.currentTimeMillis();
        logger.info("设备关系循环，耗时{}秒 ", (end4 - start4) / 1000);

        long start5 = System.currentTimeMillis();
        redisUtil.setDeviceRelationList(dataList);
        long end5 = System.currentTimeMillis();
        logger.info("设备关系循环，耗时{}秒 ", (end5 - start5) / 1000);

        // 4、设置设备状态缓存
        long start6 = System.currentTimeMillis();
        Map<String, DeviceStateCacheVo> stateMap = deviceInfoDeviceStateService.queryAll();
        long end6 = System.currentTimeMillis();
        logger.info("设备在线状态查询，耗时{}秒 ", (end6 - start6) / 1000);


        long start7 = System.currentTimeMillis();
        Collection<DeviceStateCacheVo> values = stateMap.values();
        redisUtil.setDeviceStateCollection(values);
        long end7 = System.currentTimeMillis();
        logger.info("设备在线状态redis设置，耗时{}秒 ", (end7 - start7) / 1000);

        long end = System.currentTimeMillis();
        logger.info("加载设备相关缓存完毕，耗时{}秒 ", (end - start) / 1000);

    }

    @Override
    public void deleteCache() {

    }

}
