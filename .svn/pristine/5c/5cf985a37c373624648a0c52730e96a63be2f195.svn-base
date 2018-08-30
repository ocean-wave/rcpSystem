package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.param.SmsAlarmParam;
import cn.com.cdboost.collect.dto.param.SmsPaySuccessParam;
import cn.com.cdboost.collect.dto.param.SmsRemoteSuccessParam;
import cn.com.cdboost.collect.model.Sms;

import java.util.List;

/**
 * 用户短信发送消息记录服务接口
 */
public interface SmsService extends BaseService<Sms>{
    // 发送短信验证码,并返回验证码
    String sendSmsCode(String mobilePhone);

    // 校验短信验证码
    boolean verifySmsCode(String mobilePhone, String code);

    // 订单支付成功短信通知
    void sendOrderPaySuccess(SmsPaySuccessParam param);

    // 远程充值成功短信通知
    void remoteRechargeSuccess(SmsRemoteSuccessParam param);

    // 告警信息短信通知
    void sendAlarmSms(List<SmsAlarmParam> param);

    boolean canSendSms(String customerNo,String cno, List<String> mobiles);

    int updateSms(String smsUuid,String mobilePhone,Integer sendStatus);

    Sms queryByParam(String smsUuid,String mobilePhone);

    // 查询当天发送的未收到回执通知的短信记录
    List<Sms> queryNotReceivedNoticeSmsList();
}
