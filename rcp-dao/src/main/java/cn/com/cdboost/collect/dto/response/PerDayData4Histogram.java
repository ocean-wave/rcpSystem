package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计查询，抄表数据，柱状图数据返回
 */
public class PerDayData4Histogram {
    /**
     * 本次数据时间
     */
    private Date calcData;

    /**
     * 结算正向总电量
     */
    private BigDecimal eqValue;

    public Date getCalcData() {
        return calcData;
    }

    public void setCalcData(Date calcData) {
        this.calcData = calcData;
    }

    public BigDecimal getEqValue() {
        return eqValue;
    }

    public void setEqValue(BigDecimal eqValue) {
        this.eqValue = eqValue;
    }
}
