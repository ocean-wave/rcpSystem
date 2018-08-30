package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.cache.DeviceStateCacheVo;
import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.DeviceInfoDeviceState;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

public interface DeviceInfoDeviceStateMapper extends CommonMapper<DeviceInfoDeviceState> {

    @MapKey("cno")
    Map<String,DeviceStateCacheVo> queryAll();
}