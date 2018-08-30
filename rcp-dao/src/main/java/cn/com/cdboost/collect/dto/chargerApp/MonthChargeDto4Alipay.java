package cn.com.cdboost.collect.dto.chargerApp;

public class MonthChargeDto4Alipay {
    /**
     * 1：可购买，0：不可购买
     */
    private Integer isCharge;
    /**
     * 描述信息
     */
    private String isChargeDesc;

    /**
     * 是否需要调起第三方支付 1是，0否
     */
    private Integer isPay;

    /**
     * 描述信息
     */
    private String isPayDesc;

    /**
     * 支付宝订单号，当isPay=1时，前端需要发起支付宝支付
     */
    private String tradeNo;

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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
