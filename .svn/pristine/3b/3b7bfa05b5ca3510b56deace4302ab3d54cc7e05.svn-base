package cn.com.cdboost.collect.vo.alisms;

/**
 * 发送批量短信参数
 */
public class SendBatchSmsParam extends SendSmsBaseParam{
    /**
     * 短信接收号码,JSON格式,批量上限为100个手机号码
     * 形如：["15000000000","15000000001"]
     */
    private String phoneNumberJson;

    /**
     * 短信签名,JSON格式
     * 形如：["云通信","云通信"]
     */
    private String signNameJson;

    /**
     * 短信模板ID
     */
    private String templateCode;

    /**
     * 短信模板变量替换JSON串
     * 形如：[{“code”:”1234”,”product”:”ytx1”},{“code”:”5678”,”product”:”ytx2”}]
     */
    private String templateParamJson;

    public String getPhoneNumberJson() {
        return phoneNumberJson;
    }

    public void setPhoneNumberJson(String phoneNumberJson) {
        this.phoneNumberJson = phoneNumberJson;
    }

    public String getSignNameJson() {
        return signNameJson;
    }

    public void setSignNameJson(String signNameJson) {
        this.signNameJson = signNameJson;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParamJson() {
        return templateParamJson;
    }

    public void setTemplateParamJson(String templateParamJson) {
        this.templateParamJson = templateParamJson;
    }
}
