package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterReadQueueMapper;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.MeterReadQueue;
import cn.com.cdboost.collect.service.MeterReadQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 抄表队列服务接口实现类
 */
@Service("meterReadQueueService")
public class MeterReadQueueServiceImpl extends BaseServiceImpl<MeterReadQueue> implements MeterReadQueueService {
    @Autowired
    private MeterReadQueueMapper meterReadQueueMapper;

    @Override
    @Transactional
    public void updateSelectiveByGuidAndCno(MeterReadQueue readQueue) {
        String meterCno = readQueue.getMeterCno();
        if (meterCno == null) {
            throw new BusinessException("meterCno不能为空");
        }

        String queueGuid = readQueue.getQueueGuid();
        if (queueGuid == null) {
            throw new BusinessException("queueGuid不能为空");
        }

        Date createTime = readQueue.getCreateTime();
        if (createTime == null) {
            throw new BusinessException("createTime不能为空");
        }

        Condition condition = new Condition(MeterReadQueue.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("meterCno",meterCno);
        criteria.andEqualTo("queueGuid",queueGuid);
        meterReadQueueMapper.updateByConditionSelective(readQueue,condition);
    }

    @Override
    public List<MeterReadQueue> selectByQueueGuid(String queueGuid) {
        MeterReadQueue param = new MeterReadQueue();
        param.setQueueGuid(queueGuid);
        return meterReadQueueMapper.select(param);
    }

    @Override
    public List<MeterReadQueue> selectByParams(String queueGuid, List<String> cnoList) {
        Condition condition = new Condition(MeterReadQueue.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("queueGuid",queueGuid);
        criteria.andIn("meterCno",cnoList);
        List<MeterReadQueue> readQueueList = meterReadQueueMapper.selectByCondition(condition);
        return readQueueList;
    }

    @Override
    public int batchUpdateByParams(String queueGuid, List<String> cnoList, MeterReadQueue param) {
        Condition condition = new Condition(MeterReadQueue.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("queueGuid",queueGuid);
        criteria.andIn("meterCno",cnoList);
        int i = meterReadQueueMapper.updateByConditionSelective(param, condition);
        return i;
    }

    @Override
    public int deleteByParam(String guid, List<String> cnoList) {
        Condition condition = new Condition(MeterReadQueue.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("queueGuid",guid);
        criteria.andIn("meterCno",cnoList);
        int i = meterReadQueueMapper.deleteByCondition(condition);
        return i;
    }
}
