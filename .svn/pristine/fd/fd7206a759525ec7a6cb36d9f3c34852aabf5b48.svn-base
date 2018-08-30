package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.constant.SmsConstant;
import cn.com.cdboost.collect.model.Sms;
import cn.com.cdboost.collect.service.SmsSchemeService;
import cn.com.cdboost.collect.service.SmsService;
import cn.com.cdboost.collect.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收阿里云短信的回执消息
 */
@Service
public class ReceiveAlicomMsg implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveAlicomMsg.class);

    @Autowired
    private static SmsService smsService;
    @Autowired
    private static SmsSchemeService smsSchemeService;

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.queueName}")
    private String queueName;

    @Autowired
    public void setSmsService(SmsService smsService) {
        ReceiveAlicomMsg.smsService = smsService;
    }

    @Autowired
    public void setSmsSchemeService(SmsSchemeService smsSchemeService) {
        ReceiveAlicomMsg.smsSchemeService = smsSchemeService;
    }


    //以短信回执消息处理为例
     static class MyMessageListener implements MessageListener {
        private Gson gson=new Gson();

        @Override
        public boolean dealMessage(Message message) {
            System.out.println("message handle: " + message.getReceiptHandle());
            System.out.println("message body: " + message.getMessageBodyAsString());
            System.out.println("message id: " + message.getMessageId());
            System.out.println("message dequeue count:" + message.getDequeueCount());
            //以短信回执消息处理为例
            try{
                Map<String,Object> contentMap=gson.fromJson(message.getMessageBodyAsString(), HashMap.class);
                String phoneNumber=(String)contentMap.get("phone_number");
                Boolean success=(Boolean)contentMap.get("success");
                String bizId=(String)contentMap.get("biz_id");
                String outId=(String)contentMap.get("out_id");
                String sendTime=(String)contentMap.get("send_time");
                String reportTime=(String)contentMap.get("report_time");
                String errCode=(String)contentMap.get("err_code");
                String errMsg=(String)contentMap.get("err_msg");
                String smsSize=(String)contentMap.get("sms_size");
                logger.info("阿里云短信回执消息接收消息体：" + JSON.toJSONString(contentMap));

                Date sendTimeObj = null;
                try {
                    sendTimeObj = DateUtil.parse(sendTime);
                } catch (ParseException e) {
                    logger.error("日期转换异常",e);
                }

                // 查询
                Sms sms = smsService.queryByParam(bizId, phoneNumber);
                if (sms == null) {
                    // 处理可能已经删除了的记录
                    return true;
                }
                Integer messageStatus = sms.getMessageStatus();
                if (!messageStatus.equals(0)) {
                    // 之前已处理，处理重复消息通知
                    return true;
                }

                //短信更新
                if (success) {
                    // 更新方案表记录
                    Integer isAutoBuider = sms.getIsAutoBuider();
                    if (SmsConstant.SmsType.ALARM.getType().equals(sms.getMsgType()) && isAutoBuider == 1) {
                        // 告警短信
                        smsSchemeService.updateCountAndSendTime(sms.getCustomerNo(),phoneNumber,sendTimeObj,new Date());
                    }

                    // 更新短信发送历史记录
                    Sms param = new Sms();
                    param.setId(sms.getId());
                    param.setSendTime(sendTimeObj);
                    param.setMessageStatus(1);
                    param.setUpdateTime(new Date());
                    smsService.updateByPrimaryKeySelective(param);
                } else {
                    Sms param = new Sms();
                    param.setId(sms.getId());
                    param.setSendTime(sendTimeObj);
                    param.setMessageStatus(2);
                    param.setUpdateTime(new Date());
                    smsService.updateByPrimaryKeySelective(param);
                }
            }catch(com.google.gson.JsonSyntaxException e){
                logger.error("error_json_format:"+message.getMessageBodyAsString(),e);
            }
            Boolean dealResult=true;
            return dealResult;//返回true，则工具类自动删除已拉取的消息。
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultAlicomMessagePuller puller=new DefaultAlicomMessagePuller();
//        String accessKeyId="LTAIea61AljIytkp";
//        String accessKeySecret="s7voGly322RSCB567e1ZMVxv6cdMba";
        String messageType="SmsReport";//短信回执：SmsReport，短信上行：SmsUp
//        String queueName="Alicom-Queue-1390728788274354-SmsReport";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName
        puller.startReceiveMsg(accessKeyId,accessKeySecret ,messageType,queueName , new ReceiveAlicomMsg.MyMessageListener());
        logger.info("阿里云短信回执消息处理类启动完毕");
    }
}
