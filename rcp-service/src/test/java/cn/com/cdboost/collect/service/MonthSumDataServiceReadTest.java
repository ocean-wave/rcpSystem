package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.model.MonthSumData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2 0002.
 */
public class MonthSumDataServiceReadTest extends BaseServiceReadTest {
    @Autowired
    private MonthSumDataService monthSumDataService;

    @Test
    public void query() {
        List<MonthSumData> nMonthSumDataByCno = monthSumDataService.getNMonthSumDataByCno("070000000000000000000001012063", 6);
        System.out.println(nMonthSumDataByCno.size());
    }
}
