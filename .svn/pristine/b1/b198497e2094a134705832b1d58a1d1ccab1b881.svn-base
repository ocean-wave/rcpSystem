package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_metercollectdata")
public class MeterCollectData implements Serializable {
    private static final long serialVersionUID = 1560692420335245855L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 抄表项名称
     */
    @Column(name = "collect_name")
    private String collectName;

    /**
     * 抄表标识（0000FF00）
     */
    @Column(name = "mr_flag")
    private String mrFlag;

    /**
     * 示数类型
     */
    @Column(name = "read_type")
    private Integer readType;

    /**
     * 示数
     */
    @Column(name = "read_value")
    private BigDecimal readValue;

    /**
     * 抄表日期
     */
    @Column(name = "collect_date")
    private Date collectDate;

    /**
     * 是否实时数据(1-实时，0-历史)
     */
    @Column(name = "is_real_time")
    private Integer isRealTime;

    /**
     * 队列GUID
     */
    @Column(name = "queue_guid")
    private String queueGuid;

    /**
     * 组数据标识,标识一组数据
     */
    @Column(name = "group_guid")
    private String groupGuid;

    /**
     * 抄表时间
     */
    @Column(name = "collect_time")
    private Date collectTime;

    /**
     * 冻结抄表抄收轮次(em_d_freezeflg)
     */
    private Integer round;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @return cno - 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @param cno 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取抄表项名称
     *
     * @return collect_name - 抄表项名称
     */
    public String getCollectName() {
        return collectName;
    }

    /**
     * 设置抄表项名称
     *
     * @param collectName 抄表项名称
     */
    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    /**
     * 获取抄表标识（0000FF00）
     *
     * @return mr_flag - 抄表标识（0000FF00）
     */
    public String getMrFlag() {
        return mrFlag;
    }

    /**
     * 设置抄表标识（0000FF00）
     *
     * @param mrFlag 抄表标识（0000FF00）
     */
    public void setMrFlag(String mrFlag) {
        this.mrFlag = mrFlag;
    }

    /**
     * 获取示数类型
     *
     * @return read_type - 示数类型
     */
    public Integer getReadType() {
        return readType;
    }

    /**
     * 设置示数类型
     *
     * @param readType 示数类型
     */
    public void setReadType(Integer readType) {
        this.readType = readType;
    }

    /**
     * 获取示数
     *
     * @return read_value - 示数
     */
    public BigDecimal getReadValue() {
        return readValue;
    }

    /**
     * 设置示数
     *
     * @param readValue 示数
     */
    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    /**
     * 获取抄表日期
     *
     * @return collect_date - 抄表日期
     */
    public Date getCollectDate() {
        return collectDate;
    }

    /**
     * 设置抄表日期
     *
     * @param collectDate 抄表日期
     */
    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    /**
     * 获取是否实时数据(1-实时，0-历史)
     *
     * @return is_real_time - 是否实时数据(1-实时，0-历史)
     */
    public Integer getIsRealTime() {
        return isRealTime;
    }

    /**
     * 设置是否实时数据(1-实时，0-历史)
     *
     * @param isRealTime 是否实时数据(1-实时，0-历史)
     */
    public void setIsRealTime(Integer isRealTime) {
        this.isRealTime = isRealTime;
    }

    /**
     * 获取队列GUID
     *
     * @return queue_guid - 队列GUID
     */
    public String getQueueGuid() {
        return queueGuid;
    }

    /**
     * 设置队列GUID
     *
     * @param queueGuid 队列GUID
     */
    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }

    /**
     * 获取组数据标识,标识一组数据
     *
     * @return group_guid - 组数据标识,标识一组数据
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * 设置组数据标识,标识一组数据
     *
     * @param groupGuid 组数据标识,标识一组数据
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * 获取抄表时间
     *
     * @return collect_time - 抄表时间
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 设置抄表时间
     *
     * @param collectTime 抄表时间
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取冻结抄表抄收轮次(em_d_freezeflg)
     *
     * @return round - 冻结抄表抄收轮次(em_d_freezeflg)
     */
    public Integer getRound() {
        return round;
    }

    /**
     * 设置冻结抄表抄收轮次(em_d_freezeflg)
     *
     * @param round 冻结抄表抄收轮次(em_d_freezeflg)
     */
    public void setRound(Integer round) {
        this.round = round;
    }
}