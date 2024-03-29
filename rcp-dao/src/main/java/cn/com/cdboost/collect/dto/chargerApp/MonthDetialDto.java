package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;

public class MonthDetialDto {
    /**
     * 价格方案id
     */
    private Integer priceId;
    /**
     * 使用次数
     */
    private Integer chargingCnt;
    /**
     * 月卡时长
     */
    private Integer chargingTime;
    /**
     * 月卡售价
     */
    private BigDecimal money;

    /**
     * 站点名称
     */
    private String projectName;

    /**
     * 功率范围
     */
    private String powerLimit;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getChargingCnt() {
        return chargingCnt;
    }

    public void setChargingCnt(Integer chargingCnt) {
        this.chargingCnt = chargingCnt;
    }

    public Integer getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(String powerLimit) {
        this.powerLimit = powerLimit;
    }
}
