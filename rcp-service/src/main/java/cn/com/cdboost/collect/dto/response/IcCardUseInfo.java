package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * IC卡使用记录返回
 */
public class IcCardUseInfo {
    /**
     * IC卡卡号
     */
    private String cardId;

    /**
     * 充电开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 充电结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 充电扣除费用
     */
    private BigDecimal deductMoney;

    /**
     * 本次充电后剩余金额
     */
    private BigDecimal afterRemainAmount;

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

    public BigDecimal getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(BigDecimal afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }
}
