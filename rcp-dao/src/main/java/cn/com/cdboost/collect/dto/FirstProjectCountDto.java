package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * 首页统计站点列表
 */
public class FirstProjectCountDto {
    private String projectName;
    //利用率
    private BigDecimal useRate = BigDecimal.ZERO;
    //设备数
    private Integer deviceNum = 0;
    //线损率
    private BigDecimal lossRate = BigDecimal.ZERO;
    //盈利
    private BigDecimal projectProfitable = BigDecimal.ZERO;
    //充电次数
    private Integer chargingNum = 0;

    @JSONField(format = "yyyy-mm-dd")
    private String createTime;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getUseRate() {
        return useRate;
    }

    public void setUseRate(BigDecimal useRate) {
        this.useRate = useRate;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public BigDecimal getLossRate() {
        return lossRate;
    }

    public void setLossRate(BigDecimal lossRate) {
        this.lossRate = lossRate;
    }

    public BigDecimal getProjectProfitable() {
        return projectProfitable;
    }

    public void setProjectProfitable(BigDecimal projectProfitable) {
        this.projectProfitable = projectProfitable;
    }

    public Integer getChargingNum() {
        return chargingNum;
    }

    public void setChargingNum(Integer chargingNum) {
        this.chargingNum = chargingNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}