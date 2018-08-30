package cn.com.cdboost.collect.vo;

/**
 * 短信状态查询入参
 */
public class SmsStatusQueryParam extends SmsBaseParam{
    /**
     * 网易云接口返回的sendid
     */
    private Long sendId;

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }
}
