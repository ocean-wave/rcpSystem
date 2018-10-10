package cn.com.cdboost.collect.dto.param;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 充电正常断电，发送短信需要参数
 */
public class UserNormalAlarmParam {
    /**
     * 用户唯一标识
     */
    @JSONField(serialize=false)
    private String customerNo;

    /**
     * 手机号
     */
    @JSONField(serialize=false)
    private String mobilePhone;

    /**
     * 充电guid
     */
    @JSONField(serialize=false)
    private String chargingGuid;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 消费金额
     */
    private String amount;

    /**
     * 账户余额
     */
    private String remainAmount;

    /**
     * 充电位置
     */
    private String address;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
