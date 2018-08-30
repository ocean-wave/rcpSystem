package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.ChangeMeterOth;

/**
 * 电表更换的其他参数项目服务接口
 */
public interface ChangeMeterOthService extends BaseService<ChangeMeterOth>{
    /**
     * 添加更新，事务测试
     * @param flag true不抛出异常，false 抛出异常
     */
    void addUpdateTransaction(boolean flag);

    void updateSingle();
}
