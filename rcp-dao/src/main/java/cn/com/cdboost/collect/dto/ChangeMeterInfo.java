package cn.com.cdboost.collect.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zc
 * @desc 换表记录
 * @create 2017-10-16 10:13
 **/
public class ChangeMeterInfo {
    /**
     * 更换人员
     */
    private String userName;

    /**
     * 用户编号
     */
    private String customerNo;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户电话
     */
    private String customerContact;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 旧设备CNO
     */
    private String cno;

    /**
     * 新设备CNO
     */
    private String newCno;

    /**
     * 旧电表地址
     */
    private String meterAddr;

    /**
     * 新表地址
     */
    private String newMeterAddr;

    /**
     * 旧表号
     */
    private String meterNo;

    /**
     * 新表号
     */
    private String newMeterNo;

    /**
     * 旧表总示示数
     */
    private BigDecimal power;

    /**
     * 新表总示示数
     */
    private BigDecimal newPower;

    /**
     * 旧表剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 新表剩余金额
     */
    private BigDecimal newRemainAmount;

    /**
     * 更换时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date changeTime;

    /**
     * 换表用户id
     */
    private Long changeUserId;

    /**
     * 更换数据的唯一标识
     */
    private String changeUnique;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getNewCno() {
        return newCno;
    }

    public void setNewCno(String newCno) {
        this.newCno = newCno;
    }

    public String getMeterAddr() {
        return meterAddr;
    }

    public void setMeterAddr(String meterAddr) {
        this.meterAddr = meterAddr;
    }

    public String getNewMeterAddr() {
        return newMeterAddr;
    }

    public void setNewMeterAddr(String newMeterAddr) {
        this.newMeterAddr = newMeterAddr;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getNewMeterNo() {
        return newMeterNo;
    }

    public void setNewMeterNo(String newMeterNo) {
        this.newMeterNo = newMeterNo;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getNewPower() {
        return newPower;
    }

    public void setNewPower(BigDecimal newPower) {
        this.newPower = newPower;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getNewRemainAmount() {
        return newRemainAmount;
    }

    public void setNewRemainAmount(BigDecimal newRemainAmount) {
        this.newRemainAmount = newRemainAmount;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Long getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(Long changeUserId) {
        this.changeUserId = changeUserId;
    }

    public String getChangeUnique() {
        return changeUnique;
    }

    public void setChangeUnique(String changeUnique) {
        this.changeUnique = changeUnique;
    }
}
