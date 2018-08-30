package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.SmokeDeviceSelectTotalDB;
import cn.com.cdboost.collect.model.SmokeDevice;

import java.util.List;

public interface SmokeDeviceMapper extends CommonMapper<SmokeDevice> {
    List<SmokeDeviceSelectTotalDB> SmokeDeviceSelectTotal();
}