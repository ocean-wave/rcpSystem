package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 首页统计曲线
 */
public class FirstCurveInfo {
    //时间
    private List<String> xData;
    //设备使用率
    private List<BigDecimal> useRateData;
    //盈利
    private List<BigDecimal> profitableData;

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<BigDecimal> getUseRateData() {
        return useRateData;
    }

    public void setUseRateData(List<BigDecimal> useRateData) {
        this.useRateData = useRateData;
    }

    public List<BigDecimal> getProfitableData() {
        return profitableData;
    }

    public void setProfitableData(List<BigDecimal> profitableData) {
        this.profitableData = profitableData;
    }
}
