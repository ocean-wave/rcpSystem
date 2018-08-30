package cn.com.cdboost.collect.dto.chargerApp;

public class LastUseRecordDto {
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备名称
     */
    private String installAddr;
    /**
     * 充电状态
     */
    private Integer state;
    /**
     * 充电状态描述
     */
    private String stateDesc;
    /**
     * 异常状态
     */
    private Integer eventCode;
    /**
     * 异常状态描述
     */
    private String eventCodeDesc;
    /**
     * 开始充电时间
     */
    private String startTime;

    /**
     * 充电记录唯一标识
     */
    private String chargeGuid;

    /**
     * 是否异常
     * @return
     */
    private Integer isEvent;

    public Integer getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(Integer isEvent) {
        this.isEvent = isEvent;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventCodeDesc() {
        return eventCodeDesc;
    }

    public void setEventCodeDesc(String eventCodeDesc) {
        this.eventCodeDesc = eventCodeDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getChargeGuid() {
        return chargeGuid;
    }

    public void setChargeGuid(String chargeGuid) {
        this.chargeGuid = chargeGuid;
    }
}
