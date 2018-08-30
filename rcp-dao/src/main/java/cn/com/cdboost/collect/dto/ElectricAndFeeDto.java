package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 电量电费统计列表
 */
public class ElectricAndFeeDto {
    private String chargingPlieGuid;
    private String deviceNo;
    private String deviceName;
    private String installAddr;
    private String installDate;
    private String projectAddr;
    private Integer useNumber;
    private BigDecimal electricityFees;
    private BigDecimal electricQuantity;
    private BigDecimal profitable;

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public BigDecimal getElectricityFees() {
        return electricityFees;
    }

    public void setElectricityFees(BigDecimal electricityFees) {
        this.electricityFees = electricityFees;
    }

    public BigDecimal getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(BigDecimal electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public BigDecimal getProfitable() {
        return profitable;
    }

    public void setProfitable(BigDecimal profitable) {
        this.profitable = profitable;
    }
}
