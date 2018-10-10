package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * ic卡使用记录
 */
public class ChargingICUseDto {
    private String deviceNo;
    private String port;
    private String chargingGuid;
    private String chargingPlieGuid;
    private Integer chargingTime;
    private String startTime;
    private String endTime;
    private BigDecimal remainAmount;
    private String installAddr;
    private String openNo;
    private String deductMoney;
    //实际用电量
    private BigDecimal usePower;
    //最大功率
    private BigDecimal maxPower;

    /**
     * 开启方式 1-微信 2-支付宝 3-IC卡
     */
    private Integer openMeans;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public Integer getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public Integer getOpenMeans() {
        return openMeans;
    }

    public void setOpenMeans(Integer openMeans) {
        this.openMeans = openMeans;
    }

    public String getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(String deductMoney) {
        this.deductMoney = deductMoney;
    }

    public BigDecimal getUsePower() {
        return usePower;
    }

    public void setUsePower(BigDecimal usePower) {
        this.usePower = usePower;
    }

    public BigDecimal getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(BigDecimal maxPower) {
        this.maxPower = maxPower;
    }
}
