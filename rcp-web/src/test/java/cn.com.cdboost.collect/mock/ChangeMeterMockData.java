package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.ChangeMeterDetailInfo;
import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.CustomerInfoDto;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.ChangeMeterCustomerDetialInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22 0022.
 */
public class ChangeMeterMockData {

    @Test
    public void updateChangeMeter() {
        ChangeMeterUpdateParam param = new ChangeMeterUpdateParam();
        param.setCustomerNo("sdfsfs");
        param.setChangeRemark("fsdsdfs");
        param.setOldCno("sdfsdfds");
        param.setDeviceType("07");
        param.setNewFactory("sdfsdfsdf");
        param.setNewMeterNo("1345");
        param.setNewPower("100");
        param.setNewRatio("121");
        param.setNewRemainAmount("231");
        param.setOldPower("23");
        param.setOldRemainAmount("10");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryChangeMeters() {
        ChangeMeterListQueryParam param = new ChangeMeterListQueryParam();
        param.setChangeDateStart("2017-11-11");
        param.setChangeDateEnd("2017-11-15");
        param.setCustomerAddr("skfsd");
        param.setCustomerContact("13810251489");
        param.setCustomerName("zhangsan");
        param.setDeviceNo("4554");
        param.setDeviceType("07");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryChangeMetersReturn() {
        PageResult<List<ChangeMeterInfo>> result = new PageResult<>();
        List<ChangeMeterInfo> list = Lists.newArrayList();
        ChangeMeterInfo info = new ChangeMeterInfo();
        info.setChangeTime(new Date());
        info.setChangeUnique("dsfdsfds");
        info.setChangeUserId(1L);
        info.setCno("sdfsd");
        info.setCustomerAddr("sdfsdfsd");
        info.setCustomerContact("13512645875");
        info.setCustomerName("zhangsan");
        info.setCustomerNo("sdkflsjdkfj");
        info.setDeviceType("07");
        info.setMeterAddr("fsddsdf");
        info.setNewCno("sdklfjksdf");
        info.setNewMeterAddr("dsfdsfsd");
        info.setNewMeterNo("4564");
        info.setNewPower(BigDecimal.valueOf(152));
        info.setNewRemainAmount(BigDecimal.valueOf(154));
        info.setPower(BigDecimal.valueOf(54));
        info.setPropertyName("569");
        info.setRemainAmount(BigDecimal.valueOf(54));
        info.setUserName("hahh");

        ChangeMeterInfo info2 = new ChangeMeterInfo();
        info2.setChangeTime(new Date());
        info2.setChangeUnique("dsfdsfds");
        info2.setChangeUserId(1L);
        info2.setCno("sdfsd");
        info2.setCustomerAddr("sdfsdfsd");
        info2.setCustomerContact("13512645875");
//        info2.setCustomerId("524");
        info2.setCustomerName("zhangsan");
        info2.setCustomerNo("sdkflsjdkfj");
        info2.setDeviceType("07");
        info2.setMeterAddr("fsddsdf");
        info2.setNewCno("sdklfjksdf");
        info2.setNewMeterAddr("dsfdsfsd");
        info2.setNewMeterNo("4564");
        info2.setNewPower(BigDecimal.valueOf(152));
        info2.setNewRemainAmount(BigDecimal.valueOf(154));
        info2.setPower(BigDecimal.valueOf(154));
        info2.setPropertyName("569");
        info2.setRemainAmount(BigDecimal.valueOf(45));
        info2.setUserName("hahh");

        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryChangeMeterDetail() {
        Result<ChangeMeterCustomerDetialInfo> result = new Result<>();
        ChangeMeterCustomerDetialInfo info = new ChangeMeterCustomerDetialInfo();
        ChangeMeterDetailInfo detailInfo = new ChangeMeterDetailInfo();
        detailInfo.setChangeRemark("sdfsdf");
        detailInfo.setChangeTime(new Date());
        detailInfo.setChangeUserId(1);
        detailInfo.setChangeUserName("zhangsan");
        detailInfo.setCommRule(10);
        detailInfo.setCustomerNo("sdfdsfds");
        detailInfo.setDeviceFactory("sdfsdf");
        detailInfo.setDeviceType("07");
        detailInfo.setLocalControl(1);
        detailInfo.setMeterNo("13212");
        detailInfo.setMeterPayCount(10);
        detailInfo.setMeterType("07");
        detailInfo.setNewMeterType("1");
        detailInfo.setNewCommRule(1);
        detailInfo.setNewDeviceFactory("sfsdf");
        detailInfo.setNewMeterNo("21231");
        detailInfo.setNewMeterPayCount(12);
        detailInfo.setRatio(34);
        info.setChangeMeterDetailInfo(detailInfo);

        CustomerInfoDto dto = new CustomerInfoDto();
        dto.setCno("sdfds");
        dto.setCreateTime("2017-11-11 12:23:34");
        dto.setCreateUserId(2);
        dto.setCreateUserName("zhangsan");
        dto.setCustomerAddr("sdfsdfd");
        dto.setCustomerContact("13512456984");
        dto.setCustomerName("zhangsan");
        dto.setCustomerNo("sfdlksdjfsl");
        dto.setAlarmThreshold("121");
        dto.setDeviceType("07");
        dto.setDictItemName("sldfksd");
        dto.setDictItemValue("11");
        dto.setId(1L);
        dto.setIsAutoSms(1);
        dto.setIsEnabled(1);
        dto.setOrgName("成都博高");
        dto.setOrgNo(11);
        dto.setPropertyName("524");
        dto.setRemark("sdfsd");

        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void changeElectricMeter() {
        ChangeElectricMeterParam param = new ChangeElectricMeterParam();
        param.setCustomerNo("sdfdsfsd");
        param.setChangeRemark("sdfsdfs");

        OldElectricMeterParam oldElectricMeterParam = new OldElectricMeterParam();
        oldElectricMeterParam.setDeviceNo("23423");
        oldElectricMeterParam.setReadValue(BigDecimal.valueOf(100));
        oldElectricMeterParam.setRemainAmount(BigDecimal.TEN);
        param.setOldElectricMeter(oldElectricMeterParam);

        NewElectricMeterParam newElectricMeterParam = new NewElectricMeterParam();
        newElectricMeterParam.setDeviceNo("3534");
//        newElectricMeterParam.setLocalControl(1);
//        newElectricMeterParam.setCommRule(30);
        newElectricMeterParam.setReadValue(BigDecimal.valueOf(200));
        newElectricMeterParam.setRemainAmount(BigDecimal.TEN);
        newElectricMeterParam.setInitAmount(BigDecimal.valueOf(222));
//        newElectricMeterParam.setDeviceFactory("sfsdfs");
//        newElectricMeterParam.setMeterType("1");
        param.setNewElectricMeter(newElectricMeterParam);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void changeWaterMeter() {
        ChangeWaterMeterParam param = new ChangeWaterMeterParam();
        param.setCustomerNo("dsdfdsf");
        param.setChangeRemark("sdsdfds");

        OldWaterMeterParam oldWaterMeterParam = new OldWaterMeterParam();
        oldWaterMeterParam.setDeviceNo("54564");
        oldWaterMeterParam.setReadValue(BigDecimal.valueOf(120));
        oldWaterMeterParam.setRemainAmount(BigDecimal.TEN);
        param.setOldWaterMeter(oldWaterMeterParam);

        NewWaterMeterParam newWaterMeterParam = new NewWaterMeterParam();
        newWaterMeterParam.setDeviceNo("564654");
        newWaterMeterParam.setReadValue(BigDecimal.valueOf(125));
        newWaterMeterParam.setRemainAmount(BigDecimal.valueOf(23));
        param.setNewWaterMeter(newWaterMeterParam);

        System.out.println(JSON.toJSONString(param));
    }
}
