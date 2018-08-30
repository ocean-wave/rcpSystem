package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Ic卡开户
 */
public class IcCardAddQueryParam {
    /**
     * 设备cno
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 用户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 电流互感器变比
     */
    @NotNull(message = "curTranfRto不能为null")
    private Integer curTranfRto;

    /**
     * 电压互感器变比
     */
    @NotNull(message = "volTranfRto不能为null")
    private Integer volTranfRto;

    /**
     * 告警金额1
     */
    @DecimalMin(value = "0",message = "alertFee1必须大于等于0")
    private BigDecimal alertFee1;

    /**
     * 告警金额2
     */
    @DecimalMin(value = "0",message = "alertFee2必须大于等于0")
    private BigDecimal alertFee2;

    /**
     * 电表设备类型 0其他 1 -09电表 2-13表
     */
    @NotBlank(message = "meterType不能为空")
    private String meterType;

    /**
     * 购电次数
     */
    @NotNull(message = "payCount不能为null")
    private Integer payCount;

    /**
     * 购电金额
     */
    @DecimalMin(value = "0",message = "payMoney必须大于等于0")
    @DecimalMax(value = "100000",message = "payMoney必须小于等于100000")
    private BigDecimal payMoney;

    /**
     * 缴费的金额
     */
    @DecimalMin(value = "0",message = "payment必须大于等于0")
    private BigDecimal payment;

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
}
