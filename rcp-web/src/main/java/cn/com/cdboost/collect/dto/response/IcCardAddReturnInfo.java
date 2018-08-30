package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 重新开户
 */
public class IcCardAddReturnInfo {
    private String chargeUserName;
    private String payGuid;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public String getChargeUserName() {
        return chargeUserName;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
