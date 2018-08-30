package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterCollectItemMapper;
import cn.com.cdboost.collect.model.MeterCollectItem;
import cn.com.cdboost.collect.service.MeterCollectItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 抄表项服务接口实现类
 */
@Service("meterCollectItemService")
public class MeterCollectItemServiceImpl extends BaseServiceImpl<MeterCollectItem> implements MeterCollectItemService {

    @Autowired
    private MeterCollectItemMapper meterCollectItemMapper;

    @Override
    public List<MeterCollectItem> queryByDeviceType(String deviceType) {
        MeterCollectItem param = new MeterCollectItem();
        param.setDeviceType(deviceType);
        return meterCollectItemMapper.select(param);
    }
}
