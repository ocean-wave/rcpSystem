package cn.com.cdboost.collect.dto.param;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 功率超限告警短信参数
 */
public class PowerUpAlarmParam {
    /**
     * 用户唯一标识
     */
    @JSONField(serialize=false)
    private String customerNo;

    /**
     * 手机号
     */
    @JSONField(serialize=false)
    private String mobilePhone;

    /**
     * 充电guid
     */
    @JSONField(serialize=false)
    private String chargingGuid;

    /**
     * 电瓶车功率
     */
    private String currentPower;

    /**
     * 所选方案功率上限
     */
    private String powerUp;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 充电方案
     */
    private String chargeScheme;

    /**
     * 充电位置
     */
    private String address;

    /**
     * 多少分钟后
     */
    private Integer minute;

    /**
     * 提档后充电方案描述
     */
    private String schemeDesc;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(String currentPower) {
        this.currentPower = currentPower;
    }

    public String getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(String powerUp) {
        this.powerUp = powerUp;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getChargeScheme() {
        return chargeScheme;
    }

    public void setChargeScheme(String chargeScheme) {
        this.chargeScheme = chargeScheme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getSchemeDesc() {
        return schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }
}
