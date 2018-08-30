package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.DeviceMeterConfig;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.List;

/**
 * 表计型号参数配置服务接口
 */
public interface DeviceMeterConfigService extends BaseService<DeviceMeterConfig> {
    // 根据参数标识查询
    DeviceMeterConfig queryByMeterFactoryMeterMode(String meterfactory, String meterMode);
    // 根据参数标识查询
    List<DeviceMeterConfig> queryByMeterFactory(String meterfactory);
    // 根据参数标识查询
    DeviceMeterConfig queryByParamFlag(String paramFlag);

    // 根据paramFlag集合，批量查询
    ImmutableMap<String, DeviceMeterConfig> batchQueryByParamFlags(Collection<String> paramFlags);
}
