package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterSuppQueryMapper;
import cn.com.cdboost.collect.model.MeterSuppQuery;
import cn.com.cdboost.collect.service.MeterSuppQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 补抄工单请求信息表服务接口实现类
 */
@Service("meterSuppQueryService")
public class MeterSuppQueryServiceImpl extends BaseServiceImpl<MeterSuppQuery> implements MeterSuppQueryService {

    @Autowired
    private MeterSuppQueryMapper meterSuppQueryMapper;

    @Override
    public List<MeterSuppQuery> queryByTaskNo(String taskNo) {
        MeterSuppQuery param = new MeterSuppQuery();
        param.setTaskNo(taskNo);
        return meterSuppQueryMapper.select(param);
    }
}
