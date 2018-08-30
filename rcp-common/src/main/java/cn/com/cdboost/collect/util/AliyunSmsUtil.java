package cn.com.cdboost.collect.util;

import cn.com.cdboost.collect.vo.alisms.QuerySendDetailParam;
import cn.com.cdboost.collect.vo.alisms.SendBatchSmsParam;
import cn.com.cdboost.collect.vo.alisms.SendSmsParam;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云短信工具类
 */
public class AliyunSmsUtil {
    private static final Logger logger = LoggerFactory.getLogger(AliyunSmsUtil.class);

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 发送短信,所有手机号的短信内容一致
     * @param param
     * @return
     * @throws ClientException
     */
    public static final SendSmsResponse sendSms(SendSmsParam param) throws ClientException {
        logger.info("发送短信内容SendSmsParam=" + JSON.toJSONString(param));
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", param.getAccessKeyId(), param.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(param.getPhoneMobiles());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(param.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(param.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(param.getTemplateParam());

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(param.getOutId());

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 发送批量短信，每条短信内容可个性化配置
     * @param param
     * @return
     * @throws ClientException
     */
    public static final SendBatchSmsResponse batchSendSms(SendBatchSmsParam param) throws ClientException {
        logger.info("批量发送短信内容SendBatchSmsParam=" + JSON.toJSONString(param));
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", param.getAccessKeyId(), param.getAccessKeySecret());

        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumberJson(param.getPhoneNumberJson());
        //必填:短信签名-支持不同的号码发送不同的短信签名
        request.setSignNameJson(param.getSignNameJson());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(param.getTemplateCode());
        //必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParamJson(param.getTemplateParamJson());
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");
        //请求失败这里会抛ClientException异常
        SendBatchSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    /**
     * 短信发送记录查询
     * @param param
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(QuerySendDetailParam param) throws ClientException {
        logger.info("短信发送记录查询QuerySendDetailParam=" + JSON.toJSONString(param));
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", param.getAccessKeyId(), param.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(param.getPhoneNumber());
        //可选-流水号
        request.setBizId(param.getBizId());
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        request.setSendDate(param.getSendDate());
        //必填-页大小
        request.setPageSize(param.getPageSize());
        //必填-当前页码从1开始计数
        request.setCurrentPage(param.getCurrentPage());

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

}
