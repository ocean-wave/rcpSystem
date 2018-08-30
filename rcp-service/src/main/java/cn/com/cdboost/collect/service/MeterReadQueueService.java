package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.MeterReadQueue;

import java.util.List;

/**
 * 抄表队列服务接口
 */
public interface MeterReadQueueService extends BaseService<MeterReadQueue>{
    // 安装guid和cno更新
    void updateSelectiveByGuidAndCno(MeterReadQueue readQueue);

    // 按queueGuid查询
    List<MeterReadQueue> selectByQueueGuid(String queueGuid);

    List<MeterReadQueue> selectByParams(String queueGuid, List<String> cnoList);

    // 根据条件queueGuid和cnoList，批量更新对应的抄表队列数据
    int batchUpdateByParams(String queueGuid, List<String> cnoList, MeterReadQueue param);

    int deleteByParam(String guid, List<String> cnoList);
}
