package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_card_list")
public class ChargingCardList implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "point_code")
    private Integer pointCode;

    /**
     * 设备编号
     */
    @Column(name = "device_no")
    private String deviceNo;

    /**
     * 设备deveui
     */
    @Column(name = "comm_no")
    private String commNo;

    /**
     * ic卡编号
     */
    @Column(name = "card_id")
    private String cardId;
    /**
     * ic卡编号
     */
    @Column(name = "card_guid")
    private String cardGuid;

    /**
     *IC卡状态 -1移除  0-欠费  1-正常
     */
    @Column(name = "state")
    private Integer state;

    /**
     *IC卡状态 -1移除  0-欠费  1-正常
     */
    @Column(name = "send_flag")
    private Integer sendFlag;

    /**
     *写入时间
     */
    @Column(name = "write_time")
    private Date writeTime;

    /**
     * 下发时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 项目标识
     */
    @Column(name = "project_guid")
    private String projectGuid;

    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getCardGuid() {
        return cardGuid;
    }

    public void setCardGuid(String cardGuid) {
        this.cardGuid = cardGuid;
    }
}