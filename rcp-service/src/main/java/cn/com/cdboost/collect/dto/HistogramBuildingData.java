package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 用能统计页面，柱状图楼栋用能数据
 */
public class HistogramBuildingData {
    /**
     * X轴数据，代表楼栋号
     */
    private String[] xData;

    /**
     * Y轴数据，楼栋的用能量
     */
    private BigDecimal[] yData;

    public String[] getxData() {
        return xData;
    }

    public void setxData(String[] xData) {
        this.xData = xData;
    }

    public BigDecimal[] getyData() {
        return yData;
    }

    public void setyData(BigDecimal[] yData) {
        this.yData = yData;
    }
}
