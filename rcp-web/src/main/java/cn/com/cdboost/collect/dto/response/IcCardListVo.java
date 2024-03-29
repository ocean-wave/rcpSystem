package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/8/24 0024.
 */
public class IcCardListVo {
    /**
     * IC卡卡号
     */
    private String cardId;

    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 电话号码
     */
    private String phoneNumber;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
