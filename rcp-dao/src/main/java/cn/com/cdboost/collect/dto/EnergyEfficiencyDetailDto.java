package cn.com.cdboost.collect.dto;


import java.math.BigDecimal;

public class EnergyEfficiencyDetailDto {
    /**
     *用户名称
     */
    private String customerName;
    /**
     *用户编号
     */
    private String customerNo;
    /**
     *表号
     */
    private String meterNo;
    /**
     *用电量
     */
    private BigDecimal power;
    /**
     *用电地址
     */
    private String powerAddr;
    /**
     * 表计户号
     */
    private String meterUserNo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public String getPowerAddr() {
        return powerAddr;
    }

    public void setPowerAddr(String powerAddr) {
        this.powerAddr = powerAddr;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }
}
