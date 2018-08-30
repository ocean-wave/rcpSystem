package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DayImpSumMapper;
import cn.com.cdboost.collect.model.DayImpSum;
import cn.com.cdboost.collect.service.DayImpSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 汇总重点用户的周期采集成功率服务实现类
 */
@Service
public class DayImpSumServiceImpl extends BaseServiceImpl<DayImpSum> implements DayImpSumService {
    @Autowired
    private DayImpSumMapper dayImpSumMapper;

    @Override
    public List<DayImpSum> queryByParam(String deviceType,String startDate, String endDate, List<Long> orgNoList) {
        Condition condition = new Condition(DayImpSum.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andBetween("collectTime",startDate,endDate);
        criteria.andIn("orgNo",orgNoList);
        criteria.andEqualTo("deviceType",deviceType);
        condition.setOrderByClause("id asc");
        return dayImpSumMapper.selectByCondition(condition);
    }
}
