package cn.com.cdboost.collect.dto;

/**
 * 用能统计页面，柱状图数据
 */
public class HistogramDataInfo {
    /**
     * 楼栋用能数据
     */
    private HistogramBuildingData buildingData;

    /**
     * 变压器用能数据
     */
    private HistogramTransformData transformData;

    public HistogramBuildingData getBuildingData() {
        return buildingData;
    }

    public void setBuildingData(HistogramBuildingData buildingData) {
        this.buildingData = buildingData;
    }

    public HistogramTransformData getTransformData() {
        return transformData;
    }

    public void setTransformData(HistogramTransformData transformData) {
        this.transformData = transformData;
    }
}
