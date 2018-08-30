package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.model.FeePay;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zc
 * @desc APP 显示数据
 * @create 2017-12-07 11:31
 **/
public class ShowDataEntity {
    private String 	jzqCNo; // 集中器编号
    private String 	userName; // 用户姓名
    private String customerNo;
    private String customerId;
    private String customerAddr;

    private String underAlarm; //是否低于告警值0:不是 1:是
    private String refreshDate; //刷新时间(yyyy-MM-dd HH:mm:ss)
    private String meterNo;

    private BigDecimal 	remainAmount; // 剩余金额
    private BigDecimal maxData; // 总示数
    private int isOn;   // 通断状态(0 -断开 1-连通)
    private List<BigDecimal> monthSumData; // 半年统计数据
    private List<FeePay> feePays; // 最近5次购电记录

    public String getJzqCNo() {
        return jzqCNo;
    }

    public void setJzqCNo(String jzqCNo) {
        this.jzqCNo = jzqCNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getMaxData() {
        return maxData;
    }

    public void setMaxData(BigDecimal maxData) {
        this.maxData = maxData;
    }

    public List<BigDecimal> getMonthSumData() {
        return monthSumData;
    }

    public void setMonthSumData(List<BigDecimal> monthSumData) {
        this.monthSumData = monthSumData;
    }

    public List<FeePay> getFeePays() {
        return feePays;
    }

    public void setFeePays(List<FeePay> feePays) {
        this.feePays = feePays;
    }

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    public String getUnderAlarm() {
        return underAlarm;
    }

    public void setUnderAlarm(String underAlarm) {
        this.underAlarm = underAlarm;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
