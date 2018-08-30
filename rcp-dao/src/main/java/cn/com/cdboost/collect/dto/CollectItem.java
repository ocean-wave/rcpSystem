package cn.com.cdboost.collect.dto;

import java.io.Serializable;

/**
 * 补抄工单详情中，采集项信息
 */
public class CollectItem implements Serializable{
    private static final long serialVersionUID = -6950562558007770235L;
    /**
     * 采集项名称
     */
    private String collectName;
    /**
     * 采集项标识
     */
    private String readType;
    /**
     * 采集DI
     */
    private String mrFlag;
    /**
     * 格式化模式
     */
    private String dataModel;
    /**
     * 表规约  1 标识97 30 标识07
     */
    private String meterType;
    /**
     * 表类型 07电表 08水表 09 气表
     */
    private String deviceType;

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getMrFlag() {
        return mrFlag;
    }

    public void setMrFlag(String mrFlag) {
        this.mrFlag = mrFlag;
    }

    public String getDataModel() {
        return dataModel;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
