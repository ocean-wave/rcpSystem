package cn.com.cdboost.collect.dto;

/**
 * 客户退费详细查询
 */
public class QueryDetailDto {
    /**
     * 操作人员ID
     */
    private Float createUserId;
    /**
     * 操作人员名
     */
    private String createUserName;
    /**
     * 客户地址
     */
    private String customerAddr;
    /**
     * 客户联系方式
     */
    private String customerContact;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 客户唯一标识
     */
    private String customerNo;
    /**
     * 是否有效
     */
    private Float isValid;
    /**
     * 退费原因
     */
    private String remarks;
    /**
     * 退费日期
     */
    private String refundDate;
    /**
     * 1-现金 2-微信 3-银联 4-支付宝
     */
    private Integer refundMethod;
    /**
     * 退费金额
     */
    private Float refundMoney;
    /**
     * 门牌号
     */
    private String propertyName;
    /**
     * 退款后剩余金额
     */
    private Float afterRemainAmount;
    /**
     * 流水号
     */
    private String serialNum;
    /**
     * 唯一标识
     */
    private String refundGuid;

    public Float getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Float createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Float getIsValid() {
        return isValid;
    }

    public void setIsValid(Float isValid) {
        this.isValid = isValid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public Integer getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(Integer refundMethod) {
        this.refundMethod = refundMethod;
    }

    public Float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Float getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(Float afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getRefundGuid() {
        return refundGuid;
    }

    public void setRefundGuid(String refundGuid) {
        this.refundGuid = refundGuid;
    }
}
