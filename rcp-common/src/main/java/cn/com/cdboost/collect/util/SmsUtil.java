package cn.com.cdboost.collect.util;

import cn.com.cdboost.collect.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 网易云短信工具类
 */
public final class SmsUtil {

    // 发送通知类和运营类短信
    public static final TemplateSmsResponse sendTemplateSms(TemplateSmsParam smsParam) {
        Map<String,String> param = new HashMap<>();
        param.put("templateid",String.valueOf(smsParam.getTemplateId()));
        param.put("mobiles",smsParam.getMobiles());
        param.put("params",smsParam.getParams());
        JSONObject jsonObject = HttpClientUtil.post(smsParam.getUrl(),smsParam.getAppKey(),smsParam.getAppSecret(),param);
        TemplateSmsResponse response = null;
        if (jsonObject != null) {
            response = JSON.parseObject(JSON.toJSONString(jsonObject), TemplateSmsResponse.class);
        }

        return response;
    }

    // 查询短信的发送状态
    public static final TemplateSmsStatusResponse querySmsStatus(SmsStatusQueryParam queryParam) {
        Map<String,String> param = new HashMap<>();
        param.put("sendid",String.valueOf(queryParam.getSendId()));
        JSONObject jsonObject = HttpClientUtil.post(queryParam.getUrl(), queryParam.getAppKey(), queryParam.getAppSecret(), param);
        TemplateSmsStatusResponse response = null;
        if (jsonObject != null) {
            response = JSON.parseObject(JSON.toJSONString(jsonObject), TemplateSmsStatusResponse.class);
        }

        return response;
    }

    // 发送短信验证码,并返回验证码
    public static final String sendSmsCode(SmsCodeParam codeParam) {
        Map<String,String> param = new HashMap<>();
        param.put("mobile",codeParam.getMobilePhone());
        param.put("templateid",String.valueOf(codeParam.getTemplateId()));
        JSONObject jsonObject = HttpClientUtil.post(codeParam.getUrl(), codeParam.getAppKey(), codeParam.getAppSecret(), param);
        if (jsonObject != null) {
            Integer code = (Integer) jsonObject.get("code");
            if (code == 200) {
                String obj = (String) jsonObject.get("obj");
                return obj;
            }
        }
        return "";
    }

    // 校验验证码
    public static final boolean verifycode(VerifySmsCodeParam codeParam) {
        Map<String,String> param = new HashMap<>();
        param.put("mobile",codeParam.getMobliePhone());
        param.put("code",codeParam.getCode());
        JSONObject jsonObject = HttpClientUtil.post(codeParam.getUrl(), codeParam.getAppKey(), codeParam.getAppSecret(), param);
        if (jsonObject != null) {
            Integer code = (Integer) jsonObject.get("code");
            if (code == 200) {
                return true;
            }
        }
        return false;
    }

}
