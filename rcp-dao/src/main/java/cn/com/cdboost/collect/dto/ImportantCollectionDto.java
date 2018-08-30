package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 实时数据、历史数据明细页面，用户相关信息
 */
public class ImportantCollectionDto {


    private String customerNo;


    private String meterUserNo;

    private String customerName;
    private String customerContact;
    private String customerAddr;
    private String propertyName;
    private String cNo;
    private String collectDate;
    private String activeTotal;
    private String unactiveTotal;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

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

    public String getcNo() {
        return cNo;
    }

    public void setcNo(String cNo) {
        this.cNo = cNo;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getActiveTotal() {
        return activeTotal;
    }

    public void setActiveTotal(String activeTotal) {
        this.activeTotal = activeTotal;
    }

    public String getUnactiveTotal() {
        return unactiveTotal;
    }

    public void setUnactiveTotal(String unactiveTotal) {
        this.unactiveTotal = unactiveTotal;
    }
}
