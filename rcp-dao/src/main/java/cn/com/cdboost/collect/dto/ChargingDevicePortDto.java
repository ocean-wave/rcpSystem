package cn.com.cdboost.collect.dto;

public class ChargingDevicePortDto {
    private String chargingPlieGuid;
    private String deviceNo;
    private Integer runState;
    private String commNo;
    private String port;

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
