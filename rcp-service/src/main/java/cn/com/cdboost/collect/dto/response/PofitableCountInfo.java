package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充电方案盈利分析
 */
public class PofitableCountInfo {
    //时间
    private List<String> xData;
    //盈利
    private List<BigDecimal> yPofitableData;

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<BigDecimal> getyPofitableData() {
        return yPofitableData;
    }

    public void setyPofitableData(List<BigDecimal> yPofitableData) {
        this.yPofitableData = yPofitableData;
    }
}
