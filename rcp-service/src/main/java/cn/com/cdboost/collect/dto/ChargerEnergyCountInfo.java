package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 累计数值统计
 */
public class ChargerEnergyCountInfo {
    //充电次数
    private Integer chargeNumTotal;
    //使用电量
    private BigDecimal usePowerTotal;
    //累计营收
    private BigDecimal  useTimeTotal;
    //今日累计盈利
    private BigDecimal lossPowerTotal;

    public Integer getChargeNumTotal() {
        return chargeNumTotal;
    }

    public void setChargeNumTotal(Integer chargeNumTotal) {
        this.chargeNumTotal = chargeNumTotal;
    }

    public BigDecimal getUsePowerTotal() {
        return usePowerTotal;
    }

    public void setUsePowerTotal(BigDecimal usePowerTotal) {
        this.usePowerTotal = usePowerTotal;
    }

    public BigDecimal getUseTimeTotal() {
        return useTimeTotal;
    }

    public void setUseTimeTotal(BigDecimal useTimeTotal) {
        this.useTimeTotal = useTimeTotal;
    }

    public BigDecimal getLossPowerTotal() {
        return lossPowerTotal;
    }

    public void setLossPowerTotal(BigDecimal lossPowerTotal) {
        this.lossPowerTotal = lossPowerTotal;
    }
}
