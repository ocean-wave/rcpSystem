package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.TmpCustomerInfoMapper;
import cn.com.cdboost.collect.model.TmpCustomerInfo;
import cn.com.cdboost.collect.service.TmpCustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户档案上传时，用到的临时表服务接口实现类
 */
@Service("tmpCustomerInfoService")
public class TmpCustomerInfoServiceImpl extends BaseServiceImpl<TmpCustomerInfo> implements TmpCustomerInfoService {

    @Autowired
    private TmpCustomerInfoMapper tmpCustomerInfoMapper;

    @Override
    public List<TmpCustomerInfo> queryErrorInfoByImportId(String importId) {
        TmpCustomerInfo param = new TmpCustomerInfo();
        param.setImportId(importId);
        param.setIsErr(1);
        return tmpCustomerInfoMapper.select(param);
    }
}
