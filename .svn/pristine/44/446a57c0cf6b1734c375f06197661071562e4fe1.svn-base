package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.response.StaticCountInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */
public class FirstMockData {

    @Test
    public void currentMonth() {
        Result<List<CollectAnalyzeData>> result = new Result<>();
        List<CollectAnalyzeData> list = Lists.newArrayList();
        CollectAnalyzeData data = new CollectAnalyzeData();
        data.setCltMonth(2);
        data.setDeviceType("07");
        data.setSumPR(1000);

        CollectAnalyzeData data2 = new CollectAnalyzeData();
        data2.setCltMonth(3);
        data2.setDeviceType("07");
        data2.setSumPR(2000);

        list.add(data);
        list.add(data2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void dataForYear() {
        Result<CollectAnalyzeDataForMonthInfo> result = new Result<>();
        CollectAnalyzeDataForMonthInfo info = new CollectAnalyzeDataForMonthInfo();
        info.setYear(2017);
        BigDecimal[] gasArray = new BigDecimal[12];
        for (int i=0; i < 12; i++) {
            gasArray[i] = BigDecimal.valueOf(i + 100);
        }
        info.setGas(gasArray);

        BigDecimal[] elecArray = new BigDecimal[12];
        for (int i=0; i < 12; i++) {
            elecArray[i] = BigDecimal.valueOf(i + 100);
        }
        info.setElec(elecArray);

        BigDecimal[] waterArray = new BigDecimal[12];
        for (int i=0; i < 12; i++) {
            waterArray[i] = BigDecimal.valueOf(i + 100);
        }
        info.setWater(waterArray);
        info.setYear(2016);
        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void listArrearageCustomers() {
        PageResult<List<ArrearageCustomer>> result = new PageResult<>();
        List<ArrearageCustomer> list = Lists.newArrayList();
        ArrearageCustomer customer = new ArrearageCustomer();
        customer.setCollectDate(new Date());
        customer.setCustomerAddr("sfsdfds");
        customer.setCustomerContact("13510242587");
        customer.setCustomerName("zhangsan");
        customer.setCustomerNo("dsfsdfsds");
        customer.setDeviceNo("1545");
        customer.setDeviceType("07");
        customer.setLastSendSMSTime("2017-11-11 10:15:14");
        customer.setPropertyName("125");
        customer.setSendMessage(2);

        ArrearageCustomer customer2 = new ArrearageCustomer();
        customer2.setCollectDate(new Date());
        customer2.setCustomerAddr("sfsdfds");
        customer2.setCustomerContact("13510242587");
        customer2.setCustomerName("zhangsan");
        customer2.setCustomerNo("dsfsdfsds");
        customer2.setDeviceNo("1545");
        customer2.setDeviceType("07");
        customer2.setLastSendSMSTime("2017-11-11 10:15:14");
        customer2.setPropertyName("125");
        customer2.setSendMessage(2);

        list.add(customer);
        list.add(customer2);
        result.setData(list);
        result.setTotal(50L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void pageStaticCount() {
        Result<StaticCountInfo> result = new Result<>();
        StaticCountInfo info = new StaticCountInfo();
        info.setCstTotal("100");
        info.setCstErr("sfsd");
        info.setOffCount("20");
        info.setMeterErrCount("10");

        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void pageAnalAmount() {
        Result<AnalAmountDTO> result = new Result<>();
        AnalAmountDTO dto = new AnalAmountDTO();
        dto.setC0Amount(10);
        dto.setC50Amount(102);
        dto.setC200Amount(200);
        dto.setC500Amount(500);

        result.setData(dto);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void pageSumDataGet() {
        Result<PageSumDataGetInfo> result = new Result<>();
        PageSumDataGetInfo info = new PageSumDataGetInfo();
        info.setStartTime("2017-11-12");
        info.setEndTime("2017-11-30");
        List<SumDataGetDTO> dataList = Lists.newArrayList();
        SumDataGetDTO dto = new SumDataGetDTO();
        dto.setCltMonth("11");
        dto.setDeviceType("07");
        dto.setSumPR(BigDecimal.valueOf(200));
        dto.setSumRemainAmount(BigDecimal.valueOf(30));

        SumDataGetDTO dto2 = new SumDataGetDTO();
        dto2.setCltMonth("11");
        dto2.setDeviceType("07");
        dto2.setSumPR(BigDecimal.valueOf(200));
        dto2.setSumRemainAmount(BigDecimal.valueOf(30));
        dataList.add(dto);
        dataList.add(dto2);

        info.setList(dataList);
        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void sumGetForMonth() {
        Result<GetForYearInfo> result = new Result<>();
        GetForYearInfo info = new GetForYearInfo();
        StatisticalYearDataInfo thisYeay = new StatisticalYearDataInfo();
        thisYeay.setYear(2017);
        List<SumDataGetDTO> dataList = Lists.newArrayList();
        SumDataGetDTO dto = new SumDataGetDTO();
        dto.setSumRemainAmount(BigDecimal.valueOf(20));
        dto.setSumPR(BigDecimal.valueOf(100));
        dto.setDeviceType("07");
        dto.setCltMonth("1");

        SumDataGetDTO dto2 = new SumDataGetDTO();
        dto2.setSumRemainAmount(BigDecimal.valueOf(20));
        dto2.setSumPR(BigDecimal.valueOf(100));
        dto2.setDeviceType("07");
        dto2.setCltMonth("2");
        dataList.add(dto);
        dataList.add(dto2);

        thisYeay.setDataList(dataList);
        info.setThisYear(thisYeay);


        StatisticalYearDataInfo lastYeay = new StatisticalYearDataInfo();
        thisYeay.setYear(2017);
        List<SumDataGetDTO> dataList2 = Lists.newArrayList();
        SumDataGetDTO dto3 = new SumDataGetDTO();
        dto3.setSumRemainAmount(BigDecimal.valueOf(20));
        dto3.setSumPR(BigDecimal.valueOf(100));
        dto3.setDeviceType("07");
        dto3.setCltMonth("1");

        SumDataGetDTO dto4 = new SumDataGetDTO();
        dto4.setSumRemainAmount(BigDecimal.valueOf(20));
        dto4.setSumPR(BigDecimal.valueOf(100));
        dto4.setDeviceType("07");
        dto4.setCltMonth("2");
        dataList2.add(dto3);
        dataList2.add(dto4);

        lastYeay.setDataList(dataList2);
        info.setLastYear(lastYeay);

        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void pageSumMonthPer() {
        Result<SumMonthPerInfo> result = new Result<>();
        SumMonthPerInfo info = new SumMonthPerInfo();
        info.setPercent("0.8");
        info.setCurrentMonth("12");
        info.setLastMonth("11");

        result.setData(info);
        System.out.println(JSON.toJSONString(result));
    }
}
