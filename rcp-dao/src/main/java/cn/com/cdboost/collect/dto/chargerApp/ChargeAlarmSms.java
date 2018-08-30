package cn.com.cdboost.collect.dto.chargerApp;

public class ChargeAlarmSms {
    /**
     * 充电日期
     */
    private String chargeTime;
    /**
     * 安装地址
     */
    private String installAddr;
    /**
     * 时间
     */
    private String time;
    /**
     * 事件内容
     */
    private String eventContent;

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
