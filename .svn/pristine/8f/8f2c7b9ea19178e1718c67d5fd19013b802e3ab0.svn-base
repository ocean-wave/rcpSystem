package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 补抄工单列表查询参数
 */
public class MeterSuppTaskGetQueryParam extends QueryListParam {
    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 工单内容
     */
    private String taskContent;

    /**
     * 20已完成，10已派发，0未完成
     */
    private String taskFlag;

    /**
     * 工单开始时间
     */
    @NotBlank(message = "startTime不能为空")
    private String startTime;

    /**
     * 工单结束时间
     */
    @NotBlank(message = "endTime不能为空")
    private String endTime;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
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

    public String getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
