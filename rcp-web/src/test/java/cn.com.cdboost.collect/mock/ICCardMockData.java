package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.FeePriceItemParamEntity;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.IcCardAddReturnInfo;
import cn.com.cdboost.collect.dto.response.IcCardQueryDetailInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */
public class ICCardMockData {
    @Test
    public void query() {
        FeeAcctGetQueryParam param = new FeeAcctGetQueryParam();
        param.setCustomerAddr("sfsdf");
        param.setCustomerContact("13510241025");
        param.setCustomerName("zhangsan");
        param.setDeviceNo("854564");
        param.setDeviceType("07");
        param.setStatus("1");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryReturn() {
        PageResult<List<AcctInfo>> result = new PageResult<>();
        List<AcctInfo> list = Lists.newArrayList();
        AcctInfo info = new AcctInfo();
        info.setAcctDatetime("2017-11-12 12:12:15");
        info.setCardId(1L);
        info.setCno("sfdsfsd");
        info.setCustomerAddr("sfsdfs");
        info.setCustomerContact("13210245482");
        info.setCustomerName("zhangsan");
        info.setCustomerNo("sdfdsfsd");
        info.setDictItemName("sdfsdf");
        info.setDictItemValue("fdgd");
        info.setIsAccount(1);
        info.setIsEnabled(1);
        info.setOrgName("成都博高");
        info.setPriceSolsCode("sfsd");
        info.setPropertyName("12");
        info.setRemainAmount(BigDecimal.TEN);

        AcctInfo info2 = new AcctInfo();
        info2.setAcctDatetime("2017-11-12 12:12:15");
        info2.setCardId(1L);
        info2.setCno("sfdsfsd");
        info2.setCustomerAddr("sfsdfs");
        info2.setCustomerContact("13210245482");
        info2.setCustomerName("zhangsan");
        info2.setCustomerNo("sdfdsfsd");
        info2.setDictItemName("sdfsdf");
        info2.setDictItemValue("fdgd");
        info2.setIsAccount(1);
        info2.setIsEnabled(1);
        info2.setOrgName("成都博高");
        info2.setPriceSolsCode("sfsd");
        info2.setPropertyName("12");
        info2.setRemainAmount(BigDecimal.TEN);

        list.add(info);
        list.add(info2);
        result.setData(list);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));
    }


    @Test
    public void queryDetail() {
        Result<IcCardQueryDetailInfo> result = new Result<>();
        IcCardQueryDetailInfo info = new IcCardQueryDetailInfo();
        List<FeePriceItemParamEntity> feePriceItemList = Lists.newArrayList();
        FeePriceItemParamEntity entity = new FeePriceItemParamEntity();
        entity.setDataValue1(123.10d);
        entity.setDataValue2(12.32d);
        entity.setItemCode1("sdfsdf");
        entity.setItemCode2("sdfsd");
        entity.setItemName1("sdfds");
        entity.setItemName2("gfdgdf");

        FeePriceItemParamEntity entity2 = new FeePriceItemParamEntity();
        entity2.setDataValue1(123.10d);
        entity2.setDataValue2(12.32d);
        entity2.setItemCode1("sdfsdf");
        entity2.setItemCode2("sdfsd");
        entity2.setItemName1("sdfds");
        entity2.setItemName2("gfdgdf");
        feePriceItemList.add(entity);
        feePriceItemList.add(entity2);

        info.setFeePriceItemList(feePriceItemList);

        AcctDetailInfo acctDetailInfo = new AcctDetailInfo();
        acctDetailInfo.setAlertFee1(BigDecimal.ONE);
        acctDetailInfo.setAlertFee2(BigDecimal.TEN);
        acctDetailInfo.setCardId("12");
        acctDetailInfo.setChangeCount(12);
        acctDetailInfo.setChargeUserName("sdfsd");
        acctDetailInfo.setCno("sdfsdf");
        acctDetailInfo.setCornerFee(BigDecimal.TEN);
        acctDetailInfo.setCustomerAddr("sfsdf");
        acctDetailInfo.setCustomerContact("13510242587");
        acctDetailInfo.setCustomerName("zhangsan");
        acctDetailInfo.setCustomerNo("sdfsdfsd");
        acctDetailInfo.setCurTranfRto("sdf");
        acctDetailInfo.setDictItemName("sdfsd");
        acctDetailInfo.setDictItemValue("sdfsd");
        acctDetailInfo.setEffectiveDate(new Date());
        acctDetailInfo.setInitAmount(BigDecimal.TEN);
        acctDetailInfo.setIsAccount(1);
        acctDetailInfo.setIsEnabled(1);
        acctDetailInfo.setMeterType(7);
        acctDetailInfo.setPayCount(10);
        acctDetailInfo.setPayDate(new Date());
        acctDetailInfo.setPayGuid("fsdfsd");
        acctDetailInfo.setPayment(BigDecimal.TEN);
        acctDetailInfo.setPayMoney(BigDecimal.TEN);
        acctDetailInfo.setPriceSolsCode("dsfsd");
        acctDetailInfo.setPropertyName("121");
        acctDetailInfo.setRatio(10);
        acctDetailInfo.setRemainAmount(BigDecimal.TEN);
        acctDetailInfo.setVolTranfRto("sfdds");

        info.setAcctDetailInfo(acctDetailInfo);

        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void add() {
        IcCardAddQueryParam param = new IcCardAddQueryParam();
        param.setAlertFee1(BigDecimal.TEN);
        param.setAlertFee2(BigDecimal.ONE);
        param.setCno("sdfsdfsdfds");
        param.setCurTranfRto(10);
        param.setCustomerNo("fsdsd");
        param.setMeterType("07");
        param.setPayCount(10);
        param.setPayment(BigDecimal.valueOf(100));
        param.setVolTranfRto(12);
        param.setPayMoney(BigDecimal.valueOf(200));

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void addReturn() {
        Result<IcCardAddReturnInfo> result = new Result<>();
        IcCardAddReturnInfo info = new IcCardAddReturnInfo();
        info.setCreateDate(new Date());
        info.setPayGuid("sdfsdfdsf");
        info.setChargeUserName("zhangsan");
        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryChangeICCard() {
        ChangeICCardQueryParam param = new ChangeICCardQueryParam();
        param.setCno("sdfsdfds");
        param.setCustomerNo("sfsdfsd");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryChangeICCardReturn() {
        PageResult<List<FeeChangeICCardInfo>> result = new PageResult<>();
        List<FeeChangeICCardInfo> list = Lists.newArrayList();
        FeeChangeICCardInfo info = new FeeChangeICCardInfo();
        info.setCardId("12");
        info.setChangeRemark("wohaha");
        info.setChangeTime(new Date());
        info.setChangeUserName("lisi");
        info.setOldCardId("11");

        FeeChangeICCardInfo info2 = new FeeChangeICCardInfo();
        info2.setCardId("12");
        info2.setChangeRemark("wohaha");
        info2.setChangeTime(new Date());
        info2.setChangeUserName("lisi");
        info2.setOldCardId("11");

        list.add(info);
        list.add(info2);
        result.setData(list);
        result.setTotal(50L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void addChangeICCard() {
        ChangeICCardParam param = new ChangeICCardParam();
        param.setChangeRemark("sfdsfsd");
        param.setCno("fsdsdfkdssdf");
        param.setCustomerNo("sdfsdfds");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void reAdd() {
        CustomerReAcctParam param = new CustomerReAcctParam();
        param.setCno("sfsdfsd");
        param.setCustomerNo("sdfsdfsd");
        param.setInitAmount("100");
        param.setPayment("200");
        param.setPayMoney("3432");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void reAddReturn() {
        Result<IcCardAddReturnInfo> result = new Result<>();
        IcCardAddReturnInfo info = new IcCardAddReturnInfo();
        info.setChargeUserName("zhangsan");
        info.setPayGuid("dsfsdfsd");
        info.setCreateDate(new Date());
        result.setData(info);

        System.out.println(JSON.toJSONString(result));
    }
}
