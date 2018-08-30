package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;

public class ChargeHistoryDto {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 安装地址
     */
    private String installAddr;
    /**
     * 充电时长
     */
    private String  endTime;
    /**
     * 计费方式
     */
    private Integer chargingWay;
    /**
     * 扣费方式
     */
    private Integer payCategory;
    /**
     * 计费方式描述
     */
    private String chargingWayDesc;
    /**
     * 开始充电时间
     */
    private String startTime;

    /**
     * 充电记录唯一标识
     */
    private String chargeGuid;

    private String sDate;

    private String sTime;

    private  String eTime;

    private Integer payWay;

    private String payWayDesc;

    private Integer remainCnt;

    private Integer state=1;

    private BigDecimal pay;

    private Integer isEvent;

    private String isEventDesc;

    private String  chargeTime;

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRemainCnt() {
        return remainCnt;
    }

    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public Integer getPayWay() {
        payWay = payCategory;
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getPayWayDesc() {
        return payWayDesc;
    }

    public void setPayWayDesc(String payWayDesc) {
        this.payWayDesc = payWayDesc;
    }



    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public Integer getChargingWay() {
        return chargingWay;
    }

    public void setChargingWay(Integer chargingWay) {
        this.chargingWay = chargingWay;
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

    public String getChargingWayDesc() {
        if(chargingWay!=null){
            if(chargingWay==1){
                chargingWayDesc = "按时充电";
            }else if(chargingWay==2){
                chargingWayDesc = "按电量充电";
            }else{
                chargingWayDesc = "充满断电";
            }
        }
        return chargingWayDesc;
    }

    public void setChargingWayDesc(String chargingWayDesc) {
        this.chargingWayDesc = chargingWayDesc;
    }

    public String getChargeGuid() {
        return chargeGuid;
    }

    public void setChargeGuid(String chargeGuid) {
        this.chargeGuid = chargeGuid;
    }

    public Integer getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(Integer isEvent) {
        this.isEvent = isEvent;
    }

    public String getIsEventDesc() {
        return isEventDesc;
    }

    public void setIsEventDesc(String isEventDesc) {
        this.isEventDesc = isEventDesc;
    }
}
