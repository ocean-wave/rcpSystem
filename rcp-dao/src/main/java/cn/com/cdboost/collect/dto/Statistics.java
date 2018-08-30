package cn.com.cdboost.collect.dto;

public class Statistics {

    private float payMoney;

    /**
     * 电费汇总
     */
    private Float electricityFees;
    /**
     * 电量汇总
     */
    private Float electricQuantity;

    public Float getElectricityFees() {
        return electricityFees;
    }

    public void setElectricityFees(Float electricityFees) {
        this.electricityFees = electricityFees;
    }

    public Float getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(Float electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(float payMoney) {
        this.payMoney = payMoney;
    }
}
