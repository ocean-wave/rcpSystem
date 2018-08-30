package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FreezeLogMapper;
import cn.com.cdboost.collect.model.FreezeLog;
import cn.com.cdboost.collect.service.FreezeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 冻结日志服务接口实现类
 */
@Service
public class FreezeLogServiceImpl extends BaseServiceImpl<FreezeLog> implements FreezeLogService {
    @Autowired
    private FreezeLogMapper freezeLogMapper;
    @Override
    public List<FreezeLog> queryByParams(String jzqNo, String freezeDate) {
        Condition condition = new Condition(FreezeLog.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("jzqNo",jzqNo);
        criteria.andEqualTo("freezeDate",freezeDate);
        List<FreezeLog> freezeLogs = freezeLogMapper.selectByCondition(condition);
        return freezeLogs;
    }
}
