package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.dto.chargerApp.CurveDto;

public class AppChargerResponse {
    private String openId;              //微信用户唯一标识
    private String deviceNo;           //设备编号
    private Integer state;             //设备状态
    private String stateDesc;          //设备状态描述
    private Integer  runState;            //设备运行状态
    private String runStateDesc;         //当前状态 0-正在充电 1-已完成充电',
    private String chargerElectric;  //已充电百分比
    private String Alarm;          //告警信息 1-电流越限  2-电压越限
    CurveDto currentDto;  //电流曲线
    CurveDto voltageDto;  //电压曲线
    CurveDto powerDto;    //功率曲线

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public String getRunStateDesc() {
        return runStateDesc;
    }

    public void setRunStateDesc(String runStateDesc) {
        this.runStateDesc = runStateDesc;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getChargerElectric() {
        return chargerElectric;
    }

    public void setChargerElectric(String chargerElectric) {
        this.chargerElectric = chargerElectric;
    }

    public String getAlarm() {
        return Alarm;
    }

    public void setAlarm(String alarm) {
        Alarm = alarm;
    }

    public CurveDto getCurrentDto() {
        return currentDto;
    }

    public void setCurrentDto(CurveDto currentDto) {
        this.currentDto = currentDto;
    }

    public CurveDto getVoltageDto() {
        return voltageDto;
    }

    public void setVoltageDto(CurveDto voltageDto) {
        this.voltageDto = voltageDto;
    }

    public CurveDto getPowerDto() {
        return powerDto;
    }

    public void setPowerDto(CurveDto powerDto) {
        this.powerDto = powerDto;
    }
}
