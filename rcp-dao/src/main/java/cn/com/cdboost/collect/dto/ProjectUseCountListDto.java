package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 电量电费统计---站点使用情况统计
 */
public class ProjectUseCountListDto {
    private String projectGuid;
    private String projectName;
    private Integer deviceNum = 0;
    private Integer chargingNum = 0;
    //使用电量
    private BigDecimal projectPower = BigDecimal.ZERO;
    //电费
    private BigDecimal projectFee = BigDecimal.ZERO;
    //盈利
    private BigDecimal projectProfitable = BigDecimal.ZERO;
    //组织名称
    private String orgName;
    //项目下设备使用率
    private BigDecimal projectDeviceUseRate = BigDecimal.ZERO;

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getChargingNum() {
        return chargingNum;
    }

    public void setChargingNum(Integer chargingNum) {
        this.chargingNum = chargingNum;
    }

    public BigDecimal getProjectPower() {
        return projectPower;
    }

    public void setProjectPower(BigDecimal projectPower) {
        this.projectPower = projectPower;
    }

    public BigDecimal getProjectFee() {
        return projectFee;
    }

    public void setProjectFee(BigDecimal projectFee) {
        this.projectFee = projectFee;
    }

    public BigDecimal getProjectProfitable() {
        return projectProfitable;
    }

    public void setProjectProfitable(BigDecimal projectProfitable) {
        this.projectProfitable = projectProfitable;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public BigDecimal getProjectDeviceUseRate() {
        return projectDeviceUseRate;
    }

    public void setProjectDeviceUseRate(BigDecimal projectDeviceUseRate) {
        this.projectDeviceUseRate = projectDeviceUseRate;
    }
}
