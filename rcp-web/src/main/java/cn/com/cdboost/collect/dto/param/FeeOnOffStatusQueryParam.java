package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 通断电操作，轮询操作状态查询参数
 */
public class FeeOnOffStatusQueryParam {
    /**
     * 通断操作，前端生成的guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 查询时间
     */
    @NotBlank(message = "date不能为空")
    private String date;

    /**
     * 停止标志 0前端未发起停止命令
     */
    @NotNull(message = "stopFlag不能为null")
    private Integer stopFlag;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }
}
