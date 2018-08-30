package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 集中器基础信息修改，传入参数
 */
public class JzqEditBaseInfoParam {
    /**
     * 集中器编号
     */
    @NotBlank(message = "deviceNo不能为空")
    private String deviceNo;

    /**
     * 组织编号
     */
    @NotNull(message = "orgNo不能为null")
    private Long orgNo;

    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 安装日期
     */
    @NotBlank(message = "installDate不能为空")
    private String installDate;

    /**
     * 安装位置
     */
    private String installAddr;

    /**
     * 备注
     */
    private String remark;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
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
}
