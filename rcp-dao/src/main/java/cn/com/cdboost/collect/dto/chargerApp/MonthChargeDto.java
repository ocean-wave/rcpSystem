package cn.com.cdboost.collect.dto.chargerApp;

public class MonthChargeDto {
    /**
     * 微信下单参数
     */
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packages;
    private String signType;
    private String paySign;
    private Integer isCharge;   //1：可购买，0：不可购买
    private String isChargeDesc;
    private Integer isPay;
    private String isPayDesc;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public Integer getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(Integer isCharge) {
        this.isCharge = isCharge;
    }

    public String getIsChargeDesc() {
        return isChargeDesc;
    }

    public void setIsChargeDesc(String isChargeDesc) {
        this.isChargeDesc = isChargeDesc;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getIsPayDesc() {
        return isPayDesc;
    }

    public void setIsPayDesc(String isPayDesc) {
        this.isPayDesc = isPayDesc;
    }
}
