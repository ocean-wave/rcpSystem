package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 实时数据，历史数据详情，柱状图返回信息
 */
public class CollectAnalyzeDataForMonthInfo {
    /**
     * 水表统计信息
     */
    private BigDecimal[] water;

    /**
     * 电表统计信息
     */
    private BigDecimal[] elec;

    /**
     * 气表统计信息
     */
    private BigDecimal[] gas;

    /**
     * 当前年
     */
    private Integer year;

    /**
     * 一月到当前月
     */
    private String[] month;

    public BigDecimal[] getWater() {
        return water;
    }

    public void setWater(BigDecimal[] water) {
        this.water = water;
    }

    public BigDecimal[] getElec() {
        return elec;
    }

    public void setElec(BigDecimal[] elec) {
        this.elec = elec;
    }

    public BigDecimal[] getGas() {
        return gas;
    }

    public void setGas(BigDecimal[] gas) {
        this.gas = gas;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String[] getMonth() {
        return month;
    }

    public void setMonth(String[] month) {
        this.month = month;
    }
}
