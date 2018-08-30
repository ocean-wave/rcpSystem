package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.CustomerData4Month;
import cn.com.cdboost.collect.dto.CustomerDeviceInfo;
import cn.com.cdboost.collect.dto.param.CollectDataGetQueryParam;
import cn.com.cdboost.collect.dto.response.CollectDataGetInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */
public class HistoricalDataMockData {
    @Test
    public void query() {
        CollectDataGetQueryParam param = new CollectDataGetQueryParam();
        param.setCustomerAddr("sdfsdf");
        param.setCustomerContact("13510241587");
        param.setCustomerName("zhangsan");
        param.setDeviceNo("5456");
        param.setDeviceType("07");
        param.setEndDate("2017-11-11");
        param.setIsRealTime(1);
        param.setPropertyName("132");
        param.setStartDate("2017-11-10");
        param.setStatus("1");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryReturn() {
        PageResult<List<CollectDataGetInfo>> result = new PageResult<>();
        List<CollectDataGetInfo> list = Lists.newArrayList();
        CollectDataGetInfo getInfo = new CollectDataGetInfo();
        getInfo.setDeviceCno("sdfsdf");
        getInfo.setCollectTime("2017-11-11 12:14:10");
        getInfo.setCustomerAddr("sdfsfsd");
        getInfo.setCustomerContact("13510241587");
        getInfo.setCustomerName("zhangsan");
        getInfo.setCustomerNo("sfsdfsdfsdf");
        getInfo.setDeviceNo("5454");
        getInfo.setIsAccount(1);
        getInfo.setIsRealTime(1);
        getInfo.setOrgName("成都博高");
        getInfo.setPropertyName("205");

        CollectDataGetInfo getInfo2 = new CollectDataGetInfo();
        getInfo2.setDeviceCno("sdfsdf");
        getInfo2.setCollectTime("2017-11-11 12:14:10");
        getInfo2.setCustomerAddr("sdfsfsd");
        getInfo2.setCustomerContact("13510241587");
        getInfo2.setCustomerName("zhangsan");
        getInfo2.setCustomerNo("sfsdfsdfsdf");
        getInfo2.setDeviceNo("5454");
        getInfo2.setIsAccount(1);
        getInfo2.setIsRealTime(1);
        getInfo2.setOrgName("成都博高");
        getInfo2.setPropertyName("205");

        list.add(getInfo);
        list.add(getInfo2);
        result.setData(list);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void queryDetail() {
        Result<CustomerDeviceInfo> result = new Result<>();
        CustomerDeviceInfo info = new CustomerDeviceInfo();
        info.setCustomerAddr("sfsdfds");
        info.setCustomerContact("13810251478");
        info.setCustomerName("zhangsan");
        info.setCustomerNo("sdfsfs");
        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getDataForMonth() {
        Result<List<CustomerData4Month>> result = new Result<>();
        List<CustomerData4Month> list = Lists.newArrayList();
        CustomerData4Month month = new CustomerData4Month();
//        month.setBalance(100d);
//        month.setCollectDate("2017-01");
//        month.setPayCount(1);
//        month.setPayMoney("100");
//        month.setPr0(200d);

        CustomerData4Month month2 = new CustomerData4Month();
//        month2.setBalance(100d);
//        month2.setCollectDate("2017-02");
//        month2.setPayCount(1);
//        month2.setPayMoney("100");
//        month2.setPr0(200d);

        list.add(month);
        list.add(month2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }
}
