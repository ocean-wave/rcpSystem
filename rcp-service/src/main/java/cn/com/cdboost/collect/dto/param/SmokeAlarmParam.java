package cn.com.cdboost.collect.dto.param;

/**
 * @author wt
 * @desc
 * @create in  2018/8/30
 **/
public class SmokeAlarmParam {
    private String  dateTime;
    private String  installAddress;
    private String cno;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}
