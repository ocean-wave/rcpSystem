package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.param.SmsAlarmParam;
import cn.com.cdboost.collect.dto.param.SmsPaySuccessParam;
import cn.com.cdboost.collect.dto.param.SmsRemoteSuccessParam;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12 0012.
 */
public class SmsServiceTest extends BaseServiceWriteTest{

    @Autowired
    private SmsService smsService;

    @Test
    public void sendOrderPaySuccess() {
        SmsPaySuccessParam param = new SmsPaySuccessParam();
        param.setCustomerNo("128c6ae2a0ed432aba0d8dccc02591b9");
        param.setPayTime("2018年04月12日 17:23:34");
        param.setPayMoney("20.00");
        param.setPayMethod(2);
        param.setCno("070000000000000000000023121354");
        param.setCustomerName("fly8001");
        smsService.sendOrderPaySuccess(param);
    }

    @Test
    public void remoteRechargeSuccess() {
        SmsRemoteSuccessParam param = new SmsRemoteSuccessParam();
        param.setCustomerNo("128c6ae2a0ed432aba0d8dccc02591b9");
        param.setCno("070000000000000000000023121354");
        param.setCustomerName("fly8001");
        param.setPayTime("2018年04月12日 17:23:34");
        param.setPayMoney("20.00");
        param.setPayMethod(2);
        smsService.remoteRechargeSuccess(param);
    }

    @Test
    public void sendAlarmSms() {
        List<SmsAlarmParam> param = Lists.newArrayList();
        SmsAlarmParam alarmParam = new SmsAlarmParam();
        alarmParam.setCustomerNo("128c6ae2a0ed432aba0d8dccc02591b9");
        alarmParam.setCustomerName("fly8001");
        alarmParam.setRemainAmount("10.00");
        alarmParam.setCollectTime("2018年04月12日 17:23:34");
        alarmParam.setAlarmThreshold("15.00");
        List<String> mobiles = Lists.newArrayList();
        mobiles.add("12110923812");
        mobiles.add("13110923812");
        alarmParam.setMobiles(mobiles);
        param.add(alarmParam);
        smsService.sendAlarmSms(param);
    }
}
