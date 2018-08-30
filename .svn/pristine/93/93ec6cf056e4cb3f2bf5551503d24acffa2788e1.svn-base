package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.DeviceStateCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceInfoDeviceStateMapper;
import cn.com.cdboost.collect.model.DeviceInfoDeviceState;
import cn.com.cdboost.collect.service.DeviceInfoDeviceStateService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 设备状态信息表接口服务实现类
 */
@Service
public class DeviceInfoDeviceStateServiceImpl  extends BaseServiceImpl<DeviceInfoDeviceState> implements DeviceInfoDeviceStateService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceInfoDeviceStateServiceImpl.class);
    @Autowired
    private DeviceInfoDeviceStateMapper deviceInfoDeviceStateMapper;

    @Override
    public void batchDeleteByCnos(List<String> cnos) {
        Condition condition = new Condition(DeviceInfoDeviceState.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno", cnos);
        deviceInfoDeviceStateMapper.deleteByCondition(condition);
    }

    @Override
    public void deleteByCno(String cno) {
        DeviceInfoDeviceState param = new DeviceInfoDeviceState();
        param.setCno(cno);
        deviceInfoDeviceStateMapper.delete(param);
    }

    @Override
    public List<DeviceInfoDeviceState> batchQueryByCnos(List<String> cnoList) {
        Condition condition = new Condition(DeviceInfoDeviceState.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno", cnoList);
        return deviceInfoDeviceStateMapper.selectByCondition(condition);
    }

    @Override
    public Map<String, Integer> queryDeviceStateMap(List<String> cnos) {
        Map<String, Integer> dataMap = Maps.newHashMap();
        List<DeviceInfoDeviceState> stateList = this.batchQueryByCnos(cnos);
        for (DeviceInfoDeviceState deviceState : stateList) {
            dataMap.put(deviceState.getCno(), deviceState.getIsOnline());
        }
        return dataMap;
    }

    @Override
    public Map<String, DeviceStateCacheVo> queryAll() {
        Map<String, DeviceStateCacheVo> map = deviceInfoDeviceStateMapper.queryAll();
        return map;
    }

    @Override
    public DeviceInfoDeviceState queryByCno(String cno) {
        DeviceInfoDeviceState param = new DeviceInfoDeviceState();
        param.setCno(cno);
        return deviceInfoDeviceStateMapper.selectOne(param);
    }
}
