package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * 首页统计商户信息
 */
public class MerchantCountDto {
    //站点数
    private Integer projectNum;
    //总用户数
    private Integer customerNum;
    //设备总数
    private Integer deviceNum;
    //账户余额
    private BigDecimal remainAmount;
    //账户余额
    private BigDecimal profitable;

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public Integer getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Integer customerNum) {
        this.customerNum = customerNum;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getProfitable() {
        return profitable;
    }

    public void setProfitable(BigDecimal profitable) {
        this.profitable = profitable;
    }
}
