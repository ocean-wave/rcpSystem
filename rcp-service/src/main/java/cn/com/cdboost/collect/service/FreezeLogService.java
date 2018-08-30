package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.FreezeLog;

import java.util.List;

/**
 * 冻结日志服务接口
 */
public interface FreezeLogService extends BaseService<FreezeLog>{
    List<FreezeLog> queryByParams(String jzqNo, String freezeDate);
}
