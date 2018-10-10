package cn.com.cdboost.collect.dto.param;

/**
 * 功率超限，发送微信模板需要的参数
 */
public class PowerUpWxParam {
    /**
     * 微信openId
     */
    private String openId;

    /**
     * 微信模板first字段描述
     */
    private String firstDesc;

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
     * 备注
     */
    private String remark;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFirstDesc() {
        return firstDesc;
    }

    public void setFirstDesc(String firstDesc) {
        this.firstDesc = firstDesc;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
