package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 每日采集数据vo
 */
public class CollectDayDataInfo {
    /**
     * 抄表日期
     */
    private String date;

    /**
     * 总（度）
     */
    private BigDecimal pr0;

    /**
     * 剩余金额（元）
     */
    private BigDecimal balance;

    /**
     * 购电总金额（元）
     */
    private BigDecimal payMoney;

    /**
     * 购电次数
     */
    private int payCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPr0() {
        return pr0;
    }

    public void setPr0(BigDecimal pr0) {
        this.pr0 = pr0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }
}
