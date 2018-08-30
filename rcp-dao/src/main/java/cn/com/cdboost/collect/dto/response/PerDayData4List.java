package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计查询，抄表数据，列表数据返回
 */
public class PerDayData4List {
    /**
     * 日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date collectDate;

    /**
     * 总
     */
    private BigDecimal pr0;

    /**
     * 剩余金额
     */
    private BigDecimal balance;

    /**
     * 购电总金额
     */
    private BigDecimal payMoney;

    /**
     * 购电次数
     */
    private int payCount;
    private String deviceNo;
    private String localControl;

    public String getLocalControl() {
        return localControl;
    }

    public void setLocalControl(String localControl) {
        this.localControl = localControl;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
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
