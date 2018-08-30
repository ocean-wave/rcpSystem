package cn.com.cdboost.collect.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * APP补抄工单列表,返回的实体对象
 */
public class WorkOrder implements Serializable{

    private static final long serialVersionUID = -8708109080894274104L;

    /**
     * 工单内容
     */
    private String taskContent;

    /**
     * 工单的开始时间
     */
    private Date startTime ;

    /**
     * 工单的结束时间
     */
    private Date endTime;

    /**
     * 执行人员
     */
    private String runtor;

    /**
     * 执行人员姓名
     */
    private String runtorName;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 工单的创建时间
     */
    private Date createTime;

    /**
     * 工单数量
     */
    private Integer meterCount;

    /**
     * 设备类型 07电表 08 水表 09 气表
     */
    private String deviceType;

    /**
     * 创建人员姓名
     */
    private String createName;


    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRuntor() {
        return runtor;
    }

    public void setRuntor(String runtor) {
        this.runtor = runtor;
    }

    public String getRuntorName() {
        return runtorName;
    }

    public void setRuntorName(String runtorName) {
        this.runtorName = runtorName;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(Integer meterCount) {
        this.meterCount = meterCount;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
