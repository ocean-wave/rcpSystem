package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_devlog")
public class ChargingDevlog implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "charging_plie_guid")
    private String chargingPlieGuid;

    /**
     * 事件code码（0正常，1告警，-1充电结束）
     */
    @Column(name = "event_code")
    private Integer eventCode;

    /**
     * 事件内容
     */
    @Column(name = "event_content")
    private String eventContent;

    private BigDecimal current;

    private BigDecimal voltage;

    private BigDecimal power;

    @Column(name = "active_total")
    private BigDecimal activeTotal;

    /**
     * 剩余时长
     */
    @Column(name = "remain_time")
    private BigDecimal remainTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 充电情况
     */
    @Column(name = "charging_percent")
    private BigDecimal chargingPercent;

    /**
     *充电记录唯一标识
     */
    @Column(name = "charging_guid")
    private String chargingGuid;
    /**
     * 短信发送状态（0未发送，1已发送）
     */
    @Column(name = "sms_status")
    private Integer smsStatus;
    /**
     * 充电桩端口（0-9、a-f 一共16个端口号）
     */
    @Column(name = "port")
    private String port;

    public BigDecimal getChargingPercent() {
        return chargingPercent;
    }

    public void setChargingPercent(BigDecimal chargingPercent) {
        this.chargingPercent = chargingPercent;
    }

    /**
     * 获取标识id
     *
     * @return id - 标识id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置标识id
     *
     * @param id 标识id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return charging_plie_guid
     */
    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    /**
     * @param chargingPlieGuid
     */
    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    /**
     * @return current
     */
    public BigDecimal getCurrent() {
        return current;
    }

    /**
     * @param current
     */
    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    /**
     * @return voltage
     */
    public BigDecimal getVoltage() {
        return voltage;
    }

    /**
     * @param voltage
     */
    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    /**
     * @return power
     */
    public BigDecimal getPower() {
        return power;
    }

    /**
     * @param power
     */
    public void setPower(BigDecimal power) {
        this.power = power;
    }

    /**
     * @return active_total
     */
    public BigDecimal getActiveTotal() {
        return activeTotal;
    }

    /**
     * @param activeTotal
     */
    public void setActiveTotal(BigDecimal activeTotal) {
        this.activeTotal = activeTotal;
    }

    /**
     * 获取剩余时长
     *
     * @return remain_time - 剩余时长
     */
    public BigDecimal getRemainTime() {
        return remainTime;
    }

    /**
     * 设置剩余时长
     *
     * @param remainTime 剩余时长
     */
    public void setRemainTime(BigDecimal remainTime) {
        this.remainTime = remainTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
    }
}