package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * IC卡充值记录信息
 */
public class IcCardPayInfo {
    /**
     * IC卡号
     */
    private String cardId;

    /**
     * 充值金额
     */
    private BigDecimal payMoney;

    /**
     * 支付方式 1微信 2支付宝
     */
    private String payMethod;

    /**
     * 支付完成时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 本次充值后剩余金额
     */
    private BigDecimal afterRemainAmount;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(BigDecimal afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }
}
