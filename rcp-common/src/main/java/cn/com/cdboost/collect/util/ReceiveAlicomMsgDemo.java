package cn.com.cdboost.collect.util;

import com.alibaba.fastjson.JSON;
import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 只能用于接收云通信的消息，不能用于接收其他业务的消息
 */
public class ReceiveAlicomMsgDemo {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveAlicomMsgDemo.class);


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
                System.out.println(JSON.toJSONString(contentMap));

                //
            }catch(com.google.gson.JsonSyntaxException e){
               logger.error("error_json_format:"+message.getMessageBodyAsString(),e);
            }
            Boolean dealResult=true;
            return dealResult;//返回true，则工具类自动删除已拉取的消息。
        }
    }
    public static void main(String[] args) throws com.aliyuncs.exceptions.ClientException, ParseException {
        DefaultAlicomMessagePuller puller=new DefaultAlicomMessagePuller();
        String accessKeyId="LTAIea61AljIytkp";
        String accessKeySecret="s7voGly322RSCB567e1ZMVxv6cdMba";
        String messageType="SmsReport";//短信回执：SmsReport，短信上行：SmsUp
        String queueName="Alicom-Queue-1390728788274354-SmsReport";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName
        puller.startReceiveMsg(accessKeyId,accessKeySecret ,messageType,queueName , new MyMessageListener());
    }
}