package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;

public class ChargeOnlineDto {
    /**
     * 设备状态
     */
    private Integer state;
    /**
     * 用户账户剩余金额
     */
    private BigDecimal remainAmount;
    /**
     * 已充时间
     */
    private Integer chargeredTime;
    /**
     * 剩余时间,单位秒
     */
    private Long remainTime;
    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 已冲电量百分比
     */
    private BigDecimal chargedElectric;
    /**
     * 设备状态描述
     */
    private String stateDesc;

    /**
     * 设置端口号
     * @return
     */
    private String port;

    private String chemeDesc;

    private long chargeTime;

    /**
     * 最近一次的功率
     */
    private BigDecimal power;

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getChargeredTime() {
        return chargeredTime;
    }

    public void setChargeredTime(Integer chargeredTime) {
        this.chargeredTime = chargeredTime;
    }

    public Long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Long remainTime) {
        this.remainTime = remainTime;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getChargedElectric() {
        return chargedElectric;
    }

    public void setChargedElectric(BigDecimal chargedElectric) {
        this.chargedElectric = chargedElectric;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getChemeDesc() {
        return chemeDesc;
    }

    public void setChemeDesc(String chemeDesc) {
        this.chemeDesc = chemeDesc;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }
}
