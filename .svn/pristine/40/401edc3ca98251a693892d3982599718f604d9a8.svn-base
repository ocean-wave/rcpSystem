package cn.com.cdboost.collect.dto;


import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wt
 * @desc 用户用电排行
 * @create 2017/8/29 0029
 **/
public class ChargeDetailDTO {

    private Long   sequence;

    private Date chargeTime;

    private String customerName;

    private String propertyName;

    private String customerBrand;

    private Double customerElecBill;

    private String meterUserNo;

    private Double chargeAmount;

    private Double preChargeAmount;

    private Double surplusAmount;

    private Double chargeElecAmount;

    private Double preChargeElecAmount;

    private Double surplusChargeElecAmount;
    /**
     * 1-现金 2-微信 3-银联 4-支付宝
     */
    private String payType;
    private String deviceNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getChargeTime() throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String str= simpleDateFormat.format(chargeTime);
       return    str;
    }

    public void setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
    }

    public Double getCustomerElecBill() {
        return customerElecBill;
    }

    public void setCustomerElecBill(Double customerElecBill) {
        this.customerElecBill = customerElecBill;
    }

    public Double getChargeAmount() {
          return scale(chargeAmount);
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Double getPreChargeAmount() {
        return scale(preChargeAmount);
    }

    public void setPreChargeAmount(Double preChargeAmount) {
        this.preChargeAmount = preChargeAmount;
    }

    public Double getSurplusAmount() {
        return scale(surplusAmount);
    }

    public void setSurplusAmount(Double surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public Double getChargeElecAmount() {
        return scale(chargeElecAmount);
    }

    public void setChargeElecAmount(Double chargeElecAmount) {
        this.chargeElecAmount = chargeElecAmount;
    }

    public Double getPreChargeElecAmount() {
        return scale(preChargeElecAmount);
    }

    public void setPreChargeElecAmount(Double preChargeElecAmount) {
        this.preChargeElecAmount = preChargeElecAmount;
    }

    public Double getSurplusChargeElecAmount() {
        return scale(surplusChargeElecAmount);
    }

    public void setSurplusChargeElecAmount(Double surplusChargeElecAmount) {
        this.surplusChargeElecAmount = surplusChargeElecAmount;
    }

    public String getPayType() {
        if(!StringUtils.isEmpty(payType)){
            if("1".equals(payType)){
                payType="现金";
            }
            if("2".equals(payType)){
                payType="微信";
            }
            if("3".equals(payType)){
                payType="银联";
            }
            if("4".equals(payType)){
                payType="支付宝";
            }

        }
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    private double scale(double d){
        return new BigDecimal(d).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
