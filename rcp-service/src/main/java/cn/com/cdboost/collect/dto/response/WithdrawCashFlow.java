package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现流水返回字段
 */
public class WithdrawCashFlow {
    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 提现后剩余金额
     */
    private BigDecimal afterRemainAmount;

    /**
     * 提现状态 1 提现成功， 2提现失败
     */
    private Integer status;

    /**
     * 提现错误描述
     */
    private String errorMsg;

    /**
     * 提现时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 提现后剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 提现类别
     */
    private String withdrawCashType;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(BigDecimal afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getWithdrawCashType() {
        return withdrawCashType;
    }

    public void setWithdrawCashType(String withdrawCashType) {
        this.withdrawCashType = withdrawCashType;
    }
}
