package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * @author wt
 * @desc 用户用电排行
 * @create 2017/8/29 0029
 **/
public class CustomElectReturnDTO {

    private String[] xData;
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
