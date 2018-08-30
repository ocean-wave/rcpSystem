package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.cache.DeviceStateCacheVo;
import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.DeviceInfoDeviceState;

import java.util.List;
import java.util.Map;

/**
 * 设备状态信息表接口服务
 */
public interface DeviceInfoDeviceStateService extends BaseService<DeviceInfoDeviceState>{
    // 根据cnos列表，批量删除
    void batchDeleteByCnos(List<String> cnos);
    // 根据cno，删除
    void deleteByCno(String cno);

    // 根据conList批量查询
    List<DeviceInfoDeviceState> batchQueryByCnos(List<String> cnoList);

    // 根据cnos集合查询对应的在线状态
    Map<String,Integer> queryDeviceStateMap(List<String> cnos);

    // 查询所有的设备状态
    Map<String, DeviceStateCacheVo> queryAll();

    // 根据cno查询
    DeviceInfoDeviceState queryByCno(String cno);
}
