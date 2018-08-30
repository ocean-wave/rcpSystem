package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_day_impsum")
public class DayImpSum implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 抄表日期
     */
    @Column(name = "collect_time")
    private Date collectTime;

    /**
     * 总户数
     */
    @Column(name = "meter_cnt")
    private Integer meterCnt;

    /**
     * 总成功数
     */
    @Column(name = "success_cnt")
    private Integer successCnt;

    /**
     * 广播成功数
     */
    @Column(name = "radio_succ_cnt")
    private Integer radioSuccCnt;

    /**
     * 召测成功数
     */
    @Column(name = "call_succ_cnt")
    private Integer callSuccCnt;

    /**
     * 失败数
     */
    @Column(name = "fail_count")
    private Integer failCount;

    /**
     * 广播成功率
     */
    @Column(name = "radio_succ_rate")
    private BigDecimal radioSuccRate;

    /**
     * 召测成功率
     */
    @Column(name = "call_succ_rate")
    private BigDecimal callSuccRate;

    /**
     * 召测成功率
     */
    @Column(name = "succ_rate")
    private BigDecimal succRate;

    /**
     * 组织结构
     */
    @Column(name = "org_no")
    private Integer orgNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 采集标识
     */
    @Column(name = "queueGuid")
    private String queueguid;

    /**
     * 应用方案的设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

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
     * 获取抄表日期
     *
     * @return collect_time - 抄表日期
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 设置抄表日期
     *
     * @param collectTime 抄表日期
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取总户数
     *
     * @return meter_cnt - 总户数
     */
    public Integer getMeterCnt() {
        return meterCnt;
    }

    /**
     * 设置总户数
     *
     * @param meterCnt 总户数
     */
    public void setMeterCnt(Integer meterCnt) {
        this.meterCnt = meterCnt;
    }

    /**
     * 获取总成功数
     *
     * @return success_cnt - 总成功数
     */
    public Integer getSuccessCnt() {
        return successCnt;
    }

    /**
     * 设置总成功数
     *
     * @param successCnt 总成功数
     */
    public void setSuccessCnt(Integer successCnt) {
        this.successCnt = successCnt;
    }

    /**
     * 获取广播成功数
     *
     * @return radio_succ_cnt - 广播成功数
     */
    public Integer getRadioSuccCnt() {
        return radioSuccCnt;
    }

    /**
     * 设置广播成功数
     *
     * @param radioSuccCnt 广播成功数
     */
    public void setRadioSuccCnt(Integer radioSuccCnt) {
        this.radioSuccCnt = radioSuccCnt;
    }

    /**
     * 获取召测成功数
     *
     * @return call_succ_cnt - 召测成功数
     */
    public Integer getCallSuccCnt() {
        return callSuccCnt;
    }

    /**
     * 设置召测成功数
     *
     * @param callSuccCnt 召测成功数
     */
    public void setCallSuccCnt(Integer callSuccCnt) {
        this.callSuccCnt = callSuccCnt;
    }

    /**
     * 获取失败数
     *
     * @return fail_count - 失败数
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     * 设置失败数
     *
     * @param failCount 失败数
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * 获取广播成功率
     *
     * @return radio_succ_rate - 广播成功率
     */
    public BigDecimal getRadioSuccRate() {
        return radioSuccRate;
    }

    /**
     * 设置广播成功率
     *
     * @param radioSuccRate 广播成功率
     */
    public void setRadioSuccRate(BigDecimal radioSuccRate) {
        this.radioSuccRate = radioSuccRate;
    }

    /**
     * 获取召测成功率
     *
     * @return call_succ_rate - 召测成功率
     */
    public BigDecimal getCallSuccRate() {
        return callSuccRate;
    }

    /**
     * 设置召测成功率
     *
     * @param callSuccRate 召测成功率
     */
    public void setCallSuccRate(BigDecimal callSuccRate) {
        this.callSuccRate = callSuccRate;
    }

    /**
     * 获取召测成功率
     *
     * @return succ_rate - 召测成功率
     */
    public BigDecimal getSuccRate() {
        return succRate;
    }

    /**
     * 设置召测成功率
     *
     * @param succRate 召测成功率
     */
    public void setSuccRate(BigDecimal succRate) {
        this.succRate = succRate;
    }

    /**
     * 获取组织结构
     *
     * @return org_no - 组织结构
     */
    public Integer getOrgNo() {
        return orgNo;
    }

    /**
     * 设置组织结构
     *
     * @param orgNo 组织结构
     */
    public void setOrgNo(Integer orgNo) {
        this.orgNo = orgNo;
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

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取采集标识
     *
     * @return queueGuid - 采集标识
     */
    public String getQueueguid() {
        return queueguid;
    }

    /**
     * 设置采集标识
     *
     * @param queueguid 采集标识
     */
    public void setQueueguid(String queueguid) {
        this.queueguid = queueguid;
    }

    /**
     * 获取应用方案的设备类型
     *
     * @return device_type - 应用方案的设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置应用方案的设备类型
     *
     * @param deviceType 应用方案的设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}