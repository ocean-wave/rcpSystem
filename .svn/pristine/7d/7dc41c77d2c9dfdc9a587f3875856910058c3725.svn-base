package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工单上传时，某个电表关联的采集数据信息
 */
public class CollectDataItem4Upload {
    /**
     * 采集DI
     */
    @NotBlank(message = "mrFlag不能为空")
    private String mrFlag;

    /**
     * 采集项标识
     */
    @NotBlank(message = "readType不能为空")
    private String readType;

    /**
     * 采集值
     */
    @DecimalMin(value = "0",message = "readValue大于等于0")
    private BigDecimal readValue;

    /**
     * 采集数据标识
     */
    @NotBlank(message = "groupGuid不能为空")
    private String groupGuid;

    /**
     * 采集时间
     */
    private Date collectTime;

    public String getMrFlag() {
        return mrFlag;
    }

    public void setMrFlag(String mrFlag) {
        this.mrFlag = mrFlag;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public BigDecimal getReadValue() {
        return readValue;
    }

    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}
