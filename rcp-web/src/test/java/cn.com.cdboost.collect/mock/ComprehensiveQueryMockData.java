package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.CollectDataPerDay;
import cn.com.cdboost.collect.dto.response.PerDayData4List;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 统计分析mock数据
 */
public class ComprehensiveQueryMockData {

    public static void main(String[] args) {
        String s = queryCollectDataList();
        System.out.println(s);
    }

    public static String queryCollectDataList() {
        Result<CollectDataPerDay> result = new Result<>();
        CollectDataPerDay perDay = new CollectDataPerDay();
        String[] xData = new String[] {"2018-02-01","2018-02-02","2018-02-03"};
        BigDecimal[] yData = new BigDecimal[] {new BigDecimal("10.12"),new BigDecimal("20.58"),new BigDecimal("30.69")};
        perDay.setxData(xData);
        perDay.setyData(yData);

        List<PerDayData4List> listData = Lists.newArrayList();
        PerDayData4List dataInfo = new PerDayData4List();
        dataInfo.setCollectDate(new Date());
        dataInfo.setPr0(new BigDecimal("100.24"));
        dataInfo.setBalance(new BigDecimal("20.14"));
        dataInfo.setPayMoney(new BigDecimal("25.25"));
        dataInfo.setPayCount(12);
        listData.add(dataInfo);

        PerDayData4List dataInf2 = new PerDayData4List();
        dataInf2.setCollectDate(new Date());
        dataInf2.setPr0(new BigDecimal("100.24"));
        dataInf2.setBalance(new BigDecimal("20.14"));
        dataInf2.setPayMoney(new BigDecimal("25.25"));
        dataInf2.setPayCount(12);
        listData.add(dataInf2);

        PerDayData4List dataInf3 = new PerDayData4List();
        dataInf3.setCollectDate(new Date());
        dataInf3.setPr0(new BigDecimal("100.24"));
        dataInf3.setBalance(new BigDecimal("20.14"));
        dataInf3.setPayMoney(new BigDecimal("25.25"));
        dataInf3.setPayCount(12);
        listData.add(dataInf3);
        perDay.setListData(listData);
        result.setData(perDay);

        return JSON.toJSONString(result);
    }
}
