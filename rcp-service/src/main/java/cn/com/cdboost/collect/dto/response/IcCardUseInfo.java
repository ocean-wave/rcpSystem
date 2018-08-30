package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * IC卡使用记录返回
 */
public class IcCardUseInfo {
    private String cardId;
    private Date beginTime;
    private Date endTime;
    private BigDecimal deductMoney;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }
}