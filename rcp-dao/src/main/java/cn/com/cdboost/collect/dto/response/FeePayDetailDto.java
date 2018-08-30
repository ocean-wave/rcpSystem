package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录明细相关信息
 */
public class FeePayDetailDto {
    /**
     * 用户唯一标识
     */
    private String customerNo;

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
     * 告警金额1
     */
    private BigDecimal alarmThreshold;

    /**
     * 告警金额2
     */
    private BigDecimal alarmThreshold1;

    /**
     * 告警金额3
     */
    private BigDecimal alarmThreshold2;

    /**
     * 透支金额
     */
    private Integer overdraftAmount;

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

    /**
     * 购电次数
     */
    private Integer payCount;

    /**
     * 购电日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payDate;

    /**
     * 购电金额
     */
    private BigDecimal payMoney;

    /**
     * 缴费的金额
     */
    private BigDecimal payment;

    /**
     * 充值记录是否有效 1标识有效0 标识无效
     */
    private Integer isValid;

    /**
     * 1-现金 2-微信 3-银联 4-支付宝
     */
    private String payMethod;

    /**
     * 支付方式值
     */
    private Integer payMethodValue;

    /**
     * 缴费流水号
     */
    private String serialNum;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

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

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public BigDecimal getAlarmThreshold1() {
        return alarmThreshold1;
    }

    public void setAlarmThreshold1(BigDecimal alarmThreshold1) {
        this.alarmThreshold1 = alarmThreshold1;
    }

    public BigDecimal getAlarmThreshold2() {
        return alarmThreshold2;
    }

    public void setAlarmThreshold2(BigDecimal alarmThreshold2) {
        this.alarmThreshold2 = alarmThreshold2;
    }

    public Integer getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(Integer overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public Integer getPayMethodValue() {
        return payMethodValue;
    }

    public void setPayMethodValue(Integer payMethodValue) {
        this.payMethodValue = payMethodValue;
    }
}
