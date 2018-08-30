package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.param.SmsAlarmParam;
import cn.com.cdboost.collect.dto.param.SmsPaySuccessParam;
import cn.com.cdboost.collect.dto.param.SmsRemoteSuccessParam;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public class AliyunSmsServiceTest extends BaseServiceWriteTest{
    @Autowired
    private AliyunSmsService aliyunSmsService;

//    @Test
    public void sendValidateCode() {
        String smsCode = aliyunSmsService.sendSmsCode("13810944812","2222");
        System.out.println(smsCode);
    }

//    @Test
    public void verifyCode() {
        aliyunSmsService.verifySmsCode("13219099429","009848");
        System.out.println("验证码校验成功");
    }

//    @Test
    public void sendOrderPaySuccess() {
        SmsPaySuccessParam param = new SmsPaySuccessParam();
        param.setCustomerNo("de5f3c064feb42d5ac6587fc434e26bc");
        param.setPayTime("2018年06月14日 17:23:34");
        param.setPayMoney("20.00");
        param.setPayMethod(2);
        param.setCno("070000000000000000000000000002");
        param.setCustomerName("测试表");
        aliyunSmsService.sendOrderPaySuccess(param);
    }

//    @Test
    public void remoteRechargeSuccess() {
        SmsRemoteSuccessParam param = new SmsRemoteSuccessParam();
        param.setCustomerNo("de5f3c064feb42d5ac6587fc434e26bc");
        param.setCno("070000000000000000000000000002");
        param.setCustomerName("测试表");
        param.setPayTime("2018年06月14日 17:23:34");
        param.setPayMoney("20.00");
        param.setPayMethod(2);
        aliyunSmsService.remoteRechargeSuccess(param);
    }

//    @Test
    public void sendAlarmSms() {
        List<SmsAlarmParam> param = Lists.newArrayList();
        SmsAlarmParam alarmParam = new SmsAlarmParam();
        alarmParam.setCustomerNo("de5f3c064feb42d5ac6587fc434e26bc");
        alarmParam.setCustomerName("测试表");
        alarmParam.setRemainAmount("10.00");
        alarmParam.setCollectTime("2018年06月14日 17:23:34");
        alarmParam.setAlarmThreshold("15.00");
        List<String> mobiles = Lists.newArrayList();
        mobiles.add("13810944812");
        mobiles.add("13219099429");
        alarmParam.setMobiles(mobiles);
        param.add(alarmParam);
        aliyunSmsService.sendAlarmSms(param,0);
    }
}
