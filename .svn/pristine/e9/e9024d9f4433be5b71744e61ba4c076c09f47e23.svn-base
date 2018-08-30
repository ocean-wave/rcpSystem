package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceMeterConfigMapper;
import cn.com.cdboost.collect.model.DeviceMeterConfig;
import cn.com.cdboost.collect.service.DeviceMeterConfigService;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * 表计型号参数配置服务接口实现类
 */
@Service
public class DeviceMeterConfigServiceImpl extends BaseServiceImpl<DeviceMeterConfig> implements DeviceMeterConfigService {

    @Autowired
    private DeviceMeterConfigMapper deviceMeterConfigMapper;
    @Override
    public DeviceMeterConfig queryByMeterFactoryMeterMode(String meterfactory, String meterMode) {
        DeviceMeterConfig param = new DeviceMeterConfig();
        param.setMeterFactory(meterfactory);
        param.setMeterMode(meterMode);
        return deviceMeterConfigMapper.selectOne(param);
    }
    @Override
    public List<DeviceMeterConfig> queryByMeterFactory(String meterfactory) {
        DeviceMeterConfig param = new DeviceMeterConfig();
        param.setMeterFactory(meterfactory);
        return deviceMeterConfigMapper.select(param);
    }
    @Override
    public DeviceMeterConfig queryByParamFlag(String paramFlag) {
        DeviceMeterConfig param = new DeviceMeterConfig();
        param.setParamFlag(paramFlag);
        return deviceMeterConfigMapper.selectOne(param);
    }

    @Override
    public ImmutableMap<String, DeviceMeterConfig> batchQueryByParamFlags(Collection<String> paramFlags) {
        Condition condition = new Condition(DeviceMeterConfig.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("paramFlag",paramFlags);
        List<DeviceMeterConfig> deviceMeterConfigs = deviceMeterConfigMapper.selectByCondition(condition);

        // 转换成Map
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = Maps.uniqueIndex(deviceMeterConfigs, new Function<DeviceMeterConfig, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceMeterConfig deviceMeterConfig) {
                return deviceMeterConfig.getParamFlag();
            }
        });
        return meterConfigMap;
    }
}
