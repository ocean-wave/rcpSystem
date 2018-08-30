package cn.com.cdboost.collect.dto.param;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * PC端工单创建传入参数
 */
public class WorkOrderAddParam {
    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 工单内容
     */
    @NotBlank(message = "工单内容不能为空")
    private String taskContent;

    /**
     * 执行人员
     */
    @NotNull(message = "执行人员不能为空")
    private Integer runtor;

    /**
     * 工单截止时间
     */
    @NotBlank(message = "截止时间不能为空")
    private String endTime;

    /**
     * 要补抄的设备信息
     */
    @JSONField(serialize=false)
    @NotEmpty(message = "meters列表不能为空")
    @Valid
    private List<WorkOrderDeviceParam> meters;

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

    public Integer getRuntor() {
        return runtor;
    }

    public void setRuntor(Integer runtor) {
        this.runtor = runtor;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<WorkOrderDeviceParam> getMeters() {
        return meters;
    }

    public void setMeters(List<WorkOrderDeviceParam> meters) {
        this.meters = meters;
    }
}
