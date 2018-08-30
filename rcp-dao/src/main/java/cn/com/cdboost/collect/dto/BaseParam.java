package cn.com.cdboost.collect.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xzy
 * @desc 充值缴费查询传输参数
 * @create 2017/7/11 0011
 **/
public class BaseParam implements Serializable {
    private String customerNo;
    private String customerName;
    private String customerAddr;
    private String customerContact;
    private String meterUserNo;
    private String deviceType;
    private String writeMeter;
    private Date sdate;
    private Date edate;
    private String payModel;
    private String deviceNo;
    private int pageSize;
    private int pageNumber;
    private long userId;
    private String sumPayment;
    private String sumPayMoney;
    private String sumAdjust;
    private String rowCount;
    private String payMethod;
    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getWriteMeter() {
        return writeMeter;
    }

    public void setWriteMeter(String writeMeter) {
        this.writeMeter = writeMeter;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(String sumPayment) {
        this.sumPayment = sumPayment;
    }

    public String getSumPayMoney() {
        return sumPayMoney;
    }

    public void setSumPayMoney(String sumPayMoney) {
        this.sumPayMoney = sumPayMoney;
    }

    public String getSumAdjust() {
        return sumAdjust;
    }

    public void setSumAdjust(String sumAdjust) {
        this.sumAdjust = sumAdjust;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }
}
