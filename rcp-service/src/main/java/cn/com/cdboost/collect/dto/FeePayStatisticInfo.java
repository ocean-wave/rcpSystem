package cn.com.cdboost.collect.dto;

/**
 * Created by Administrator on 2017/12/15 0015.
 */
public class FeePayStatisticInfo {
    private String payment;
    private String adjustAmount;
    private String payMoney;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(String adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
