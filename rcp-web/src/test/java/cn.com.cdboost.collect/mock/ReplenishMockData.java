package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */
public class ReplenishMockData {
    @Test
    public void queryReplenishMeter() {
        MeterSuppCstQueryParam param = new MeterSuppCstQueryParam();
        param.setDeviceNo("2323");
        param.setCustomerName("zhangsan");
        param.setCustomerAddr("sdfsdfds");
        param.setDeviceType("07");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));

    }

    @Test
    public void queryReplenishMeterReturn () {
        PageResult<List<MeterSuppCstValueText>> result = new PageResult<>();
        List<MeterSuppCstValueText> list = Lists.newArrayList();
        MeterSuppCstValueText valueText = new MeterSuppCstValueText();
        valueText.setText("用户姓名：zhangsan|门牌编号：520|用户地址：sdflksfksd|用户户号：2032");
        BaseHandheldReplenishDTO dto = new BaseHandheldReplenishDTO();
        dto.setCno("werwer");
        dto.setCustomerAddr("sdfsdfs");
        dto.setCustomerName("zhangsna");
        dto.setCustomerNo("dsfjksjfkls");
        dto.setDeviceNo("5646");
        dto.setPropertyName("520");
        valueText.setValue(dto);


        MeterSuppCstValueText valueText2 = new MeterSuppCstValueText();
        valueText2.setText("用户姓名：zhangsan|门牌编号：520|用户地址：sdflksfksd|用户户号：2032");
        BaseHandheldReplenishDTO dto2 = new BaseHandheldReplenishDTO();
        dto2.setCno("werwer");
        dto2.setCustomerAddr("sdfsdfs");
        dto2.setCustomerName("zhangsna");
        dto2.setCustomerNo("dsfjksjfkls");
        dto2.setDeviceNo("5646");
        dto2.setPropertyName("520");
        valueText2.setValue(dto2);

        list.add(valueText);
        list.add(valueText2);
        result.setData(list);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void createReplenishWorkOrder() {
        WorkOrderAddParam param = new WorkOrderAddParam();
        param.setDeviceType("07");
        param.setEndTime("2017-12-30");
        param.setRuntor(1);
        param.setTaskContent("sfdjsjfsdf");
        List<WorkOrderDeviceParam> meters = Lists.newArrayList();
        WorkOrderDeviceParam deviceParam = new WorkOrderDeviceParam();
        deviceParam.setCno("070000000000545");
        deviceParam.setCustomerNo("sdfsdfdsfsddsfsd");

        WorkOrderDeviceParam deviceParam2 = new WorkOrderDeviceParam();
        deviceParam2.setCno("0700000000005899");
        deviceParam2.setCustomerNo("sdfsdgfdgsdfsdfd");
        meters.add(deviceParam);
        meters.add(deviceParam2);

        param.setMeters(meters);

        System.out.println(JSON.toJSONString(param));
    }


    @Test
    public void replenishWorkOrderList() {
        MeterSuppTaskGetQueryParam param = new MeterSuppTaskGetQueryParam();
        param.setDeviceType("07");
        param.setTaskContent("sdfsd");
        param.setStartTime("2017-11-11");
        param.setEndTime("2017-12-30");
        param.setTaskFlag("");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void replenishWorkOrderListReturn() {
        PageResult<List<WorkOrderDetialDTO>> result = new PageResult<>();
        List<WorkOrderDetialDTO> list = Lists.newArrayList();
        WorkOrderDetialDTO dto = new WorkOrderDetialDTO();
        dto.setComplateCount(10);
        dto.setCreateTime(new Date());
        dto.setCreateUserId("1");
        dto.setEndTime(new Date());
        dto.setFlag(1);
        dto.setMeterCount("20");
        dto.setOrgName("chengdubogao");
        dto.setStartTime(new Date());
        dto.setTaskContent("sdfsd");
        dto.setTaskNo("sfsdfsd");
        dto.setUserName("sfsd");
        dto.setUpdateTime(new Date());

        WorkOrderDetialDTO dto2 = new WorkOrderDetialDTO();
        dto2.setComplateCount(10);
        dto2.setCreateTime(new Date());
        dto2.setCreateUserId("1");
        dto2.setEndTime(new Date());
        dto2.setFlag(1);
        dto2.setMeterCount("20");
        dto2.setOrgName("chengdubogao");
        dto2.setStartTime(new Date());
        dto2.setTaskContent("sdfsd");
        dto2.setTaskNo("sfsdfsd");
        dto2.setUserName("sfsd");
        dto2.setUpdateTime(new Date());

        list.add(dto);
        list.add(dto2);
        result.setData(list);
        result.setTotal(200L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void replenishDataDetial() {
        MeterSuppTaskDetailQueryVo queryVo = new MeterSuppTaskDetailQueryVo();
        queryVo.setStatus(1);
        queryVo.setTaskNo("dsdfsdfsd");
        queryVo.setPageNumber(1);
        queryVo.setPageSize(20);

        System.out.println(JSON.toJSONString(queryVo));
    }

    @Test
    public void replenishDataDetialReturn() {
        PageResult<List<ReplenishDataDTO>> result = new PageResult<>();
        List<ReplenishDataDTO> list = Lists.newArrayList();
        ReplenishDataDTO dto = new ReplenishDataDTO();
        dto.setCno("sdfdsfdsf");
        dto.setCollectTime("2017-11-12 12:24:25");
        dto.setCustomerAddr("dsfsdf");
        dto.setCustomerName("sdfkjsd");
        dto.setDataSrc(2);
        dto.setDeviceNo("1545");
        dto.setFlag(1);
        dto.setId("12");
        dto.setPayCount(1);
        dto.setPropertyName("502");
//        dto.setUserName("zhaosi");

        ReplenishDataDTO dto2 = new ReplenishDataDTO();
        dto2.setCno("sdfdsfdsf");
        dto2.setCollectTime("2017-11-12 12:24:25");
        dto2.setCustomerAddr("dsfsdf");
        dto2.setCustomerName("sdfkjsd");
        dto2.setDataSrc(2);
        dto2.setDeviceNo("1545");
        dto2.setFlag(1);
        dto2.setId("12");
        dto2.setPayCount(1);
        dto2.setPropertyName("502");
//        dto2.setUserName("zhaosi");

        list.add(dto);
        list.add(dto2);
        result.setData(list);
        result.setTotal(200L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryUser() {
        Result<List<WorkRuntorNameOrgInfo>> result = new Result();
        List<WorkRuntorNameOrgInfo> list = Lists.newArrayList();
        WorkRuntorNameOrgInfo info = new WorkRuntorNameOrgInfo();
        info.setText("姓名：zhangsan|部门：成都博高");
        info.setUserId(11);

        WorkRuntorNameOrgInfo info2 = new WorkRuntorNameOrgInfo();
        info2.setText("姓名：zhangwu|部门：成都博高");
        info2.setUserId(12);
        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void requestWorkOrderList() {
        Result<List<MeterSuppQueryInfo>> result = new Result<>();
        List<MeterSuppQueryInfo> list = Lists.newArrayList();
        MeterSuppQueryInfo info = new MeterSuppQueryInfo();
        info.setQueryUserName("zhangsan");
        info.setQueryTime(new Date());

        MeterSuppQueryInfo info2 = new MeterSuppQueryInfo();
        info2.setQueryUserName("zhangsan");
        info2.setQueryTime(new Date());

        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

}
