package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.TmpCustomerInfo;

import java.util.List;

/**
 * 客户档案上传时，用到的临时表服务接口
 */
public interface TmpCustomerInfoService extends BaseService<TmpCustomerInfo>{

    // 根据importId，查询导入异常数据
    List<TmpCustomerInfo> queryErrorInfoByImportId(String importId);
}
