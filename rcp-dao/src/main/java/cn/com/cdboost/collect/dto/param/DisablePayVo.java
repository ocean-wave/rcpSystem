package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class DisablePayVo {
    private String payGuid;
    private String reason;
    private String userId;
    private Long result;

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
