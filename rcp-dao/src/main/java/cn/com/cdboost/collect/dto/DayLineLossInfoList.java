package cn.com.cdboost.collect.dto;

/**
 * @author wt
 * @desc
 * @create in  2018/8/11
 **/
public class DayLineLossInfoList {
    private String  deviceNo;
    private String  port;
    private String  deviceElect;
    private String  userTime;
    private String  startDate;
    private String  endDate="";
    private String  mostPower;
    private String  mostCurrent;
    private String  state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if("1".equals(state)){
            state="充电完成";
        }else{
            state="充电中";
        }
        this.state = state;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDeviceElect() {
        return deviceElect;
    }

    public void setDeviceElect(String deviceElect) {
        this.deviceElect = deviceElect;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMostPower() {
        return mostPower;
    }

    public void setMostPower(String mostPower) {
        this.mostPower = mostPower;
    }

    public String getMostCurrent() {
        return mostCurrent;
    }

    public void setMostCurrent(String mostCurrent) {
        this.mostCurrent = mostCurrent;
    }
}
