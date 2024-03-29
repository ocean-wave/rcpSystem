package cn.com.cdboost.collect.dto;

/**
 * ic卡下发列表
 */
public class CardListInfo {
    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备deveui
     */
    private String commNo;

    /**
     * ic卡编号
     */
    private String cardId;

    /**
     *IC卡状态 -1移除  0-欠费  1-正常
     */
    private Integer state;

    private Integer pointCode;

    /**
     *IC卡状态 -1移除  0-欠费  1-正常
     */
    private Integer sendFlag;

    /**
     * 下发时间
     */
    private String updateTime;

    private String cardGuid;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    public String getCardGuid() {
        return cardGuid;
    }

    public void setCardGuid(String cardGuid) {
        this.cardGuid = cardGuid;
    }
}
