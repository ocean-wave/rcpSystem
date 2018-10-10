package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 使用列表
 */
public class ChargingUseDetailedDto {
    private Integer carCategory;
    private Integer payCategory;
    private BigDecimal payMoney;
    private BigDecimal profitable;
    private Integer state;
    private Integer useTime;
    private BigDecimal usePower;
    private String startTime;
    private String endTime;
    private String customerGuid;
    private String webcharNo;
    private BigDecimal deductMoney;
    private String port;
    private String chargingPlieGuid;
    private String chargingGuid;
    private String deviceNo;
    private String openNo;

    /**
     * 开启方式 1-微信 2-支付宝 3-IC卡
     */
    private Integer openMeans;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(Integer carCategory) {
        this.carCategory = carCategory;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getProfitable() {
        return profitable;
    }

    public void setProfitable(BigDecimal profitable) {
        this.profitable = profitable;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public BigDecimal getUsePower() {
        return usePower;
    }

    public void setUsePower(BigDecimal usePower) {
        this.usePower = usePower;
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

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    public String getWebcharNo() {
        return webcharNo;
    }

    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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
}
