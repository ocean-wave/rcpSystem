package cn.com.cdboost.collect.dto.chargerApp.vo;

public class WxBaseInfoVo {
    /**
     * 1微信，2支付宝
     */
    private Integer appType;
    /**
     * appType=1时表示微信openId,appType=2时表示支付宝userId
     */
    private String openId;
    /**
     * 设备ID
     */
    private String deviceNo;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

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
}
