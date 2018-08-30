package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.model.Sms;

import java.util.List;

/**
 * 阿里云短信服务
 */
public interface AliyunSmsService {
    // 发送短信验证码,并返回验证码
    String sendSmsCode(String mobilePhone,String customerNo);
    // 校验短信验证码
    void verifySmsCode(String mobilePhone, String code);

    // 订单支付成功短信通知
    void sendOrderPaySuccess(SmsPaySuccessParam param);

    // 远程充值成功短信通知
    void remoteRechargeSuccess(SmsRemoteSuccessParam param);

    // 告警信息短信通知
    void sendAlarmSms(List<SmsAlarmParam> param,Integer isAutoBuider);


    // 充电正常结束，短信通知
    void sendChargeNormalAlarm(ChargeNormalAlarmParam alarmParam);

    // 充电异常结束，短信通知
    void sendChargeAbnormalAlarm(ChargeAbnormalAlarmParam alarmParam);

    void checkSendStatus(List<Sms> list);
}
