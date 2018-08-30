package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_freeze_analyze")
public class FreezeAnalyze implements Serializable {
    private static final long serialVersionUID = -4645047130776609737L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 冻结时间
     */
    @Column(name = "freeze_day")
    private Date freezeDay;

    /**
     * 采集器编号
     */
    @Column(name = "collection_no")
    private String collectionNo;

    /**
     * 采集器地址
     */
    @Column(name = "install_addr")
    private String installAddr;

    /**
     * 采集器下挂设备数量
     */
    @Column(name = "meter_count")
    private Integer meterCount;

    /**
     * 开始轮次
     */
    @Column(name = "min_round")
    private Integer minRound;

    /**
     * 结束轮次
     */
    @Column(name = "max_round")
    private Integer maxRound;

    /**
     * 执行轮次
     */
    @Column(name = "run_round")
    private Integer runRound;

    /**
     * 建档时间
     */
    @Column(name = "install_time")
    private Date installTime;

    /**
     * 开始轮次的时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束轮次的时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 分析时间
     */
    @Column(name = "anal_time")
    private Date analTime;

    /**
     * 集中器编号
     */
    @Column(name = "jzq_no")
    private String jzqNo;

    /**
     * 成功数量
     */
    @Column(name = "succ_count")
    private Integer succCount;

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
     * 获取冻结时间
     *
     * @return freeze_day - 冻结时间
     */
    public Date getFreezeDay() {
        return freezeDay;
    }

    /**
     * 设置冻结时间
     *
     * @param freezeDay 冻结时间
     */
    public void setFreezeDay(Date freezeDay) {
        this.freezeDay = freezeDay;
    }

    /**
     * 获取采集器编号
     *
     * @return collection_no - 采集器编号
     */
    public String getCollectionNo() {
        return collectionNo;
    }

    /**
     * 设置采集器编号
     *
     * @param collectionNo 采集器编号
     */
    public void setCollectionNo(String collectionNo) {
        this.collectionNo = collectionNo;
    }

    /**
     * 获取采集器地址
     *
     * @return install_addr - 采集器地址
     */
    public String getInstallAddr() {
        return installAddr;
    }

    /**
     * 设置采集器地址
     *
     * @param installAddr 采集器地址
     */
    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    /**
     * 获取采集器下挂设备数量
     *
     * @return meter_count - 采集器下挂设备数量
     */
    public Integer getMeterCount() {
        return meterCount;
    }

    /**
     * 设置采集器下挂设备数量
     *
     * @param meterCount 采集器下挂设备数量
     */
    public void setMeterCount(Integer meterCount) {
        this.meterCount = meterCount;
    }

    /**
     * 获取开始轮次
     *
     * @return min_round - 开始轮次
     */
    public Integer getMinRound() {
        return minRound;
    }

    /**
     * 设置开始轮次
     *
     * @param minRound 开始轮次
     */
    public void setMinRound(Integer minRound) {
        this.minRound = minRound;
    }

    /**
     * 获取结束轮次
     *
     * @return max_round - 结束轮次
     */
    public Integer getMaxRound() {
        return maxRound;
    }

    /**
     * 设置结束轮次
     *
     * @param maxRound 结束轮次
     */
    public void setMaxRound(Integer maxRound) {
        this.maxRound = maxRound;
    }

    /**
     * 获取执行轮次
     *
     * @return run_round - 执行轮次
     */
    public Integer getRunRound() {
        return runRound;
    }

    /**
     * 设置执行轮次
     *
     * @param runRound 执行轮次
     */
    public void setRunRound(Integer runRound) {
        this.runRound = runRound;
    }

    /**
     * 获取建档时间
     *
     * @return install_time - 建档时间
     */
    public Date getInstallTime() {
        return installTime;
    }

    /**
     * 设置建档时间
     *
     * @param installTime 建档时间
     */
    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    /**
     * 获取开始轮次的时间
     *
     * @return start_time - 开始轮次的时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始轮次的时间
     *
     * @param startTime 开始轮次的时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束轮次的时间
     *
     * @return end_time - 结束轮次的时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束轮次的时间
     *
     * @param endTime 结束轮次的时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取分析时间
     *
     * @return anal_time - 分析时间
     */
    public Date getAnalTime() {
        return analTime;
    }

    /**
     * 设置分析时间
     *
     * @param analTime 分析时间
     */
    public void setAnalTime(Date analTime) {
        this.analTime = analTime;
    }

    /**
     * 获取集中器编号
     *
     * @return jzq_no - 集中器编号
     */
    public String getJzqNo() {
        return jzqNo;
    }

    /**
     * 设置集中器编号
     *
     * @param jzqNo 集中器编号
     */
    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    /**
     * 获取成功数量
     *
     * @return succ_count - 成功数量
     */
    public Integer getSuccCount() {
        return succCount;
    }

    /**
     * 设置成功数量
     *
     * @param succCount 成功数量
     */
    public void setSuccCount(Integer succCount) {
        this.succCount = succCount;
    }
}