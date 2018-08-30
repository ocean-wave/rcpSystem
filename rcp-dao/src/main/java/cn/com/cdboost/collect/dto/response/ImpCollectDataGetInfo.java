package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 重点用户历史采集数据列表返回信息
 */
public class ImpCollectDataGetInfo {
    /**
     * 设备cno
     */
    private String deviceCno;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 联系方式
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 采集时间
     */
    private String collectTime;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 有功总
     */
    private BigDecimal pr0;

    /**
     * 有功总电量
     */
    private BigDecimal activeTotal;

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
     * 瞬时正向有功总
     */
    private BigDecimal instantPower;

    /**
     * 瞬时正向有功A相
     */
    private BigDecimal instantPowerA;

    /**
     * 瞬时正向有功A相
     */
    private BigDecimal instantPowerB;

    /**
     * 瞬时正向有功A相
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

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

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

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public BigDecimal getPr0() {
        return pr0;
    }

    public void setPr0(BigDecimal pr0) {
        this.pr0 = pr0;
    }

    public BigDecimal getActiveTotal() {
        return activeTotal;
    }

    public void setActiveTotal(BigDecimal activeTotal) {
        this.activeTotal = activeTotal;
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
}
