package cn.com.cdboost.collect.dto;

import java.util.List;

public class CurveQueryInfo {
    private List<String> xData;
    private List<Float> yPowerData;
    private List<Float>yCurrentData;
    private List<Float>yVoltageData;

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<Float> getyPowerData() {
        return yPowerData;
    }

    public void setyPowerData(List<Float> yPowerData) {
        this.yPowerData = yPowerData;
    }

    public List<Float> getyCurrentData() {
        return yCurrentData;
    }

    public void setyCurrentData(List<Float> yCurrentData) {
        this.yCurrentData = yCurrentData;
    }

    public List<Float> getyVoltageData() {
        return yVoltageData;
    }

    public void setyVoltageData(List<Float> yVoltageData) {
        this.yVoltageData = yVoltageData;
    }
}
