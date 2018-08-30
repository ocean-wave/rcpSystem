package cn.com.cdboost.collect.dto.param;

import java.math.BigDecimal;

/**
 * 开户参数信息
 */
public class IcCardAddParamVo {
    /**
     * 设备cno
     */
    private String cno;

    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 电流互感器变比
     */
    private Integer curTranfRto;

    /**
     * 电压互感器变比
     */
    private Integer volTranfRto;

    /**
     * 告警金额1
     */
    private BigDecimal alertFee1;

    /**
     * 告警金额2
     */
    private BigDecimal alertFee2;

    /**
     * 设备meterType
     */
    private String meterType;

    /**
     * 购电次数
     */
    private Integer payCount;

    /**
     * 购电金额
     */
    private BigDecimal payMoney;

    /**
     * 缴费金额
     */
    private BigDecimal payment;
    /**
     * 前端生成的guid
     */
    private String payGuid;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getCurTranfRto() {
        return curTranfRto;
    }

    public void setCurTranfRto(Integer curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    public Integer getVolTranfRto() {
        return volTranfRto;
    }

    public void setVolTranfRto(Integer volTranfRto) {
        this.volTranfRto = volTranfRto;
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

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }
}
