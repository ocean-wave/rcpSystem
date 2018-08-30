package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MonthSumDataMapper;
import cn.com.cdboost.collect.model.MonthSumData;
import cn.com.cdboost.collect.service.MonthSumDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 每月电量数据 接口服务实现类
 */
@Service("monthSumDataService")
public class MonthSumDataServiceImpl extends BaseServiceImpl<MonthSumData> implements MonthSumDataService{
    @Autowired
    private MonthSumDataMapper monthSumDataMapper;

    @Override
    public List<MonthSumData> getNMonthSumDataByCno(String cno, int n) {
        Condition condition = new Condition(MonthSumData.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("cno",cno);
        String orderBy = "sum_year,sum_month desc limit " + n;
        condition.setOrderByClause(orderBy);
        List<MonthSumData> monthSumData = monthSumDataMapper.selectByCondition(condition);
        return monthSumData;
    }

    @Override
    public MonthSumData querySumData(int sumYear, int sumMonth, String cno) {
        MonthSumData param = new MonthSumData();
        param.setSumYear(sumYear);
        param.setSumMonth(sumMonth);
        param.setCno(cno);
        return monthSumDataMapper.selectOne(param);
    }
}
