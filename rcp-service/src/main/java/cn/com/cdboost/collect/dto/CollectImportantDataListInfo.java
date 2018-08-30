package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 实时采集，重点用户列表返回信息
 */
public class CollectImportantDataListInfo {

    /**
     * 用户在系统的唯一标识
     */
    private String customerNo;

    /**
     * 设备唯一标识
     */
    private String deviceCno;

    /**
     * 集中器唯一标识
     */
    private String jzqCno;

    /**
     * 集中器号
     */
    private String jzqNo;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户联系方式
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 招测标识（0未招测，1召测成功，-1召测失败）
     */
    private Integer readStatus;

    /**
     * 是否在线
     */
    private Integer isOnline;

    /**
     * 有功（总）
     */
    private BigDecimal pr0;

    /**
     * A相电流
     */
    private BigDecimal currentA;

    /**
     * B相电流
     */
    private BigDecimal currentB;

    /**
     * C相电流
     */
    private BigDecimal currentC;

    /**
     * A相电压
     */
    private BigDecimal voltageA;

    /**
     * B相电压
     */
    private BigDecimal voltageB;

    /**
     * C相电压
     */
    private BigDecimal voltageC;

    /**
     * groupGuid
     */
    private String groupGuid;

    /**
     * 采集时间
     */
    private String collectTime;
    /** 新加
     * 瞬时正向有功总
     */
    private BigDecimal instantPower;
    /**
     * 瞬时正向有功A相
     */
    private BigDecimal instantPowerA;
    /**
     * 瞬时正向有功B相
     */
    private BigDecimal instantPowerB;
    /**
     * 瞬时正向有功C相
     */
    private BigDecimal instantPowerC;

    public BigDecimal getInstantPower() {
        return instantPower;
    }

    public void setInstantPower(BigDecimal instantPower) {
        this.instantPower = instantPower;
    }

    public BigDecimal getInstantPowerA() {
        return instantPowerA;
    }

    public void setInstantPowerA(BigDecimal instantPowerA) {
        this.instantPowerA = instantPowerA;
    }

    public BigDecimal getInstantPowerB() {
        return instantPowerB;
    }

    public void setInstantPowerB(BigDecimal instantPowerB) {
        this.instantPowerB = instantPowerB;
    }

    public BigDecimal getInstantPowerC() {
        return instantPowerC;
    }

    public void setInstantPowerC(BigDecimal instantPowerC) {
        this.instantPowerC = instantPowerC;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
    }

    public String getJzqCno() {
        return jzqCno;
    }

    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public BigDecimal getPr0() {
        return pr0;
    }

    public void setPr0(BigDecimal pr0) {
        this.pr0 = pr0;
    }

    public BigDecimal getCurrentA() {
        return currentA;
    }

    public void setCurrentA(BigDecimal currentA) {
        this.currentA = currentA;
    }

    public BigDecimal getCurrentB() {
        return currentB;
    }

    public void setCurrentB(BigDecimal currentB) {
        this.currentB = currentB;
    }

    public BigDecimal getCurrentC() {
        return currentC;
    }

    public void setCurrentC(BigDecimal currentC) {
        this.currentC = currentC;
    }

    public BigDecimal getVoltageA() {
        return voltageA;
    }

    public void setVoltageA(BigDecimal voltageA) {
        this.voltageA = voltageA;
    }

    public BigDecimal getVoltageB() {
        return voltageB;
    }

    public void setVoltageB(BigDecimal voltageB) {
        this.voltageB = voltageB;
    }

    public BigDecimal getVoltageC() {
        return voltageC;
    }

    public void setVoltageC(BigDecimal voltageC) {
        this.voltageC = voltageC;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }
}
