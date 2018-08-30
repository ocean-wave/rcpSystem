package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_metersupptask")
public class MeterSuppTask implements Serializable {
    private static final long serialVersionUID = 1412597327610331577L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 工单描述
     */
    @Column(name = "task_content")
    private String taskContent;

    /**
     * 工单的开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 工单结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 执行人员
     */
    private Integer runtor;

    /**
     * 任务编号
     */
    @Column(name = "task_no")
    private String taskNo;

    /**
     * 0 标识待处理 10 标识掌机已请求 20工单已完成
     */
    private Integer flag;

    /**
     * 工单的创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 工单创建人员
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 补采数量
     */
    @Column(name = "meter_count")
    private Integer meterCount;

    /**
     * 所属组织
     */
    @Column(name = "org_no")
    private Integer orgNo;

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
     * 获取设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     *
     * @return device_type - 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     *
     * @param deviceType 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取工单描述
     *
     * @return task_content - 工单描述
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * 设置工单描述
     *
     * @param taskContent 工单描述
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * 获取工单的开始时间
     *
     * @return start_time - 工单的开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置工单的开始时间
     *
     * @param startTime 工单的开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取工单结束时间
     *
     * @return end_time - 工单结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置工单结束时间
     *
     * @param endTime 工单结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取执行人员
     *
     * @return runtor - 执行人员
     */
    public Integer getRuntor() {
        return runtor;
    }

    /**
     * 设置执行人员
     *
     * @param runtor 执行人员
     */
    public void setRuntor(Integer runtor) {
        this.runtor = runtor;
    }

    /**
     * 获取任务编号
     *
     * @return task_no - 任务编号
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * 设置任务编号
     *
     * @param taskNo 任务编号
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * 获取0 标识待处理 10 标识掌机已请求 20工单已完成
     *
     * @return flag - 0 标识待处理 10 标识掌机已请求 20工单已完成
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置0 标识待处理 10 标识掌机已请求 20工单已完成
     *
     * @param flag 0 标识待处理 10 标识掌机已请求 20工单已完成
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取工单的创建时间
     *
     * @return create_time - 工单的创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置工单的创建时间
     *
     * @param createTime 工单的创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取工单创建人员
     *
     * @return create_user_id - 工单创建人员
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置工单创建人员
     *
     * @param createUserId 工单创建人员
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
     * 获取补采数量
     *
     * @return meter_count - 补采数量
     */
    public Integer getMeterCount() {
        return meterCount;
    }

    /**
     * 设置补采数量
     *
     * @param meterCount 补采数量
     */
    public void setMeterCount(Integer meterCount) {
        this.meterCount = meterCount;
    }

    /**
     * 获取所属组织
     *
     * @return org_no - 所属组织
     */
    public Integer getOrgNo() {
        return orgNo;
    }

    /**
     * 设置所属组织
     *
     * @param orgNo 所属组织
     */
    public void setOrgNo(Integer orgNo) {
        this.orgNo = orgNo;
    }
}