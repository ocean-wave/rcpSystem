package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.param.HistoryElectricAmountParam;
import cn.com.cdboost.collect.service.HistoryElectricAmountService;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.TokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * @author wt
 * @desc
 * @create in  2018/7/30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:META-INF/spring/applicationContext.xml")
public class HistoryElectricAmountServiceImplTest {
    @Autowired
    HistoryElectricAmountService HistoryElectricAmountService;
    @Test
    public void queryHistoryElectricAmount() throws NoSuchAlgorithmException, ParseException {
        HistoryElectricAmountParam historyElectricAmountParam=new HistoryElectricAmountParam();
        historyElectricAmountParam.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
        historyElectricAmountParam.setAddrCode("110");
        historyElectricAmountParam.setStartDate("2018-01");
        historyElectricAmountParam.setEndDate("2018-07");
        historyElectricAmountParam.setDataFlag("2");
        HistoryElectricAmountService.queryHistoryElectricAmount(historyElectricAmountParam);
    }

}