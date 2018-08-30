package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xzy
 * @desc ${DESCRIPTION}
 * @create 2017/7/28 0028
 **/
public class QueryUserDTO extends BaseQueryPayDTO{

    private String payGuid;
    private BigDecimal totalAmount;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date amountCollectTime;
    private BigDecimal meterTotalAmount;
    private Integer meterPayCount;
    private Integer isEnabled;
    private String chargeUserName; // 收费人员
    private String propertyName; // 门牌号
    private String payMethod;
    private String serialNum;
    private Integer localControl;
    private Integer isOn;
    private String jzqCno;

    public Integer getIsOn() {
        return isOn;
    }

    public void setIsOn(Integer isOn) {
        this.isOn = isOn;
    }

    public String getJzqCno() {
        return jzqCno;
    }

    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    public Integer getLocalControl() {
        return localControl;
    }

    public void setLocalControl(Integer localControl) {
        this.localControl = localControl;
    }

    public String getPayMethod() {
        /** 1-现金 2-微信 3-银联 4-支付宝
         *
         */
        if("1".equals(payMethod)){
            payMethod="现金";
        }
        if("2".equals(payMethod)){
            payMethod="微信";
        }
        if("3".equals(payMethod)){
            payMethod="银联";
        }
        if("4".equals(payMethod)){
            payMethod="支付宝";
        }
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getAmountCollectTime() {
        return amountCollectTime;
    }

    public void setAmountCollectTime(Date amountCollectTime) {
        this.amountCollectTime = amountCollectTime;
    }

    public BigDecimal getMeterTotalAmount() {
        return meterTotalAmount;
    }

    public void setMeterTotalAmount(BigDecimal meterTotalAmount) {
        this.meterTotalAmount = meterTotalAmount;
    }

    public Integer getMeterPayCount() {
        return meterPayCount;
    }

    public void setMeterPayCount(Integer meterPayCount) {
        this.meterPayCount = meterPayCount;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getChargeUserName() {
        return chargeUserName;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
