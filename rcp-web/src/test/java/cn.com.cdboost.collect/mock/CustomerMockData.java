package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CustomerDetailInfo;
import cn.com.cdboost.collect.dto.response.CustomerInfoDetailInfo;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class CustomerMockData {

    @Test
    public void queryList() {
        CustomerInfoQueryParam param = new CustomerInfoQueryParam();
        param.setCustomerAddr("fsdfds");
        param.setCustomerContact("13512041587");
        param.setCustomerName("zhangsan");
        param.setPropertyName("536");
        param.setDeviceNo("5454");
        param.setPageNumber(1);
        param.setPageSize(20);
        param.setSortName("sfsd");
        param.setSortOrder("desc");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void add() {
        CustomerInfoCreateParam param = new CustomerInfoCreateParam();
        // 客户档案
        CustomerInfoAddParam addParam = new CustomerInfoAddParam();
        addParam.setCustomerName("sfdsfs");
        addParam.setCustomerContact("13510252489");
        addParam.setCustomerAddr("sfsdfsd");
        addParam.setPropertyName("520");
        addParam.setOrgNo(1000L);
        addParam.setRemark("sfsdfds");
        param.setCustomerInfo(addParam);

        // 电表
        ElectricMeterAddParam electricMeterAddParam = new ElectricMeterAddParam();
        electricMeterAddParam.setDeviceNo("21513");
        electricMeterAddParam.setLocalControl(1);
//        electricMeterAddParam.setCommRule(30);
      //  electricMeterAddParam.setInitAmount(BigDecimal.TEN);
        electricMeterAddParam.setJzqNo("999999999");
        electricMeterAddParam.setRatio(12);
        electricMeterAddParam.setCjqNo("121231");
        electricMeterAddParam.setInstallAddr("sdfsfsd");
        electricMeterAddParam.setRemark("sfsdfsd");
        electricMeterAddParam.setCommPort(30);
//        electricMeterAddParam.setMeterType(2);
//        param.setElectricMeter(electricMeterAddParam);

        // 水表
        WaterMeterAddParam waterMeterAddParam = new WaterMeterAddParam();
        waterMeterAddParam.setDeviceNo("15415");
//        waterMeterAddParam.setCommRule(30);
       // waterMeterAddParam.setInitAmount(BigDecimal.TEN);
        waterMeterAddParam.setJzqNo("452415");
        waterMeterAddParam.setCjqNo("12441");
        waterMeterAddParam.setInstallAddr("fdssdfs");
        waterMeterAddParam.setRemark("sfsdf");
        waterMeterAddParam.setCommPort(30);
//        waterMeterAddParam.setMeterType(2);
//        param.setWaterMeter(waterMeterAddParam);

        // 气表
        GasMeterAddParam gasMeterAddParam = new GasMeterAddParam();
        gasMeterAddParam.setDeviceNo("13213");
//        gasMeterAddParam.setCommRule(30);
      //  gasMeterAddParam.setInitAmount(BigDecimal.TEN);
        gasMeterAddParam.setJzqNo("122121");
        gasMeterAddParam.setCjqNo("5645415");
        gasMeterAddParam.setInstallAddr("sdfsdf");
        gasMeterAddParam.setRemark("sdfsdfds");
        gasMeterAddParam.setCommPort(30);
//        gasMeterAddParam.setMeterType(2);
//        param.setGasMeter(gasMeterAddParam);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void batchDelete() {
        List<String> customerNos = Lists.newArrayList();
        customerNos.add("ff7b9218-a10d-4d93-9133-cbbf8cfe936d");
        customerNos.add("e77f39d5-d17e-4d60-9c90-b1d3275ceb3f");

        System.out.println(JSON.toJSONString(customerNos));
    }

    @Test
    public void queryDetails() {
        Result<CustomerDetailInfo> result = new Result<>();
        CustomerDetailInfo detailInfo = new CustomerDetailInfo();
        CustomerInfoDetailInfo info = new CustomerInfoDetailInfo();
        info.setCustomerName("sdfsdf");
        info.setCustomerContact("13512455874");
        info.setCustomerAddr("sdfsdfs");
        info.setCustomerNo("sdfsdfsdfsd");
        info.setPropertyName("522");
        info.setOrgName("成都博高");

        detailInfo.setCustomerInfo(info);

//        ElectricMeterInfo electricMeter = new ElectricMeterInfo();
//        detailInfo.setElectricMeter(electricMeter);
//
//        WaterMeterInfo waterMeter = new WaterMeterInfo();
//        detailInfo.setWaterMeter(waterMeter);
//
//        GasMeterInfo gasMeter = new GasMeterInfo();
//        detailInfo.setGasMeter(gasMeter);
        result.setData(detailInfo);

        System.out.println(JSON.toJSONString(detailInfo));

    }

    @Test
    public void edit() {
        CustomerInfoEditParam param = new CustomerInfoEditParam();
        param.setId(111L);
        param.setCustomerName("sfdsfs");
        param.setCustomerContact("13510252489");
        param.setCustomerAddr("sfsdfsd");
        param.setPropertyName("520");
        param.setOrgNo(1000L);
        param.setRemark("sfsdfds");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void editElectricDevice() {
        ElectricMeterEditParam param = new ElectricMeterEditParam();
        param.setCustomerNo("sdsfsdfsdfds");
        param.setOrgNo(1000L);
        param.setDeviceNo("21513");
        param.setLocalControl(1);
//        param.setCommRule(30);
       // param.setInitAmount(BigDecimal.TEN);
        param.setJzqNo("999999999");
        param.setRatio(12);
        param.setCjqNo("121231");
        param.setInstallAddr("sdfsfsd");
        param.setRemark("sfsdfsd");
        param.setCommPort(30);
//        param.setMeterType(2);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void editWaterDevice() {
        WaterMeterEditParam param = new WaterMeterEditParam();
        param.setCustomerNo("sdfsdfdsfs");
        param.setOrgNo(1000L);
        param.setDeviceNo("15415");
//        param.setCommRule(30);
      //  param.setInitAmount(BigDecimal.TEN);
        param.setJzqNo("452415");
        param.setCjqNo("12441");
        param.setInstallAddr("fdssdfs");
        param.setRemark("sfsdf");
        param.setCommPort(30);
//        param.setMeterType(2);

        System.out.println(JSON.toJSONString(param));
    }
}
