package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 首页统计近两日数据
 */
public class CompareDataCountDto {
    //充电次数
    private Integer chargeNum;
    //电量
    private BigDecimal usePower;
    //营收
    private BigDecimal  deductMoney;
    //盈利
    private BigDecimal profitable;
    //充电时长
    private Integer useTime;

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    public BigDecimal getUsePower() {
        return usePower;
    }

    public void setUsePower(BigDecimal usePower) {
        this.usePower = usePower;
    }

    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    public BigDecimal getProfitable() {
        return profitable;
    }

    public void setProfitable(BigDecimal profitable) {
        this.profitable = profitable;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }
}
