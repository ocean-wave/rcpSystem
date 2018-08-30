package cn.com.cdboost.collect.dto.param;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 阿里云短信，告警短信模板参数
 */
public class AlarmTempParam {
    /**
     * 用户唯一标识，不要json序列化
     */
    @JSONField(serialize=false)
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 采集时间
     */
    private String collectTime;

    /**
     * 告警阈值（单位元）
     */
    private String alarmThreshold;

    /**
     * 剩余金额（单位元）
     */
    private String remainAmount;

    /**
     * 设备名称
     */
    private String deviceName;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(String alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
