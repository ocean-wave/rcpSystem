package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 客户电能电费日结算
 */
public class DaySettlementResponse {

    private String meterUserNo;
    private String customerName;
    private String customerContact;
    private String customerAddr;
    private String propertyName;
    private String installAddr;
    private String collectDate;
    private String deviceNo;
    private String eqTime;
    private BigDecimal eqValue;
    private String calcMoney;

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getEqTime() {
        return eqTime;
    }

    public void setEqTime(String eqTime) {
        this.eqTime = eqTime;
    }

    public BigDecimal getEqValue() {
        return eqValue;
    }

    public void setEqValue(BigDecimal eqValue) {
        this.eqValue = eqValue;
    }

    public String getCalcMoney() {
        return calcMoney;
    }

    public void setCalcMoney(String calcMoney) {
        this.calcMoney = calcMoney;
    }
}
