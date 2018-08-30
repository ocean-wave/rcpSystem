package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 工单上传时的相关信息
 */
public class WorkOrder4Upload {
    /**
     * 任务编号
     */
    @NotBlank(message = "taskNo不能为空")
    private String taskNo;

    /**
     * 设备类型 07电表 08 水表 09 气表
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 工单内容
     */
    @NotBlank(message = "taskContent不能为空")
    private String taskContent;

    /**
     * 工单的开始时间
     */
    @NotNull(message = "startTime不能为空")
    private Date startTime ;

    /**
     * 工单的结束时间
     */
    @NotNull(message = "endTime不能为空")
    private Date endTime;

    /**
     * 采集数量
     */
    @NotNull(message = "meterCount不能为空")
    private Integer meterCount;

    /**
     * 工单状态，1已完成，0未完成
     */
    @NotNull(message = "flag不能为空")
    private Integer flag;

    @NotEmpty(message = "workOrderDetails列表不能为空")
    @Valid
    private List<WorkOrderDetail4Upload> workOrderDetails;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

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

    public Integer getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(Integer meterCount) {
        this.meterCount = meterCount;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<WorkOrderDetail4Upload> getWorkOrderDetails() {
        return workOrderDetails;
    }

    public void setWorkOrderDetails(List<WorkOrderDetail4Upload> workOrderDetails) {
        this.workOrderDetails = workOrderDetails;
    }
}
