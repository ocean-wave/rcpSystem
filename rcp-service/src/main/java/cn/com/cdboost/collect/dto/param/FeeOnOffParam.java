package cn.com.cdboost.collect.dto.param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class FeeOnOffParam {
    private String guid;
    private Integer onOff;
    private String reason;
    private Integer userId;
    private Date date;
    private List<OnOffMeterVo> meters;
    private String sessionId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OnOffMeterVo> getMeters() {
        return meters;
    }

    public void setMeters(List<OnOffMeterVo> meters) {
        this.meters = meters;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
