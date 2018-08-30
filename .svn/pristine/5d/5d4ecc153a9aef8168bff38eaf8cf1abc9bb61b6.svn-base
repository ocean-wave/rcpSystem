package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 充电桩添加
 */
public class ChargerDeviceAddParam {
    /**
     * 设备编号
     */
    @NotBlank(message = "deviceNo不能为空")
    @Valid
    private String deviceNo;

    /**
     * 充电桩端口号（1-8号端口）
     */
    private String port;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 安装地址
     */
    @NotBlank(message = "安装地址不能为空")
    private String installAddr;

    /**
     * 安装日期
     */
    private String installDate;

    /**
     * 通信方式
     */
    @NotNull(message = "comMethod不能为null")
    private Integer comMethod;

    /**
     * 通信编号
     */
    private String commNo;

    /**
     * 电流越限阀值
     */
    private BigDecimal currentLimit;

    /**
     * 变压器号
     */
    @NotBlank(message = "transformerNo不能为空")
    private String transformerNo;

    /**
     * 总表地址
     */
    private String meterCno;

    /**
     * 总表表号
     */
    @NotBlank(message = "meterNo不能为空")
    private String meterNo;

    /**
     * 所属项目
     */
    //@NotNull(message = "所属项目不能为null")
    private String projectGuid;

    /**
     * 所属项目
     */
    private String remark;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public Integer getComMethod() {
        return comMethod;
    }

    public void setComMethod(Integer comMethod) {
        this.comMethod = comMethod;
    }

    public BigDecimal getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(BigDecimal currentLimit) {
        this.currentLimit = currentLimit;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getMeterCno() {
        return meterCno;
    }

    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
