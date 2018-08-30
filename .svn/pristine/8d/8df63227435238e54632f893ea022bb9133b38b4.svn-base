package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.FeeOnOffInfo;
import cn.com.cdboost.collect.dto.FeeOnOffStatusListInfo;
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
public class FeeOnOffMockData {

    @Test
    public void query() {
        CstOnOffGetQueryParam param = new CstOnOffGetQueryParam();
        param.setCustomerAddr("sdfsdf");
        param.setCustomerContact("13510245841");
        param.setCustomerName("zhangsan");
        param.setDeviceNo("5454");
        param.setDeviceType("07");
        param.setOnOff(1);
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryReturn() {
        PageResult<List<FeeOnOffInfo>> result = new PageResult<>();
        List<FeeOnOffInfo> list = Lists.newArrayList();
        FeeOnOffInfo info = new FeeOnOffInfo();
        info.setCno("sdsfds");
        info.setCreateTime(new Date());
        info.setCustomerAddr("sdfsfdsd");
        info.setCustomerContact("13810230147");
        info.setCustomerName("zhangsan");
        info.setCustomerNo("dsfsdfds");
        info.setIsOn(1);
        info.setJzqCno("sfsdfs");
        info.setLastOnOffTime(new Date());
        info.setLastOnOffUserName("lisi");
        info.setOnOff(1);
        info.setOrgName("成都博高");
        info.setPropertyName("521");
        info.setRemainAmount(BigDecimal.TEN);
        info.setRemark("hahahh");
        info.setStatus(1);
        info.setUserName("wangwu");

        FeeOnOffInfo info2 = new FeeOnOffInfo();
        info2.setCno("sdsfds");
        info2.setCreateTime(new Date());
        info2.setCustomerAddr("sdfsfdsd");
        info2.setCustomerContact("13810230147");
        info2.setCustomerName("zhangsan");
        info2.setCustomerNo("dsfsdfds");
        info2.setIsOn(1);
        info2.setJzqCno("sfsdfs");
        info2.setLastOnOffTime(new Date());
        info2.setLastOnOffUserName("lisi");
        info2.setOnOff(1);
        info2.setOrgName("成都博高");
        info2.setPropertyName("521");
        info2.setRemainAmount(BigDecimal.TEN);
        info2.setRemark("hahahh");
        info2.setStatus(1);
        info2.setUserName("wangwu");
        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void onOff() {
        FeeOnOffQueryParam param = new FeeOnOffQueryParam();
        param.setGuid("sdfsdfs");
        param.setOnOff(1);
        param.setReason("sfdsfds");
        List<OnOffMeterVo> meters = Lists.newArrayList();
        OnOffMeterVo meterVo = new OnOffMeterVo();
        meterVo.setCustomerNo("sfsdfs");
        meterVo.setDeviceCno("45645");
        meterVo.setJzqCno("sdfsdfsd");

        OnOffMeterVo meterVo2 = new OnOffMeterVo();
        meterVo2.setCustomerNo("sfsdfs");
        meterVo2.setDeviceCno("45645");
        meterVo2.setJzqCno("sdfsdfsd");
        meters.add(meterVo);
        meters.add(meterVo2);
        param.setMeters(meters);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryStatus() {
        Result<FeeOnOffStatusListInfo> result = new Result<>();
        FeeOnOffStatusListInfo info = new FeeOnOffStatusListInfo();
        info.setDealNum(11);
        info.setFailNum(4);
        info.setIsUpdate(true);
        info.setStatus(1);
        info.setSuccessfulNum(30);
        info.setTotal(100);
        info.setUndealNum(20);
        List<FeeOnOffInfo> failList = Lists.newArrayList();
        FeeOnOffInfo onOffInfo = new FeeOnOffInfo();

//        info.setFailList();
//        info.setCnoList();
//        info.setSuccessList();
    }

    @Test
    public void queryResult() {
        CstOnOffOptRstGetQueryVo queryVo = new CstOnOffOptRstGetQueryVo();
        queryVo.setDate("2017-11-11 12:12:23");
        queryVo.setDataFlag("1");
        queryVo.setDeviceType("07");
        queryVo.setGuid("sdfsd");
        queryVo.setPageNumber(1);
        queryVo.setPageSize(20);

        System.out.println(JSON.toJSONString(queryVo));
    }

    @Test
    public void queryHistory() {
        CstOnOffByNoGetQueryVo queryVo = new CstOnOffByNoGetQueryVo();
        queryVo.setDeviceType("07");
        queryVo.setCustomerNo("sfsfsd");
        queryVo.setPageNumber(1);
        queryVo.setPageSize(20);

        System.out.println(JSON.toJSONString(queryVo));
    }
}
