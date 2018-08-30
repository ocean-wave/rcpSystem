package cn.com.cdboost.collect.dto.chargerApp.vo;

public class ChargeVo {
    /**
     * 微信用户唯一标识
     */
    private String openId;
    /**
     * 设备表号
     */
    private String deviceNo;
    /**
     * 价格方案id
     */
    private Integer priceId;

    /**
     * ip
     * @return
     */
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
}
