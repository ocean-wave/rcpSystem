package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 实时采集右边树，模糊查询接口入参
 */
public class FuzzyQueryTreeParam {
    /**
     * 设备类型
     */
    @NotBlank(message = "设备类型不能为空")
    private String deviceType;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 在线状态 1在线，0不在线
     */
    private Integer onlineStatus;

    /**
     * 是否重点用户 1重点用户，0非重点用户
     */
    private Integer isImportant;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }
}
