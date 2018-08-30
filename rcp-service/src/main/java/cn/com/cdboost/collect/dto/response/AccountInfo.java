package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 客户档案明细展示页面，关于开户信息
 */
public class AccountInfo {
    /**
     * 用电类型
     */
    private String dictItemName;

    /**
     * 预留金额
     */
    private BigDecimal initAmount;

    /**
     * 缴费金额
     */
    private BigDecimal payment;

    /**
     * 充值金额
     */
    private BigDecimal payMoney;

    /**
     * 告警金额1
     */
    private BigDecimal alertFee1;

    /**
     * 告警金额2
     */
    private BigDecimal alertFee2;

    /**
     * 购电次数
     */
    private Integer payCount;

    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 透支金额
     */
    private BigDecimal overdraftFee;

    /**
     * 电流互感器变比
     */
    private String curTranfRto;

    /**
     * 囤积金额限
     */
    private BigDecimal cornerFee;

    /**
     * 电压互感器变比
     */
    private String volTranfRto;

    /**
     * 电价方案代码,用于查询电价方案
     */
    private String priceSolsCode;

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getAlertFee1() {
        return alertFee1;
    }

    public void setAlertFee1(BigDecimal alertFee1) {
        this.alertFee1 = alertFee1;
    }

    public BigDecimal getAlertFee2() {
        return alertFee2;
    }

    public void setAlertFee2(BigDecimal alertFee2) {
        this.alertFee2 = alertFee2;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public String getCurTranfRto() {
        return curTranfRto;
    }

    public void setCurTranfRto(String curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    public BigDecimal getCornerFee() {
        return cornerFee;
    }

    public void setCornerFee(BigDecimal cornerFee) {
        this.cornerFee = cornerFee;
    }

    public String getVolTranfRto() {
        return volTranfRto;
    }

    public void setVolTranfRto(String volTranfRto) {
        this.volTranfRto = volTranfRto;
    }

    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }
}
