package cn.com.cdboost.collect.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 补抄工单详情中，关于补抄用户的采集数据项信息
 */
public class CollectDataItem implements Serializable{

    private static final long serialVersionUID = 1934118514861059233L;

    /**
     * 采集项标识
     */
    private String readType;
    /**
     * 采集值
     */
    private String readValue;
    /**
     * 上次采集数据
     */
    private String lastReadValue;
    /**
     * 采集数据标识
     */
    private String groupGuid;
    /**
     * 表的唯一标识
     */
    private String cno;
    /**
     * 采集时间
     */
    private Date collectTime;

    /**
     * 采集项名称
     */
    private String collectName;

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

    /**
     * 1标识当前工单采集的数据（新）,0 标识上一次采集的数据
     */
    private Integer dataFlag;

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getReadValue() {
        return readValue;
    }

    public void setReadValue(String readValue) {
        this.readValue = readValue;
    }

    public String getLastReadValue() {
        return lastReadValue;
    }

    public void setLastReadValue(String lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
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

    public Integer getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(Integer dataFlag) {
        this.dataFlag = dataFlag;
    }
}
