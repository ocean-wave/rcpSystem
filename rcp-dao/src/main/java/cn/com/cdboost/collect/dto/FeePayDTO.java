package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.model.FeePay;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class FeePayDTO extends FeePay {
    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 联系方式
     */
    private String customerContact;

    /**
     * 客户地址
     */
    private String customerAddr;

    /**
     * 是否有效
     */
    private int isEnabled;

    /**
     * 收费人员
     */
    private String chargeUserName;

    /**
     * 撤销时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /**
     * 撤销人员
     */
    private String cancelUserName;

    /**
     * 撤销原因
     */
    private String reason;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getChargeUserName() {
        return chargeUserName;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelUserName() {
        return cancelUserName;
    }

    public void setCancelUserName(String cancelUserName) {
        this.cancelUserName = cancelUserName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}