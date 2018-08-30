package cn.com.cdboost.collect.vo;

/**
 * 短信验证码校验入参
 */
public class VerifySmsCodeParam extends SmsBaseParam{
    /**
     * 手机号
     */
    private String mobliePhone;

    /**
     * 验证码
     */
    private String code;

    public String getMobliePhone() {
        return mobliePhone;
    }

    public void setMobliePhone(String mobliePhone) {
        this.mobliePhone = mobliePhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
