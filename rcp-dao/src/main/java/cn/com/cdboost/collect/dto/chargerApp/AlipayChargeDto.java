package cn.com.cdboost.collect.dto.chargerApp;

/**
 * 支付宝充电时返回信息
 */
public class AlipayChargeDto {
    /**
     * 是否可充电 0标识余额不足，需要发起支付宝支付 1表示余额充足，后端直接扣减并发送充电指令
     */
    private Integer isCharge;

    /**
     * 当isCharge=0时，支付宝订单号
     */
    private String tradeNo;

    public Integer getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(Integer isCharge) {
        this.isCharge = isCharge;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
