package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
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
public class FeePayMockData {

    @Test
    public void feePay() {
        RemoteRechargeParam param = new RemoteRechargeParam();
        param.setCustomerNo("sfsdfs");
        param.setPayCount(12);
        param.setPayModel(1);
        param.setPayMoney(BigDecimal.TEN);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryUser() {
        RechargeUserQueryParam param = new RechargeUserQueryParam();
        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void query() {
        RechargeRecordQueryParam param = new RechargeRecordQueryParam();
        param.setCustomerAddr("sdfsdfs");
        param.setCustomerContact("13510242569");
        param.setCustomerName("zhangsan");
        param.setDeviceNo("64546");
        param.setDeviceType("07");
        param.setStartDate("2017-11-11");
        param.setEndDate("2017-12-12");
        param.setPayModel("1");
        param.setWriteMeter("2");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryReturn() {
        PageResult<FeePayRecordInfo> result = new PageResult<>();
        FeePayRecordInfo info = new FeePayRecordInfo();
        FeePayStatisticInfo statisticInfo = new FeePayStatisticInfo();
        statisticInfo.setAdjustAmount("100");
        statisticInfo.setPayment("2332");
        statisticInfo.setPayMoney("238");
        info.setStatistics(statisticInfo);

        List<QueryProcDTO> list = Lists.newArrayList();
        QueryProcDTO dto = new QueryProcDTO();
        dto.setAdjustAmount(BigDecimal.TEN);
        dto.setCno("dsfsdfsd");
        dto.setCustomerAddr("dsfsaf");
        dto.setCustomerContact("13510241589");
        dto.setCustomerName("zhangsan");
        dto.setCustomerNo("dsfksdlfjkds");
        dto.setCreateUserId("1");
        dto.setCreateUserName("sfjs");
        dto.setIsEnabled(1);
        dto.setIsRepeatCard(1);
        dto.setIsValid(1);
        dto.setOrgName("成都博高");
        dto.setPayCount(10);
        dto.setPayDate(new Date());
        dto.setPayGuid("dsfsdfs");
        dto.setPayment(BigDecimal.valueOf(100));
        dto.setPayModel(1);
        dto.setPayMoney(BigDecimal.valueOf(100));
        dto.setPropertyName("561");
        dto.setRemainAmount(BigDecimal.valueOf(200));
        dto.setWriteMeter(1);
        dto.setWriteMeterTime(new Date());

        QueryProcDTO dto2 = new QueryProcDTO();
        dto2.setAdjustAmount(BigDecimal.TEN);
        dto2.setCno("dsfsdfsd");
        dto2.setCustomerAddr("dsfsaf");
        dto2.setCustomerContact("13510241589");
        dto2.setCustomerName("zhangsan");
        dto2.setCustomerNo("dsfksdlfjkds");
        dto2.setCreateUserId("1");
        dto2.setCreateUserName("sfjs");
        dto2.setIsEnabled(1);
        dto2.setIsRepeatCard(1);
        dto2.setIsValid(1);
        dto2.setOrgName("成都博高");
        dto2.setPayCount(10);
        dto2.setPayDate(new Date());
        dto2.setPayGuid("dsfsdfs");
        dto2.setPayment(BigDecimal.valueOf(100));
        dto2.setPayModel(1);
        dto2.setPayMoney(BigDecimal.valueOf(100));
        dto2.setPropertyName("561");
        dto2.setRemainAmount(BigDecimal.valueOf(200));
        dto2.setWriteMeter(1);
        dto2.setWriteMeterTime(new Date());
        list.add(dto);
        list.add(dto2);

        info.setList(list);
        result.setData(info);
        result.setTotal(200L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void saveCardRecord() {
        FeeReadCardParam param = new FeeReadCardParam();
        param.setCardId("11");
        param.setCno("dsfsd");
        param.setCurTranfRto(10);
        param.setCustomerNo("dfsdf");
        param.setErrorCnt(20);
        param.setKeyCount(4);
        param.setKeyVer(3);
        param.setKeyState(3);
        param.setMeterNo("215");
        param.setOverdraftFee(BigDecimal.valueOf(20));
        param.setPayCount(200);
        param.setRatio(20);
        param.setRemainAmount(BigDecimal.valueOf(232));
        param.setReWrtTime("2017-11-13");
        param.setVolTranfRto(4);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void fuzzyQuery() {
        UserFuzzyQueryParam param = new UserFuzzyQueryParam();
        param.setCustomerAddr("sdfsd");
        param.setCustomerContact("13510241589");
        param.setCustomerName("zhangsan");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void fuzzyQueryReturn() {
        Result<List<FuzzyQueryDTO>> result = new Result<>();
        List<FuzzyQueryDTO> list = Lists.newArrayList();
        FuzzyQueryDTO dto = new FuzzyQueryDTO();
        dto.setText("用户姓名：张三 ||用户电话：13512051424 ||用户地址：胜多负少 ||用户户号：521");
        dto.setValue("customerNo值");

        FuzzyQueryDTO dto2 = new FuzzyQueryDTO();
        dto2.setText("用户姓名：张三 ||用户电话：13512051424 ||用户地址：胜多负少 ||用户户号：521");
        dto2.setValue("customerNo值");

        list.add(dto);
        list.add(dto2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getPurchaseRecord() {
        FeePayDetailQueryParam param = new FeePayDetailQueryParam();
        param.setDeviceType("07");
        param.setStartDate("2017-11-12");
        param.setEndDate("2017-11-13");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));

    }

    @Test
    public void getPurchaseRecordReturn() {
        PageResult<List<QueryProcDTO>> result = new PageResult<>();
        List<QueryProcDTO> list = Lists.newArrayList();

        QueryProcDTO dto = new QueryProcDTO();
        dto.setAdjustAmount(BigDecimal.TEN);
        dto.setCno("dsfsdfsd");
        dto.setCustomerAddr("dsfsaf");
        dto.setCustomerContact("13510241589");
        dto.setCustomerName("zhangsan");
        dto.setCustomerNo("dsfksdlfjkds");
        dto.setCreateUserId("1");
        dto.setCreateUserName("sfjs");
        dto.setIsEnabled(1);
        dto.setIsRepeatCard(1);
        dto.setIsValid(1);
        dto.setOrgName("成都博高");
        dto.setPayCount(10);
        dto.setPayDate(new Date());
        dto.setPayGuid("dsfsdfs");
        dto.setPayment(BigDecimal.valueOf(100));
        dto.setPayModel(1);
        dto.setPayMoney(BigDecimal.valueOf(100));
        dto.setPropertyName("561");
        dto.setRemainAmount(BigDecimal.valueOf(200));
        dto.setWriteMeter(1);
        dto.setWriteMeterTime(new Date());

        QueryProcDTO dto2 = new QueryProcDTO();
        dto2.setAdjustAmount(BigDecimal.TEN);
        dto2.setCno("dsfsdfsd");
        dto2.setCustomerAddr("dsfsaf");
        dto2.setCustomerContact("13510241589");
        dto2.setCustomerName("zhangsan");
        dto2.setCustomerNo("dsfksdlfjkds");
        dto2.setCreateUserId("1");
        dto2.setCreateUserName("sfjs");
        dto2.setIsEnabled(1);
        dto2.setIsRepeatCard(1);
        dto2.setIsValid(1);
        dto2.setOrgName("成都博高");
        dto2.setPayCount(10);
        dto2.setPayDate(new Date());
        dto2.setPayGuid("dsfsdfs");
        dto2.setPayment(BigDecimal.valueOf(100));
        dto2.setPayModel(1);
        dto2.setPayMoney(BigDecimal.valueOf(100));
        dto2.setPropertyName("561");
        dto2.setRemainAmount(BigDecimal.valueOf(200));
        dto2.setWriteMeter(1);
        dto2.setWriteMeterTime(new Date());

        list.add(dto);
        list.add(dto2);
        result.setData(list);
        result.setTotal(200L);

        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void searchFeePayByGuid() {
        Result<FeePayDTO> result = new Result<>();

    }
}
