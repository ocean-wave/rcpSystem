package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_meterreadqueue")
public class MeterReadQueue implements Serializable {
    private static final long serialVersionUID = 2168165036692831802L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 集中器编号(DeviceNo)
     */
    @Column(name = "jzq_no")
    private String jzqNo;

    /**
     * 集中器唯一编号
     */
    @Column(name = "jzq_cno")
    private String jzqCno;

    /**
     * 电表编号(DeviceNo)
     */
    @Column(name = "meter_no")
    private String meterNo;

    /**
     * 电表唯一标识
     */
    @Column(name = "meter_cno")
    private String meterCno;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

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
     * AFN
     */
    private String afn;

    /**
     * FN
     */
    private String fn;

    /**
     * 队列GUID
     */
    @Column(name = "queue_guid")
    private String queueGuid;

    /**
     * 抄表状态（0-未抄，1-成功，2-失败）
     */
    @Column(name = "read_status")
    private Integer readStatus;

    // 冗余字段
    @Transient
    private String groupGuid;
    @Transient
    private String[] di645 = new String[]{"0001FF00"};
    @Transient
    private String[] dataFormat;
    @Transient
    private int overTime;
    @Transient
    private int isImportant;
    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    public int getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(int isImportant) {
        this.isImportant = isImportant;
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
     * 获取集中器编号(DeviceNo)
     *
     * @return jzq_no - 集中器编号(DeviceNo)
     */
    public String getJzqNo() {
        return jzqNo;
    }

    /**
     * 设置集中器编号(DeviceNo)
     *
     * @param jzqNo 集中器编号(DeviceNo)
     */
    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    /**
     * 获取集中器唯一编号
     *
     * @return jzq_cno - 集中器唯一编号
     */
    public String getJzqCno() {
        return jzqCno;
    }

    /**
     * 设置集中器唯一编号
     *
     * @param jzqCno 集中器唯一编号
     */
    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    /**
     * 获取电表编号(DeviceNo)
     *
     * @return meter_no - 电表编号(DeviceNo)
     */
    public String getMeterNo() {
        return meterNo;
    }

    /**
     * 设置电表编号(DeviceNo)
     *
     * @param meterNo 电表编号(DeviceNo)
     */
    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    /**
     * 获取电表唯一标识
     *
     * @return meter_cno - 电表唯一标识
     */
    public String getMeterCno() {
        return meterCno;
    }

    /**
     * 设置电表唯一标识
     *
     * @param meterCno 电表唯一标识
     */
    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    /**
     * 获取创建人员ID
     *
     * @return create_user_id - 创建人员ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员ID
     *
     * @param createUserId 创建人员ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
     * 获取AFN
     *
     * @return afn - AFN
     */
    public String getAfn() {
        return afn;
    }

    /**
     * 设置AFN
     *
     * @param afn AFN
     */
    public void setAfn(String afn) {
        this.afn = afn;
    }

    /**
     * 获取FN
     *
     * @return fn - FN
     */
    public String getFn() {
        return fn;
    }

    /**
     * 设置FN
     *
     * @param fn FN
     */
    public void setFn(String fn) {
        this.fn = fn;
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
     * 获取抄表状态（0-未抄，1-成功，2-失败）
     *
     * @return read_status - 抄表状态（0-未抄，1-成功，2-失败）
     */
    public Integer getReadStatus() {
        return readStatus;
    }

    /**
     * 设置抄表状态（0-未抄，1-成功，2-失败）
     *
     * @param readStatus 抄表状态（0-未抄，1-成功，2-失败）
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String[] getDi645() {
        return di645;
    }

    public void setDi645(String[] di645) {
        this.di645 = di645;
    }

    public String[] getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String[] dataFormat) {
        this.dataFormat = dataFormat;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }
}