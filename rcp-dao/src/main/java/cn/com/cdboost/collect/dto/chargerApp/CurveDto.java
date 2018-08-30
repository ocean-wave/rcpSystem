package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;

public class CurveDto {
    /**
     * 曲线时间
     */
    private String time;
    /**
     * 曲线值
     */
    private BigDecimal value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
