package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 水电气表添加上，公共的属性
 */
public class BaseDeviceAddParam {
    /**
     * 表号
     */
    @NotBlank(message = "deviceNo不能为空")
    private String deviceNo;



    /**
     * 采集方式
     */
    @NotNull(message = "commPort不能为null")
    private Integer commPort;

    /**
     * 集中器号
     */
    @Length(min = 9,max = 9,message = "jzqNo长度必须是9位")
    private String jzqNo;

    /**
     * 电表：采集器号
     * 水表：采集方式如果选择"转换器LoraWan"，则为转换器号；采集方式非"转换器LoraWan",则为采集器号
     * 气表：采集方式如果选择"转换器LoraWan"，则为转换器号；采集方式非"转换器LoraWan",则为采集器号
     */
    private String cjqNo;

    /**
     * 安装位置
     */
    @NotBlank(message = "installAddr不能为空")
    private String installAddr;

    /**
     * 备注
     */
    private String remark;

    /**
     * 采集设备类型  电表：1标识节点 3标识采集器
     * 采集设备类型  水表：1标识节点 2标识转换器
     * 采集设备类型  水表：1标识节点 2标识转换器
     */
    @NotNull(message = "collectDevType不能为null")
    private Integer collectDevType;

    /**
     * 表计户号
     */
    @NotBlank(message = "meterUserNo不能为空")
    private String meterUserNo;

    /**
     * 变压器号
     */
    private String transformerNo;

    /**
     * 楼栋编号
     */
    private String buildingNo;

    /**
     * 表计计费类型
     */
    @NotBlank(message = "dictItemValue不能为空")
    private String dictItemValue;

    /**
     * 是否重点表 0-非重点 1-重点
     */
    @NotNull(message = "isImportant不能为null")
    private Integer isImportant;

    /**
     * 总表表号
     */
    private String parentDeviceNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 参数标识
     */
    @NotNull(message = "paramFlag不能为null")
    private String paramFlag;

    /**
     * 0 立即拉闸 1-延时拉闸 2-不拉闸
     */
    private Integer offScheme;

    /**
     * 延时拉闸时间,单位小时
     */
    private Integer offParam;

    /**
     * 正向总示数
     */
    @NotNull(message = "readValue不能为null")
    private BigDecimal readValue;

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }



    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    public String getCjqNo() {
        return cjqNo;
    }

    public void setCjqNo(String cjqNo) {
        this.cjqNo = cjqNo;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCommPort() {
        return commPort;
    }

    public void setCommPort(Integer commPort) {
        this.commPort = commPort;
    }

    public Integer getCollectDevType() {
        return collectDevType;
    }

    public void setCollectDevType(Integer collectDevType) {
        this.collectDevType = collectDevType;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getDictItemValue() {
        return dictItemValue;
    }

    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public Integer getOffScheme() {
        return offScheme;
    }

    public void setOffScheme(Integer offScheme) {
        this.offScheme = offScheme;
    }

    public Integer getOffParam() {
        return offParam;
    }

    public void setOffParam(Integer offParam) {
        this.offParam = offParam;
    }

    public BigDecimal getReadValue() {
        return readValue;
    }

    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    public String getParentDeviceNo() {
        return parentDeviceNo;
    }

    public void setParentDeviceNo(String parentDeviceNo) {
        this.parentDeviceNo = parentDeviceNo;
    }
}
