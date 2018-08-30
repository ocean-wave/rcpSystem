package cn.com.cdboost.util;

import cn.com.cdboost.collect.util.AliyunSmsUtil;
import cn.com.cdboost.collect.vo.alisms.QuerySendDetailParam;
import cn.com.cdboost.collect.vo.alisms.SendBatchSmsParam;
import cn.com.cdboost.collect.vo.alisms.SendSmsParam;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;

/**
 * 阿里云短信单元测试
 */
public class AliyunSmsUtilTest {

//    @Test
    public void testSend() throws ClientException {
        //发短信
        SendSmsParam sendSmsParam = new SendSmsParam();
        sendSmsParam.setAccessKeyId("LTAIea61AljIytkp");
        sendSmsParam.setAccessKeySecret("s7voGly322RSCB567e1ZMVxv6cdMba");
        sendSmsParam.setPhoneMobiles("13810944812");
        sendSmsParam.setSignName("成都博高");
        sendSmsParam.setTemplateCode("SMS_137425732");
        sendSmsParam.setTemplateParam("{\"code\":\"123456\"}");
        SendSmsResponse response = AliyunSmsUtil.sendSms(sendSmsParam);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
    }

//    @Test
    public void batchSend() throws ClientException {
        SendBatchSmsParam param = new SendBatchSmsParam();
        param.setTemplateParamJson("[{\"alarmThreshold\":\"30.00\",\"collectTime\":\"2018-06-26 16:22:31\",\"customerName\":\"测试广州仪表\",\"deviceName\":\"电表\",\"deviceNo\":\"201606000674\",\"installAddr\":\"黑板下面\",\"remainAmount\":\"-0.14\"},{\"alarmThreshold\":\"30.00\",\"collectTime\":\"2018-06-26 16:22:31\",\"customerName\":\"测试广州仪表\",\"deviceName\":\"电表\",\"deviceNo\":\"201606000674\",\"installAddr\":\"黑板下面\",\"remainAmount\":\"-0.14\"},{\"alarmThreshold\":\"30.00\",\"collectTime\":\"2018-06-26 16:22:31\",\"customerName\":\"测试广州仪表\",\"deviceName\":\"电表\",\"deviceNo\":\"201606000674\",\"installAddr\":\"黑板下面\",\"remainAmount\":\"-0.14\"},{\"alarmThreshold\":\"50.00\",\"collectTime\":\"2018-06-26 16:22:48\",\"customerName\":\"测试1012063\",\"deviceName\":\"电表\",\"deviceNo\":\"1012063\",\"installAddr\":\"12\",\"remainAmount\":\"-291.70\"},{\"alarmThreshold\":\"50.00\",\"collectTime\":\"2018-06-26 16:22:48\",\"customerName\":\"测试1012063\",\"deviceName\":\"电表\",\"deviceNo\":\"1012063\",\"installAddr\":\"12\",\"remainAmount\":\"-291.70\"},{\"alarmThreshold\":\"50.00\",\"collectTime\":\"2018-06-26 16:22:48\",\"customerName\":\"测试1012063\",\"deviceName\":\"电表\",\"deviceNo\":\"1012063\",\"installAddr\":\"12\",\"remainAmount\":\"-291.70\"}]");
        param.setSignNameJson("[\"成都博高\",\"成都博高\",\"成都博高\",\"成都博高\",\"成都博高\",\"成都博高\"]");
        param.setPhoneNumberJson("[\"13810944812\",\"18030501521\",\"18030501527\",\"13810944812\",\"18030501521\",\"18030501527\"]");
        param.setTemplateCode("SMS_137425816");
        param.setAccessKeyId("LTAIea61AljIytkp");
        param.setAccessKeySecret("s7voGly322RSCB567e1ZMVxv6cdMba");
        SendBatchSmsResponse response = AliyunSmsUtil.batchSendSms(param);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
    }

    @Test
    public void testQueryDetail() throws ClientException {
        //查明细
        QuerySendDetailParam detailParam = new QuerySendDetailParam();
        detailParam.setAccessKeyId("LTAIea61AljIytkp");
        detailParam.setAccessKeySecret("s7voGly322RSCB567e1ZMVxv6cdMba");
        detailParam.setPhoneNumber("13810944812");
        detailParam.setBizId("468909029997655966^0");
        detailParam.setSendDate("20180626");
        detailParam.setCurrentPage(1L);
        detailParam.setPageSize(50L);
        QuerySendDetailsResponse querySendDetailsResponse = AliyunSmsUtil.querySendDetails(detailParam);
        System.out.println("短信明细查询接口返回数据----------------");
        System.out.println("Code=" + querySendDetailsResponse.getCode());
        System.out.println("Message=" + querySendDetailsResponse.getMessage());
        int i = 0;
        for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
            System.out.println("SmsSendDetailDTO["+i+"]:");
            System.out.println("Content=" + smsSendDetailDTO.getContent());
            System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
            System.out.println("OutId=" + smsSendDetailDTO.getOutId());
            System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
            System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
            System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
            System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
            System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
        }
        System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
        System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
    }
}
