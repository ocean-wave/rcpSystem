package cn.com.cdboost.collect.dto.param;

/**
 * 充电开始，发送微信模板参数
 */
public class ChargeStartWxParam {

    /**
     * 微信openId
     */
    private String openId;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
}
