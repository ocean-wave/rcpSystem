package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_freeze_log")
public class FreezeLog implements Serializable {
    private static final long serialVersionUID = -2285577624112225666L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 集中器编号(LoRa默认999999999)
     */
    @Column(name = "jzq_no")
    private String jzqNo;

    /**
     * 日冻结日期
     */
    @Column(name = "freeze_date")
    private Date freezeDate;

    /**
     * 电表总数
     */
    @Column(name = "meter_total")
    private Integer meterTotal;

    /**
     * 当日补抄轮次
     */
    private Integer round;

    /**
     * 每轮补抄数
     */
    @Column(name = "meter_cnt")
    private Integer meterCnt;

    /**
     * 每轮成功采集数
     */
    @Column(name = "meter_succ_cnt")
    private Integer meterSuccCnt;

    @Column(name = "item_cnt")
    private Integer itemCnt;

    /**
     * 数据项总数量
     */
    @Column(name = "item_succ_cnt")
    private Integer itemSuccCnt;

    @Column(name = "ready_send")
    private Integer readySend;

    @Column(name = "send_to_gw")
    private Integer sendToGw;

    @Column(name = "payload_error")
    private Integer payloadError;

    @Column(name = "device_error")
    private Integer deviceError;

    @Column(name = "send_buff_full")
    private Integer sendBuffFull;

    @Column(name = "unkonwn_device")
    private Integer unkonwnDevice;

    @Column(name = "send_fail")
    private Integer sendFail;

    private String remark;

    /**
     * 每轮开始时间
     */
    @Column(name = "st_time")
    private Date stTime;

    /**
     * 每轮结束时间
     */
    @Column(name = "ed_time")
    private Date edTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取集中器编号(LoRa默认999999999)
     *
     * @return jzq_no - 集中器编号(LoRa默认999999999)
     */
    public String getJzqNo() {
        return jzqNo;
    }

    /**
     * 设置集中器编号(LoRa默认999999999)
     *
     * @param jzqNo 集中器编号(LoRa默认999999999)
     */
    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    /**
     * 获取日冻结日期
     *
     * @return freeze_date - 日冻结日期
     */
    public Date getFreezeDate() {
        return freezeDate;
    }

    /**
     * 设置日冻结日期
     *
     * @param freezeDate 日冻结日期
     */
    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    /**
     * 获取电表总数
     *
     * @return meter_total - 电表总数
     */
    public Integer getMeterTotal() {
        return meterTotal;
    }

    /**
     * 设置电表总数
     *
     * @param meterTotal 电表总数
     */
    public void setMeterTotal(Integer meterTotal) {
        this.meterTotal = meterTotal;
    }

    /**
     * 获取当日补抄轮次
     *
     * @return round - 当日补抄轮次
     */
    public Integer getRound() {
        return round;
    }

    /**
     * 设置当日补抄轮次
     *
     * @param round 当日补抄轮次
     */
    public void setRound(Integer round) {
        this.round = round;
    }

    /**
     * 获取每轮补抄数
     *
     * @return meter_cnt - 每轮补抄数
     */
    public Integer getMeterCnt() {
        return meterCnt;
    }

    /**
     * 设置每轮补抄数
     *
     * @param meterCnt 每轮补抄数
     */
    public void setMeterCnt(Integer meterCnt) {
        this.meterCnt = meterCnt;
    }

    /**
     * 获取每轮成功采集数
     *
     * @return meter_succ_cnt - 每轮成功采集数
     */
    public Integer getMeterSuccCnt() {
        return meterSuccCnt;
    }

    /**
     * 设置每轮成功采集数
     *
     * @param meterSuccCnt 每轮成功采集数
     */
    public void setMeterSuccCnt(Integer meterSuccCnt) {
        this.meterSuccCnt = meterSuccCnt;
    }

    /**
     * @return item_cnt
     */
    public Integer getItemCnt() {
        return itemCnt;
    }

    /**
     * @param itemCnt
     */
    public void setItemCnt(Integer itemCnt) {
        this.itemCnt = itemCnt;
    }

    /**
     * 获取数据项总数量
     *
     * @return item_succ_cnt - 数据项总数量
     */
    public Integer getItemSuccCnt() {
        return itemSuccCnt;
    }

    /**
     * 设置数据项总数量
     *
     * @param itemSuccCnt 数据项总数量
     */
    public void setItemSuccCnt(Integer itemSuccCnt) {
        this.itemSuccCnt = itemSuccCnt;
    }

    /**
     * @return ready_send
     */
    public Integer getReadySend() {
        return readySend;
    }

    /**
     * @param readySend
     */
    public void setReadySend(Integer readySend) {
        this.readySend = readySend;
    }

    /**
     * @return send_to_gw
     */
    public Integer getSendToGw() {
        return sendToGw;
    }

    /**
     * @param sendToGw
     */
    public void setSendToGw(Integer sendToGw) {
        this.sendToGw = sendToGw;
    }

    /**
     * @return payload_error
     */
    public Integer getPayloadError() {
        return payloadError;
    }

    /**
     * @param payloadError
     */
    public void setPayloadError(Integer payloadError) {
        this.payloadError = payloadError;
    }

    /**
     * @return device_error
     */
    public Integer getDeviceError() {
        return deviceError;
    }

    /**
     * @param deviceError
     */
    public void setDeviceError(Integer deviceError) {
        this.deviceError = deviceError;
    }

    /**
     * @return send_buff_full
     */
    public Integer getSendBuffFull() {
        return sendBuffFull;
    }

    /**
     * @param sendBuffFull
     */
    public void setSendBuffFull(Integer sendBuffFull) {
        this.sendBuffFull = sendBuffFull;
    }

    /**
     * @return unkonwn_device
     */
    public Integer getUnkonwnDevice() {
        return unkonwnDevice;
    }

    /**
     * @param unkonwnDevice
     */
    public void setUnkonwnDevice(Integer unkonwnDevice) {
        this.unkonwnDevice = unkonwnDevice;
    }

    /**
     * @return send_fail
     */
    public Integer getSendFail() {
        return sendFail;
    }

    /**
     * @param sendFail
     */
    public void setSendFail(Integer sendFail) {
        this.sendFail = sendFail;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取每轮开始时间
     *
     * @return st_time - 每轮开始时间
     */
    public Date getStTime() {
        return stTime;
    }

    /**
     * 设置每轮开始时间
     *
     * @param stTime 每轮开始时间
     */
    public void setStTime(Date stTime) {
        this.stTime = stTime;
    }

    /**
     * 获取每轮结束时间
     *
     * @return ed_time - 每轮结束时间
     */
    public Date getEdTime() {
        return edTime;
    }

    /**
     * 设置每轮结束时间
     *
     * @param edTime 每轮结束时间
     */
    public void setEdTime(Date edTime) {
        this.edTime = edTime;
    }
}