package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

public class MonthlySchemeDto {
    private String schemeGuid;
    private Integer monthlyCnt;
    private BigDecimal monthlyPrice;
    private Integer monthlyTime;
    //0添加，1删除，2修改
    private Integer flag;

    private Integer payCategory;
    //电瓶车功率
    private Integer powerType;

    public String getSchemeGuid() {
        return schemeGuid;
    }

    public void setSchemeGuid(String schemeGuid) {
        this.schemeGuid = schemeGuid;
    }

    public Integer getMonthlyCnt() {
        return monthlyCnt;
    }

    public void setMonthlyCnt(Integer monthlyCnt) {
        this.monthlyCnt = monthlyCnt;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getMonthlyTime() {
        return monthlyTime;
    }

    public void setMonthlyTime(Integer monthlyTime) {
        this.monthlyTime = monthlyTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }
}
