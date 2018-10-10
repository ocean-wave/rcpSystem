package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 电量电费统计---站点使用情况统计
 */
public class DeviceUseCountListDto {
    private String chargingPlieGuid;
    private String deviceNo;
    private String port;
    //安装地址
    private String installAddr;
    //安装时间
    @JSONField(format = "yyyy-MM-dd")
    private Date installDate;
    private String projectName;
    private Integer runState;
    private Integer chargingNum = 0;
    //使用电量
    private BigDecimal projectPower = BigDecimal.ZERO;
    //电费
    private BigDecimal projectFee = BigDecimal.ZERO;
    //盈利
    private BigDecimal projectProfitable = BigDecimal.ZERO;
    //设备使用率
    private BigDecimal projectDeviceUseRate = BigDecimal.ZERO;


    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
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

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getProjectDeviceUseRate() {
        return projectDeviceUseRate;
    }

    public void setProjectDeviceUseRate(BigDecimal projectDeviceUseRate) {
        this.projectDeviceUseRate = projectDeviceUseRate;
    }
}
