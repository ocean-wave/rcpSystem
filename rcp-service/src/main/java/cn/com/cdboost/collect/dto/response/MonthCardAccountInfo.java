package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2018/9/6 0006.
 */
public class MonthCardAccountInfo {

    /**
     * 电瓶车功率标识 0小,1中,2大
     */
    private Integer power;

    /**
     * 功率范围
     */
    private String powerLimit;

    /**
     * 月卡有效期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date expireTime;

    /**
     * 站点名称
     */
    private String projectName;

    /**
     * 剩余次数
     */
    private Integer remainCnt;

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(String powerLimit) {
        this.powerLimit = powerLimit;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getRemainCnt() {
        return remainCnt;
    }

    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }
}
