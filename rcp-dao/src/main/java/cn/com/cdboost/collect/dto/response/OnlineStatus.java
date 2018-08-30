package cn.com.cdboost.collect.dto.response;

/**
 * 表的在线状态
 */
public class OnlineStatus {
    /**
     * 设备cno
     */
    private String cno;

    /**
     * 在线状态
     */
    private Integer onlineStatus;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
