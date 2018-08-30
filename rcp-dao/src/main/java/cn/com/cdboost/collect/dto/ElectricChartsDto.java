package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 能效统计
 */
public class ElectricChartsDto {
    /**
     * 同期电量
     */
    private List<BigDecimal> yLastData;
    /**
     * 当前电量
     */
    private List<BigDecimal> yThisData;
    /**
     * 日期
     */
    private List<String> xData;

    public List<BigDecimal> getyLastData() {
        return yLastData;
    }

    public void setyLastData(List<BigDecimal> yLastData) {
        this.yLastData = yLastData;
    }

    public List<BigDecimal> getyThisData() {
        return yThisData;
    }

    public void setyThisData(List<BigDecimal> yThisData) {
        this.yThisData = yThisData;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }
}
