package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 采集状态查询，请求参数
 */
public class CollectStatusQueryParam {
    /**
     * 登录用户id
     */
    @NotNull(message = "userId不能为空")
    private Long userId;

    /**
     * guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 日期
     */
    @NotBlank(message = "date不能为空")
    private String date;

    @NotNull(message = "stopFlag不能为null")
    private Integer stopFlag;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
