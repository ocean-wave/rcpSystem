package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.FeeOnOffDetailInfo;
import cn.com.cdboost.collect.dto.param.OnOffQueryVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FeeOnOffServiceReadTest extends BaseServiceReadTest{
    @Autowired
    private FeeOnOffService feeOnOffService;

    @Test
    public void query() {
        OnOffQueryVo queryVo = new OnOffQueryVo();
        queryVo.setCustomerNo("57facc6153d149019e57afb76ed8709b");
        queryVo.setDeviceType("07");
        queryVo.setStartTime("2018-02-07 00:00:00");
        queryVo.setEndTime("2018-02-08 23:59:59");

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//            Date start = format.parse("2018-02-08 00:00:00");
//            queryVo.setStartTime(start);
//            Date end = format.parse("2018-02-08 23:59:59");
//            queryVo.setEndTime(end);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        queryVo.setPageNumber(1);
        queryVo.setPageSize(20);
        List<FeeOnOffDetailInfo> data = feeOnOffService.queryHistory4Single(queryVo);
        System.out.println(data);
    }
}
