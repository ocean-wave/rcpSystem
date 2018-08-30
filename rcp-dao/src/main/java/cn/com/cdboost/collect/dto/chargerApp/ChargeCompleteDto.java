package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;

public class ChargeCompleteDto {
    /**
     * 选择充电的套餐类型（1-临时充值  2-包月充值 3-一次充满）
     */
    private Integer payCategory;
    /**
     * （1-临时充值  2-包月充值 3-一次充满）
     */
    private String payCategoryDesc;
    /**
     * 异常值(1-异常,0-正常)
     */
    private Integer isEvent;
    /**
     * 异常描述（1- 电流告警 2-率告警 3-中途更换充电电瓶车 4-设备短路 5-充电结束 0-其他）
     */
    private String eventCodeDesc;
    /**
     * 充值时长
     */
    private String chargeTime;
    /**
     * 充电金额
     */
    private BigDecimal payMoney;
    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;
    /**
     * 扣除次数
     */
    private Integer useCnt;
    /**
     *剩余次数
     */
    private Integer remainCnt;
    /**
     * 开始充电时间
     */
    private String startTime;
    /**
     * endTime结束充电时间
     */
    private String endTime;
    /**
     * 联系电话
     */
    private String contact;

    /**
     * 设备编号（带端口）
     */
    private String deviceNo;

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public String getPayCategoryDesc() {
        return payCategoryDesc;
    }

    public void setPayCategoryDesc(String payCategoryDesc) {
        this.payCategoryDesc = payCategoryDesc;
    }

    public Integer getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(Integer isEvent) {
        this.isEvent = isEvent;
    }

    public String getEventCodeDesc() {
        return eventCodeDesc;
    }

    public void setEventCodeDesc(String eventCodeDesc) {
        this.eventCodeDesc = eventCodeDesc;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getUseCnt() {
        return useCnt;
    }

    public void setUseCnt(Integer useCnt) {
        this.useCnt = useCnt;
    }

    public Integer getRemainCnt() {
        return remainCnt;
    }

    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
