package cn.com.cdboost.collect.dto.param;

/**
 * @author wt
 * @desc
 * @create in  2018/8/23
 **/
public class SmokeDeviceSelectTotalInfo {
    private String total;
    private String alarm;
    private String offline;
    private String undervoltage;
    private String online;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getUndervoltage() {
        return undervoltage;
    }

    public void setUndervoltage(String undervoltage) {
        this.undervoltage = undervoltage;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
