package cn.com.cdboost.collect.dto;


import java.math.BigDecimal;

/**
 * @author wt
 * @desc 用户用电排行
 * @create 2017/8/29 0029
 **/
public class ChargeSummaryDTO {

    private Long sequence;
    private String customerName;
    private String propertyName;
    private String deviceNo;
    private Double unitPrice;
    private Double remainAmount;
    private Double sumRemainAmount;
    private Double payment;
    private String customerBrand;
    /**
     * 当期电量
     */
    private Double eqValue;
    private Double remainPower;
    private Double startReadValue;
    private Double endReadValue;
    private Double expenseMoney;
    private String meterUserNo;

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Double getUnitPrice() {
        return  unitPrice;
    }
    private double scale(Double d){
        if(d==null){
            d= Double.valueOf(0);
        }
        return new BigDecimal(d).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }



    public Double getRemainAmount() {
        return  scale(remainAmount);
    }

    public void setRemainAmount(Double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Double getSumRemainAmount() {
        return  scale(sumRemainAmount);
    }

    public void setSumRemainAmount(Double sumRemainAmount) {
        this.sumRemainAmount = sumRemainAmount;
    }

    public Double getPayment() {
        return  scale(payment);

    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getEqValue() {
        return  scale(eqValue);
    }

    public void setEqValue(Double eqValue) {
        this.eqValue = eqValue;
    }

    public Double getRemainPower() {
        return  scale(remainPower);
    }

    public void setRemainPower(Double remainPower) {
        this.remainPower = remainPower;
    }

    public Double getStartReadValue() {
        if(startReadValue==null||startReadValue.intValue()==-1){
            return Double.valueOf(0);
        }
        return  scale(startReadValue);
    }

    public void setStartReadValue(Double startReadValue) {
        this.startReadValue = startReadValue;
    }

    public Double getEndReadValue() {
        return  scale(endReadValue);
    }

    public void setEndReadValue(Double endReadValue) {
        this.endReadValue = endReadValue;
    }

    public Double getExpenseMoney() {
        return  scale(expenseMoney);
    }

    public void setExpenseMoney(Double expenseMoney) {
        this.expenseMoney = expenseMoney;
    }
}
