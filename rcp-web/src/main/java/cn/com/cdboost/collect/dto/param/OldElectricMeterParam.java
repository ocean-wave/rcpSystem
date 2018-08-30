package cn.com.cdboost.collect.dto.param;

import java.math.BigDecimal;

/**
 * 换表操作时，旧电表传入参数
 */
public class OldElectricMeterParam extends ElectricMeterChangeParam{
    /**
     * 旧表最后一次算费总示数
     */
    private BigDecimal lastReadValue;

    /**
     * 旧表最后一次算费尖示数
     */
    private BigDecimal lastReadValue1;

    /**
     * 旧表最后一次算费峰示数
     */
    private BigDecimal lastReadValue2;

    /**
     * 旧表最后一次算费平示数
     */
    private BigDecimal lastReadValue3;

    /**
     * 旧表最后一次算费谷示数
     */
    private BigDecimal lastReadValue4;

    public BigDecimal getLastReadValue() {
        return lastReadValue;
    }

    public void setLastReadValue(BigDecimal lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    public BigDecimal getLastReadValue1() {
        return lastReadValue1;
    }

    public void setLastReadValue1(BigDecimal lastReadValue1) {
        this.lastReadValue1 = lastReadValue1;
    }

    public BigDecimal getLastReadValue2() {
        return lastReadValue2;
    }

    public void setLastReadValue2(BigDecimal lastReadValue2) {
        this.lastReadValue2 = lastReadValue2;
    }

    public BigDecimal getLastReadValue3() {
        return lastReadValue3;
    }

    public void setLastReadValue3(BigDecimal lastReadValue3) {
        this.lastReadValue3 = lastReadValue3;
    }

    public BigDecimal getLastReadValue4() {
        return lastReadValue4;
    }

    public void setLastReadValue4(BigDecimal lastReadValue4) {
        this.lastReadValue4 = lastReadValue4;
    }
}
