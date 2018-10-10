package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * APP端IC卡实时充电页面推送字段
 */
public class AppCharge4IcResponse {

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备状态
     */
    private Integer state;

    /**
     * 设备状态描述
     */
    private String stateDesc;

    /**
     * 已充电百分比
     */
    private String chargerElectric;

    /**
     * 当前功率
     */
    private BigDecimal currentPower;

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

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getChargerElectric() {
        return chargerElectric;
    }

    public void setChargerElectric(String chargerElectric) {
        this.chargerElectric = chargerElectric;
    }

    public BigDecimal getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(BigDecimal currentPower) {
        this.currentPower = currentPower;
    }
}
