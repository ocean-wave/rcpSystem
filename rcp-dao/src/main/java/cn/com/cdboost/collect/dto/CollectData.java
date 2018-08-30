package cn.com.cdboost.collect.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 补抄工单详情中，已采集的数据信息
 */
public class CollectData implements Serializable{
    private static final long serialVersionUID = 6367436378860298630L;

    /**
     * 采集项标识
     */
    private String readType;
    /**
     * 采集值
     */
    private String readValue;
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
     * 1 标识当前工单采集的数据（新）
     * 0 标识上一次采集的数据
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

    public Integer getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(Integer dataFlag) {
        this.dataFlag = dataFlag;
    }
}
